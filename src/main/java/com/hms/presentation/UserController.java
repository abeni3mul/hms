package com.hms.presentation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.effect.Glow;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class UserController {
    @FXML
    private VBox vboxMain;

    public void initialize() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("patientBox.fxml"));
        vboxMain.getChildren().add(loader.load());
    }

}
