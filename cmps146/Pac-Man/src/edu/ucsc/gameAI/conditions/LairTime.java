package edu.ucsc.gameAI.conditions;

import edu.ucsc.gameAI.ICondition;
import pacman.game.Constants.GHOST;
import pacman.game.Game;

public class LairTime implements ICondition {

	//Game ggame;
	GHOST gghost;
	int mmin;
	int mmax;
	
	public LairTime (GHOST ghost, int min, int max)
	{
		//ggame = game;
		gghost = ghost;
		mmin = min;
		mmax = max;
	}
	
	public boolean test(Game game) 
	{
		return (mmin <= game.getGhostLairTime(gghost) &&
				game.getGhostLairTime(gghost) <= mmax);
	}

}
