package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import config.DatabaseConnection;

public class KategoriDAO {

    // KODE BACKEND: Mengambil daftar kategori untuk menu pilihan (Dropdown) di UI
    public List<Map<String, Object>> ambilSemuaKategori() {
        List<Map<String, Object>> daftarKategori = new ArrayList<>();
        String query = "SELECT * FROM kategori";
        
        try (Connection conn = DatabaseConnection.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Map<String, Object> kat = new HashMap<>();
                kat.put("id_kategori", rs.getInt("id_kategori"));
                kat.put("nama_kategori", rs.getString("nama_kategori"));
                
                daftarKategori.add(kat);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return daftarKategori;
    }
}