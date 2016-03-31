/*
 * Conjunto de operadores: Intercambiar
 */

import IA.DistFS.Requests;
import IA.DistFS.Servers;
import aima.search.framework.Successor;
import aima.search.framework.SuccessorFunction;

import java.util.*;

public class SuccessorFunction2 implements SuccessorFunction {

    boolean HC;
    Random seed;

    SuccessorFunction2 (String method)
    {
        if (method.equals("Hill Climbing")) HC = true;
        else if (method.equals("Simulated Annealing")) {
            HC = false;
            seed = new Random(System.currentTimeMillis());
        }
        else throw new RuntimeException("Stupid Local Search Method");
    }

    SuccessorFunction2 (String method, Random seed)
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

    private List getSuccessorsHC (Object estat)
    {
        ArrayList<Successor> llistaSuccessors = new ArrayList<Successor>();

        Board board = (Board) estat;
        Servers servers = board.getServers();
        Requests requests = board.getRequests();
        ArrayList<Integer> assigs = board.getAssignations();

        int nreq = requests.size();
        for (int req1 = 0; req1 < nreq; ++req1) {
            int serv1 = assigs.get(req1);
            Set<Integer> replications1 = servers.fileLocations(requests.getRequest(req1)[1]);
            for (int req2 = req1+1; req2 < nreq; ++req2) {
                int serv2 = assigs.get(req2);
                Set<Integer> replications2 = servers.fileLocations(requests.getRequest(req2)[1]);
                if (replications1.contains(serv2) && replications2.contains(serv1) && serv1 != serv2) {
                    Board successor = new Board(board);
                    successor.swap(req1, req2);
                    llistaSuccessors.add(new Successor("Swap "+req1+" "+req2,successor));
                }
            }
        }

        return llistaSuccessors;
    }

    private List getSuccessorsSA (Object estat)
    {
        ArrayList<Successor> llistaSuccessors = new ArrayList<Successor>();

        Board board = (Board) estat;
        Servers servers = board.getServers();
        Requests requests = board.getRequests();
        ArrayList<Integer> assigs = board.getAssignations();

        int req1 = 0;
        ArrayList<Integer> candidates = new ArrayList<Integer>();
        boolean found = false;
        while (! found) {
            req1 = seed.nextInt(board.getnRequests());
            int serv1 = assigs.get(req1);
            Set<Integer> replications1 = servers.fileLocations(requests.getRequest(req1)[1]);

            int iterations = seed.nextInt(replications1.size() - 1) + 1;
            Iterator<Integer> iterator = replications1.iterator();
            int serv2 = 0;
            while (iterations != 0) {
                serv2 = iterator.next();
                if (serv2 == serv1) iterator.next();
                --iterations;
            }

            for (int candidate = 0; candidate < requests.size(); ++candidate) {
                if (assigs.get(candidate) == serv2) {
                    Set<Integer> replications2 = servers.fileLocations(requests.getRequest(candidate)[1]);
                    if (replications2.contains(serv1)) candidates.add(candidate);
                }
            }

            if (! candidates.isEmpty()) found = true;
        }
        int req2 = candidates.get(seed.nextInt(candidates.size()));

        Board successor = new Board(board);
        successor.swap(req1, req2);
        llistaSuccessors.add(new Successor("Swap "+req1+" "+req2,successor));

        return llistaSuccessors;
    }
}


