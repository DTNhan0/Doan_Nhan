package main.quanlynet;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.Initializable;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
public class MainController implements Initializable{
    @FXML
    private JFXButton DSKHButton;

    @FXML
    private JFXButton ThongKeButton;

    @FXML
    private JFXButton StatusButton;

    @FXML
    private JFXButton KHTiemNangButton;

    @FXML
    private JFXButton DangXuatButton;

    public BorderPane MainSwitching;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
    @FXML
    public void ChonDSKH(ActionEvent event) throws IOException {
        FXMLloader object = new FXMLloader();
        Pane view = object.getPage("DSKH.fxml");
        MainSwitching.setCenter(view);
    }
    public void ChonTK(ActionEvent event) throws IOException {
        FXMLloader object = new FXMLloader();
        Pane view = object.getPage("ThongKe.fxml");
        MainSwitching.setCenter(view);
    }
}
