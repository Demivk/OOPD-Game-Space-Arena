package nl.han.ica.spacearena;

import java.util.List;

import nl.han.ica.OOPDProcessingEngineHAN.Collision.CollidedTile;
import nl.han.ica.OOPDProcessingEngineHAN.Collision.ICollidableWithGameObjects;
import nl.han.ica.OOPDProcessingEngineHAN.Collision.ICollidableWithTiles;
import nl.han.ica.OOPDProcessingEngineHAN.Exceptions.TileNotFoundException;
import nl.han.ica.OOPDProcessingEngineHAN.Objects.GameObject;
import nl.han.ica.OOPDProcessingEngineHAN.Objects.Sprite;
import nl.han.ica.spacearena.tiles.BoardsTile;
import nl.han.ica.OOPDProcessingEngineHAN.Objects.AnimatedSpriteObject;

public class Laser extends AnimatedSpriteObject implements ICollidableWithTiles, ICollidableWithGameObjects {

	private float x, y; 
	private int laserSpeed, damage;
	private float direction;
	private SpaceArena world;
	private Player owner;

	/**
	 * Constructor
	 * @param x
	 * @param y
	 * @param laserSpeed
	 * @param damage
	 * @param direction
	 * @param world
	 * @param filename
	 * @param colour
	 * @param owner
	 */
	public Laser(float x, float y, int laserSpeed, int damage, float direction, SpaceArena world, String filename, String colour, Player owner) {
		super(new Sprite("src/main/java/nl/han/ica/spacearena/media/" + filename + colour + ".png"), 4);
		this.x = x;
		this.y = y;
		this.laserSpeed = laserSpeed;
		this.damage = damage;
		this.direction = direction;
		this.world = world;
		this.owner = owner;
	}

	/**
	 * Verplaatst de laser
	 */
	public void move() {
		if (this.direction == world.UPDEGREES) {
			setX(this.x);
			this.y -= this.laserSpeed;
			setCurrentFrameIndex(world.UPIMAGEINDEX);
		}
		if (this.direction == world.RIGHTDEGREES) {
			this.x += this.laserSpeed;
			setY(this.y);
			setCurrentFrameIndex(world.RIGHTIMAGEINDEX);
		}
		if (this.direction == world.DOWNDEGREES) {
			setX(this.x);
			this.y += this.laserSpeed;
			setCurrentFrameIndex(world.DOWNIMAGEINDEX);
		}
		if (this.direction == world.LEFTDEGREES) {
			this.x -= this.laserSpeed;
			setY(this.y);
			setCurrentFrameIndex(world.LEFTIMAGEINDEX);
		}
		setX(this.x);
		setY(this.y);
		setDirectionSpeed(this.direction, this.laserSpeed);
	}

	public void update() {
		if (this.x < 0 || this.y < 0 || this.x > SpaceArena.getWorldWidth() || this.y > SpaceArena.getWorldHeight()) {
			world.deleteGameObject(this);
		}
	}

	@Override
	public void gameObjectCollisionOccurred(List<GameObject> collidedGameObjects) {
		for (GameObject g: collidedGameObjects) {
			if (g instanceof Player && g != this.owner) {
				((Player)g).doDamage(this.damage);
				world.deleteGameObject(this);
			}
			
		}
	}

	@Override
	public void tileCollisionOccurred(List<CollidedTile> collidedTiles) {
		for (CollidedTile ct : collidedTiles) {
			if (ct.theTile instanceof BoardsTile) {
				if (ct.collisionSide == ct.RIGHT || ct.collisionSide == ct.LEFT || ct.collisionSide == ct.TOP || ct.collisionSide == ct.BOTTOM  /* ct.collisionSide == ct.INSIDE */) {
					try {
						world.deleteGameObject(this);
					} catch (TileNotFoundException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
}
