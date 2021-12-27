import java.sql.SQLException;

public interface PTABC {
    void viewdata() throws SQLException;
    void tambahdata() throws SQLException;
    void updatedata() throws SQLException;
    void deletedata();
    void caridata() throws SQLException;
}

