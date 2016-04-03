import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

public class Experiments {

    static final int usualNusers = 200;
    static final int usualNrequests = 5;          // max per user
    static final int usualNservers = 50;
    static final int usualMinReplications = 5;    // per file

    static final char usualHeuristicOne = 'B';
    static final char usualHeuristicTwo = 'A';
    static final String settledOperatorS = "Move+Swap";
    static final int settledSolIni = 4;

    private static void experimentSp()      // Seed 1234
    {
        int seed = 1234;
        ArrayList<Results> results = new ArrayList<>();
        for (int repetition = 1; repetition <= 3; ++repetition)
            results.add(Controller.Hill_Climbing(seed, 
                    usualNusers, usualNrequests, usualNservers, usualMinReplications,
                    settledSolIni, settledOperatorS, usualHeuristicOne, 2));
        try {
            File file = new File("experimentSp.txt");
            //file.createNewFile();
            dumpResults("\nSpecial Experiment for " +
                    "solIni #4 and Move & Swap\n",results,file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void experiment1()       // Seleccion del conjunto de operadores
    {
        String move = "move";
        String swap = "swap";
        String both = "move + swap";
        ArrayList<Results> rMove = new ArrayList<>();
        ArrayList<Results> rSwap = new ArrayList<>();
        ArrayList<Results> rBoth = new ArrayList<>();

        for (int repetition = 1; repetition <= 10; ++repetition) {
            int seed = repetition;
            rMove.add(Controller.Hill_Climbing(seed, usualNusers, usualNrequests,
                    usualNservers, usualMinReplications, settledSolIni, move, usualHeuristicOne, 1));
            rSwap.add(Controller.Hill_Climbing(seed, usualNusers, usualNrequests,
                    usualNservers, usualMinReplications, settledSolIni, swap, usualHeuristicOne, 1));
            rBoth.add(Controller.Hill_Climbing(seed, usualNusers, usualNrequests,
                    usualNservers, usualMinReplications, settledSolIni, both, usualHeuristicOne, 1));
        }

        try {
            File file = new File("data/experiment1.txt");
            file.createNewFile();
            dumpResults("\nMove\n",rMove,file);
            dumpResults("\nSwap\n",rSwap,file);
            dumpResults("\nMove & Swap\n",rBoth,file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void experiment2()      // Seleccion del generador de solucion inicial.
    {
        ArrayList<Results> rSI1 = new ArrayList<>();
        ArrayList<Results> rSI2 = new ArrayList<>();
        ArrayList<Results> rSI3 = new ArrayList<>();
        ArrayList<Results> rSI4 = new ArrayList<>();

        for (int repetition = 0; repetition < 10; ++repetition) {
            int seed = repetition;
            for (int solIni = 1; solIni <= 4; ++solIni) {
                rSI1.add(Controller.Hill_Climbing(seed, usualNusers, usualNrequests,
                        usualNservers, usualMinReplications, solIni, settledOperatorS, usualHeuristicOne, 1));
                rSI2.add(Controller.Hill_Climbing(seed, usualNusers, usualNrequests,
                        usualNservers, usualMinReplications, solIni, settledOperatorS, usualHeuristicOne, 1));
                rSI3.add(Controller.Hill_Climbing(seed, usualNusers, usualNrequests,
                        usualNservers, usualMinReplications, solIni, settledOperatorS, usualHeuristicOne, 1));
                rSI4.add(Controller.Hill_Climbing(seed, usualNusers, usualNrequests,
                        usualNservers, usualMinReplications, solIni, settledOperatorS, usualHeuristicOne, 1));
            }
        }

        try {
            File file = new File("data/experiment2.txt");
            file.createNewFile();
            dumpResults("\nsolIni #1\n",rSI1,file);
            dumpResults("\nsolIni #2\n",rSI2,file);
            dumpResults("\nsolIni #3\n",rSI3,file);
            dumpResults("\nsolIni #4\n",rSI4,file);
        } catch (Exception e) {
            e.printStackTrace();
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

        for (int repetition = 0; repetition < 10; ++repetition) {
            seed = repetition;

            Controller.Simmulated_Annealing(seed, nUsers, nRequests, nServers, minReplications,
                    settledSolIni, settledOperatorS, heuristic, criterion,0,0,0,0);

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
                        settledSolIni, settledOperatorS, heuristic, criterion);
            }
            nUsers = 200;
            for (int incS = 0; incS < 10; ++incS) {      //incrementar hasta ver tendencia
                nServers += 50;
                Controller.Hill_Climbing(seed, nUsers, nRequests, nServers, minReplications,
                        settledSolIni, settledOperatorS, heuristic, criterion);
            }
        }
    }

    private static void experiments5and6() {
        int seed;
        int nUsers = 200;
        int nRequests = 5;          // max per user
        int nServers = 50;
        int minReplications = 5;    // per file
        int solIni = 3;
        String operatorS = "Move+Swap";
        int criterion;
        char heuristic;

        for (int repetition = 0; repetition < 10; ++repetition) {
            seed = repetition;
            for (int op = 1; op <= 3; ++op) {
                if (op == 1 || op == 3) {
                    criterion = 1;
                    heuristic = 'A';
                } else {
                    criterion = 2;
                    heuristic = 'B';
                }
                if (op == 1 || op == 2) Controller.Hill_Climbing(seed, nUsers, nRequests, nServers, minReplications,
                        settledSolIni, settledOperatorS, heuristic, criterion);
                else Controller.Simmulated_Annealing(seed, nUsers, nRequests, nServers, minReplications,
                        settledSolIni, settledOperatorS, heuristic, criterion,0,0,0,0);
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

        for (int repetition = 1; repetition <= 5; ++repetition) {
            for (int op = 1; op <= 2; ++op) {
                if (op == 1) {
                    criterion = 1;
                    heuristic = 'A';
                } else {
                    criterion = 2;
                    heuristic = 'B';
                }
                Controller.Hill_Climbing(seed,nUsers,nRequests,nServers,minReplications,
                        settledSolIni,settledOperatorS,heuristic,criterion);
            }
            minReplications += 5;
        }
    }

    private static void experiment8() {}

    private static void debugging()     // Program here what you want to debug.
    {
        int seed = 1234;
        Results results;
        results = Controller.Hill_Climbing(seed,
        2, 1, 3, 1,
        settledSolIni, settledOperatorS, usualHeuristicOne, 2);
        System.out.println(results.compareData());
    }

    public static void main(String[] args)
    {
        int experiment;
        try {
            System.out.print("Introduce the experiment number.\n" +
                    "Experiment 1: operator sets\n" + "Experiment 2: initial solutions\n" +
                    "Experiment 3:\n" + "Experiment 4:\n" +
                    "Experiment 5:\n" + "Experiment 6:\n" +
                    "Experiment 7:\n" + "Experiment 8:\n" +
                    "Special Experiment 0\n" + "Debugging with code 66\n");
            Scanner scanner = new Scanner(System.in);
            experiment = scanner.nextInt();
        } catch (RuntimeException e) {
            experiment = 99;
        }

        File directory = new File("data");
        if (! directory.exists()){
            directory.mkdir();
        }

        switch(experiment) {
            case 0:
                experimentSp();
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
            case 5:
            case 6:
                experiments5and6();
                break;
            case 7:
                experiment7();
                break;
            case 8:
                experiment8();
                break;
            case 66:
                debugging();
                break;
            default:
                System.out.println("No such experiment");
        }
    }

    private static void dumpResults(String descriptor,ArrayList<Results> results,File file) throws java.io.IOException
    {
        String nL = System.lineSeparator();
        FileWriter fileWriter = new FileWriter(file,true);
        fileWriter.append(nL+descriptor+nL);
        fileWriter.append(nL+Results.headers+nL);
        for (int i = 0; i < results.size(); ++i)
            fileWriter.append(results.get(i).toString()+nL);
        fileWriter.close();
    }
}