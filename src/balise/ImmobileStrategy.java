package balise;

public class ImmobileStrategy implements MoveStrategy {
    private int xFixe;
    private int yFixe;
    
    public ImmobileStrategy(int xFixe, int yFixe) {
        this.xFixe = xFixe;
        this.yFixe = Math.max(400, yFixe); 
    }
    
    @Override
    public void move(Balise balise) {
        balise.setX(xFixe);
        balise.setY(yFixe);
    }
    
}
