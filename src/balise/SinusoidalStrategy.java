package balise;

public class SinusoidalStrategy implements MoveStrategy {
    private int vitesseX;       
    private int amplitude;     
    private int profondeurCentrale;
    private double frequency; 
    private int time;          
    private int limiteGauche;
    private int limiteDroite;

    public SinusoidalStrategy(int vitesseX, int amplitude, int profondeurCentrale, 
                             double frequency, int limiteGauche, int limiteDroite) {
        this.vitesseX = vitesseX;
        this.amplitude = amplitude;
        this.profondeurCentrale = Math.max(400 + amplitude, profondeurCentrale);
        this.frequency = frequency;
        this.time = 0;
        this.limiteGauche = limiteGauche;
        this.limiteDroite = limiteDroite;
    }
    
    @Override
    public void move(Balise balise) {
        time++;
        
        int newX = balise.getX() + (vitesseX * balise.getDirection());
        
        if (newX <= limiteGauche || newX >= limiteDroite) {
            balise.setDirection(balise.getDirection() * -1);
            newX = balise.getX() + (vitesseX * balise.getDirection());
        }
        
        int newY = (int) (profondeurCentrale + Math.sin(time * frequency) * amplitude);
        
        newY = Math.max(400, newY);
        
        balise.setX(newX);
        balise.setY(newY);
    }
}