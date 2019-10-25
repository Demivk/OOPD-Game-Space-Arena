package nl.han.ica.spacearena;

public class Keys {
	
	private int pressedKey;
	private boolean isKeyPressed = false;
	
	/**
	 * Constructor
	 * @param pressedKey
	 * @param isKeyPressed
	 */
	public Keys(int pressedKey, boolean isKeyPressed) {
		this.pressedKey = pressedKey;
		this.isKeyPressed = isKeyPressed;
	}

	/**
	 * Controleert of key ingedrukt is (getter)
	 * @return isKeyPressed
	 */
	public boolean getIsKeyPressed() {
		return this.isKeyPressed;
	}
	
	/**
	 * Set isKeyPressed naar true/false
	 * @param isKeyPressed
	 */
	public void setIsKeyPressed(boolean isKeyPressed) {
		this.isKeyPressed = isKeyPressed;
	}
	
	/**
	 * Returnt ingedrukte key
	 * @return pressedKey
	 */
	public int getPressedKey() {
		return this.pressedKey;
	}
}
