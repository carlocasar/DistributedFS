
import aima.search.framework.HeuristicFunction;

public class HeuristicFunction2A implements HeuristicFunction
{
    @Override
    public double getHeuristicValue(Object o) {
        Board board = (Board) o;
        int nServers = Board.getnServers();
        int totalTransmissionTime = board.getTotalTransmissionTime();
        int totalSquareTime = board.getTotalSquareTime();

        double mean = totalTransmissionTime / (double)nServers;
        double variance = (totalSquareTime / (double)nServers) - (mean*mean);
        double stdev = java.lang.Math.sqrt(variance);

        return mean + stdev;
    }
}
