package edu.ucsc.gameAI.conditions;

import edu.ucsc.gameAI.ICondition;
import pacman.game.Game;

public class Score implements ICondition {

	//Game ggame;
	int mmin;
	int mmax;
	
	public Score (int min, int max)
	{
		//ggame = game;
		mmin = min;
		mmax = max;
	}
	
	public boolean test(Game game) 
	{
		return (mmin <= game.getScore() &&
				game.getScore() <= mmax);
	}

}
