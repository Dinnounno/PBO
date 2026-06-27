package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import dao.MahasiswaDAO;

public class RegisterForm extends JFrame {

    private JTextField txtNIM, txtNama, txtEmail;
    private JPasswordField txtPassword;
    private JButton btnRegister, btnBack;

    public RegisterForm() {
        setTitle("Form Pendaftaran Mahasiswa FT Unud");
        setSize(420, 520);
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
        
        JLabel lblTitle = new JLabel("Daftar Akun Baru", JLabel.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitle.setForeground(primaryColor);
        
        JLabel lblSub = new JLabel("Mahasiswa Fakultas Teknik Udayana", JLabel.CENTER);
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
        Dimension inputSize = new Dimension(340, 35);

        // Input NIM
        JLabel lblNim = new JLabel("NIM / ID Mahasiswa");
        lblNim.setFont(fontLabel);
        lblNim.setForeground(primaryColor);
        lblNim.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        txtNIM = new JTextField();
        txtNIM.setFont(fontInput);
        txtNIM.setMaximumSize(inputSize);
        txtNIM.setAlignmentX(Component.CENTER_ALIGNMENT);
        txtNIM.setHorizontalAlignment(SwingConstants.CENTER);
        txtNIM.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 214, 229), 1),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));

        // Input Nama Lengkap
        JLabel lblNama = new JLabel("Nama Lengkap");
        lblNama.setFont(fontLabel);
        lblNama.setForeground(primaryColor);
        lblNama.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        txtNama = new JTextField();
        txtNama.setFont(fontInput);
        txtNama.setMaximumSize(inputSize);
        txtNama.setAlignmentX(Component.CENTER_ALIGNMENT);
        txtNama.setHorizontalAlignment(SwingConstants.CENTER);
        txtNama.setBorder(BorderFactory.createCompoundBorder(
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
        txtPassword.setMaximumSize(inputSize);
        txtPassword.setAlignmentX(Component.CENTER_ALIGNMENT);
        txtPassword.setHorizontalAlignment(SwingConstants.CENTER);
        txtPassword.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 214, 229), 1),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));

        // Input Email
        JLabel lblEmail = new JLabel("Email Student");
        lblEmail.setFont(fontLabel);
        lblEmail.setForeground(primaryColor);
        lblEmail.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        txtEmail = new JTextField();
        txtEmail.setFont(fontInput);
        txtEmail.setMaximumSize(inputSize);
        txtEmail.setAlignmentX(Component.CENTER_ALIGNMENT);
        txtEmail.setHorizontalAlignment(SwingConstants.CENTER);
        txtEmail.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 214, 229), 1),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));

        panelForm.add(lblNim); panelForm.add(Box.createVerticalStrut(4));
        panelForm.add(txtNIM); panelForm.add(Box.createVerticalStrut(12));
        panelForm.add(lblNama); panelForm.add(Box.createVerticalStrut(4));
        panelForm.add(txtNama); panelForm.add(Box.createVerticalStrut(12));
        panelForm.add(lblPass); panelForm.add(Box.createVerticalStrut(4));
        panelForm.add(txtPassword); panelForm.add(Box.createVerticalStrut(12));
        panelForm.add(lblEmail); panelForm.add(Box.createVerticalStrut(4));
        panelForm.add(txtEmail);

        mainPanel.add(panelForm, BorderLayout.CENTER);

        // --- 3. BUTTONS PANEL ---
        JPanel panelButtons = new JPanel();
        panelButtons.setLayout(new BoxLayout(panelButtons, BoxLayout.Y_AXIS));
        panelButtons.setBackground(bgColor);
        panelButtons.setBorder(new EmptyBorder(15, 0, 0, 0));

        btnRegister = new JButton("Daftar Sekarang");
        btnRegister.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnRegister.setForeground(Color.WHITE);
        btnRegister.setBackground(accentColor);
        btnRegister.setMaximumSize(new Dimension(340, 40));
        btnRegister.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnRegister.setFocusPainted(false);
        btnRegister.setBorderPainted(false);
        btnRegister.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnBack = new JButton("Sudah punya akun? Masuk di sini");
        btnBack.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btnBack.setForeground(accentColor);
        btnBack.setContentAreaFilled(false);
        btnBack.setBorderPainted(false);
        btnBack.setFocusPainted(false);
        btnBack.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnBack.setCursor(new Cursor(Cursor.HAND_CURSOR));

        panelButtons.add(btnRegister);
        panelButtons.add(Box.createVerticalStrut(10));
        panelButtons.add(btnBack);

        mainPanel.add(panelButtons, BorderLayout.SOUTH);
        add(mainPanel);

        btnRegister.addActionListener(e -> aksiRegistrasi());
        btnBack.addActionListener(e -> {
            new LoginForm().setVisible(true);
            this.dispose();
        });
    }

    private void aksiRegistrasi() {
        String nimStr = txtNIM.getText().trim();
        String namaInput = txtNama.getText().trim();
        String passwordInput = new String(txtPassword.getPassword()).trim();
        String emailInput = txtEmail.getText().trim();

        // 1. Validasi Input Kosong
        if (nimStr.isEmpty() || namaInput.isEmpty() || passwordInput.isEmpty() || emailInput.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Mohon isi semua kolom registrasi.", "Informasi", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // 2. Proteksi Karakter Non-Angka pada NIM
        if (!nimStr.matches("\\d+")) {
            JOptionPane.showMessageDialog(this, "NIM harus berupa angka murni.", "Format Salah", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            // 3. Langsung kirim nimStr berupa String murni ke backend MahasiswaDAO
            MahasiswaDAO mhsDAO = new MahasiswaDAO();
            if (mhsDAO.insertMahasiswa(nimStr, namaInput, passwordInput, emailInput)) {
                JOptionPane.showMessageDialog(this, "Pendaftaran Berhasil! Silakan login kembali.", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                new LoginForm().setVisible(true);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Gagal mendaftar. NIM mungkin sudah terdaftar.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Terjadi gangguan sistem registrasi.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}