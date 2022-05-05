package controller.net;

import controller.game.ServerController;
import model.Player;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Server implements Runnable{

    public static int MAX_SIZE = 0xffff;

    public static int PORT = 57565;

    private int maxplayers = 3;
    DatagramSocket sock;

    Thread thread;
    private HashMap<String, User> users;
    volatile boolean running = false;
    private volatile boolean hasUpdated = false;
    private volatile boolean needsUpdate = false;

    private volatile List<ClientUpdate> updates;

    private ServerController controller;

    public Server(ServerController controller) {
        this.controller = controller;
        users = new HashMap<>();
        thread = new Thread(this);
        updates = new ArrayList<>();
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

    public synchronized void update(){
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

    public void end(){
        running = false;
        sock.close();
    }

    public void listen(){
        DatagramPacket dp = new DatagramPacket(new byte[MAX_SIZE], MAX_SIZE);
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
                setHasUpdated(true);
            }
        }
        if(received instanceof ClientUpdate){
            ClientUpdate cu = (ClientUpdate)received;
            if(getUsers().containsKey(cu.getPlayer().getPlayerName())) {
                synchronized (this) {
                    getUpdates().add(cu);
                    if(cu.getActions().size() != 0) {
                        System.out.println("Signal received: "+cu.getActions().get(0).getActionCommand());
                        cu.getActions().get(0).setPlayer(controller.playerObjet(cu.getPlayer().getPlayerId()));
                        controller.getMyactionListener().clientActionPerformed(cu.getActions().get(0));
                    }
                    setHasUpdated(true);
                }
            }
        }

    }




    @Override
    public void run() {
        while(running) {
            listen();
            synchronized (this) {
                if (isNeedsUpdate()) {
                    setNeedsUpdate(false);
                    send();
                }
            }
        }
    }

    public HashMap<String, User> getUsers() {
        return users;
    }

    public synchronized List<ClientUpdate> getUpdates() {
        return updates;
    }

    public synchronized boolean hasUpdated() {
        return hasUpdated;
    }

    public synchronized void setHasUpdated(boolean hasUpdated) {
        this.hasUpdated = hasUpdated;
    }

    private synchronized boolean isNeedsUpdate() {
        return true;
        //return needsUpdate;
    }

    public synchronized void setNeedsUpdate(boolean needsUpdate) {
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
