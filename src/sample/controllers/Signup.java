package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import sample.extraClasses.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Signup {
    private Parent parent;
    private Stage stage;
    private Scene scene;
    private User user;
    private CreditCard cc;
    private FileReader file;
    private BufferedReader reader;
    @FXML
    TextField fName, lName, email, username, pass, confirmPass;
    @FXML
    Label errorMessage;

    @FXML
    public void backButton(MouseEvent mouseEvent) {
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

    @FXML
    public void nextButton1(MouseEvent mouseEvent) throws IOException{
        file = new FileReader("src/sample/server/users.txt");
        reader = new BufferedReader(file);
        user = new User();
        user.fName = fName.getText();
        user.lName = lName.getText();
        user.email = email.getText();

        if(reader.readLine() == null){
            user.username = username.getText();
            if(pass.getText().equals(confirmPass.getText())){
                user.pass = pass.getText();
                try {
                    parent = FXMLLoader.load(getClass().getResource("../ui/signup2.fxml"));
                    scene = new Scene(parent);
                    stage = (Stage) ((Node)mouseEvent.getSource()).getScene().getWindow();
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else{
                errorMessage.setText("Pass does not match!!");
            }
        }

        else{

        }
    }
}
