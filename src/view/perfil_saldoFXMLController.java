/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package view;

import businessLogic.BusinessLogicException;
import businessLogic.ClienteManager;
import static businessLogic.ClienteManagerFactory.createClienteManager;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import transferObjects.ClienteBean;


/**
 *
 * @author Sergio
 */
public class perfil_saldoFXMLController{
    private static final Logger LOGGER = Logger.getLogger("escritorio.view.Perfil_saldo");
    private ClienteManager clientLogic = createClienteManager("REAL");
    private Stage stage;
    private ClienteBean user;
    @FXML private TextField txtSaldo;
    @FXML private Button btnAceptarSaldo; 
    @FXML private Button btnCancelarSaldo; 
    public void setUser(ClienteBean user){this.user = user;}
    public ClienteBean getUser(){return user;}
    public void setStage(Stage stage){this.stage = stage;}
    public void initStage(Parent root){
        txtSaldo.setOnKeyPressed(this::keyPress);
        btnAceptarSaldo.setOnKeyPressed(this::keyPress);
        btnCancelarSaldo.setOnKeyPressed(this::keyPress);
    }
    /**
     * Actualiza el saldo del cliente
     */
    @FXML public void aceptarSaldo(){
        LOGGER.info("He ingresado mi saldo");
        Alert alertIngresarSaldo = new Alert(Alert.AlertType.CONFIRMATION,"Seguro de ingresar esta cantidad?.",ButtonType.NO,ButtonType.YES);
        alertIngresarSaldo.setTitle("Ingresar Saldo");
        alertIngresarSaldo.setHeaderText("¿Quieres ingresar saldo?.");
        alertIngresarSaldo.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                try {
                    user.setSaldo(user.getSaldo()+Float.valueOf(txtSaldo.getText()));
                    clientLogic.edit(user);
                } catch (BusinessLogicException ex) {
                    Logger.getLogger(perfil_saldoFXMLController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        stage.hide();
    }
    @FXML public void cancelarSaldo(){
        LOGGER.info("He cancelado mi saldo");
        stage.hide();
    }
    /**
     * Atajos
     * @param key
     */
    public void keyPress(KeyEvent key){
        if(key.getCode().equals(KeyCode.ENTER)) {
            if(txtSaldo.isFocused() || btnAceptarSaldo.isFocused())
                aceptarSaldo();
            else
                stage.hide();
        }else if(key.getCode().equals(KeyCode.ESCAPE))
            stage.hide();
    }
}
