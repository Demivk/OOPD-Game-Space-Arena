package nl.han.ica.spacearena.weapons;

import nl.han.ica.spacearena.Player;
import nl.han.ica.spacearena.SpaceArena;

public interface IWeapon {
	
	/**
	 * Maakt nieuwe laser aan
	 * @param x
	 * @param y
	 * @param direction
	 * @param world
	 * @param filename
	 * @param colour
	 * @param owner
	 */
	public void shoot(float x, float y, float direction, SpaceArena world, String filename, String colour, Player owner);
}
