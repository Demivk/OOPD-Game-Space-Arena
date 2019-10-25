package nl.han.ica.spacearena.weapons;
import nl.han.ica.spacearena.Laser;
import nl.han.ica.spacearena.Player;
import nl.han.ica.spacearena.SpaceArena;

public class Railgun implements IWeapon {
	
	private int speedRG = 15;
	private int damageLightRG = 10;
	//private int cooldownLightRG = 1500;
	//private int durationLightRG = 30;
	private int damageHeavyRG = 40;
	//private int cooldownHeavyRG = 3000;
	//private int durationHeavyRG = 30000;
	
	private String gunType;

	/**
	 * Constructor
	 * @param owner
	 * @param gunType
	 */
	public Railgun(Player owner, String gunType) {
		this.gunType = gunType;
	}
	
	/**
	 * Maakt een nieuwe laser aan
	 */
	public void shoot(float x, float y, float direction, SpaceArena world, String filename, String colour, Player owner) {
		if(gunType == "Heavy") {
			Laser laser = new Laser(x, y, speedRG, damageHeavyRG, direction, world, filename, colour, owner);
			world.addGameObject(laser);
			laser.move();
		} else {
			Laser laser = new Laser(x, y, speedRG, damageLightRG, direction, world, filename, colour, owner);
			world.addGameObject(laser);
			laser.move();
		}
	}
}
