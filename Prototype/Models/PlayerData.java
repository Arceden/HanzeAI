package Models;

import Players.Player;

public class PlayerData {
    String username;
    Player controlPreference;
    String serverAddress;
    int serverPort;

    int winCount    = 0;
    int loseCount   = 0;
    int tieCount    = 0;

    public PlayerData(){
        this.username=null;
        this.controlPreference=null;
    }

    public void setControlPreference(Player controlPreference) {
        this.controlPreference = controlPreference;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public Player getControlPreference() {
        return controlPreference;
    }

    public void setServerAddress(String serverAddress) {
        this.serverAddress = serverAddress;
    }

    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }

    public String getServerAddress() {
        return serverAddress;
    }

    public int getServerPort() {
        return serverPort;
    }
}
