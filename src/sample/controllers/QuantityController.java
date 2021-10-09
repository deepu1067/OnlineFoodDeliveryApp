package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class QuantityController {
    private String fileName;
    private BufferedWriter writer;
    private BufferedReader reader;
    private Parent parent;
    private Stage stage;
    private Scene scene;
    private Socket client;
    int boxSize;

    @FXML
    ChoiceBox<String> choiceBox;
    @FXML
    Label userName, warning;
    @FXML
    TextField quantity;

    HashMap<String, Integer> quantityMapping = new HashMap<>();

    @FXML
    void initialize(Socket socket, String name, String id) throws IOException {
        client = socket;
        userName.setText(name);
        userName.setVisible(false);
        warning.setVisible(false);

        try {
            writer = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
            reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        String [] foodArray = addedFoods(id);
        choiceBox.getItems().addAll(foodArray);
    }

    @FXML
    void goCart(ActionEvent event) throws IOException {
        String user = userName.getText();
        if(quantityMapping.size() != boxSize){
            warning.setVisible(true);
        }
        else{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../ui/cart.fxml"));
            parent = loader.load();

            Cart q = loader.getController();
            q.initialize(client, userName.getText(),price());

            q.setFileName(fileName);

            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(parent);
            stage.setScene(scene);
            stage.show();
        }
    }

    @FXML
    void setQ(){
        warning.setVisible(false);
        String food = choiceBox.getSelectionModel().getSelectedItem();
        if(!quantity.getText().equals("")){
            int qnt = Integer.parseInt(quantity.getText());
            quantity.setText("");
            quantityMapping.put(food, qnt);
        }
    }

    @FXML
    void back() throws IOException {
        System.out.println(price());
    }

    private String [] addedFoods(String id) throws IOException{
        writer.write("getFood\n");
        writer.write(id+"\n");
        writer.flush();

        String sList = reader.readLine();
        String [] p = sList.split("#");
        boxSize = p.length;
        return p;
    }

    private String price() throws IOException{
        int totalPrice = 0;
        try {
            FileWriter file ;
            for(int i=1; ;i++){
                File f = new File("src/sample/server/billings/"+i+".txt");
                if(!f.exists()){
                    file = new FileWriter("src/sample/server/billings/"+i+".txt", true);
                    fileName = i+".txt";
                    break;
                }
            }

            BufferedWriter fileWriter = new BufferedWriter(file);

            for(String food : quantityMapping.keySet()){
                writer.write("prices\n");
                writer.write(food + "\n");
                writer.flush();

                String s = reader.readLine();
                int fPrice = quantityMapping.get(food)*(Integer.parseInt(s));
                fileWriter.write("Food: " + food
                                    + "###Per Price: " + s
                                    + "###Quantity: " + quantityMapping.get(food)
                                    + "###Price: " + fPrice + "\n");

                totalPrice += fPrice;
            }

            fileWriter.close();
        }
        catch (IOException e){
            System.out.println("QuantityController" + e.getMessage());
        }
        return totalPrice+"";
    }
}
