//SF con hill climbing


import IA.DistFS.Servers;
import aima.search.framework.SuccessorFunction;

import java.util.ArrayList;
import java.util.List;

public class Practica1SuccessorFunctionHC implements SuccessorFunction {

    private ArrayList<Practica1Board> llistaSuccessors;
    private Practica1Board board;
    private Servers servers;
    private ArrayList<Integer> assigs;

    public List getSuccessors (Object estat)
    {
        llistaSuccessors = new ArrayList();

        board = (Practica1Board) estat;
        servers = board.getServers();
        assigs = board.getServReq();

        int nreq = 1;

        for (int req = 0; req < nreq; ++req) {


        }


        return llistaSuccessors;
    }
}