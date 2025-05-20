import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Util {

    public static boolean isSolved(Board board) {
        int ex = board.exitRow;
        int ey = board.exitCol;
        char[][] grid = board.grid;

        int rows = board.rows;
        int cols = board.cols;

        // CASE: Horizontal goal (K sejajar baris dengan P)
        if (ex >= 0 && ex < rows) {
            for (int j = 0; j < cols; j++) {
                if (grid[ex][j] == 'P') {
                    // Cari ujung kanan P
                    int end = j;
                    while (end + 1 < cols && grid[ex][end + 1] == 'P') end++;

                    // Pastikan semua cell antara P dan K kosong
                    for (int k = end + 1; k < ey; k++) {
                        if (k < cols && grid[ex][k] != '.') return false;
                    }

                    // Akhirnya, cek apakah pintu keluar ada tepat di ey
                    if (ey == end + 1 || ey > end) {
                        return true;
                    }
                }
            }
        }

        // CASE: Vertical goal (K sejajar kolom dengan P)
        if (ey >= 0 && ey < cols) {
            for (int i = 0; i < rows; i++) {
                if (grid[i][ey] == 'P') {
                    // Cari ujung bawah P
                    int end = i;
                    while (end + 1 < rows && grid[end + 1][ey] == 'P') end++;

                    for (int k = end + 1; k < ex; k++) {
                        if (k < rows && grid[k][ey] != '.') return false;
                    }

                    if (ex == end + 1 || ex > end) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public static String hash(Board board) {
        StringBuilder sb = new StringBuilder();
        for (char[] row : board.grid)
            sb.append(row);
        return sb.toString();
    }

    public static void printSolution(State state) {
        LinkedList<State> path = new LinkedList<>();
        while (state != null) {
            path.addFirst(state);
            state = state.parent;
        }

        int moveNum = 0;
        for (State s : path) {
            if (moveNum == 0) {
                System.out.println("Papan Awal:");
            } else {
                System.out.printf("Gerakan %d: %s\n", moveNum, s.move);
            }

            String highlight = (s.move != null) ? s.move.split("-")[0] : "";
            s.board.printBoard(highlight);
            moveNum++;
        }
    }

    public static int countBlockingCars(Board board) {
        int rows = board.rows;
        int cols = board.cols;
        char[][] grid = board.grid;

        for (int i = 0; i < rows; i++) {
            int pStart = -1, pEnd = -1;
            for (int j = 0; j < cols; j++) {
                if (grid[i][j] == 'P') {
                    if (pStart == -1) pStart = j;
                    pEnd = j;
                }
            }

            if (pStart != -1 && pEnd != -1) {
                int count = 0;
                for (int j = pEnd + 1; j < cols; j++) {
                    if (grid[i][j] != '.' && grid[i][j] != 'K') {
                        count++;
                    }
                }
                return count;
            }
        }

        return Integer.MAX_VALUE; // fallback jika tidak ditemukan P
    }

    public static int distanceToExit(Board board) {
        int rows = board.rows;
        int cols = board.cols;
        char[][] grid = board.grid;

        for (int i = 0; i < rows; i++) {
            int pEnd = -1;
            int kIndex = -1;

            for (int j = 0; j < cols; j++) {
                if (grid[i][j] == 'P') {
                    pEnd = j;
                }
                if (grid[i][j] == 'K') {
                    kIndex = j;
                }
            }

            if (pEnd != -1 && kIndex != -1 && kIndex > pEnd) {
                return kIndex - pEnd - 1;
            }
        }

        return Integer.MAX_VALUE; // fallback
    }


    public static Board loadBoardFromFile(String path) {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            
            /* Edge case handling */
            // Baca baris pertama: ukuran board
            String checkSizeLine = br.readLine();
            if (checkSizeLine == null)
                throw new IllegalArgumentException("Format file tidak valid! File kosong.");
            
            String[] size = checkSizeLine.trim().split("\\s+");
            if (size.length != 2)
                throw new IllegalArgumentException("Format file tidak valid! Baris pertama harus memiliki 2 nilai: rows dan cols. Diberikan: " + Arrays.toString(size));
            
            int rows, cols;
            try {
                rows = Integer.parseInt(size[0]);
                cols = Integer.parseInt(size[1]);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Format file tidak valid! Baris pertama harus berupa angka, sedangkan diberikan: " + Arrays.toString(size));
            }

            // Baca baris kedua: jumlah kendaraan
            String checkPiecesLine = br.readLine();
            if (checkPiecesLine == null)
                throw new IllegalArgumentException("Format file tidak valid. Baris kedua (jumlah kendaraan) tidak ditemukan.");
            
            int pieceCount;
            try {
                pieceCount = Integer.parseInt(checkPiecesLine.trim());
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Format file tidak valid. Baris kedua (jumlah kendaraan) harus berupa angka, sedangkan diberikan: " + checkPiecesLine);
            }

            /* Baca Grid */
            Board board = new Board(rows, cols);
            board.exitRow = -1;
            board.exitCol = -1;

            List<String> gridLines = new ArrayList<>();
            String line;
            while ((line = br.readLine()) != null) {
                line = line.stripTrailing();
                if (!line.isEmpty()) gridLines.add(line);
            }

            if (gridLines.size() < rows)
                throw new IllegalArgumentException("Format file tidak valid! Expected " + rows + " baris papan, diberikan: " + gridLines.size());

            /* Cek 'K' muncul di atas/bawah grid */
            if (gridLines.size() > rows){
                // K di atas grid
                if (gridLines.get(0).contains("K")){
                    board.exitRow = -1;
                    board.exitCol = gridLines.get(0).indexOf('K');
                    gridLines.remove(0);
                }

                // K di bawah grid
                if (gridLines.get(gridLines.size() - 1).contains("K")){
                    // Handle duplicate 'K'
                    if (board.exitCol != -1 || board.exitRow != -1)
                        throw new IllegalArgumentException("Format file tidak valid! 'K' duplikat ditemukan.");
                    board.exitRow = rows;
                    board.exitCol = gridLines.get(gridLines.size() - 1).indexOf('K');
                    gridLines.remove(gridLines.size() - 1);
                }
            }

            for (int i = 0; i < rows; i++){
                String rowLine = gridLines.get(i);

                // K di kiri
                if (rowLine.length() > 0 && rowLine.charAt(0) == 'K'){
                    // Handle duplicate 'K'
                    if (board.exitCol != -1 || board.exitRow != -1)
                        throw new IllegalArgumentException("Format file tidak valid! 'K' duplikat ditemukan.");
                    board.exitRow = i;
                    board.exitCol = -1;
                    rowLine = rowLine.substring(1);
                }

                // K di kanan
                if (rowLine.length() > cols && rowLine.charAt(cols) == 'K'){
                    // Handle duplicate 'K'
                    if (board.exitCol != -1 || board.exitRow != -1)
                        throw new IllegalArgumentException("Format file tidak valid! 'K' duplikat ditemukan.");
                    board.exitRow = i;
                    board.exitCol = cols;
                }

                // Isi grid
                for (int j = 0; j < cols; j++) {
                    if (j < rowLine.length()) {
                        char ch = rowLine.charAt(j);
                        
                        // jika K di dalam board (sudah dicek di pinggir dan atas bawah)
                        if (ch == 'K'){
                            throw new IllegalArgumentException("Format file tidak valid! 'K' ditemukan dalam papan, sedangkan hanya boleh di dinding papan.");
                        } else {
                            board.grid[i][j] = ch;
                        }
                    } else {
                        board.grid[i][j] = '.';
                    }
                }
            }

            if (board.exitRow == -1 && board.exitCol == -1)
                    throw new IllegalArgumentException("Format file tidak valid! 'K' tidak ditemukan dalam board.");

            System.out.println("Board berhasil dimuat!");
            // board.printBoard("");

            System.out.println("Posisi K: (" + board.exitRow + ", " + board.exitCol + ")");
            System.out.println("Jumlah kendaraan: " + pieceCount);
            System.out.println("Ukuran papan: " + rows + " x " + cols);
            System.out.println();

            return board;
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());    
        } catch (IOException e) {
            System.err.println("Gagal membaca file: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Format file salah: " + e.getMessage());
        }
        return null;
    }

    public static List<State> getSuccessors(State current, String algorithm, int heuristicId) {
        List<State> successors = new ArrayList<>();
        Board board = current.board;

        Map<Character, Piece> pieces = detectPieces(board);

        for (Piece piece : pieces.values()) {
            // Geser maju
            for (int d = 1; canMove(piece, board, d); d++) {
                Board newBoard = movePiece(piece, board, d);
                int cost = current.cost + d;
                int h = Heuristic.evaluate(newBoard, heuristicId);
                String move = piece.label + "-" + (piece.horizontal ? "kanan" : "bawah");
                successors.add(new State(newBoard, cost, move, current, h));
            }

            // Geser mundur
            for (int d = -1; canMove(piece, board, d); d--) {
                Board newBoard = movePiece(piece, board, d);
                int cost = current.cost + Math.abs(d);
                int h = Heuristic.evaluate(newBoard, heuristicId);
                String move = piece.label + "-" + (piece.horizontal ? "kiri" : "atas");
                successors.add(new State(newBoard, cost, move, current, h));
            }
        }

        return successors;
    }

    private static Map<Character, Piece> detectPieces(Board board) {
        Map<Character, Piece> pieces = new HashMap<>();

        for (int i = 0; i < board.rows; i++) {
            for (int j = 0; j < board.cols; j++) {
                char c = board.grid[i][j];
                if (c != '.' && c != 'K' && !pieces.containsKey(c)) {
                    // Cek horizontal
                    if (j + 1 < board.cols && board.grid[i][j + 1] == c) {
                        int size = 1;
                        while (j + size < board.cols && board.grid[i][j + size] == c)
                            size++;
                        pieces.put(c, new Piece(c, i, j, size, true));
                    }
                    // Cek vertikal
                    else if (i + 1 < board.rows && board.grid[i + 1][j] == c) {
                        int size = 1;
                        while (i + size < board.rows && board.grid[i + size][j] == c)
                            size++;
                        pieces.put(c, new Piece(c, i, j, size, false));
                    }
                }
            }
        }

        return pieces;
    }

    private static boolean canMove(Piece piece, Board board, int delta) {
        int r = piece.row, c = piece.col;
        if (piece.horizontal) {
            if (delta > 0) {
                int end = c + piece.size - 1;
                if (end + delta >= board.cols) return false;
                for (int i = 1; i <= delta; i++) {
                    if (board.grid[r][end + i] != '.') return false;
                }
            } else {
                if (c + delta < 0) return false;
                for (int i = -1; i >= delta; i--) {
                    if (board.grid[r][c + i] != '.') return false;
                }
            }
        } else {
            if (delta > 0) {
                int end = r + piece.size - 1;
                if (end + delta >= board.rows) return false;
                for (int i = 1; i <= delta; i++) {
                    if (board.grid[end + i][c] != '.') return false;
                }
            } else {
                if (r + delta < 0) return false;
                for (int i = -1; i >= delta; i--) {
                    if (board.grid[r + i][c] != '.') return false;
                }
            }
        }
        return true;
    }

    private static Board movePiece(Piece piece, Board board, int delta) {
        Board newBoard = board.clone();
        int r = piece.row, c = piece.col;
        char ch = piece.label;

        // Kosongkan posisi lama
        if (piece.horizontal) {
            for (int i = 0; i < piece.size; i++)
                newBoard.grid[r][c + i] = '.';
            int newCol = c + delta;
            for (int i = 0; i < piece.size; i++)
                newBoard.grid[r][newCol + i] = ch;
        } else {
            for (int i = 0; i < piece.size; i++)
                newBoard.grid[r + i][c] = '.';
            int newRow = r + delta;
            for (int i = 0; i < piece.size; i++)
                newBoard.grid[newRow + i][c] = ch;
        }

        return newBoard;
    }

}
