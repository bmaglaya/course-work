package edu.ucsc.gameAI.conditions;

import edu.ucsc.gameAI.ICondition;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class PacmanLastMove implements ICondition {

	//Game ggame;
	MOVE mmove;
	
	public PacmanLastMove (MOVE move)
	{
		//ggame = game;
		mmove = move;
	}
	
	public boolean test(Game game) 
	{
		MOVE last = game.getPacmanLastMoveMade();
		//System.out.println(mmove);
		//System.out.println(last);
		//int res = mmove.equals(last);
		//System.out.println(mmove.equals(last));
		return (mmove.equals(last));
	}

}
