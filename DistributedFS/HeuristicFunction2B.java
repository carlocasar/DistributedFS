import aima.search.framework.HeuristicFunction;

import java.util.ArrayList;

/**
 * Created by gerard.otero on 01/04/2016.
 */
public class HeuristicFunction2B implements HeuristicFunction {

    @Override
    public double getHeuristicValue(Object o) {
        Board board = (Board) o;
        double difference = board.getDifference();
        double time = board.getTotalTransmissionTime();
        return time + difference;
    }
}
