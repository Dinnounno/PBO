package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import config.DatabaseConnection;

public class DetailLaporanDAO {

    // KODE BACKEND: Menyimpan detail tambahan beserta nama file gambar bukti ke MySQL
    public boolean insertDetailLaporan(int idLaporan, String deskripsiTambahan, String namaFileGambar) {
        String query = "INSERT INTO detail_laporan (id_laporan, deskripsi_tambahan, bukti_foto) VALUES (?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, idLaporan);
            stmt.setString(2, deskripsiTambahan);
            stmt.setString(3, namaFileGambar); // Nama file gambar masuk ke kolom VARCHAR
            
            int barisTersimpan = stmt.executeUpdate();
            return barisTersimpan > 0;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
}