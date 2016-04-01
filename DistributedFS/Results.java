
public class Results {
    private long solIniTime;
    private int solIniTrans;
    private long searchTime;
    private int finalTransmission;
    private long totalTime;
    private int nodes;
    private int maxservtime;

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

    public String toString() {
        return solIniTime + ("\t") + solIniTrans + ("\t") + searchTime + ("\t") +
                finalTransmission + ("\t") + maxservtime + ("\t") + totalTime + ("\t") + nodes;
    }
}
