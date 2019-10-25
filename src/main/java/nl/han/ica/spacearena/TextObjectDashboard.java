package nl.han.ica.spacearena;

import nl.han.ica.OOPDProcessingEngineHAN.Objects.GameObject;
import processing.core.*;

public class TextObjectDashboard extends GameObject {

	private float xPos, yPos, textSize = 0;
    private String text;
    private int textXAlign;
    private int textColor = 255;
    
    public TextObjectDashboard(String text, float xPos, float yPos, float textSize, int textXAlign) {
        this.text=text;
        this.xPos=xPos;
        this.yPos=yPos;
        this.textSize = textSize;
        this.textXAlign = textXAlign;
    }
    
    public TextObjectDashboard(String text, float xPos, float yPos) {
    	this(text, xPos, yPos, 50, LEFT);
    }

    public void setText(String text) {
        this.text=text;
    }

    public void setTextColor(int textColor) {
    	this.textColor=textColor;
    }
    
    @Override
    public void update() {

    }

    @Override
    public void draw(PGraphics g) { 		
    	g.textAlign(textXAlign,g.TOP);
    	g.fill(textColor);
        g.textSize(textSize);
        g.text(text,xPos,yPos);
    }
}
