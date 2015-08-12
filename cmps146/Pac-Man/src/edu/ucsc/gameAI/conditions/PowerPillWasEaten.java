package edu.ucsc.gameAI.conditions;

import pacman.game.Game;
import edu.ucsc.gameAI.ICondition;

public class PowerPillWasEaten implements ICondition {

	//Game ggame;
	
	public PowerPillWasEaten ()
	{
		//ggame = game;
	}
	
	public boolean test(Game game) 
	{
		return game.wasPowerPillEaten();
	}

}
