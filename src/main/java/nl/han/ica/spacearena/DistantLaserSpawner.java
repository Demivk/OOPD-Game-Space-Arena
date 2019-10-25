package nl.han.ica.spacearena;

import nl.han.ica.OOPDProcessingEngineHAN.Alarm.Alarm;
import nl.han.ica.OOPDProcessingEngineHAN.Alarm.IAlarmListener;
import nl.han.ica.OOPDProcessingEngineHAN.Sound.Sound;

import java.util.Random;

/**
 * @author Ralph Niels, Dennis Gommer, Demi van Kesteren
 */
public class DistantLaserSpawner implements IAlarmListener {

    private float lasersPerSecond;
    private Random random;
    private SpaceArena world;


    /** Constructor
     * @param world Referentie naar de wereld
     * @param lasersPerSecond Aantal lasers dat per seconden gemaakt moet worden
     */
    public DistantLaserSpawner(SpaceArena world,float lasersPerSecond) {
        this.lasersPerSecond=lasersPerSecond;
        this.world=world;
        random=new Random();
        startAlarm();
    }

    private void startAlarm() {
        Alarm alarm=new Alarm("New distantLaser",1/this.lasersPerSecond);
        alarm.addTarget(this);
        alarm.start();
    }

    @Override
    public void triggerAlarm(String alarmName) {

        DistantLaser b=new DistantLaser(world);
        world.addGameObject(b, random.nextInt(world.getWidth()), world.getHeight());
        startAlarm();
    }

}
