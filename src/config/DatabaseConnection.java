package config; // Alamat folder wajib config

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/db_pengaduan_pbi?serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = ""; 

    // WAJIB menggunakan kata kunci 'public static'
    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }
}