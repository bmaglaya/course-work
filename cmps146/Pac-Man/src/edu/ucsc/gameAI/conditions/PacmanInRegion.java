package edu.ucsc.gameAI.conditions;

import pacman.game.Game;
import edu.ucsc.gameAI.ICondition;

public class PacmanInRegion implements ICondition {
	
	int xx1;
	int yy1;
	int xx2;
	int yy2;

	public PacmanInRegion (int x1, int y1, int x2, int y2)
	{
		xx1 = x1;
		yy1 = y1;
		xx2 = x2;
		yy2 = y2;
	}
	
	public boolean test(Game game) 
	{
		int ipac = game.getPacmanCurrentNodeIndex();
		int pacx = game.getNodeXCood(ipac);
		int pacy = game.getNodeYCood(ipac);
		return (xx1 <= pacx && yy1 <= pacy && xx2 >= pacx && yy2 >= pacy);
	}

}
