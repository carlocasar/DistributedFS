
public class Results {
    private long solIniTime;
    private int solIniTrans;
    private long searchTime;
    private int finalTransmission;
    private long totalTime;
    private int nodes;
    private int maxservtime;
    Board init;
    Board end;

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

    public void setInit(Board board) {init = board;}

    public void setEnd(Board board) {end = board;}

    public String toString() {
        return solIniTime + ("\t") + solIniTrans + ("\t") + searchTime + ("\t") +
                finalTransmission + ("\t") + maxservtime + ("\t") + totalTime + ("\t") + nodes;
    }

    public String compareData(){
        int x, y, b;
        init.initMaterialized();
        end.initMaterialized();
        x = init.getTotalTransmissionTime();
        y = end.getTotalTransmissionTime();
        b = end.getMaxServerTime()/2;
        String s = "Are results equal?\n";
        s = s.concat("solIniTrans: ");
        s = s.concat(String.valueOf(x == solIniTrans) + ("\n"));
        s = s.concat("Total transmission time: ");
        s = s.concat(String.valueOf(finalTransmission == y) + ("\n"));
        s = s.concat("Max server time: ");
        s = s.concat(String.valueOf(maxservtime == b) + ("\n"));
        return s;
    }
}
