package model;

import java.util.Date;

public class DetailLaporan {

    private int idDetailLaporan;
    private int idLaporan;
    private String deskripsi;
    private Date tanggalUpload;
    private String buktiLaporan;

    public DetailLaporan() {
    }

    public DetailLaporan(int idDetailLaporan,
                         int idLaporan,
                         String deskripsi,
                         Date tanggalUpload,
                         String buktiLaporan) {

        this.idDetailLaporan = idDetailLaporan;
        this.idLaporan = idLaporan;
        this.deskripsi = deskripsi;
        this.tanggalUpload = tanggalUpload;
        this.buktiLaporan = buktiLaporan;
    }

    public int getIdDetailLaporan() {
        return idDetailLaporan;
    }

    public void setIdDetailLaporan(int idDetailLaporan) {
        this.idDetailLaporan = idDetailLaporan;
    }

    public int getIdLaporan() {
        return idLaporan;
    }

    public void setIdLaporan(int idLaporan) {
        this.idLaporan = idLaporan;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public Date getTanggalUpload() {
        return tanggalUpload;
    }

    public void setTanggalUpload(Date tanggalUpload) {
        this.tanggalUpload = tanggalUpload;
    }

    public String getBuktiLaporan() {
        return buktiLaporan;
    }

    public void setBuktiLaporan(String buktiLaporan) {
        this.buktiLaporan = buktiLaporan;
    }

    // getter & setter
}
