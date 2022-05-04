package controller.controllers;

import controller.editor.ReadingFiles;
import controller.game.ServerController;
import controller.net.Board;
import controller.net.ClientUpdate;
import model.CardTypes;
import model.Player;

import java.util.List;

public class BoardFacade {

    ServerController controller;

    public BoardFacade(ServerController controller){
        this.controller = controller;
    }

    /**
     *  Saves an error message for a player.
     *
     * */
    public void sendErrorMessage(String errortext, Player player){
        Board.ErrorMessage msg = new Board.ErrorMessage(errortext, player);
        int id = controller.getBoard().getErrors().size();
        msg.setId(id);
        controller.getBoard().getErrors().put(id, msg);
    }

    public void copyInformation(){
        controller.getBoard().setPlayers(ReadingFiles.getPlayerId2());
        controller.getBoard().setContinents(ReadingFiles.getContinentNameObject());
        controller.getBoard().setCountries(ReadingFiles.getCountryNameObject());
        controller.getBoard().setSelectedCountry1(controller.getAttackCountry1());
        controller.getBoard().setSelectedCountry1(controller.getAttackCountry2());
        controller.getBoard().setCurrentPhase(controller.getCurrentPhase());

    }

    public void setSelectedCards(List<CardTypes> selectedCards){
        controller.getBoard().setCardTypesList(selectedCards);
    }

    public void sendServerInfo(String string){
        controller.getBoard().getServerInfo().put(controller.getBoard().getServerInfo().size(), string);
    }



    public void setMapName(String name){
        controller.getBoard().setMapName(name);
    }


    /**
     *  This function registers in the board that there has been a client action
     * */
    public void performedClientAction(ClientUpdate.ClientAction action){
        int id = controller.getBoard().getActions().size();
        controller.getBoard().getActions().put(id, action);
    }

}
