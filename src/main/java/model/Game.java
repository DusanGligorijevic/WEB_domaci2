package model;

import java.util.Random;

public class Game implements Runnable{
    private int rounds=0;
    private boolean[] sticks;
    private int playerOnMove=-1;
    private Table table;
    private int maxBorder=6;

    public Game(Table table){
        this.table=table;
    }
    @Override
    public void run() {
        System.out.println("Igra počinje");
        while(table.getMaxPlayers()!=1) {
            System.out.println("Pocinje "+ ++rounds+". runda.");
            createSticks();
            shuffleSticks(sticks);
            System.out.println("Štapići su promešani");
            System.out.println("Na potezu je " + ++playerOnMove + ". igrač.");
            if(table.checkPlayer(playerOnMove)) {
                maxBorder--;
                continue;
            }
            table.bet(playerOnMove);
            System.out.println("Igraci su polozili svoje opklade, kraci stapic je stapic broj "+ table.returnFalseStick(sticks));
            int bet = table.chooseStick(playerOnMove, sticks,maxBorder);
            if (sticks[bet] == false) {
                System.out.println("Kraj runde, igrac je izabrao kraci stapic. Igrac ispada.");
                table.switchPlayers(playerOnMove);
                table.setMaxPlayers(table.getMaxPlayers()-1);
                maxBorder=table.getMaxPlayers();
                rounds=0;
                playerOnMove=-1;
                System.out.println("Igrac je izbacen, ostalo je jos "+ table.getMaxPlayers() + " igraca.");
            } else {
                System.out.println("Na potezu je sledeci igrac.");
                maxBorder--;
                System.out.println("ostalo je jos "+maxBorder+" rundi ");
            }
        }
        table.showWinner();


    }
    public void createSticks(){
        sticks = new boolean[maxBorder];
        sticks[0]=false;
        for(int i = 1; i < maxBorder; i++){
            sticks[i]=true;
        }
    }
    public void shuffleSticks(boolean[] sticks){
        Random r = new Random();
        for(int i = 0; i < maxBorder; i++){
            if(sticks[i]==false){
                sticks[i]=true;
                sticks[r.nextInt(maxBorder)]=false;
                break;
            }
        }
    }
}
