
import aima.search.framework.HeuristicFunction;

public class HeuristicFunction1A implements HeuristicFunction
{

    static final int maxFileTransmissionTime = 5000;

    @Override
    public double getHeuristicValue(Object o) {
        Board board = (Board) o;
        double maxServerTime = board.getMaxServerTime();
        double totalTransmissionTime = board.getTotalTransmissionTime();
        double maxTotalTransmissionTime = board.getnRequests() * maxFileTransmissionTime;

        return maxServerTime + (totalTransmissionTime/maxTotalTransmissionTime);
    }
}
