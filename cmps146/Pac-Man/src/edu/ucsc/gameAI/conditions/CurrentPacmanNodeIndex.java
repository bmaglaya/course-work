package edu.ucsc.gameAI.conditions;

import edu.ucsc.gameAI.ICondition;
import pacman.game.Game;

public class CurrentPacmanNodeIndex implements ICondition {

	//Game ggame;
	int iindex;
	
	public CurrentPacmanNodeIndex (int index)
	{
		//ggame = game;
		iindex = index;
	}
	
	public boolean test(Game game) 
	{
		return (iindex == game.getPacmanCurrentNodeIndex());
	}

}
