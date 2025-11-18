package satellite;

import announcer.Announcer;
import observer.SatelliteMoveEvent;
import observer.SatelliteSynchroEvent;

/**
 * Représente un satellite en orbite capable de se synchroniser avec des balises sous-marines.
 * 
 * <p>Un satellite peut être dans deux états définis par {@link SatelliteState} :</p>
 * <ul>
 *   <li><b>Libre</b> : Le satellite se déplace normalement et peut accepter une synchronisation</li>
 *   <li><b>Synchro</b> : Le satellite est en cours de synchronisation avec une balise et reste immobile</li>
 * </ul>
 * 
 * <p>Lorsqu'un satellite entre en état de synchronisation, il reste immobile pendant une durée
 * déterminée (par défaut 20000 cycles), puis redevient automatiquement libre.</p>
 * 
 * <p>Le satellite utilise le pattern Observer pour notifier les observateurs de ses déplacements
 * et de ses changements d'état.</p>
 * 
 */
public class Satellite {
    
  
    private int x, y;
    private int direction;
    Announcer announcer;
    private SatelliteState state;
    private String name;
    
    /**
     * Construit un nouveau satellite à la position et direction spécifiées.
     * 
     * <p>Le satellite est initialisé en état Libre avec une durée de synchronisation
     * par défaut de 20000 cycles.</p>
     * 
     * @param x La coordonnée horizontale initiale
     * @param y La coordonnée verticale initiale (altitude)
     * @param direction La direction initiale de déplacement (-1 = gauche, 1 = droite)
     */
    public Satellite(int x, int y, int direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.announcer = new Announcer();
        this.state = SatelliteState.Libre;
    }
    
    /**
     * Déplace le satellite ou gère la progression de la synchronisation.
     * 
     * <p>Comportement selon l'état :</p>
     * <ul>
     *   <li><b>État Libre</b> : Déplace le satellite horizontalement de {@code gap} pixels
     *       dans la direction courante, puis notifie les observateurs via un {@link SatelliteMoveEvent}</li>
     *   <li><b>État Synchro</b> : Incrémente le compteur de synchronisation. Lorsque le compteur
     *       atteint {@code synchroDuration}, le satellite redevient libre et le compteur est réinitialisé</li>
     * </ul>
     * 
     * @param gap Le nombre de pixels à parcourir (ignoré en état Synchro)
     */
    public void move(int gap) {
        if (state == SatelliteState.Libre) {
            this.x = this.x + (direction * gap);
            announcer.announce(new SatelliteMoveEvent(this));
        } 
    }
    
    /**
     * Annonce le début d'une synchronisation aux observateurs.
     * 
     * <p>Cette méthode doit être appelée lorsque le satellite commence une synchronisation
     * avec une balise. Elle notifie tous les observateurs enregistrés via un 
     * {@link SatelliteSynchroEvent}.</p>
     */
    public void announceSynchro() {
        announcer.announce(new SatelliteSynchroEvent(this));
    }
    
    /**
     * Enregistre un observateur pour les événements de déplacement et de synchronisation.
     * 
     * <p>L'observateur recevra les notifications pour les événements de type
     * {@link SatelliteMoveEvent} et {@link SatelliteSynchroEvent}.</p>
     * 
     * @param o L'objet observateur à enregistrer
     */
    public void registerMoveEvent(Object o) {
        this.announcer.register(o, SatelliteMoveEvent.class);
        this.announcer.register(o, SatelliteSynchroEvent.class);
    }
    
   
    public int getX() {
        return x;
    }
    
    public void setX(int x) {
        this.x = x;
    }
    
    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getDirection() {
        return direction;
    }
    
    public void setDirection(int direction) {
        this.direction = direction;
    }
    

    public SatelliteState getState() { 
        return state; 
    }

    public void setState(SatelliteState state) { 
        this.state = state;
    }

    public String getName() {
        return name;
    }
    
    public void setName(String n) {
        this.name = n;
    }
}