package nl.han.ica.spacearena;

import nl.han.ica.OOPDProcessingEngineHAN.Collision.CollidedTile;
import nl.han.ica.OOPDProcessingEngineHAN.Collision.ICollidableWithGameObjects;
import nl.han.ica.OOPDProcessingEngineHAN.Collision.ICollidableWithTiles;
import nl.han.ica.OOPDProcessingEngineHAN.Exceptions.TileNotFoundException;
import nl.han.ica.OOPDProcessingEngineHAN.Objects.AnimatedSpriteObject;
import nl.han.ica.OOPDProcessingEngineHAN.Objects.GameObject;
import nl.han.ica.OOPDProcessingEngineHAN.Objects.Sprite;
import nl.han.ica.spacearena.tiles.BoardsTile;
import nl.han.ica.spacearena.weapons.Plasmagun;
import nl.han.ica.spacearena.weapons.Railgun;
import nl.han.ica.spacearena.weapons.Shotgun;

import processing.core.PVector;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ralph Niels, Dennis Gommer en Demi van Kesteren
 */
public class Player extends AnimatedSpriteObject implements ICollidableWithTiles, ICollidableWithGameObjects {

	final int size = 25;
	private int deaths = 0;
	private int health = 500;
	final int playerSpeed = 5;

	private final SpaceArena world;
	private String colour;
	private boolean pickUp = true;
	private ArrayList<Keys> keys;
	private int numberOfKeys = 5;
	private int playerDirection = 0;
	
	private int leftKeyIndex = 0;
	private int upKeyIndex = 1;
	private int rightKeyIndex = 2;
	private int downKeyIndex = 3;
	private int shootKeyIndex = 4;
	
	private int[] spawnPos = new int[2];
	
	private long previousFireTimestamp = 0;
	private long previousGunTimestampP1 = 0;
	private long previousGunTimestampP2 = 0;
	
	/**
	 * Constructor
	 * 
	 * @param world
	 *            Referentie naar de wereld
	 */
	public Player(SpaceArena world, String colour, ArrayList<Keys> keys, int[] spawnPos) {
		super(new Sprite("src/main/java/nl/han/ica/spacearena/media/SpaceShip" + colour + "4.png"), 4);
		this.world = world;
		this.colour = colour;
		this.keys = keys;
		this.spawnPos = spawnPos;
		
		setCurrentFrameIndex(world.UPIMAGEINDEX);
		setFriction(0.05f);
	}

	@Override
	public void update() {
		syncSpriteDirection();
		checkKeys();
		blockOutOfBounds();
		checkDurationWeapon(this);
	}
	
	/**
	 * Zorgt ervoor dat de speler niet buiten het scherm kan komen
	 */
	private void blockOutOfBounds() {
		if (getX() <= 0) {
			setxSpeed(0);
			setX(1);
		}
		if (getY() <= 0) {
			setySpeed(0);
			setY(1);
		}
		if (getX() >= world.getWidth() - size*2) {
			setxSpeed(0);
			setX(world.getWidth() - size*2-1);
		}
		if (getY() >= world.getHeight() - size*2) {
			setySpeed(0);
			setY(world.getHeight() - size*2-1);
		}
	}

	/**
	 * Zet het wapen terug naar de default (Light Plasmagun) na ~30 seconden
	 * @param player
	 */
	public void checkDurationWeapon(Player player) {	//aanroepen in update?
		int durationWeapon = 30000;
		if(player.getColour() == "Red") { //P1
			if(world.CURRENTWEAPONP1 != "Light Plasmagun") {
				if(world.currentTimestamp - this.previousGunTimestampP1 >= durationWeapon) {
					this.previousGunTimestampP1  = world.currentTimestamp;
					world.CURRENTWEAPONP1 = "Light Plasmagun";
					
				}
			}
		} else if(player.getColour() == "Blue") { //P2
			if(world.CURRENTWEAPONP2 != "Light Plasmagun") {
				if(world.currentTimestamp - this.previousGunTimestampP2 >= durationWeapon) {
					this.previousGunTimestampP2  = world.currentTimestamp;
					world.CURRENTWEAPONP2 = "Light Plasmagun";
				
				}
			}
		}
	}

	/**
	 * Checkt welke key ingedrukt is
	 */
	private void checkKeys() {
		if (this.keys.get(leftKeyIndex).getIsKeyPressed()) {
			setDirectionSpeed(SpaceArena.LEFTDEGREES, playerSpeed);
			
		}
		if (this.keys.get(upKeyIndex).getIsKeyPressed()) {
			setDirectionSpeed(SpaceArena.UPDEGREES, playerSpeed);
			
		}
		if (this.keys.get(rightKeyIndex).getIsKeyPressed()) {
			setDirectionSpeed(SpaceArena.RIGHTDEGREES, playerSpeed);
			
		}
		if (this.keys.get(downKeyIndex).getIsKeyPressed()) {
			setDirectionSpeed(SpaceArena.DOWNDEGREES, playerSpeed);
		
		}
		if (this.keys.get(shootKeyIndex).getIsKeyPressed()) {
			if(SpaceArena.currentTimestamp - this.previousFireTimestamp > 200) {
				this.previousFireTimestamp = SpaceArena.currentTimestamp;
				pickLaser();
			}
		}
	}
	
	/**
	 * Bepaald aan de hand van 'CURRENTWEAPONP1/CURRENTWEAPONP2' welk wapen er gebruikt wordt en maakt lasers aan
	 */
	public void pickLaser() {
		float xPos = getX()-(this.size/4) + this.size;
		float yPos = getY()+(this.size/2)+7;
		
		if(this.colour == "Red") {
			switch(world.CURRENTWEAPONP1) {
				case "Heavy Plasmagun":
					Plasmagun heavyPlasmagun = new Plasmagun(this, world.HEAVY);
					System.out.println("Heavy Plasmagun");
					heavyPlasmagun.shoot(xPos, yPos, this.getDirection(), this.world, "Laser", this.getColour(), this);
					break;
				case "Light Railgun":
					Railgun lightRailgun = new Railgun(this, world.LIGHT);
					System.out.println("Light Railgun");
					lightRailgun.shoot(xPos, yPos, this.getDirection(), this.world, "LongLaser", this.getColour(), this);
					break;
				case "Heavy Railgun":
					Railgun heavyRailgun = new Railgun(this, world.HEAVY);
					System.out.println("Heavy Railgun");
					heavyRailgun.shoot(xPos, yPos, this.getDirection(), this.world, "LongLaser", this.getColour(), this);
					break;
				case "Shotgun":
					Shotgun shotgun = new Shotgun(this);
					System.out.println("Shotgun");
					shotgun.shoot(xPos, yPos, this.getDirection(), this.world, "Laser", this.getColour(), this);
					break;
					//
				case "Light Plasmagun":
					Plasmagun lightPlasmagun = new Plasmagun(this, world.LIGHT);
					System.out.println("Light Plasmagun");
					lightPlasmagun.shoot(xPos, yPos, this.getDirection(), this.world, "Laser", this.getColour(), this);
					break;
			}

		} else if(this.colour == "Blue") {
			switch(world.CURRENTWEAPONP2) {
				case "Heavy Plasmagun":
					Plasmagun heavyPlasmagun = new Plasmagun(this, world.HEAVY);
					System.out.println("Heavy Plasmagun");
					heavyPlasmagun.shoot(xPos, yPos, this.getDirection(), this.world, "Laser", this.getColour(), this);
					break;
				case "Light Railgun":
					Railgun lightRailgun = new Railgun(this, world.LIGHT);
					System.out.println("Light Railgun");
					lightRailgun.shoot(xPos, yPos, this.getDirection(), this.world, "LongLaser", this.getColour(), this);
					break;
				case "Heavy Railgun":
					Railgun heavyRailgun = new Railgun(this, world.HEAVY);
					System.out.println("Heavy Railgun");
					heavyRailgun.shoot(xPos, yPos, this.getDirection(), this.world, "LongLaser", this.getColour(), this);
					break;
				case "Shotgun":
					Shotgun shotgun = new Shotgun(this);
					System.out.println("Shotgun");
					shotgun.shoot(xPos, yPos, this.getDirection(), this.world, "Laser", this.getColour(), this);
					break;
					//
				case "Light Plasmagun":
					Plasmagun lightPlasmagun = new Plasmagun(this, world.LIGHT);
					System.out.println("Light Plasmagun");
					lightPlasmagun.shoot(xPos, yPos, this.getDirection(), this.world, "Laser", this.getColour(), this);
					break;
			}

		}
	}
	
	/**
	 * Zet de desbetreffende key op true
	 */
	@Override
	public void keyPressed(int keyCode, char key) {		
		for (int i = 0; i < numberOfKeys; i++) {
			if (keyCode == this.keys.get(i).getPressedKey()) {
				this.keys.get(i).setIsKeyPressed(true);
			}
		}
	}
	
	/**
	 * Zet de desbetreffende key op false
	 */
	public void keyReleased(int keyCode, char key) {
		for (int i = 0; i < numberOfKeys; i++) {
			if (keyCode == this.keys.get(i).getPressedKey()) {
				this.keys.get(i).setIsKeyPressed(false);
			}
		}
	}
	
	/**
	 * Veranderd de richting van de speler
	 */
	private void syncSpriteDirection() {
		playerDirection = (int) (this.getDirection());
		switch(playerDirection) {
			case 270:
				setCurrentFrameIndex(SpaceArena.LEFTIMAGEINDEX);
				break;
			case 0:
				setCurrentFrameIndex(SpaceArena.UPIMAGEINDEX);
				break;
			case 90:
				setCurrentFrameIndex(SpaceArena.RIGHTIMAGEINDEX);
				break;
			case 180:
				setCurrentFrameIndex(SpaceArena.DOWNIMAGEINDEX);
				break;
		}
	}

	/**
	 * Veranderd de health van de speler met behulp van de damage
	 * @param damage
	 */
	public void doDamage(int damage) {
		this.health -= damage;
	}

	/**
	 * Controleert of de speler dood is (health kleiner of gelijk aan 0)
	 * @return health <= 0
	 */
	public boolean died() {
		return (this.health <= 0);
	}
	
	/**
	 * Reset de health van de speler, zet de speler terug op de startpositie, zet het wapen terug naar default, 
	 */
	public void respawn() {
		this.health = 500;
		setX(spawnPos[0]);
		setY(spawnPos[1]);	
	}
	
	/**
	 * Checkt of de speler dood is
	 */
	public void checkDeaths() {
		if (died()) {
			System.out.println("YOU DED");
			this.deaths++;
			world.respawnAllPlayers();
		}
	}

	@Override
	public void tileCollisionOccurred(List<CollidedTile> collidedTiles) {
		PVector vector;

		for (CollidedTile ct : collidedTiles) {
			if (ct.theTile instanceof BoardsTile) {
				if (ct.collisionSide == ct.TOP) {
					try {
						vector = world.getTileMap().getTilePixelLocation(ct.theTile);
						setY(vector.y - getHeight());
					} catch (TileNotFoundException e) {
						e.printStackTrace();
					}
				}
				if (ct.collisionSide == ct.BOTTOM) {
					try {
						vector = world.getTileMap().getTilePixelLocation(ct.theTile);
						setY(vector.y + getHeight());
					} catch (TileNotFoundException e) {
						e.printStackTrace();
					}
				}
				if (ct.collisionSide == ct.LEFT) {
					try {
						vector = world.getTileMap().getTilePixelLocation(ct.theTile);
						setX(vector.x - getWidth());
					} catch (TileNotFoundException e) {
						e.printStackTrace();
					}
				}
				if (ct.collisionSide == ct.RIGHT) {
					try {
						vector = world.getTileMap().getTilePixelLocation(ct.theTile);
						setX(vector.x + getHeight());
					} catch (TileNotFoundException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	@Override
	public void gameObjectCollisionOccurred(List<GameObject> collidedGameObjects) {
		// TODO Auto-generated method stub

	}

	// Getters
	/**
	 * @return deaths
	 */
	public int getDeaths() {
		return this.deaths;
	}
	
	/**
	 * @return colour
	 */
	public String getColour() {
		return this.colour;
	}
	
	/**
	 * @return Player health
	 */
	public int getHealth() {
		return health;
	}

	/**
	 * @return pickUp
	 */
	public boolean isPickUp() {
		return pickUp;
	}

	/**
	 * Set pickUp
	 * @param pickUp
	 */
	public void setPickUp(boolean pickUp) {
		this.pickUp = pickUp;
	}
}
