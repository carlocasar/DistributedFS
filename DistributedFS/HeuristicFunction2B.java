import aima.search.framework.HeuristicFunction;

import java.util.ArrayList;

/**
 * Created by gerard.otero on 01/04/2016.
 */
public class HeuristicFunction2B implements HeuristicFunction {

    @Override
    public double getHeuristicValue(Object o) {
        Board board = (Board) o;
        int maxtime = board.getTotalTransmissionTime();
        ArrayList<Integer> servs = board.getServerTimes();
        int size = servs.size();
        double mean = 0;
        for (int i = 0; i < size; ++i){
            mean += servs.get(i);
        }
        mean = mean/size;
        return maxtime + mean;
    }
}
