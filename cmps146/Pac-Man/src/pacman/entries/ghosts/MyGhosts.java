package pacman.entries.ghosts;

import java.util.EnumMap;

import pacman.controllers.Controller;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;
import edu.ucsc.gameAI.conditions.IsEdible;
import edu.ucsc.gameAI.decisionTrees.binary.*;
import edu.ucsc.gameAI.*;

/*
 * This is the class you need to modify for your entry. In particular, you need to
 * fill in the getActions() method. Any additional classes you write should either
 * be placed in this package or sub-packages (e.g., game.entries.ghosts.mypackage).
 */
public class MyGhosts extends Controller<EnumMap<GHOST,MOVE>>
{
	private EnumMap<GHOST, MOVE> myMoves=new EnumMap<GHOST, MOVE>(GHOST.class);
	
	public EnumMap<GHOST, MOVE> getMove(Game game, long timeDue)
	{
		myMoves.clear();
		
		for(GHOST ghost : GHOST.values())	//for each ghost
		{
			BinaryDecision edibleBinaryDecision = new BinaryDecision();
			MOVE fmove;
			
			//BLINKY always chases PacMan
			//PINKY always goes in front of PacMan (ambush)
			//INKY always guards a power pill
			//SUE camps the middle region, creating a bottleneck depending on where PacMan is
			
			
			edibleBinaryDecision.setCondition(new IsEdible(ghost));
			edibleBinaryDecision.setTrueBranch(new EvadeAction()); //flee
			edibleBinaryDecision.setFalseBranch(new FollowAction());
			
			/*
			else 
			{
				edibleBinaryDecision.setCondition(new IsEdible(ghost));
				edibleBinaryDecision.setTrueBranch(new GoLeftAction());
				edibleBinaryDecision.setFalseBranch(new GoRightAction());
			}
			*/
			if(game.doesGhostRequireAction(ghost))		//if ghost requires an action
			{
				if (edibleBinaryDecision.makeDecision(game).getClass() == GoLeftAction.class)
					myMoves.put(ghost,MOVE.LEFT);
				else if (edibleBinaryDecision.makeDecision(game).getClass() == FollowAction.class) {
					int pac = game.getPacmanCurrentNodeIndex();
					int gIndex = game.getGhostCurrentNodeIndex(ghost);
					fmove = game.getNextMoveTowardsTarget(gIndex, pac, DM.PATH);
					myMoves.put(ghost,fmove);
					//System.out.println("Blinky is following");
				}
				else if (edibleBinaryDecision.makeDecision(game).getClass() == EvadeAction.class) {
					int pac = game.getPacmanCurrentNodeIndex();
					int gIndex = game.getGhostCurrentNodeIndex(ghost);
					fmove = game.getNextMoveAwayFromTarget(gIndex, pac, DM.PATH);
					myMoves.put(ghost,fmove);
					//System.out.println("ghosts is evading");
				}
				else if (edibleBinaryDecision.makeDecision(game).getClass() == GoRightAction.class)
					myMoves.put(ghost,MOVE.RIGHT);
				
			}
		}
		
		
		return myMoves;
	}
}