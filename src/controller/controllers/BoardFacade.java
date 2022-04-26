package controller.controllers;

import controller.game.MainController;
import controller.net.Board;
import model.Player;

public class BoardFacade {

    MainController controller;

    public BoardFacade(MainController controller){
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

}
