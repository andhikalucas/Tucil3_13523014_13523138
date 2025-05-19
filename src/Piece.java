public class Piece {
    public char label;
    public int row, col, size;
    public boolean horizontal;

    public Piece(char label, int row, int col, int size, boolean horizontal) {
        this.label = label;
        this.row = row;
        this.col = col;
        this.size = size;
        this.horizontal = horizontal;
    }
}
