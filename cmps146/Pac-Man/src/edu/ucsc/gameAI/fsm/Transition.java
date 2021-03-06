package edu.ucsc.gameAI.fsm;

import pacman.game.Game;
import edu.ucsc.gameAI.IAction;
import edu.ucsc.gameAI.ICondition;

/**
 * The interface for transitions in finite state machines.
 * 
 * @author Josh McCoy
 */
public class Transition implements ITransition {
	
	private IState itargetState;
	private IAction tAction;
	private ICondition iCondition;
	
	/**
	 * Accesses the state that this transition leads to.
	 * @return The state this transition leads to.
	 */
	public IState getTargetState()
	{
		return itargetState;
	}
	
	/**
	 * Sets the target state of this transition.
	 * @param targetState The target state.
	 */
	public void setTargetState(IState targetState)
	{
		itargetState = targetState;
	}
	
	/**
	 * Generates the action associated with taking this transition.
	 * @return The action associated with taking this transition.
	 */
	public IAction getAction()
	{
		return tAction;
	}
	
	/**
	 * Sets the action for enacting the transition.
	 * @param action Transition action.
	 */
	public void setAction(IAction action)
	{
		tAction = action;
	}
	
	/**
	 * Sets the condition that determines if the transition is triggered.
	 * @param condition A testable condition.
	 */
	public void setCondition(ICondition condition)
	{
		iCondition = condition;
	}
	
	/**
	 * Determines if this transition is triggered.
	 * @param game TODO
	 * @return True if triggered, false if not.
	 */
	public boolean isTriggered(Game game)
	{
		return iCondition.test(game);
	}
}
