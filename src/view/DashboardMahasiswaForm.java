package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.util.Map;
import dao.MahasiswaDAO;

public class DashboardMahasiswaForm extends JFrame {

    private String nimAktif;
    private String namaMahasiswa = "Mahasiswa FT";
    private String emailMahasiswa = "-";
    
    private JLabel lblWelcome;
    private JPanel panelKontenUtama;
    private CardLayout cardLayout;

    // Tema Warna Premium (Sesuai Preferensi Desain Flat)
    private Color sidebarColor = new Color(39, 55, 70);       // Slate Dark modern
    private Color bgColor = new Color(245, 246, 250);         // Abu-abu muda bersih
    private Color fontColorUtama = new Color(44, 62, 80);
    
    // Warna untuk 4 Kotak Info Statistik
    private Color warnaHijau = new Color(39, 174, 96);
    private Color warnaMerah = new Color(231, 76, 60);
    private Color warnaUngu = new Color(155, 89, 182);
    private Color warnaKuning = new Color(241, 196, 15);

    public DashboardMahasiswaForm(String nim) {
        this.nimAktif = nim;
        
        // Ambil Data Profil
        MahasiswaDAO mhsDAO = new MahasiswaDAO();
        Map<String, String> dataProfil = mhsDAO.ambilProfilMahasiswa(nimAktif);
        if (dataProfil != null && !dataProfil.isEmpty()) {
            this.namaMahasiswa = dataProfil.getOrDefault("nama", "Mahasiswa FT");
            this.emailMahasiswa = dataProfil.getOrDefault("email", "-");
        }

        setTitle("Portal Layanan Pengaduan Teknik Udayana");
        setSize(1000, 620); // Sedikit dilebarkan untuk menampung 4 kotak horizontal
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(bgColor);

        // ==========================================
        // --- A. SIDEBAR PREMIUM (KIRI) ---
        // ==========================================
        JPanel panelSidebar = new JPanel();
        panelSidebar.setLayout(new BoxLayout(panelSidebar, BoxLayout.Y_AXIS));
        panelSidebar.setBackground(sidebarColor);
        panelSidebar.setPreferredSize(new Dimension(250, 620));
        panelSidebar.setBorder(new EmptyBorder(20, 15, 20, 15));

        // Header Aplikasi
        JLabel lblLogo = new JLabel("BUDAYABALI PANEL", JLabel.CENTER);
        lblLogo.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblLogo.setForeground(Color.WHITE);
        lblLogo.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelSidebar.add(lblLogo);
        panelSidebar.add(Box.createVerticalStrut(20));

        // --- MINI PROFIL DI SIDEBAR (Mengikuti gaya contoh gambar 2) ---
        JPanel panelMiniProfil = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        panelMiniProfil.setOpaque(false);
        panelMiniProfil.setMaximumSize(new Dimension(220, 50));
        panelMiniProfil.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Simbol Ava Bulat Sederhana memakai Teks
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
        lblStatusMhs.setForeground(new Color(46, 204, 113)); // Hijau terang
        
        panelTeksProfil.add(lblNamaUser);
        panelTeksProfil.add(lblStatusMhs);
        panelMiniProfil.add(lblAvatar);
        panelMiniProfil.add(panelTeksProfil);
        
        panelSidebar.add(panelMiniProfil);
        panelSidebar.add(Box.createVerticalStrut(15));

        // Label Pembatas Navigasi
        JLabel lblNav = new JLabel("MAIN NAVIGATION");
        lblNav.setFont(new Font("Segoe UI", Font.BOLD, 10));
        lblNav.setForeground(new Color(127, 140, 141));
        lblNav.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblNav.setBorder(new EmptyBorder(0, 10, 10, 0));
        panelSidebar.add(lblNav);

        // Tombol Navigasi Menu Ber-Outline Indah
        JButton btnHome = buatTombolMenuPremium("🏠  Home");
        JButton btnBuatAduan = buatTombolMenuPremium("📝  Add Post / Aduan");
        JButton btnRiwayat = buatTombolMenuPremium("📂  Drafts & History");
        JButton btnProfil = buatTombolMenuPremium("👤  Profile User");
        JButton btnLogout = buatTombolMenuPremium("🚪  Sign Out");
        btnLogout.setForeground(new Color(240, 128, 128));

        panelSidebar.add(btnHome); panelSidebar.add(Box.createVerticalStrut(8));
        panelSidebar.add(btnBuatAduan); panelSidebar.add(Box.createVerticalStrut(8));
        panelSidebar.add(btnRiwayat); panelSidebar.add(Box.createVerticalStrut(8));
        panelSidebar.add(btnProfil); panelSidebar.add(Box.createVerticalGlue());
        panelSidebar.add(btnLogout); panelSidebar.add(Box.createVerticalStrut(10));

        mainPanel.add(panelSidebar, BorderLayout.WEST);

        // ==========================================
        // --- B. KONTEN UTAMA (CARDLAYOUT KANAN) ---
        // ==========================================
        cardLayout = new CardLayout();
        panelKontenUtama = new JPanel(cardLayout);
        panelKontenUtama.setBackground(bgColor);
        panelKontenUtama.setBorder(new EmptyBorder(30, 30, 30, 30));

        // Halaman Utama (Home)
        JPanel panelHome = new JPanel(new BorderLayout(0, 20));
        panelHome.setBackground(bgColor);
        
        lblWelcome = new JLabel("<html><span style='font-size:16px; font-weight:bold;'>Halo, " + namaMahasiswa + "</span><br><span style='color:gray;'>Selamat datang di Sistem Manajemen Informasi Pengaduan Kampus.</span></html>");
        panelHome.add(lblWelcome, BorderLayout.NORTH);
        
        // SUSUNAN 4 KOTAK INFO HORIZONTAL (Sesuai gambar contoh ke-2)
        JPanel panelGridStats = new JPanel(new GridLayout(1, 4, 15, 0));
        panelGridStats.setBackground(bgColor);
        panelGridStats.setPreferredSize(new Dimension(700, 115));
        
        panelGridStats.add(new PanelMelengkungInfo("2", "Total Aduan", warnaHijau));
        panelGridStats.add(new PanelMelengkungInfo("0", "Pending Laporan", warnaMerah));
        panelGridStats.add(new PanelMelengkungInfo("0", "Draft Aduan", warnaUngu));
        panelGridStats.add(new PanelMelengkungInfo("0", "Selesai Diproses", warnaKuning));
        
        panelHome.add(panelGridStats, BorderLayout.CENTER);
        
        // Sebagai penyeimbang layout bawah agar kotak tidak memanjang ke bawah terlalu ekstrem
        JPanel panelSpacerBawah = new JPanel();
        panelSpacerBawah.setBackground(bgColor);
        panelSpacerBawah.setPreferredSize(new Dimension(700, 280));
        panelHome.add(panelSpacerBawah, BorderLayout.SOUTH);

        // Pendaftaran Halaman ke Card
        panelKontenUtama.add(panelHome, "MENU_HOME");
        panelKontenUtama.add(new JLabel("Halaman Form Pengaduan Aktif", JLabel.CENTER), "MENU_ADUAN");
        panelKontenUtama.add(new JLabel("Halaman Riwayat Pengaduan", JLabel.CENTER), "MENU_RIWAYAT");
        panelKontenUtama.add(new JLabel("Halaman Pengaturan Profil", JLabel.CENTER), "MENU_PROFIL");

        mainPanel.add(panelKontenUtama, BorderLayout.CENTER);
        add(mainPanel);

        // Aksi Perpindahan Halaman
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

    // Pembuat Tombol Menu Sidebar Ber-Outline Tipis Modern
    private JButton buatTombolMenuPremium(String teks) {
        JButton btn = new JButton(teks);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btn.setForeground(new Color(189, 195, 199));
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setMaximumSize(new Dimension(220, 40));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        
        // Garis batas outline tipis semi transparan di sekeliling tombol menu
        btn.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(255, 255, 255, 25), 1),
            BorderFactory.createEmptyBorder(8, 15, 8, 15)
        ));
        
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setContentAreaFilled(true);
                btn.setBackground(new Color(52, 73, 94));
                btn.setForeground(Color.WHITE);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setContentAreaFilled(false);
                btn.setForeground(new Color(189, 195, 199));
            }
        });
        return btn;
    }

    // Class Custom JComponent untuk Menggambar Kotak Ber-Sudut Lengkung (Rounded Corner Card)
    private class PanelMelengkungInfo extends JPanel {
        private String nilai, judul;
        private Color warnaDasar;

        public PanelMelengkungInfo(String nilai, String judul, Color warna) {
            this.nilai = nilai;
            this.judul = judul;
            this.warnaDasar = warna;
            setOpaque(false); // Mematikan background kotak kaku bawaan swing
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            // Menyalakan Anti-Aliasing agar lekukan halus & tidak pecah-pecah/bergerigi
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Menggambar Bentuk Kotak dengan Lekukan Sudut Sebesar 12 Piksel
            g2.setColor(warnaDasar);
            g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 12, 12));

            // Menulis Teks Angka Besar di Dalam Kartu
            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Segoe UI", Font.BOLD, 28));
            g2.drawString(nilai, 20, 45);

            // Menulis Teks Keterangan Label di Bawah Angka
            g2.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            g2.drawString(judul, 20, 80);

            g2.dispose();
        }
    }
}