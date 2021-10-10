package sample.controllers;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;

public class Review {
    private Parent parent;
    private Scene scene;
    private Stage stage;
    private Socket client;
    private BufferedWriter writer;
    private BufferedReader reader;

    @FXML
    private Label userName, fileName;

    @FXML
    private TextField reviewTf;

    @FXML
    void initialize(Socket sc, String user, String fName) throws IOException{
        reviewTf.setText("");
        userName.setText(user);
        userName.setVisible(false);

        fileName.setText(fName);
        fileName.setVisible(false);

        client = sc;
        writer = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
        reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
    }

    @FXML
    void submitBtn(ActionEvent event) throws IOException {
        System.out.println("Done");
    }
}
