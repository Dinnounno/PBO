package model;

import java.util.Date;

public class Laporan {

    private int idLaporan;
    private String judulLaporan;
    private String status;
    private Date tanggal;
    private String idMahasiswa;
    private int idKategori;

    public Laporan() {
    }

    public Laporan(int idLaporan,
                   String judulLaporan,
                   String status,
                   Date tanggal,
                   String idMahasiswa,
                   int idKategori) {

        this.idLaporan = idLaporan;
        this.judulLaporan = judulLaporan;
        this.status = status;
        this.tanggal = tanggal;
        this.idMahasiswa = idMahasiswa;
        this.idKategori = idKategori;
    }

    public int getIdLaporan() {
        return idLaporan;
    }

    public void setIdLaporan(int idLaporan) {
        this.idLaporan = idLaporan;
    }

    public String getJudulLaporan() {
        return judulLaporan;
    }

    public void setJudulLaporan(String judulLaporan) {
        this.judulLaporan = judulLaporan;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getTanggal() {
        return tanggal;
    }

    public void setTanggal(Date tanggal) {
        this.tanggal = tanggal;
    }

    public String getIdMahasiswa() {
        return idMahasiswa;
    }

    public void setIdMahasiswa(String idMahasiswa) {
        this.idMahasiswa = idMahasiswa;
    }

    public int getIdKategori() {
        return idKategori;
    }

    public void setIdKategori(int idKategori) {
        this.idKategori = idKategori;
    }

    // getter & setter
}
