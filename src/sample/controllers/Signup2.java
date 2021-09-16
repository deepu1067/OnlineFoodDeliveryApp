package sample.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import sample.extraClasses.User;

import java.io.IOException;

public class Signup2 {
    private Scene scene;
    private Stage stage;
    private Parent parent;

    @FXML
    TextField cardNum, expDate;

    private User user;

    //Saving credit card info
    @FXML
    public void nextButton2(MouseEvent mouseEvent){
        user.ccNum = cardNum.getText();
        user.validity = expDate.getText();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../ui/signup3.fxml"));
            parent = loader.load();

            //passing creditCard info to the next scene controller
            Signup3 signup3 = loader.getController();
            signup3.setCcInfo(user);

            scene = new Scene(parent);
            stage = (Stage) ((Node)mouseEvent.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void backButton2(MouseEvent mouseEvent) {
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

    //getting data from the previous scene controller
    public void personalInfo(User user){
        this.user = user;

    }
}
