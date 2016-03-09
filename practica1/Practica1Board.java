/*!
 * REpresentacion del estado de la practica 1
 */
import aima.*;
import IA.DistFS.*;
package practica1;

public class Practica1Board {
    private int nServ; //Numero de servidores
    private int nUser; //Numero de usuarios
    private int nUsMaxReq; //Numero de requests maximas por usuario
    private int nReq; //Numero de requests
    private int nRepl; //Numero de replicaciones minimas
    private int seed;
    private int servReq[]; //Un vector de nReq posiciones indicando en cada posición, equivalente al request, a que servidor lo envia
    private Requests requests; //Requests
    private Servers servers; //Servidores

    public Practica1Board(int users, int requs, int servs, int repls, int seed){
        this.nUser = users;
        this.nServ = servs;
        this.nReq = requs;
        this.nRepl = repls;
        this.seed = seed;
        this.requests = new Requests(users,requs,seed);
        this.servers = new Servers(servs,repls,seed);
    }

     public static void main(String[] args){

     }

}