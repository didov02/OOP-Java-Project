package client.app;

import java.net.URL;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import server.app.CallBack;
import server.app.CallBackImpl;
import server.app.Get15Interface;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;

public class Get15ClientSceneControler {

    private String currentPlayer;

    private boolean myTurn = false;

    private Get15Interface game;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField txtActionsField;

    @FXML
    private TextField txtInput;

    @FXML
    private TextField txtOpponentNumbers;

    @FXML
    private TextField txtYourNumbers;

    @FXML
    private Button btnAdd;

    @FXML
    void btnAddClicked(ActionEvent event) throws RemoteException {
        int number = Integer.parseInt(txtInput.getText());
        String player = currentPlayer;
        game.myChoice(number,player);
    }

    @FXML
    void initialize() {
        assert btnAdd != null : "fx:id=\"btnAdd\" was not injected: check your FXML file 'Get15ClientScene.fxml'.";
        assert txtActionsField != null : "fx:id=\"txtActionsField\" was not injected: check your FXML file 'Get15ClientScene.fxml'.";
        assert txtInput != null : "fx:id=\"txtInput\" was not injected: check your FXML file 'Get15ClientScene.fxml'.";
        assert txtOpponentNumbers != null : "fx:id=\"txtOpponentNumbers\" was not injected: check your FXML file 'Get15ClientScene.fxml'.";
        assert txtYourNumbers != null : "fx:id=\"txtYourNumbers\" was not injected: check your FXML file 'Get15ClientScene.fxml'.";

        try {
            initializeRMI();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    protected boolean initializeRMI() throws Exception {
        String host = "";

        try {
            Registry registry = LocateRegistry.getRegistry(host);
            game = (Get15Interface) registry.lookup("Get15Impl");
            System.out.println("Server object " + game + " found");
        } catch (Exception ex) {
            System.out.println(ex);
        }

        CallBackImpl callBackControl = new CallBackImpl(this);

        if ( (currentPlayer = game.connect((CallBack) callBackControl)) != " ") {
            System.out.println("connected as " + currentPlayer);
            return true;
        } else {
            System.out.println("already two players connected as ");
            return false;
        }
    }

    public void choose(int number, String otherNumbers)
    {
        if(currentPlayer.equals("First Player"))
        {
            txtYourNumbers.appendText((number + ", "));
            txtOpponentNumbers.setText(otherNumbers);

            if(otherNumbers.equals(""))
                setMessage(String.format("First player choose number: %d  ", number));
            else
                txtActionsField.appendText(String.format("First player choose number: %d  ", number));
        }
        else
        {
            txtOpponentNumbers.appendText((number + ", "));
            txtYourNumbers.setText(otherNumbers);
            txtActionsField.appendText(String.format("Second player choose number: %d  ", number));
        }
    }

    public void setMessage(String message)
    {
        Platform.runLater(()-> txtActionsField.setText(message));
    }

    public void setMyTurn(boolean turn)
    {
        Platform.runLater(()->this.myTurn = turn);
    }
}

