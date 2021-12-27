import java.util.Date;
import java.util.InputMismatchException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import com.mysql.cj.protocol.Resultset;
import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.*;

public class Program {
    static Connection conn;
    public static void main(String[] args) throws Exception {
        
        Scanner scan = new Scanner (System.in);
    	String pilihan;
    	boolean isLanjutkan = true;

        DateFormat tanggal = new SimpleDateFormat("dd MMMM yyyy");
        DateFormat jam = new SimpleDateFormat("HH:mm:ss");
        Date tgldanjam = new Date();

        String url = "jdbc:mysql://localhost:3306/db_terimagaji";
		try {
        System.out.println("\n");
        String sapa = "   Hello!!! Selamat Sore!   ";
        String GantiKalimat = sapa.replace("Selamat Sore!","Selamat Pagi");//method String replace()
        //String sapa = "\nHello!!! Selamat Pagi!";
        //String GantiKalimat = sapa.replace("Selamat Pagi!","Selamat Sore");//method String replace()
        System.out.println(GantiKalimat.trim());//method trim()
        
        System.out.println("\n===============================================");
        System.out.println(" Tanggal\t: " + tanggal.format(tgldanjam) );
        System.out.println(" Jam    \t: " + jam.format(tgldanjam) + " WIB");
        System.out.println("-----------------------------------------------");

        Class.forName("com.mysql.cj.jdbc.Driver");
        conn = DriverManager.getConnection(url,"root","");
        System.out.println("Class Driver ditemukan1");
        
        Gaji gaji = new Gaji();

        while (isLanjutkan) {
            System.out.println("\n------------------------");
            System.out.println("====Database Pegawai====");
            System.out.println("------------------------");
            System.out.println("1. Tampilkan Data Pegawai");
            System.out.println("2. Tambah Data Pegawai");
            System.out.println("3. Update Data Pegawai");
            System.out.println("4. Hapus Data Pegawai");
            System.out.println("5. Cari Data Pegawai");
            
            System.out.print("\n> Pilihan anda (1/2/3/4/5): ");
            pilihan = scan.next();
            
            switch (pilihan) {
            case "1":
                gaji.viewdata();
                break;
            case "2":
                gaji.tambahdata();
                break;
            case "3":
                gaji.updatedata();
                break;
            case "4":
                gaji.deletedata();
                break;
            case "5":
                gaji.caridata();
                break;
            default:
                System.err.println("\nInput anda tidak ditemukan");
                System.out.println("> Silakan pilih [1-5]");
            }
            
            System.out.print("\n> Apakah Anda ingin melanjutkan [y/n]? ");
            pilihan = scan.next();
            isLanjutkan = pilihan.equalsIgnoreCase("y");
        }
        System.out.println("\nProgram selesai, Selamat Berjumpa Kembali");

        }
        catch(ClassNotFoundException ex) {
        System.err.println("Driver Error");
        System.exit(0);
        }
        catch(SQLException e){
        System.err.println("Tidak berhasil koneksi");
        }

        finally 
        {
            System.out.println("\nSekian, Terima Kasih :D");
            System.out.println("===============================================");
        }

    }
}
