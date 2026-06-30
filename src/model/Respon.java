package model;

import java.util.Date;

public class Respon {

    private int idRespon;
    private int idLaporan;
    private int idAdmin;
    private String isiRespon;
    private Date tanggalRespon;

    public Respon() {
    }

    public Respon(int idRespon,
                  int idLaporan,
                  int idAdmin,
                  String isiRespon,
                  Date tanggalRespon) {

        this.idRespon = idRespon;
        this.idLaporan = idLaporan;
        this.idAdmin = idAdmin;
        this.isiRespon = isiRespon;
        this.tanggalRespon = tanggalRespon;
    }

    public int getIdRespon() {
        return idRespon;
    }

    public void setIdRespon(int idRespon) {
        this.idRespon = idRespon;
    }

    public int getIdLaporan() {
        return idLaporan;
    }

    public void setIdLaporan(int idLaporan) {
        this.idLaporan = idLaporan;
    }

    public int getIdAdmin() {
        return idAdmin;
    }

    public void setIdAdmin(int idAdmin) {
        this.idAdmin = idAdmin;
    }

    public String getIsiRespon() {
        return isiRespon;
    }

    public void setIsiRespon(String isiRespon) {
        this.isiRespon = isiRespon;
    }

    public Date getTanggalRespon() {
        return tanggalRespon;
    }

    public void setTanggalRespon(Date tanggalRespon) {
        this.tanggalRespon = tanggalRespon;
    }

    // getter & setter
}
