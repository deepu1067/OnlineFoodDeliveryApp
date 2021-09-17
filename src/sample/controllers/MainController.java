package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class MainController {
    private HashMap<String, String> mapping = new HashMap<>();
    private Parent parent;
    private Scene scene;
    private Stage stage;
    @FXML
    TextField username, password;

    @FXML
    Label errorUsername, errorPass;

    @FXML
    void initialize(){
        addToMap();
    }

    @FXML
    void signup(ActionEvent event) throws IOException {
        parent = FXMLLoader.load(getClass().getResource("../ui/signup.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void login(ActionEvent event) throws IOException {
        String u = username.getText();
        String p = password.getText();

        if(mapping.containsKey(u)) {
            errorUsername.setText("");
            String mPass = mapping.get(u);
            if(mPass.equals(p)) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../ui/home.fxml"));
                parent = loader.load();
                HomeController home = loader.getController();
                home.setUser(u);
                scene = new Scene(parent);
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            }
            else {
                errorPass.setText("Incorrect Password");
            }
        }
        else{
            errorUsername.setText("Username not found");
        }
    }

    private void addToMap(){
        try{
            FileReader f = new FileReader("src/sample/server/users.txt");
            File file = new File("src/sample/server/users.txt");

            if(file.exists() && file.length() != 0){
                BufferedReader reader = new BufferedReader(f);
                String line;
                while ((line = reader.readLine()) != null) {
                    String [] parts = line.split("#");
                    mapping.put(parts[3], parts[4]);
                }
                reader.close();
            }
        }
        catch (Exception e){
            System.out.println("file not found");
        }
    }
}
