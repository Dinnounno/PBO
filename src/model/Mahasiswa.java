package model;

public class Mahasiswa {

    private String idMahasiswa;
    private String nama;
    private String email;
    private String password;

    public Mahasiswa() {
    }

    public Mahasiswa(String idMahasiswa, String nama,
                     String email, String password) {
        this.idMahasiswa = idMahasiswa;
        this.nama = nama;
        this.email = email;
        this.password = password;
    }

    public String getIdMahasiswa() {
        return idMahasiswa;
    }

    public void setIdMahasiswa(String idMahasiswa) {
        this.idMahasiswa = idMahasiswa;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
