
import aima.search.framework.HeuristicFunction;

public class HeuristicFunction1A implements HeuristicFunction
{
    @Override
    public double getHeuristicValue(Object o) {
        Board board = (Board) o;
        double maxServerTime = board.getMaxServerTime();
        double totalTransmissionTime = board.getTotalTransmissionTime();

        return maxServerTime + 1/(maxServerTime/totalTransmissionTime);
    }
}
