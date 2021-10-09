package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;

public class Cart {
    private Parent parent;
    private Scene scene;
    private Stage stage;
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

    public void paymentButton(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../ui/checkout.fxml"));
        parent = loader.load();

        Checkout c = loader.getController();
        c.initialize(client, userLabel.getText(), finalPrice.getText());
        c.setFileName(fileName.getText());

        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();
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