import java.util.Random;

/**
 * Created by carlos.casar on 31/03/2016.
 */
public class Experiments {

    //static private Random seed;

    public static void main(String[] args)
    {
        experiment1();
        //experimentEsp();
    }

    private static void experiment1(){
        int seed;
        int nUsers = 200;
        int nRequests = 5;          // max per user
        int nServers = 50;
        int minReplications = 5;    // per file
        int solIni = 3;
        String operatorS;
        int criterion = 1;
        char heuristic = 'A';

        for (int rep = 1; rep <= 10; ++rep) {
            seed = rep;
            for (int op = 1; op <= 3; ++op){
                if (op == 1)operatorS = "Move";
                else if (op == 2)operatorS = "Swap";
                else operatorS = "Move+Swap";
                Controller.Hill_Climbing(seed,nUsers,nRequests,nServers,minReplications,
                        solIni,operatorS,heuristic,criterion);

            }
        }
    }

    private static void experiment2(){
        int seed;
        int nUsers = 200;
        int nRequests = 5;          // max per user
        int nServers = 50;
        int minReplications = 5;    // per file
        int solIni;
        String operatorS = "Move+Swap";
        int criterion = 1;
        char heuristic = 'A';

        for (int rep = 0; rep < 10; ++rep) {
            seed = rep;
            for (int sol = 1; sol <= 3; ++sol) {
                solIni = sol;
                Controller.Hill_Climbing(seed,nUsers,nRequests,nServers,minReplications,
                        solIni,operatorS,heuristic,criterion);
            }
        }
    }
    private static void experimentEsp()
    {
        int seed = 1234;
        int nUsers = 200;
        int nRequests = 5;          // max per user
        int nServers = 50;
        int minReplications = 5;    // per file
        int solIni = 3;
        String operatorS = "Move+Swap";
        int criterion = 1;
        char heuristic = 'A';

        Controller.Hill_Climbing(seed,nUsers,nRequests,nServers,minReplications,
                solIni,operatorS,heuristic,criterion);

        Controller.Hill_Climbing(seed,nUsers,nRequests,nServers,minReplications,
                solIni,operatorS,heuristic,criterion);

        Controller.Hill_Climbing(seed,nUsers,nRequests,nServers,minReplications,
                solIni,operatorS,heuristic,criterion);

    }

}
