package sample.controllers;

import sample.extraClasses.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import sample.extraClasses.User;

import java.io.*;

public class Signup3 {
    public User user;
    private Parent parent;
    private Stage stage;
    private Scene scene;

    @FXML
    Label successMsg;
    @FXML
    TextField stAds, tAds, dAds, divAds;

    public void setCcInfo(User user){
        this.user = user;
    }

    @FXML
    public void backButton3(MouseEvent mouseEvent){
        try {
            FXMLLoader loader = FXMLLoader.load(getClass().getResource("../ui/main.fxml"));
            parent  = loader.load();
            scene = new Scene(parent);
            stage = (Stage) ((Node)mouseEvent.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Saving address
    @FXML
    public void submit(MouseEvent mouseEvent){
        String street = stAds.getText();
        String thana = tAds.getText();
        String district = dAds.getText();
        String division = divAds.getText();

        user.address = street + "-" + thana + "-" + district + "-" + division;
        successMsg.setText("Account create successful");

        try {
            FileWriter f = new FileWriter("src/sample/server/users.txt", true);
            BufferedWriter writer = new BufferedWriter(f);
            String str = user.fName+"#"+user.lName+"#"+user.email+"#"+
                    user.username + "#" + user.pass + "#" + user.address + "#"+
                    user.ccNum + "#" + user.validity;

            writer.write(str);
            writer.newLine();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            parent = FXMLLoader.load(getClass().getResource("../ui/main.fxml"));
            scene = new Scene(parent);
            stage = (Stage) ((Node)mouseEvent.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
