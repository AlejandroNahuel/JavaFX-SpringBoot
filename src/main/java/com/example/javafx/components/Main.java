package com.example.javafx.components;

import com.example.javafx.JavaFxApplication;
import com.example.javafx.dao.SocioDAO;
import com.example.javafx.model.Socio;
import com.example.javafx.model.User;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
@Component
public class Main implements Initializable {

    private Socio socio;

    @Autowired
    private SocioDAO socioDAO;
    //Etiqueta raíz del archivo FXML
    @FXML
    public AnchorPane root;
    @FXML
    public Label title;
    @FXML
    public Label welcome;
    @FXML
    public Label noInfoLbl;
    @FXML
    public TextField searchTxtFld;
    @FXML
    public ImageView memberImage;
    @FXML
    public Label memberNumber;
    @FXML
    public Label memberName;
    @FXML
    public Label memberExpiricy;
    @FXML
    public Label cuotaLabel;
    @FXML
    public Button searchBtn;
    @FXML
    public Button actualizarCuotaBtn;
    @FXML
    public Button limpiarBtn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {Platform.runLater(this::retrieveData);}
    @FXML
    public void retrieveData(){
        Stage stage = (Stage) root.getScene().getWindow();
        User authenticatedUser = (User) stage.getUserData();

        String name = authenticatedUser.getName();

        welcome.setText("Bienvenido, " + name);

        //Image img = new Image(getClass().getResourceAsStream("/img/default-user.jpeg"));
        //if(img != null) System.out.println("Imagen encontrada");
        //memberImage.setImage(img);
    }
    @FXML
    public void searchMember(){
        socio = socioDAO.findSocioByDni(searchTxtFld.getText().trim());

        //System.out.println("Nombre socio: " + socio.getName());

        if(socio != null){
            noInfoLbl.setVisible(false);
            memberNumber.setText("Número de socio: " + socio.getId());
            memberNumber.setVisible(true);
            memberName.setText("Nombre: " + socio.getName() + " " + socio.getLastname());
            memberName.setVisible(true);
            memberExpiricy.setText("Fecha de Vencimiento: " + socio.getFechaVenc().format(DateTimeFormatter.ofPattern("dd MMM u")));
            memberExpiricy.setVisible(true);

            if(socio.isMembershipExpired()){
                cuotaLabel.setText("CUOTA VENCIDA");
                cuotaLabel.setTextFill(Color.WHITE);
                cuotaLabel.setBackground(new Background(new BackgroundFill(Color.RED, null, null)));
            }
            else{
                cuotaLabel.setText("CUOTA AL DIA");
                cuotaLabel.setTextFill(Color.WHITE);
                cuotaLabel.setBackground(new Background(new BackgroundFill(Color.GREEN, null, null)));
            }

            cuotaLabel.setVisible(true);
            actualizarCuotaBtn.setVisible(true);
            limpiarBtn.setVisible(true);
        }
    }
    @FXML
    public void clear(){
        limpiarBtn.setVisible(false);
        actualizarCuotaBtn.setVisible(false);
        noInfoLbl.setVisible(true);
        memberNumber.setVisible(false);
        memberName.setVisible(false);
        memberExpiricy.setVisible(false);
        cuotaLabel.setVisible(false);
    }

    @FXML
    public void addNewMember() throws IOException {

        //Recuperamos el Stage para setear una nueva escena
        Stage stage = (Stage) root.getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AddMember.fxml"));
        loader.setControllerFactory(JavaFxApplication.getContext()::getBean);
        Scene scene = new Scene(loader.load());

        stage.setScene(scene);

    }
}
