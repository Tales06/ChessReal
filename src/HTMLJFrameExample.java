import javax.swing.*;
import java.awt.*;

public class HTMLJFrameExample extends JFrame {
    
    public HTMLJFrameExample() {
        setTitle("HTML JFrame Example");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);

        // Creazione di un JEditorPane
        JEditorPane editorPane = new JEditorPane();
        editorPane.setEditable(false);
        
        // Caricamento del file HTML
        try {
            editorPane.setPage("file:///C:\\Users\\lenovo\\OneDrive\\Informatica\\Java\\ChessReal\\src\\prova.html"); // Inserisci il percorso del tuo file HTML
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Aggiungi il JEditorPane al JFrame
        JScrollPane scrollPane = new JScrollPane(editorPane);
        getContentPane().add(scrollPane, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            HTMLJFrameExample frame = new HTMLJFrameExample();
            frame.setVisible(true);
        });
    }
}
