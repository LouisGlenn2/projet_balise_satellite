package strategy;

import balise.Balise;

public class VerticalUpStrategy implements MoveStrategy {
    private int xFixe;
    private int SpeedY;
    private int depthMin;  
    private int depthMax;
    
    public VerticalUpStrategy(int xFixe, int SpeedY, int depthMin, int depthMax) {
        this.xFixe = xFixe;
        this.SpeedY = Math.abs(SpeedY);
        this.depthMin = Math.max(400, depthMin);
        this.depthMax = depthMax;
    }
    
    @Override
    public void move(Balise balise) {
        balise.setX(xFixe);
        
        int newY = balise.getY() - (SpeedY * balise.getDirection());
        
        if (newY <= depthMin || newY >= depthMax) {
            balise.setDirection(balise.getDirection() * -1);
            newY = balise.getY() - (SpeedY * balise.getDirection());
        }
        
        balise.setY(newY);
    }
}