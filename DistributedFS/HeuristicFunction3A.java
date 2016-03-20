/*
Un heurístico muy sencillo, simplemente comprueba el tiempo total de transmisión
 */
import aima.search.framework.HeuristicFunction;

public class HeuristicFunction3A implements HeuristicFunction
{
    @Override
    public double getHeuristicValue(Object o) {
        Board board = (Board) o;
        return board.getTotalTransmissionTime();
    }
}
