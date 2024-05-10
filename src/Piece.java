import javax.swing.*;

public class Piece extends JLabel {

    private static boolean tinyCastling = false, bigCastling = false;
    protected static int subChoosed = 0;
    protected static boolean wKingMoved = false, bKingMoved = false;
    private static ImageIcon Wking = new ImageIcon("src/resources/WhiteKing.png");
    private static ImageIcon Wrook = new ImageIcon("src/resources/WhiteTower.png");
    private static ImageIcon Bking = new ImageIcon("src/resources/BlackKing.png");
    private static ImageIcon Brook = new ImageIcon("src/resources/BlackTower.png");

    public Piece(String path) {
        setIcon(new ImageIcon(path));
    }

    public boolean isWhite() {
        return getIcon().toString().contains("White");
    }
    public boolean isBlack() {
        return getIcon().toString().contains("Black");
    }

    private static boolean hintActive() {
        if(Gui.getTextHintButton().equals("Hide Move")) {
            return true;
        } else {
            return false;
        }
    }

    public static void showMove(int i, int j,boolean color) {
        ChessBoard.setAllCellBackground();
        if (hintActive()) {
            ChessBoard.cells[i][j].setBackground(ChessBoard.chooseColor(i, j, 0));
        }
        try {
            if (ChessBoard.cells[i][j].getIcon().toString().contains("Pawn")) {
                pawnMove(color);
            }
        } catch (NullPointerException e) {}
        try {
            if (ChessBoard.cells[i][j].getIcon().toString().contains("Knight")) {
                knightMove(color);
            }
        } catch (NullPointerException e) {}
        try {
            if (ChessBoard.cells[i][j].getIcon().toString().contains("Bishop")) {
                bishopMove(color);
            }
        } catch (NullPointerException e) {}
        try {
            if (ChessBoard.cells[i][j].getIcon().toString().contains("Tower")) {
                rookMove(color);
            }
        } catch (NullPointerException e) {}
        try {
            if (ChessBoard.cells[i][j].getIcon().toString().contains("Queen")) {
                queenMove(color);
            }
        } catch (NullPointerException e) {}
        try {
            if (ChessBoard.cells[i][j].getIcon().toString().contains("King")) {
                kingMove(color);
            }
        } catch (NullPointerException e) {}
    }

    private static void pawnMove(boolean color) {

        ImageIcon pawIcon = new ImageIcon("src/resources/"+((color)? "WhitePawn.png":"BlackPawn.png"));

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (ChessBoard.cells[i][j].isChoosed()) {
                    if (color) {
                        if (ChessBoard.cells[i][j].isChoosed()
                                && ChessBoard.cells[i][j].getIcon().toString().contains("Black")) {
                            return;
                        }
                        ChessBoard.cells[i - 1][j].setSubChoosed(true);
                        if (ChessBoard.cells[i - 1][j].getIcon() != null) {
                            ChessBoard.cells[i - 1][j].setSubChoosed(false);
                        } else {
                            ChessBoard.cells[i - 1][j].setIcon(pawIcon);
                            ChessBoard.cells[i][j].setIcon(null);
                            if(isUnderAttack(color) == 1) {
                                ChessBoard.cells[i - 1][j].setIcon(null);
                                ChessBoard.cells[i][j].setIcon(pawIcon);
                                ChessBoard.cells[i - 1][j].setSubChoosed(false);
                            } else {
                                ChessBoard.cells[i - 1][j].setIcon(null);
                                ChessBoard.cells[i][j].setIcon(pawIcon);
                                ChessBoard.cells[i - 1][j].setSubChoosed(true);
                                if(hintActive()) {
                                    ChessBoard.cells[i - 1][j].setBackground(ChessBoard.chooseColor(i - 1, j, 1));
                                }
                            }
                        }
                        try {
                            if (!ChessBoard.moved) {
                                ChessBoard.cells[i - 2][j].setSubChoosed(true);
                                if (ChessBoard.cells[i - 2][j].getIcon() != null
                                    || ChessBoard.cells[i - 1][j].getIcon() != null) {
                                        ChessBoard.cells[i - 2][j].setSubChoosed(false);
                                } else {
                                    ChessBoard.cells[i - 2][j].setIcon(pawIcon);
                                    ChessBoard.cells[i][j].setIcon(null);
                                    if(isUnderAttack(color) == 1) {
                                        ChessBoard.cells[i - 2][j].setIcon(null);
                                        ChessBoard.cells[i][j].setIcon(pawIcon);
                                        ChessBoard.cells[i - 2][j].setSubChoosed(false);
                                    } else {
                                        ChessBoard.cells[i - 2][j].setIcon(null);
                                        ChessBoard.cells[i][j].setIcon(pawIcon);
                                        ChessBoard.cells[i - 2][j].setSubChoosed(true);
                                        if(hintActive()) {
                                            ChessBoard.cells[i - 2][j].setBackground(ChessBoard.chooseColor(i - 2, j, 1));
                                        }
                                    }
                                }
                            }
                        } catch (ArrayIndexOutOfBoundsException e) {}
                        if (j != 0) {
                            if (ChessBoard.cells[i - 1][j - 1].getIcon() != null
                                && !ChessBoard.cells[i - 1][j - 1].getIcon().toString().contains("White")) {
                                ImageIcon pieceReplaced = new ImageIcon(ChessBoard.cells[i - 1][j - 1].getIcon().toString());
                                ChessBoard.cells[i - 1][j - 1].setIcon(pawIcon);
                                ChessBoard.cells[i][j].setIcon(null);
                                if (isUnderAttack(color) == 1) {
                                    ChessBoard.cells[i - 1][j - 1].setIcon(pieceReplaced);
                                    ChessBoard.cells[i][j].setIcon(pawIcon);
                                    ChessBoard.cells[i - 1][j - 1].setKillable(false);
                                } else {
                                    ChessBoard.cells[i - 1][j - 1].setIcon(pieceReplaced);
                                    ChessBoard.cells[i][j].setIcon(pawIcon);
                                    if(hintActive()) {
                                        ChessBoard.cells[i - 1][j - 1].setBackground(ChessBoard.chooseColor(i - 1, j - 1, 2));
                                    }
                                    ChessBoard.cells[i - 1][j - 1].setKillable(true);
                                }
                            }
                        }
                        if (j != 7) {
                            if (ChessBoard.cells[i - 1][j + 1].getIcon() != null
                            && !ChessBoard.cells[i - 1][j + 1].getIcon().toString().contains("White")) {
                                ImageIcon pieceReplaced = new ImageIcon(ChessBoard.cells[i - 1][j + 1].getIcon().toString());
                                ChessBoard.cells[i - 1][j + 1].setIcon(pawIcon);
                                ChessBoard.cells[i][j].setIcon(null);
                                if (isUnderAttack(color) == 1) {
                                    ChessBoard.cells[i - 1][j + 1].setIcon(pieceReplaced);
                                    ChessBoard.cells[i][j].setIcon(pawIcon);
                                    ChessBoard.cells[i - 1][j + 1].setKillable(false);
                                } else {
                                    ChessBoard.cells[i - 1][j + 1].setIcon(pieceReplaced);
                                    ChessBoard.cells[i][j].setIcon(pawIcon);
                                    if(hintActive()) {
                                        ChessBoard.cells[i - 1][j + 1].setBackground(ChessBoard.chooseColor(i - 1, j + 1, 2));
                                    }
                                    ChessBoard.cells[i - 1][j + 1].setKillable(true);
                                }
                            }
                        }
                    } else {
                        if (ChessBoard.cells[i][j].isChoosed()
                            && ChessBoard.cells[i][j].getIcon().toString().contains("White")) {
                            return;
                        }
                        ChessBoard.cells[i + 1][j].setSubChoosed(true);
                        if (ChessBoard.cells[i + 1][j].getIcon() != null) {
                            ChessBoard.cells[i + 1][j].setSubChoosed(false);
                        } else {
                            ChessBoard.cells[i + 1][j].setIcon(pawIcon);
                            ChessBoard.cells[i][j].setIcon(null);
                            if(isUnderAttack(color) == 1) {
                                ChessBoard.cells[i + 1][j].setIcon(null);
                                ChessBoard.cells[i][j].setIcon(pawIcon);
                                ChessBoard.cells[i + 1][j].setSubChoosed(false);
                            } else {
                                ChessBoard.cells[i + 1][j].setIcon(null);
                                ChessBoard.cells[i][j].setIcon(pawIcon);
                                ChessBoard.cells[i + 1][j].setSubChoosed(true);
                                if(hintActive()) {
                                    ChessBoard.cells[i + 1][j].setBackground(ChessBoard.chooseColor(i + 1, j, 1));
                                }
                            }
                        }
                        try {
                            if (!ChessBoard.moved) {
                                ChessBoard.cells[i + 2][j].setSubChoosed(true);
                                if (ChessBoard.cells[i + 2][j].getIcon() != null
                                || ChessBoard.cells[i + 1][j].getIcon() != null) {
                                    ChessBoard.cells[i + 2][j].setSubChoosed(false);
                                } else {
                                    ChessBoard.cells[i + 2][j].setIcon(pawIcon);
                                    ChessBoard.cells[i][j].setIcon(null);
                                    if(isUnderAttack(color) == 1) {
                                        ChessBoard.cells[i + 2][j].setIcon(null);
                                        ChessBoard.cells[i][j].setIcon(pawIcon);
                                        ChessBoard.cells[i + 2][j].setSubChoosed(false);
                                    } else {
                                        ChessBoard.cells[i + 2][j].setIcon(null);
                                        ChessBoard.cells[i][j].setIcon(pawIcon);
                                        ChessBoard.cells[i + 2][j].setSubChoosed(true);
                                        if(hintActive()) {
                                            ChessBoard.cells[i + 2][j].setBackground(ChessBoard.chooseColor(i + 2, j, 1));
                                        }
                                    }
                                }
                            }
                        } catch (ArrayIndexOutOfBoundsException e) {}
                        if (j != 7) {
                            if (ChessBoard.cells[i + 1][j + 1].getIcon() != null
                                && !ChessBoard.cells[i + 1][j + 1].getIcon().toString().contains("Black")) {
                                ImageIcon pieceReplaced = new ImageIcon(ChessBoard.cells[i + 1][j + 1].getIcon().toString());
                                ChessBoard.cells[i + 1][j + 1].setIcon(pawIcon);
                                ChessBoard.cells[i][j].setIcon(null);
                                if (isUnderAttack(color) == 1) {
                                    ChessBoard.cells[i + 1][j + 1].setIcon(pieceReplaced);
                                    ChessBoard.cells[i][j].setIcon(pawIcon);
                                    ChessBoard.cells[i + 1][j + 1].setKillable(false);
                                } else {
                                    ChessBoard.cells[i + 1][j + 1].setIcon(pieceReplaced);
                                    ChessBoard.cells[i][j].setIcon(pawIcon);
                                    if(hintActive()) {
                                        ChessBoard.cells[i + 1][j + 1].setBackground(ChessBoard.chooseColor(i + 1, j + 1, 2));
                                    }
                                    ChessBoard.cells[i + 1][j + 1].setKillable(true);
                                }
                            }
                        }
                        if (j != 0) {
                            if (ChessBoard.cells[i + 1][j - 1].getIcon() != null
                                && !ChessBoard.cells[i + 1][j - 1].getIcon().toString().contains("Black")) {
                                ImageIcon pieceReplaced = new ImageIcon(ChessBoard.cells[i + 1][j - 1].getIcon().toString());
                                ChessBoard.cells[i + 1][j - 1].setIcon(pawIcon);
                                ChessBoard.cells[i][j].setIcon(null);
                                if (isUnderAttack(color) == 1) {
                                    ChessBoard.cells[i + 1][j - 1].setIcon(pieceReplaced);
                                    ChessBoard.cells[i][j].setIcon(pawIcon);
                                    ChessBoard.cells[i + 1][j - 1].setKillable(false);
                                } else {
                                    ChessBoard.cells[i + 1][j - 1].setIcon(pieceReplaced);
                                    ChessBoard.cells[i][j].setIcon(pawIcon);
                                    if(hintActive()) {
                                        ChessBoard.cells[i + 1][j - 1].setBackground(ChessBoard.chooseColor(i + 1, j - 1, 2));
                                    }
                                    ChessBoard.cells[i + 1][j - 1].setKillable(true);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private static void knightMove(boolean color) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (ChessBoard.cells[i][j].isChoosed()) {
                    if (color) {
                        if (ChessBoard.cells[i][j].isChoosed()
                                && ChessBoard.cells[i][j].getIcon().toString().contains("Black")) {
                            return;
                        }
                        // ---------------------------
                        handleKnightMove(i, j, -2, -1, true, false);
                        // ---------------------------
                        handleKnightMove(i, j, -2, +1, true, false);
                        // ---------------------------
                        handleKnightMove(i, j, -1, +2, true, false);
                        // ---------------------------
                        handleKnightMove(i, j, +1, +2, true, false);
                        // ---------------------------
                        handleKnightMove(i, j, +2, +1, true, false);
                        // ---------------------------
                        handleKnightMove(i, j, +2, -1, true, false);
                        // ---------------------------
                        handleKnightMove(i, j, +1, -2, true, false);
                        // ---------------------------
                        handleKnightMove(i, j, -1, -2, true, false);
                        // ---------------------------
                    } else {
                        if (ChessBoard.cells[i][j].isChoosed()
                                && ChessBoard.cells[i][j].getIcon().toString().contains("White")) {
                            return;
                        }
                        // ---------------------------
                        handleKnightMove(i, j, +2, +1, false, false);
                        // ---------------------------
                        handleKnightMove(i, j, -2, -1, false, false);
                        // ---------------------------
                        handleKnightMove(i, j, -2, +1, false, false);
                        // ---------------------------
                        handleKnightMove(i, j, -1, +2, false, false);
                        // ---------------------------
                        handleKnightMove(i, j, +1, +2, false, false);
                        // ---------------------------
                        handleKnightMove(i, j, +2, -1, false, false);
                        // ---------------------------
                        handleKnightMove(i, j, +1, -2, false, false);
                        // ---------------------------
                        handleKnightMove(i, j, -1, -2, false, false);
                        // ---------------------------
                    }
                }
            }
        }
    }

    private static void handleKnightMove( int i, int j, int p, int s, boolean color, boolean king) {

        ImageIcon knightIcon = new ImageIcon("src/resources/"+ ((color)? "WhiteKnight.png" : "BlackKnight.png"));
        ImageIcon kingIcon = new ImageIcon("src/resources/"+ ((color)? "WhiteKing.png" : "BlackKing.png"));

        if (i + (p) < 8 && i + (p) >= 0 && j + (s) < 8 && j + (s) >= 0) {
            ChessBoard.cells[i + (p)][j + (s)].setSubChoosed(true);
            if(ChessBoard.cells[i + (p)][j + (s)].getIcon() != null
            && ChessBoard.cells[i + (p)][j + (s)].getIcon().toString().contains((color) ? "White" : "Black")) {
                ChessBoard.cells[i + (p)][j + (s)].setSubChoosed(false);
            }
            if (ChessBoard.cells[i + (p)][j + (s)].getIcon() != null
            && !ChessBoard.cells[i + (p)][j + (s)].getIcon().toString().contains((color) ? "White" : "Black")) {
                ChessBoard.cells[i + (p)][j + (s)].setSubChoosed(false);
                ImageIcon pieceReplaced = new ImageIcon(ChessBoard.cells[i + (p)][j + (s)].getIcon().toString());
                ChessBoard.cells[i + (p)][j + (s)].setIcon((king)? kingIcon:knightIcon);
                ChessBoard.cells[i][j].setIcon(null);
                if (isUnderAttack(color) == 1) {
                    ChessBoard.cells[i + (p)][j + (s)].setIcon(pieceReplaced);
                    ChessBoard.cells[i][j].setIcon((king)? kingIcon:knightIcon);
                    ChessBoard.cells[i + (p)][j + (s)].setKillable(false);
                } else {
                    ChessBoard.cells[i + (p)][j + (s)].setIcon(pieceReplaced);
                    ChessBoard.cells[i][j].setIcon((king)? kingIcon:knightIcon);
                    if(hintActive()) {
                        ChessBoard.cells[i + (p)][j + (s)].setBackground(ChessBoard.chooseColor(i + (p), j + (s), 2));
                    }
                    ChessBoard.cells[i + (p)][j + (s)].setKillable(true);
                }
                // ChessBoard.cells[i + (p)][j + (s)].setKillable(true);
            } else if (ChessBoard.cells[i + (p)][j + (s)].getIcon() == null) {
                ChessBoard.cells[i + (p)][j + (s)].setIcon((king)? kingIcon:knightIcon);
                ChessBoard.cells[i][j].setIcon(null);
                if(isUnderAttack(color) == 1) {
                    ChessBoard.cells[i + (p)][j + (s)].setIcon(null);
                    ChessBoard.cells[i][j].setIcon((king)? kingIcon:knightIcon);
                    ChessBoard.cells[i + (p)][j + (s)].setSubChoosed(false);
                } else {
                    ChessBoard.cells[i +(p)][j + (s)].setIcon(null);
                    ChessBoard.cells[i][j].setIcon((king)? kingIcon:knightIcon);
                    ChessBoard.cells[i +(p)][j + (s)].setSubChoosed(true);
                    if(hintActive()) {
                        ChessBoard.cells[i +(p)][j + (s)].setBackground(ChessBoard.chooseColor(i +(p), j + (s), 1));
                    }
                }
            }
        } else {
            return;
        }
    }

    private static void bishopMove(boolean color) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (ChessBoard.cells[i][j].isChoosed()) {
                    if (color) {
                        if (ChessBoard.cells[i][j].isChoosed()
                                && ChessBoard.cells[i][j].getIcon().toString().contains("Black")) {
                            return;
                        }
                        // ---------------------------
                        handleBishopMove(i, j, -1, -1, true, new ImageIcon("src/resources/WhiteBishop.png"));
                        // ---------------------------
                        handleBishopMove(i, j, -1, +1, true, new ImageIcon("src/resources/WhiteBishop.png"));
                        // ---------------------------
                        handleBishopMove(i, j, +1, +1, true, new ImageIcon("src/resources/WhiteBishop.png"));
                        // ---------------------------
                        handleBishopMove(i, j, +1, -1, true, new ImageIcon("src/resources/WhiteBishop.png"));
                    } else {
                        if (ChessBoard.cells[i][j].isChoosed()
                                && ChessBoard.cells[i][j].getIcon().toString().contains("White")) {
                            return;
                        }
                        // ---------------------------
                        handleBishopMove(i, j, -1, -1, false, new ImageIcon("src/resources/BlackBishop.png"));
                        // ---------------------------
                        handleBishopMove(i, j, -1, +1, false, new ImageIcon("src/resources/BlackBishop.png"));
                        // ---------------------------
                        handleBishopMove(i, j, +1, +1, false, new ImageIcon("src/resources/BlackBishop.png"));
                        // ---------------------------
                        handleBishopMove(i, j, +1, -1, false, new ImageIcon("src/resources/BlackBishop.png"));
                    }
                }
            }
        }
    }

    private static void handleBishopMove( int i, int j, int p, int s, boolean color, ImageIcon piece) {

        // ImageIcon bishopIcon = new ImageIcon("src/resources/"+((color) ? "WhiteBishop.png" : "BlackBishop.png"));
        // ImageIcon queenIcon = new ImageIcon("src/resources/"+((color) ? "WhiteQueen.png" : "BlackQueen.png"));
        // ImageIcon rookIcon = new ImageIcon("src/resources/"+((color) ? "WhiteRook.png" : "BlackRook.png"));

        for (int z = 1; z < 8; z++) {

            
            if ((i + (s * (z)) < 0 || i + (s * (z)) >= 8) || (j + (p * (z)) < 0 || j + (p * (z)) >= 8)) {
                break;
            }
            // System.out.println((i + (s * (z))) + " " + (j + (p * (z))));

            if (ChessBoard.cells[i + (s * (z))][j + (p * (z))].getIcon() == null) {
                ChessBoard.cells[i + (s * (z))][j + (p * (z))].setIcon(piece);
                ChessBoard.cells[i][j].setIcon(null);
                if(isUnderAttack(color) == 1) {
                    ChessBoard.cells[i + (s * (z))][j + (p * (z))].setIcon(null);
                    ChessBoard.cells[i][j].setIcon(piece);
                    ChessBoard.cells[i + (s * (z))][j + (p * (z))].setSubChoosed(false);
                } else {
                    ChessBoard.cells[i + (s * (z))][j + (p * (z))].setIcon(null);
                    ChessBoard.cells[i][j].setIcon(piece);
                    ChessBoard.cells[i + (s * (z))][j + (p * (z))].setSubChoosed(true);
                    if(hintActive()) {
                        ChessBoard.cells[i + (s * (z))][j + (p * (z))].setBackground(ChessBoard.chooseColor(i + (s * (z)), j + (p * (z)), 1));
                    }
                }
                ChessBoard.cells[i + (s * (z))][j + (p * (z))].setKillable(false);
            } else {
                if (ChessBoard.cells[i + (s * (z))][j + (p * (z))].getIcon().toString()
                        .contains((color) ? "Black" : "White")) {
                            ImageIcon pieceReplaced = new ImageIcon(ChessBoard.cells[i + (s * (z))][j + (p * (z))].getIcon().toString());
                            ChessBoard.cells[i + (s * (z))][j + (p * (z))].setIcon(piece);
                            ChessBoard.cells[i][j].setIcon(null);
                            if (isUnderAttack(color) == 1) {
                                ChessBoard.cells[i + (s * (z))][j + (p * (z))].setIcon(pieceReplaced);
                                ChessBoard.cells[i][j].setIcon(piece);
                                ChessBoard.cells[i + (s * (z))][j + (p * (z))].setKillable(false);
                            } else {
                                ChessBoard.cells[i + (s * (z))][j + (p * (z))].setIcon(pieceReplaced);
                                ChessBoard.cells[i][j].setIcon(piece);
                                if(hintActive()) {
                                    ChessBoard.cells[i + (s * (z))][j + (p * (z))].setBackground(ChessBoard.chooseColor(i + (s * (z)), j + (p * (z)), 2));
                                }
                                ChessBoard.cells[i + (s * (z))][j + (p * (z))].setKillable(true);
                            }
                            // ChessBoard.cells[i + (s * (z))][j + (p * (z))].setKillable(true);
                    break;
                } else {
                    break;
                }
            }
        }
    }

    private static void rookMove(boolean color) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (ChessBoard.cells[i][j].isChoosed()) {
                    if (color) {
                        if (ChessBoard.cells[i][j].isChoosed()
                                && ChessBoard.cells[i][j].getIcon().toString().contains("Black")) {
                            return;
                        }
                        // ---------------------------
                        handleRookMove(i, j, -1, 0, true);
                        // ---------------------------
                        handleRookMove(i, j, 0, -1, true);
                        // ---------------------------
                        handleRookMove(i, j, +1, 0, true);
                        // ---------------------------
                        handleRookMove(i, j, 0, +1, true);
                        // ---------------------------
                        if(!wKingMoved && isUnderAttack(color) != 1) {
                            rookCastling(true, i, j);
                        }
                    } else {
                        if (ChessBoard.cells[i][j].isChoosed()
                                && ChessBoard.cells[i][j].getIcon().toString().contains("White")) {
                            return;
                        }
                        // ---------------------------
                        handleRookMove(i, j, -1, 0, false);
                        // ---------------------------
                        handleRookMove(i, j, 0, -1, false);
                        // ---------------------------
                        handleRookMove(i, j, +1, 0, false);
                        // ---------------------------
                        handleRookMove(i, j, 0, +1, false);
                        // ---------------------------
                        if(!bKingMoved && isUnderAttack(color) != 1) {
                            rookCastling(false, i, j);
                        }
                    }
                }
            }
        }
    }

    private static void handleRookMove( int i, int j, int p, int s, boolean color) {
        handleBishopMove(i, j, p, s, color, new ImageIcon("src/resources/"+((color) ? "WhiteTower.png":"BlackTower.png")));
    }

    private static void queenMove(boolean color) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (ChessBoard.cells[i][j].isChoosed()) {
                    if (color) {
                        if (ChessBoard.cells[i][j].isChoosed()
                                && ChessBoard.cells[i][j].getIcon().toString().contains("Black")) {
                            return;
                        }
                        // ---------------------------
                        handleQueenMove(i, j, -1, 0, true);
                        // ---------------------------
                        handleQueenMove(i, j, 0, -1, true);
                        // ---------------------------
                        handleQueenMove(i, j, +1, 0, true);
                        // ---------------------------
                        handleQueenMove(i, j, 0, +1, true);
                        // ---------------------------
                        handleQueenMove(i, j, -1, -1, true);
                        // ---------------------------
                        handleQueenMove(i, j, -1, +1, true);
                        // ---------------------------
                        handleQueenMove(i, j, +1, -1, true);
                        // ---------------------------
                        handleQueenMove(i, j, +1, +1, true);
                    } else {
                        if (ChessBoard.cells[i][j].isChoosed()
                                && ChessBoard.cells[i][j].getIcon().toString().contains("White")) {
                            return;
                        }
                        // ---------------------------
                        handleQueenMove(i, j, -1, 0, false);
                        // ---------------------------
                        handleQueenMove(i, j, 0, -1, false);
                        // ---------------------------
                        handleQueenMove(i, j, +1, 0, false);
                        // ---------------------------
                        handleQueenMove(i, j, 0, +1, false);
                        // ---------------------------
                        handleQueenMove(i, j, -1, -1, false);
                        // ---------------------------
                        handleQueenMove(i, j, -1, +1, false);
                        // ---------------------------
                        handleQueenMove(i, j, +1, -1, false);
                        // ---------------------------
                        handleQueenMove(i, j, +1, +1, false);
                    }
                }
            }
        }
    }

    private static void handleQueenMove( int i, int j, int p, int s, boolean color) {
        handleBishopMove(i, j, p, s, color, new ImageIcon("src/resources/"+((color)? "WhiteQueen.png":"BlackQueen.png")));
    }

    private static void rookCastling(boolean isKingSide, int i, int j) {
        int row = isKingSide ? 7 : 0;
        int kingStartCol = 4;
        int tinyRookStartCol = 7, bigRookStartCol = 0;
        int tinyKingEndCol = 6, bigKingEndCol = 2;
        int tinyRookEndCol = 5, bigRookEndCol = 3;

        if(isKingSide? i==7 : i==0) {
            if(bigCastling && j==0) {
                    // Sposta il re
                    ChessBoard.cells[row][kingStartCol].setIcon(null);
                    ChessBoard.cells[row][bigKingEndCol].setIcon((isKingSide)? Wking:Bking);
                    
                    // Sposta la torre
                    ChessBoard.cells[row][bigRookStartCol].setIcon(null);
                    ChessBoard.cells[row][bigRookEndCol].setIcon((isKingSide)? Wrook:Brook);
    
                    ChessBoard.turnCount++;
                    ChessBoard.setAllCellBackground();
                    ChessBoard.setFalseCellsChoosed();
                    // bigCastling = false;
                    return;
                }
                if(tinyCastling && j==7) {
                    // Sposta il re
                    ChessBoard.cells[row][kingStartCol].setIcon(null);
                    ChessBoard.cells[row][tinyKingEndCol].setIcon((isKingSide)? Wking:Bking);
                    
                    // Sposta la torre
                    ChessBoard.cells[row][tinyRookStartCol].setIcon(null);
                    ChessBoard.cells[row][tinyRookEndCol].setIcon((isKingSide)? Wrook:Brook);
    
                    ChessBoard.turnCount++;
                    ChessBoard.setAllCellBackground();
                    ChessBoard.setFalseCellsChoosed();
                    // tinyCastling = false;
                    return;
                }
        }
        
    }

    private static void kingCastling( boolean isKingSide, int i) {
        boolean tinyIsUnderAttack = false, bigIsUnderAttack = false;
        int row = isKingSide ? 7 : 0;
        int kingStartCol = 4;

        if(isKingSide? i==7 : i==0){
            try {
                // if((isKingSide)? !wKingMoved:!bKingMoved) {
                    if(ChessBoard.cells[row][kingStartCol+1].getIcon() == null && ChessBoard.cells[row][kingStartCol+2].getIcon() == null) {
                        ChessBoard.cells[row][kingStartCol].setIcon(null);
                        ChessBoard.cells[row][kingStartCol+1].setIcon((isKingSide)? Wking:Bking);
                        if(isUnderAttack(isKingSide) == 1) {
                            ChessBoard.cells[row][kingStartCol+1].setIcon(null);
                            ChessBoard.cells[row][kingStartCol].setIcon((isKingSide)? Wking:Bking);
                            tinyIsUnderAttack = true;
                        } else {
                            ChessBoard.cells[row][kingStartCol+1].setIcon(null);
                            ChessBoard.cells[row][kingStartCol+2].setIcon((isKingSide)? Wking:Bking);
                            if(isUnderAttack(isKingSide) == 1) {
                                ChessBoard.cells[row][kingStartCol+2].setIcon(null);
                                ChessBoard.cells[row][kingStartCol+1].setIcon((isKingSide)? Wking:Bking);
                                tinyIsUnderAttack = true;
                            } else {
                                ChessBoard.cells[row][kingStartCol+2].setIcon(null);
                                ChessBoard.cells[row][kingStartCol].setIcon((isKingSide)? Wking:Bking);
                            }
                        }
                        if(tinyIsUnderAttack == false) {
                            if(hintActive()) {
                                ChessBoard.cells[row][kingStartCol+3].setBackground(ChessBoard.chooseColor(row, kingStartCol+3, 1));
                            }
                            ChessBoard.cells[row][kingStartCol+3].setSubChoosed(true);
                            tinyCastling = true;
                        } else {
                            tinyIsUnderAttack = false;
                            tinyCastling = false;
                        }
                    } else {
                        tinyCastling = false;
                    }
                // }
            } catch (Exception e) {
            }
            try {
                if(ChessBoard.cells[row][kingStartCol-1].getIcon() == null && ChessBoard.cells[row][kingStartCol-2].getIcon() == null && ChessBoard.cells[row][kingStartCol-3].getIcon() == null) {
                    ChessBoard.cells[row][kingStartCol].setIcon(null);
                        ChessBoard.cells[row][kingStartCol-1].setIcon((isKingSide)? Wking:Bking);
                        if(isUnderAttack(isKingSide) == 1) {
                            ChessBoard.cells[row][kingStartCol-1].setIcon(null);
                            ChessBoard.cells[row][kingStartCol].setIcon((isKingSide)? Wking:Bking);
                            bigIsUnderAttack = true;
                        } else {
                            ChessBoard.cells[row][kingStartCol-1].setIcon(null);
                            ChessBoard.cells[row][kingStartCol-2].setIcon((isKingSide)? Wking:Bking);
                            if(isUnderAttack(isKingSide) == 1) {
                                ChessBoard.cells[row][kingStartCol-2].setIcon(null);
                                ChessBoard.cells[row][kingStartCol].setIcon((isKingSide)? Wking:Bking);
                                bigIsUnderAttack = true;
                            } else {
                                ChessBoard.cells[row][kingStartCol-2].setIcon(null);
                                ChessBoard.cells[row][kingStartCol-3].setIcon((isKingSide)? Wking:Bking);
                                if(isUnderAttack(isKingSide) == 1) {
                                    ChessBoard.cells[row][kingStartCol-3].setIcon(null);
                                    ChessBoard.cells[row][kingStartCol].setIcon((isKingSide)? Wking:Bking);
                                    bigIsUnderAttack = true;
                                } else {
                                    ChessBoard.cells[row][kingStartCol-3].setIcon(null);
                                    ChessBoard.cells[row][kingStartCol].setIcon((isKingSide)? Wking:Bking);
                                }
                            }
                        }
                    if(bigIsUnderAttack == false) {
                        if(hintActive()) {
                            ChessBoard.cells[row][kingStartCol-4].setBackground(ChessBoard.chooseColor(row, kingStartCol-4, 1));
                        }
                        ChessBoard.cells[row][kingStartCol-4].setSubChoosed(true);
                        bigCastling = true;
                    } else {
                        bigIsUnderAttack = false;
                        bigCastling = false;
                    }
                } else {
                    bigCastling = false;
                }
            } catch (Exception e) {
            }
        }
    }

    private static void kingMove(boolean color) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (ChessBoard.cells[i][j].isChoosed()) {
                    if (color) {
                        if (ChessBoard.cells[i][j].isChoosed()
                                && ChessBoard.cells[i][j].getIcon().toString().contains("Black")) {
                            return;
                        }
                        // ---------------------------
                        handleKingMove(i, j, -1, 0, true);
                        // ---------------------------
                        handleKingMove(i, j, 0, -1, true);
                        // ---------------------------
                        handleKingMove(i, j, +1, 0, true);
                        // ---------------------------
                        handleKingMove(i, j, 0, +1, true);
                        // ---------------------------
                        handleKingMove(i, j, -1, -1, true);
                        // ---------------------------
                        handleKingMove(i, j, -1, +1, true);
                        // ---------------------------
                        handleKingMove(i, j, +1, -1, true);
                        // ---------------------------
                        handleKingMove(i, j, +1, +1, true);
                        // ---------------------------
                        if(!wKingMoved && isUnderAttack(color) != 1) {
                            kingCastling(true, i);
                        }
                    } else {
                        if (ChessBoard.cells[i][j].isChoosed()
                                && ChessBoard.cells[i][j].getIcon().toString().contains("White")) {
                            return;
                        }
                        // ---------------------------
                        handleKingMove(i, j, -1, 0, false);
                        // ---------------------------
                        handleKingMove(i, j, 0, -1, false);
                        // ---------------------------
                        handleKingMove(i, j, +1, 0, false);
                        // ---------------------------
                        handleKingMove(i, j, 0, +1, false);
                        // ---------------------------
                        handleKingMove(i, j, -1, -1, false);
                        // ---------------------------
                        handleKingMove(i, j, -1, +1, false);
                        // ---------------------------
                        handleKingMove(i, j, +1, -1, false);
                        // ---------------------------
                        handleKingMove(i, j, +1, +1, false);
                        // ---------------------------
                        if(!bKingMoved && isUnderAttack(color) != 1) {
                            kingCastling(false, i);
                        }
                    }
                }

            }
        }
    }

    private static void handleKingMove( int i, int j, int p, int s, boolean color) {
        handleKnightMove(i, j, p, s, color, true);
    }


    protected static boolean isCheckMate(boolean isWhite) {
        ChessBoard.setFalseCellsChoosed();
        boolean isCheckMate = true;
        /*
         * Controlla se il giocatore passato come argomento è in scacco matto, e restituisce true in caso affermativo,
         * false altrimenti.
         */
        
        // Scorriamo attraverso tutte le celle della scacchiera che contengono un pezzo del colore corretto
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                if(ChessBoard.cells[i][j].getIcon() != null && 
                   ChessBoard.cells[i][j].getIcon().toString().contains((isWhite) ? "White" : "Black")) {
                    
                    ChessBoard.cells[i][j].setChoosed(true);
                    showMove(i, j, isWhite);
                    
                    if(ChessBoard.isCellsChoosed() || ChessBoard.isCellsKillable()) isCheckMate = false;
                    
                    ChessBoard.setFalseCellsChoosed();
                    ChessBoard.setFalseCellsKilled();
                    ChessBoard.setAllCellBackground();
                }
            }
        }
        
        // Se non è stata trovata una mossa legale o una mossa per catturare,
        // allora il giocatore corrente è in scacco matto.
        return isCheckMate;
    }

    

    protected static int isUnderAttack(boolean color) {
        int kingRow = -1, kingCol = -1;
        int[] checkAllies = new int[18];
        int possibleMove = 0;
    
        // Trova la posizione del re
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                try {
                    if (ChessBoard.cells[i][j].getIcon().toString().contains((color) ? "WhiteKin" : "BlackKin")) {
                        kingRow = i;
                        kingCol = j;
                        break;
                    }
                } catch (NullPointerException e) {}
            }
        }
    
        // Controlla le diagonali
        for (int i = 1; i < 8; i++) {
            // Diagonale superiore sinistra
            if(checkAllies[0] == 0) {
                if (kingRow - i >= 0 && kingCol - i >= 0) {
                    checkAllies[0] = isDiagonalPiece(kingRow - i, kingCol - i, color);
                }
            }
    
            // Diagonale superiore destra
            if(checkAllies[1] == 0) {
                if (kingRow - i >= 0 && kingCol + i < 8) {
                    checkAllies[1] = isDiagonalPiece(kingRow - i, kingCol + i, color);
                }
            }
    
            // Diagonale inferiore sinistra
            if(checkAllies[2] == 0) {
                if (kingRow + i < 8 && kingCol - i >= 0) {
                    checkAllies[2] = isDiagonalPiece(kingRow + i, kingCol - i, color);
                }
            }

            // Diagonale inferiore destra
            if(checkAllies[3] == 0) {
                if (kingRow + i < 8 && kingCol + i < 8) {
                    checkAllies[3] = isDiagonalPiece(kingRow + i, kingCol + i, color);
                }
            }

            // Laterale sinistra
            if(checkAllies[4] == 0) {
                if(kingRow >= 0 && kingRow < 8 && kingCol - i >= 0) {
                    checkAllies[4] = isLateralPiece(kingRow, kingCol - i, color);
                }
            }

            // Laterale destra
            if(checkAllies[5] == 0) {
                if(kingRow >= 0 && kingRow < 8 && kingCol + i < 8) {
                    checkAllies[5] = isLateralPiece(kingRow, kingCol + i, color);
                }
            }

            // Laterale sopra
            if(checkAllies[6] == 0) {
                if(kingRow - i >= 0 && kingCol < 8 && kingCol >= 0) {
                    checkAllies[6] = isLateralPiece(kingRow - i, kingCol, color);
                }
            }

            // Laterale sotto
            if(checkAllies[7] == 0) {
                if(kingRow + i < 8 && kingCol < 8 && kingCol >= 0) {
                    checkAllies[7] = isLateralPiece(kingRow + i, kingCol, color);
                }
            }
        }

        //Controllo le mosse ad L
        if(checkAllies[8] == 0) {
            if(kingRow - 2 >= 0 && kingCol - 1 >= 0) {
                checkAllies[8] = isLPiece(kingRow - 2, kingCol - 1, color);
            }
        }

        if(checkAllies[9] == 0) {
            if(kingRow - 2 >= 0 && kingCol + 1 < 8) {
                checkAllies[9] = isLPiece(kingRow - 2, kingCol + 1, color);
            }
        }

        if(checkAllies[10] == 0) {
            if(kingRow - 1 >= 0 && kingCol + 2 < 8) {
                checkAllies[10] = isLPiece(kingRow - 1, kingCol + 2, color);
            }
        }

        if(checkAllies[11] == 0) {
            if(kingRow + 1 < 8 && kingCol + 2 < 8) {
                checkAllies[11] = isLPiece(kingRow + 1, kingCol + 2, color);
            }
        }

        if(checkAllies[12] == 0) {
            if(kingRow + 2 < 8 && kingCol + 1 < 8) {
                checkAllies[12] = isLPiece(kingRow + 2, kingCol + 1, color);
            }
        }

        if(checkAllies[13] == 0) {
            if(kingRow + 2 < 8 && kingCol - 1 >= 0) {
                checkAllies[13] = isLPiece(kingRow + 2, kingCol - 1, color);
            }
        }

        if(checkAllies[14] == 0) {
            if(kingRow + 1 < 8 && kingCol - 2 >= 0) {
                checkAllies[14] = isLPiece(kingRow + 1, kingCol - 2, color);
            }
        }

        if(checkAllies[15] == 0) {
            if(kingRow - 1 >= 0 && kingCol - 2 >= 0) {
                checkAllies[15] = isLPiece(kingRow - 1, kingCol - 2, color);
            }
        }

        try{
            if(color == true) {
                if(checkAllies[16] == 0) {
                    if(kingRow -1 >= 0 && kingCol - 1 >= 0) {
                        checkAllies[16] = isPawnPiece(kingRow - 1, kingCol - 1, color);
                    }
                }

                if(checkAllies[17] == 0) {
                    if(kingRow -1 >= 0 && kingCol + 1 < 8) {
                        checkAllies[17] = isPawnPiece(kingRow - 1, kingCol + 1, color);
                    }
                }
            } 
            if(color == false) {
                if(checkAllies[16] == 0) {
                    if(kingRow + 1 < 8 && kingCol + 1 < 8) {
                        checkAllies[16] = isPawnPiece(kingRow + 1, kingCol + 1, color);
                    }
                }

                if(checkAllies[17] == 0) {
                    if(kingRow + 1 < 8 && kingCol - 1 >= 0) {
                        checkAllies[17] = isPawnPiece(kingRow + 1, kingCol - 1, color);
                    }
                }
            }
        } catch(IllegalArgumentException e) {}
        
        for (int i = 0; i < 18; i++) {
            if (checkAllies[i] == 1) {
                possibleMove++;
            }
        }
        if(possibleMove == 0) {
            return 0; //non sotto attacco
        } else if(possibleMove > 0) {
            return 1; //sotto attacco
        } else if(possibleMove > 0 && possibleMove < 18) {
            return 2; //sotto scaccomatto
        }
        return 99;
    }
    
    // Metodo di supporto per controllare se c'è un pezzo avversario in una determinata posizione diagonale
    private static int isDiagonalPiece(int row, int col, boolean color) {
        if (ChessBoard.cells[row][col].getIcon() != null) {
            String pieceColor = (color) ? "Black" : "White";
            String pieceColorProtect = (color)? "White" : "Black";

            if (ChessBoard.cells[row][col].getIcon().toString().contains(pieceColorProtect)) {
                return 2; // C'è un tuo pezzo sulla diagonale
            }
            if (ChessBoard.cells[row][col].getIcon().toString().contains(pieceColor+"Quee") ||
                ChessBoard.cells[row][col].getIcon().toString().contains(pieceColor+"Bisho")) {
                return 1; // C'è un pezzo avversario sulla diagonale
            }
        }
        return 0;
    }
    
    private static int isLateralPiece(int row, int col, boolean color) {
        if (ChessBoard.cells[row][col].getIcon() != null) {
            String pieceColor = (color) ? "Black" : "White";
            String pieceColorProtect = (color)? "White" : "Black";

            if (ChessBoard.cells[row][col].getIcon().toString().contains(pieceColorProtect)) {
                return 2; // C'è un tuo pezzo sulla laterale
            }
            if (ChessBoard.cells[row][col].getIcon().toString().contains(pieceColor+"Quee") ||
                ChessBoard.cells[row][col].getIcon().toString().contains(pieceColor+"Towe")) {
                return 1; // C'è un pezzo avversario sulla laterale
            }
        }
        return 0;
    }

    private static int isLPiece(int row, int col, boolean color) {
        if (ChessBoard.cells[row][col].getIcon() != null) {
            String pieceColor = (color) ? "Black" : "White";
            String pieceColorProtect = (color)? "White" : "Black";

            if (ChessBoard.cells[row][col].getIcon().toString().contains(pieceColorProtect)) {
                return 2; // C'è un tuo pezzo sulla L
            }
            if (ChessBoard.cells[row][col].getIcon().toString().contains(pieceColor+"Knigh")) {
                return 1; // C'è un pezzo avversario sulla L
            }
        }
        return 0;
    }

    private static int isPawnPiece(int row, int col, boolean color) {
        if (ChessBoard.cells[row][col].getIcon() != null) {
            String pieceColor = (color) ? "Black" : "White";
            String pieceColorProtect = (color)? "White" : "Black";

            if (ChessBoard.cells[row][col].getIcon().toString().contains(pieceColorProtect)) {
                return 2; // C'è un tuo pezzo sul quadrato
            }
            if (ChessBoard.cells[row][col].getIcon().toString().contains(pieceColor+"Paw")) {
                return 1; // C'è un pezzo avversario sul quadrato
            }
        }
        return 0;
    }
}