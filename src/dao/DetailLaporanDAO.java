package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import config.DatabaseConnection;

public class DetailLaporanDAO {

    // Menyimpan detail laporan ke database
    public boolean insertDetailLaporan(int idLaporan,
                                       String deskripsi,
                                       String buktiLaporan) {

        String query = """
                INSERT INTO detail_laporan
                (id_laporan, deskripsi, bukti_laporan)
                VALUES (?, ?, ?)
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idLaporan);
            stmt.setString(2, deskripsi);
            stmt.setString(3, buktiLaporan);

            return stmt.executeUpdate() > 0;

        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
    public Map<String, String> getDetailLaporan(int idLaporan) {

    Map<String, String> detail = new HashMap<>();

    String query = """
            SELECT deskripsi, bukti_laporan
            FROM detail_laporan
            WHERE id_laporan = ?
            """;

    try (
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)
    ) {

        stmt.setInt(1, idLaporan);

        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {

            detail.put("deskripsi", rs.getString("deskripsi"));
            detail.put("bukti", rs.getString("bukti_laporan"));

        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return detail;
    }

    public boolean deleteDetailLaporan(int idLaporan) {

        String query = "DELETE FROM detail_laporan WHERE id_laporan = ?";

        try (Connection conn = DatabaseConnection.getConnection()) {

            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, idLaporan);

            return stmt.executeUpdate() >= 0;

        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }

    }
}