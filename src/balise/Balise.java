package balise;

import announcer.Announcer;
import java.util.Random;

import javax.swing.Timer;

import observer.*;
import satellite.Satellite;
import satellite.SatelliteState;
import strategy.*;

/**
 * Représente une balise sous-marine autonome capable de collecter des données,
 * remonter en surface pour synchroniser avec un satellite, puis redescendre.
 * 
 * <p>La balise suit un cycle de vie en plusieurs phases :</p>
 * <ul>
 *   <li><b>Collect</b> : Collecte de données en profondeur</li>
 *   <li><b>Remontee</b> : Remontée vers la surface lorsque la mémoire est pleine</li>
 *   <li><b>WaitSynchro</b> : Attente d'un satellite en surface</li>
 *   <li><b>Synchro</b> : Synchronisation des données avec un satellite</li>
 *   <li><b>Redescente</b> : Retour à la profondeur d'origine pour reprendre la collecte</li>
 * </ul>
 * 
 * <p>La balise utilise le pattern Strategy pour définir son comportement de déplacement
 * et le pattern Observer pour notifier les changements d'état.</p>
 * 
 */
public class Balise {
    private int x, y;
    private int direction, profondeurOrigine;
    private MoveStrategy moveStrategy;
    private MoveStrategy savedStrategy;
    private Announcer announcer;
    private BaliseState state;
    private int memory;
    private int maxMemory;
    private String name;
    
    /**
     * Construit une nouvelle balise à la position spécifiée.
     * 
     * @param x La coordonnée horizontale initiale
     * @param y La coordonnée verticale initiale (profondeur). 
     *          Sera automatiquement ajustée à un minimum de 400 pour éviter la surface.
     * @param direction La direction initiale de déplacement
     */
    public Balise(int x, int y, int direction) {
        this.x = x;
        this.y = Math.max(400, y);
        this.direction = direction;
        this.announcer = new Announcer();
        this.state = BaliseState.Collect;
        this.memory = 0;
        this.profondeurOrigine = this.y;
       
        Random rand = new Random();
        this.maxMemory = 50 + rand.nextInt(171); 
    }

    /**
     * Définit la stratégie de déplacement de la balise.
     * La stratégie est également sauvegardée pour être restaurée après synchronisation.
     * 
     * @param strategy La stratégie de mouvement à appliquer
     */
    public void setMoveStrategy(MoveStrategy strategy) {
        this.moveStrategy = strategy;
        this.savedStrategy = strategy;
    }

    /**
     * Effectue un déplacement de la balise selon son état actuel et sa stratégie.
     * 
     * <p>Comportement selon l'état :</p>
     * <ul>
     *   <li><b>Collect</b> : Se déplace selon la stratégie, incrémente la mémoire, 
     *       déclenche la remontée si la mémoire est pleine</li>
     *   <li><b>Remontee</b> : Monte vers la surface, passe en attente de synchro à l'arrivée</li>
     *   <li><b>WaitSynchro</b> : Reste immobile en surface</li>
     *   <li><b>Synchro</b> : Reste immobile pendant la synchronisation</li>
     *   <li><b>Redescente</b> : Redescend vers la profondeur d'origine</li>
     * </ul>
     * 
     * <p>Notifie les observateurs à chaque déplacement via un {@link BaliseMoveEvent}.</p>
     */
    public void move() {
        if (state == BaliseState.Collect && moveStrategy != null) {
            moveStrategy.move(this);
            announcer.announce(new BaliseMoveEvent(this));
            memory++;

            if (memory >= maxMemory) {
                startRemontee();
                System.out.println("Collecte Fini montée");
            }
        } 
        else if (state == BaliseState.Remontee) {
            moveStrategy.move(this);
            announcer.announce(new BaliseMoveEvent(this));

            if (this.y <= 405) {
            	ArrivalSurface();
            }
        }
        else if (state == BaliseState.WaitSynchro) {
            if (moveStrategy != null) {
                moveStrategy.move(this);
                announcer.announce(new BaliseMoveEvent(this));
            }
        }
        else if (state == BaliseState.Synchro) {
            if (moveStrategy != null) {
                moveStrategy.move(this);
                announcer.announce(new BaliseMoveEvent(this));
            }
        }
        else if (state == BaliseState.redescente) {
            System.out.println("Synchro Fini redescente");
            GoBackDown();
        }
    }

    /**
     * Démarre la phase de remontée vers la surface.
     * Change l'état en Remontee et applique une stratégie de déplacement vertical ascendant.
     */
    private void startRemontee() {
        this.state = BaliseState.Remontee;       
        this.moveStrategy = new VerticalUpStrategy(this.x, 3, 400, this.y);
        this.direction = 1; 
        
        announcer.announce(new BaliseMoveEvent(this));
    }

    /**
     * Gère l'arrivée de la balise en surface.
     * Fixe la position à la surface (y=400), passe en état d'attente de synchronisation
     * et applique une stratégie immobile.
     */
    private void ArrivalSurface() {
        this.y = 400; 
        this.state = BaliseState.WaitSynchro;
        
        this.moveStrategy = new ImmobilStrategy(this.x, 400);
        
        announcer.announce(new BaliseMoveEvent(this));
    }

    /**
     * Tente une synchronisation avec un satellite donné.
     * 
     * <p>La synchronisation réussit si :</p>
     * <ul>
     *   <li>La balise est en état WaitSynchro</li>
     *   <li>Le satellite est libre (état Libre)</li>
     *   <li>La distance horizontale entre la balise et le satellite est inférieure à 60 unités</li>
     * </ul>
     * 
     * <p>En cas de succès, change l'état de la balise en Synchro, notifie les observateurs,
     * met le satellite en état Synchro, puis termine automatiquement la synchronisation.</p>
     * 
     * @param sat Le satellite avec lequel tenter la synchronisation
     */
    public void trySynchronize(Satellite sat) {
        if (this.state == BaliseState.WaitSynchro && sat.getState() == SatelliteState.Libre && Math.abs(this.x - sat.getX()) < 60) {
            
            System.out.println("Synchro " + this.getName() + " <-> " + sat.getName());
            
            this.state = BaliseState.Synchro;
            announcer.announce(new BaliseSynchroEvent(this));

            sat.setState(SatelliteState.Synchro);
            sat.announceSynchro();
            
           Timer timer = new Timer(1000, e -> {
                endSynchronization(sat);
            });
            timer.setRepeats(false);
            timer.start();
            
            //endSynchronization(sat);
        }
    }
    
    /**
     * Termine la synchronisation et prépare la balise pour la redescente.
     * Change l'état de la balise en Redescente et libère le satellite.
     * 
     * @param sat Le satellite à libérer
     */
    private void endSynchronization(Satellite sat) {
        this.state = BaliseState.redescente;
        sat.setState(SatelliteState.Libre);
    }

    /**
     * Effectue la redescente de la balise vers sa profondeur d'origine.
     * 
     * <p>Applique une stratégie de descente verticale jusqu'à atteindre la profondeur d'origine + 10.
     * Une fois la profondeur atteinte, restaure l'état de collecte, réinitialise la mémoire,
     * restaure la stratégie de mouvement d'origine et génère un nouveau seuil de mémoire aléatoire
     * (entre 130 et 200).</p>
     */
    private void GoBackDown() {
        if (savedStrategy != null) {
            this.moveStrategy = new VerticalDownStrategy(this.x, 3, 1, profondeurOrigine + 10);
            moveStrategy.move(this);
            announcer.announce(new BaliseMoveEvent(this));
        }
        
        if (this.y >= this.profondeurOrigine) {
            this.state = BaliseState.Collect;
            this.memory = 0;
            System.out.println(this.y); 
            this.moveStrategy = this.savedStrategy;

            Random rand = new Random();
            this.maxMemory = 130 + rand.nextInt(71);

            announcer.announce(new BaliseMoveEvent(this));
        }
    }

    /**
     * Enregistre un observateur pour les événements de déplacement et de synchronisation.
     * 
     * @param observer L'objet observateur à enregistrer pour les événements
     *                 {@link BaliseMoveEvent} et {@link BaliseSynchroEvent}
     */
    public void registerMoveEvent(Object observer) {
        this.announcer.register(observer, BaliseMoveEvent.class);
        this.announcer.register(observer, BaliseSynchroEvent.class);
    }
    
    public void setLocation(int x, int y) {
        this.x = x;
        this.y = Math.max(400, y); 
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
        this.y = Math.max(400, y);
    }
    
    public int getDirection() {
        return direction;
    }
    
    public void setDirection(int direction) {
        this.direction = direction;
    }
    
    public MoveStrategy getMoveStrategy() {
        return moveStrategy;
    }
    
    public BaliseState getState() {
        return state; 
    }
    
    public int getMemory() { 
        return memory;
    }
    
    public int getMaxMemory() { 
        return maxMemory; 
    }

    public void setName(String n) {
        this.name = n; 
    }
    
    public String getName() {
        return name;
    }
}