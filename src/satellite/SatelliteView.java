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

/**
 * Vue graphique d'un satellite en orbite.
 * 
 * <p>Cette classe implémente le pattern Observer en tant qu'observateur d'un {@link Satellite}.
 * Elle affiche une représentation visuelle du satellite et met à jour automatiquement
 * sa position et son apparence en fonction des événements reçus.</p>
 * 
 * <p>Correspondance couleurs/états :</p>
 * <ul>
 *   <li><b>Blanc transparent</b> : État Libre (déplacement normal)</li>
 *   <li><b>Bleu</b> : État Synchro (synchronisation en cours avec une balise)</li>
 * </ul>
 * 
 * <p>La vue implémente un système de défilement horizontal cyclique : lorsque le satellite
 * atteint le bord droit de l'écran, il réapparaît sur le bord gauche (effet "wrap-around").</p>
 * 
 * <p>La vue superpose une image de satellite (satellite.png) sur un rectangle coloré
 * pour indiquer visuellement l'état actuel.</p>
 * 
 * @author [Votre nom]
 * @version 1.0
 * @see Satellite
 * @see SatelliteListener
 * @see SatelliteState
 */
public class SatelliteView extends NiRectangle implements SatelliteListener {

    private static final long serialVersionUID = 1L;
    private Satellite mobi;
    private NiImage imageLayer;

    /**
     * Construit une nouvelle vue pour le satellite spécifié.
     * 
     * <p>Charge l'image du satellite depuis le fichier "src/ressources/satellite.png",
     * dimensionne le composant selon la taille de l'image, et superpose l'image
     * sur le rectangle de base.</p>
     * 
     * @param mobi Le satellite à représenter visuellement
     */
    public SatelliteView(Satellite mobi) {
        this.mobi = mobi;
        File path = new File("src/ressources/satellite.png");
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
    }

    /**
     * Appelée lorsque le satellite se déplace.
     * 
     * <p>Met à jour la position graphique du satellite avec gestion du défilement cyclique.
     * Lorsque le satellite dépasse le bord droit de l'écran, il réapparaît à gauche,
     * créant un effet d'orbite continue.</p>
     * 
     * <p>Le calcul de position utilise l'opération modulo pour créer cet effet :
     * {@code x = ((x % maxX) + maxX) % maxX}, ce qui gère correctement les valeurs
     * négatives et positives.</p>
     * 
     * <p>Après repositionnement, met à jour la couleur d'arrière-plan selon l'état actuel.</p>
     * 
     * @param event L'événement contenant les informations sur le déplacement du satellite
     */
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
    
    /**
     * Appelée lorsqu'une synchronisation du satellite est déclenchée.
     * 
     * <p>Met à jour l'apparence visuelle du satellite pour refléter l'état de synchronisation.
     * Cette méthode est invoquée en réponse à un {@link SatelliteSynchroEvent}.</p>
     * 
     * @param event L'événement de synchronisation contenant le satellite source
     */
    @Override
    public void SattelliteSynchroEvent(SatelliteSynchroEvent event) {
        //Circle.setvisible(true);
        updateColor();
    }

    /**
     * Met à jour la couleur d'arrière-plan selon l'état actuel du satellite.
     * 
     * <p>Code couleur :</p>
     * <ul>
     *   <li><b>Libre</b> : Fond blanc transparent sans bordure (seule l'image est visible)</li>
     *   <li><b>Synchro</b> : Fond bleu pour indiquer visuellement la synchronisation en cours</li>
     * </ul>
     * 
     * <p>Cette méthode est appelée automatiquement après chaque déplacement
     * ou événement de synchronisation.</p>
     */
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