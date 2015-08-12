package edu.ucsc.gameAI.conditions;

import edu.ucsc.gameAI.ICondition;
import pacman.game.Constants.GHOST;
import pacman.game.Game;

public class CurrentGhostNodeIndex implements ICondition {

	//Game ggame;
	GHOST gghost;
	int iindex;
	
	public CurrentGhostNodeIndex (GHOST ghost, int index)
	{
		//ggame = game;
		gghost = ghost;
		iindex = index;
	}
	
	public boolean test(Game game) 
	{
		return (iindex == game.getGhostCurrentNodeIndex(gghost));
	}

}
