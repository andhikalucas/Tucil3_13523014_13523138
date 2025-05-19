public class Heuristic {
    public static int evaluate(Board board, int id) {
        switch (id) {
            case 1:
                return Util.countBlockingCars(board); // Heuristic: jumlah kendaraan menghalangi
            case 2:
                return Util.distanceToExit(board);    // Heuristic: jarak ke pintu keluar
            default:
                return 0; // fallback
        }
    }
}
