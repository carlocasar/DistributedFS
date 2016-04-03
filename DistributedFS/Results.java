import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Objects;

public class Results {
    private long solIniTime;
    private int solIniTrans;
    private long searchTime;
    private int finalTransmission;
    private long totalTime;
    private int nodes;
    private int maxservtime;
    private int numServs;
    private long totalsquare;
    Board init;
    Board end;
    private ArrayList<Integer> servtimes;

    public static final String
            headers = "SolIniTime\tSolIniTrans\tSearchTime\tTotalTime\tMaxServTime\tExecTime\tNodes";

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

    public void setNumServs(int n) {numServs = n;}

    public void setInit(Board board) {
        init = new Board(board);
    }

    public void setEnd(Board board) {
        end = new Board(board);
    }

    public void setServerTimes(ArrayList<Integer> v){ servtimes = v; }

    public void setTotalsquare(long n) {totalsquare = n;}

    public String toString() {
        return solIniTime + ("\t") + solIniTrans + ("\t") + searchTime + ("\t") +
                finalTransmission + ("\t") + maxservtime + ("\t") + totalTime + ("\t") + nodes;
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
        else b = end.getTotalSquareTime();
        String s = "Are results equal?\n";
        s = s.concat("solIniTrans: ");
        s = s.concat(String.valueOf(x == solIniTrans) + ("\n"));
        s = s.concat("Total transmission time: ");
        s = s.concat(String.valueOf(finalTransmission == y) + ("\n"));
        s = s.concat(init.getTotalTransmissionTime() + ("\n"));
        s = s.concat("Num servers with max time: ");
        s = s.concat(String.valueOf(numServs == a) + ("\n"));
        int n = end.getnServers();
        Boolean equal = true;
        for (int i = 0; i < n; ++i){
            if (equal) equal = Objects.equals(end.getServerTimes().get(i), servtimes.get(i));
        }
        s = s.concat("Assignations: ");
        s = s.concat(String.valueOf(equal) + ("\n"));
        s = s.concat(String.valueOf(end.getServerTimes()) + ("\n"));
        s = s.concat(String.valueOf(servtimes) + ("\n"));
        if (crit == 1) {
            s = s.concat("Max server time: ");
            s = s.concat(String.valueOf(maxservtime == b) + ("\n"));
        }
        else {
            s = s.concat("Total square time: ");
            s = s.concat(String.valueOf(totalsquare == b) + ("\n"));
            s = s.concat(totalsquare + " " + b);
        }
        return s;
    }
}
