// Hill Climbing - Operador Mover
// Apunte: se añade el estado actual como sucesor. Pero considero que eso es mejor que poner un if en el bucle.

import IA.DistFS.Requests;
import IA.DistFS.Servers;
import aima.search.framework.Successor;
import aima.search.framework.SuccessorFunction;

import java.util.*;

// Operador Mover
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
        ArrayList<Integer> assigs = board.getAssignations();

        int nreq = requests.size();
        for (int req = 0; req < nreq; ++req) {
            int presentServ = assigs.get(req);
            Set<Integer> replications = servers.fileLocations(requests.getRequest(req)[1]);
            Iterator<Integer> replication = replications.iterator();
            while (replication.hasNext()) {
                Board successor = new Board(board);
                int nextServ = replication.next();
                if (nextServ != presentServ) successor.move(req,nextServ);
                llistaSuccessors.add(new Successor("Move "+req+" "+nextServ, successor));
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

        int req = seed.nextInt(requests.size());
        int presentServ = board.getAssignations().get(req);
        Set<Integer> replications = servers.fileLocations(requests.getRequest(req)[1]);

        int iterations = seed.nextInt(replications.size() - 1) + 1;
        Iterator<Integer> iterator = replications.iterator();
        int nextServ = 0;
        while (iterations != 0) {
            nextServ = iterator.next();
            if (nextServ == presentServ) iterator.next();
            --iterations;
        }

        Board successor = new Board(board);
        successor.move(req,nextServ);
        llistaSuccessors.add(new Successor("Move "+req+" "+nextServ, successor));

        return llistaSuccessors;
    }
}