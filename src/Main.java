import view.RegisterForm;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        // Menyalakan dan memunculkan form ketikan UI ke layar desktop Anda
        SwingUtilities.invokeLater(() -> {
            RegisterForm formPendaftaran = new RegisterForm();
            formPendaftaran.setVisible(true); 
        });
    }
}