package edu.ucsc.gameAI.conditions;

import edu.ucsc.gameAI.ICondition;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class GhostLastMove implements ICondition {

	//Game ggame;
	GHOST gghost;
	MOVE mmove;
	
	public GhostLastMove (GHOST ghost, MOVE move)
	{
		//ggame = game;
		gghost = ghost;
		mmove = move;
	}
	
	public boolean test(Game game) 
	{
		return (mmove == game.getGhostLastMoveMade(gghost));
	}

}
