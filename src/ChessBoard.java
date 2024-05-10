import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

public class ChessBoard extends JFrame {

    private JPanel grid;

    protected static Cell[][] cells = new Cell[8][8];
    private static JLayeredPane layeredPane = new JLayeredPane();
    protected static ArrayList<Point> subChoosedMove = new ArrayList<>();
    protected static ArrayList<Point> killableMove = new ArrayList<>();
    protected static ArrayList<Point> pieceAvailable = new ArrayList<>();
    private static Color[][] colors = new Color[8][8];
    private int[] numbers = { 8, 7, 6, 5, 4, 3, 2, 1 };
    private static String[] letters = { "a", "b", "c", "d", "e", "f", "g", "h" };
    protected static boolean state = true;

    private Piece piece;
    private boolean firstMove = false;
    protected static int turnCount = 0;
    private int x, y;
    protected static boolean moved = false;
    private Icon privateIcon;

    private MouseAdapter mouseAdapter = new MouseAdapter() {
        @Override
        public void mousePressed(MouseEvent e) {
            handleMousePress(e);
        }
    };

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        // if(turnCount % 2 == 1) {
        //     System.out.println(Minimax.generatePossibleMoves());
        //     Minimax.applyMove(Minimax.minimaxMove());
        //     System.out.println(Minimax.minimaxMove());
        //     ChessBoard.setAllCellBackground();
        //     ChessBoard.setFalseCellsChoosed();
        //     ChessBoard.setFalseCellsKilled();
        //     turnCount++;
        // }
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (cells[i][j].isChoosed()) {
                    Piece.showMove(i, j, (turnCount % 2 == 0) ? true : false);
                }
            }
            // if(isUnderCheckMate()) {
            //     JOptionPane.showMessageDialog(null, "Check Mate");
            // }
        }
        setTrueKingMoved();
        checkPromotion();
        // if(isUnderCheckMate()) {
        //     JOptionPane.showMessageDialog(null, "Check Mate");
        // }
    }

    public ChessBoard() {
        initializeBoard();
        setupUI();
    }

    public ChessBoard(int x) {

    }

    protected void resetBoard() {
        setAllCellBackground(); // Reimposta il background delle celle
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                setInitialPieces(i, j);     // Reimposta i pezzi iniziali
            }
        }
        setFalseCellsChoosed(); // Reimposta le celle selezionate
        setFalseCellsKilled();  // Reimposta le celle uccise
        turnCount = 0;          // Reimposta il conteggio dei turni
        repaint();              // Ridisegna la scacchiera
    }
    
    protected void initializeBoard() {
        grid = new JPanel();
        grid.setLayout(new GridLayout(8, 8));
        setChessBoard();
    }

    private void setupUI() {
        this.add(layeredPane);
        this.setSize(616, 618);
        grid.setBounds(0, 0, 576, 576);
        this.add(grid);
        this.setLocation(400, 100);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    private void setChessBoard() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                cells[i][j] = new Cell(i * 72, j * 72);
                cells[i][j].setCoordinate(numbers[i]+letters[j]);
                setCellBackground(i, j);
                setInitialPieces(i, j);
                repaint();
                cells[i][j].setOpaque(true);
                cells[i][j].addMouseListener(mouseAdapter);
                grid.add(cells[i][j]);
            }
        }
        this.setUndecorated(true);
    }

    private void setCellBackground(int i, int j) {
        Color lightColor = (i + j) % 2 == 0 ? new Color(248, 212, 186) : new Color(151, 94, 77);
        cells[i][j].setBackground(lightColor);
        colors[i][j] = lightColor;
    }

    protected static Color chooseColor(int i, int j, int mode) {
        if(colors[i][j].equals(new Color(248, 212, 186))) {
            if(mode == 0) { // choosed
                return new Color(184, 214, 153);
            } else if(mode == 1) { // subChoosed
                return new Color(212, 196, 181);
            } else if(mode == 2) { // killable
                return new Color(252, 232, 144);
            } else if(mode == 3) { // checked
                return new Color(217, 153, 97);
            }
        } else {
            if(mode == 0) { // choosed
                return new Color(90, 105, 72);
            } else if(mode == 1) { // subChoosed
                return new Color(125, 95, 86);
            } else if(mode == 2) { // killable
                return new Color(193, 143, 39);
            } else if(mode == 3) { // checked
                return new Color(140, 64, 51);
            }
        }
        return null;
    }

    protected static void setAllCellBackground() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Color lightColor = (i + j) % 2 == 0 ? new Color(248, 212, 186) : new Color(151, 94, 77);
                cells[i][j].setBackground(lightColor);
            }
        }
    }

    private void setInitialPieces(int i, int j) {
        if (i == 0 || i == 7) {
            piece = new Piece(choosePiece(i, j));
            cells[i][j].setIcon(piece.getIcon());
        } else if (i == 1 || i == 6) {
            piece = new Piece((i == 1) ? ("src/resources/BlackPawn.png") : ("src/resources/WhitePawn.png"));
            cells[i][j].setIcon(piece.getIcon());
        } else {
            cells[i][j].setIcon(null);
        }
    }

    protected static void checkPromotion() {
        try {
            for (int i = 0; i < 8; i++) {
                if (cells[0][i].getIcon().toString().contains("WhitePaw")) {
                    cells[0][i].setIcon(new ImageIcon("src/resources/WhiteQueen.png"));
                }
                if (cells[7][i].getIcon().toString().contains("BlackPaw")) {
                    cells[7][i].setIcon(new ImageIcon("src/resources/BlackQueen.png"));
                }
            }
        } catch (NullPointerException e) {}
    }

    protected static void setFalseCellsChoosed() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                cells[i][j].setChoosed(false);
                cells[i][j].setSubChoosed(false);
            }
        }
    }

    protected static boolean isCellsChoosed() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (cells[i][j].isSubChoosed()) {
                    return true;
                }
            }
        }
        return false;
    }    

    protected static boolean isCellsKillable() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (cells[i][j].isKillable()) {
                    return true;
                }
            }
        }
        return false;
    }    

    protected static void setFalseCellsKilled() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                cells[i][j].setKillable(false);
            }
        }
    }

    private void handleMousePress(MouseEvent e) {
        if (e.getSource() instanceof Cell) {
            Cell clickedCell = (Cell) e.getSource();
            try {
                piece.setIcon(clickedCell.getIcon());
                if (piece.isWhite()) {
                    firstMove = true;
                }
                if (firstMove) {
                    setFalseCellsChoosed();
                    if (!clickedCell.isKillable()) {
                        clickedCell.setChoosed(true);
                    }
                    if (clickedCell.getY() == 432 || clickedCell.getY() == 72) {
                        moved = false;
                    } else {
                        moved = true;
                    }
                    if (clickedCell.isChoosed()) {
                        privateIcon = clickedCell.getIcon();
                        x = clickedCell._getX();
                        y = clickedCell._getY();
                    }
                    for (int i = 0; i < 8; i++) {
                        for (int j = 0; j < 8; j++) {
                            
                            if(clickedCell.isKillable()) {
                                if (cells[i][j]._getX() == x && cells[i][j]._getY() == y && clickedCell.isKillable()) {
                                    cells[i][j].setIcon(null);
                                    turnCount++;
                                }
                            }
                        }
                    }
                    clickedCell.setIcon(privateIcon);
                    setFalseCellsKilled();
                    setAllCellBackground();
                }
            } catch (java.lang.NullPointerException ex) {
                if (clickedCell.isChoosed() || clickedCell.isSubChoosed()) {
                    for (int i = 0; i < 8; i++) {
                        for (int j = 0; j < 8; j++) {
                            if(j != 0) {
                                if (cells[i][j]._getX() == x && cells[i][j]._getY() == y) {
                                    cells[i][j].setIcon(null);
                                }
                            }
                            if(j != 7) {
                                if (cells[i][j]._getX() == x && cells[i][j]._getY() == y) {
                                    cells[i][j].setIcon(null);
                                }
                            }
                            if (cells[i][j].isChoosed()) {
                                cells[i][j].setIcon(null);
                                turnCount++;
                            }
                        }
                    }
                    clickedCell.setIcon(privateIcon);
                }
                // setTrueKingMoved();
                // checkPromotion();
                if(isUnderCheckMate()) {
                    JOptionPane.showMessageDialog(null, "Check Mate");
                }
                setFalseCellsChoosed();
                setFalseCellsKilled();
                setAllCellBackground();
            }
        }
        repaint();
    }

    protected static boolean isUnderCheckMate()  {
        if(Piece.isUnderAttack(((turnCount % 2 == 0) ? true : false)) == 1) {
            if(Piece.isCheckMate(((turnCount % 2 == 0) ? true : false))) {
                // setAllCellBackground();
                // setFalseCellsChoosed();
                // setFalseCellsKilled();
                return true;
            } else {
                setAllCellBackground();
                setFalseCellsChoosed();
                setFalseCellsKilled();
                return false;
            }
        }
        return false;
    }

    private void setTrueKingMoved() {
        if(cells[0][4].getIcon() == null) {
            Piece.bKingMoved = true;
        }
        if(cells[7][4].getIcon() == null) {
            Piece.wKingMoved = true;
        }
    }


    private String choosePiece(int a, int n) {
        String colorPrefix = (a < 5) ? "Black" : "White";
        switch (n) {
            case 0:
            case 7:
                return "src/resources/" + colorPrefix + "Tower.png";
            case 1:
            case 6:
                return "src/resources/" + colorPrefix + "Knight.png";
            case 2:
            case 5:
                return "src/resources/" + colorPrefix + "Bishop.png";
            case 3:
                return "src/resources/" + colorPrefix + "Queen.png";
            case 4:
                return "src/resources/" + colorPrefix + "King.png";
            default:
                return "";
        }
    }
}