package TestingUI;

import controller.controllers.net.ClientController;
import controller.net.ClientUpdate;

import java.util.Scanner;

public class TestMainClient {

    ClientController controller;

    public TestMainClient(){
        controller = new ClientController();
    }

    public void start(){
        controller.getClient().connect("localhost");
        controller.getClient().start();
        System.out.println("Esperando respuesta del server...");
        while(controller.getServerBoard() == null){}
        System.out.println("Respuesta recibida!");
        mainLoop();
    }

    public void mainLoop(){
        Scanner s = new Scanner(System.in);
        while(true){
            String command = s.next();
            ClientUpdate.ClientAction action = new ClientUpdate.ClientAction();
            action.setActionCommand(command);
            controller.sendAction(action);
        }
    }

    public static void main(String[] args){
        TestMainClient testMainClient = new TestMainClient();
        testMainClient.start();
    }

}
