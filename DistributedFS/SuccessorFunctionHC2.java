// Hill Climbing - Operador Intercambiar

import IA.DistFS.Requests;
import IA.DistFS.Servers;
import aima.search.framework.Successor;
import aima.search.framework.SuccessorFunction;

import java.util.*;

public class SuccessorFunctionHC2 implements SuccessorFunction {

    boolean HC;
    Random seed;

    SuccessorFunctionHC2 (String method)
    {
        if (method == "Hill Climbing") HC = true;
        else if (method == "Simmulated Annealing") HC = false;
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
        ArrayList llistaSuccessors = new ArrayList();

        Board board = (Board) estat;
        Servers servers = board.getServers();
        Requests requests = board.getRequests();
        ArrayList<Integer> assigs = board.getAssignations(); // Req -> Server

        int nreq = requests.size();
        for (int req1 = 0; req1 < nreq; ++req1) {
            Set<Integer> replications1 = servers.fileLocations(requests.getRequest(req1)[1]);
            for (int req2 = req1+1; req2 < nreq; ++req2) {
                Set<Integer> replications2 = servers.fileLocations(requests.getRequest(req2)[1]);
                if (replications1.contains(assigs.get(req2)) && replications2.contains(assigs.get(req1)) ) {
                    Board successor = new Board(board);
                    successor.swap(req1, req2);
                    llistaSuccessors.add(successor);
                }
            }
        }

        return llistaSuccessors;
    }

    private List getSuccessorsSA (Object estat)
    {
        ArrayList llistaSuccessors = new ArrayList();

        Board board = (Board) estat;
        Servers servers = board.getServers();
        Requests requests = board.getRequests();
        ArrayList<Integer> assigs = board.getAssignations(); // Req -> Server

        int req1,req2;
        req1 = req2 = 0;
        ArrayList<Integer> candidates = new ArrayList<Integer>();
        boolean found = false;
        while (! found) {
            req1 = (int) (seed.nextDouble() * board.getnRequests() + 0);
            int serv1 = assigs.get(req1);

            Set<Integer> replications1 = servers.fileLocations(requests.getRequest(req1)[1]);
            int iterations = (int) (seed.nextDouble() * replications1.size() + 1);
            Iterator<Integer> iterator = replications1.iterator();
            int serv2 = 0;
            while (iterations != 0) {
                serv2 = iterator.next();
                if (serv2 == assigs.get(req1)) iterator.next();
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
        req2 = candidates.get((int) (seed.nextDouble() * candidates.size() + 0));

        Board successor = new Board(board);
        successor.swap(req1, req2);
        llistaSuccessors.add(new Successor("Swap "+req1+" "+req2,successor));

        return llistaSuccessors;
    }
}