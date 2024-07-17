package com.example.javafx.components;

import com.example.javafx.dao.SocioDAO;
import com.example.javafx.model.Socio;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class AddMember implements Initializable {

    private Socio socio = new Socio();

    @Autowired
    public SocioDAO socioDAO;
    @FXML
    public Label addMemberLbl, nameLbl, lastnameLbl, dniLbl;
    @FXML
    public TextField nameTxtFld, lastnameTxtFld, dniTxtFld;
    @FXML
    public Button addMemberBtn, cancelBtn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {}

    public void addMember(){
        socio.setName(nameTxtFld.getText().trim());
        socio.setLastname(lastnameTxtFld.getText().trim());
        socio.setDni(dniTxtFld.getText().trim());

        socioDAO.save(socio);
    }

}
