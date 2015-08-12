package edu.ucsc.gameAI.conditions;

import edu.ucsc.gameAI.ICondition;
import pacman.game.Game;

public class MazeIndex implements ICondition {

	//Game ggame;
	int iindex;
	
	public MazeIndex (int index)
	{
		//ggame = game;
		iindex = index;
	}
	
	public boolean test(Game game) 
	{
		return (iindex == game.getMazeIndex());
	}

}
