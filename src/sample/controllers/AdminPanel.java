package sample.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import sample.extraClasses.Food;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Properties;

public class AdminPanel {
    private Socket client;
    private BufferedWriter writer;
    private BufferedReader reader;
    private Parent parent;
    private Scene scene;
    private Stage stage;
    private ArrayList<Food> newAdded = new ArrayList<>();
    private boolean notified = false;

    @FXML
    private Label emailSent;

    @FXML
    private Rectangle shape;

    @FXML
    private TextField id;

    @FXML
    private TextField name;

    @FXML
    private TextField price;

    @FXML
    private TextField restaurant;

    @FXML
    TableView<Food> table;
    @FXML
    TableColumn<Food, String> idf;
    @FXML
    TableColumn<Food, String> namef;
    @FXML
    TableColumn<Food, Boolean> availablef;
    @FXML
    TableColumn<Food, Double> pricef;
    @FXML
    TableColumn<Food, String> restaurantf;

    ObservableList<Food> foods = FXCollections.observableArrayList();

    @FXML
    void initialize(Socket sc) throws IOException {
        client = sc;
        emailSent.setVisible(false);
        shape.setVisible(false);
        writer = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
        reader = new BufferedReader(new InputStreamReader(client.getInputStream()));

        writer.write("initializeHomePage\n");
        writer.flush();

        String fString = reader.readLine();
        String [] parts1 = fString.split("###");
        for (String value : parts1) {
            String[] s = value.split("#");
            Food f = new Food(s[0], s[1], Boolean.parseBoolean(s[2]), Double.parseDouble(s[3]), s[4]);
            foods.add(f);
        }

        idf.setCellValueFactory(new PropertyValueFactory<>("id"));
        namef.setCellValueFactory(new PropertyValueFactory<>("name"));
        availablef.setCellValueFactory(new PropertyValueFactory<>("available"));
        pricef.setCellValueFactory(new PropertyValueFactory<>("price"));
        restaurantf.setCellValueFactory(new PropertyValueFactory<>("restaurant"));

        table.setItems(foods);
    }

    @FXML
    void add(ActionEvent event) throws IOException{
        String sID = id.getText();
        String sName = name.getText();
        String sPrice = price.getText();
        String sRestaurant = restaurant.getText();

        String line = sID + "#" + sName+"#"+ "true" + "#" + sPrice + "#" + sRestaurant;
        foods.add(new Food(sID, sName, true, Double.parseDouble(sPrice), sRestaurant));
        newAdded.add(new Food(sID, sName, true, Double.parseDouble(sPrice), sRestaurant));
        table.setItems(foods);

        writer.write("adminAdd\n");
        writer.write(line + "\n");
        writer.flush();
    }

    @FXML
    void deleteItem(ActionEvent event) throws IOException{
        Food selectedItem = table.getSelectionModel().getSelectedItem();
        table.getItems().remove(selectedItem);

        String sID = selectedItem.getId();
        String sName = selectedItem.getName();
        String sPrice = selectedItem.getPrice()+"";
        String sRestaurant = selectedItem.getRestaurant();

        String line = sID + "#" + sName+"#"+ "true" + "#" + sPrice + "#" + sRestaurant;

        writer.write("deleting\n");
        writer.write(line+"\n");
        writer.flush();
    }

    @FXML
    void home(ActionEvent event) throws IOException, MessagingException {
        if(notified || newAdded.size() == 0){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../ui/main.fxml"));
            parent = loader.load();

            scene = new Scene(parent);
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
        else{
            emailSent.setText("Press notify then wait a few seconds");
            emailSent.setVisible(true);
            shape.setVisible(true);
        }
    }

    @FXML
    void notifyUser() throws IOException, MessagingException {
        if(newAdded.size() != 0){
            writer.write("getEmails\n");
            writer.flush();
            String emails = reader.readLine();
            sendingEmail(emails);
            emailSent.setText("The list of the new arrivals has been send to all users");
            emailSent.setVisible(true);
            shape.setVisible(true);
            notified = true;
        }
        else{
            emailSent.setText("Nothing to notify the users");
            emailSent.setVisible(true);
            shape.setVisible(true);
        }
    }
    private void sendingEmail(String emails) throws MessagingException {

        String gUsername = "foodo5999@gmail.com";
        String password = "AOOP159753";

        Properties properties = System.getProperties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(gUsername, password);
            }
        });

        MimeMessage message = new MimeMessage(session);
        message.setFrom(gUsername);

        message.addRecipients(Message.RecipientType.TO, InternetAddress.parse(emails));

        message.setSubject("Hey checkout the new item in our list");
        String msg = "";
        int count=1;

        for(Food f: newAdded){
            String str = count + ". Name: " + f.getName()
                        +"   Restaurant name: " + f.getRestaurant()+"\n\n";
            msg += str;
            count++;
        }

        msg = msg + "That's all of the new arrivals. To order this delicious item login now";
        message.setText(msg);
        Transport.send(message);
    }
}
