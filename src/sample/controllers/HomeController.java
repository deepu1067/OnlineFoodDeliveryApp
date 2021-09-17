package sample.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import sample.extraClasses.Food;

import java.util.ArrayList;

public class HomeController {
    private String user;
    private ArrayList<Food> list;
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


    ObservableList<Food> foods;

    @FXML
    void initialize(){
        list = new ArrayList<>();


    }

    public void setUser(String user){
        this.user = user;
    }
}
