import aima.search.framework.SuccessorFunction;

import java.util.List;
import java.util.Random;

/*
 * Conjunto de operadores: Mover + Intercambiar
 */

public class SuccessorFunction3 implements SuccessorFunction {

    boolean HC;
    Random seed;
    SuccessorFunction1 moveOp;
    SuccessorFunction2 swapOp;

    SuccessorFunction3 (String method)
    {
        if (method.equals("Hill Climbing")) HC = true;
        else if (method.equals("Simulated Annealing")) {
            HC = false;
            seed = new Random(System.currentTimeMillis());
        }
        else throw new RuntimeException("Stupid Local Search Method");

        moveOp = new SuccessorFunction1(method,seed);
        swapOp = new SuccessorFunction2(method,seed);
    }

    public List getSuccessors (Object estat)
    {
        if (HC) {
            List llistaSuccessors = moveOp.getSuccessors(estat);
            llistaSuccessors.addAll(swapOp.getSuccessors(estat));

            return llistaSuccessors;
        }
        else {  // SA
            int operator = (int) (seed.nextDouble() * 2 + 0);
            if (operator == 0) return moveOp.getSuccessors(estat);
            else return swapOp.getSuccessors(estat);
        }
    }
}
