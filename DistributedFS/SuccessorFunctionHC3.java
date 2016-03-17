// Hill Climbing - Ambos operadores

import IA.DistFS.Requests;
import IA.DistFS.Servers;
import aima.search.framework.SuccessorFunction;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

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