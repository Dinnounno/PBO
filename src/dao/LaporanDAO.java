package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import config.DatabaseConnection; // Memastikan import package config benar

public class LaporanDAO {

    // 1. BACKEND: Menyimpan Laporan Baru dari Mahasiswa ke MySQL
    public boolean insertLaporan(String judulLaporan, int idMahasiswa, int idKategori) {
        String query = "INSERT INTO laporan (judul_laporan, status, id_mahasiswa, id_kategori) VALUES (?, 'Pending', ?, ?)";
        
        // Memanggil langsung method static getConnection() dari class DatabaseConnection
        try (Connection conn = DatabaseConnection.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, judulLaporan);
            stmt.setInt(2, idMahasiswa);
            stmt.setInt(3, idKategori);
            
            int barisTersimpan = stmt.executeUpdate();
            return barisTersimpan > 0;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    // 2. BACKEND: Mengambil Seluruh Daftar Laporan dari MySQL untuk Dashboard
    public List<Map<String, Object>> ambilSemuaLaporan() {
        List<Map<String, Object>> daftarLaporan = new ArrayList<>();
        String query = "SELECT * FROM laporan";
        
        try (Connection conn = DatabaseConnection.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Map<String, Object> laporan = new HashMap<>();
                laporan.put("id_laporan", rs.getInt("id_laporan"));
                laporan.put("judul_laporan", rs.getString("judul_laporan"));
                laporan.put("status", rs.getString("status"));
                laporan.put("id_mahasiswa", rs.getInt("id_mahasiswa"));
                laporan.put("id_kategori", rs.getInt("id_kategori"));
                
                daftarLaporan.add(laporan);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return daftarLaporan;
    }

    // 3. BACKEND: Mengubah Status Pengaduan (Aksi Tombol oleh Admin)
    public boolean updateStatusLaporan(int idLaporan, String statusBaru) {
        String query = "UPDATE laporan SET status = ? WHERE id_laporan = ?";
        
        try (Connection conn = DatabaseConnection.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, statusBaru);
            stmt.setInt(2, idLaporan);
            
            int barisTerubah = stmt.executeUpdate();
            return barisTerubah > 0;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
}