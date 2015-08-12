package edu.ucsc.gameAI;

import pacman.game.Constants.*;
import pacman.game.Game;
import edu.ucsc.gameAI.decisionTrees.binary.IBinaryNode;
import edu.ucsc.gameAI.conditions.*;

public class FollowAction implements IAction, IBinaryNode {
	
	MOVE fmove = MOVE.NEUTRAL;
	
	public void doAction() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public IAction makeDecision(Game game) {
		
		return this;
	}
	
	public MOVE getMove() {
		return fmove;
	}

	@Override
	public MOVE getMove(Game game) {
		return fmove;
	}
	
}
