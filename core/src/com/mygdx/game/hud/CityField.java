package com.mygdx.game.hud;

public class CityField {

    private int boardPosition;
    private String name;
    private int value;
    private int rentPrice;
    private int mortgagePrice;
    private boolean isOwned;
    private Player ownedBy;

    public CityField(int boardPosition, String name, int value, int rentPrice, int mortgagePrice) {
        this.boardPosition = boardPosition;
        this.name = name;
        this.value = value;
        this.rentPrice = rentPrice;
        this.mortgagePrice = mortgagePrice;
        this.isOwned = false;
        this.ownedBy = null;  //na pocetku igre niti jedno polje nema vlasnika
    }


    public int getBoardPosition() {
        return boardPosition;
    }

    public void setBoardPosition(int boardPosition) {
        this.boardPosition = boardPosition;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getRentPrice() {
        return rentPrice;
    }

    public void setRentPrice(int rentPrice) {
        this.rentPrice = rentPrice;
    }

    public int getMortgagePrice() {
        return mortgagePrice;
    }

    public void setMortgagePrice(int mortgagePrice) {
        this.mortgagePrice = mortgagePrice;
    }

    public boolean isOwned() {
        return isOwned;
    }

    public void setIsOwned(boolean owned) {
        isOwned = owned;
    }


    public Player getOwnedBy() {
        return ownedBy;
    }

    public void setOwnedBy(Player ownedBy) {
        this.ownedBy = ownedBy;
    }


    @Override
    public String toString() {
        return "Property{" +
                "boardPosition=" + boardPosition +
                ", name='" + name + '\'' +
                ", value=" + value +
                ", rentPrice=" + rentPrice +
                ", mortgagePrice=" + mortgagePrice +
                ", isMortgaged=" + isOwned +
                '}';
    }


}
