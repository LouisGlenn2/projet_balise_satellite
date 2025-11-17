package strategy;

import balise.Balise;

public class HorizontalRightStrategy implements MoveStrategy {
    private int Fixeddepth;
    private int SpeedX;
    private int LeftLimit;
    private int RightLimit;
    
    public HorizontalRightStrategy(int Fixeddepth, int SpeedX, int LeftLimit, int RightLimit) {
        this.Fixeddepth = Math.max(400, Fixeddepth);
        this.SpeedX = Math.abs(SpeedX);
        this.LeftLimit = LeftLimit;
        this.RightLimit = RightLimit;
    }
    
    @Override
    public void move(Balise balise) {
        balise.setY(Fixeddepth);
        
        int newX = balise.getX() + (SpeedX * balise.getDirection());

        if (newX >= RightLimit || newX <= LeftLimit) {
            balise.setDirection(balise.getDirection() * -1);
            newX = balise.getX() + (SpeedX * balise.getDirection());
        }
        
        balise.setX(newX);
    }
}