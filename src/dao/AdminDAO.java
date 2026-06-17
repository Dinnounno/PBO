package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import config.DatabaseConnection; // <--- UBAH MENJADI SEPERTI INI

public class AdminDAO {
    public boolean cekLogin(String username, String password) {
        String query = "SELECT * FROM admin WHERE username = ? AND password = ?";
        
        try (Connection conn = getConnectionFromDatabaseConnection()) {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            stmt.setString(2, password);
            
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    // Tambahkan fungsi ini di dalam class AdminDAO Anda yang sudah ada
public boolean insertAdmin(String username, String password) {
    String query = "INSERT INTO admin (username, password) VALUES (?, ?)";
    
    try (Connection conn = config.DatabaseConnection.getConnection()) {
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, username);
        stmt.setString(2, password);
        
        int barisTersimpan = stmt.executeUpdate();
        return barisTersimpan > 0;
    } catch (Exception ex) {
        ex.printStackTrace();
        return false;
    }
}
    private Connection getConnectionFromDatabaseConnection() throws Exception {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        Class<?> clazz = databaseConnection.getClass();
        String[] candidates = {
            "getConnection",
            "connect",
            "openConnection",
            "getDbConnection",
            "getDatabaseConnection"
        };

        for (String methodName : candidates) {
            try {
                java.lang.reflect.Method method = clazz.getMethod(methodName);
                Object result = method.invoke(databaseConnection);
                if (result instanceof Connection) {
                    return (Connection) result;
                }
            } catch (NoSuchMethodException ignored) {
                // try next candidate
            }
        }

        throw new NoSuchMethodException("No suitable connection method found in DatabaseConnection");
    }
}


