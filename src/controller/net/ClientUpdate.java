package controller.net;

import model.Country;
import model.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ClientUpdate extends NetPackages.Package {

    private List<ClientAction> actions;
    private Player player;

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public ClientUpdate(){
        actions = new ArrayList<>();
        player = new Player(0);
    }

    public List<ClientAction> getActions() {
        return actions;
    }

    public static class ClientAction extends NetPackages.Package {
        String actionCommand;
        Player player;

        public ClientAction() {

        }
        public ClientAction(String actionCommand, Player player) {
            this.actionCommand = actionCommand;
            this.player = player;
        }

        public String getActionCommand() {
            return actionCommand;
        }

        public void setActionCommand(String actionCommand) {
            this.actionCommand = actionCommand;
        }

        public Player getPlayer() {
            return player;
        }

        public void setPlayer(Player player) {
            this.player = player;
        }
    }
}
