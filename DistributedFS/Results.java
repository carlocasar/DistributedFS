
public class Results {
    private long solIniTime;
    private int solIniTrans;
    private long searchTime;
    private int finalTransmission;
    private long totalTime;

    void setSolIni(long iniTime, long finTime, int iniTrans){
        solIniTime = finTime - iniTime;
        solIniTrans = iniTrans;
    }

    void setSearchTime(long iniSearch, long finSearch){
        searchTime = finSearch - iniSearch;
    }

    void setFinalTransmission(int transmission){
        finalTransmission = transmission;
    }

    void setTotalTime(long iniTime, long finTime){
        totalTime = finTime - iniTime;
    }
}
