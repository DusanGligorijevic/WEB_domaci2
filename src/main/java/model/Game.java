package model;

import server.ServerMain;

import java.util.Random;
import java.util.concurrent.Semaphore;

import static java.lang.Thread.sleep;

public class Game implements Runnable{
    private int rounds=0;
    private int partija=1;
    private boolean[] sticks;
    private int numberOfSticks=6;
    private int playerOnMove=-1;
    private Table table;
    private int maxBorder=6;
    Semaphore semaphore;

    public Game(Table table, Semaphore semaphore){
        this.table=table;
        this.semaphore=semaphore;
    }
    @Override
    public void run() {
        try {
            Thread.currentThread().sleep(5000);
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("Igra počinje");
        System.out.println(table.getIgraciKojiCekaju().size()+" igraca cekaju...");
        createSticks();
        //Igra se sve dok postoje igraci koji cekaju
        while(!table.isExit()) {
            shuffleSticks(sticks);
            System.out.println("Pocinje " + ++rounds + ". runda " + partija + ". partije!");
            System.out.println("Štapići su promešani");
            /// za slucaj da se u jednoj rundi ne uzvuce kraci stapic, igra se nova runda i prvi igrac je na potezu
            if(playerOnMove==5) playerOnMove=-1;
            System.out.println("Na potezu je " + ++playerOnMove + ". igrač.");
            table.bet(playerOnMove); //igraci pogadjaju ishod
            System.out.println("Igraci su polozili svoje opklade, kraci stapic je stapic broj " + table.returnFalseStick(sticks, numberOfSticks));
            int bet = table.chooseStick(playerOnMove, sticks, numberOfSticks); //Igtrac izvlaci stapic
            if (sticks[bet] == false) {
                System.out.println("Kraj runde, igrac je izabrao kraci stapic. Igrac ispada.");
                System.out.println("---------------------------------------------------------------------------------");
                table.switchPlayers(playerOnMove);
                playerOnMove = -1;
                numberOfSticks = 6;
            } else {
                System.out.println("Na potezu je sledeci igrac.");
            }
            //posle svake partije ispisujemo pobednika
            if (rounds % 6 == 0) {
                partija++;
                rounds=0;
                table.showWinner();
                System.out.println("################################################################################");
                if (table.getIgraciKojiCekaju().size() == 0)
                    break;
                table.setPlayersPoints();
            }
        }

    }
    public void createSticks(){
        Random r = new Random();
        sticks = new boolean[numberOfSticks];
        for(int i = 1; i < numberOfSticks; i++){
            sticks[i]=true;
        }
        sticks[r.nextInt(numberOfSticks)]=false;
    }
    public void shuffleSticks(boolean[] sticks){
        Random r = new Random();
        for(int i = 0; i < numberOfSticks; i++){
            if(sticks[i]==false){
                sticks[i]=true;
                sticks[r.nextInt(numberOfSticks)]=false;
                break;
            }
        }
    }
}
