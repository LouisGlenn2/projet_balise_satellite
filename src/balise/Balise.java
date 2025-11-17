package balise;

import announcer.Announcer;
import java.util.Random;
import observer.*;
import satellite.Satellite;
import satellite.SatelliteState;
import strategy.*;

public class Balise {
    private int x, y;
    private int direction;
    private MoveStrategy moveStrategy;
    private MoveStrategy savedStrategy; // Pour sauvegarder la stratégie de collecte
    private Announcer announcer;
    private BaliseState state;
    private int memory;
    private int maxMemory; 
    private String nom;
    
    public Balise(int x, int y, int direction) {
        this.x = x;
        this.y = Math.max(400, y);
        this.direction = direction;
        this.announcer = new Announcer();
        this.state = BaliseState.Collect;
        this.memory = 0;
       
        Random rand = new Random();
        this.maxMemory = 530 + rand.nextInt(71); 
    }

    public void setMoveStrategy(MoveStrategy strategy) {
        this.moveStrategy = strategy;
        this.savedStrategy=strategy;
    }

    public void deplacer() {
        if (state == BaliseState.Collect && moveStrategy != null) {
            // Phase de collecte normale
            moveStrategy.move(this);
            announcer.announce(new BaliseMoveEvent(this));
            memory++;

            // Quand la mémoire atteint le max → début de la remontée
            if (memory >= maxMemory) {
                startRemontee();
                System.out.println("Collecte Fini montée");
            }
        } 
        else if (state == BaliseState.Remontee) {
            // Phase de remontée vers la surface
            moveStrategy.move(this);
            announcer.announce(new BaliseMoveEvent(this));

            // Vérifier si arrivée en surface
            if (this.y <= 405) {
                arriveeSurface();
            }
        }
        else if (state == BaliseState.WaitSynchro) {
            // En attente immobile - appliquer quand même la stratégie pour rester immobile
            if (moveStrategy != null) {
                moveStrategy.move(this);
                announcer.announce(new BaliseMoveEvent(this));
            }
        }
        else if (state == BaliseState.Synchro) {
            // En cours de synchro - rester immobile
            if (moveStrategy != null) {
                moveStrategy.move(this);
                announcer.announce(new BaliseMoveEvent(this));
            }
        }
        else if (state == BaliseState.redescente) {
            // Phase de redescente après synchronisation
            System.out.println("Synchro Fini redescente");
            redescendre();
        }
    }

    /**
     * Démarre la phase de remontée
     */
    private void startRemontee() {
        this.state = BaliseState.Remontee;       
        this.moveStrategy = new VerticalUpStrategy(this.x,3,400,this.y );
        this.direction = 1; 
        
        announcer.announce(new BaliseMoveEvent(this));
    }

    /**
     * Appelée quand la balise arrive en surface
     */
    private void arriveeSurface() {
        this.y = 400; 
        this.state = BaliseState.WaitSynchro;
        
        this.moveStrategy = new ImmobilStrategy(this.x, 400);
        
        announcer.announce(new BaliseMoveEvent(this));
    }

    /**
     * Tente une synchronisation avec un satellite
     */
    public void trySynchronize(Satellite sat) {
        if (this.state == BaliseState.WaitSynchro && sat.getState() == SatelliteState.Libre && Math.abs(this.x - sat.getX()) < 60) {
            
            System.out.println("Synchro " + this.getNom() + " <-> " + sat.getNom());
            
            this.state = BaliseState.Synchro;
            announcer.announce(new BaliseSynchroEvent(this));

            sat.setState(SatelliteState.Synchro);
            sat.announceSynchro();
            
            endSynchronization(sat);
        }
    }
    
    /**
     * Termine la synchronisation et prépare la redescente
     */
    private void endSynchronization(Satellite sat) {
        this.state = BaliseState.redescente;
        sat.setState(SatelliteState.Libre);
    }

    /**
     * Redescend et reprend la collecte
     */
    private void redescendre() {
        // Restaurer la stratégie de collecte sauvegardée
        if (savedStrategy != null) {
            Random rand = new Random();
            int profondeur = 450 + rand.nextInt(351);
            this.moveStrategy = new VerticalDownStrategy(this.x, 3, 1, profondeur);
            announcer.announce(new BaliseMoveEvent(this));
        }
        
        // Réinitialiser la profondeur (selon la stratégie)
        // On force un déplacement pour repositionner correctement
        if (moveStrategy != null) {
            moveStrategy.move(this);
        }
        
        // Reprendre la collecte
        this.state = BaliseState.Collect;
        this.memory = 0;
        System.out.println(this.y);
        //this.moveStrategy= this.savedStrategy;

        // Nouveau seuil aléatoire pour la prochaine collecte
        Random rand = new Random();
        this.maxMemory = 130 + rand.nextInt(71);

        announcer.announce(new BaliseMoveEvent(this));
    }

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

    public void setNom(String n){
        this.nom = n; 
    }

    public String getNom(){
        return nom;
    }
}