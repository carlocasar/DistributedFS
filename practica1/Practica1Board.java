/*
 * Representacion del estado de la practica 1
 */
import IA.DistFS.Requests;
import IA.DistFS.Servers;

import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;
import java.util.Vector;

public class Practica1Board {
    private int nServ; //Numero de servidores
    private int nUser; //Numero de usuarios
    private int nUsMaxReq; //Numero de requests maximas por usuario
    private int nReq; //Numero de requests
    private int nRepl; //Numero de replicaciones minimas
    private int seed;
    private Vector<Integer> servReq; //Un vector de nReq posiciones indicando en cada posición, equivalente al request, a que servidor lo envia
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

     /*public static void main(String[] args){

         Scanner s = new Scanner(System.in);
        System.out.println("Introduce seed, numero de usuarios, numero de requests, numero de servers, numero de replicaciones:");
         int seed = s.nextInt();
         int nUsers = s.nextInt();
         int nRequs = s.nextInt();
         int nServ = s.nextInt();
         int nRepls = s.nextInt();
         Practica1Board board = new Practica1Board(nUsers,nRequs,nServ,nRepls,seed);
         System.out.println("Ahora haré print de todos los datos para comprobar que realmente crea lo que queremos:");
         System.out.println("Número de requests totales: " + board.requests.size());
        for (int i = 0; i < board.requests.size(); ++i){
            System.out.print(board.requests.getRequest(i)[0] + " ");
            System.out.println(board.requests.getRequest(i)[1]);
        }
         board.servReq = solIni1(board);
         System.out.println("Ahora saco por pantalla las requests y a que servidor iran:");
         for (int i = 0; i < board.requests.size(); ++i){
             System.out.println("Request " + i + " servidor " + board.servReq.get(i));
         }
     }*/

    /*
    Este algoritmo encuentra una solución muy sencilla: pregunta que servidores contienen el archivo del que se hace
     request y, al primero que encuentra, le envia la request.
     */
    public Vector<Integer> solIni1(Practica1Board board){
        Vector<Integer> servReq = new Vector<Integer>(board.requests.size());
        //vamos a iterar sobre todas las request para asignarles un servidor
        for (int i = 0; i < board.requests.size(); ++i) {
            //aqui se coge el archivo
            int fileReq = board.requests.getRequest(i)[1];
            //se pregunta en que servidores esta
            Set<Integer> set = board.servers.fileLocations(fileReq);
            //aqui se hace el proceso de iterar sobre el set. No se hace mas que una vez porque es un algoritmo de
            //mierda, pero se tendria que hacer un for para iterar sobre los demas
            Iterator<Integer> it = set.iterator();
            Integer server = it.next();
            servReq.add(i, server);
        }
        return servReq;
    }

    public Vector<Integer> solIni2(Practica1Board board){
        Vector<Integer> servReq = new Vector<Integer>(board.requests.size());
        Vector<Integer> numReqServ = new Vector<Integer>(board.servers.size(),0);
        //vamos a iterar sobre todas las request para asignarles un servidor
        for (int i = 0; i < board.requests.size(); ++i) {
            //aqui se coge el archivo
            int fileReq = board.requests.getRequest(i)[1];
            //se pregunta en que servidores esta
            Set<Integer> set = board.servers.fileLocations(fileReq);
            //aqui se hace el proceso de iterar sobre el set. No se hace mas que una vez porque es un algoritmo de
            //mierda, pero se tendria que hacer un for para iterar sobre los demas
            int min = 0;
            Integer aux = 0;
            Iterator<Integer> it = set.iterator();
            while(it.hasNext()) {
                Integer server = it.next();
                if(min < numReqServ.get(board.servers.hashCode())){
                    min = numReqServ.get(board.servers.hashCode());
                    aux = server;
                }
            }
            servReq.add(i, aux);
        }
        return servReq;
    }

}