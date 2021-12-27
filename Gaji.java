import java.util.Scanner;
import java.util.InputMismatchException;

import com.mysql.cj.protocol.Resultset;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.*;

public class Gaji implements PTABC {
	static Connection conn;

	String url = "jdbc:mysql://localhost:3306/db_terimagaji";

    public Integer noPegawai = 0, potongan = 0, gajiPokok = 0, jumlahHariMasuk = 0, totalGaji = 0;
    public String namaPegawai, jabatan;

    Scanner scan = new Scanner(System.in);

    public void viewdata() throws SQLException 
    {
		String kata = "\nDaftar Data Seluruh Pegawai";
		System.out.println(kata.toUpperCase());
        System.out.println("---------------------------");
						
		String sql ="SELECT * FROM pegawai";
		conn = DriverManager.getConnection(url, "root", "");
		Statement statement = conn.createStatement();
		ResultSet result = statement.executeQuery(sql);
        
		
		while(result.next()){
            System.out.print("\nNama pegawai\t  : ");
            System.out.print(result.getString("Nama_Pegawai"));
            System.out.print("\nNomor pegawai\t  : ");
            System.out.print(result.getInt("No_Pegawai"));
            System.out.print("\nJabatan\t\t  : ");
            System.out.print(result.getString("Jabatan"));
            System.out.print("\nGaji pokok\t  : ");
            System.out.print(result.getInt("Gaji_Pokok"));
            System.out.print("\nJumlah Absent\t  : ");
            System.out.print(result.getInt("Jumlah_Absent"));
            System.out.print("\nPotongan Gaji\t  : ");
            System.out.print(result.getInt("Potongan_Gaji"));
            System.out.print("\nTotal gaji\t  : ");
            System.out.print(result.getInt("Total_Gaji"));
            System.out.print("\n");
		}
	}

    public void tambahdata() throws SQLException 
    {
    	String text = "\nTambah Data Pegawai";
		System.out.println(text.toUpperCase());
        System.out.println("-------------------");
		
    	try {
            // NamaPegawai
	        System.out.print("Masukkan Nama Pegawai: ");
	        namaPegawai = scan.nextLine(); 

	        // NoPegawai
	    	System.out.print("Masukkan Nomor Pegawai: ");
	        noPegawai = scan.nextInt();
	        scan.nextLine();
		        
	        // PilihanJabatan
	        Integer pilihanJabatan;
            System.out.println("Daftar Jabatan");
            System.out.println("1. Direktur");
            System.out.println("2. General Manajer");
            System.out.println("3. Manajer");
            System.out.println("4. Staff");
	        System.out.print("Pilih jabatan (1 - 4) : ");
	        pilihanJabatan = scan.nextInt();
	        
            if (pilihanJabatan == 1){
	            jabatan = "Direktur";
	        }
	        else if (pilihanJabatan == 2){
	            jabatan = "General Manajer";
	        }
	        else if (pilihanJabatan == 3){
	            jabatan = "Manajer";
	        }
	        else if (pilihanJabatan == 4){
	            jabatan = "Staff";
	        }
	        else{
	            System.out.println("\nJabatan tidak tersedia");
	        }
	        System.out.println("\nJabatan : " + jabatan.toUpperCase());

            //GajiPokok
            if (jabatan == "Direktur"){
	            gajiPokok = 50000000;
	        }
	        else if (jabatan == "General Manajer"){
	            gajiPokok = 30000000;
	        }
	        else if (jabatan == "Manajer"){
	            gajiPokok = 15000000;
	        }
	        else if (jabatan == "Staff"){
	            gajiPokok = 8000000;
	        }
	        else{
	            System.out.println("\nGaji pokok tidak tersedia");
	        }
	        System.out.println("Gaji pokok : Rp" + gajiPokok);
    
            //JumlahAbsent
            System.out.print("Jumlah Absent di Kantor : ");
            this.jumlahHariMasuk = scan.nextInt();

            //Potongangaji
            this.potongan = jumlahHariMasuk*150000;
            System.out.println("Potongan Gaji: Rp" + potongan);
            
            //TotalGaji
            this.totalGaji = gajiPokok - potongan;
            System.out.println("Total gaji: Rp" + totalGaji);

            String sql = "INSERT INTO pegawai (Nama_Pegawai, No_Pegawai, Jabatan, Gaji_Pokok, Jumlah_Absent, Potongan_Gaji, Total_Gaji) VALUES ('"+namaPegawai+"','"+noPegawai+"', '"+jabatan+"','"+gajiPokok+"','"+potongan+"','"+jumlahHariMasuk+"', '"+totalGaji+"')";
            conn = DriverManager.getConnection(url,"root","");
	        Statement statement = conn.createStatement();
            statement.execute(sql);
            System.out.println("\nBerhasil input data!!!");
    	}

    	catch (SQLException e) {
            System.err.println("\nTerjadi kesalahan input data");
        } 
        catch (InputMismatchException e) {
            System.err.println("\nInputan harus berupa angka");
           }
	   	}

        public void updatedata() throws SQLException{
            String text3 = "\nUpdate Data Pegawai";
            System.out.println(text3.toUpperCase());
            System.out.println("---------------------");
            
            try {
                viewdata();
                System.out.print("\nMasukkan Nomor Pegawai yang akan diupdate : ");
                Integer noPegawai = Integer.parseInt(scan.nextLine());
                
                String sql = "SELECT * FROM pegawai WHERE No_Pegawai = " + noPegawai;
                conn = DriverManager.getConnection(url,"root","");
                Statement statement = conn.createStatement();
                ResultSet result = statement.executeQuery(sql);
                
                if(result.next()){
                    
                    System.out.print("Nama baru ["+result.getString("Nama_Pegawai")+"]\t: ");
                    String namaPegawai = scan.nextLine();
                       
                    sql = "UPDATE pegawai SET Nama_Pegawai='" + namaPegawai + "' WHERE No_Pegawai = '" + noPegawai + "'";
                    //System.out.println(sql);
    
                    if(statement.executeUpdate(sql) > 0){
                        System.out.println("\nBerhasil memperbaharui data pegawai (Nomor "+noPegawai+")");
                    }
                }
                statement.close();        
            } 
            catch (SQLException e) {
                System.err.println("\nTerjadi kesalahan dalam mengedit data");
                System.err.println(e.getMessage());
            }
        }
        
        public void deletedata() {
            String text4 = "\nHapus Data Pegawai";
            System.out.println(text4.toUpperCase());
            System.out.println("------------------");
            
            try{
                viewdata();
                System.out.print("\nMasukan No Pegawai yang akan Anda Hapus : ");
                Integer noPegawai= Integer.parseInt(scan.nextLine());
    
                String sql = "DELETE FROM pegawai WHERE No_Pegawai = "+ noPegawai;
                conn = DriverManager.getConnection(url,"root","");
                Statement statement = conn.createStatement();
                
                if(statement.executeUpdate(sql) > 0){
                    System.out.println("\nBerhasil menghapus data pegawai (Nomor "+noPegawai+")");
                }
            }
            catch(SQLException e){
                System.out.println("\nTerjadi kesalahan dalam menghapus data");
            }
            catch(Exception e){
                System.out.println("\nMasukan data yang benar!!!");
            }

        }
        
        public void caridata() throws SQLException {
            String text5 = "\nCari Data Pegawai";
            System.out.println(text5.toUpperCase());
            System.out.println("-----------------");
                    
            System.out.print("Masukkan No Pegawai yang ingin dilihat : ");    
            Integer keyword = Integer.parseInt(scan.nextLine());
            
            String sql = "SELECT * FROM pegawai WHERE No_Pegawai LIKE '%"+keyword+"%'";
            conn = DriverManager.getConnection(url,"root","");
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery(sql); 
                    
            while(result.next()){
                System.out.print("\nNama Pegawai\t  : ");
                System.out.print(result.getString("nama_pegawai"));
                System.out.print("\nNomor pegawai\t  : ");
                System.out.print(result.getInt("No_Pegawai"));
                System.out.print("\nJabatan\t\t  : ");
                System.out.print(result.getString("Jabatan"));
                System.out.print("\nJumlah absent\t  : ");
                System.out.print(result.getInt("Jumlah_Absent"));;
                System.out.print("\nPotongan Gaji\t  : ");
                System.out.print(result.getInt("Potongan_Gaji"));
                System.out.print("\nTotal gaji\t  : ");
                System.out.print(result.getInt("Total_Gaji"));
                System.out.print("\n");
            }
        }   
    
}
