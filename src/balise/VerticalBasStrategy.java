package balise;

public class VerticalBasStrategy implements MoveStrategy {
    private int xFixe;
    private int vitesseY;
    private int profondeurMin;  
    private int profondeurMax;
    
    public VerticalBasStrategy(int xFixe, int vitesseY, int profondeurMin, int profondeurMax) {
        this.xFixe = xFixe;
        this.vitesseY = Math.abs(vitesseY);
        this.profondeurMin = Math.max(400, profondeurMin);
        this.profondeurMax = profondeurMax;
    }
    
    @Override
    public void move(Balise balise) {
        balise.setX(xFixe);
        
        int newY = balise.getY() + (vitesseY * balise.getDirection());

        if (newY >= profondeurMax || newY <= profondeurMin) {
            balise.setDirection(balise.getDirection() * -1);
            newY = balise.getY() + (vitesseY * balise.getDirection());
        }
        
        balise.setY(newY);
    }
}