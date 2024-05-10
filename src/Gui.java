import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class Gui extends JFrame implements ActionListener{

    private JButton resetButton = new JButton("Reset");
    private static JButton hintButton = new JButton("Hide Move");
    private ChessBoard chessBoard = new ChessBoard(1);

    public Gui() {

        resetButton.setSize(50, 50);
        hintButton.addActionListener(this);
        resetButton.addActionListener(this);
        setLayout(new GridLayout(1, 2));
        add(resetButton);
        add(hintButton);
        setSize(400, 400);
        setLocation(1110, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    protected  static String getTextHintButton() {
        return hintButton.getText();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == resetButton) {
            chessBoard.resetBoard();
        }
        if(e.getSource() == hintButton) {
            hintButton.setText(hintButton.getText().equals("Hide Move") ? "Show Move" : "Hide Move");
        }
        
    }
}
