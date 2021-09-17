package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;

public class Cart {

    @FXML
    public Label cutMsg;

    @FXML
    public RadioButton redButton;


    public void paymentButton(ActionEvent actionEvent) {
    }

    public void cutRedBtn(ActionEvent actionEvent) {
        if(redButton.isSelected()){
            cutMsg.setText("Cutlery will be provided by the restaurant, if it is available.");
        }
    }
}
/*
Comments
*
    fx:id
    Restaurants name = resName
    Subtotal Price = foodPrice
    Delivery Fee = delFee
    Cutlery message = cutMsg
    Total = finalPrice

*
*/

