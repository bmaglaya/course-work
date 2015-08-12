package edu.ucsc.gameAI;

import pacman.game.Game;
import pacman.game.Constants.MOVE;
import edu.ucsc.gameAI.decisionTrees.binary.IBinaryNode;

public class GoDownAction implements IAction, IBinaryNode {

	public void doAction() {
	}

	@Override
	public IAction makeDecision(Game game) 
	{
		return this;
	}

	@Override
	public MOVE getMove() 
	{
		return MOVE.DOWN;
	}
	
	@Override
	public MOVE getMove(Game game) 
	{
		return MOVE.DOWN;
	}
}


