package com.hms.presentation;

import com.hms.business.User;
import com.hms.business.UserTypes;
import com.hms.exceptions.InvalidIDException;
import com.hms.exceptions.UnexpectedErrorException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.controlsfx.control.action.Action;

import java.io.IOException;

public class LoginController {
    @FXML
    private BorderPane bpMainParent;
    @FXML
    private ComboBox<String> cbxUserType;
    @FXML
    private TextField txtUserId;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private Parent userRoot;

    public void initialize(){
        cbxUserType.getItems().addAll("Manager", "Doctor", "Nurse");
    }
    @FXML
    protected void onLogin(ActionEvent e){
        if(txtUserId.getText().trim().equals("") || txtPassword.getText().trim().equals("") || cbxUserType.getValue().equals("")){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Invalid Input");
            alert.setContentText("User type, user id password field are required.");
            alert.showAndWait();
            return;
        }

        UserTypes userType;
        if(cbxUserType.getValue().equals("Doctor"))
            userType = UserTypes.DOCTOR;
        else if(cbxUserType.getValue().equals("Manager"))
            userType = UserTypes.MANAGER;
        else
            userType = UserTypes.NURSE;

        try {
            boolean credentialsMatch = new User(userType, Integer.parseInt(txtUserId.getText().trim()), txtPassword.getText().trim()).login();

            if(!credentialsMatch)
                throw new InvalidIDException("Invalid user id or password.");

            txtUserId.setText("");
            txtPassword.setText("");
            cbxUserType.setValue("");

            String fxmlPath = "user.fxml";

            if(userType == UserTypes.MANAGER)
                fxmlPath = "manager.fxml";

            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            userRoot = loader.load();

            UserController userController = loader.getController();

            Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
            Scene scene = new Scene(userRoot);
            stage.setScene(scene);
            stage.show();

        }
        catch (NumberFormatException error){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Invalid Input");
            alert.setContentText("User id is not valid. Please, make sure user id is a number.");
            alert.showAndWait();
        }
        catch (InvalidIDException error){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Login Failed");
            alert.setContentText(error.getMessage());
            alert.showAndWait();
        }
        catch (Exception error){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Unexpected Error");
            alert.setContentText(error.getMessage());
            error.printStackTrace();
            alert.showAndWait();
        }

    }
}
