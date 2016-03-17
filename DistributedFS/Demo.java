
import aima.search.framework.Problem;
import aima.search.framework.SuccessorFunction;

public class Demo {

    public static void HillClimbing(int users, int reqs, int servs, int repls, int seed, int iSol, int sGen) {
        System.out.println("describir la demo, sus parametros, o algo");

        Board initSol = new Board(users,reqs,servs,repls,seed);
        switch (iSol) {}

        SuccessorFunction sucGen;
        switch (sGen) {
            case 1 : sucGen = new SuccessorFunctionHC1();
                break;
            case 2 : sucGen = new SuccessorFunctionHC2();
                break;
            case 3 : sucGen = new SuccessorFunctionHC3();
                break;
            default : sucGen = null;
        }

        try {
            Problem e = new Problem(initSol, sucGen, new GoalFalseTest());

            /* ¿? */

        } catch (Exception var4) {
            var4.printStackTrace();
        }
    }

    public static void SimmulatedAnnealing(/*parametros*/) {}

}