package edu.ucsc.gameAI.conditions;
import pacman.game.Game;
import edu.ucsc.gameAI.ICondition;

public class PowerPillInRegion implements ICondition 
{
	int x1, x2, y1, y2;
	
	public PowerPillInRegion(int x1, int y1, int x2, int y2)
	{
		this.x1 = Math.min(x1, x2);
		this.x2 = Math.max(x1, x2);
		this.y1 = Math.min(y1, y2);
		this.y2 = Math.max(y1, y2);
	}
	
	@Override
	public boolean test(Game game) {
		// TODO Auto-generated method stub
		for (int pill : game.getActivePowerPillsIndices())
		{
			if (game.getNodeXCood(pill) >= this.x1 && game.getNodeXCood(pill) <= this.x2 ){
				if (game.getNodeYCood(pill) >= this.y1 && game.getNodeYCood(pill) <= this.y2 ){
					return true;
				}
			}
		}
		return false;
	}
}