// Hill Climbing - Operador Mover
// Apunte: se añade el estado actual como sucesor. Pero considero que eso es mejor que poner un if en el bucle.

import IA.DistFS.Requests;
import IA.DistFS.Servers;
import aima.search.framework.Successor;
import aima.search.framework.SuccessorFunction;

import java.util.*;

public class SuccessorFunction1 implements SuccessorFunction {

    boolean HC;
    Random seed;

    SuccessorFunction1 (String method)
    {
        if (method.equals("Hill Climbing")) HC = true;
        else if (method.equals("Simulated Annealing")) {
            HC = false;
            seed = new Random(System.currentTimeMillis());
        }
        else throw new RuntimeException("Stupid Local Search Method");
    }

    SuccessorFunction1 (String method, Random seed)
    {
        if (method.equals("Hill Climbing")) HC = true;
        else if (method.equals("Simulated Annealing")) {
            HC = false;
            this.seed = seed;
        }
        else throw new RuntimeException("Stupid Local Search Method");
    }

    @Override
    public List getSuccessors (Object estat)
    {
        if (HC) return getSuccessorsHC(estat);
        else return getSuccessorsSA(estat);
    }

    public List getSuccessorsHC (Object estat)
    {
        ArrayList<Successor> llistaSuccessors = new ArrayList<Successor>();

        Board board = (Board) estat;
        Servers servers = board.getServers();
        Requests requests = board.getRequests();

        int nreq = requests.size();
        for (int req = 0; req < nreq; ++req) {
            Set<Integer> replications = servers.fileLocations(requests.getRequest(req)[1]);
            Iterator<Integer> repl = replications.iterator();
            while (repl.hasNext()) {
                Board successor = new Board(board);
                int next = repl.next();
                successor.move(req,next);
                llistaSuccessors.add(new Successor("Request " + req + " a server " + next, successor));
            }
        }

        return llistaSuccessors;
    }

    public List getSuccessorsSA (Object estat)
    {
        ArrayList<Successor> llistaSuccessors = new ArrayList<Successor>();

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