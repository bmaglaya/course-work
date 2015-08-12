package edu.ucsc.gameAI.fsm;

import java.util.Collection;
import java.util.ArrayList;

import edu.ucsc.gameAI.IAction;

/**
 * The interface for a state in a finite state machine. 
 * 
 * Update is the most complicated part of the FSM implementation. See Millington
 * and Funge starting on page 312 for details.
 * 
 * @author Josh McCoy
 */
public class State implements IState {
	
	private Collection<ITransition> stateTransitions;
	private IAction entryAction;
	private IAction exitAction;
	private IAction stateAction;
	
	public State()
	{
		this.stateTransitions = new ArrayList<ITransition>();
	}
	
	/**
	 * Determines the action appropriate for being in this state. 
	 * @return Action for being in this state.
	 */
	public IAction getAction()
	{
		return stateAction;
	}
	
	/**
	 * sets the action returned while in the state.
	 * @param action action to set
	 */
	public void setAction(IAction action)
	{
		stateAction = action;
	}
		
	/**
	 * Generates the action for entering this state.
	 * @return The action associated with entering this state.
	 */
	public IAction getEntryAction()
	{
		return entryAction;
	}

	/**
	 * Sets the action for entering the state.
	 * @param action Entrance action.
	 */
	public void setEntryAction(IAction action)
	{
		entryAction = action;
	}
	
	/**
	 * Retrieves the action for exiting this state.
	 * @return The action associated with exiting this state.
	 */
	public IAction getExitAction()
	{
		return exitAction;
	}
	
	/**
	 * Sets the action for exiting the state.
	 * @param action Exit action.
	 */
	public void setExitAction(IAction action)
	{
		exitAction = action;
	}

	
	/**
	 * Accessor for all transitions out of this state.
	 * @return The outbound transitions from this state.
	 */
	public Collection<ITransition> getTransitions()
	{
		return stateTransitions;
	}
	
	/**
	 * Sets the transition collection for this state.
	 * @param trans the transitions
	 */
	public void setTransitions(Collection<ITransition> trans)
	{
		this.stateTransitions = trans;
	}
}

