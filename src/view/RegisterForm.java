package view;

import javax.swing.*;
import java.awt.*;
import dao.MahasiswaDAO; // Jembatan ke backend Anda

public class RegisterForm extends JFrame {
    private JTextField txtNIM, txtNama, txtPassword, txtEmail;
    private JButton btnDaftar;

    public RegisterForm() {
        setTitle("Form Pendaftaran Mahasiswa FT");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Memosisikan jendela di tengah layar
        setLayout(new GridLayout(5, 2, 10, 10));

        // Membuat label dan kotak kosong yang bisa diketik bebas
        add(new JLabel(" NIM / ID Mahasiswa:"));
        txtNIM = new JTextField(); add(txtNIM);

        add(new JLabel(" Nama Lengkap:"));
        txtNama = new JTextField(); add(txtNama);

        add(new JLabel(" Password:"));
        txtPassword = new JTextField(); add(txtPassword);

        add(new JLabel(" Email Student:"));
        txtEmail = new JTextField(); add(txtEmail);

        btnDaftar = new JButton("Daftar Sekarang");
        add(btnDaftar);

        // AKSI SAAT TOMBOL DIKLIK (Mengambil ketikan layar lalu dikirim ke database)
        btnDaftar.addActionListener(e -> {
            try {
                // 1. Ambil teks yang Anda ketik secara bebas di layar
                int nimInput = Integer.parseInt(txtNIM.getText());
                String namaInput = txtNama.getText();
                String passInput = txtPassword.getText();
                String emailInput = txtEmail.getText();

                // 2. Alirkan teks ketikan tersebut ke fungsi backend milik Anda
                MahasiswaDAO backend = new MahasiswaDAO();
                boolean sukses = backend.insertMahasiswa(nimInput, namaInput, passInput, emailInput);

                // 3. Munculkan pop-up status di layar
                if (sukses) {
                    JOptionPane.showMessageDialog(this, "Mantap! " + namaInput + " Berhasil Masuk Database.");
                    // Mengosongkan form kembali setelah sukses input
                    txtNIM.setText(""); txtNama.setText(""); txtPassword.setText(""); txtEmail.setText("");
                } else {
                    JOptionPane.showMessageDialog(this, "Gagal! NIM mungkin sudah terdaftar.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "NIM harus berupa angka!", "Format Salah", JOptionPane.WARNING_MESSAGE);
            }
        });
    }
}