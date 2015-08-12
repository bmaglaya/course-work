package edu.ucsc.gameAI.conditions;

import edu.ucsc.gameAI.ICondition;
import pacman.game.Game;

public class LevelCount implements ICondition {

	//Game ggame;
	int llevel;
	
	public LevelCount (int level)
	{
		//ggame = game;
		llevel = level;
	}
	
	public boolean test(Game game) 
	{
		return (llevel == game.getCurrentLevel());
	}

}
