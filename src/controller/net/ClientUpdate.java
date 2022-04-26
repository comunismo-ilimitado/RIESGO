package controller.net;

import model.Country;
import model.Player;

import java.util.HashMap;
import java.util.List;

public class ClientUpdate extends NetPackages.Package {

    private List<ClientAction> actions;
    private Player player;

    public Player getPlayer() {
        return player;
    }

    public static class ClientAction{

    }
}
