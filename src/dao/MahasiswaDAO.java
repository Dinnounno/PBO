package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import config.DatabaseConnection;

public class MahasiswaDAO {

    // 1. BACKEND REGISTRATION: Menyimpan data mahasiswa baru dari form Register UI
    public boolean insertMahasiswa(int idMahasiswa, String nama, String password, String email) {
        String query = "INSERT INTO mahasiswa (id_mahasiswa, nama, password, email) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, idMahasiswa);
            stmt.setString(2, nama);
            stmt.setString(3, password);
            stmt.setString(4, email);
            
            int barisTersimpan = stmt.executeUpdate();
            return barisTersimpan > 0;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    // 2. BACKEND LOGIN: Memeriksa NIM (id_mahasiswa) & Password dari form Login UI
    public boolean cekLoginMahasiswa(int idMahasiswa, String password) {
        String query = "SELECT * FROM mahasiswa WHERE id_mahasiswa = ? AND password = ?";
        
        try (Connection conn = DatabaseConnection.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, idMahasiswa);
            stmt.setString(2, password);
            
            ResultSet rs = stmt.executeQuery();
            return rs.next(); // Mengembalikan true jika akunnya cocok ada di database
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
}