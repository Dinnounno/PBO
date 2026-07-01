package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import dao.MahasiswaDAO;
import dao.AdminDAO;
import javax.swing.DefaultListCellRenderer;
import javax.swing.SwingConstants;

public class LoginForm extends JFrame {
    
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JComboBox<String> cmbRole;
    private JButton btnLogin, btnRegister;

    public LoginForm() {
        setTitle("Sistem Informasi Pengaduan FT Unud");
        setSize(420, 460);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        
        Color primaryColor = new Color(34, 49, 63);
        Color accentColor = new Color(41, 128, 185);
        Color bgColor = new Color(245, 246, 250);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(bgColor);
        mainPanel.setBorder(new EmptyBorder(30, 40, 30, 40));

        // --- 1. HEADER ---
        JPanel panelHeader = new JPanel(new GridLayout(2, 1, 5, 5));
        panelHeader.setBackground(bgColor);
        
        JLabel lblTitle = new JLabel("Sistem Pengaduan", JLabel.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitle.setForeground(primaryColor);
        
        JLabel lblSub = new JLabel("Fakultas Teknik Universitas Udayana", JLabel.CENTER);
        lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblSub.setForeground(Color.GRAY);
        
        panelHeader.add(lblTitle);
        panelHeader.add(lblSub);
        panelHeader.setBorder(new EmptyBorder(0, 0, 25, 0));
        mainPanel.add(panelHeader, BorderLayout.NORTH);

        // --- 2. FORM INPUT ---
        JPanel panelForm = new JPanel();
        panelForm.setLayout(new BoxLayout(panelForm, BoxLayout.Y_AXIS));
        panelForm.setBackground(bgColor);

        Font fontLabel = new Font("Segoe UI", Font.BOLD, 12);
        Font fontInput = new Font("Segoe UI", Font.PLAIN, 14);

        // Input Username / NIM
        JLabel lblUser = new JLabel("NIM atau Username");
        lblUser.setFont(fontLabel);
        lblUser.setForeground(primaryColor);
        lblUser.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        txtUsername = new JTextField();
        txtUsername.setFont(fontInput);
        txtUsername.setMaximumSize(new Dimension(340, 35));
        txtUsername.setAlignmentX(Component.CENTER_ALIGNMENT);
        txtUsername.setHorizontalAlignment(SwingConstants.CENTER);
        txtUsername.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 214, 229), 1),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));

        // Input Password
        JLabel lblPass = new JLabel("Password");
        lblPass.setFont(fontLabel);
        lblPass.setForeground(primaryColor);
        lblPass.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        txtPassword = new JPasswordField();
        txtPassword.setFont(fontInput);
        txtPassword.setMaximumSize(new Dimension(340, 35));
        txtPassword.setAlignmentX(Component.CENTER_ALIGNMENT);
        txtPassword.setHorizontalAlignment(SwingConstants.CENTER);
        txtPassword.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 214, 229), 1),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));

        // Pilihan Role
        JLabel lblRole = new JLabel("Masuk Sebagai");
        lblRole.setFont(fontLabel);
        lblRole.setForeground(primaryColor);
        lblRole.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        cmbRole = new JComboBox<>(new String[]{"Mahasiswa", "Admin"});
        cmbRole.setFont(fontInput);
        cmbRole.setBackground(Color.WHITE);
        cmbRole.setMaximumSize(new Dimension(340, 35));
        cmbRole.setAlignmentX(Component.CENTER_ALIGNMENT);
        DefaultListCellRenderer renderer = new DefaultListCellRenderer();
        renderer.setHorizontalAlignment(SwingConstants.CENTER);
        cmbRole.setRenderer(renderer);

        panelForm.add(lblUser); panelForm.add(Box.createVerticalStrut(6));
        panelForm.add(txtUsername); panelForm.add(Box.createVerticalStrut(15));
        panelForm.add(lblPass); panelForm.add(Box.createVerticalStrut(6));
        panelForm.add(txtPassword); panelForm.add(Box.createVerticalStrut(15));
        panelForm.add(lblRole); panelForm.add(Box.createVerticalStrut(6));
        panelForm.add(cmbRole);

        mainPanel.add(panelForm, BorderLayout.CENTER);

        // --- 3. BUTTONS PANEL ---
        JPanel panelButtons = new JPanel();
        panelButtons.setLayout(new BoxLayout(panelButtons, BoxLayout.Y_AXIS));
        panelButtons.setBackground(bgColor);
        panelButtons.setBorder(new EmptyBorder(20, 0, 0, 0));

        btnLogin = new JButton("Masuk Ke Sistem");
        btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnLogin.setBackground(new Color(28, 54, 94));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setContentAreaFilled(true);
        btnLogin.setOpaque(true);
        btnLogin.setMaximumSize(new Dimension(340, 40));
        btnLogin.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnLogin.setFocusPainted(false);
        btnLogin.setBorderPainted(false);
        btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnRegister = new JButton("Belum punya akun? Daftar di sini");
        btnRegister.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btnRegister.setForeground(accentColor);
        btnRegister.setContentAreaFilled(false);
        btnRegister.setBorderPainted(false);
        btnRegister.setFocusPainted(false);
        btnRegister.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnRegister.setCursor(new Cursor(Cursor.HAND_CURSOR));

        panelButtons.add(btnLogin);
        panelButtons.add(Box.createVerticalStrut(10));
        panelButtons.add(btnRegister);

        mainPanel.add(panelButtons, BorderLayout.SOUTH);
        add(mainPanel);

        btnLogin.addActionListener(e -> aksiLogin());
        btnRegister.addActionListener(e -> {
            new RegisterForm().setVisible(true);
            this.dispose();
        });
    }

    private void aksiLogin() {
    String usernameInput = txtUsername.getText().trim();
    String passwordInput = new String(txtPassword.getPassword()).trim();
    String roleDipilih = cmbRole.getSelectedItem().toString();

    // 1. Validasi Input Kosong
    if (usernameInput.isEmpty() || passwordInput.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Mohon isi semua kolom input.", "Informasi", JOptionPane.INFORMATION_MESSAGE);
        return;
    }

    // 2. Logika Login untuk Mahasiswa
    if (roleDipilih.equals("Mahasiswa")) {
        // Tidak perlu cek regex angka murni, langsung buat objek DAO
        MahasiswaDAO mhsDAO = new MahasiswaDAO();
        
        // Langsung kirim usernameInput berupa String murni ke database
        if (mhsDAO.cekLoginMahasiswa(usernameInput, passwordInput)) {
            JOptionPane.showMessageDialog(this, "Selamat datang kembali!", "Login Sukses", JOptionPane.INFORMATION_MESSAGE);
            
            // Buka Dashboard dan kirim data String NIM-nya
            new DashboardMahasiswaForm(usernameInput).setVisible(true);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "NIM atau Password tidak cocok.", "Akses Ditolak", JOptionPane.ERROR_MESSAGE);
        }
        
    // 3. Logika Login untuk Admin
    } else if (roleDipilih.equals("Admin")) {
        AdminDAO adminDAO = new AdminDAO();
        if (adminDAO.cekLogin(usernameInput, passwordInput)) {
            JOptionPane.showMessageDialog(this, "Mode Admin diaktifkan.", "Login Sukses", JOptionPane.INFORMATION_MESSAGE);
            
            // JALUR PANGGIL PANEL ADMIN BARU KAMU:
            new DashboardAdminForm().setVisible(true); 
            
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Kredensial admin salah.", "Akses Ditolak", JOptionPane.ERROR_MESSAGE);
        }
    }
    }

}

