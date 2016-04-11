import aima.search.framework.HeuristicFunction;

import java.util.ArrayList;

/**
 * Only cares about Total transmission time
 */
public class HeuristicFunction2B implements HeuristicFunction {

    @Override
    public double getHeuristicValue(Object o) {
        Board board = (Board) o;
        return (double) board.getTotalTransmissionTime();
    }
}
