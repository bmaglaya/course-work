package pacman.entries.pacman;

import pacman.controllers.Controller;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

import java.util.ArrayList;
import java.util.EnumMap;

import pacman.controllers.Controller;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;
import edu.ucsc.gameAI.conditions.*;
import edu.ucsc.gameAI.decisionTrees.binary.*;
import edu.ucsc.gameAI.*;

/*
 * This is the class you need to modify for your entry. In particular, you need to
 * fill in the getAction() method. Any additional classes you write should either
 * be placed in this package or sub-packages (e.g., game.entries.pacman.mypackage).
 */
public class MyPacMan extends Controller<MOVE>
{
	private MOVE myMove = MOVE.NEUTRAL;
	
	public MOVE getMove(Game game, long timeDue) 
	{
		int pac = game.getPacmanCurrentNodeIndex();
		int pacX = game.getNodeXCood(pac);
		int pacY = game.getNodeYCood(pac);
		
		BinaryDecision edibleBinaryDecision = new BinaryDecision();
		BinaryDecision powerPillCount = new BinaryDecision();
		BinaryDecision pPillInRegion = new BinaryDecision();
		BinaryDecision ghostInRegion = new BinaryDecision();
		
		for (GHOST ghost: GHOST.values())
			edibleBinaryDecision.setCondition(new IsEdible(ghost));
		edibleBinaryDecision.setTrueBranch(new ChaseEdible());
		edibleBinaryDecision.setFalseBranch(powerPillCount);
		
		powerPillCount.setCondition(new PowerPillAvailable());
		powerPillCount.setTrueBranch(pPillInRegion);
		powerPillCount.setFalseBranch(new GetPills());
		
		pPillInRegion.setCondition(new PowerPillInRegion(pacX-20,pacY-20,pacX+20,pacY+20));
		pPillInRegion.setTrueBranch(ghostInRegion);
		pPillInRegion.setFalseBranch(new GoNearestPPill());
		
		ghostInRegion.setCondition(new GhostInRegion(pacX-10,pacY-10,pacX+10,pacY+10));
		ghostInRegion.setTrueBranch(new GetPowerPill());
		ghostInRegion.setFalseBranch(new WaitAction());
		
		
		if (edibleBinaryDecision.makeDecision(game).getClass() == ChaseEdible.class) {
			int minDistance=Integer.MAX_VALUE;
			GHOST minGhost=null;		
			
			for(GHOST ghost : GHOST.values())
				if(game.getGhostEdibleTime(ghost)>0)
				{
					int distance=game.getShortestPathDistance(pac,game.getGhostCurrentNodeIndex(ghost));
					
					if(distance<minDistance)
					{
						minDistance=distance;
						minGhost=ghost;
					}
				}
			
			if(minGhost!=null)	//we found an edible ghost
				return game.getNextMoveTowardsTarget(game.getPacmanCurrentNodeIndex(),game.getGhostCurrentNodeIndex(minGhost),DM.PATH);
			
		} else if (powerPillCount.makeDecision(game).getClass() == GetPills.class) {
			int[] pills=game.getPillIndices();	
			
			ArrayList<Integer> targets=new ArrayList<Integer>();
			
			for(int i=0;i<pills.length;i++)					//check which pills are available			
				if(game.isPillStillAvailable(i))
					targets.add(pills[i]);

			int[] targetsArray=new int[targets.size()];		//convert from ArrayList to array
			
			for(int i=0;i<targetsArray.length;i++)
				targetsArray[i]=targets.get(i);
			
			//return the next direction once the closest target has been identified
			return game.getNextMoveTowardsTarget(pac,game.getClosestNodeIndexFromNodeIndex(pac,targetsArray,DM.PATH),DM.PATH);
			
		} else if (pPillInRegion.makeDecision(game).getClass() == GoNearestPPill.class) {
			int[] powerPills=game.getPowerPillIndices();		
			
			ArrayList<Integer> targets=new ArrayList<Integer>();

			for(int i=0;i<powerPills.length;i++)			//check with power pills are available
				if(game.isPowerPillStillAvailable(i))
					targets.add(powerPills[i]);				
			
			int[] targetsArray=new int[targets.size()];		//convert from ArrayList to array
			
			for(int i=0;i<targetsArray.length;i++)
				targetsArray[i]=targets.get(i);
			
			//return the next direction once the closest target has been identified
			return game.getNextMoveTowardsTarget(pac,game.getClosestNodeIndexFromNodeIndex(pac,targetsArray,DM.PATH),DM.PATH);
	
		} else if (ghostInRegion.makeDecision(game).getClass() == GetPowerPill.class) {
			
			//get all active power pills
			int[] activePowerPills=game.getActivePowerPillsIndices();
			
			//create a target array that includes all ACTIVE pills and power pills
			int[] targetNodeIndices=new int[activePowerPills.length];
			
			for(int i=0;i<activePowerPills.length;i++)
				targetNodeIndices[i]=activePowerPills[i];		
			
			//return the next direction once the closest target has been identified
			return game.getNextMoveTowardsTarget(game.getPacmanCurrentNodeIndex(),game.getClosestNodeIndexFromNodeIndex(pac,targetNodeIndices,DM.PATH),DM.PATH);
	
		} else {
			return MOVE.NEUTRAL;
		}
		
		return myMove;
	}
}