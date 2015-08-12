package edu.ucsc.gameAI.fsm;

import java.util.Collection;
import java.util.LinkedList;

import pacman.game.Game;
import edu.ucsc.gameAI.IAction;

/**
 * The interface for a state machine. This Interface must be extended for 
 * state machine implementations to interface wit automatic testing and 
 * grading. 
 * @author Josh McCoy
 *
 */
public class StateMachine implements IStateMachine {
	
	private IState currentState;
	
	public StateMachine()
	{
		
	}
	
	/**
	 * The member function that performs the update on the FSM:
	 * - Test transitions for current state and moves to new state.
	 * - Returns a collection of IActions that result from the current
	 *   state and any transitions, entrances and exits that may occur.
	 * @return A collection of actions produced by evaluating the FSM.
	 */
	public Collection<IAction> update(Game game)
	{
		final LinkedList<IAction> actions = new LinkedList<IAction>();
		boolean bTransTrig = false;
		for (ITransition trans : currentState.getTransitions())
		{
			if (trans.isTriggered(game))
			{
				bTransTrig = true;
				if (currentState.getExitAction() != null)
					actions.add(currentState.getExitAction());
				if (trans.getAction() != null)
					actions.add(trans.getAction());
				currentState = trans.getTargetState();
				if (currentState.getEntryAction() != null)
					actions.add(currentState.getEntryAction());
				break;	
			}
		}
		
		if (!bTransTrig)
			actions.add(currentState.getAction());
			
		return actions;
	}
	
	/**
	 * Retrieves the current state of the finite state machine.
	 * @return The current state of the finite state machine.
	 */
	public IState getCurrentState()
	{
		return currentState;
	}
	
	/**
	 * Sets the current state of fsm. For setting initial state.
	 */
	public void setCurrentState(IState state)
	{
		currentState = state;
	}
}
