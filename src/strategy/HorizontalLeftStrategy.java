package strategy;

import balise.Balise;

public class HorizontalLeftStrategy implements MoveStrategy {
    private int Fixeddepth;
    private int SpeedX;
    private int LeftLimit;
    private int RightLimit;
    
    public HorizontalLeftStrategy(int Fixeddepth, int SpeedX, int LeftLimit, int RightLimit) {
        this.Fixeddepth = Math.max(400, Fixeddepth);
        this.SpeedX = Math.abs(SpeedX);
        this.LeftLimit = LeftLimit;
        this.RightLimit = RightLimit;
    }
    
    @Override
    public void move(Balise balise) {
        balise.setY(Fixeddepth);
        
        int newX = balise.getX() + (SpeedX * balise.getDirection());
        
        if (newX <= LeftLimit || newX >= RightLimit) {
            balise.setDirection(balise.getDirection() * -1);
            newX = balise.getX() + (SpeedX * balise.getDirection());
        }
        
        balise.setX(newX);
    }
}
