// Hill Climbing - Operador Mover
// Apunte: se añade el estado actual como sucesor. Pero considero que eso es mejor que poner un if en el bucle.

import IA.DistFS.Requests;
import IA.DistFS.Servers;
import aima.search.framework.SuccessorFunction;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class SuccessorFunctionHC1 implements SuccessorFunction {

    public List getSuccessors (Object estat)
    {
        ArrayList llistaSuccessors = new ArrayList();

        Board board = (Board) estat;
        Servers servers = board.getServers();
        Requests requests = board.getRequests();

        int nreq = requests.size();
        for (int req = 0; req < nreq; ++req) {
            Set<Integer> replications = servers.fileLocations(requests.getRequest(req)[1]);
            Iterator<Integer> repl = replications.iterator();
            while (repl.hasNext()) {
                Board successor = new Board(board);
                llistaSuccessors.add(successor.move(req,repl.next()));
            }
        }

        return llistaSuccessors;
    }
}