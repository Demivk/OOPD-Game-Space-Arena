package nl.han.ica.spacearena;

import java.util.ArrayList;

import com.sun.prism.image.ViewPort;
import nl.han.ica.OOPDProcessingEngineHAN.Dashboard.Dashboard;
import nl.han.ica.OOPDProcessingEngineHAN.Engine.GameEngine;
import nl.han.ica.OOPDProcessingEngineHAN.Objects.Sprite;
import nl.han.ica.OOPDProcessingEngineHAN.Persistence.FilePersistence;
import nl.han.ica.OOPDProcessingEngineHAN.Persistence.IPersistence;
import nl.han.ica.OOPDProcessingEngineHAN.Sound.Sound;
import nl.han.ica.OOPDProcessingEngineHAN.Tile.TileMap;
import nl.han.ica.OOPDProcessingEngineHAN.Tile.TileType;
import nl.han.ica.OOPDProcessingEngineHAN.View.EdgeFollowingViewport;
import nl.han.ica.OOPDProcessingEngineHAN.View.View;
import nl.han.ica.spacearena.tiles.BoardsTile;
import processing.core.PApplet;

/**
 * @author Ralph Niels, Dennis Gommer en Demi van Kesteren
 */
@SuppressWarnings("serial")
public class SpaceArena extends GameEngine {

	//private Sound spaceCoreSound;
	
    private DistantLaserSpawner distantLaserSpawner;
    private PowerUpSpawner powerUpSpawner;
    private String winnerText = "";
    private boolean gameIsOver = false;
    private IPersistence persistence;
    private SpaceCore spaceCore;
    
    private Player player1;
    private Player player2;
    
    //Evt. direct in arraylist als ze verder niet gebruikt worden
    private Keys player1LeftKey = new Keys(65, false);		//A
	private Keys player1UpKey = new Keys(87, false);		//W
	private Keys player1RightKey = new Keys(68, false);		//D
	private Keys player1DownKey = new Keys(83, false);		//S
	private Keys player1ShootKey = new Keys(70, false);		//F
	
	//Numlock OFF!
	private Keys player2LeftKey = new Keys(37, false);		//numlock 4 
	private Keys player2UpKey = new Keys(38, false);		//numlock 8
	private Keys player2RightKey = new Keys(39, false);		//numlock 6
	private Keys player2DownKey = new Keys(12, false);		//numlock 5
	private Keys player2ShootKey = new Keys(76, false);		//L
	
	ArrayList<Keys> keysPlayer1 = new ArrayList<Keys>() {{
		add(player1LeftKey);
		add(player1UpKey);
		add(player1RightKey);
		add(player1DownKey);
		add(player1ShootKey);
	}};
	ArrayList<Keys> keysPlayer2 = new ArrayList<Keys>() {{
		add(player2LeftKey);
		add(player2UpKey);
		add(player2RightKey);
		add(player2DownKey);
		add(player2ShootKey);
	}};
    
    public static String RED = "Red";
    public static String BLUE = "Blue";
    
    public static int LEFTDEGREES = 270;
    public static int UPDEGREES = 0;
    public static int RIGHTDEGREES = 90;
    public static int DOWNDEGREES = 180;
    
    public static int LEFTIMAGEINDEX = 3;
    public static int UPIMAGEINDEX = 0;
    public static int RIGHTIMAGEINDEX = 2;
    public static int DOWNIMAGEINDEX = 1;
	
    public static String LIGHT = "Light";
    public static String HEAVY = "Heavy";
	
    public static String CURRENTWEAPONP1 = "Light Plasma Gun";
    public static String CURRENTWEAPONP2 = "Light Plasma Gun";
    
    private TextObjectDashboard scoreText;
    private TextObjectDashboard winText;
    private TextObjectDashboard health1Text;
    private TextObjectDashboard health2Text;
    private TextObjectDashboard weaponText1;
    private TextObjectDashboard weaponText2;
    private TextObjectDashboard scoreboardPlayer1;
    private TextObjectDashboard scoreboardPlayer2;
    private TextObjectDashboard weaponEquipedText1;
    private TextObjectDashboard weaponEquipedText2;
    private TextObjectDashboard healthDisplayPlayer1;
    private TextObjectDashboard healthDisplayPlayer2;
 

    public static int worldWidth = 1204;
    public static int worldHeight = 903;
    
    
    
    private static int[] redSpawn = {50,700};
    private static int[] blueSpawn = {worldWidth-100,250};
    
    public static long currentTimestamp = 0;
    
    public static void main(String[] args) {
        SpaceArena w = new SpaceArena();
        w.runSketch();
    }

    /**
     * In deze methode worden de voor het spel
     * noodzakelijke zaken geinitialiseerd
     */
    @Override
    public void setupGame() {

    	createObjects();
    	
        initializeSound();
        createDashboard(worldWidth, 100);
        initializeTileMap();
   

        createSpawners();

        createViewWithoutViewport(worldWidth, worldHeight);
        //createViewWithViewport(worldWidth, worldHeight, 800, 800, 1.1f);

    }

    /**
     * Creëert de view zonder viewport
     * @param screenWidth Breedte van het scherm
     * @param screenHeight Hoogte van het scherm
     */
    private void createViewWithoutViewport(int screenWidth, int screenHeight) {
        View view = new View(screenWidth,screenHeight);
        view.setBackground(loadImage("src/main/java/nl/han/ica/spacearena/media/SpaceBackgroundColors.png"));

        setView(view);
        size(screenWidth, screenHeight);
    }

    /**
     * Creëert de view met viewport
     * @param worldWidth Totale breedte van de wereld
     * @param worldHeight Totale hoogte van de wereld
     * @param screenWidth Breedte van het scherm
     * @param screenHeight Hoogte van het scherm
     * @param zoomFactor Factor waarmee wordt ingezoomd
     */


    /**
     * Initialiseert geluid
     */
    private void initializeSound() {

    }


    /**
     * Maakt de spelobjecten aan
     */
    private void createObjects() {
	    
     
        player1 = new Player(this, RED, keysPlayer1, redSpawn);
        addGameObject(player1, redSpawn[0], redSpawn[1]);
       
        player2 = new Player(this, BLUE, keysPlayer2, blueSpawn);
        addGameObject(player2, blueSpawn[0], blueSpawn[1]);
        
        respawnAllPlayers();
        spaceCore = new SpaceCore(this);
        addGameObject(spaceCore,200,0);
    }

    /**
     * Maakt de spawner voor de bellen aan
     */
    public void createSpawners() {
    	float distantLasersPerSecond = (float) (1.5);
        distantLaserSpawner=new DistantLaserSpawner(this,distantLasersPerSecond);
        powerUpSpawner = new PowerUpSpawner(this, 0, worldWidth, 100, worldHeight, 10);
    }

    /**
     * Maakt het dashboard aan
     * @param dashboardWidth Gewenste breedte van dashboard
     * @param dashboardHeight Gewenste hoogte van dashboard
     */
    private void createDashboard(int dashboardWidth,int dashboardHeight) {
    	Dashboard dashboard = new Dashboard(0,0, dashboardWidth, dashboardHeight);
    	scoreText = new TextObjectDashboard("SCORE", worldWidth/2, 0, 25, CENTER);
    	winText = new TextObjectDashboard(winnerText, worldWidth/2, 70, 25, CENTER);
        health1Text = new TextObjectDashboard("HEALTH", 5, 0, 25, LEFT);
        health2Text = new TextObjectDashboard("HEALTH", worldWidth-5, 0, 25, RIGHT);
        weaponText1 = new TextObjectDashboard("WEAPON", 150, 0, 25, LEFT);					//ipv weapon een methode die een string returnt	Weapon.getGunType();
        weaponEquipedText1 = new TextObjectDashboard("", 150, 20, 30, LEFT);
        weaponEquipedText2 = new TextObjectDashboard("", worldWidth - 150, 20, 30, RIGHT);
        weaponText2 = new TextObjectDashboard("WEAPON", worldWidth - 150, 0, 25, RIGHT); 	//ipv weapon een methode die een string returnt OF globale var. aanpassen in de methode summon (weapon)
        scoreboardPlayer1 = new  TextObjectDashboard("", worldWidth/2-8, 20, 50, RIGHT);
        scoreboardPlayer2 = new  TextObjectDashboard("", worldWidth/2+8, 20, 50, LEFT);
        healthDisplayPlayer1 = new TextObjectDashboard("", 5, 20, 45, LEFT);
        healthDisplayPlayer2 = new TextObjectDashboard("", worldWidth-5, 20, 45, RIGHT);
        
        
        dashboard.addGameObject(scoreText);
        dashboard.addGameObject(winText);
        dashboard.addGameObject(health1Text);
        dashboard.addGameObject(health2Text);
        dashboard.addGameObject(weaponText1);
        dashboard.addGameObject(weaponText2);
        dashboard.addGameObject(scoreboardPlayer1);
        dashboard.addGameObject(scoreboardPlayer2);
        dashboard.addGameObject(healthDisplayPlayer1);
        dashboard.addGameObject(healthDisplayPlayer2);
        dashboard.addGameObject(weaponEquipedText1);
        dashboard.addGameObject(weaponEquipedText2);
        
        
        scoreboardPlayer1.setTextColor(color(255,100,100));
        healthDisplayPlayer1.setTextColor(color(255,100,100));
        weaponEquipedText1.setTextColor(color(255,100,100));
        scoreboardPlayer2.setTextColor(color(100,100,255));
        healthDisplayPlayer2.setTextColor(color(100,100,255));
        weaponEquipedText2.setTextColor(color(100,100,255));
       
        addDashboard(dashboard);
    }

    /**
     * Initialiseert de opslag van de bellenteller
     * en laadt indien mogelijk de eerder opgeslagen
     * waarde
     */

    /** 
     * Initialiseert de tilemap
     */
    private void initializeTileMap() {
        /* TILES */
        Sprite boardSpriteMetalJunkA = new Sprite("src/main/java/nl/han/ica/spacearena/media/metalJunk1.png");
        Sprite boardSpriteMetalJunkB = new Sprite("src/main/java/nl/han/ica/spacearena/media/metalJunk2.png");
        Sprite boardSpriteRock = new Sprite("src/main/java/nl/han/ica/spacearena/media/Rock.png");
        TileType<BoardsTile> boardTileTypeMetalJunkA = new TileType<>(BoardsTile.class, boardSpriteMetalJunkA);
        TileType<BoardsTile> boardTileTypeMetalJunkB = new TileType<>(BoardsTile.class, boardSpriteMetalJunkB);
        TileType<BoardsTile> boardTileTypeRock = new TileType<>(BoardsTile.class, boardSpriteRock);

        TileType[] tileTypes = { boardTileTypeMetalJunkA, boardTileTypeMetalJunkB, boardTileTypeRock};
        int tileSize=50;
        int tilesMap[][]={
                {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
                { 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0},
                {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1, 1, 1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
                {-1,-1,-1,-1,-1,-1,-1, 1, 0,-1,-1, 1, 2,-1,-1, 1, 0,-1,-1,-1,-1,-1,-1,-1},
                {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
                {-1,-1,-1,-1, 0,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1, 0,-1,-1,-1,-1},
                {-1,-1,-1,-1, 0,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1, 0,-1,-1,-1,-1},
                {-1,-1,-1,-1, 0,-1,-1,-1,-1,-1, 0, 1, 1, 0,-1,-1,-1,-1,-1, 0,-1,-1,-1,-1},
                {-1,-1,-1,-1, 0,-1,-1,-1,-1, 2, 0,-1,-1, 0, 2,-1,-1,-1,-1, 0,-1,-1,-1,-1},
                {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
                {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
                {-1,-1,-1,-1, 0,-1,-1,-1,-1, 2, 0,-1,-1, 0, 2,-1,-1,-1,-1, 0,-1,-1,-1,-1},
                {-1,-1,-1,-1, 0,-1,-1,-1,-1,-1, 0, 1, 1, 0,-1,-1,-1,-1,-1, 0,-1,-1,-1,-1},
                {-1,-1,-1,-1, 0,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1, 0,-1,-1,-1,-1},
                {-1,-1,-1,-1, 0,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1, 0,-1,-1,-1,-1},
                {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
                {-1,-1,-1,-1,-1,-1,-1, 1, 0,-1,-1, 1, 2,-1,-1, 1, 0,-1,-1,-1,-1,-1,-1,-1},
                {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1, 0, 1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1}
        };
        tileMap = new TileMap(tileSize, tileTypes, tilesMap);
    }

    @Override
    public void update() {
    	currentTimestamp = millis();
    	player1.checkDeaths();
    	player2.checkDeaths();
    	refreshDashboardText();
    	
        if(player2.getDeaths() >= 5 && gameIsOver == false) {
    		this.winnerText = "Player 1 wins!";
    		gameIsOver = true;
    		
    	} else if(player1.getDeaths() >= 5 && gameIsOver == false) {
    		this.winnerText = "Player 2 wins!";
    		gameIsOver = true;
    		
    	}
   
    }

    /**
     * Vernieuwt het dashboard
     */
    public void respawnAllPlayers() {
    	player1.respawn();
    	player2.respawn();
    	CURRENTWEAPONP1 = CURRENTWEAPONP2 = "Light Plasmagun";
    }
    
    private void refreshDashboardText() {
    	  scoreboardPlayer1.setText(""+player2.getDeaths());
          scoreboardPlayer2.setText(""+player1.getDeaths());
          healthDisplayPlayer1.setText(""+player1.getHealth());
          healthDisplayPlayer2.setText(""+player2.getHealth());
          weaponEquipedText1.setText(""+CURRENTWEAPONP1);
          weaponEquipedText2.setText(""+CURRENTWEAPONP2);
          winText.setText(winnerText);
          
          
          

    }

    /**
     * Verhoogt de teller voor het aantal
     * geknapte bellen met 1
     */
    
    /**
     * Toont winnaar voor 5 seconden, daarna reset het spel
     */

    //Getters
    /**
     * @return worldWidth
     */
    public static int getWorldWidth() {
	return worldWidth;
    }
    
    /**
     * @return worldHeight
     */
    public static int getWorldHeight() {
    	return worldHeight;
    }
}
