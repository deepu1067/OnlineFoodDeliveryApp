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
    private Socket client;

    @FXML
    public Label userName, fileName, userLabel;

    @FXML
    public Label foodPrice, cutMsg, finalPrice, delFee;

    @FXML
    public RadioButton redButton;

    @FXML
    public void initialize(Socket socket, String name, String price){
        client = socket;
        userLabel.setText(name);
        userLabel.setVisible(false);
        userName.setText("Hi, "+name);
        foodPrice.setText(price);

        int totalPrice = Integer.parseInt(price) + Integer.parseInt(delFee.getText());
        finalPrice.setText(totalPrice+"");

        try {
            writer = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
            reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void paymentButton(ActionEvent actionEvent) {
        try {
            writer.write("File Name: "+fileName.getText() +
                    "     FinalPrice: "+finalPrice.getText()+"\n");
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

    void setFileName(String name){
        fileName.setText(name);
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

