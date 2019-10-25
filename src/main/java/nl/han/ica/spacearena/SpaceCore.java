package nl.han.ica.spacearena;

import nl.han.ica.OOPDProcessingEngineHAN.Objects.Sprite;
import nl.han.ica.OOPDProcessingEngineHAN.Objects.SpriteObject;

/**
 * @author Ralph Niels, Dennis Gommer, Demi van Kesteren
 * SpaceCore is een spelobject dat zelfstandig door de wereld beweegt
 */
public class SpaceCore extends SpriteObject {

    private SpaceArena world;

    /**
     * Constructor
     * @param world Referentie naar de wereld
     */
    public SpaceCore(SpaceArena world) {
        this(new Sprite("src/main/java/nl/han/ica/spacearena/media/SpaceCore.png"));
        this.world=world;
    }

    /**
     * Maak een SpaceCore aan met een sprite
     * @param sprite De sprite die aan dit object gekoppeld moet worden
     */
    private SpaceCore(Sprite sprite) {
        super(sprite);
        setxSpeed(-2);
    }

    @Override
    public void update() {
        if (getX()+getWidth()<=0) {
            setX(world.getWidth());
        }
    }
}
