package nl.han.ica.spacearena;
import java.util.List;
import java.util.Random;

import nl.han.ica.OOPDProcessingEngineHAN.Collision.CollidedTile;
import nl.han.ica.OOPDProcessingEngineHAN.Collision.ICollidableWithGameObjects;
import nl.han.ica.OOPDProcessingEngineHAN.Collision.ICollidableWithTiles;
import nl.han.ica.OOPDProcessingEngineHAN.Exceptions.TileNotFoundException;
import nl.han.ica.OOPDProcessingEngineHAN.Objects.AnimatedSpriteObject;
import nl.han.ica.OOPDProcessingEngineHAN.Objects.GameObject;
import nl.han.ica.OOPDProcessingEngineHAN.Objects.Sprite;
import nl.han.ica.spacearena.tiles.BoardsTile;

public class PowerUp extends AnimatedSpriteObject implements ICollidableWithTiles, ICollidableWithGameObjects {
	
	private SpaceArena world;
	private Player owner;	
	Random randomizerWeapons = new Random();
	int randomNumber = randomizerWeapons.nextInt(4) + 1;
	
	/**
	 * Constructor
	 * @param world
	 */
	public PowerUp(SpaceArena world) {
		super(new Sprite("src/main/java/nl/han/ica/spacearena/media/poweruptest.png"), 1);
		this.world = world;
	}

	@Override
	public void gameObjectCollisionOccurred(List<GameObject> collidedGameObjects) {
		for (GameObject g: collidedGameObjects) {
			if (g instanceof Player) {
				
					System.out.println(((Player)g).getColour() + " TOUCHED THE POWER UP!");
					summonWeapon(g);
					world.deleteGameObject(this);
					
			}
			if (g instanceof Laser) {
				world.deleteGameObject(this);
			}
		}
	}
	
	/**
	 * Veranderd current weapon
	 * @param g
	 */
	public void summonWeapon(GameObject g) {
		if(g instanceof Player) {
			switch(randomNumber) {
				case 1:
					if(((Player)g).getColour() == world.RED) {
						world.CURRENTWEAPONP1 = "Heavy Plasmagun";
					} else {
						world.CURRENTWEAPONP2 = "Heavy Plasmagun";
					}
					break;
				case 2:
					if(((Player)g).getColour() == world.RED) {
						world.CURRENTWEAPONP1 = "Light Railgun";
					} else {
						world.CURRENTWEAPONP2 = "Light Railgun";
					}
					break;
				case 3:
					if(((Player)g).getColour() == world.RED) {
						world.CURRENTWEAPONP1 = "Heavy Railgun";
					} else {
						world.CURRENTWEAPONP2 = "Heavy Railgun";
					}
					break;
				case 4:
					if(((Player)g).getColour() == world.RED) {
						world.CURRENTWEAPONP1 = "Shotgun";
					} else {
						world.CURRENTWEAPONP2 = "Shotgun";
					}
					break;
			}
		}
	}
	
	

	@Override
	public void tileCollisionOccurred(List<CollidedTile> collidedTiles) {
		// TODO Auto-generated method stub
		for (CollidedTile ct : collidedTiles) {
			if (ct.theTile instanceof BoardsTile) {
				if (ct.collisionSide == ct.RIGHT || ct.collisionSide == ct.LEFT || ct.collisionSide == ct.TOP || ct.collisionSide == ct.BOTTOM || ct.collisionSide == ct.INSIDE) {
					try {
						world.deleteGameObject(this);
					} catch (TileNotFoundException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
	}
}
