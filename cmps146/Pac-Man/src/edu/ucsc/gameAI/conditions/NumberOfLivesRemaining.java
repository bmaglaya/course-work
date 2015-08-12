package edu.ucsc.gameAI.conditions;

import edu.ucsc.gameAI.ICondition;
import pacman.game.Game;

public class NumberOfLivesRemaining implements ICondition {

	//Game ggame;
	int mmin;
	int mmax;
	
	public NumberOfLivesRemaining (int min, int max)
	{
		//ggame = game;
		mmin = min;
		mmax = max;
	}
	
	public boolean test(Game game) 
	{
		return (mmin <= game.getPacmanNumberOfLivesRemaining() &&
				game.getPacmanNumberOfLivesRemaining() <= mmax);
	}

}
