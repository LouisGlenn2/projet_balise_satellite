package balise;

import java.util.Random;

import announcer.Announcer;
import strategy.*;
import observer.*;

public class Balise {
    private int x, y;
    private int direction;
    private MoveStrategy moveStrategy;
    private Announcer announcer;
    private BaliseState state;
    private int memory;
    private int maxMemory; 
    
    
    public Balise(int x, int y, int direction) {
        this.x = x;
        this.y = Math.max(400, y);
        this.direction = direction;
        this.announcer = new Announcer();
        this.state=BaliseState.Collect;
        this.memory=0;
        
        Random rand = new Random();
        this.maxMemory = 30 + rand.nextInt(71); 
    }

    public void setMoveStrategy(MoveStrategy strategy) {
        this.moveStrategy = strategy;
    }

    public void deplacer() {
        if (moveStrategy != null) {
            moveStrategy.move(this);
            announcer.announce(new BaliseMoveEvent(this));
        }
    }
    /* public void deplacer() {
        if (state == BaliseState.Collect && moveStrategy != null) {
            moveStrategy.move(this);
            announcer.announce(new BaliseMoveEvent(this));
            memory++;

            // Quand la mémoire atteint le max → passage en attente synchro
            if (memory >= maxMemory) {
                this.state = BaliseState.WaitSynchro;
                this.y = 400; // remonte en surface
                announcer.announce(new BaliseMoveEvent(this));
            }
        } else if (state == BaliseState.Redescente) {
            this.y = 600;
            this.state = BaliseState.Collect;
            memory = 0;

            // Nouveau seuil aléatoire pour la prochaine collecte
            Random rand = new Random();
            this.maxMemory = 30 + rand.nextInt(71);

            announcer.announce(new BaliseMoveEvent(this));
        }
    }

    public void trySynchronize(Satellite sat) {
        if (this.state == BaliseState.WaitSynchro 
            && sat.getState() == SatelliteState.Libre 
            && Math.abs(this.x - sat.getX()) < 10) {

            this.state = BaliseState.Synchro;
            announcer.announce(new BaliseSynchroEvent(this));

            sat.setState(SatelliteState.Synchro);
            sat.announceSynchro();

            endSynchronization(sat);
        }
    }

    private void endSynchronization(Satellite sat) {
        this.state = BaliseState.Redescente;
        announcer.announce(new BaliseMoveEvent(this));
        sat.setState(SatelliteState.Libre);
    }*/

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
}