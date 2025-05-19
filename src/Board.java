import java.util.Arrays;

public class Board {
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String BLUE = "\u001B[34m";

    public char[][] grid;
    public int rows, cols;
    public int exitRow = -1, exitCol = -1;

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
        // Jika exit di atas grid, tampilkan baris K
        if (exitRow == -1) {
            for (int j = 0; j < cols; j++) {
                System.out.print(j == exitCol ? GREEN + 'K' + RESET : " ");
            }
            System.out.println();
        }

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                char ch = grid[i][j];

                // Tampilkan K jika berada di dalam grid
                if (i == exitRow && j == exitCol) {
                    System.out.print(GREEN + 'K' + RESET);
                } else if (ch == 'P') {
                    System.out.print(RED + ch + RESET);
                } else if (("" + ch).equals(highlightLabel)) {
                    System.out.print(BLUE + ch + RESET);
                } else {
                    System.out.print(ch);
                }
            }
            System.out.println();
        }
        System.out.println();
    }

}
