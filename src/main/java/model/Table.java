package model;


import java.util.Random;

public class Table {
    int maxPlayers = 6;
    private Player[] players;

    public Table() {
        this.players = new Player[maxPlayers];
    }

    public synchronized boolean giveSeat(Player player) {
        for (int i = 0; i < maxPlayers ; i++) {
            if(players[i] == null) {
                players[i] = player;
                player.setNumberOfSeat(i+1);
                return true;
            }
        }
        return false;
    }

    public void bet(int n){
        Random r = new Random();
        for( int i = 0; i < maxPlayers; i++){
            if(i==n) continue;
            players[i].setBet(r.nextBoolean());
            System.out.println("Player "+ players[i].getId()+" says "+ players[i].isBet());
        }
    }
    public int  chooseStick(int n, boolean[] sticks, int maxBorder){
        Random r = new Random();
        int bet=r.nextInt(maxBorder);
        System.out.println("Player "+ players[n].getId()+ " pulls stick "+ bet);
        if(sticks[bet]==false){
            System.out.println("Wrong stick! Player "+ players[n].getId()+ " is out.");
        }else{
            System.out.println("Normal stick!");
        }
        for( int i=0; i<maxBorder;i++){
            if(players[i].isBet()==sticks[bet]){
                players[i].setPoints(players[i].getPoints()+1);
            }
        }
        return bet;
    }
    public int returnFalseStick(boolean[] sticks){
        for( int i = 0; i < maxPlayers; i++){
            if(sticks[i]==false) return i;
        }
        return -1;
    }

    public boolean checkPlayer(int n){

            if(players[n]==null) return true;

        return false;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }
    public void switchPlayers(int n){
        players[n]=players[maxPlayers-1];
        players[maxPlayers-1]=null;
    }
    public void showWinner(){
        System.out.println("Pobednik je "+players[0].getId()+", osvojio je "+ players[0].getPoints()+" poena.");
    }
}
