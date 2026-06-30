package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.util.Map;
import dao.MahasiswaDAO;
import dao.LaporanDAO;
import dao.DetailLaporanDAO;
import java.util.List;

public class DashboardMahasiswaForm extends JFrame {

    private String nimAktif;
    private String namaMahasiswa = "Mahasiswa FT";
    private String emailMahasiswa = "-";
    
    private JLabel lblWelcome;
    private JPanel panelKontenUtama;
    private CardLayout cardLayout;

    private JTable tabelRiwayat;
    private javax.swing.table.DefaultTableModel tableModel;
    
    // --- TEMA WARNA PREMIUM FLAT DESIGN ---
    private Color sidebarColor = new Color(39, 55, 70);       // Slate Dark Modern
    private Color bgColor = new Color(245, 246, 250);         // Abu-abu muda bersih
    private Color fontColorUtama = new Color(44, 62, 80);
    
    // Warna untuk 4 Kotak Info Statistik
    private Color warnaHijau = new Color(39, 174, 96);
    private Color warnaMerah = new Color(231, 76, 60);
    private Color warnaUngu = new Color(155, 89, 182);
    private Color warnaKuning = new Color(241, 196, 15);

    public DashboardMahasiswaForm(String nim) {
        this.nimAktif = nim;
        
        // Ambil Data Profil dari Database lewat DAO
        MahasiswaDAO mhsDAO = new MahasiswaDAO();
        Map<String, String> dataProfil = mhsDAO.ambilProfilMahasiswa(nimAktif);
        if (dataProfil != null && !dataProfil.isEmpty()) {
            this.namaMahasiswa = dataProfil.getOrDefault("nama", "Mahasiswa FT");
            this.emailMahasiswa = dataProfil.getOrDefault("email", "-");
        }

        setTitle("Portal Layanan Pengaduan Teknik Udayana");
        setSize(1000, 620);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(bgColor);

        // ==========================================
        // --- 1. SIDEBAR NAVIGATION (KIRI) ---
        // ==========================================
        JPanel panelSidebar = new JPanel();
        panelSidebar.setLayout(new BoxLayout(panelSidebar, BoxLayout.Y_AXIS));
        panelSidebar.setBackground(sidebarColor);
        panelSidebar.setPreferredSize(new Dimension(250, 620));
        panelSidebar.setBorder(new EmptyBorder(20, 15, 20, 15));

        // Header Aplikasi
        JLabel lblLogo = new JLabel("FT-PENGADUAN", JLabel.LEFT);
        lblLogo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblLogo.setForeground(Color.WHITE);
        lblLogo.setAlignmentX(Component.LEFT_ALIGNMENT); // Pastikan Rata Kiri
        panelSidebar.add(lblLogo);
        panelSidebar.add(Box.createVerticalStrut(20));

        // Komponen Mini Profil Pengguna
        JPanel panelMiniProfil = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        panelMiniProfil.setOpaque(false);
        panelMiniProfil.setMaximumSize(new Dimension(220, 50));
        panelMiniProfil.setAlignmentX(Component.LEFT_ALIGNMENT); // Rata Kiri
        
        JLabel lblAvatar = new JLabel("👤");
        lblAvatar.setFont(new Font("Segoe UI", Font.PLAIN, 28));
        lblAvatar.setForeground(Color.LIGHT_GRAY);
        
        JPanel panelTeksProfil = new JPanel(new GridLayout(2, 1, 0, 2));
        panelTeksProfil.setOpaque(false);
        JLabel lblNamaUser = new JLabel(namaMahasiswa.length() > 18 ? namaMahasiswa.substring(0, 16) + ".." : namaMahasiswa);
        lblNamaUser.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblNamaUser.setForeground(Color.WHITE);
        JLabel lblStatusMhs = new JLabel("● Online");
        lblStatusMhs.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblStatusMhs.setForeground(new Color(46, 204, 113));
        
        panelTeksProfil.add(lblNamaUser);
        panelTeksProfil.add(lblStatusMhs);
        panelMiniProfil.add(lblAvatar);
        panelMiniProfil.add(panelTeksProfil);
        
        panelSidebar.add(panelMiniProfil);
        panelSidebar.add(Box.createVerticalStrut(15));

        // Pembatas Kategori Menu
        JLabel lblNav = new JLabel("MAIN NAVIGATION");
        lblNav.setFont(new Font("Segoe UI", Font.BOLD, 10));
        lblNav.setForeground(new Color(127, 140, 141));
        lblNav.setAlignmentX(Component.LEFT_ALIGNMENT); // Rata Kiri
        lblNav.setBorder(new EmptyBorder(0, 5, 10, 0));
        panelSidebar.add(lblNav);

        // --- DAFTAR TOMBOL NAVIGASI MENU (KEMBALI ASLI & RATA KIRI) ---
        JButton btnHome = buatTombolMenuPremium(" Dashboard Utama");
        JButton btnBuatAduan = buatTombolMenuPremium(" Tulis Pengaduan");
        JButton btnRiwayat = buatTombolMenuPremium(" Riwayat Laporan");
        JButton btnProfil = buatTombolMenuPremium(" Profil Saya");
        JButton btnLogout = buatTombolMenuPremium(" Keluar Sistem");
        btnLogout.setForeground(new Color(240, 128, 128));

        panelSidebar.add(btnHome); panelSidebar.add(Box.createVerticalStrut(8));
        panelSidebar.add(btnBuatAduan); panelSidebar.add(Box.createVerticalStrut(8));
        panelSidebar.add(btnRiwayat); panelSidebar.add(Box.createVerticalStrut(8));
        panelSidebar.add(btnProfil); panelSidebar.add(Box.createVerticalGlue());
        panelSidebar.add(btnLogout); panelSidebar.add(Box.createVerticalStrut(10));

        mainPanel.add(panelSidebar, BorderLayout.WEST);

        // ==========================================
        // --- 2. KONTEN AREA CARDLAYOUT (KANAN) ---
        // ==========================================
        cardLayout = new CardLayout();
        panelKontenUtama = new JPanel(cardLayout);
        panelKontenUtama.setBackground(bgColor);
        panelKontenUtama.setBorder(new EmptyBorder(30, 30, 30, 30));

        // Halaman A: Dashboard Utama (Home)
        JPanel panelHome = new JPanel(new BorderLayout(0, 20));
        panelHome.setBackground(bgColor);
        
        lblWelcome = new JLabel("<html><span style='font-size:16px; font-weight:bold;'>Halo, " + namaMahasiswa + "</span><br><span style='color:gray;'>Selamat datang di Sistem Manajemen Informasi Pengaduan Kampus.</span></html>");
        panelHome.add(lblWelcome, BorderLayout.NORTH);
        
        LaporanDAO laporanDAO = new LaporanDAO();
        List<Map<String, Object>> daftarLaporan = laporanDAO.ambilRiwayatLaporan(nimAktif);

        int totalLaporan = daftarLaporan.size();
        int pending = 0;
        int diproses = 0;
        int selesai = 0;

        for (Map<String, Object> laporan : daftarLaporan) {
            String status = laporan.get("status").toString();

            if (status.equals("Pending")) pending++;
            else if (status.equals("Diproses")) diproses++;
            else if (status.equals("Selesai")) selesai++;
        }

        JPanel panelGridStats = new JPanel(new GridLayout(1, 4, 15, 0));
        panelGridStats.setBackground(bgColor);
        panelGridStats.setPreferredSize(new Dimension(700, 115));

        panelGridStats.add(new PanelMelengkungInfo(String.valueOf(totalLaporan), "Total Aduan", warnaHijau));
        panelGridStats.add(new PanelMelengkungInfo(String.valueOf(pending), "Pending Laporan", warnaMerah));
        panelGridStats.add(new PanelMelengkungInfo(String.valueOf(diproses), "Sedang Diproses", warnaUngu));
        panelGridStats.add(new PanelMelengkungInfo(String.valueOf(selesai), "Selesai Diproses", warnaKuning));

        panelHome.add(panelGridStats, BorderLayout.CENTER);
        
        JPanel panelSpacerBawah = new JPanel();
        panelSpacerBawah.setBackground(bgColor);
        panelSpacerBawah.setPreferredSize(new Dimension(700, 280));
        panelHome.add(panelSpacerBawah, BorderLayout.SOUTH);

        // --- PENDAFTARAN SELURUH HALAMAN AKTIF KE CARD ---
        panelKontenUtama.add(panelHome, "MENU_HOME");
        panelKontenUtama.add(buatPanelFormAduanPremium(), "MENU_ADUAN");
        panelKontenUtama.add(buatPanelRiwayatLaporanPremium(), "MENU_RIWAYAT"); 
        panelKontenUtama.add(buatPanelPengaturanProfilPremium(), "MENU_PROFIL"); // Menghubungkan ke panel profil baru

        mainPanel.add(panelKontenUtama, BorderLayout.CENTER);
        add(mainPanel);

        loadRiwayatLaporan();

        // Logika Klik Berpindah Tab Menu
        btnHome.addActionListener(e -> cardLayout.show(panelKontenUtama, "MENU_HOME"));
        btnBuatAduan.addActionListener(e -> cardLayout.show(panelKontenUtama, "MENU_ADUAN"));
        btnRiwayat.addActionListener(e -> cardLayout.show(panelKontenUtama, "MENU_RIWAYAT"));
        btnProfil.addActionListener(e -> cardLayout.show(panelKontenUtama, "MENU_PROFIL"));
        btnLogout.addActionListener(e -> {
            new LoginForm().setVisible(true);
            this.dispose();
        });
    }

    // ==========================================
    // --- 🛠️ HELPER KOMPONEN CUSTOM UI ---
    // ==========================================

    // [CREATE]: Pembuat Tampilan Halaman Input Form Pengaduan (Judul Ter-Center-kan)
    private JPanel buatPanelFormAduanPremium() {
        JPanel panel = new JPanel(new BorderLayout(0, 20));
        panel.setBackground(bgColor);
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Header Laporan di-Center-kan
        JPanel panelHeader = new JPanel(new GridLayout(2, 1, 0, 5));
        panelHeader.setBackground(bgColor);
        JLabel lblTitle = new JLabel("Buat Laporan Pengaduan Baru", JLabel.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitle.setForeground(fontColorUtama);
        JLabel lblSub = new JLabel("Sampaikan detail keluhan Anda mengenai fasilitas Kampus Teknik.", JLabel.CENTER);
        lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblSub.setForeground(Color.GRAY);
        panelHeader.add(lblTitle); panelHeader.add(lblSub);
        panel.add(panelHeader, BorderLayout.NORTH);

        // Kontainer Form
        JPanel panelForm = new JPanel();
        panelForm.setLayout(new BoxLayout(panelForm, BoxLayout.Y_AXIS));
        panelForm.setBackground(Color.WHITE);
        panelForm.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(228, 233, 237), 1),
            new EmptyBorder(25, 30, 25, 30)
        ));

        Font fontLabel = new Font("Segoe UI", Font.BOLD, 12);
        Font fontInput = new Font("Segoe UI", Font.PLAIN, 13);
        Dimension maxComponentSize = new Dimension(650, 35);

        // Input Judul
        JLabel lblJudul = new JLabel("Judul Aduan / Keluhan");
        lblJudul.setFont(fontLabel); lblJudul.setForeground(fontColorUtama);
        lblJudul.setAlignmentX(Component.LEFT_ALIGNMENT);
        JTextField txtJudul = new JTextField();
        txtJudul.setFont(fontInput); txtJudul.setMaximumSize(maxComponentSize);
        txtJudul.setAlignmentX(Component.LEFT_ALIGNMENT);
        txtJudul.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 214, 229), 1),
            BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));

        // Input Jenis
        JLabel lblJenis = new JLabel("Jenis Kategori Pengaduan");
        lblJenis.setFont(fontLabel); lblJenis.setForeground(fontColorUtama);
        lblJenis.setAlignmentX(Component.LEFT_ALIGNMENT);
        JComboBox<String> cmbJenis = new JComboBox<>(new String[]{
            "Fasilitas Kelas (AC, Proyektor, Kursi)", 
            "Fasilitas Lab & Komputer", 
            "Kebersihan Toilet / Lingkungan Kampus", 
            "Layanan Admin / Akademik",
            "Lainnya"
        });
        cmbJenis.setFont(fontInput); cmbJenis.setBackground(Color.WHITE);
        cmbJenis.setMaximumSize(maxComponentSize); cmbJenis.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Input Deskripsi
        JLabel lblDeskripsi = new JLabel("Deskripsi Kronologi Lengkap");
        lblDeskripsi.setFont(fontLabel); lblDeskripsi.setForeground(fontColorUtama);
        lblDeskripsi.setAlignmentX(Component.LEFT_ALIGNMENT);
        JTextArea txtDeskripsi = new JTextArea(6, 20);
        txtDeskripsi.setFont(fontInput); txtDeskripsi.setLineWrap(true); txtDeskripsi.setWrapStyleWord(true);
        JScrollPane scrollDeskripsi = new JScrollPane(txtDeskripsi);
        scrollDeskripsi.setMaximumSize(new Dimension(650, 120));
        scrollDeskripsi.setAlignmentX(Component.LEFT_ALIGNMENT);
        scrollDeskripsi.setBorder(BorderFactory.createLineBorder(new Color(200, 214, 229), 1));

        // Input Upload File
        JLabel lblFoto = new JLabel("Lampiran Foto Bukti");
        lblFoto.setFont(fontLabel); lblFoto.setForeground(fontColorUtama);
        lblFoto.setAlignmentX(Component.LEFT_ALIGNMENT);
        JButton btnPilihFoto = new JButton("📁 Pilih File Gambar");
        btnPilihFoto.setFont(new Font("Segoe UI", Font.PLAIN, 12)); btnPilihFoto.setCursor(new Cursor(Cursor.HAND_CURSOR));
        JLabel lblNamaFile = new JLabel("Belum ada file terpilih (Opsional)");
        lblNamaFile.setFont(new Font("Segoe UI", Font.ITALIC, 12)); lblNamaFile.setForeground(Color.GRAY);
        
        JPanel panelUpload = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        panelUpload.setBackground(Color.WHITE); panelUpload.setMaximumSize(maxComponentSize);
        panelUpload.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelUpload.add(btnPilihFoto); panelUpload.add(lblNamaFile);

        btnPilihFoto.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                lblNamaFile.setText(fileChooser.getSelectedFile().getName());
                lblNamaFile.setForeground(warnaHijau);
            }
        });

        panelForm.add(lblJudul); panelForm.add(Box.createVerticalStrut(6));
        panelForm.add(txtJudul); panelForm.add(Box.createVerticalStrut(15));
        panelForm.add(lblJenis); panelForm.add(Box.createVerticalStrut(6));
        panelForm.add(cmbJenis); panelForm.add(Box.createVerticalStrut(15));
        panelForm.add(lblDeskripsi); panelForm.add(Box.createVerticalStrut(6));
        panelForm.add(scrollDeskripsi); panelForm.add(Box.createVerticalStrut(15));
        panelForm.add(lblFoto); panelForm.add(Box.createVerticalStrut(6));
        panelForm.add(panelUpload);
        panel.add(panelForm, BorderLayout.CENTER);

        JButton btnKirim = new JButton("🚀 Kirim Laporan Pengaduan");
        btnKirim.setFont(new Font("Segoe UI", Font.BOLD, 14)); btnKirim.setForeground(Color.WHITE);
        btnKirim.setBackground(new Color(41, 128, 185)); btnKirim.setPreferredSize(new Dimension(700, 42));
        btnKirim.setFocusPainted(false); btnKirim.setBorderPainted(false); btnKirim.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnKirim.addActionListener(e -> {
            if (txtJudul.getText().trim().isEmpty() || txtDeskripsi.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Kolom Judul dan Deskripsi tidak boleh kosong!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            } else {

                LaporanDAO laporanDAO = new LaporanDAO();
                DetailLaporanDAO detailDAO = new DetailLaporanDAO();

                int idKategori = cmbJenis.getSelectedIndex() + 1;

                int idLaporan = laporanDAO.insertLaporan(
                        txtJudul.getText().trim(),
                        nimAktif,
                        idKategori
                );

                if (idLaporan > 0) {

                    boolean berhasil = detailDAO.insertDetailLaporan(
                            idLaporan,
                            txtDeskripsi.getText().trim(),
                            lblNamaFile.getText().equals("Belum ada file terpilih (Opsional)")
                                    ? null
                                    : lblNamaFile.getText()
                    );

                if (berhasil) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Laporan berhasil dikirim!",
                            "Sukses",
                            JOptionPane.INFORMATION_MESSAGE
                    );

                    dispose(); 
                    new DashboardMahasiswaForm(nimAktif).setVisible(true);

                    txtJudul.setText("");
                    txtDeskripsi.setText("");
                    cmbJenis.setSelectedIndex(0);

                    lblNamaFile.setText("Belum ada file terpilih (Opsional)");
                    lblNamaFile.setForeground(Color.GRAY);

                } else {

                        JOptionPane.showMessageDialog(
                                this,
                                "Detail laporan gagal disimpan!",
                                "Error",
                                JOptionPane.ERROR_MESSAGE
                        );

                }

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Laporan gagal disimpan!",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );

    }
}
        });
        panel.add(btnKirim, BorderLayout.SOUTH);

        return panel;
    }

    // [READ]: Pembuat Halaman Tabel Riwayat Laporan Pengaduan Dinamis
    private JPanel buatPanelRiwayatLaporanPremium() {
        JPanel panel = new JPanel(new BorderLayout(0, 20));
        panel.setBackground(bgColor);
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel panelHeader = new JPanel(new GridLayout(2, 1, 0, 5));
        panelHeader.setBackground(bgColor);
        JLabel lblTitle = new JLabel("Riwayat Laporan Pengaduan Anda", JLabel.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 18)); lblTitle.setForeground(fontColorUtama);
        JLabel lblSub = new JLabel("Klik pada salah satu baris untuk melihat detail kronologi dan status aduan.", JLabel.CENTER);
        lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 12)); lblSub.setForeground(Color.GRAY);
        panelHeader.add(lblTitle); panelHeader.add(lblSub);
        panel.add(panelHeader, BorderLayout.NORTH);

        String[] kolom = {"ID Laporan", "Tanggal", "Judul Pengaduan", "Kategori", "Status"};
        Object[][] dataAduan = {};

        tableModel = new javax.swing.table.DefaultTableModel(dataAduan, kolom) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        tabelRiwayat = new JTable(tableModel);
        tabelRiwayat.setFont(new Font("Segoe UI", Font.PLAIN, 12)); tabelRiwayat.setRowHeight(35);
        tabelRiwayat.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabelRiwayat.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        tabelRiwayat.getTableHeader().setBackground(new Color(52, 73, 94));
        tabelRiwayat.getTableHeader().setForeground(Color.WHITE);
        tabelRiwayat.setGridColor(new Color(236, 240, 241));

        tabelRiwayat.getColumnModel().getColumn(4).setCellRenderer(new javax.swing.table.DefaultTableCellRenderer() {
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

        JScrollPane scrollTabel = new JScrollPane(tabelRiwayat);
        scrollTabel.setBorder(BorderFactory.createLineBorder(new Color(228, 233, 237), 1));
        panel.add(scrollTabel, BorderLayout.CENTER);

        tabelRiwayat.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int barisTerpilih = tabelRiwayat.getSelectedRow();
                if (barisTerpilih != -1) {
                    String id = tableModel.getValueAt(barisTerpilih, 0).toString();
                    String tanggal = tableModel.getValueAt(barisTerpilih, 1).toString();
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

                    tampilkanDialogDetailAduan(id, tanggal, judul, kategori, deskripsiKronologi, namaFoto, status);
                }
            }
        });

        return panel;
    }

    private void tampilkanDialogDetailAduan(String id, String tgl, String judul, String kat, String kronologi, String foto, String status) {
        JDialog dialog = new JDialog(this, "Detail Informasi Pengaduan " + id, true);
        dialog.setSize(500, 420);
        dialog.setLocationRelativeTo(this);
        
        JPanel pnlMain = new JPanel();
        pnlMain.setLayout(new BoxLayout(pnlMain, BoxLayout.Y_AXIS));
        pnlMain.setBorder(new EmptyBorder(20, 25, 20, 25));
        pnlMain.setBackground(Color.WHITE);

        Font fLbl = new Font("Segoe UI", Font.BOLD, 12);
        Font fVal = new Font("Segoe UI", Font.PLAIN, 13);
        
        String[] labels = {"ID & Tanggal Laporan:", "Judul Keluhan:", "Kategori Keluhan:", "Kronologi Lengkap:", "Lampiran Berkas Bukti:", "Status Validasi:"};
        String[] values = {id + "   |   " + tgl, judul, kat, kronologi, "📁 " + foto, status};

        for (int i = 0; i < labels.length; i++) {
            JLabel l = new JLabel(labels[i]); l.setFont(fLbl); l.setForeground(fontColorUtama);
            l.setAlignmentX(Component.LEFT_ALIGNMENT);
            pnlMain.add(l); pnlMain.add(Box.createVerticalStrut(4));
            
            if(i == 3) {
                JTextArea ta = new JTextArea(values[i]); ta.setFont(fVal); ta.setEditable(false);
                ta.setLineWrap(true); ta.setWrapStyleWord(true); ta.setBackground(new Color(248, 249, 250));
                ta.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                JScrollPane sp = new JScrollPane(ta); sp.setMaximumSize(new Dimension(450, 70));
                sp.setAlignmentX(Component.LEFT_ALIGNMENT);
                pnlMain.add(sp);
            } else {
                JLabel v = new JLabel(values[i]); v.setFont(fVal);
                v.setAlignmentX(Component.LEFT_ALIGNMENT);
                if(i == 5) {
                    v.setFont(new Font("Segoe UI", Font.BOLD, 14));
                    if(status.equals("Pending")) v.setForeground(warnaMerah);
                    else if(status.equals("Diproses")) v.setForeground(warnaUngu);
                    else v.setForeground(warnaHijau);
                } else { v.setForeground(Color.DARK_GRAY); }
                pnlMain.add(v);
            }
            pnlMain.add(Box.createVerticalStrut(12));
        }
        JButton btnHapus = new JButton("🗑 Hapus Laporan");
        btnHapus.setCursor(new Cursor(Cursor.HAND_CURSOR));
        JButton btnTutup = new JButton("Tutup Detail");
        btnTutup.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnTutup.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnTutup.addActionListener(evt -> dialog.dispose());
        JPanel panelButton = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));
        panelButton.setBackground(Color.WHITE);

        btnHapus.setPreferredSize(new Dimension(150, 35));
        btnTutup.setPreferredSize(new Dimension(120, 35));

        panelButton.add(btnHapus);
        panelButton.add(btnTutup);

        pnlMain.add(Box.createVerticalStrut(10));
        pnlMain.add(panelButton);

        panelButton.add(btnHapus);
        panelButton.add(btnTutup);

        pnlMain.add(Box.createVerticalStrut(10));
        pnlMain.add(panelButton);;

        dialog.add(pnlMain);
        dialog.setVisible(true);
    }

    // --- 👤 TAMPILAN PROFILE SEDERHANA (ALA SIMAK-NG NYAMAN) ---
    private JPanel buatPanelPengaturanProfilPremium() {
        JPanel panel = new JPanel(new BorderLayout(0, 15));
        panel.setBackground(bgColor);
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel panelTopBar = new JPanel(new BorderLayout());
        panelTopBar.setBackground(Color.WHITE);
        panelTopBar.setBorder(new EmptyBorder(10, 15, 10, 15));
        
        JLabel lblMenuTitle = new JLabel("Profil Mahasiswa", JLabel.LEFT);
        lblMenuTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblMenuTitle.setForeground(fontColorUtama);
        
        JButton btnSimpan = new JButton("💾 Simpan Perubahan Data");
        btnSimpan.setBackground(new Color(46, 204, 113)); 
        btnSimpan.setForeground(Color.WHITE);
        btnSimpan.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnSimpan.setFocusPainted(false); btnSimpan.setBorderPainted(false);
        btnSimpan.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSimpan.addActionListener(e -> JOptionPane.showMessageDialog(this, "Perubahan profil berhasil disimpan!"));

        panelTopBar.add(lblMenuTitle, BorderLayout.WEST);
        panelTopBar.add(btnSimpan, BorderLayout.EAST);
        panel.add(panelTopBar, BorderLayout.NORTH);

        JPanel panelKontenProfil = new JPanel(new BorderLayout(25, 0));
        panelKontenProfil.setBackground(Color.WHITE);
        panelKontenProfil.setBorder(new EmptyBorder(25, 30, 25, 30));

        JPanel panelKiriAvatar = new JPanel();
        panelKiriAvatar.setLayout(new BoxLayout(panelKiriAvatar, BoxLayout.Y_AXIS));
        panelKiriAvatar.setBackground(Color.WHITE);
        
        JLabel lblBigAvatar = new JLabel("👤", JLabel.CENTER);
        lblBigAvatar.setFont(new Font("Segoe UI", Font.PLAIN, 110)); 
        lblBigAvatar.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel lblNamaBawah = new JLabel(namaMahasiswa, JLabel.CENTER);
        lblNamaBawah.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblNamaBawah.setForeground(fontColorUtama);
        lblNamaBawah.setAlignmentX(Component.CENTER_ALIGNMENT);

        panelKiriAvatar.add(lblBigAvatar);
        panelKiriAvatar.add(Box.createVerticalStrut(10));
        panelKiriAvatar.add(lblNamaBawah);
        panelKontenProfil.add(panelKiriAvatar, BorderLayout.WEST);

        JPanel panelRincianTeks = new JPanel();
        panelRincianTeks.setLayout(new BoxLayout(panelRincianTeks, BoxLayout.Y_AXIS));
        panelRincianTeks.setBackground(Color.WHITE);

        Font fLabel = new Font("Segoe UI", Font.BOLD, 11);
        Font fField = new Font("Segoe UI", Font.PLAIN, 13);
        Dimension fieldSize = new Dimension(450, 30);

        String[] dataLabels = {"NOMOR INDUK MAHASISWA (NIM)", "EMAIL MAHASISWA", "PROGRAM STUDI / FAKULTAS", "ANGKATAN"};
        String[] dataValues = {nimAktif, emailMahasiswa, "Teknologi Informasi / Fakultas Teknik", "2505551023".equals(nimAktif) ? "2025" : "2026"};

        for (int i = 0; i < dataLabels.length; i++) {
            JLabel lbl = new JLabel(dataLabels[i]);
            lbl.setFont(fLabel); lbl.setForeground(Color.GRAY);
            lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
            
            JTextField txt = new JTextField(dataValues[i]);
            txt.setFont(fField); txt.setMaximumSize(fieldSize);
            txt.setEditable(i == 1); 
            txt.setAlignmentX(Component.LEFT_ALIGNMENT);
            txt.setBackground(i == 1 ? Color.WHITE : new Color(245, 246, 250));
            
            panelRincianTeks.add(lbl);
            panelRincianTeks.add(Box.createVerticalStrut(4));
            panelRincianTeks.add(txt);
            panelRincianTeks.add(Box.createVerticalStrut(14));
        }
        
        panelKontenProfil.add(panelRincianTeks, BorderLayout.CENTER);
        panel.add(panelKontenProfil, BorderLayout.CENTER);

        return panel;
    }

    // Pembuat Komponen Tombol Menu Samping Modern (Sudah Diperbaiki Rata Kiri Sempurna)
    private JButton buatTombolMenuPremium(String teks) {
        JButton btn = new JButton(teks);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btn.setForeground(new Color(189, 195, 199));
        btn.setContentAreaFilled(false); btn.setFocusPainted(false);
        
        // Memaksa perataan komponen rata kiri di dalam BoxLayout
        btn.setAlignmentX(Component.LEFT_ALIGNMENT); 
        btn.setMaximumSize(new Dimension(220, 40)); 
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        
        btn.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(255, 255, 255, 25), 1),
            BorderFactory.createEmptyBorder(8, 15, 8, 15)
        ));
        
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setContentAreaFilled(true); btn.setBackground(new Color(52, 73, 94)); btn.setForeground(Color.WHITE);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setContentAreaFilled(false); btn.setForeground(new Color(189, 195, 199));
            }
        });
        return btn;
    }

    // Class Custom JComponent untuk Menggambar Kotak Ber-Sudut Lengkung + Ikon Orang Otomatis
    private class PanelMelengkungInfo extends JPanel {
        private String nilai, judul;
        private Color warnaDasar;

        public PanelMelengkungInfo(String nilai, String judul, Color warna) {
            this.nilai = nilai;
            this.judul = judul;
            this.warnaDasar = warna;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // 1. Menggambar Background Kotak Melengkung
            g2.setColor(warnaDasar);
            g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 12, 12));

            // 2. Menggambar Ikon Orang (👤) Semi Transparan di Pojok Kanan Atas
            g2.setColor(new Color(255, 255, 255, 50)); 
            g2.setFont(new Font("Segoe UI", Font.PLAIN, 42)); 
            g2.drawString("👤", getWidth() - 55, 48);

            // 3. Menulis Teks Angka Besar
            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Segoe UI", Font.BOLD, 28));
            g2.drawString(nilai, 20, 45);

            // 4. Menulis Teks Keterangan Label
            g2.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            g2.drawString(judul, 20, 80);

            g2.dispose();
        }
    }

        private void loadRiwayatLaporan() {

        tableModel.setRowCount(0);

        LaporanDAO laporanDAO = new LaporanDAO();

        List<Map<String, Object>> daftarLaporan =
                laporanDAO.ambilRiwayatLaporan(nimAktif);

        for (Map<String, Object> laporan : daftarLaporan) {

            tableModel.addRow(new Object[]{
                    laporan.get("id_laporan"),
                    laporan.get("tanggal"), // tanggal (sementara dikosongkan)
                    laporan.get("judul"),
                    laporan.get("kategori"),
                    laporan.get("status")
            });

        }
    }
}