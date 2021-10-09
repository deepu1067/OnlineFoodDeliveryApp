package sample.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.security.PrivateKey;

public class Checkout {
    private BufferedWriter writer;
    private BufferedReader reader;
    @FXML
    Button orderPlaceBtn;

    @FXML
    Label methodName;

    @FXML
    public ChoiceBox<String> paymentMethod;
    public String[] methods = {"Cash", "Visa", "Mastercard"};

    @FXML
    void initialize(){
        paymentMethod.getItems().addAll(methods);
        paymentMethod.setOnAction(this::getMethod);
    }

    void getMethod(javafx.event.ActionEvent actionEvent){
        String method = paymentMethod.getValue();
        methodName.setText(method);
    }

    public void orderPlaceBtn(javafx.event.ActionEvent actionEvent) {
        try {
            writer.write("Your order has been placed. Thank you!\n");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
