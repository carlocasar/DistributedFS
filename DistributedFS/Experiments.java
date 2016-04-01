import java.io.*;

public class Experiments {

    //static private Random seed;
    private static int solIni = 3;
    private static String operatorS = "Move+Swap";

    private static void experimentEsp()
    {
        File f;
        f = new File("experimentEsp.txt");
        int seed = 1234;
        int nUsers = 200;
        int nRequests = 5;          // max per user
        int nServers = 50;
        int minReplications = 5;    // per file
        int criterion = 1;
        char heuristic = 'A';

        Results r1;
        r1 = new Results();
        r1 = Controller.Hill_Climbing(seed,nUsers,nRequests,nServers,minReplications,
                solIni,operatorS,heuristic,criterion);
        escribir(r1,f);

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

    private static void experiment3(){
        int seed;
        int nUsers = 200;
        int nRequests = 5;          // max per user
        int nServers = 50;
        int minReplications = 5;    // per file
        int criterion = 1;
        char heuristic = 'A';

        for (int rep = 0; rep < 10; ++rep) {
            seed = rep;

            Controller.Simmulated_Annealing(seed, nUsers, nRequests, nServers, minReplications,
                    solIni, operatorS, heuristic, criterion,0,0,0,0);

        }
    }

    private static void experiment4(){
        int seed = 1;
        int nUsers = 100;
        int nRequests = 5;          // max per user
        int nServers = 50;
        int minReplications = 5;    // per file
        int criterion = 1;
        char heuristic = 'A';
        for (int i = 1; i < 5; ++i) {
            seed = i;
            for (int incU = 0; incU < 10; ++incU) {     //incrementar hasta ver tendencia
                nUsers += 100;
                Controller.Hill_Climbing(seed, nUsers, nRequests, nServers, minReplications,
                        solIni, operatorS, heuristic, criterion);
            }
            nUsers = 200;
            for (int incS = 0; incS < 10; ++incS) {      //incrementar hasta ver tendencia
                nServers += 50;
                Controller.Hill_Climbing(seed, nUsers, nRequests, nServers, minReplications,
                        solIni, operatorS, heuristic, criterion);
            }
        }
    }

    private static void experiment5and6() {
        int seed;
        int nUsers = 200;
        int nRequests = 5;          // max per user
        int nServers = 50;
        int minReplications = 5;    // per file
        int solIni = 3;
        String operatorS = "Move+Swap";
        int criterion;
        char heuristic;

        for (int rep = 0; rep < 10; ++rep) {
            seed = rep;
            for (int op = 1; op <= 3; ++op) {
                if (op == 1 || op == 3) {
                    criterion = 1;
                    heuristic = 'A';
                } else {
                    criterion = 2;
                    heuristic = 'B';
                }
                if (op == 1 || op == 2) Controller.Hill_Climbing(seed, nUsers, nRequests, nServers, minReplications,
                        solIni, operatorS, heuristic, criterion);
                else Controller.Simmulated_Annealing(seed, nUsers, nRequests, nServers, minReplications,
                        solIni, operatorS, heuristic, criterion,0,0,0,0);
            }
        }
    }

    private static void experiment7() {
        int seed = 1;
        int nUsers = 200;
        int nRequests = 5;          // max per user
        int nServers = 50;
        int minReplications = 5;    // per file
        int criterion;
        char heuristic;

        for (int rep = 1; rep <= 5; ++rep) {
            for (int op = 1; op <= 2; ++op) {
                if (op == 1) {
                    criterion = 1;
                    heuristic = 'A';
                } else {
                    criterion = 2;
                    heuristic = 'B';
                }
                Controller.Hill_Climbing(seed,nUsers,nRequests,nServers,minReplications,
                        solIni,operatorS,heuristic,criterion);
            }
            minReplications += 5;
        }


    }


    public static void main(String[] args)
    {
        int experiment;
        try {
            experiment = 1;
            // aquí falta el read del experiment
        } catch (RuntimeException e) {
            experiment = 10;
        }

        switch(experiment) {
            case 0:
                experimentEsp();
                break;
            case 1:
                experiment1();
                break;
            case 2:
                experiment2();
                break;
            case 3:
                experiment3();
                break;
            case 4:
                experiment4();
                break;
            case 56:
                experiment5and6();
                break;
            case 7:
                experiment7();
                break;
            //case 8: experiment8(); break;
            default:
                System.out.println("No such experiment");
        }

        // el write se hace dentro de cada experiment en su fichero particular.
    }

    public static void escribir(Results r, File f){
        try {

            FileWriter bw = new FileWriter(f);
            PrintWriter wr = new PrintWriter(bw);
            wr.append(r.toString());
            wr.close();
            bw.close();
        }
        catch (IOException e){}

    }
}