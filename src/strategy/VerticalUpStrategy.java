package strategy;

import balise.Balise;

public class VerticalUpStrategy implements MoveStrategy {
    private int xFixe;
    private int speedY;
    private int depthMin;  
    private int depthMax;
    
    public VerticalUpStrategy(int xFixe, int speedY, int depthMin, int depthMax) {
        this.xFixe = xFixe;
        this.speedY = Math.abs(speedY);
        this.depthMin = Math.max(400, depthMin);
        this.depthMax = depthMax;
    }
    
    @Override
    public void move(Balise balise) {
        balise.setX(xFixe);
        
        int newY = balise.getY() - (speedY * balise.getDirection());
        
        if (newY <= depthMin || newY >= depthMax) {
            balise.setDirection(balise.getDirection() * -1);
            newY = balise.getY() - (speedY * balise.getDirection());
        }
        
        balise.setY(newY);
    }
}