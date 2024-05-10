import javax.swing.JLabel;

public class Cell extends JLabel{

    private int x;
    private int y;
    private boolean isChoosed;
    private boolean subChoosed;
    private boolean killable;
    private String coordinate;

    
    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
        this.isChoosed = false;
        this.subChoosed = false;
        this.killable = false;
    }
    public String getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(String coordinate) {
        this.coordinate = coordinate;
    }
    
    public int _getX() {
        return x;
    }

    public void _setX(int x) {
        this.x = x;
    }

    public int _getY() {
        return y;
    }

    public void _setY(int y) {
        this.y = y;
    }

    public boolean isChoosed() {
        return isChoosed;
    }

    public void setChoosed(boolean isChoosed) {
        this.isChoosed = isChoosed;
    }

    public boolean isSubChoosed() {
        return subChoosed;
    }

    public void setSubChoosed(boolean subChoosed) {
        this.subChoosed = subChoosed;
    }

    public boolean isKillable() {
        return killable;
    }

    public void setKillable(boolean killable) {
        this.killable = killable;
    }

    protected boolean isWhite() {
        return getIcon().toString().contains("White");
    }
}