import java.io.File;
import java.util.Scanner;
public class Main implements Style{
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        // Header
        System.out.print(CLEAR_SCREEN);
        System.out.println(RUSHHOUR);
        System.out.println();
        System.out.println("Selamat datang di Rush Hour Solver!");
        System.out.println(ITALIC + YELLOW + "Masukkan input menggunakan angka!" + RESET);
        System.out.println();

        // List all .txt files in ../test/
        File testFolder = new File("../test/input");
        File[] txtFiles = testFolder.listFiles((_, name) -> name.endsWith(".txt"));
        if (txtFiles == null || txtFiles.length == 0) {
            System.out.println(YELLOW + "Tidak ada file .txt di folder ../test/. Program berhenti." + RESET);
            sc.close();
            throw new RuntimeException("Tidak ada file .txt di folder ../test/. Program berhenti.");
        }
        System.out.println(BRIGHT_GREEN + "Daftar file input berhasil dimuat dari folder test!" + RESET);

        // Pilih file
        int fileChoice = 0;
        String filePath = "";
        String statusMessage = ""; // Variabel untuk menyimpan pesan status
        String statusColor = ""; // Variabel untuk warna pesan
        boolean firstLoad = true; // Flag untuk pertama kali menampilkan pilihan

        while (true) {
            if (firstLoad) {
                firstLoad = false; // Tidak perlu clear screen pada tampilan pertama
            } else {
                System.out.print(CLEAR_SCREEN);
                // Tampilkan pesan status jika ada
                if (!statusMessage.isEmpty()) {
                    System.out.println(statusColor + statusMessage + RESET + "\n");
                }
            }

            for (int i = 0; i < txtFiles.length; i++) {
                System.out.println(GRAY + "  " + (i + 1) + ". " + RESET + txtFiles[i].getName());
            }
            System.out.println();
            System.out.print("Pilih file input dengan angka (1-" + txtFiles.length + ")" + RESET + ":\n" + GREEN + ">> " + RESET);
            String input = sc.nextLine().trim();
            try {
                fileChoice = Integer.parseInt(input);
                if (fileChoice < 1 || fileChoice > txtFiles.length) throw new NumberFormatException();
                filePath = txtFiles[fileChoice - 1].getPath();
                break;
            } catch (NumberFormatException e) {
                statusMessage = "Pilihan salah. Mohon masukkan angka antara 1 dan " + txtFiles.length + ".";
                statusColor = YELLOW;
            }
        }

        // Pilih algoritma
        int algoChoice = 0;
        String algorithm = "";
        statusMessage = "File konfigurasi berhasil dimuat!";
        statusColor = BRIGHT_GREEN;
        
        while (true) {
            System.out.print(CLEAR_SCREEN);
            // Tampilkan pesan status di awal
            System.out.println(statusColor + statusMessage + RESET + "\n");
            
            System.out.println(GRAY + "  1. " + RESET + "Uniform Cost Search (UCS)");
            System.out.println(GRAY + "  2. " + RESET + "Greedy Best First Search");
            System.out.println(GRAY + "  3. " + RESET + "A* Search");
            System.out.println();
            System.out.print("Pilih algoritma pencarian:\n" + GREEN + ">> " + RESET);
            String input = sc.nextLine().trim();
            try {
                algoChoice = Integer.parseInt(input);
                if (algoChoice == 1) algorithm = "UCS";
                else if (algoChoice == 2) algorithm = "Greedy";
                else if (algoChoice == 3) algorithm = "A*";
                else throw new NumberFormatException();
                break;
            } catch (NumberFormatException e) {
                statusMessage = "Pilihan salah. Mohon masukkan angka 1, 2, atau 3.";
                statusColor = YELLOW;
            }
        }

        // Skip memilih heuristic jika algoritma adalah UCS
        int heuristicId = 0;
        if (!algorithm.equals("UCS")) {
            // Pilih heuristic
            statusMessage = "Algoritma berhasil dipilih: " + algorithm;
            statusColor = BRIGHT_GREEN;
            
            while (true) {
                System.out.print(CLEAR_SCREEN);
                // Tampilkan pesan status di awal
                System.out.println(statusColor + statusMessage + RESET + "\n");
                
                System.out.println(GRAY + "  1. " + RESET + "Jumlah kendaraan yang menghalangi ke pintu keluar");
                System.out.println(GRAY + "  2. " + RESET + "Jarak ke pintu keluar");
                System.out.println();
                System.out.print("Pilih heuristic:\n" + GREEN + ">> " + RESET);
                String input = sc.nextLine().trim();
                try {
                    heuristicId = Integer.parseInt(input);
                    if (heuristicId == 1 || heuristicId == 2) {
                        break;
                    } else throw new NumberFormatException();
                } catch (NumberFormatException e) {
                    statusMessage = "Pilihan salah. Mohon masukkan angka 1 atau 2.";
                    statusColor = YELLOW;
                }
            }
        }

        // Mulai proses
        System.out.print(CLEAR_SCREEN);
        System.out.println(BRIGHT_GREEN + "Membaca file dan memulai pencarian solusi..." + RESET);
        System.out.println();

        try {
            Board board = Util.loadBoardFromFile(filePath);
            if (board == null) {
                System.out.println(YELLOW + "Board gagal dimuat. Program berhenti." + RESET);
                return;
            }
            
            Solver solver = new Solver(board, algorithm, heuristicId);
            State solution = solver.solve();
            System.out.println();
            
            // Penyimpanan solusi jika ada solusi
            if (solution != null) {
                boolean valid = false;
                while (!valid){
                    try {
                        System.out.print("Apakah Anda ingin menyimpan solusi? (y/n):\n" + GREEN + ">> " + RESET);
                        String response = sc.nextLine().trim().toLowerCase();
                        System.out.println();
                        
                        if (response.equals("y")) {
                            System.out.print("Masukkan nama file solusi " + ITALIC + YELLOW + "(tanpa ekstensi .txt):\n" + RESET + GREEN + ">> " + RESET);
                            String fileName = sc.nextLine().trim();
                            System.out.println();
                            
                            // Jika nama file kosong, gunakan default
                            if (fileName.isEmpty()) {
                                fileName = "solution";
                            }
                            
                            // Simpan solusi ke file
                            Util.writeSolutionToFile(solution, algorithm, heuristicId, fileName);
                            valid = true;
                        } else if (response.equals("n")){
                            valid = true;
                        } else {
                            throw new IllegalArgumentException("Mohon masukkan pilihan 'y' atau 'n'.\n");
                        }
                    } catch (IllegalArgumentException e) {
                        System.out.println(YELLOW + "Pilihan salah. " + e.getMessage() + RESET);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(YELLOW + "Terjadi kesalahan: " + e.getMessage() + RESET);
        } finally {
            sc.close();
            System.out.println(BOLD_GREEN + "\nSampai jumpa di compile selanjutnya!" + RESET);
        }
    }
}