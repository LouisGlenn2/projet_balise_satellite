package balise;

public class HorizontalGaucheStrategy implements MoveStrategy {
    private int profondeurFixe;
    private int vitesseX;
    private int limiteGauche;
    private int limiteDroite;
    
    public HorizontalGaucheStrategy(int profondeurFixe, int vitesseX, int limiteGauche, int limiteDroite) {
        this.profondeurFixe = Math.max(400, profondeurFixe);
        this.vitesseX = Math.abs(vitesseX);
        this.limiteGauche = limiteGauche;
        this.limiteDroite = limiteDroite;
    }
    
    @Override
    public void move(Balise balise) {
        balise.setY(profondeurFixe);
        
        int newX = balise.getX() + (vitesseX * balise.getDirection());
        
        if (newX <= limiteGauche || newX >= limiteDroite) {
            balise.setDirection(balise.getDirection() * -1);
            newX = balise.getX() + (vitesseX * balise.getDirection());
        }
        
        balise.setX(newX);
    }
}