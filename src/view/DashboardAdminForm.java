package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class DashboardAdminForm extends JFrame {

    // --- TEMA WARNA ADMIN PREMIUM (DARK SLATE & CRIMSON ACCENT) ---
    private Color sidebarColor = new Color(44, 62, 80);       
    private Color bgColor = new Color(245, 246, 250);         
    private Color fontColorUtama = new Color(44, 62, 80);
    
    private Color warnaHijau = new Color(46, 204, 113);
    private Color warnaMerah = new Color(231, 76, 60);
    private Color warnaUngu = new Color(155, 89, 182);
    private Color warnaKuning = new Color(241, 196, 15);

    private JPanel panelKontenUtama;
    private CardLayout cardLayout;
    private JTable tabelAduanMasuk;
    private DefaultTableModel tableModel;

    public DashboardAdminForm() {
        setTitle("Panel Kontrol Administrator - Sistem Pengaduan FT Unud");
        setSize(1050, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(bgColor);

        // ==========================================
        // --- 1. SIDEBAR ADMIN (KIRI) ---
        // ==========================================
        JPanel panelSidebar = new JPanel();
        panelSidebar.setLayout(new BoxLayout(panelSidebar, BoxLayout.Y_AXIS));
        panelSidebar.setBackground(sidebarColor);
        panelSidebar.setPreferredSize(new Dimension(250, 650));
        panelSidebar.setBorder(new EmptyBorder(20, 15, 20, 15));

        // Header Logo Admin
        JLabel lblLogo = new JLabel("ADMIN CONTROL", JLabel.LEFT);
        lblLogo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblLogo.setForeground(new Color(231, 76, 60)); 
        lblLogo.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelSidebar.add(lblLogo);
        panelSidebar.add(Box.createVerticalStrut(25));

        // Badge Status Admin
        JLabel lblAdminBadge = new JLabel("👑 Administrator Mode");
        lblAdminBadge.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        lblAdminBadge.setForeground(Color.LIGHT_GRAY);
        lblAdminBadge.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelSidebar.add(lblAdminBadge);
        panelSidebar.add(Box.createVerticalStrut(20));

        // Pembatas Navigasi
        JLabel lblNav = new JLabel("MENU MANAJEMEN");
        lblNav.setFont(new Font("Segoe UI", Font.BOLD, 10));
        lblNav.setForeground(new Color(149, 165, 166));
        lblNav.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblNav.setBorder(new EmptyBorder(0, 5, 10, 0));
        panelSidebar.add(lblNav);

        // Tombol Navigasi Admin
        JButton btnHome = buatTombolAdmin(" Verifikasi Aduan");
        JButton btnLogout = buatTombolAdmin(" Keluar Sistem");
        btnLogout.setForeground(new Color(240, 128, 128));

        panelSidebar.add(btnHome);
        panelSidebar.add(Box.createVerticalGlue());
        panelSidebar.add(btnLogout);
        panelSidebar.add(Box.createVerticalStrut(10));

        mainPanel.add(panelSidebar, BorderLayout.WEST);

        // ==========================================
        // --- 2. KONTEN UTAMA VIA CARDLAYOUT (KANAN) ---
        // ==========================================
        cardLayout = new CardLayout();
        panelKontenUtama = new JPanel(cardLayout);
        panelKontenUtama.setBackground(bgColor);
        panelKontenUtama.setBorder(new EmptyBorder(30, 30, 30, 30));

        // Halaman Manajemen Aduan
        JPanel panelManajemen = new JPanel(new BorderLayout(0, 20));
        panelManajemen.setBackground(bgColor);

        // Header Konten
        JPanel panelHeader = new JPanel(new GridLayout(2, 1, 0, 5));
        panelHeader.setBackground(bgColor);
        JLabel lblTitle = new JLabel("Validasi & Verifikasi Pengaduan Mahasiswa", JLabel.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitle.setForeground(fontColorUtama);
        JLabel lblSub = new JLabel("Klik baris aduan untuk melihat rincian & bukti. Gunakan tombol bawah untuk mengubah status.", JLabel.CENTER);
        lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblSub.setForeground(Color.GRAY);
        panelHeader.add(lblTitle);
        panelHeader.add(lblSub);
        panelManajemen.add(panelHeader, BorderLayout.NORTH);

        // DATA TABEL PENGADUAN MASUK [READ]
        String[] kolom = {"ID Laporan", "NIM Pengirim", "Judul Keluhan", "Kategori", "Status Current"};
        Object[][] dataAduan = {
            {"A-001", "2505551023", "AC Ruang BI 3.2 Bocor dan Mati", "Fasilitas Kelas", "Pending"},
            {"A-002", "2505551040", "Komputer Lab SIM Hang massal saat praktikum", "Fasilitas Lab & Komputer", "Diproses"},
            {"A-003", "2505551011", "Kran Air Toilet Lantai 2 Gedung Teknik Pecah", "Kebersihan Toilet", "Selesai"}
        };

        tableModel = new DefaultTableModel(dataAduan, kolom) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        tabelAduanMasuk = new JTable(tableModel);
        tabelAduanMasuk.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tabelAduanMasuk.setRowHeight(35);
        tabelAduanMasuk.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabelAduanMasuk.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        tabelAduanMasuk.getTableHeader().setBackground(sidebarColor);
        tabelAduanMasuk.getTableHeader().setForeground(Color.WHITE);
        tabelAduanMasuk.setGridColor(new Color(236, 240, 241));

        // Mewarnai Kolom Status Current
        tabelAduanMasuk.getColumnModel().getColumn(4).setCellRenderer(new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (value != null) {
                    String status = value.toString();
                    setHorizontalAlignment(JLabel.CENTER);
                    setFont(new Font("Segoe UI", Font.BOLD, 12));
                    if (status.equalsIgnoreCase("Pending")) c.setForeground(warnaMerah);
                    else if (status.equalsIgnoreCase("Diproses")) c.setForeground(warnaUngu);
                    else if (status.equalsIgnoreCase("Selesai")) c.setForeground(warnaHijau);
                }
                if (isSelected) c.setBackground(new Color(232, 244, 248));
                else c.setBackground(Color.WHITE);
                return c;
            }
        });

        JScrollPane scrollTable = new JScrollPane(tabelAduanMasuk);
        scrollTable.setBorder(BorderFactory.createLineBorder(new Color(228, 233, 237), 1));
        panelManajemen.add(scrollTable, BorderLayout.CENTER);

        // --- PANEL CONTROL AKSI [UPDATE & DELETE] (BAWAH TABEL) ---
        JPanel panelAksiBawah = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panelAksiBawah.setBackground(bgColor);

        JButton btnUpdateProses = new JButton("⚙️ Set 'Diproses'");
        btnUpdateProses.setBackground(warnaUngu); btnUpdateProses.setForeground(Color.WHITE);
        
        JButton btnUpdateSelesai = new JButton("✅ Set 'Selesai'");
        btnUpdateSelesai.setBackground(warnaHijau); btnUpdateSelesai.setForeground(Color.WHITE);

        JButton btnDeleteLaporan = new JButton("🗑️ Hapus Aduan");
        btnDeleteLaporan.setBackground(warnaMerah); btnDeleteLaporan.setForeground(Color.WHITE);

        Dimension btnSize = new Dimension(160, 38);
        btnUpdateProses.setPreferredSize(btnSize); btnUpdateSelesai.setPreferredSize(btnSize); btnDeleteLaporan.setPreferredSize(btnSize);
        btnUpdateProses.setCursor(new Cursor(Cursor.HAND_CURSOR)); btnUpdateSelesai.setCursor(new Cursor(Cursor.HAND_CURSOR)); btnDeleteLaporan.setCursor(new Cursor(Cursor.HAND_CURSOR));

        panelAksiBawah.add(btnUpdateProses);
        panelAksiBawah.add(btnUpdateSelesai);
        panelAksiBawah.add(btnDeleteLaporan);
        panelManajemen.add(panelAksiBawah, BorderLayout.SOUTH);

        panelKontenUtama.add(panelManajemen, "MENU_MANAJEMEN");
        mainPanel.add(panelKontenUtama, BorderLayout.CENTER);
        add(mainPanel);

        // ========================================================
        // --- LOGIKA EVENT AKSI TOMBOL CRUD & POP-UP DETAIL ---
        // ========================================================

        // Aksi Klik Baris Tabel: Memunculkan Rincian & Bukti Foto aduan
        tabelAduanMasuk.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int barisTerpilih = tabelAduanMasuk.getSelectedRow();
                if (barisTerpilih != -1) {
                    String id = tableModel.getValueAt(barisTerpilih, 0).toString();
                    String nimMhs = tableModel.getValueAt(barisTerpilih, 1).toString();
                    String judul = tableModel.getValueAt(barisTerpilih, 2).toString();
                    String kategori = tableModel.getValueAt(barisTerpilih, 3).toString();
                    String status = tableModel.getValueAt(barisTerpilih, 4).toString();
                    
                    String deskripsiKronologi = "";
                    String namaFoto = "Tidak ada lampiran foto.";
                    
                    if(id.equals("A-001")) {
                        deskripsiKronologi = "Sejak pagi hari air menetes deras dari casing AC bagian dalam kelas BI 3.2, hawa ruangan panas menyebabkan konsentrasi belajar terganggu.";
                        namaFoto = "bukti_ac_bocor.jpg";
                    } else if(id.equals("A-002")) {
                        deskripsiKronologi = "Komputer nomor 05 s/d 12 tiba-tiba freeze total saat mahasiswa mencoba melakukan koneksi database master-slave di Ubuntu Server VirtualBox.";
                        namaFoto = "lab_hang.png";
                    } else {
                        deskripsiKronologi = "Kran air di bilik tengah toilet cowok lantai 2 patah di bagian ulir, sehingga air terus mengalir mubazir membasahi seluruh lantai.";
                        namaFoto = "toilet_ft.jpg";
                    }

                    tampilkanDialogDetailAduanAdmin(id, nimMhs, judul, kategori, deskripsiKronologi, namaFoto, status);
                }
            }
        });

        // Logika UPDATE menjadi 'Diproses'
        btnUpdateProses.addActionListener(e -> {
            int row = tabelAduanMasuk.getSelectedRow();
            if (row != -1) {
                tableModel.setValueAt("Diproses", row, 4);
                JOptionPane.showMessageDialog(this, "Status aduan berhasil diperbarui ke 'Diproses'!", "Update Sukses", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Pilih baris laporan terlebih dahulu!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            }
        });

        // Logika UPDATE menjadi 'Selesai'
        btnUpdateSelesai.addActionListener(e -> {
            int row = tabelAduanMasuk.getSelectedRow();
            if (row != -1) {
                tableModel.setValueAt("Selesai", row, 4);
                JOptionPane.showMessageDialog(this, "Status aduan berhasil diselesaikan!", "Update Sukses", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Pilih baris laporan terlebih dahulu!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            }
        });

        // Logika DELETE Laporan
        btnDeleteLaporan.addActionListener(e -> {
            int row = tabelAduanMasuk.getSelectedRow();
            if (row != -1) {
                int konfirmasi = JOptionPane.showConfirmDialog(this, "Apakah Anda yakin ingin menghapus laporan ini secara permanen?", "Hapus Data", JOptionPane.YES_NO_OPTION);
                if (konfirmasi == JOptionPane.YES_OPTION) {
                    tableModel.removeRow(row);
                    JOptionPane.showMessageDialog(this, "Laporan pengaduan berhasil dihapus dari sistem!", "Delete Sukses", JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Pilih baris laporan yang ingin dihapus!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            }
        });

        // Logika Navigasi Keluar
        btnLogout.addActionListener(e -> {
            new LoginForm().setVisible(true);
            this.dispose();
        });
    }

    // --- 🛠️ HELPER JDIALOG: MENAMPILKAN DETAIL KRONOLOGI & BUKTI FOTO ---
    private void tampilkanDialogDetailAduanAdmin(String id, String nimMhs, String judul, String kat, String kronologi, String foto, String status) {
        JDialog dialog = new JDialog(this, "Admin Verifikator - Rincian Pengaduan " + id, true);
        dialog.setSize(520, 450);
        dialog.setLocationRelativeTo(this);
        
        JPanel pnlMain = new JPanel();
        pnlMain.setLayout(new BoxLayout(pnlMain, BoxLayout.Y_AXIS));
        pnlMain.setBorder(new EmptyBorder(20, 25, 20, 25));
        pnlMain.setBackground(Color.WHITE);

        Font fLbl = new Font("Segoe UI", Font.BOLD, 12);
        Font fVal = new Font("Segoe UI", Font.PLAIN, 13);
        
        String[] labels = {"ID Laporan & NIM Pengirim:", "Judul Keluhan Mahasiswa:", "Kategori Keluhan:", "Kronologi / Deskripsi Lengkap:", "File Bukti Lampiran:", "Status Validasi Saat Ini:"};
        String[] values = {id + "   |   Pelapor: " + nimMhs, judul, kat, kronologi, "📁 " + foto, status};

        for (int i = 0; i < labels.length; i++) {
            JLabel l = new JLabel(labels[i]); l.setFont(fLbl); l.setForeground(fontColorUtama);
            l.setAlignmentX(Component.LEFT_ALIGNMENT);
            pnlMain.add(l); pnlMain.add(Box.createVerticalStrut(4));
            
            if(i == 3) { // Area Deskripsi Kronologi
                JTextArea ta = new JTextArea(values[i]); ta.setFont(fVal); ta.setEditable(false);
                ta.setLineWrap(true); ta.setWrapStyleWord(true); ta.setBackground(new Color(248, 249, 250));
                ta.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                JScrollPane sp = new JScrollPane(ta); sp.setMaximumSize(new Dimension(470, 75));
                sp.setAlignmentX(Component.LEFT_ALIGNMENT);
                pnlMain.add(sp);
            } else {
                JLabel v = new JLabel(values[i]); v.setFont(fVal);
                v.setAlignmentX(Component.LEFT_ALIGNMENT);
                if(i == 5) { // Logika Pewarnaan Status di Dialog Admin
                    v.setFont(new Font("Segoe UI", Font.BOLD, 14));
                    if(status.equals("Pending")) v.setForeground(warnaMerah);
                    else if(status.equals("Diproses")) v.setForeground(warnaUngu);
                    else v.setForeground(warnaHijau);
                } else { v.setForeground(Color.DARK_GRAY); }
                pnlMain.add(v);
            }
            pnlMain.add(Box.createVerticalStrut(12));
        }

        JButton btnTutup = new JButton("Tutup Rincian");
        btnTutup.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnTutup.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnTutup.addActionListener(evt -> dialog.dispose());
        pnlMain.add(Box.createVerticalStrut(8));
        pnlMain.add(btnTutup);

        dialog.add(pnlMain);
        dialog.setVisible(true);
    }

    // Builder Button Samping Rata Kiri
    private JButton buatTombolAdmin(String teks) {
        JButton btn = new JButton(teks);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btn.setForeground(new Color(189, 195, 199));
        btn.setContentAreaFilled(false); btn.setFocusPainted(false);
        btn.setAlignmentX(Component.LEFT_ALIGNMENT);
        btn.setMaximumSize(new Dimension(220, 40));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(255, 255, 255, 25), 1),
            BorderFactory.createEmptyBorder(8, 15, 8, 15)
        ));
        return btn;
    }
}