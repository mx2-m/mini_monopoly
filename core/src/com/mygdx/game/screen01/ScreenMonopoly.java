package com.mygdx.game.screen01;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Monopoly;
import com.mygdx.game.assets.AnimatedSprite;
import com.mygdx.game.assets.AssetDescriptors;
import com.mygdx.game.assets.RegionNames;
import com.mygdx.game.buttons.BuyButton;
import com.mygdx.game.buttons.ChanceButton;
import com.mygdx.game.buttons.SurpriseButton;
import com.mygdx.game.buttons.EndTurnButton;
import com.mygdx.game.buttons.StartGameButton;
import com.mygdx.game.buttons.ThrowDiceButton;
import com.mygdx.game.hud.CityField;
import com.mygdx.game.hud.MonopolyFields;
import com.mygdx.game.hud.Player;
import com.mygdx.game.hud.Hud;
import com.mygdx.game.util.GameManager;

import java.util.ArrayList;
import java.util.Random;

import de.tomgrill.gdxdialogs.core.GDXDialogs;
import de.tomgrill.gdxdialogs.core.GDXDialogsSystem;
import de.tomgrill.gdxdialogs.core.dialogs.GDXButtonDialog;
import de.tomgrill.gdxdialogs.core.listener.ButtonClickListener;


public class ScreenMonopoly implements Screen {

    protected Monopoly game;
    private OrthographicCamera camera;
    private Viewport viewport;
    private Stage screen;
    private SpriteBatch spriteBatch;
    private AssetManager assetManager;
    public TextureAtlas atlas;
    public TextureRegion background;
    public TextureRegion buttonBuy;
    public TextureRegion buttonChance;
    public TextureRegion buttonSurprise;
    public TextureRegion buttonDice;
    public TextureRegion end;
    private Sound sound;


    private static GDXDialogs dialogs;
    private static ArrayList<Player> players;
    private static MonopolyFields[] board;
    private static String[] surprise;
    private static String[] chance;

    //buttons

    private static BuyButton buyButton;
    private static ThrowDiceButton diceButton;
    private static SurpriseButton surpriseButton;
    private static ChanceButton chanceButton;
    private static EndTurnButton endButton;
    private static GDXButtonDialog buttonDialog;


    private Hud hud;
    private static int turn;

    public static FitViewport getFitViewport(Camera camera) {
        Rectangle surrounds = fittedSize();
        FitViewport fitViewport = new FitViewport(surrounds.width,
                surrounds.height, camera);
        fitViewport.update((int) surrounds.width, (int) surrounds.height, true);

        return fitViewport;
    }

    private final static Rectangle targetScreenSize = new Rectangle(0, 0, 1280,
            720);


    private static Rectangle fittedSize() {
        int h = Gdx.graphics.getHeight();
        int w = Gdx.graphics.getWidth();
        Rectangle surrounds = new Rectangle(0, 0, w, h);
        surrounds.fitOutside(targetScreenSize);
        return surrounds;
    }

    public enum GameState {

        STARTED,  //The game is started. This event happens only one time per game, right after the game has started
        BEFORE_TURN, //The player is about to begin his turn
        DURING_TURN, //The player is during his turn and is able to perform his moves .
        AFTER_TURN,//The player has ended his turn.
        STOPPED, //The game has ended.
        DICE_RESULT, //The dice was thrown by the user
        DICE_ROLL_FOR_PRISON, //
        BEFORE_TURN_IN_PRISON, //The player is about to begin his turn, but is in prison
        NOT_STARTED, //The game hasn't started yet and is about to begin
        END_WIN,
        END_LOSE

    }

    private static GameState state;

    protected ScreenMonopoly(final Monopoly mg) {

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);

        assetManager = new AssetManager();
        assetManager.load(AssetDescriptors.GAME_PLAY);
        assetManager.finishLoading();

        //assetManager.load(AssetDescriptors.SOUND);
        //assetManager.finishLoading();

        atlas = assetManager.get(AssetDescriptors.GAME_PLAY);
        background = atlas.findRegion(RegionNames.BOARD);
        buttonBuy = atlas.findRegion(RegionNames.BUTTON_BUY);
        buttonChance = atlas.findRegion(RegionNames.BUTTON_CHANCE);
        buttonSurprise = atlas.findRegion(RegionNames.BUTTON_SURPRISE);
        end = atlas.findRegion(RegionNames.BUTTON_FINISH);
        buttonDice = atlas.findRegion(RegionNames.BUTTON_DICE);

        // sound = assetManager.get(AssetDescriptors.SOUND);
        sound = Gdx.audio.newSound(Gdx.files.internal("walking.mp3"));


        dialogs = GDXDialogsSystem.install();
        buttonDialog = dialogs.newDialog(GDXButtonDialog.class);

        this.game = mg;
        turn = 0;
        board = MonopolyFields.initSquares();
        surprise = MonopolyFields.surprise();
        chance = MonopolyFields.chance();

        //screen = new Stage(new FillViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        screen = new Stage();
        // screen.setViewport(getFitViewport(screen.getCamera()));

        buyButton = new BuyButton(Gdx.graphics.getWidth() - 600, Gdx.graphics.getHeight() - 450, buttonBuy);
        diceButton = new ThrowDiceButton(Gdx.graphics.getWidth() - 600, Gdx.graphics.getHeight() - 450, buttonDice);
        surpriseButton = new SurpriseButton(Gdx.graphics.getWidth() - 600, Gdx.graphics.getHeight() - 450, buttonSurprise);
        chanceButton = new ChanceButton(Gdx.graphics.getWidth() - 600, Gdx.graphics.getHeight() - 450, buttonChance);
        endButton = new EndTurnButton(Gdx.graphics.getWidth() - 600, Gdx.graphics.getHeight() - 450, end);

        spriteBatch = new SpriteBatch();

        players = new ArrayList<>();
        players.add(new Player("Player1", "Snowman", "Snowman.atlas"));
        players.add(new Player("Santa", "Santa Claus", "Santa.atlas"));

        hud = new Hud(players.get(0), players.get(1));

        screen.addActor(diceButton);
        screen.addActor(buyButton);
        screen.addActor(surpriseButton);
        screen.addActor(chanceButton);
        screen.addActor(endButton);

        for (int i = 0; i < players.size(); i++)
            screen.addActor(players.get(i));

        buyButton.setVisible(false);
        diceButton.setVisible(true);
        surpriseButton.setVisible(false);
        chanceButton.setVisible(false);
        endButton.setVisible(false);


        diceButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Random r = new Random();
                final int number = r.nextInt(6) + 1;
                final int number2 = r.nextInt(6) + 1;
                Gdx.app.log("DICE", "kliknuto");

                if (!players.get(0).isInJail()) { // If player isn't in jail
                    buttonDialog = dialogs.newDialog(GDXButtonDialog.class);
                    showBasicDialog(buttonDialog, players.get(0).getName() + " has thrown dice", "Dice 1: " + number + "\nDice 2: " + number2);
                    buttonDialog.setClickListener(new ButtonClickListener() {
                        @Override
                        public void click(int button) {
                            Gdx.app.log("DICE", "dialog");
                            players.get(0).playerMovement(number + number2, 3f, false);
                            sound.play();
                            if (players.get(0).getMoney() < 0)
                                mg.gotoScreen(Monopoly.Screens.GAME_OVER);
                            if (players.get(1).getMoney() < 0)
                                mg.gotoScreen(Monopoly.Screens.WON);
                            else
                                landingSituations(players.get(0));
                        }
                    });
                } else { // If player is in jail
                    GDXButtonDialog bDialog = dialogs.newDialog(GDXButtonDialog.class);
                    bDialog.setTitle("Jail");
                    bDialog.setMessage("Player is in jail");
                    bDialog.addButton("Dices");
                    bDialog.addButton("Pay $50");
                    if (players.get(0).getnGetOutOfJailFreeCards() > 0) {
                        bDialog.addButton("Use get out of jail free card");
                    }
                    bDialog.build().show();
                    bDialog.setClickListener(new ButtonClickListener() {
                        @Override
                        public void click(int button) {
                            if (button == 0) { // If the player opts to throw the dice
                                buttonDialog = dialogs.newDialog(GDXButtonDialog.class);
                                showBasicDialog(buttonDialog, players.get(0).getName() + " has thrown dices", "Dice 1: " + number + "\nDice 2: " + number2);
                                buttonDialog.setClickListener(new ButtonClickListener() {
                                    @Override
                                    public void click(int button) {
                                        if (number == number2) {
                                            players.get(0).setInJail(false);
                                            players.get(0).playerMovement((number + number2), 0.5f, false);
                                            sound.play();
                                            landingSituations(players.get(0));
                                        } else {
                                            diceButton.setVisible(false);
                                            endButton.setVisible(true);
                                        }
                                    }
                                });
                            } else if (button == 1) {
                                players.get(0).setMoney(players.get(0).getMoney() - 50);
                                players.get(0).setInJail(false);
                                buttonDialog = dialogs.newDialog(GDXButtonDialog.class);
                                showBasicDialog(buttonDialog, players.get(0).getName() + " has thrown dices", "Dice 1: " + number + "\nDice 2: " + number2);
                                buttonDialog.setClickListener(new ButtonClickListener() {
                                    @Override
                                    public void click(int button) {
                                        players.get(0).playerMovement((number + number2), 0.5f, false);
                                        sound.play();
                                        landingSituations(players.get(0));
                                    }
                                });
                            } else if (button == 2) {
                                players.get(0).setnGetOutOfJailFreeCards(players.get(0).getnGetOutOfJailFreeCards() - 1);
                                players.get(0).setInJail(false);
                                buttonDialog = dialogs.newDialog(GDXButtonDialog.class);
                                showBasicDialog(buttonDialog, players.get(0).getName() + " has thrown dices", "Dice 1: " + number + "\nDice 2: " + number2);
                                buttonDialog.setClickListener(new ButtonClickListener() {
                                    @Override
                                    public void click(int button) {
                                        players.get(0).playerMovement((number + number2), 0.5f, false);
                                        landingSituations(players.get(0));
                                    }
                                });
                            }
                        }
                    });
                }
                turn++;
            }
        });

        buyButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                CityField prop = board[players.get(0).getBoardPosition()].getCity();
                buttonDialog = dialogs.newDialog(GDXButtonDialog.class);
                showBasicDialog(buttonDialog, "Buy property", players.get(0).getName() + " has bought " + prop.getName() + " for " + "$" + prop.getValue());
                buttonDialog.setClickListener(new ButtonClickListener() {
                    @Override
                    public void click(int button) {
                        if (players.get(0).getMoney() < 0)
                            mg.gotoScreen(Monopoly.Screens.GAME_OVER);

                        players.get(0).buyProperty(board);
                        buyButton.setVisible(false);
                        endButton.setVisible(true);
                    }
                });
            }
        });

        endButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (players.get(0).getMoney() < 0)
                    mg.gotoScreen(Monopoly.Screens.GAME_OVER);
                if (players.get(1).getMoney() < 0)
                    mg.gotoScreen(Monopoly.Screens.WON);
                else
                    AiFunction();
            }
        });

        surpriseButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                players.get(0).surprise(surprise, dialogs, players.get(0));
                surpriseButton.setVisible(false);
                endButton.setVisible(true);
            }
        });

        chanceButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                players.get(0).chance(players.get(0), chance, dialogs);
                chanceButton.setVisible(false);
                if (board[players.get(0).getBoardPosition()].getState() == MonopolyFields.CellState.CITY) {
                    endButton.setVisible(false);
                } else {
                    endButton.setVisible(true);
                }
            }
        });
        //screen.addListener(new StageListener(screen));
        Gdx.input.setInputProcessor(screen);

        if (state == GameState.END_LOSE)
            mg.gotoScreen(Monopoly.Screens.GAME_OVER);
    }


    public static void landingSituations(final Player p) {
        switch (board[p.getBoardPosition()].getState()) {
            case CITY:
                if (board[p.getBoardPosition()].getCity().getOwnedBy() == null) {
                    if (!p.getName().contains("Santa")) {
                        diceButton.setVisible(false);
                        endButton.setVisible(false);
                        buyButton.setVisible(true);
                    } else {
                        CityField prop = board[p.getBoardPosition()].getCity();
                        if (prop.getValue() <= p.getMoney()) {
                            buttonDialog = dialogs.newDialog(GDXButtonDialog.class);
                            showBasicDialog(buttonDialog, "buy property", p.getName() + " has bought " + prop.getName() + " for " + "$" + prop.getValue());
                            buttonDialog.setClickListener(new ButtonClickListener() {
                                @Override
                                public void click(int button) {
                                    p.buyProperty(board);
                                }
                            });
                        } else {
                            boolean declareBankrupt = true;

                            if (declareBankrupt) {
                                p.setBankrupt(true);
                                state = GameState.END_LOSE;
                            }
                           /* Gdx.app.log("isBankrupt", p.isBankrupt() + "");
                            if (p.isBankrupt()) {
                                buttonDialog = dialogs.newDialog(GDXButtonDialog.class);
                                showBasicDialog(buttonDialog, p.getName() + "Bankrupt",
                                        p.getName() + " is bankrupt "
                                );
                                buttonDialog.setClickListener(new ButtonClickListener() {
                                    @Override
                                    public void click(int button) {
                                        p.setBankrupt(true);
                                        for (MonopolyFields sq : board) {
                                            if (sq.getState() == MonopolyFields.CellState.CITY) {
                                                if (sq.getCity().getOwnedBy().getName().equals(p.getName())) {
                                                    sq.getCity().setOwnedBy(null);
                                                }
                                            }
                                        }
                                    }
                                });
                            }*/
                            else {
                                p.buyProperty(board);
                            }
                        }
                    }
                } else {
                    final CityField prop = board[p.getBoardPosition()].getCity();
                    if (!prop.getOwnedBy().getName().equals(p.getName())) {
                        if (!prop.isOwned()) {
                            if (p.getMoney() >= prop.getRentPrice()) {
                                p.payRent(dialogs, prop, players, p);
                            } else {
                                boolean declareBankrupt = true;
                                for (CityField property : p.getPropertiesBought()) {
                                    if (!property.isOwned()) {
                                        property.setIsOwned(true);
                                        p.setMoney(p.getMoney() + property.getMortgagePrice());
                                        if (p.getMoney() >= prop.getRentPrice()) {
                                            declareBankrupt = false;
                                            break;
                                        }
                                    }
                                }
                                if (declareBankrupt) {
                                    p.setBankrupt(true);
                                    state = GameState.END_LOSE;
                                }
                            /*    if (p.isBankrupt()) {
                                    buttonDialog = dialogs.newDialog(GDXButtonDialog.class);
                                    showBasicDialog(buttonDialog, p.getName() + "bankrupt",
                                            p.getName() + "is bankrupt");
                                    buttonDialog.setClickListener(new ButtonClickListener() {
                                        @Override
                                        public void click(int button) {
                                            p.setBankrupt(true);
                                            for (MonopolyFields sq : board) {
                                                if (sq.getState() == MonopolyFields.CellState.CITY) {
                                                    if (sq.getCity().getOwnedBy().getName().equals(p.getName())) {
                                                        sq.getCity().setOwnedBy(null);
                                                    }
                                                }
                                            }
                                        }
                                    });
                                } */
                                else {
                                    p.payRent(dialogs, prop, players, p);
                                }
                            }
                        }
                    }
                    if (!p.getName().contains("Santa")) {
                        endButton.setVisible(true);
                        diceButton.setVisible(false);
                    }
                }
                break;
            case CARD_SURPRISE:
                if (!p.getName().contains("Santa")) {
                    surpriseButton.setVisible(true);
                    diceButton.setVisible(false);
                    endButton.setVisible(false);
                } else {
                    p.surprise(surprise, dialogs, p);
                }
                break;
            case CARD_CHANCE:
                if (!p.getName().contains("Santa")) {
                    chanceButton.setVisible(true);
                    diceButton.setVisible(false);
                    endButton.setVisible(false);
                } else {
                    p.chance(p, chance, dialogs);
                }
                break;
            case START:
                break;

            case GO_TO_JAIL:
                buttonDialog = dialogs.newDialog(GDXButtonDialog.class);
                showBasicDialog(buttonDialog, "Jail", p.getName() + " go to jail");
                buttonDialog.setClickListener(new ButtonClickListener() {
                    @Override
                    public void click(int button) {
                        if (!p.getName().contains("Santa")) {
                            endButton.setVisible(true);
                        }
                        p.playerMovement(14, 3f, true);
                        p.setInJail(true);
                    }
                });
                break;
            case JAIL_VISIT:
            case FREE_PARKING:
                if (!p.getName().contains("Santa")) {
                    diceButton.setVisible(false);
                    endButton.setVisible(true);
                }
        }
    }


    public static void showBasicDialog(GDXButtonDialog bDialog, String title, String message) {
        bDialog.setTitle(title);
        bDialog.setMessage(message);
        bDialog.addButton("OK");
        bDialog.build().show();
    }


    private static void AiFunction() {
        // if (!players.get(turn).isBankrupt()) {
        final int aiPlayer = turn;
        endButton.setVisible(false);
        Random r = new Random();
        final int number = r.nextInt(6) + 1;
        final int number2 = r.nextInt(6) + 1;
        if (!players.get(aiPlayer).isInJail()) {


            buttonDialog = dialogs.newDialog(GDXButtonDialog.class);
            showBasicDialog(buttonDialog, players.get(aiPlayer).getName() + " has thrown dices", "Dice 1: " + number + "\nDice 2: " + number2);
            buttonDialog.setClickListener(new ButtonClickListener() {
                @Override
                public void click(int button) {
                    players.get(aiPlayer).playerMovement(number + number2, 0.5f, false);
                    landingSituations(players.get(aiPlayer));
                }
            });
        } else {
            if (players.get(aiPlayer).getnGetOutOfJailFreeCards() > 0) {
                players.get(aiPlayer).setnGetOutOfJailFreeCards(players.get(turn).getnGetOutOfJailFreeCards() - 1);
                buttonDialog = dialogs.newDialog(GDXButtonDialog.class);
                showBasicDialog(buttonDialog, players.get(aiPlayer).getName() + " out of jail",
                        players.get(aiPlayer).getName() + " card");
                buttonDialog.setClickListener(new ButtonClickListener() {
                    @Override
                    public void click(int button) {
                        buttonDialog = dialogs.newDialog(GDXButtonDialog.class);
                        showBasicDialog(buttonDialog, players.get(aiPlayer).getName() + " has thrown dices",
                                "Dice 1: " + number + "\nDice 2: " + number2);
                        buttonDialog.setClickListener(new ButtonClickListener() {
                            @Override
                            public void click(int button) {
                                players.get(aiPlayer).playerMovement((number + number2), 0.5f, false);
                                landingSituations(players.get(aiPlayer));
                                players.get(aiPlayer).setInJail(false);
                            }
                        });
                    }
                });
            } else if (players.get(aiPlayer).getMoney() >= 50) {
                players.get(aiPlayer).setMoney(players.get(aiPlayer).getMoney() - 50);
                buttonDialog = dialogs.newDialog(GDXButtonDialog.class);
                showBasicDialog(buttonDialog, players.get(aiPlayer).getName() + " out of jail",
                        players.get(aiPlayer).getName() + " paid 50");
                buttonDialog.setClickListener(new ButtonClickListener() {
                    @Override
                    public void click(int button) {
                        buttonDialog = dialogs.newDialog(GDXButtonDialog.class);
                        showBasicDialog(buttonDialog, players.get(aiPlayer).getName() + " has thrown dices",
                                "Dice 1: " + number + "\nDice 2: " + number2);
                        buttonDialog.setClickListener(new ButtonClickListener() {
                            @Override
                            public void click(int button) {
                                players.get(aiPlayer).playerMovement((number + number2), 0.5f, false);
                                landingSituations(players.get(aiPlayer));
                                players.get(aiPlayer).setInJail(false);
                            }
                        });
                    }
                });
            } else {
                buttonDialog = dialogs.newDialog(GDXButtonDialog.class);
                showBasicDialog(buttonDialog, players.get(aiPlayer).getName() + " has thrown dices",
                        "Dice 1: " + number + "\nDice 2: " + number2);
                buttonDialog.setClickListener(new ButtonClickListener() {
                    @Override
                    public void click(int button) {
                        if (number == number2) {
                            buttonDialog = dialogs.newDialog(GDXButtonDialog.class);
                            showBasicDialog(buttonDialog, players.get(aiPlayer).getName() + " out of jail",
                                    players.get(aiPlayer).getName() + " ");
                            players.get(aiPlayer).playerMovement((number + number2), 0.5f, false);
                            landingSituations(players.get(aiPlayer));
                            players.get(aiPlayer).setInJail(false);
                        }
                    }
                });
            }
        }
        //  } else {
        //  buttonDialog = dialogs.newDialog(GDXButtonDialog.class);
        //   showBasicDialog(buttonDialog, "Bankrupt",
        //           players.get(turn).getName() + " is bankrupt");
        // }
        turn++;
        if (turn == players.size()) {
            turn = 0;
            endButton.setVisible(false);
            diceButton.setVisible(true);

        } else {
            endButton.setVisible(true);
            diceButton.setVisible(false);
        }
    }

    @Override
    public void show() {

    }

    public Sound getSound() {
        return sound;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        //screen.getBatch().setProjectionMatrix(screen.getCamera().combined);
        //screen.getCamera().update();

        screen.getBatch().begin();
        screen.getBatch().draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        screen.getBatch().end();
        screen.act(delta);

        screen.draw();
        spriteBatch.begin();

        hud.updatePlayer1(players.get(0).getMoney());
        hud.updatePlayer2(players.get(1).getMoney());

        //1 game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
        spriteBatch.end();

    }

    @Override
    public void resize(int width, int height) {
        screen.getViewport().update(width, height);
        screen.getCamera().update();

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        atlas.dispose();
        assetManager.dispose();
        sound.dispose();

    }

    @Override
    public void dispose() {

    }
}
