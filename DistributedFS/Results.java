import java.util.ArrayList;
import java.util.Objects;

public class Results {
    private int seed;
    private int numUsers;
    private int numRepl;
    private long solIniTime;
    private int solIniTrans;
    private long searchTime;
    private int finalTransmission;
    private long totalTime;
    private int nodes;
    private int maxservtime;
    private int numServs;
    Board init;
    Board end;
    private ArrayList<Integer> servtimes;
    private ArrayList<Integer> assig;

    public static final String
            headers = "Seed\tnUsers\tnServs\tnReplic\tSolIniTime" +
            "\tSolIniTrans\tSearchTime\tTotalTime\tMaxServ/Mean\tExecTime\tNodes";

    public void setSolIni(long iniTime, long finTime, int iniTrans){
        solIniTime = finTime - iniTime;
        solIniTrans = iniTrans;
    }

    public void setNodes(int n){nodes = n;}

    public void setSearchTime(long iniSearch, long finSearch){
        searchTime = finSearch - iniSearch;
    }

    public void setFinalTransmission(int transmission){
        finalTransmission = transmission;
    }

    public void setTotalTime(long iniTime, long finTime){
        totalTime = finTime - iniTime;
    }

    public void setMaxservtime(int maxtime) { maxservtime = maxtime; }

    public void setInit(Board board) {
        init = new Board(board);
        numServs = board.getnServers();
        numRepl = board.getnRepls();
        numUsers = board.getnUsers();
        seed = board.getnSeed();
    }

    public void setEnd(Board board) {
        end = new Board(board);
    }

    public void setServerTimes(ArrayList<Integer> v){ servtimes = v; }

    public void setAssig(ArrayList<Integer> v) { assig = v; }

    public String toString() {
        String s = seed + ("\t") + numUsers + ("\t") + numServs + ("\t") + numRepl + ("\t") + solIniTime + ("\t") + solIniTrans + ("\t") + searchTime + ("\t") + finalTransmission + ("\t");
        if (end.getCriterion() == 1) s = s.concat(String.valueOf(maxservtime));
        else s = s.concat(String.valueOf(totalTime/numServs));
        s = s.concat("\t") + totalTime + ("\t") + nodes;
        return s;
    }

    public String compareData(){
        int x, y, a;
        long b;
        int crit = init.getCriterion();
        init.initMaterialized();
        end.initMaterialized();
        x = init.getTotalTransmissionTime();
        y = end.getTotalTransmissionTime();
        a = end.getMaxTimeServers();
        if (crit == 1){
            b = end.getMaxServerTime()/2;
        }
        else b = end.getTotalTransmissionTime()/end.getnServers();
        String s = "Are results equal?\n";
        s = s.concat("solIniTrans: ");
        s = s.concat(String.valueOf(x == solIniTrans) + ("\n"));
        s = s.concat("Total transmission time: ");
        s = s.concat(String.valueOf(finalTransmission == y) + ("\n"));
        s = s.concat(end.getTotalTransmissionTime() + ("\n"));
        s = s.concat("Num servers with max time: ");
        s = s.concat(String.valueOf(numServs == a) + ("\n"));
        int n = end.getAssignations().size();
        Boolean equal = true;
        for (int i = 0; i < n && equal; ++i){
            equal = Objects.equals(end.getAssignations().get(i), assig.get(i));
        }
        s = s.concat("Assignations: ");
        s = s.concat(String.valueOf(equal) + ("\n"));
        s = s.concat(String.valueOf(servtimes) + ("\n"));
        s = s.concat(String.valueOf(end.getAssignations()) + ("\n"));
        if (crit == 1) {
            s = s.concat("Max server time: ");
            s = s.concat(String.valueOf(maxservtime == b) + ("\n"));
        }
        else {
            s = s.concat("Mean: " + b + ("\n"));
        }
        return s;
    }



}
