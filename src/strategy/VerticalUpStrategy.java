package strategy;

import balise.Balise;

public class VerticalUpStrategy implements MoveStrategy {
    private int xFixe;
    private int vitesseY;
    private int profondeurMin;
    private int profondeurMax;
    
    public VerticalUpStrategy(int xFixe, int vitesseY, int profondeurMin, int profondeurMax) {
        this.xFixe = xFixe;
        this.vitesseY = Math.abs(vitesseY);
        this.profondeurMin = Math.max(400, profondeurMin);
        this.profondeurMax = profondeurMax;
    }
    
    @Override
    public void move(Balise balise) {
        balise.setX(xFixe);
        
        int newY = balise.getY() - (vitesseY * balise.getDirection());
        
        if (newY <= profondeurMin || newY >= profondeurMax) {
            balise.setDirection(balise.getDirection() * -1);
            newY = balise.getY() - (vitesseY * balise.getDirection());
        }
        
        balise.setY(newY);
    }
}