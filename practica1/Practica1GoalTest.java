//como acaba
package practica1;

import aima.search.framework.GoalTest;

public class CentralsEstatFinal implements GoalTest {

    public boolean isGoalState(Object state) {
        return ((Practica1Board)(state)).isGoalState();

    }

}
