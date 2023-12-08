package main.quanlynet.Database;

import javafx.application.Platform;
import main.quanlynet.ThongTin.KhachHang;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DSKHDAO {
    List <KhachHang> DSkh = new ArrayList<>();

    public List<KhachHang> getDSkh() {
        return DSkh;
    }

    public void setDSkh(List<KhachHang> DSkh) {
        this.DSkh = DSkh;
    }

    public List <KhachHang> layKhachHangtuDBS() {

        Connection con = new MainConnection().getConnection();
        Statement stmt = null;

        try {
            stmt = con.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        String sql = "SELECT * FROM TAIKHOAN";
        try {
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String username = rs.getString(1);
                String sdt = rs.getString(2);
                String matKhau = rs.getString(3);
                boolean vaiTro = rs.getBoolean(4);
                String hangThanhVien = rs.getString(5);
                int soPhutSD = rs.getInt(6);
                double soTien = rs.getDouble(7);
                boolean trangThaiSD = rs.getBoolean(8);

                KhachHang kh = new KhachHang(username, sdt, matKhau, vaiTro, hangThanhVien,
                        soPhutSD, soTien, trangThaiSD);
                DSkh.add(kh);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return DSkh;
    }
    public void themKhachHangVaoDB(KhachHang khachHang, boolean NutAdmin) {
        Platform.runLater(() -> {
            Connection con = new MainConnection().getConnection();
            PreparedStatement pstmt = null;
            boolean checkAdmin = false;
            if(NutAdmin){
                checkAdmin = true;
            }
            try {
                String sql = "INSERT INTO TAIKHOAN (USERNAME, SDT, PASSWORD, ROLE, HANGTHANHVIEN, SOPHUTDADUNG, SOTIEN, DANGSUDUNG) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                pstmt = con.prepareStatement(sql);
                pstmt.setString(1, khachHang.getUSERNAME());
                pstmt.setString(2, khachHang.getSDT());
                pstmt.setString(3, khachHang.getPASSWORD());
                pstmt.setBoolean(4, checkAdmin);
                pstmt.setString(5, khachHang.getHANGTHANHVIEN());
                pstmt.setInt(6, 0);

                BigDecimal soTienBigDecimal = BigDecimal.valueOf(khachHang.getMONEY());
                pstmt.setBigDecimal(7, soTienBigDecimal);

                pstmt.setBoolean(8, false);
                pstmt.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } finally {
                // Đóng PreparedStatement ở đây nếu cần
                try {
                    if (pstmt != null) {
                        pstmt.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            DSkh.add(khachHang);
//            BangDSKH.getItems().add(khachHang);
        });
    }
    public void xoaKhachHangKhoiDB(KhachHang khachHang) {
        Platform.runLater(() -> {
            Connection con = new MainConnection().getConnection();
            PreparedStatement pstmt = null;

            try {
                String sql = "DELETE FROM TAIKHOAN WHERE USERNAME = ?";
                pstmt = con.prepareStatement(sql);
                pstmt.setString(1, khachHang.getUSERNAME());
                pstmt.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } finally {
                // Đóng PreparedStatement ở đây nếu cần
                try {
                    if (pstmt != null) {
                        pstmt.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    public void capNhatKhachHangTrongDB(KhachHang khachHang, String TaiKhoanTF, String PassTF, String HangTVTF,boolean NutAdmin) {
        Connection con = new MainConnection().getConnection();
        PreparedStatement pstmt = null;
        boolean vaiTro = false;
        if(NutAdmin){
            vaiTro = true;
        }
        try {
            String sql = "UPDATE TAIKHOAN SET USERNAME=?, PASSWORD=?, ROLE=?, HANGTHANHVIEN=?, SOTIEN=? WHERE SDT=?";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, TaiKhoanTF);
            pstmt.setString(2, PassTF);
            pstmt.setBoolean(3, vaiTro);
            pstmt.setString(4, HangTVTF);
            BigDecimal soTienBigDecimal = BigDecimal.valueOf(khachHang.getMONEY());
            pstmt.setBigDecimal(5, soTienBigDecimal);
            pstmt.setString(6, khachHang.getSDT());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
