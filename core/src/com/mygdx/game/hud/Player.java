package com.mygdx.game.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.utils.Disposable;
import com.mygdx.game.assets.AnimatedSprite;
import com.mygdx.game.screen01.ScreenMonopoly;

import de.tomgrill.gdxdialogs.core.GDXDialogs;
import de.tomgrill.gdxdialogs.core.dialogs.GDXButtonDialog;
import de.tomgrill.gdxdialogs.core.listener.ButtonClickListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class Player extends Actor implements Serializable {
    private String name;
    private int boardPosition;
    private String selectedPiece;
    private int money;
    private ArrayList<CityField> propertiesBought;
    private boolean isBankrupt;
    private boolean isInJail;
    private int nGetOutOfJailFreeCards;
    private TextureAtlas textureAtlas;
    private Animation<TextureRegion> animation;
    private AnimatedSprite animatedSprite;


    public Player(String name, String piece, String path) {
        this.name = name;
        this.selectedPiece = piece;
        this.boardPosition = 0;
        this.money = 3000;
        this.propertiesBought = new ArrayList<>();
        this.isBankrupt = false;
        this.isInJail = false;
        this.nGetOutOfJailFreeCards = 0;


        textureAtlas = new TextureAtlas(Gdx.files.internal(path));
        animation = new Animation(1f / 15f, textureAtlas.getRegions());

        animation.setPlayMode(Animation.PlayMode.LOOP);
        animatedSprite = new AnimatedSprite(animation);

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        animatedSprite.setPosition(getX() + 600, getY() + 50);
        animatedSprite.draw(batch);
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBoardPosition() {
        return boardPosition;
    }

    public void setBoardPosition(int boardPosition) {
        this.boardPosition = boardPosition;
    }

    public String getSelectedPiece() {
        return selectedPiece;
    }

    public void setSelectedPiece(String selectedPiece) {
        this.selectedPiece = selectedPiece;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public ArrayList<com.mygdx.game.hud.CityField> getPropertiesBought() {
        return propertiesBought;
    }

    public void setPropertiesBought(ArrayList<com.mygdx.game.hud.CityField> propertiesBought) {
        this.propertiesBought = propertiesBought;
    }

    public boolean isBankrupt() {
        return isBankrupt;
    }

    public void setBankrupt(boolean bankrupt) {
        isBankrupt = bankrupt;
    }

    public boolean isInJail() {
        return isInJail;
    }

    public void setInJail(boolean inJail) {
        isInJail = inJail;
    }

    public int getnGetOutOfJailFreeCards() {
        return nGetOutOfJailFreeCards;
    }

    public void setnGetOutOfJailFreeCards(int nGetOutOfJailFreeCards) {
        this.nGetOutOfJailFreeCards = nGetOutOfJailFreeCards;
    }


    public void buyProperty(MonopolyFields[] sold) {
        sold[this.getBoardPosition()].getCity().setOwnedBy(this);
        this.getPropertiesBought().add(sold[this.getBoardPosition()].getCity());
        this.setMoney(this.getMoney() - sold[this.getBoardPosition()].getCity().getValue());
    }


    public void playerMovement(int diceNumber, float duration, boolean reverseMovement) {
        SequenceAction sa = new SequenceAction();
        if (!reverseMovement) {

            for (int i = 0; i < diceNumber; i++) {
                if (this.getBoardPosition() >= 0 && this.getBoardPosition() <= 6) {
                    MoveByAction moveLeft = new MoveByAction();
                    if (this.getBoardPosition() == 0 || this.getBoardPosition() == 6) {
                        moveLeft.setAmountX(Gdx.graphics.getWidth() / 12.047f * -1);
                    } else {
                        moveLeft.setAmountX(Gdx.graphics.getWidth() / 12.047f * -1);
                    }
                    moveLeft.setDuration(duration);
                    sa.addAction(moveLeft);
                    this.addAction(sa);
                } else if (this.getBoardPosition() >= 7 && this.getBoardPosition() <= 13) {
                    MoveByAction moveUp = new MoveByAction();
                    if (this.getBoardPosition() == 7 || this.getBoardPosition() == 13) {
                        moveUp.setAmountY(Gdx.graphics.getHeight() / 9.035f);
                    } else {
                        moveUp.setAmountY(Gdx.graphics.getHeight() / 9.035f);
                    }
                    moveUp.setDuration(duration);
                    sa.addAction(moveUp);
                    this.addAction(sa);
                } else if (this.getBoardPosition() >= 14 && this.getBoardPosition() <= 20) {
                    MoveByAction moveRight = new MoveByAction();
                    if (this.getBoardPosition() == 14 || this.getBoardPosition() == 20) {
                        moveRight.setAmountX(Gdx.graphics.getWidth() / 12.047f);
                    } else {
                        moveRight.setAmountX(Gdx.graphics.getWidth() / 12.047f);
                    }
                    moveRight.setDuration(duration);
                    sa.addAction(moveRight);
                    this.addAction(sa);
                } else if (this.getBoardPosition() >= 21 && this.getBoardPosition() <= 27) {
                    MoveByAction moveDown = new MoveByAction();
                    if (this.getBoardPosition() == 21 || this.getBoardPosition() == 27) {
                        moveDown.setAmountY(Gdx.graphics.getHeight() / 9.035f * -1);
                    } else {
                        moveDown.setAmountY(Gdx.graphics.getHeight() / 9.035f * -1);
                    }
                    moveDown.setDuration(duration);
                    sa.addAction(moveDown);
                    this.addAction(sa);
                }
                if (this.getBoardPosition() == 27) {
                    this.setBoardPosition(0);
                    this.setMoney(this.getMoney() + 200);
                } else {
                    this.setBoardPosition(this.getBoardPosition() + 1);
                }
            }
        } else {
            for (int i = 0; i < diceNumber; i++) {
                if (this.getBoardPosition() >= 1 && this.getBoardPosition() <= 7) {
                    MoveByAction moveRight = new MoveByAction();
                    if (this.getBoardPosition() == 1 || this.getBoardPosition() == 7) {
                        moveRight.setAmountX(Gdx.graphics.getWidth() / 12.047f);
                    } else {
                        moveRight.setAmountX(Gdx.graphics.getWidth() / 12.047f);
                    }
                    moveRight.setDuration(duration);
                    sa.addAction(moveRight);
                    this.addAction(sa);
                } else if (this.getBoardPosition() >= 8 && this.getBoardPosition() <= 14) {
                    MoveByAction moveDown = new MoveByAction();
                    if (this.getBoardPosition() == 8 || this.getBoardPosition() == 14) {
                        moveDown.setAmountY(Gdx.graphics.getHeight() / 9.035f * -1);
                    } else {
                        moveDown.setAmountY(Gdx.graphics.getHeight() / 9.035f * -1);
                    }
                    moveDown.setDuration(duration);
                    sa.addAction(moveDown);
                    this.addAction(sa);
                } else if (this.getBoardPosition() >= 15 && this.getBoardPosition() <= 21) {
                    MoveByAction moveLeft = new MoveByAction();
                    if (this.getBoardPosition() == 15 || this.getBoardPosition() == 21) {
                        moveLeft.setAmountX(Gdx.graphics.getWidth() / 12.047f * -1);
                    } else {
                        moveLeft.setAmountX(Gdx.graphics.getWidth() / 12.047f * -1);
                    }
                    moveLeft.setDuration(duration);
                    sa.addAction(moveLeft);
                    this.addAction(sa);
                } else if ((this.getBoardPosition() >= 22 && this.getBoardPosition() <= 27) || this.getBoardPosition() == 0) {
                    MoveByAction moveUp = new MoveByAction();
                    if (this.getBoardPosition() == 22 || this.getBoardPosition() == 0) {
                        moveUp.setAmountY(Gdx.graphics.getHeight() / 9.035f);
                    } else {
                        moveUp.setAmountY(Gdx.graphics.getHeight() / 9.035f);
                    }
                    moveUp.setDuration(duration);
                    sa.addAction(moveUp);
                    this.addAction(sa);
                }
                if (this.getBoardPosition() == 0) {
                    this.setBoardPosition(27);
                    this.setMoney(this.getMoney() + 200);
                } else {
                    this.setBoardPosition(this.getBoardPosition() - 1);
                }
            }
        }
    }


    public void surprise(String[] cc, GDXDialogs dialogs, final Player player) {
        Random r = new Random();
        final int randomNum = r.nextInt(cc.length);
        String surprise = cc[randomNum];

        GDXButtonDialog bDialog = dialogs.newDialog(GDXButtonDialog.class);
        bDialog.setTitle("Surprise");
        bDialog.setMessage(surprise);
        bDialog.addButton("OK");
        bDialog.build().show();
        bDialog.setClickListener(new ButtonClickListener() {
            @Override
            public void click(int button) {
                switch (randomNum) {
                    case 0:
                        player.setnGetOutOfJailFreeCards(getnGetOutOfJailFreeCards() + 1);
                        break;
                    case 1:
                        player.setMoney(getMoney() - 100);
                        break;
                    case 2:
                        player.setMoney(getMoney() + 100);
                        break;
                    case 3:
                        player.setMoney(getMoney() - 15);
                        break;
                    case 4:
                        player.setMoney(getMoney() - 105);
                        break;
                    case 5:
                        player.setMoney(getMoney() - 215);
                        break;
                    case 6:
                        player.setMoney(getMoney() - 5);
                        break;
                    case 7:
                        player.setMoney(getMoney() + 105);
                        break;
                    case 8:
                        player.setMoney(getMoney() + 10);
                        break;
                    case 9:
                        player.setMoney(getMoney() + 5);
                        break;
                    case 10:
                        player.setMoney(getMoney() + 1050);
                        break;
                }
            }
        });
    }

    public void chance(final Player player, String[] c, GDXDialogs dialogs) {
        final Random r = new Random();
        final int randomNum = r.nextInt(c.length);
        String textChance = c[randomNum];

        GDXButtonDialog bDialog = dialogs.newDialog(GDXButtonDialog.class);
        bDialog.setTitle("Chance");
        bDialog.setMessage(textChance);
        bDialog.addButton("OK");
        bDialog.build().show();
        bDialog.setClickListener(new ButtonClickListener() {
            @Override
            public void click(int button) {
                switch (randomNum) {
                    case 0:
                        player.setnGetOutOfJailFreeCards(getnGetOutOfJailFreeCards() + 1);
                        break;
                    case 1:
                        if (player.getBoardPosition() - 7 <= 0) {
                            player.playerMovement(7 - player.getBoardPosition(), 0.5f, false);
                        } else {
                            player.playerMovement(17, 1f, true);
                        }
                        player.setInJail(true);
                        break;
                    case 2:
                        player.playerMovement(3, 0.5f, false);
                        ScreenMonopoly.landingSituations(player);
                        break;
                    case 3:
                        player.playerMovement(3, 1f, true);
                        ScreenMonopoly.landingSituations(player);
                        break;
                    case 4:
                        player.setMoney(getMoney() + 100);
                        break;
                    case 5:
                        player.setMoney(getMoney() + 34);
                        break;
                    case 6:
                        player.playerMovement(4, 0.5f, false);
                        ScreenMonopoly.landingSituations(player);
                        break;
                    case 7:
                        player.playerMovement(1, 0.5f, false);
                        ScreenMonopoly.landingSituations(player);
                        break;
                    case 8:
                        player.setMoney(player.getMoney() - 23);
                        break;
                    case 9:
                        player.setMoney(player.getMoney() - 21);
                        break;
                    case 10:
                        player.playerMovement(2, 1f, true);
                        break;
                }
            }
        });
    }


    public void payRent(GDXDialogs dialogs, final CityField prop, final ArrayList<Player> players, final Player p) {
        GDXButtonDialog bDialog = dialogs.newDialog(GDXButtonDialog.class);
        bDialog.setTitle("Pay rent");
        bDialog.setMessage(prop.getOwnedBy().getName() + " is the owner of " + prop.getName() + ". " +
                "Rent price is: " + prop.getRentPrice() + "€");
        bDialog.addButton("OK");
        bDialog.setClickListener(new ButtonClickListener() {
            @Override
            public void click(int button) {
                boolean ownerPaid = false;
                boolean playerPaid = false;
                for (int i = 0; i < players.size(); i++) {
                    if (!ownerPaid) {
                        if (players.get(i).equals(prop.getOwnedBy())) {
                            players.get(i).setMoney(players.get(i).getMoney() + prop.getRentPrice());
                            ownerPaid = true;
                        }
                    }
                    if (!playerPaid) {
                        if (players.get(i).equals(p)) {
                            players.get(i).setMoney(players.get(i).getMoney() - prop.getRentPrice());
                            playerPaid = true;
                        }
                    }
                }
            }
        });
        bDialog.build().show();
    }

    @Override
    public String toString() {
        return "Player{" +
                "sprite=" +
                ", name='" + name + '\'' +
                ", boardPosition=" + boardPosition +
                ", money=" + money +
                ", propertiesBought=" + propertiesBought +
                ", isBankrupt=" + isBankrupt +
                ", isInJail=" + isInJail +
                ", nGetOutOfJailFreeCards=" + nGetOutOfJailFreeCards +
                '}';
    }


}
