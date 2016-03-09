/*!
 * Representacion del estado de la practica 1
 */
import IA.DistFS.Requests;
import IA.DistFS.Servers;

import java.util.Scanner;

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

    public Practica1Board(int users, int requs, int servs, int repls, int seed) {
        this.nUser = users;
        this.nServ = servs;
        this.nReq = requs;
        this.nRepl = repls;
        this.seed = seed;
        this.requests = new Requests(users,requs,seed);
        try {
            this.servers = new Servers(servs,repls,seed);
        } catch (Servers.WrongParametersException e) {
            e.printStackTrace();
        }
    }

     public static void main(String[] args){

         Scanner s = new Scanner(System.in);
        System.out.println("Introduce seed, numero de usuarios, numero de requests, numero de servers, numero de replicaciones:");
         int seed = s.nextInt();
         int nUsers = s.nextInt();
         int nRequs = s.nextInt();
         int nServ = s.nextInt();
         int nRepls = s.nextInt();
         Practica1Board board = new Practica1Board(nUsers,nRequs,nServ,nRepls,seed);
         System.out.println("Ahora haré print de todos los datos para comprobar que realmente crea lo que queremos:");
        for (int i = 0; i < nServ; ++i){
            System.out.print(board.requests.getRequest(i)[0] + " ");
            System.out.println(board.requests.getRequest(i)[1]);
        }
     }

}