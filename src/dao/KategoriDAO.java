package dao;

import config.DatabaseConnection;
import model.Kategori;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class KategoriDAO {

    public List<Kategori> getSemuaKategori() {

        List<Kategori> daftarKategori = new ArrayList<>();

        String sql = "SELECT * FROM kategori_laporan ORDER BY nama_kategori";

        try (
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {

                Kategori kategori = new Kategori();

                kategori.setIdKategori(rs.getInt("id_kategori"));
                kategori.setNamaKategori(rs.getString("nama_kategori"));

                daftarKategori.add(kategori);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return daftarKategori;
    }

}