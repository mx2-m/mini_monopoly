package com.mygdx.game.hud;

public class MonopolyFields {
    //public int width;
    // public int height;
    private CellState state;
    private com.mygdx.game.hud.CityField cField;

    public enum CellState {
        CITY, START, TAX, GO_TO_JAIL, JAIL_VISIT, FREE_PARKING, CARD_SURPRISE, CARD_CHANCE
    }


   /* CellState[] board = { CellState.START, CellState.CITY, CellState.TAX,
            CellState.CITY, CellState.CARD_SURPRISE, CellState.CITY,
            CellState.CITY, CellState.PRISON_VISIT, CellState.CITY,
            CellState.CARD_CHANCE, CellState.CITY, CellState.CITY,
            CellState.TAX, CellState.CITY, CellState.FREE_PARKING,
            CellState.CITY, CellState.CARD_SURPRISE, CellState.CITY,
            CellState.CITY, CellState.CARD_CHANCE, CellState.CITY,
            CellState.GO_TO_PRISON, CellState.CITY, CellState.CARD_SURPRISE,
            CellState.CITY, CellState.CITY, CellState.TAX, CellState.CITY };*/

    public MonopolyFields(CellState state, com.mygdx.game.hud.CityField city) {
        this.state = state;
        this.cField = city;
    }

    public MonopolyFields(CellState state) {
        this.state = state;
        this.cField = null;
    }

    public CellState getState() {
        return state;
    }

    public void setState(CellState state) {
        this.state = state;
    }

    public com.mygdx.game.hud.CityField getCity() {
        return cField;
    }

    public void setCity(com.mygdx.game.hud.CityField city) {
        this.cField = city;
    }


    public static MonopolyFields[] initSquares() {
        MonopolyFields[] field = new MonopolyFields[28];

        field[0] = new MonopolyFields(CellState.START);
        field[1] = new MonopolyFields(CellState.CITY, new com.mygdx.game.hud.CityField(1, "Barcelona", 100, 6, 50));
        field[2] = new MonopolyFields(CellState.CITY, new com.mygdx.game.hud.CityField(2, "Madrid", 140, 100, 250));
        field[3] = new MonopolyFields(CellState.CARD_CHANCE);
        field[4] = new MonopolyFields(CellState.CITY, new com.mygdx.game.hud.CityField(4, "Istanbul", 109, 27, 60));
        field[5] = new MonopolyFields(CellState.CITY, new com.mygdx.game.hud.CityField(5, "Paris", 276, 170, 190));
        field[6] = new MonopolyFields(CellState.CITY, new com.mygdx.game.hud.CityField(6, "Venezia", 100, 50, 30));
        field[7] = new MonopolyFields(CellState.JAIL_VISIT);
        field[8] = new MonopolyFields(CellState.CITY, new com.mygdx.game.hud.CityField(8, "Milano", 90, 50, 30));
        field[9] = new MonopolyFields(CellState.CITY, new com.mygdx.game.hud.CityField(9, "Rio De Janeiro", 345, 290, 160));
        field[10] = new MonopolyFields(CellState.CITY, new com.mygdx.game.hud.CityField(10, "London", 190, 100, 90));
        field[11] = new MonopolyFields(CellState.CARD_SURPRISE);
        field[12] = new MonopolyFields(CellState.CITY, new com.mygdx.game.hud.CityField(12, "Vancouver", 300, 200, 200));
        field[13] = new MonopolyFields(CellState.CITY, new com.mygdx.game.hud.CityField(13, "Oslo", 300, 150, 100));
        field[14] = new MonopolyFields(CellState.FREE_PARKING);
        field[15] = new MonopolyFields(CellState.CITY, new com.mygdx.game.hud.CityField(15, "Berlin", 230, 79, 100));
        field[16] = new MonopolyFields(CellState.CITY, new com.mygdx.game.hud.CityField(16, "Bombay", 400, 200, 200));
        field[17] = new MonopolyFields(CellState.CARD_SURPRISE);
        field[18] = new MonopolyFields(CellState.CITY, new com.mygdx.game.hud.CityField(18, "Peking", 400, 200, 150));
        field[19] = new MonopolyFields(CellState.CITY, new com.mygdx.game.hud.CityField(19, "Melbourne", 200, 200, 100));
        field[20] = new MonopolyFields(CellState.CITY, new com.mygdx.game.hud.CityField(20, "Koln", 2200, 100, 100));
        field[21] = new MonopolyFields(CellState.GO_TO_JAIL);
        field[22] = new MonopolyFields(CellState.CITY, new com.mygdx.game.hud.CityField(22, "Geneve", 400, 250, 200));
        field[23] = new MonopolyFields(CellState.CITY, new com.mygdx.game.hud.CityField(23, "Nice", 400, 200, 200));
        field[24] = new MonopolyFields(CellState.CARD_CHANCE);
        field[25] = new MonopolyFields(CellState.CITY, new com.mygdx.game.hud.CityField(25, "Athena", 200, 200, 100));
        field[26] = new MonopolyFields(CellState.CITY, new com.mygdx.game.hud.CityField(26, "Roma", 134, 70, 56));
        field[27] = new MonopolyFields(CellState.CITY, new CityField(27, "Zurich", 106, 78, 36));

        return field;
    }

    public static String[] surprise() {
        String[] surprise = new String[11];

        surprise[0] = "Get out of Jail Free";
        surprise[1] = "Your car is broken you have to pay $100";
        surprise[2] = "You have won a crossword competition. Collect $100";
        surprise[3] = "Pay tax of $15";
        surprise[4] = "Pay tax of $105";
        surprise[5] = "Pay tax of $215";
        surprise[6] = "Pay tax of $5";
        surprise[7] = "Collect $105";
        surprise[8] = "Collect $10";
        surprise[9] = "Collect $5";
        surprise[10] = "Collect $1050";


        return surprise;
    }


    public static String[] chance() {
        String[] chance = new String[11];

        chance[0] = "Get out of Jail Free";
        chance[1] = "Go to jail";
        chance[2] = "Move forward 3 fields";
        chance[3] = "Move backwards 3 fields";
        chance[4] = "Collect $100";
        chance[5] = "Collect $34";
        chance[6] = "Move forward 4 fields";
        chance[7] = "Move forward 1 field";
        chance[8] = "Pay $23";
        chance[9] = "Pay $21";
        chance[10] = "Move backwards 2 fields";

        return chance;
    }

    @Override
    public String toString() {
        return "MonopolyFields{" +
                "state=" + state +
                ", cField=" + cField +
                '}';
    }
}
