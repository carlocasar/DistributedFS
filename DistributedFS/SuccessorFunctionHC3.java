// Hill Climbing - Ambos operadores

import aima.search.framework.SuccessorFunction;
import java.util.List;

public class SuccessorFunctionHC3 implements SuccessorFunction {

    public List getSuccessors (Object estat)
    {
        SuccessorFunctionHC1 moveOp = new SuccessorFunctionHC1();
        SuccessorFunctionHC2 swapOp = new SuccessorFunctionHC2();

        List llistaSuccessors = moveOp.getSuccessors(estat);
        llistaSuccessors.addAll(swapOp.getSuccessors(estat));

        return llistaSuccessors;
    }
}