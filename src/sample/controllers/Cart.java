package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;

public class Cart {

    @FXML
    public Label restName;

    @FXML
    public Label quantity;

    @FXML
    public Label foodItem;

    @FXML
    public Label foodPrice1;

    @FXML
    public Label foodPrice2;

    @FXML
    public Label cutMsg;

    @FXML
    public Label finalPrice;

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
    Food Item price = foodPrice1
    Subtotal Price = foodPrice2
    Delivery Fee = delFee
    Cutlery message = cutMsg
    Total = finalPrice
    Food Item = foodItem
    Quant. = quantity


*
*/

