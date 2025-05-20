import java.util.Arrays;

public class Board {
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String BLUE = "\u001B[34m";

    public char[][] grid;
    public int rows, cols;
    public int exitRow, exitCol;

    public Board(int r, int c) {
        this.rows = r;
        this.cols = c;
        this.grid = new char[r][c];
    }

    public Board clone() {
        Board copy = new Board(rows, cols);
        for (int i = 0; i < rows; i++)
            copy.grid[i] = Arrays.copyOf(grid[i], cols);
        copy.exitRow = this.exitRow;
        copy.exitCol = this.exitCol;
        return copy;
    }

    public void printBoard(String highlightLabel) {
        // Border atas (dengan kemungkinan exit di atas)
        System.out.print("  +");
        for (int j = 0; j < cols; j++) {
            if (exitRow == -1 && exitCol == j) {
                System.out.print(GREEN + "K" + RESET + "-");
            } else {
                System.out.print("--");
            }
        }
        System.out.println("+");

        for (int i = 0; i < rows; i++) {
            // Border kiri (dengan kemungkinan exit di kiri)
            if (exitCol == -1 && exitRow == i) {
                System.out.print(GREEN + "K" + RESET);
            } else {
                System.out.print("  |");
            }

            // Isi grid
            for (int j = 0; j < cols; j++) {
                char ch = grid[i][j];
                if (ch == 'P') {
                    System.out.print(RED + ch + RESET + " ");
                } else if (("" + ch).equals(highlightLabel)) {
                    System.out.print(BLUE + ch + RESET + " ");
                } else {
                    System.out.print(ch + " ");
                }
            }

            // Border kanan (dengan kemungkinan exit di kanan)
            if (exitCol == cols && exitRow == i) {
                System.out.print(GREEN + "K" + RESET);
            } else {
                System.out.print("|");
            }
            System.out.println();
        }

        // Border bawah (dengan kemungkinan exit di bawah)
        System.out.print("  +");
        for (int j = 0; j < cols; j++) {
            if (exitRow == rows && exitCol == j) {
                System.out.print(GREEN + "K" + RESET + "-");
            } else {
                System.out.print("--");
            }
        }
        System.out.println("+");
        System.out.println();
    }
}
