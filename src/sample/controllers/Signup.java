package sample.controllers;

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

import java.io.*;
import java.util.ArrayList;

public class Signup {
    ArrayList<String> uList = new ArrayList<>();

    private Parent parent;
    private Stage stage;
    private Scene scene;
    private User user;

    @FXML
    TextField fName, lName, email, username, pass, confirmPass;
    @FXML
    Label errorMessage;

    @FXML
    void initialize(){
        try{
            FileReader f = new FileReader("src/sample/server/users.txt");
            File file = new File("src/sample/server/users.txt");

            if(file.exists() && file.length() != 0){
                BufferedReader reader = new BufferedReader(f);
                String line;
                while ((line = reader.readLine()) != null) {
                    String [] parts = line.split("#");
                    uList.add(parts[3]);
                }
                reader.close();
            }
        }
        catch (Exception e){}
    }

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

    //Saving personal data except credit card info and address
    @FXML
    public void nextButton1(MouseEvent mouseEvent) throws Exception {
        user = new User();
        user.fName = fName.getText();
        user.lName = lName.getText();
        user.email = email.getText();

        if (!uList.contains(username.getText())) {
            user.username = username.getText();
            if (pass.getText().equals(confirmPass.getText())) {
                user.pass = pass.getText();
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("../ui/signup2.fxml"));
                    parent = loader.load();

                    //passing user object to the next scene controller
                    Signup2 signup2 = loader.getController();
                    signup2.personalInfo(user);

                    scene = new Scene(parent);
                    stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                errorMessage.setText("Pass does not match!!");
            }
        } else {
            errorMessage.setText("Username already exist!!");
        }
    }
}
