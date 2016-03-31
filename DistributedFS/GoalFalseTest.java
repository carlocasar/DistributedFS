
import aima.search.framework.GoalTest;

public class GoalFalseTest implements GoalTest {

    public GoalFalseTest() {}

    public boolean isGoalState(Object aState)
    {
        return false;
    }
}

