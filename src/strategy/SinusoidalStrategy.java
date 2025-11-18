package strategy;

import balise.Balise;

public class SinusoidalStrategy implements MoveStrategy {
    private int speedX;       
    private int amplitude;     
    private int centralDepth;
    private double frequency; 
    private int time;          
    private int leftLimit;
    private int rightLimit;

    public SinusoidalStrategy(int speedX, int amplitude, int centralDepth, 
                             double frequency, int leftLimit, int rightLimit) {
        this.speedX = speedX;
        this.amplitude = amplitude;
        this.centralDepth = Math.max(400 + amplitude, centralDepth);
        this.frequency = frequency;
        this.time = 0;
        this.leftLimit = leftLimit;
        this.rightLimit = rightLimit;
    }
    
    @Override
    public void move(Balise balise) {
        time++;
        
        int newX = balise.getX() + (speedX * balise.getDirection());
        
        if (newX <= leftLimit || newX >= rightLimit) {
            balise.setDirection(balise.getDirection() * -1);
            newX = balise.getX() + (speedX * balise.getDirection());
        }
        
        int newY = (int) (centralDepth + Math.sin(time * frequency) * amplitude);
        
        newY = Math.max(400, newY);
        
        balise.setX(newX);
        balise.setY(newY);
    }
}