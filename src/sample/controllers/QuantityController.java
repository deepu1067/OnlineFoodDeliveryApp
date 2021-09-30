package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;

public class QuantityController {
    private BufferedWriter writer;
    private BufferedReader reader;
    private Parent parent;
    private Stage stage;
    private Scene scene;
    @FXML
    ComboBox comboBox;
    @FXML
    Label userName;


    @FXML
    void initialize(Socket socket, String name, String id) throws IOException {
        Socket client = socket;
        userName.setText(name);
        userName.setVisible(false);
        try {
            writer = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
            reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        writer.write("priceList\n");
        writer.write(id+"\n");
        writer.flush();


    }

    @FXML
    void goCart(ActionEvent event) throws IOException {
        String user = userName.getText();

    }
}
