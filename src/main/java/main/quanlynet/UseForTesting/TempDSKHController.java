package main.quanlynet.UseForTesting;

import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import main.quanlynet.Database.MainConnection;
import main.quanlynet.ThongTin.KhachHang;

import java.math.BigDecimal;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class TempDSKHController implements Initializable {
    public boolean checkAdmin = false;
    List<KhachHang> DSKH = new ArrayList<>();
    @FXML
    private TableView <KhachHang> BangDSKH;

    @FXML
    private TableColumn<KhachHang, String> HangTVCol;

    @FXML
    private TableColumn<KhachHang, String> PassCol;

    @FXML
    private TableColumn<KhachHang, String> SdtCol;

    @FXML
    private TableColumn<KhachHang, Integer> SoPhutSDCol;

    @FXML
    private TableColumn<KhachHang, Double> SoTienCol;

    @FXML
    private TableColumn<KhachHang, Boolean> TrangThaiCol;

    @FXML
    private TableColumn<KhachHang, String> UsernameCol;

    @FXML
    private TableColumn<KhachHang, Boolean> VaiTroCol;

    @FXML
    private TextField HangTVTF;

    @FXML
    private JFXButton LamMoi;

    @FXML
    private TextField PassTF;

    @FXML
    private ToggleGroup RoleRB;

    @FXML
    private TextField SdtTF;

    @FXML
    private TextField SoTienNapTF;

    @FXML
    private JFXButton SuaTK;

    @FXML
    private TextField TaiKhoanTF;

    @FXML
    private JFXButton ThemTK;

    @FXML
    private JFXButton XoaTK;

    @FXML
    private RadioButton NutAdmin;

    @FXML
    private RadioButton NutKH;
    private void hienThiThongTinTaiKhoan(KhachHang khachHang) {
        HangTVTF.setText(khachHang.getHANGTHANHVIEN());
        PassTF.setText(khachHang.getPASSWORD());
        SdtTF.setText(khachHang.getSDT());
        SoTienNapTF.setText(String.valueOf(khachHang.getMONEY()));
        TaiKhoanTF.setText(khachHang.getUSERNAME());
        if(khachHang.isROLE()){
            NutAdmin.setSelected(true);
        }else{
            NutKH.setSelected(true);
        }

        // Ẩn nút "Thêm" và làm cho TextField "SĐT" không editable
        ThemTK.setDisable(true);
        SdtTF.setEditable(false);
        SdtTF.setDisable(true);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        BangDSKH.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                // Hiển thị thông tin tài khoản được chọn lên các TextField
                hienThiThongTinTaiKhoan(newSelection);
            }
        });
            LayDuLieuTuDBChoVaoDSKH();
            HangTVCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getHANGTHANHVIEN()));
            PassCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPASSWORD()));
            SdtCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSDT()));
            SoPhutSDCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getSOPHUTDADUNG()).asObject());
            SoTienCol.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getMONEY()).asObject());
            TrangThaiCol.setCellValueFactory(cellData -> new SimpleBooleanProperty(cellData.getValue().isDANGSUDUNG()).asObject());
            UsernameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUSERNAME()));
            VaiTroCol.setCellValueFactory(cellData -> new SimpleBooleanProperty(cellData.getValue().isROLE()).asObject());
            BangDSKH.getItems().addAll(DSKH);
    }
    public void LayDuLieuTuDBChoVaoDSKH() {
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
                DSKH.add(kh);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
      }
    private void themKhachHangVaoDB(KhachHang khachHang) {
        Platform.runLater(() -> {
            Connection con = new MainConnection().getConnection();
            PreparedStatement pstmt = null;
            boolean checkAdmin = false;
            if(NutAdmin.isSelected()){
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

            BangDSKH.getItems().add(khachHang);
        });
    }

    @FXML
    private void themTaiKhoan() {
        String username = TaiKhoanTF.getText();
        String sdt = SdtTF.getText();
        String matKhau = PassTF.getText();
        boolean vaiTro = false;
        if(NutAdmin.isSelected()){
            vaiTro = true;
        }
        String hangThanhVien = HangTVTF.getText();
        int soPhutSD = Integer.parseInt(SoTienNapTF.getText());
        double soTien = Double.parseDouble(SoTienNapTF.getText());
        boolean trangThaiSD = false;

        KhachHang khMoi = new KhachHang(username, sdt, matKhau, vaiTro, hangThanhVien, soPhutSD, soTien, trangThaiSD);

        // Thêm vào danh sách DSKH
        DSKH.add(khMoi);

        // Thêm vào cơ sở dữ liệu
        themKhachHangVaoDB(khMoi);
    }
    private void xoaKhachHangKhoiDB(KhachHang khachHang) {
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
    @FXML
    private void xoaTaiKhoan() {
        int selectedIndex = BangDSKH.getSelectionModel().getSelectedIndex();
        if (selectedIndex != -1) {
            KhachHang selectedKhachHang = BangDSKH.getItems().get(selectedIndex);

            // Hiển thị hộp thoại xác nhận trước khi xóa
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Xác nhận xóa");
            alert.setHeaderText(null);
            alert.setContentText("Bạn có chắc chắn muốn xóa tài khoản có SĐT: " + selectedKhachHang.getSDT());

            // Sử dụng Optional để xác nhận người dùng chọn OK hoặc Cancel
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    // Xóa khỏi cơ sở dữ liệu
                    xoaKhachHangKhoiDB(selectedKhachHang);

                    // Xóa khỏi danh sách DSKH
                    DSKH.remove(selectedKhachHang);

                    // Cập nhật lại TableView
                    BangDSKH.getItems().remove(selectedIndex);
                }
            });
        } else {
            showAlert("Lỗi!!!", "Vui lòng chọn một tài khoản để xóa.", Alert.AlertType.ERROR);
        }
    }
    private void showAlert(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    private void capNhatTaiKhoan() {
        int selectedIndex = BangDSKH.getSelectionModel().getSelectedIndex();
        if (selectedIndex != -1) {
            KhachHang selectedKhachHang = BangDSKH.getItems().get(selectedIndex);

            // Hiển thị hộp thoại xác nhận trước khi cập nhật
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Xác nhận cập nhật");
            alert.setHeaderText(null);
            alert.setContentText("Bạn có chắc chắn muốn cập nhật thông tin cho tài khoản có SĐT: " + selectedKhachHang.getSDT());

            // Sử dụng Optional để xác nhận người dùng chọn OK hoặc Cancel
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    // Cập nhật thông tin trong cơ sở dữ liệu
                    capNhatKhachHangTrongDB(selectedKhachHang);

                    // Cập nhật lại thông tin trong danh sách DSKH
                    capNhatThongTinTaiKhoan(selectedKhachHang);

                    // Cập nhật lại TableView
                    BangDSKH.refresh();
                }
            });
        } else {
            showAlert("Lỗi!!!", "Vui lòng chọn một tài khoản để cập nhật.", Alert.AlertType.ERROR);
        }
    }

    private void capNhatKhachHangTrongDB(KhachHang khachHang) {
        Connection con = new MainConnection().getConnection();
        PreparedStatement pstmt = null;
        boolean vaiTro = false;
        if(NutAdmin.isSelected()){
            vaiTro = true;
        }
        try {
            String sql = "UPDATE TAIKHOAN SET USERNAME=?, PASSWORD=?, ROLE=?, HANGTHANHVIEN=?, SOTIEN=? WHERE SDT=?";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, TaiKhoanTF.getText());
            pstmt.setString(2, PassTF.getText());
            pstmt.setBoolean(3, vaiTro);
            pstmt.setString(4, HangTVTF.getText());
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

    private void capNhatThongTinTaiKhoan(KhachHang khachHang) {
        boolean vaiTro = false;
        if(NutAdmin.isSelected()){
            vaiTro = true;
        }
        khachHang.setHANGTHANHVIEN(HangTVTF.getText());
        khachHang.setPASSWORD(PassTF.getText());
        khachHang.setSDT(SdtTF.getText());
        khachHang.setMONEY(Double.parseDouble(SoTienNapTF.getText()));
        khachHang.setUSERNAME(TaiKhoanTF.getText());
        khachHang.setROLE(vaiTro);
        khachHang.setSOPHUTDADUNG((int) Double.parseDouble(SoTienNapTF.getText()));
        khachHang.setDANGSUDUNG(false);

        // Cập nhật lại TableView
        BangDSKH.refresh();
    }
    public void ResetThongTinNhap(){
        HangTVTF.setText(null);
        PassTF.setText(null);
        SdtTF.setText(null);
        SoTienNapTF.setText(null);
        TaiKhoanTF.setText(null);
        NutAdmin.setSelected(false);
        NutKH.setSelected(false);
    }
}
