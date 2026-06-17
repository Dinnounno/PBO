package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import config.DatabaseConnection;

public class ResponDAO {

    // KODE BACKEND: Menyimpan respon yang diketik oleh Admin ke MySQL
    public boolean kirimResponAdmin(int idLaporan, int idAdmin, String isiRespon) {
        String query = "INSERT INTO respon (id_laporan, id_admin, isi_respon) VALUES (?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, idLaporan);
            stmt.setInt(2, idAdmin);
            stmt.setString(3, isiRespon);
            
            int barisTersimpan = stmt.executeUpdate();
            
            // LOGIKA TAMBAHAN OTOMATIS:
            // Jika respon berhasil disimpan, kita otomatis ubah status laporan tersebut menjadi 'Diproses' atau 'Selesai'
            if (barisTersimpan > 0) {
                LaporanDAO updateStatus = new LaporanDAO();
                updateStatus.updateStatusLaporan(idLaporan, "Selesai");
            }
            
            return barisTersimpan > 0;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
}