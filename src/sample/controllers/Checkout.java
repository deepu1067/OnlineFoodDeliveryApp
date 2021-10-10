package sample.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;

public class Checkout {
    private Parent parent;
    private Scene scene;
    private Stage stage;
    private Socket client;
    private BufferedWriter writer;
    private BufferedReader reader;

    @FXML
    Label finalPrice, userName, fileName, address;

    @FXML
    ChoiceBox<String> paymentMethod;

    @FXML
    void initialize(Socket sc, String user, String price) throws IOException {
        client = sc;
        userName.setText(user);
        userName.setVisible(false);
        finalPrice.setText(price);

        writer = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
        reader = new BufferedReader(new InputStreamReader(client.getInputStream()));

        String [] methods = {"Cash on delivery", "Visa", "Mastercard"};
        paymentMethod.getItems().addAll(methods);
        getAddress(userName.getText());
    }

    public void orderPlaceBtn(javafx.event.ActionEvent actionEvent) throws IOException{
        String method = paymentMethod.getSelectionModel().getSelectedItem();
        if(method != null){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../ui/review.fxml"));
            parent = loader.load();

            Review r = loader.getController();
            r.initialize(client, userName.getText(), fileName.getText());

            stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
            scene = new Scene(parent);
            stage.setScene(scene);
            stage.show();
        }
        else{
            System.out.println(method);
        }
    }

    void setFileName(String file){
        fileName.setText(file);
        fileName.setVisible(false);
    }

    private void getAddress(String user) throws IOException{
        writer.write("userAddress\n");
        writer.write(user+"\n");
        writer.flush();

        String adrs = reader.readLine();
        String [] parts = adrs.split("-");
        address.setText(String.join(", ", parts));
    }
}
