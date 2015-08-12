package edu.ucsc.gameAI.conditions;

import pacman.game.Game;
import edu.ucsc.gameAI.ICondition;

public class IsPillStillAvailable implements ICondition {

	//Game ggame;
	int ppillIndex;
	
	public IsPillStillAvailable (int pillIndex)
	{
		//ggame = game;
		ppillIndex = pillIndex;
	}
	
	public boolean test(Game game) 
	{
		return game.isPillStillAvailable(ppillIndex);
	}

}
