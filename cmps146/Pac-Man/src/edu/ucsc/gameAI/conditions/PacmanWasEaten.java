package edu.ucsc.gameAI.conditions;

import edu.ucsc.gameAI.ICondition;
import pacman.game.Game;

public class PacmanWasEaten implements ICondition {

	//Game ggame;
	
	public PacmanWasEaten ()
	{
		//ggame = game;
	}
	
	public boolean test(Game game) 
	{
		return game.wasPacManEaten();
	}

}
