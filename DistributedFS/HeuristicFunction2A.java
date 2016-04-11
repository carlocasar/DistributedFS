import aima.search.framework.HeuristicFunction;

/*
 * (Transmission time) -> mean + standard deviation
 */

public class HeuristicFunction2A implements HeuristicFunction
{
    @Override
    public double getHeuristicValue(Object o) {
        Board board = (Board) o;
        int nServers = board.getnServers();
        int TTT = board.getTotalTransmissionTime();
        double TST = board.getTotalSquareTime();

        double mean = (double) TTT / nServers;
        double variance = (TST / nServers) - (mean * mean);
        double stdev = java.lang.Math.sqrt(variance);

        return mean + stdev;
    }
}