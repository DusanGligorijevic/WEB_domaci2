package client;

import java.io.IOException;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ClientMain {
    private static int numberOfPlayers;
    private static int arrivalTime;

    public static void main(String[] args) throws IOException {
        System.out.println("Unesite broj igrača: ");
        Scanner scanner = new Scanner(System.in);
        numberOfPlayers= Integer.parseInt(scanner.nextLine());
        System.out.println(numberOfPlayers+ " igrača pokušavaju da pristupe stolu.");
        Random r = new Random();
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(10);
        for (int i = 0; i < numberOfPlayers; i++) {
            arrivalTime = r.nextInt(1001);
            scheduledExecutorService.schedule(new ClientThread(), arrivalTime, TimeUnit.MILLISECONDS);
        }
        scheduledExecutorService.shutdown();
    }
}
