package sample.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.extraClasses.Food;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class HomeController {
//    private ArrayList<Food> list = new ArrayList<>();
    private Socket client;

    @FXML
    private Label userLabel;
    @FXML
    TableView<Food> table;
    @FXML
    TableColumn<Food, String> id;
    @FXML
    TableColumn<Food, String> name;
    @FXML
    TableColumn<Food, Boolean> available;
    @FXML
    TableColumn<Food, Double> price;
    @FXML
    TableColumn<Food, String> restaurant;

    ObservableList<Food> foods = FXCollections.observableArrayList();

    @FXML
    void initialize(){
        try {
            client = new Socket("localhost", 6000);
            OutputStreamWriter o = new OutputStreamWriter(client.getOutputStream());
            BufferedWriter writer = new BufferedWriter(o);

            InputStreamReader i = new InputStreamReader(client.getInputStream());
            BufferedReader reader = new BufferedReader(i);

            writer.write("initializeHomePage\n");
            writer.flush();

            String fString = reader.readLine();
            System.out.println(fString);
            String [] parts1 = fString.split("###");
            for (String value : parts1) {
                String[] s = value.split("#");
                Food f = new Food(s[0], s[1], Boolean.parseBoolean(s[2]), Double.parseDouble(s[3]), s[4]);
                foods.add(f);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        available.setCellValueFactory(new PropertyValueFactory<>("available"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        restaurant.setCellValueFactory(new PropertyValueFactory<>("restaurant"));

        table.setItems(foods);
    }

    @FXML
    void cart(){

    }

    public void setUser(String user){
        userLabel.setText("User: "+user);
    }
}
