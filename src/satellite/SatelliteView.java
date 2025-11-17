package satellite;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import observer.*;
import src.nicellipse.component.NiImage;
import src.nicellipse.component.NiRectangle;


public class SatelliteView extends NiRectangle implements SatelliteListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Satellite mobi;
	private NiImage imageLayer;

	public SatelliteView(Satellite mobi) {
		this.mobi = mobi;
		 // Charger l'image comme dans ton exemple GrBalise
        File path = new File("src/ressources/satellite.png");
        BufferedImage rawImage = null;
        try {
            rawImage = ImageIO.read(path);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Ajouter l'image par-dessus le rectangle
        if (rawImage != null) {
            imageLayer = new NiImage(rawImage);
            imageLayer.setLocation(0, 0);
            this.add(imageLayer);

            // Dimensionner le composant selon l'image
            this.setDimension(new Dimension(rawImage.getWidth(), rawImage.getHeight()));
        } 
	}
	
	@Override
	public void SatelliteMoveEvent(SatelliteMoveEvent event) {
		Satellite source = (Satellite) event.getSource();
		int x = source.getX();
		int y = source.getY();
		
		int pw = this.getParent().getWidth();
		int w = this.getWidth();
		int maxX = pw - w;
		
		x = ((x % maxX) + maxX) % maxX;
		mobi.setX(x);
		this.setLocation(x, y);
		updateColor();
	}
	@Override
	public void SattelliteSynchroEvent(SatelliteSynchroEvent event) {
		//Circle.setvisible(true);
		updateColor();
		
		
	}
	
	private void updateColor() {
        switch (mobi.getState()) {
            case Libre:
                this.setBackground(Color.WHITE);
            	this.setOpaque(false);
                this.setBorder(null);
                
                break;
            case Synchro:
                this.setBackground(Color.BLUE);
                
                break;
        }
    }
}
