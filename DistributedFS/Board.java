/*
 * Representacion del estado y soluciones iniciales.
 */

import IA.DistFS.Requests;
import IA.DistFS.Servers;

import java.util.Iterator;
import java.util.Set;
import java.util.ArrayList;

public class Board {
    private static int nServ; //Numero de servidores
    private static int nUser; //Numero de usuarios
    private static int nReq; //Numero de requests
    private static int nUsMaxReq; //Numero de requests maximas por usuario
    private static int nRepl; //Numero de replicaciones minimas
    private static Requests requests; //Requests
    private static Servers servers; //Servidores

    private ArrayList<Integer> assigs; //Un vector de nReq posiciones indicando en cada posición, equivalente al request, a que servidor lo envia

    public Board(int users, int requs, int servs, int repls, int seed)
    {
        nUser = users;
        nServ = servs;
        nReq = requs;
        nRepl = repls;
        requests = new Requests(users,requs,seed);
        try {
            servers = new Servers(servs,repls,seed);
        } catch (Servers.WrongParametersException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < requests.size(); ++i){
            System.out.println(requests.getRequest(i)[1]);
            Set<Integer> set = servers.fileLocations(requests.getRequest(i)[1]);
            Iterator<Integer> it = set.iterator();
            while(it.hasNext()) {
                Integer server = it.next();
                System.out.print(server + " ");
            }
            System.out.println();
        }
    }

    public Board(Board original)
    {
        this.assigs = new ArrayList<Integer>(original.assigs);
        // + materializados aun no definidos
    }

    public void move(int req, int serv)
    {
        assigs.set(req,serv);
        // + materializados aun no definidos
    }

    public void swap(int req1, int req2)
    {
        int aux = assigs.get(req1);
        assigs.set(req1,assigs.get(req2));
        assigs.set(req2,aux);
        // + materializados aun no definidos
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
         board.assigs = solIni1(board);
         System.out.println("Ahora saco por pantalla las requests y a que servidor iran:");
         for (int i = 0; i < board.requests.size(); ++i){
             System.out.println("Request " + i + " servidor " + board.assigs.get(i));
         }
     }*/

    /*
    Este algoritmo encuentra una solución muy sencilla: pregunta que servidores contienen el archivo del que se hace
     request y, al primero que encuentra, le envia la request.
     */
    public ArrayList<Integer> solIni1(){
        ArrayList<Integer> assigs = new ArrayList<Integer>(requests.size());
        //vamos a iterar sobre todas las request para asignarles un servidor
        for (int i = 0; i < requests.size(); ++i) {
            //aqui se coge el archivo
            int fileReq = requests.getRequest(i)[1];
            //se pregunta en que servidores esta
            Set<Integer> set = servers.fileLocations(fileReq);
            //aqui se hace el proceso de iterar sobre el set. No se hace mas que una vez porque es un algoritmo de
            //mierda, pero se tendria que hacer un for para iterar sobre los demas
            Iterator<Integer> it = set.iterator();
            Integer server = it.next();
            assigs.add(i, server);
        }
        return assigs;
    }

    public ArrayList<Integer> solIni2(){
        ArrayList<Integer> assigs = new ArrayList<Integer>(requests.size());
        ArrayList<Integer> numReqServ = new ArrayList<Integer>(servers.size());
        //vamos a iterar sobre todas las request para asignarles un servidor
        for (int i = 0; i < requests.size(); ++i) {
            //aqui se coge el archivo
            int fileReq = requests.getRequest(i)[1];
            //se pregunta en que servidores esta
            Set<Integer> set = servers.fileLocations(fileReq);
            //aqui se hace el proceso de iterar sobre el set. No se hace mas que una vez porque es un algoritmo de
            //mierda, pero se tendria que hacer un for para iterar sobre los demas
            int min = 0;
            Integer aux = 0;
            Iterator<Integer> it = set.iterator();
            while(it.hasNext()) {
                Integer server = it.next();
                if(min < numReqServ.get(server)){
                    min = numReqServ.get(server);
                    aux = server;
                }
            }
            assigs.add(i, aux);
        }
        return assigs;
    }


    public Servers getServers() {
        return servers;
    }

    public int getnServ() {
        return nServ;
    }

    public int getnUser() {
        return nUser;
    }

    public int getnUsMaxReq() {
        return nUsMaxReq;
    }

    public int getnReq() {
        return nReq;
    }

    public int getnRepl() {
        return nRepl;
    }

    public ArrayList<Integer> getServReq() {
        return assigs;
    }

    public Requests getRequests() {
        return requests;
    }
}