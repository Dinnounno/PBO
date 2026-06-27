package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import config.DatabaseConnection;

public class MahasiswaDAO {

    // 1. BACKEND REGISTRATION: Menyimpan data mahasiswa baru (NIM sebagai String/VARCHAR)
    public boolean insertMahasiswa(String idMahasiswa, String nama, String password, String email) {
        String query = "INSERT INTO mahasiswa (id_mahasiswa, nama, password, email) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, idMahasiswa); // varchar
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

    // 2. BACKEND LOGIN: Memeriksa NIM & Password (NIM sebagai String/VARCHAR)
    public boolean cekLoginMahasiswa(String idMahasiswa, String password) {
        String query = "SELECT * FROM mahasiswa WHERE id_mahasiswa = ? AND password = ?";
        
        try (Connection conn = DatabaseConnection.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, idMahasiswa); // varchar
            stmt.setString(2, password);
            
            ResultSet rs = stmt.executeQuery();
            return rs.next(); // Mengembalikan true jika data cocok ditemukan
        } catch (Exception ex) { 
            ex.printStackTrace(); 
            return false; 
        }
    }

    // 3. FUNGSI UNTUK MENU PROFIL: Mengambil nama dan email mahasiswa berdasarkan NIM (String)
    public Map<String, String> ambilProfilMahasiswa(String idMahasiswa) {
        Map<String, String> profil = new HashMap<>();
        String query = "SELECT nama, email FROM mahasiswa WHERE id_mahasiswa = ?";
        
        try (Connection conn = DatabaseConnection.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, idMahasiswa); // varchar
            
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                profil.put("nama", rs.getString("nama"));
                profil.put("email", rs.getString("email"));
            }
        } catch (Exception ex) { 
            ex.printStackTrace(); 
        }
        return profil;
    }
}