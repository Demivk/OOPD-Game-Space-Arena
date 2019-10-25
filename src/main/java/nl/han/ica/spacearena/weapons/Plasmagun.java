package nl.han.ica.spacearena.weapons;
import nl.han.ica.spacearena.Laser;
import nl.han.ica.spacearena.Player;
import nl.han.ica.spacearena.SpaceArena;

public class Plasmagun implements IWeapon {
	
	private int damageLightPG = 5;
	private int speedLightPG = 5;
	//private int cooldownLightPG = 500;
	private int damageHeavyPG = 15;
	private int speedHeavyPG = 10;
	//private int cooldownHeavyPG = 750;
	//private int durationHeavyPG = 30000;
	
	private String gunType;
	
	/**
	 * Constructor
	 * @param owner
	 * @param gunType
	 */
	public Plasmagun(Player owner, String gunType) {
		this.gunType = gunType;
	}
	
	/**
	 * Maakt een nieuwe laser aan
	 */
	public void shoot(float x, float y, float direction, SpaceArena world, String filename, String colour, Player owner) {
		if(gunType == "Heavy") {
			Laser laser = new Laser(x, y, speedHeavyPG, damageHeavyPG, direction, world, filename, colour, owner);
			world.addGameObject(laser);
			laser.move();
		} else {
			Laser laser = new Laser(x, y, speedLightPG, damageLightPG, direction, world, filename, colour, owner);
			world.addGameObject(laser);
			laser.move();
		}
	}
}
