public class Main {
    public static void main(String[] args) {
        String filePath = "test/2.txt";
        String algorithm = "Greedy"; // Bisa "UCS", "Greedy", atau "A*"
        int heuristicId = 1;

        Board board = Util.loadBoardFromFile(filePath);
        if (board == null) {
            System.out.println("Board gagal dimuat. Program berhenti.");
            return;
        }

        Solver solver = new Solver(board, algorithm, heuristicId);
        solver.solve();
    }
}