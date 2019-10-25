package nl.han.ica.spacearena.objects;
import java.util.List;
import nl.han.ica.OOPDProcessingEngineHAN.Collision.ICollidableWithGameObjects;
import nl.han.ica.OOPDProcessingEngineHAN.Objects.AnimatedSpriteObject;
import nl.han.ica.OOPDProcessingEngineHAN.Objects.GameObject;
import nl.han.ica.OOPDProcessingEngineHAN.Objects.Sprite;
import nl.han.ica.spacearena.Player;

public class BrokenShip extends AnimatedSpriteObject implements Obstacles, ICollidableWithGameObjects {
	
	Player owner;

	public BrokenShip() {
		super(new Sprite("src/main/java/nl/han/ica/spacearena/media/brokenship.png"), 1);
	}
	
	//public void collide() {
	//}
	
	public void destroy () {
	}

	@Override
	public void gameObjectCollisionOccurred(List<GameObject> collidedGameObjects) {
		for (GameObject g: collidedGameObjects) {
			if (g instanceof Player) {
				System.out.println(((Player) g).getColour() + " hit the broken ship.");
			}
		}
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}
}