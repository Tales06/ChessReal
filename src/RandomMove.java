import java.util.Random;

import javax.swing.ImageIcon;

public class RandomMove {

    private static Random random = new Random();

    protected static String randomMove() {
        int x, y;
        
        int move  = random.nextInt(ChessBoard.pieceAvailable.size());
        x = ChessBoard.pieceAvailable.get(move).x;
        y = ChessBoard.pieceAvailable.get(move).y;

        ChessBoard.cells[x][y].setChoosed(true);

        return x+""+y;
    }

    protected static void subRandomMove(int i, int j) {
        int x, y, move;
        ImageIcon piece = new ImageIcon(ChessBoard.cells[i][j].getIcon().toString());
        
        move = random.nextInt(0, ChessBoard.subChoosedMove.size());
        x = ChessBoard.subChoosedMove.get(move).x;
        y = ChessBoard.subChoosedMove.get(move).y;
        
        try {
            // System.out.println(x + " " + y);
            ChessBoard.cells[i][j].setIcon(null);
            ChessBoard.cells[x][y].setIcon(piece);
        } catch (NullPointerException e) {
            System.out.println("NullPointerException in randomMove()");
        }
        return;
    }

    protected static void killRandomMove(int i, int j) {
        int x, y, move;
        ImageIcon piece = new ImageIcon(ChessBoard.cells[i][j].getIcon().toString());
        
        move = random.nextInt(0, ChessBoard.killableMove.size());
        x = ChessBoard.killableMove.get(move).x;
        y = ChessBoard.killableMove.get(move).y;
        
        try {
            // System.out.println(x + " " + y);
            ChessBoard.cells[i][j].setIcon(null);
            ChessBoard.cells[x][y].setIcon(piece);
        } catch (NullPointerException e) {
            System.out.println("NullPointerException in randomMove()");
        }
        return;
    }

}

