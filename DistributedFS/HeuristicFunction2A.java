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

/*
    Integer servs = 50;
    Integer maxTime = 5000;
    Integer reqs = 1000;
    Integer timeS =  maxTime * 500;
    double squared = (long) timeS*timeS*2;
    double mean = (reqs * maxTime) / servs;
                                    double sMean = squared / servs;
                                    double meanS = mean * mean;
                                    double variance = sMean - meanS;
    double stdev = java.lang.Math.sqrt(variance);
 */