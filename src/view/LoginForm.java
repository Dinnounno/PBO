package view;

import javax.swing.*;
import java.awt.*;
import dao.AdminDAO; // Memanggil backend login buatan Anda

public class LoginForm extends JFrame {
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;

    public LoginForm() {
        // Desain halaman UI oleh teman Anda
        setTitle("Sistem Pengaduan Teknik - Login");
        setSize(400, 280);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        getContentPane().setBackground(new Color(240, 244, 248));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Header
        JLabel lblHeader = new JLabel("LOGIN ADMIN", SwingConstants.CENTER);
        lblHeader.setFont(new Font("SansSerif", Font.BOLD, 18));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        add(lblHeader, gbc);

        // Input Username
        gbc.gridwidth = 1; gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("Username:"), gbc);
        txtUsername = new JTextField(15);
        gbc.gridx = 1; gbc.gridy = 1;
        add(txtUsername, gbc);

        // Input Password
        gbc.gridx = 0; gbc.gridy = 2;
        add(new JLabel("Password:"), gbc);
        txtPassword = new JPasswordField(15);
        gbc.gridx = 1; gbc.gridy = 2;
        add(txtPassword, gbc);

        // Tombol Login
        btnLogin = new JButton("Masuk Sistem");
        btnLogin.setBackground(new Color(41, 128, 185));
        btnLogin.setForeground(Color.WHITE);
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        add(btnLogin, gbc);

        // JEMBATAN KE BACKEND ANDA
        btnLogin.addActionListener(e -> {
            String user = txtUsername.getText();
            String pass = new String(txtPassword.getPassword());

            // UI memanggil fungsi murni dari backend Anda
            AdminDAO backend = new AdminDAO();
            boolean loginSukses = backend.cekLogin(user, pass);

            if (loginSukses) {
                JOptionPane.showMessageDialog(this, "Login Sukses! Masuk ke Dashboard.");
                this.dispose(); 
                // Di sini nanti teman Anda tinggal memanggil DashboardForm().setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Akun tidak ditemukan di database Laragon!", "Gagal", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}