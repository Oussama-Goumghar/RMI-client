package UI_Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


public class LoginController implements Initializable{
    
    @FXML private AnchorPane MAIN_FRM;
    @FXML private Button LOGIN_BTN;
    @FXML private TextField USERNAME_TXT;

    @FXML
    public void getLogin(ActionEvent e){
        LOGIN_BTN.getScene().getWindow().hide();
        
        try{
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/UI/ContactsUI.fxml"));
            Parent root = loader.load();

            root.getStylesheets().add(getClass().getResource("/CSS/ContactsUI_CSS.css").toExternalForm());

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Secret Chat !");
            ContactsController controller = loader.getController();
            controller.setName(USERNAME_TXT.getText());
            stage.show();
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    } 
}
