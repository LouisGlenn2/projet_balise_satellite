package balise;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import cercle.Circle;
import observer.*;
import src.nicellipse.component.NiEllipse;
import src.nicellipse.component.NiImage;
import src.nicellipse.component.NiRectangle;
import src.nicellipse.component.NiSpace;

/**
 * Vue graphique d'une balise sous-marine.
 * 
 * <p>Cette classe implémente le pattern Observer en tant qu'observateur d'une {@link Balise}.
 * Elle affiche une représentation visuelle de la balise et met à jour automatiquement
 * sa position et sa couleur en fonction des événements reçus.</p>
 * 
 * <p>Correspondance couleurs/états :</p>
 * <ul>
 *   <li><b>Transparent</b> : État Collect (collecte de données)</li>
 *   <li><b>Bleu clair</b> : État Remontee (remontée vers la surface)</li>
 *   <li><b>Orange</b> : État WaitSynchro (attente de synchronisation)</li>
 *   <li><b>Vert</b> : État Synchro (synchronisation en cours)</li>
 *   <li><b>Cyan</b> : État redescente (retour en profondeur)</li>
 * </ul>
 * 
 * <p>La vue superpose une image de balise (balise.png) sur un rectangle coloré
 * pour indiquer visuellement l'état actuel.</p>
 * 
 */
public class BaliseView extends NiRectangle implements BaliseListener {
    
    private static final long serialVersionUID = 1L;
    private Balise balise;
    private NiImage imageLayer;
    private Circle signalSynchro;
    private NiEllipse signalView;
    
    /**
     * Construit une nouvelle vue pour la balise spécifiée.
     * 
     * <p>Charge l'image de la balise depuis le fichier "src/ressources/balise.png",
     * dimensionne le composant selon la taille de l'image, et positionne la vue
     * aux coordonnées initiales de la balise.</p>
     * 
     * @param balise La balise à représenter visuellement
     * @throws IOException Si l'image de la balise ne peut pas être chargée
     */
    public BaliseView(Balise balise) throws IOException {
        this.balise = balise;
        this.setBorder(null);
        
        File path = new File("src/ressources/balise.png");
        BufferedImage rawImage = null;
        try {
            rawImage = ImageIO.read(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    
        if (rawImage != null) {
            imageLayer = new NiImage(rawImage);
            imageLayer.setLocation(0, 0);
            this.add(imageLayer);
            this.setDimension(new Dimension(rawImage.getWidth(), rawImage.getHeight()));
        }
        this.setLocation(balise.getX(), balise.getY());
        
        // Initialiser les cercles (mais ne pas les afficher)
        int baliseWidth = rawImage != null ? rawImage.getWidth() : 110;
        int baliseHeight = rawImage != null ? rawImage.getHeight() : 58;
        int signalSize = 340;
        
        // Calculer la position des cercles pour qu'ils soient centrés sur la balise
        int circleOffsetX = -(signalSize - baliseWidth) / 2;
        int circleOffsetY = -(signalSize - baliseHeight) / 2;
        
        signalSynchro = new Circle(circleOffsetX, circleOffsetY);
        signalSynchro.setVisible(false);
    }
    
    /**
     * Appelée lorsque la balise se déplace.
     * 
     * <p>Met à jour la position graphique de la vue pour correspondre à la nouvelle
     * position de la balise, puis actualise la couleur d'arrière-plan selon le nouvel état.</p>
     * 
     * @param event L'événement contenant les informations sur le déplacement
     */
    @Override
    public void onBaliseMove(BaliseMoveEvent event) {
        Balise source = (Balise) event.getSource();
        int x = source.getX();
        int y = source.getY();
        
        this.setLocation(x, y);
        
        // Mettre à jour la position des cercles si ils sont affichés
        if (signalView != null && this.getParent() != null) {
            int absoluteX = x + signalSynchro.getX();
            int absoluteY = y + signalSynchro.getY();
            signalView.setLocation(absoluteX, absoluteY);
        }
        
        updateColor();
    }
    
    public Balise getBalise() {
        return balise;
    }
    
    /**
     * Met à jour la couleur d'arrière-plan selon l'état actuel de la balise.
     * 
     * <p>Code couleur :</p>
     * <ul>
     *   <li>Collect : Transparent (pas de couleur de fond visible)</li>
     *   <li>Remontee : Bleu ciel clair (Light Sky Blue - RGB 135, 206, 250)</li>
     *   <li>WaitSynchro : Orange</li>
     *   <li>Synchro : Vert</li>
     *   <li>redescente : Cyan</li>
     * </ul>
     * 
     * <p>Cette méthode est appelée automatiquement après chaque déplacement
     * ou événement de synchronisation.</p>
     */
    private void updateColor() {
        switch (balise.getState()) {
            case Collect:
                this.setOpaque(false);
                this.setBackground(new Color(0, 0, 0, 0));
                hideSignal();
                break;
            case Remontee:
                this.setBackground(new Color(135, 206, 250)); 
                hideSignal();
                break;
            case WaitSynchro:
                this.setBackground(Color.ORANGE);
                hideSignal();
                break;
            case Synchro:
                this.setBackground(Color.GREEN);
                showSignal();
                break;
            case redescente:
                this.setBackground(Color.CYAN);
                hideSignal();
                break;
        }
    }
    
    /**
     * Appelée lorsqu'une synchronisation de la balise est déclenchée.
     * 
     * <p>Met à jour la couleur d'arrière-plan pour refléter l'état de synchronisation.
     * Cette méthode est invoquée en réponse à un {@link BaliseSynchroEvent}.</p>
     * 
     * @param event L'événement de synchronisation contenant la balise source
     */
    @Override
    public void SynchroBalise(BaliseSynchroEvent event) {
        updateColor();
    }
    
    /**
     * Affiche les cercles de signal autour de la balise.
     */
    private void showSignal() {
        if (signalView == null && this.getParent() != null) {
        	signalSynchro.setVisible(true);
            signalView = signalSynchro.createSignal();
            
            if (signalView != null) {
              
                int absoluteX = this.getX() + signalSynchro.getX();
                int absoluteY = this.getY() + signalSynchro.getY();
                signalView.setLocation(absoluteX, absoluteY);
                NiSpace space = (NiSpace) this.getParent();
                space.add(signalView, 0); 
                space.repaint();
            }
        }
    }
    
    /**
     * Masque les cercles de signal.
     */
    private void hideSignal() {
        if (signalView != null && this.getParent() != null) {
            NiSpace space = (NiSpace) this.getParent();
            space.remove(signalView);
            space.repaint();
            signalView = null;
            signalSynchro.setVisible(false);
        }
    }
}