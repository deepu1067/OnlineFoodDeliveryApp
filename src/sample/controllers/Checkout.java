package sample.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;

import java.io.*;
import java.net.Socket;

public class Checkout {
    private Socket client;
    private BufferedWriter writer;
    private BufferedReader reader;

    @FXML
    Label finalPrice, userName, fileName;

    @FXML
    ChoiceBox<String> paymentMethod;

    @FXML
    void initialize(Socket sc, String user, String price) throws IOException {
        client = sc;
        userName.setText(user);
        userName.setVisible(false);
        finalPrice.setText(price);

        writer = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
        reader = new BufferedReader(new InputStreamReader(client.getInputStream()));

        String [] methods = {"Cash on delivery", "Visa", "Mastercard"};
        paymentMethod.getItems().addAll(methods);
    }

    public void orderPlaceBtn(javafx.event.ActionEvent actionEvent) {
        String method = paymentMethod.getSelectionModel().getSelectedItem();
        if(method != null){
            System.out.println(method);
        }
        else{
            System.out.println(method);
        }
    }

    void setFileName(String file){
        fileName.setText(file);
        fileName.setVisible(false);
    }
}
