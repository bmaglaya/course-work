package edu.ucsc.gameAI.conditions;

import edu.ucsc.gameAI.ICondition;
import pacman.game.Game;

public class PowerPillAvailable implements ICondition {
       
       int numPPills;
       
       public PowerPillAvailable()
       {
               
       }
       
       public boolean test(Game game) 
       {
               numPPills = game.getNumberOfActivePowerPills();
               if (numPPills == 0) return false;
               else return true;
       }
       
}
