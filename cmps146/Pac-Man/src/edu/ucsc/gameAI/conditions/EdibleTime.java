package edu.ucsc.gameAI.conditions;

import edu.ucsc.gameAI.ICondition;
import pacman.game.Constants.GHOST;
import pacman.game.Game;

public class EdibleTime implements ICondition {

	//Game ggame;
	GHOST gghost;
	int mmin;
	int mmax;
	
	public EdibleTime (GHOST ghost, int min, int max)
	{
		//ggame = game;
		gghost = ghost;
		mmin = min;
		mmax = max;
	}
	
	public boolean test(Game game) 
	{
		return (mmin <= game.getGhostEdibleTime(gghost) &&
				game.getGhostEdibleTime(gghost) <= mmax);
	}

}
