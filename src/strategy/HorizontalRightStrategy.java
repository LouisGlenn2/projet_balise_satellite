package strategy;

import balise.Balise;

public class HorizontalRightStrategy implements MoveStrategy {
    private int fixeddepth;
    private int speedX;
    private int leftLimit;
    private int rightLimit;
    
    public HorizontalRightStrategy(int fixeddepth, int speedX, int leftLimit, int rightLimit) {
        this.fixeddepth = Math.max(400, fixeddepth);
        this.speedX = Math.abs(speedX);
        this.leftLimit = leftLimit;
        this.rightLimit = rightLimit;
    }
    
    @Override
    public void move(Balise balise) {
        balise.setY(fixeddepth);
        
        int newX = balise.getX() + (speedX * balise.getDirection());

        if (newX >= rightLimit || newX <= leftLimit) {
            balise.setDirection(balise.getDirection() * -1);
            newX = balise.getX() + (speedX * balise.getDirection());
        }
        
        balise.setX(newX);
    }
}