package controller.net;

import controller.game.MainController;
import jdk.tools.jmod.Main;
import model.Player;

import java.io.IOException;
import java.net.*;
import java.util.HashMap;
import java.util.List;

public class Server implements Runnable{

    public static int PORT = 57565;

    private int maxplayers = 3;
    DatagramSocket sock;

    Thread thread;
    private HashMap<String, User> users;
    volatile boolean running = false;
    private volatile boolean hasUpdated = false;
    private volatile boolean needsUpdate = false;

    private volatile List<ClientUpdate> updates;

    private MainController controller;

    public Server(MainController controller) {
        this.controller = controller;
        users = new HashMap<>();
        thread = new Thread(this);
        try {
            sock = new DatagramSocket(PORT);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public void start(){
        running = true;
        thread.start();
    }

    public void update(){
        needsUpdate = true;
    }

    private Board getBoard(){
        return controller.getBoard();
    }

    public void send(){
        Board boardUpdate = getBoard();
        byte[] b = boardUpdate.getBytes();
        DatagramPacket dp = new DatagramPacket(b, b.length);

        for(User user : users.values()){
            dp.setSocketAddress(user.addr);
            try {
                sock.send(dp);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void listen(){
        DatagramPacket dp = new DatagramPacket(new byte[1024], 1024);
        Object received = null;
        try {
            sock.receive(dp);
            received = NetPackages.Package.bytesToObject(dp.getData());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        if(received instanceof NetPackages.ClientInfo){
            NetPackages.ClientInfo ci = (NetPackages.ClientInfo)received;
            if(users.size() < maxplayers && !users.containsKey(ci.name)){
                User user = new User(ci.name, dp.getSocketAddress());
                user.lastresp = System.currentTimeMillis();
                users.put(ci.name, user);
                System.out.println("User connected: "+ci.name);
            }
        }
        if(received instanceof ClientUpdate){
            ClientUpdate cu = (ClientUpdate)received;
            if(getUsers().containsKey(cu.getPlayer().getPlayerName())) {
                getUpdates().add(cu);
                setHasUpdated(true);
            }
        }

    }




    @Override
    public void run() {
        while(running) {
            listen();
            if (isNeedsUpdate()) {
                send();
                setNeedsUpdate(false);
            }
        }
    }

    public HashMap<String, User> getUsers() {
        return users;
    }

    public List<ClientUpdate> getUpdates() {
        return updates;
    }

    public boolean hasUpdated() {
        return hasUpdated;
    }

    public void setHasUpdated(boolean hasUpdated) {
        this.hasUpdated = hasUpdated;
    }

    private boolean isNeedsUpdate() {
        return needsUpdate;
    }

    public void setNeedsUpdate(boolean needsUpdate) {
        this.needsUpdate = needsUpdate;
    }

    public static class User{
        public String name;
        public SocketAddress addr;
        public Player.PlayerConfiguration conf;
        public long lastresp;

        public User(String name, SocketAddress addr) {
            this.name = name;
            this.addr = addr;
        }
    }

}
