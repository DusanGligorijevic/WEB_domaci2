package model;

import java.util.UUID;

public class Player {

    private UUID id;
    private int numberOfSeat;
    private int points;
    private boolean bet;

    public Player(UUID id) {
        this.id = id;
    }

    public int getPoints() {
        return points;
    }

    public void addPoint() {
        points++;
    }

    public UUID getId() {
        return id;
    }

    public int getNumberOfSeat() {
        return numberOfSeat;
    }

    public void setNumberOfSeat(int numberOfSeat) {
        this.numberOfSeat = numberOfSeat;
    }

    public boolean isBet() {
        return bet;
    }

    public void setBet(boolean bet) {
        this.bet = bet;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
