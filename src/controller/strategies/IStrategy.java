package controller.strategies;

import model.Player;

public interface IStrategy {
    void reinforce(Player player);

    void attack(Player player);

    void fortify(Player player);
}