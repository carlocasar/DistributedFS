/*
 * Transmission time of the most loaded server + (decimal part) number of servers with that time
 */

import aima.search.framework.HeuristicFunction;

public class HeuristicFunction1B implements HeuristicFunction
{
    @Override
    public double getHeuristicValue(Object o) {
        Board board = (Board) o;
        double maxServerTime = board.getMaxServerTime();
        double maxTimeServers = board.getTotalTransmissionTime();
        double nServers = board.getnServers();

        return maxServerTime + (maxTimeServers/nServers);
    }
}
