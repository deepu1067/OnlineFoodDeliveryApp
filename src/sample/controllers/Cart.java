package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;

import java.io.*;
import java.net.Socket;

public class Cart {
    private BufferedWriter writer;
    private BufferedReader reader;

    @FXML
    public Label userName;

    @FXML
    public Label foodPrice, cutMsg, finalPrice;

    @FXML
    public RadioButton redButton;

    @FXML
    public void initialize(Socket socket, String name){
        Socket client = socket;
        userName.setText("Hi, "+name);
        try {
            writer = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
            reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void paymentButton(ActionEvent actionEvent) {
        try {
            writer.write("okay\n");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cutRedBtn(ActionEvent actionEvent) {
        if(redButton.isSelected()){
            cutMsg.setText("Cutlery will be provided by the restaurant, if it is available.");
        }
        else{
            cutMsg.setText("Cutlery will not be provided by the restaurant");
        }
    }
}
/*
Comments
*
    fx:id
    Restaurants name = resName
    Food Item price = foodPrice1
    Subtotal Price = foodPrice2
    Delivery Fee = delFee
    Cutlery message = cutMsg
    Total = finalPrice
    Food Item = foodItem
    Quant. = quantity


*
*/

