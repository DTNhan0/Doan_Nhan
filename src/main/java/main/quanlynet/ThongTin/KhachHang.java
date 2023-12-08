package main.quanlynet.ThongTin;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import java.util.ArrayList;
import java.util.List;

public class KhachHang {
    private String USERNAME;
    private String SDT;
    private String PASSWORD;
    private boolean ROLE;
    private String HANGTHANHVIEN;

    public String getUSERNAME() {
        return USERNAME;
    }

    public void setUSERNAME(String USERNAME) {
        this.USERNAME = USERNAME;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    public String getPASSWORD() {
        return PASSWORD;
    }

    public void setPASSWORD(String PASSWORD) {
        this.PASSWORD = PASSWORD;
    }

    public boolean isROLE() {
        return ROLE;
    }

    public void setROLE(boolean ROLE) {
        this.ROLE = ROLE;
    }

    public String getHANGTHANHVIEN() {
        return HANGTHANHVIEN;
    }

    public void setHANGTHANHVIEN(String HANGTHANHVIEN) {
        this.HANGTHANHVIEN = HANGTHANHVIEN;
    }

    public int getSOPHUTDADUNG() {
        return SOPHUTDADUNG;
    }

    public void setSOPHUTDADUNG(int SOPHUTDADUNG) {
        this.SOPHUTDADUNG = SOPHUTDADUNG;
    }

    public double getMONEY() {
        return MONEY;
    }

    public void setMONEY(double MONEY) {
        this.MONEY = MONEY;
    }

    public boolean isDANGSUDUNG() {
        return DANGSUDUNG;
    }

    public void setDANGSUDUNG(boolean DANGSUDUNG) {
        this.DANGSUDUNG = DANGSUDUNG;
    }

    @Override
    public String toString() {
        return "KhachHang{" +
                "USERNAME='" + USERNAME + '\'' +
                ", SDT='" + SDT + '\'' +
                ", PASSWORD='" + PASSWORD + '\'' +
                ", ROLE=" + ROLE +
                ", HANGTHANHVIEN='" + HANGTHANHVIEN + '\'' +
                ", SOPHUTDADUNG=" + SOPHUTDADUNG +
                ", MONEY=" + MONEY +
                ", DANGSUDUNG=" + DANGSUDUNG +
                '}';
    }

    public KhachHang(String USERNAME, String SDT, String PASSWORD, boolean ROLE, String HANGTHANHVIEN, int SOPHUTDADUNG, double MONEY, boolean DANGSUDUNG) {
        this.USERNAME = USERNAME;
        this.SDT = SDT;
        this.PASSWORD = PASSWORD;
        this.ROLE = ROLE;
        this.HANGTHANHVIEN = HANGTHANHVIEN;
        this.SOPHUTDADUNG = SOPHUTDADUNG;
        this.MONEY = MONEY;
        this.DANGSUDUNG = DANGSUDUNG;
    }

    private int SOPHUTDADUNG;
    private double MONEY;
    private boolean DANGSUDUNG;

}
