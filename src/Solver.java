import java.util.*;

public class Solver {
    Board initial;
    String algorithm;
    int heuristicId;

    public Solver(Board board, String algorithm, int heuristicId) {
        this.initial = board;
        this.algorithm = algorithm;
        this.heuristicId = heuristicId;
    }

    public void solve() {
        long startTime = System.nanoTime();

        PriorityQueue<State> queue = new PriorityQueue<>(Comparator.comparingInt(s -> s.getTotalCost(algorithm)));
        Set<String> visited = new HashSet<>();

        State start = new State(initial, 0, null, null, Heuristic.evaluate(initial, heuristicId));
        queue.add(start);

        int visitedCount = 0;

        while (!queue.isEmpty()) {
            State current = queue.poll();
            visitedCount++;

            if (Util.isSolved(current.board)) {
                long endTime = System.nanoTime();
                Util.printSolution(current);
                System.out.println("Visited nodes: " + visitedCount);
                System.out.printf("Execution time: %.4f ms%n", (endTime - startTime) / 1e6);
                return;
            }

            String hash = Util.hash(current.board);
            if (visited.contains(hash)) continue;
            visited.add(hash);

            List<State> successors = Util.getSuccessors(current, algorithm, heuristicId);
            queue.addAll(successors);
        }
        Util.printSolution(start);
        System.out.println("No solution found.");
    }
}
