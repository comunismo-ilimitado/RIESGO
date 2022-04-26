package controller.net;

import model.Player;

import java.io.IOException;
import java.io.Serializable;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;

public class Client implements Runnable{

    Thread thread;
    DatagramSocket sock;
    public static volatile ClientUpdate cu = new ClientUpdate();
    Player.PlayerConfiguration conf;
    volatile boolean running = false;
    volatile boolean sended = false;

    public Client(){
        try {
            sock = new DatagramSocket();
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public void start(){
        thread = new Thread(this);
        thread.start();
        running = true;
    }

    // Funcion de prueba de cliente
    // TODO borrar la funcion
    public static void main(String[] args){
        Client client = new Client();
        client.connect("localhost");
    }

    public void close(){
        running = false;
        sock.close();
        sock.disconnect();
    }

    public void connect(String ip){

        NetPackages.ClientInfo ci = new NetPackages.ClientInfo();
        ci.name = conf.getName();
        DatagramPacket pack = new DatagramPacket(new byte[0xfff], 0xfff);
        pack.setData(ci.getBytes());

        try {
            sock.disconnect();
            sock.connect(new InetSocketAddress(ip, Server.PORT));
            sock.send(pack);
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        sended = true;
    }

    @Override
    public void run() {

    }

}
