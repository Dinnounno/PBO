package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import config.DatabaseConnection; 

public class LaporanDAO {

    // 1. BACKEND: Menyimpan Laporan Baru dari Mahasiswa ke MySQL (Mengembalikan ID Laporan murni berupa int)
    public int insertLaporan(String judulLaporan, String idMahasiswa, int idKategori) {
    String query = "INSERT INTO laporan (judul_laporan, status, id_mahasiswa, id_kategori) VALUES (?, 'Pending', ?, ?)";
    try (Connection conn = DatabaseConnection.getConnection()) {
        PreparedStatement stmt = conn.prepareStatement(query, java.sql.Statement.RETURN_GENERATED_KEYS);
        stmt.setString(1, judulLaporan);
        stmt.setString(2, idMahasiswa); // Menggunakan setString
        stmt.setInt(3, idKategori);
        if (stmt.executeUpdate() > 0) {
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) return rs.getInt(1);
        }
    } catch (Exception ex) { ex.printStackTrace(); }
    return 0;
}

public List<Map<String, Object>> ambilSemuaLaporan() {

    List<Map<String, Object>> daftarLaporan = new ArrayList<>();

    String query = """
        SELECT
            l.id_laporan,
            l.id_mahasiswa,
            l.judul_laporan,
            k.nama_kategori,
            l.status
        FROM laporan l
        JOIN kategori_laporan k
            ON l.id_kategori = k.id_kategori
        ORDER BY l.id_laporan DESC
        """;

    try (
        Connection conn = DatabaseConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(query);
        ResultSet rs = stmt.executeQuery()
    ) {

        while (rs.next()) {

            Map<String, Object> data = new HashMap<>();

            data.put("id_laporan", rs.getInt("id_laporan"));
            data.put("id_mahasiswa", rs.getString("id_mahasiswa"));
            data.put("judul", rs.getString("judul_laporan"));
            data.put("kategori", rs.getString("nama_kategori"));
            data.put("status", rs.getString("status"));

            daftarLaporan.add(data);
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return daftarLaporan;
}

public int hitungTotalLaporan(String idMahasiswa) {

    String query = """
            SELECT COUNT(*) AS total
            FROM laporan
            WHERE id_mahasiswa = ?
            """;

    try (
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)
    ) {

        stmt.setString(1, idMahasiswa);

        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            return rs.getInt("total");
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return 0;
}

public List<Map<String, Object>> ambilRiwayatLaporan(String idMahasiswa) {

    List<Map<String, Object>> listRiwayat = new ArrayList<>();

    String query = """
        SELECT
            l.id_laporan,
            l.tanggal,
            l.judul_laporan,
            l.status,
            k.nama_kategori
        FROM laporan l
        JOIN kategori_laporan k
            ON l.id_kategori = k.id_kategori
        WHERE l.id_mahasiswa = ?
        ORDER BY l.id_laporan DESC
        """;

    try (
        Connection conn = DatabaseConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(query)
    ) {

        stmt.setString(1, idMahasiswa);

        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {

            Map<String, Object> row = new HashMap<>();

            row.put("id_laporan", rs.getInt("id_laporan"));
            row.put("tanggal", rs.getTimestamp("tanggal"));
            row.put("judul", rs.getString("judul_laporan"));
            row.put("kategori", rs.getString("nama_kategori"));
            row.put("status", rs.getString("status"));

            listRiwayat.add(row);
        }

    } catch (Exception ex) {
        ex.printStackTrace();
    }

    return listRiwayat;
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

    public boolean tolakLaporan(int idLaporan, String alasan) {

        String query = """
            UPDATE laporan
            SET status = ?, alasan_penolakan = ?
            WHERE id_laporan = ?
            """;

        try (
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)
        ) {

            stmt.setString(1, "Ditolak");
            stmt.setString(2, alasan);
            stmt.setInt(3, idLaporan);

            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    public boolean deleteLaporan(int idLaporan) {

        String query = "DELETE FROM laporan WHERE id_laporan = ?";

        try (Connection conn = DatabaseConnection.getConnection()) {

            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, idLaporan);

            return stmt.executeUpdate() > 0;

        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }

    }

    // 4. FUNGSI RIWAYAT: Menarik daftar keluhan khusus mahasiswa tertentu
    public List<Map<String, Object>> ambilRiwayatLaporan(long idMahasiswa) {
        List<Map<String, Object>> listRiwayat = new ArrayList<>();
        String query = "SELECT l.id_laporan, l.judul_laporan, l.status, k.nama_kategori " +
                       "FROM laporan l JOIN kategori k ON l.id_kategori = k.id_kategori " +
                       "WHERE l.id_mahasiswa = ?";
        
        try (Connection conn = DatabaseConnection.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setLong(1, idMahasiswa);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("id_laporan", rs.getInt("id_laporan"));
                row.put("judul", rs.getString("judul_laporan"));
                row.put("status", rs.getString("status"));
                row.put("kategori", rs.getString("nama_kategori"));
                listRiwayat.add(row);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return listRiwayat;
    }
}