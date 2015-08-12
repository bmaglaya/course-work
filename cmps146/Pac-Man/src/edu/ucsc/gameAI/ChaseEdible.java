package edu.ucsc.gameAI;

import pacman.game.Game;
import pacman.game.Constants.MOVE;
import edu.ucsc.gameAI.decisionTrees.binary.IBinaryNode;

public class ChaseEdible implements IAction, IBinaryNode {

	public void doAction() {
	}
	
	@Override
	public IAction makeDecision(Game game) {
		return this;
	}

	@Override
	public MOVE getMove() 
	{
		return MOVE.NEUTRAL;
	}
	
	@Override
	public MOVE getMove(Game game) 
	{
		return MOVE.NEUTRAL;
	}
}
