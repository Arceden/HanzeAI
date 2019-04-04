package Controllers;

import Models.PlayerData;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class LoginController {

    PlayerData playerData;

    @FXML
    private TextField tfAddress;
    @FXML
    private TextField tfPort;

    public void setPlayerData(PlayerData playerData) {
        this.playerData = playerData;
    }

    @FXML
    void connect(){
        playerData.setServerAddress(tfAddress.getText());
        playerData.setServerPort(Integer.parseInt(tfPort.getText()));

        System.out.println("Connecting to "+playerData.getServerAddress()+":"+playerData.getServerPort());
    }

}
