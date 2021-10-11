package sample.controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.extraClasses.Food;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.net.Socket;
import java.util.Properties;

public class Review {
    private Parent parent;
    private Scene scene;
    private Stage stage;
    private Socket client;
    private BufferedWriter writer;
    private BufferedReader reader;

    @FXML
    private Label userName, fileName, billMsg, reviewWarning;

    @FXML
    private TextField reviewTf;

    @FXML
    void initialize(Socket sc, String user, String fName) throws IOException{
        reviewTf.setText("");
        reviewWarning.setVisible(false);
        userName.setText(user);
        userName.setVisible(false);
        billMsg.setVisible(false);
        fileName.setText(fName);
        fileName.setVisible(false);

        client = sc;
        writer = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
        reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
    }

    @FXML
    void billing() throws IOException, MessagingException {
        String fileN = fileName.getText();
        FileReader file = new FileReader("src/sample/server/billings/"+fileN);
        BufferedReader fr = new BufferedReader(file);

        String msg = "";
        String line;
        while ((line = fr.readLine()) != null){
            String [] parts = line.split("###");
            String str;
            if(parts.length>1)
                str = parts[0] +"\n" + parts[1] + "\n" + parts[2] + "\n" + parts[3] + "\n\n";
            else
                str = parts[0]+"\n\n\nIf you like our service then please submit a review";

            msg += str;
        }
        fr.close();

        writer.write("getUserEmail\n");
        writer.write(userName.getText()+"\n");
        writer.flush();

        String email = reader.readLine();

        System.out.println(email);
        sendingEmail(email, msg);
        billMsg.setVisible(true);
    }

    @FXML
    void submitBtn(ActionEvent event) throws IOException {
        if(reviewTf.getText().length()>0){
            String str = reviewTf.getText();
            writer.write("review\n");
            writer.write(userName.getText()+"\n");
            writer.write(str + "\n");
            writer.flush();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("../ui/home.fxml"));
            parent = loader.load();

            HomeController h = loader.getController();
            h.initialize(client, userName.getText());

            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(parent);
            stage.setScene(scene);
            stage.show();
        }
        else {
            reviewWarning.setVisible(true);
        }
    }

    private void sendingEmail(String emails, String bill) throws MessagingException {
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

        message.setSubject("Thanks for your order");
        message.setText(bill);
        Transport.send(message);
    }
}
