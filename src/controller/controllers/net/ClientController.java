package controller.controllers.net;

import TestingUI.GameContainer;
import TestingUI.MapController;
import controller.net.Board;
import controller.net.Client;
import controller.net.ClientUpdate;
import model.Player;

public class ClientController {

    private Client client;
    private volatile Board serverBoard;
    private ClientUpdate clientUpdate;
    Player.PlayerConfiguration conf;

    private int lastActionId;

    private MapController mapController;

    public ClientController(){
        this.client = new Client(this);
        clientUpdate = new ClientUpdate();
        conf = new Player.PlayerConfiguration();
    }

    public void setMapController(MapController mapController) {
        this.mapController = mapController;
    }

    public MapController getMapController() {
        return mapController;
    }

    public Client getClient() {
        return client;
    }

    public Board getServerBoard() {
        return serverBoard;
    }

    public void updatePlayer(){
        Player player =  getServerBoard().getPlayers().values()
                        .stream().filter(pla -> pla.getPlayerName().equals(getPlayerConfiguration().getName()))
                        .findFirst().orElse(null);
        getClientUpdate().getPlayer().setPlayerName(player.getPlayerName());
        getClientUpdate().getPlayer().setPlayerId(player.getPlayerId());
    }

    public void sendAction(ClientUpdate.ClientAction action) {
        clientUpdate.getActions().add(action);
        client.send();
    }


    public void exit(){
        client.end();
    }


    public void update(){
        if(mapController != null)
            mapController.update();
    }

    public void actionPerformed(ClientUpdate.ClientAction action){
        System.out.println(action.getActionCommand());
    }

    public synchronized void setServerBoard(Board serverBoard) {
        this.serverBoard = serverBoard;
    }

    public ClientUpdate getClientUpdate() {
        return clientUpdate;
    }

    public Player.PlayerConfiguration getPlayerConfiguration() {
        return conf;
    }
}
