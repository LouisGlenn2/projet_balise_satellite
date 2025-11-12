package strategy;

import balise.Balise;

public class ImmobilStrategy implements MoveStrategy {
    private int xFixe;
    private int yFixe;
    
    public ImmobilStrategy(int xFixe, int yFixe) {
        this.xFixe = xFixe;
        this.yFixe = Math.max(400, yFixe); 
    }
    
    @Override
    public void move(Balise balise) {
        balise.setX(xFixe);
        balise.setY(yFixe);
    }
    
}
