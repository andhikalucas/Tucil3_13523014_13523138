
public class State implements Comparable<State> {
    public Board board;
    public int cost;
    public String move;
    public State parent;
    public int heuristic;

    public State(Board b, int cost, String move, State parent, int h) {
        this.board = b;
        this.cost = cost;
        this.move = move;
        this.parent = parent;
        this.heuristic = h;
    }

    public int getTotalCost(String algo) {
        if (algo.equals("UCS")) return cost;
        if (algo.equals("Greedy")) return heuristic;
        return cost + heuristic; // A*
    }

    @Override
    public int compareTo(State other) {
        return Integer.compare(this.getTotalCost("A*"), other.getTotalCost("A*"));
    }
}
