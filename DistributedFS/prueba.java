/**
 * Created by gerard.otero on 05/04/2016.
 */
public class prueba {
    public static void main(String args[]){
        Integer servs = 50;
        Integer maxTime = 5000;
        Integer reqs = 1000;
        Integer timeS =  maxTime * 500;
        double squared = (long) timeS*timeS*2;
        System.out.println(squared);
        double sMean = squared / servs;
        System.out.println(sMean);
        double mean = (reqs * maxTime) / servs;
        System.out.println(mean);
        double meanS = mean * mean;
        System.out.println(meanS);
        double variance = sMean - meanS;
        System.out.println(variance);
        double stdev = java.lang.Math.sqrt(variance);
        System.out.println(stdev);        // hasta aquí guay
    }
}

// 200 users
// 5 reqs/user (max)
// maxTransmisionTime = 5000

/*
Board board = (Board) o;
        int nServers = board.getnServers();
        int totalTransmissionTime = board.getTotalTransmissionTime();
        long totalSquareTime = board.getTotalSquareTime();

        double mean = totalTransmissionTime / (double)nServers;
        double variance = (totalSquareTime / (double)nServers) - mean * mean;
        double stdev = java.lang.Math.sqrt(variance);

        return mean + stdev;
 */