package main.quanlynet;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.layout.Pane;
import java.io.FileNotFoundException;
import java.net.MulticastSocket;
import java.net.URL;
public class FXMLloader {
    public Pane view;

    public Pane getPage(String filename) {
        try {
            URL fileURL = Main.class.getResource(filename);
            if(fileURL == null){
                throw new java.io.FileNotFoundException("FXMl file can't be found");
            }
            view = new FXMLLoader().load(fileURL);
        } catch (Exception e) {
            System.out.println("No page " + filename + " pls check FxmlLoader.");
        }
        return view;
    }
}

