package nl.han.ica.spacearena;

import nl.han.ica.OOPDProcessingEngineHAN.Collision.ICollidableWithGameObjects;
import nl.han.ica.OOPDProcessingEngineHAN.Objects.GameObject;
import nl.han.ica.OOPDProcessingEngineHAN.Sound.Sound;
import processing.core.PGraphics;

import java.util.List;

/**
 * @author Ralph Niels, Dennis Gommer, Demi van Kesteren
 */
public class DistantLaser extends GameObject{


    private SpaceArena world;
    private int laserSize;

    /**
     * Constructor
     * @param laserSize Afmeting van de laser
     * @param world Referentie naar de wereld
     */
    public DistantLaser(SpaceArena world) {
        
        this.world=world;
        setySpeed(-60);

    }

    @Override
    public void update() {
        if (getY() <=-100) {
            world.deleteGameObject(this);
        }
    }

    @Override
    public void draw(PGraphics g) {
        g.ellipseMode(g.CORNER);
        g.stroke(244, 98, 66, 50);
        g.fill(244, 125, 65, 50);
        g.ellipse(getX(), getY(), 2, 200);
    }

    
}
