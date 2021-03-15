package model;


import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

public class Table {
    int maxPlayers = 6;
    private Player[] players;
    private ArrayList<Player> igraciKojiCekaju;
    private int next=0; //koristi se pri ubacivanju za sto igraca koji ceka
    private boolean exit=false;

    public Table() {
        this.players = new Player[maxPlayers];
        this.igraciKojiCekaju = new ArrayList<>();
    }

    public synchronized boolean giveSeat(Player player) {
        for (int i = 0; i < 6 ; i++) {
            if(players[i] == null) {
                players[i] = player;
                player.setNumberOfSeat(i+1);
                return true;
            }
        }
        return false;
    }
     public synchronized void giveNumber(Player player){
        igraciKojiCekaju.add(player);
    }
    //Svi igraci sem igraca koji je na potezu prognoziraju kakav ce stapic biti
    public void bet(int n){
        Random r = new Random();
        for( int i = 0; i < 6; i++){
            if(i==n) continue;
            players[i].setBet(r.nextBoolean());
            System.out.println("Player "+ players[i].getId()+" says "+ players[i].isBet());
        }
    }
    //igrac izvlaci stapic
    public int  chooseStick(int n, boolean[] sticks, int numberOfSticks){
        Random r = new Random();
        int bet=r.nextInt(numberOfSticks);
        System.out.println("Igrac "+ players[n].getId()+ " izvlaci stapic "+ bet);
        if(sticks[bet]==false){
            System.out.println("Pogresan stapic! Igrac "+ players[n].getId()+ " ispada!");
        }else{
            System.out.println("Normalan stapic!");
        }
        //setuju se poeni svih igraca koji su pogodili
        for( int i=0; i<6;i++){
            if(players[i].isBet()==sticks[bet]){
                players[i].setPoints(players[i].getPoints()+1);
            }
        }
        return bet;
    }
    public int returnFalseStick(boolean[] sticks, int numberOfSticks){
        for( int i = 0; i < numberOfSticks; i++){
            if(sticks[i]==false) return i;
        }
        return -1;
    }
    //kada igrac izvuce kratki stapic on odlazi sa stola, a umesto njega dolazi igrac koji ceka

    /**
     * Ukoliko bude uhvacen indexoutofbounds exception, to znaci da nema vise igraca koji cekaju,
     * i da se igra zavrsila, prikazuje se igrac koji je do tog trenutka imao najvise poena u poslednjoj partiji.
     */
    public synchronized  void switchPlayers(int n) {
            players[n]=null;
            try {
                players[n] = igraciKojiCekaju.get(next++);
            }catch(IndexOutOfBoundsException e){
                System.out.println("Nema više igrača koji čekaju, kraj partije!");
                players[n] = new Player(UUID.randomUUID());
                setExit(true);
                showWinner();
                return;
            }
            System.out.println("promena!");
            System.out.println("novi igrac je "+ players[n].getId());

    }
    public void showWinner(){
        int max=0;
        Player player=null;
        for(int i = 0; i<6; i++) {
            if ( players[i].getPoints() > max) {
                max = players[i].getPoints();
                player=players[i];
            }
        }
        System.out.println("Pobednik je "+ player.getId()+", osvojio je "+ player.getPoints() + " poena.");
    }


    public ArrayList<Player> getIgraciKojiCekaju() {
        return igraciKojiCekaju;
    }
    public void setPlayersPoints(){
        for(int i = 0; i < 6; i++){
            if(players[i]!=null)
            players[i].setPoints(0);
        }
    }

    public void setExit(boolean exit) {
        this.exit = exit;
    }

    public boolean isExit() {
        return exit;
    }
}
