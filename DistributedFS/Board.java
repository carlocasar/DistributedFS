/*
 * Representacion del estado, soluciones iniciales, operadores.
 */

import IA.DistFS.Requests;
import IA.DistFS.Servers;

import java.util.*;

public class Board {
    private static int nServers;                // Number of servers
    private static int nUsers;                  // Number of users
    private static int nRequests;               // Number of requests
    private static Requests requests;           // Requests
    private static Servers servers;             // Servers
    private static int criterio;                // criterio de calidad de la solucion,
                                                // condiciona los atributos materializados.
    private ArrayList<Integer> assignations;    // assignations : request -> server.

    private ArrayList<Integer> serverTimes;     // serverTimes : server -> server transmission time.
    private int totalTransmissionTime;          // sum of server times.
    private int totalSquareTime;                // sum of server times^2, needed for std deviation.
    private int maxServerTime;                  // maximum of server times.



    private int maxTimeServers;                 // number of servers with maxServerTime.


    public Board(int users, int requs, int servs, int repls, int seed, int crit)
    {
        criterio = crit;
        nUsers = users;
        nServers = servs;
        nRequests = requs;

        requests = new Requests(users,requs,seed);
        try {
            servers = new Servers(servs,repls,seed);
        } catch (Servers.WrongParametersException e) {
            e.printStackTrace();
        }

        serverTimes = new ArrayList<Integer>();
        for (int i = 0; i < nServers; ++i) serverTimes.add(0);
        totalTransmissionTime = totalSquareTime = maxServerTime = maxTimeServers = 0;

        // criterio = parametro de entrada!
    }

    public Board(Board original)
    {
        nServers = original.getnServers();
        nUsers = original.getnUsers();
        nRequests = original.getnRequests();
        requests = original.getRequests();
        servers = original.getServers();
        assignations = new ArrayList<Integer>(original.getAssignations());
        criterio = original.getCriterio();
        serverTimes = new ArrayList<Integer>(original.getServerTimes());
        maxServerTime = original.getMaxServerTime();
        totalTransmissionTime = original.getTotalTransmissionTime();
        totalSquareTime = original.getTotalSquareTime();
        maxTimeServers = original.getMaxTimeServers();
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
         board.assignations = solIni1(board);
         System.out.println("Ahora saco por pantalla las requests y a que servidor iran:");
         for (int i = 0; i < board.requests.size(); ++i){
             System.out.println("Request " + i + " servidor " + board.assignations.get(i));
         }
     }*/

    /*
    Este algoritmo encuentra una solución muy sencilla: pregunta que servidores contienen el archivo del que se hace
     request y, al primero que encuentra, le envia la request.
     */
    public void solIni1(){
        assignations = new ArrayList<Integer>(requests.size());
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
            assignations.add(i, server);

        }
        initServerTimes();
        reloadMaxTime();
        System.out.println(serverTimes);
        //return assignations;
    }

    public void solIni2(){
        assignations = new ArrayList<Integer>(requests.size());
        ArrayList<Integer> numReqServ = new ArrayList<Integer>(Collections.nCopies(servers.size(), 0));
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
            numReqServ.set(aux, aux + 1);
            assignations.add(i, aux);
        }
        //return assignations;
    }

    public void solIni3(){
        assignations = new ArrayList<Integer>(requests.size());
        Map<Integer, Iterator<Integer>> fileDic = new HashMap<Integer, Iterator<Integer>>();
        //vamos a iterar sobre todas las request para asignarles un servidor
        for (int i = 0; i < requests.size(); ++i) {
            //aqui se coge el archivo
            int fileReq = requests.getRequest(i)[1];

            if(! fileDic.containsKey(fileReq))
                fileDic.put(fileReq,servers.fileLocations(fileReq).iterator());
            Iterator<Integer> it = fileDic.get(fileReq);
            if(!it.hasNext()) it = servers.fileLocations(fileReq).iterator();
            Integer server = it.next();
            assignations.add(i, server);
        }
        initServerTimes();
        reloadMaxTime();
        System.out.println(serverTimes);
        //return assignations;
    }

    private void initMaterialized() {
        initServerTimes();
        if (criterio == 1) reloadMaxTime();
        else if (criterio == 2) initTotalSquaredTime();
    }

    private void initServerTimes() {
        totalTransmissionTime = 0;

        for (int i = 0; i < requests.size(); ++i) {
            int user = requests.getRequest(i)[0];
            int server = assignations.get(i);
            int requestTime = servers.tranmissionTime(server,user);
            totalTransmissionTime += requestTime;
            serverTimes.set(server,(serverTimes.get(server) + requestTime));
        }
    }

    private void initTotalSquaredTime() {
        totalSquareTime = 0;

        for (int i = 0; i < nServers; ++i)
            totalSquareTime += serverTimes.get(i) * serverTimes.get(i);
    }

    public void move(int request, int newServer)
    {
        if (criterio == 1) move1(request,newServer);
        else if (criterio == 2) move2(request,newServer);
    }

    public void swap(int req1, int req2)
    {
        if (criterio == 1) swap1(req1,req2);
        else if (criterio == 2) swap2(req1,req2);
    }

    private void move1(int request, int newServer) {
        int user = requests.getRequest(request)[0];
        int oldServer = assignations.get(request);

        int time1 = serverTimes.get(oldServer);
        int time2 = time1 - servers.tranmissionTime(oldServer, user);
        totalTransmissionTime -= servers.tranmissionTime(oldServer, user);
        serverTimes.set(oldServer, time2);

        assignations.set(request, newServer);

        time2 = serverTimes.get(newServer);
        time2 += servers.tranmissionTime(newServer, user);
        totalTransmissionTime += servers.tranmissionTime(newServer, user);
        serverTimes.set(newServer, time2);

        processMaxTimes(time1,time2);
    }

    private void swap1(int req1, int req2) {
        int user1 = requests.getRequest(req1)[0];
        int user2 = requests.getRequest(req2)[0];
        int serv1 = assignations.get(req1);
        int serv2 = assignations.get(req2);

        int time1 = serverTimes.get(serv1);
        totalTransmissionTime -= time1;
        int time2 = time1 - servers.tranmissionTime(serv1, user1);
        assignations.set(req2, serv1);
        time2 += servers.tranmissionTime(serv1, user2);
        totalTransmissionTime += time2;
        serverTimes.set(serv1, time2);
        processMaxTimes(time1, time2);

        time1 = serverTimes.get(serv2);
        totalTransmissionTime -= time1;
        time2 = time1 - servers.tranmissionTime(serv2, user2);
        assignations.set(req1, serv2);
        time2 += servers.tranmissionTime(serv2, user1);
        totalTransmissionTime += time2;
        serverTimes.set(serv2, time2);
        processMaxTimes(time1, time2);
    }

    private void processMaxTimes(int time1, int time2) {
        if (time2 > maxServerTime) {
            maxServerTime = time2;
            maxTimeServers = 1;
        }
        else {
            if (time2 == maxServerTime) ++maxTimeServers;
            if (time1 == maxServerTime) {
                --maxTimeServers;
                if (maxTimeServers == 0) reloadMaxTime();
            }
        }
    }

    private void reloadMaxTime() {
        maxServerTime = 0;
        maxTimeServers = 0;

        for (int i = 0; i < nServers; ++i) {
            if (serverTimes.get(i) == maxServerTime) ++maxTimeServers;
            else if (serverTimes.get(i) > maxServerTime) {
                maxServerTime = serverTimes.get(i);
                maxTimeServers = 1;
            }
        }
    }

    private void move2(int request, int newServer)
    {
        int user = requests.getRequest(request)[0];
        int oldServer = assignations.get(request);

        int time = serverTimes.get(oldServer);
        totalSquareTime -= (time*time);
        time -= servers.tranmissionTime(oldServer,user);
        totalTransmissionTime -= servers.tranmissionTime(oldServer,user);
        serverTimes.set(oldServer,time);
        totalSquareTime += (time*time);

        assignations.set(request,newServer);

        time = serverTimes.get(newServer);
        totalSquareTime -= (time*time);
        time += servers.tranmissionTime(newServer,user);
        totalTransmissionTime += servers.tranmissionTime(newServer,user);
        serverTimes.set(newServer,time);
        totalSquareTime += (time*time);
    }

    private void swap2(int req1, int req2)
    {
        int user1 = requests.getRequest(req1)[0];
        int user2 = requests.getRequest(req2)[0];
        int serv1 = assignations.get(req1);
        int serv2 = assignations.get(req2);

        int time = serverTimes.get(serv1);
        totalTransmissionTime -= time;
        totalSquareTime -= (time*time);
        time -= servers.tranmissionTime(serv1,user1);
        assignations.set(req2,serv1);
        time += servers.tranmissionTime(serv1,user2);
        totalSquareTime += (time*time);
        totalTransmissionTime += time;
        serverTimes.set(serv1,time);

        time = serverTimes.get(serv2);
        totalTransmissionTime -= time;
        totalSquareTime -= (time*time);
        time -= servers.tranmissionTime(serv2,user2);
        assignations.set(req1,serv2);
        time += servers.tranmissionTime(serv2,user1);
        totalSquareTime += (time*time);
        totalTransmissionTime += time;
        serverTimes.set(serv2,time);
    }

    public Servers getServers() {
        return servers;
    }

    public Requests getRequests() {
        return requests;
    }

    public ArrayList<Integer> getAssignations() {
        return assignations;
    }

    public int getnServers() {
        return nServers;
    }

    public int getnUsers() {
        return nUsers;
    }

    public int getnRequests() {
        return nRequests;
    }

    public ArrayList<Integer> getServerTimes() {
        return serverTimes;
    }

    public int getTotalTransmissionTime() {
        return totalTransmissionTime;
    }

    public int getTotalSquareTime() {
        return totalSquareTime;
    }

    public int getMaxServerTime() {
        return maxServerTime;
    }

    public int getCriterio() {
        return criterio;
    }

    public int getMaxTimeServers() {return maxTimeServers;    }

    public String toString() {
        // retorna estat explicat en un string
        String s = "";
        int numServ;
        for (int i = 0; i < assignations.size(); i++) {
            numServ = assignations.get(i);
            s = s.concat("request " + i + " server " + numServ + " | ");
        }
        s = s.concat("\n");
        s = s.concat(String.valueOf(maxServerTime));
        return s;
    }
}