package controller;
import model.*;
public interface IStrategy {
	public void reinforce(Player player);
	public void attack(Player player);
	public void fortify(Player player);
}