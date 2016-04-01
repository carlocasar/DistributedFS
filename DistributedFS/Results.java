
public class Results {
    private long solIniTime;
    private int solIniTrans;
    private long searchTime;
    private int finalTransmission;
    private long totalTime;
    private int nodes;

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

    public String toString() {
        return solIniTime + ("\t") + solIniTrans + ("\t") + searchTime + ("\t") + finalTransmission + ("\t") + totalTime + ("\t") + nodes + ("\n");
    }
}
