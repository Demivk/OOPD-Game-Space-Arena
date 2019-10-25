package nl.han.ica.spacearena;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import nl.han.ica.OOPDProcessingEngineHAN.Alarm.Alarm;
import nl.han.ica.OOPDProcessingEngineHAN.Alarm.IAlarmListener;
import nl.han.ica.OOPDProcessingEngineHAN.Collision.CollidedTile;
import nl.han.ica.OOPDProcessingEngineHAN.Collision.ICollidableWithGameObjects;
import nl.han.ica.OOPDProcessingEngineHAN.Collision.ICollidableWithTiles;
import nl.han.ica.OOPDProcessingEngineHAN.Objects.AnimatedSpriteObject;
import nl.han.ica.OOPDProcessingEngineHAN.Objects.GameObject;
import nl.han.ica.OOPDProcessingEngineHAN.Objects.Sprite;

public class PowerUpSpawner implements IAlarmListener {
	private float minXPos;
	private float maxXPos;
	private float minYPos;
	private float maxYPos;
	private float powerUpsPerMinute;
	private SpaceArena world;
	private Random random;
	
	public PowerUpSpawner(SpaceArena world, float minXPos, float maxXPos, float minYPos, float maxYPos, int powerUpsPerMinute) {
		this.minXPos = minXPos;
		this.maxXPos = maxXPos;
		this.minYPos = minYPos;
		this.maxYPos = maxYPos;
	    this.powerUpsPerMinute=powerUpsPerMinute;
	    this.world=world;
	    random=new Random();
	    startAlarm();
	}

    private void startAlarm() {
        Alarm alarm=new Alarm("New powerUp",60/this.powerUpsPerMinute);
        alarm.addTarget(this);
        alarm.start();
    }

    @Override
    public void triggerAlarm(String alarmName) {
    	int randomXPos=random.nextInt((int) (maxXPos - minXPos) + 1) + (int) (minXPos);
    	int randomYPos=random.nextInt((int) (maxYPos - minYPos) + 1) + (int) (minYPos);
        PowerUp powerUp=new PowerUp(world);
        world.addGameObject(powerUp, randomXPos, randomYPos);
        startAlarm();
    }
}
