package balise;

import announcer.Announcer;
import strategy.*;
import observer.*;

public class Balise {
    private int x, y;
    private int direction;
    private MoveStrategy moveStrategy;
    private Announcer announcer;
    
    public Balise(int x, int y, int direction) {
        this.x = x;
        this.y = Math.max(400, y);
        this.direction = direction;
        this.announcer = new Announcer();
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

    public void registerMoveEvent(Object observer) {
        this.announcer.register(observer, BaliseMoveEvent.class);
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
}