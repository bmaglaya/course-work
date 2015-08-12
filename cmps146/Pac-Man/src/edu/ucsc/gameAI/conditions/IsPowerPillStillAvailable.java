package edu.ucsc.gameAI.conditions;

import edu.ucsc.gameAI.ICondition;
import pacman.game.Game;

public class IsPowerPillStillAvailable implements ICondition {

	//Game ggame;
	int ppillIndex;
	
	public IsPowerPillStillAvailable (int pillIndex)
	{
		//ggame = game;
		ppillIndex = pillIndex;
	}
	
	public boolean test(Game game) 
	{
		return game.isPowerPillStillAvailable(ppillIndex);
	}

}
