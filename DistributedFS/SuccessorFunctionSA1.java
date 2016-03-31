import IA.DistFS.Requests;
import IA.DistFS.Servers;
import aima.search.framework.Successor;
import aima.search.framework.SuccessorFunction;

import java.util.*;

/**
 * Created by gerard.otero on 30/03/2016.
 */
public class SuccessorFunctionSA1 implements SuccessorFunction{
    public List getSuccessors (Object estat)
    {
        ArrayList llistaSuccessors = new ArrayList();

        Board board = (Board) estat;
        Servers servers = board.getServers();
        Requests requests = board.getRequests();
        Random random = new Random(System.currentTimeMillis());

        int nreq = requests.size();

        int randomReq = random.nextInt(nreq);
        Set<Integer> replications = servers.fileLocations(requests.getRequest(randomReq)[1]);
        Iterator<Integer> repl = replications.iterator();
        int i = random.nextInt(replications.size());
        int j = 0;
        int next = 0;
        while (j <= i) {
            next = repl.next();
            ++j;
        }
        Board successor = new Board(board);
        successor.move(randomReq,next);
        llistaSuccessors.add(new Successor("Request " + randomReq + " a server " + next, successor));

        return llistaSuccessors;
    }
}
