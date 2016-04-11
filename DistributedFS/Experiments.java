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
    static final int settledSolIni = 3;

    static final int settledSteps = 1000000;
    static final int settledStiter = 10000;
    static final int settledK = 25;
    static final double settledLamb = 0.0008;

    private static void experimentSp()      // Seed 1234
    {
        int seed = 1234;
        ArrayList<Results> results = new ArrayList<>();
        for (int repetition = 1; repetition <= 3; ++repetition)
            results.add(Controller.Hill_Climbing(seed,
                    usualNusers, usualNrequests, usualNservers, usualMinReplications,
                    settledSolIni, settledOperatorS, usualHeuristicOne, 1));
        try {
            File file = new File("data/experimentSp.txt");
            dumpResults("Special Experiment for " +
                    "solIni #4, Move & Swap, heuristic 1B",results,file);
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
        int seed;
        for (int repetition = 1; repetition <= 10; ++repetition) {
            seed = repetition;
            rMove.add(Controller.Hill_Climbing(seed, usualNusers, usualNrequests,
                    usualNservers, usualMinReplications, 3, move, usualHeuristicOne, 1));
            rSwap.add(Controller.Hill_Climbing(seed, usualNusers, usualNrequests,
                    usualNservers, usualMinReplications, 3, swap, usualHeuristicOne, 1));
            rBoth.add(Controller.Hill_Climbing(seed, usualNusers, usualNrequests,
                    usualNservers, usualMinReplications, 3, both, usualHeuristicOne, 1));
        }

        try {
            File file = new File("data/experiment1.txt");
            file.delete();
            file.createNewFile();
            dumpResults("Move",rMove,file);
            dumpResults("Swap",rSwap,file);
            dumpResults("Move & Swap",rBoth,file);
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
        int seed;
        for (int repetition = 1; repetition <= 10; ++repetition) {
            seed = repetition;
                rSI1.add(Controller.Hill_Climbing(seed, usualNusers, usualNrequests,
                        usualNservers, usualMinReplications, 1, settledOperatorS, usualHeuristicOne, 1));
                rSI2.add(Controller.Hill_Climbing(seed, usualNusers, usualNrequests,
                        usualNservers, usualMinReplications, 2, settledOperatorS, usualHeuristicOne, 1));
                rSI3.add(Controller.Hill_Climbing(seed, usualNusers, usualNrequests,
                        usualNservers, usualMinReplications, 3, settledOperatorS, usualHeuristicOne, 1));
                rSI4.add(Controller.Hill_Climbing(seed, usualNusers, usualNrequests,
                        usualNservers, usualMinReplications, 4, settledOperatorS, usualHeuristicOne, 1));
        }

        try {
            File file = new File("data/experiment2.txt");
            file.delete();
            file.createNewFile();
            dumpResults("solIni #1",rSI1,file);
            dumpResults("solIni #2",rSI2,file);
            dumpResults("solIni #3",rSI3,file);
            dumpResults("solIni #4",rSI4,file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void experiment3()
    {
        int seed = 1234;
        ArrayList<Results> r1 = new ArrayList<>();
        ArrayList<Results> r2 = new ArrayList<>();
        ArrayList<Results> r3 = new ArrayList<>();
        ArrayList<Results> r4 = new ArrayList<>();
        int steps = 2000;
        int stiter = 200;
        int k = 10;
        double lambda = 0.5;

        for (int repetition = 0; repetition < 10; ++repetition) {
            r1.add(Controller.Simmulated_Annealing(seed, usualNusers, usualNrequests, usualNservers, usualMinReplications,
                    settledSolIni, settledOperatorS, usualHeuristicOne, 1,steps,1000,50,0.05));
            r2.add(Controller.Simmulated_Annealing(seed, usualNusers, usualNrequests, usualNservers, usualMinReplications,
                    settledSolIni, settledOperatorS, usualHeuristicOne, 1,200000,stiter,50,0.05));
            r3.add(Controller.Simmulated_Annealing(seed, usualNusers, usualNrequests, usualNservers, usualMinReplications,
                    settledSolIni, settledOperatorS, usualHeuristicOne, 1,200000,1000,k,0.05));
            r4.add(Controller.Simmulated_Annealing(seed, usualNusers, usualNrequests, usualNservers, usualMinReplications,
                    settledSolIni, settledOperatorS, usualHeuristicOne, 1,200000,1000,50,lambda));
            steps += 2000;
            stiter *= 2;
            k *= 5;
            lambda /= 5;
        }
        try {
            File file = new File("data/experiment3.txt");
            file.delete();
            file.createNewFile();
            dumpResults("steps (usual = 200000, ini = 2000, + 2000)",r1,file);
            dumpResults("stiter (usual = 1000, ini = 200, *2)",r2,file);
            dumpResults("k (usual = 50, ini = 10, *5)",r3,file);
            dumpResults("lambda (usual = 0.05, ini = 0.5, /5)",r4,file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void experiment4()       // Experimenting with nUsers & nServers
    {
        ArrayList<Results> results = new ArrayList<>();
        int seed = 2;
        for (int nUsers = 100; nUsers <= 1000; nUsers += 100)       //incrementar hasta ver tendencia
                results.add(Controller.Hill_Climbing(seed, nUsers, usualNrequests, usualNservers, usualMinReplications,
                        settledSolIni, settledOperatorS, usualHeuristicOne, 1));


        File file;
        file = new File("data/experiment4.txt");
        try {
            file.delete();
            file.createNewFile();
            dumpResults("nUsers",results,file);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("first part done. see results");

        results = new ArrayList<>();
        for (int nServers = 50; nServers <= 500; nServers += 50)    //incrementar hasta ver tendencia
                results.add(Controller.Hill_Climbing(seed, usualNusers, usualNrequests, nServers, usualMinReplications,
                        settledSolIni, settledOperatorS, usualHeuristicOne, 1));


        try {
            dumpResults("nServers",results,file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void experiment5()
    {
        ArrayList<Results> r = new ArrayList<>();

        int seed;
        for (int repetition = 1; repetition <= 10; ++repetition) {
            seed = repetition;
            r.add(Controller.Hill_Climbing(seed, usualNusers, usualNrequests,
                    usualNservers, usualMinReplications, settledSolIni, settledOperatorS, usualHeuristicTwo, 2));
        }

        try {
            File file = new File("data/experiment5.txt");
            file.delete();
            file.createNewFile();
            dumpResults("Hill Climbing: Criterion 2",r,file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void experiment6()
    {
        ArrayList<Results> r1 = new ArrayList<>();
        ArrayList<Results> r2 = new ArrayList<>();

        int seed;
        for (int repetition = 1; repetition <= 10; ++repetition) {
            seed = repetition;
            r1.add(Controller.Simmulated_Annealing(seed, usualNusers, usualNrequests,
                    usualNservers, usualMinReplications, settledSolIni, settledOperatorS,
                    usualHeuristicOne, 1, settledSteps, settledStiter, settledK, settledLamb));
            r2.add(Controller.Simmulated_Annealing(seed, usualNusers, usualNrequests,
                    usualNservers, usualMinReplications, settledSolIni, settledOperatorS,
                    usualHeuristicTwo, 2, settledSteps, settledStiter, settledK, settledLamb));
        }

        try {
            File file = new File("data/experiment6_2.txt");
            file.delete();
            file.createNewFile();
            dumpResults("Simulated Annealing: Criterion 1",r1,file);
            dumpResults("Simulated Annealing: Criterion 2",r2,file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void experiment7() {
        int seed = 1;
        int minReplications = 5;    // per file
        ArrayList<Results> r1B = new ArrayList<>();
        ArrayList<Results> r2A = new ArrayList<>();


        for (int repetition = 1; repetition <= 5; ++repetition) {
            r1B.add(Controller.Hill_Climbing(seed,usualNusers,usualNrequests,usualNservers,minReplications,
                    settledSolIni,settledOperatorS,usualHeuristicOne,1));
            r2A.add(Controller.Hill_Climbing(seed, usualNusers, usualNrequests, usualNservers, minReplications,
                    settledSolIni, settledOperatorS, usualHeuristicTwo, 2));
            minReplications += 5;
        }

        try {
            File file = new File("data/experiment7.txt");
            file.delete();
            file.createNewFile();
            dumpResults("Simulated Annealing: Criterion 1",r1B,file);
            dumpResults("Simulated Annealing: Criterion 2",r2A,file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void experiment8() {}

    private static void debugging()     // Program here what you want to debug.
    {
        int seed = 1234;
        Results results;
        results = Controller.Hill_Climbing(seed,
        usualNusers, usualNrequests, usualNservers, usualMinReplications,
        settledSolIni, settledOperatorS, usualHeuristicOne, 1);
        System.out.println(results.compareData());
        results = Controller.Hill_Climbing(seed,
                usualNusers, usualNrequests, usualNservers, usualMinReplications,
                settledSolIni, settledOperatorS, usualHeuristicTwo, 2);
        System.out.println(results.compareData());
    }

    public static void main(String[] args)
    {
        int experiment;
        try {
            System.out.print("Introduce the experiment number.\n" +
                    "Experiment 1: operator sets\n" + "Experiment 2: initial solutions\n" +
                    "Experiment 3: simulated annealing\n" + "Experiment 4: nUsers & nServers (long)\n" +
                    "Experiment 5: criteria comparative(HC)\n" + "Experiment 6: criteria comparative(SA)\n" +
                    "Experiment 7:\n" + "Experiment 8:\n" +
                    "Special Experiment 0\n" + "Debugging with code 66\n");
            Scanner scanner = new Scanner(System.in);
            experiment = scanner.nextInt();
        } catch (RuntimeException e) {
            experiment = 99;
        }

        File directory = new File("./data");
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
                experiment5();
                break;
            case 6:
                experiment6();
                break;
            case 7:
                experiment7();
                break;
            case 8:
                System.out.println("You just have been pranked");
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