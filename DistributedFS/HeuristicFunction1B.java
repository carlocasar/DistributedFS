import aima.search.framework.HeuristicFunction;

/*
 * Transmission time of the most loaded server + (decimal part) number of servers with that time
 */

public class HeuristicFunction1B implements HeuristicFunction
{
    @Override
    public double getHeuristicValue(Object o) {
        Board board = (Board) o;
        double maxServerTime = board.getMaxServerTime();
        double maxTimeServers = board.getMaxTimeServers();
        double nServers = board.getnServers();

        return maxServerTime + (maxTimeServers/nServers);
    }
}
