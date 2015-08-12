package edu.ucsc.gameAI.conditions;

import pacman.game.Game;
import pacman.game.Constants.GHOST;
import edu.ucsc.gameAI.ICondition;

public class GhostInRegion implements ICondition {
	
	int xx1;
	int yy1;
	int xx2;
	int yy2;

	public GhostInRegion (int x1, int y1, int x2, int y2)
	{
		xx1 = x1;
		yy1 = y1;
		xx2 = x2;
		yy2 = y2;
	}
	
	public boolean test(Game game) 
	{
		int blinkyX = game.getNodeXCood(game.getGhostCurrentNodeIndex(GHOST.BLINKY));
		int blinkyY = game.getNodeYCood(game.getGhostCurrentNodeIndex(GHOST.BLINKY));
		int pinkyX = game.getNodeXCood(game.getGhostCurrentNodeIndex(GHOST.PINKY));
		int pinkyY = game.getNodeYCood(game.getGhostCurrentNodeIndex(GHOST.PINKY));
		int inkyX = game.getNodeXCood(game.getGhostCurrentNodeIndex(GHOST.INKY));
		int inkyY = game.getNodeYCood(game.getGhostCurrentNodeIndex(GHOST.INKY));
		int sueX = game.getNodeXCood(game.getGhostCurrentNodeIndex(GHOST.SUE));
		int sueY = game.getNodeYCood(game.getGhostCurrentNodeIndex(GHOST.SUE));
		
		return (xx1 <= blinkyX && yy1 <= blinkyY && xx2 >= blinkyX && yy2 >= blinkyY) ||
			   (xx1 <= pinkyX && yy1 <= pinkyY && xx2 >= pinkyX && yy2 >= pinkyY) ||
			   (xx1 <= inkyX && yy1 <= inkyY && xx2 >= inkyX && yy2 >= inkyY) ||
			   (xx1 <= sueX && yy1 <= sueY && xx2 >= sueX && yy2 >= sueY);
		
	}

}
