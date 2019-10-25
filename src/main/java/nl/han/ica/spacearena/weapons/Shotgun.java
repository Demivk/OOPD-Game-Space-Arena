package nl.han.ica.spacearena.weapons;
import nl.han.ica.spacearena.Laser;
import nl.han.ica.spacearena.Player;
import nl.han.ica.spacearena.SpaceArena;

public class Shotgun implements IWeapon {
	 
	private int damageSG = 8;
	private int speedSG = 3;
	//private int cooldownSG = 1000;
	//private int durationSG = 20000;

	/**
	 * Constructor
	 * @param owner
	 */
	public Shotgun(Player owner) {
	}
	
	/**
	 * Zorgt ervoor dat er aan de voor- en zijkanten lasers geschoten kunnen worden
	 * @param forwardDirection
	 * @return laserDirections
	 */
	private float[] laserDirectionCalculator(float forwardDirection) {
		float[] laserDirections = {forwardDirection-90,forwardDirection,forwardDirection+90};
		
		for(int i = 0; i < laserDirections.length; i++) {
			if(laserDirections[i] == -90) {
				laserDirections[i] = 270;
			}else if(laserDirections[i] == 360) {
				laserDirections[i] = 0;
			}
		}
		return laserDirections;
	}
	
	/**
	 * Maakt nieuwe lasers aan
	 */
	public void shoot(float x, float y, float direction, SpaceArena world, String filename, String colour, Player owner) {
		float[] laserDirections = new float[3];
		laserDirections = laserDirectionCalculator(direction);
		
		Laser laser = new Laser(x, y, speedSG, damageSG, laserDirections[0], world, filename, colour, owner);
		Laser laserB = new Laser(x, y, speedSG, damageSG, laserDirections[1], world, filename, colour, owner);
		Laser laserC = new Laser(x, y, speedSG, damageSG, laserDirections[2], world, filename, colour, owner);
		world.addGameObject(laser);
		world.addGameObject(laserB);
		world.addGameObject(laserC);
		laser.move();
		laserB.move();
		laserC.move();
	}
}
