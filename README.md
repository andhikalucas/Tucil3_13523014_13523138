# Tugas Kecil 3 IF2211 2024/2025
![alt text](rushhour.png)
<h3 align="center">Penyelesaian Puzzle Rush Hour Menggunakan Algoritma Pathfinding</h3>

<p align="center">
<b>Rush Hour Solver</b> adalah program Java berbasis CLI yang menggunakan algoritma pathfinding untuk menyelesaikan permainan <i>Rush Hour</i> menggunakan algoritma pencarian <b>Uniform Cost Search (UCS)</b>, <b>Greedy Best First Search (GBFS)</b>, atau <b>A*</b>.</p>

<p align="center"><b><i>Rush Hour</b></i> adalah sebuah permainan puzzle logika berbasis grid yang bertujuan untuk menggeser kendaraan di dalam sebuah kotak agar mobil utama dapat keluar dari kemacetan melalui pintu keluar di sisi papan.</p>

## Daftar Isi
- [Requirements](#requirements)
- [Instalasi](#instalasi)
- [Kompilasi Program](#kompilasi-program)
- [Menjalankan Program](#menjalankan-program)
- [Cara Menggunakan Program](#cara-menggunakan-program)
- [Author](#author)

## Requirements
- **Java Development Kit (JDK)** versi 11 atau lebih baru
- **Command Line** atau terminal untuk menjalankan program

## Instalasi

1. **Instal JDK**

   * Unduh JDK versi terbaru dari [situs resmi OpenJDK](https://jdk.java.net/)
   * Ikuti instruksi instalasi sesuai OS

2. **Clone repository ini**

```bash
git clone https://github.com/andhikalucas/Tucil3_13523014_13523138.git
```

## Kompilasi Program

1. **Buka terminal dan masuk ke direktori src**

```bash
cd path/to/Tucil3_13523014_13523138/src
```

2. **Kompilasi Program ke Direktori Bin**

```bash
javac -d ../bin *.java
```
## Menjalankan Program

1. **Masuk ke direktori bin**
```bash
cd path/to/Tucil3_13523014_13523138/bin
```

2. **Jalankan Program Main**
```bash
java Main
```

## Cara Menggunakan Program

1. Masukkan input pilihan sesuai permintaan program:
- File konfigurasi puzzle (yang terletak pada direktori test/input)
- Algoritma pencarian (UCS, GBFS, atau A*)
- Heuristik (jika menggunakan GBFS atau A*)

2. Output program berupa langkah-langkah solusi pada terminal dengan opsi menyimpan solusi ke file .txt

3. Untuk mengakses output solusi permainan, masuk ke direktori output:
```bash
cd path/to/Tucil3_13523014_13523138/test/output/namafileanda.txt
```

4. Untuk menambahkan file konfigurasi puzzle baru, masuk ke direktori input:

```bash
cd path/to/Tucil3_13523014_13523138/test/input
```

kemudian buatlah file .txt konfigurasi puzzle baru, dengan format

```bash
A B
N
konfigurasi_papan

Contoh:
6 6
11
AAB..F
..BCDF
GPPCDFK
GH.III
GHJ...
LLJMM.
```
#### Keterangan:
- Dimensi papan A x B
- N: Banyak piece BUKAN primary piece 
- P: Primary piece
- K: Pintu keluar
- Huruf/karakter lain: piece berbeda
- . (titik): cell kosong


## Author

| Nama                          | NIM        |
|-------------------------------|------------|
| Nicholas Andhika Lucas        | 13523014   |
| Samantha Laqueenna Ginting    | 13523138   |