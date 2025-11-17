package balise;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import src.nicellipse.component.NiRectangle;
import src.nicellipse.component.NiImage;
import observer.*;


public class BaliseView extends NiRectangle implements BaliseListener {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Balise balise;
	 private NiImage imageLayer;
    
    public BaliseView(Balise balise) throws IOException {
        this.balise = balise;
        this.setBorder(null);
        
        // Charger l'image comme dans ton exemple GrBalise
        File path = new File("src/ressources/balise.png");
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

        // Position initiale
        this.setLocation(balise.getX(), balise.getY());
      
    }

    @Override
    public void onBaliseMove(BaliseMoveEvent event) {
        Balise source = (Balise) event.getSource();
        int x = source.getX();
        int y = source.getY();
        
        this.setLocation(x, y);
        updateColor();
    }
    
    public Balise getBalise() {
        return balise;
    }
    
    /**
     * Met à jour la couleur selon l'état de la balise
     */
    private void updateColor() {
        switch (balise.getState()) {
            case Collect:
                // Jaune pendant la collecte
            	this.setOpaque(false);
                this.setBackground(new Color(0, 0, 0, 0));
                break;
            case Remontee:
                // Bleu clair pendant la remontée
                this.setBackground(new Color(135, 206, 250)); // Light Sky Blue
                break;
            case WaitSynchro:
                // Orange en attente de synchronisation
                this.setBackground(Color.ORANGE);
                break;
            case Synchro:
                // Vert pendant la synchronisation
                this.setBackground(Color.GREEN);
                break;
            case redescente:
                // Cyan pendant la redescente
                this.setBackground(Color.CYAN);
                break;
        }
    }

    @Override
    public void SynchroBalise(BaliseSynchroEvent event) {
        Balise source = (Balise) event.getSource();
        updateColor();
    }
    
}