package strategy;

import balise.Balise;

public class SinusoidalStrategy implements MoveStrategy {
    private int SpeedX;       
    private int amplitude;     
    private int Centraldepth;
    private double frequency; 
    private int time;          
    private int LeftLimit;
    private int RightLimit;

    public SinusoidalStrategy(int SpeedX, int amplitude, int Centraldepth, 
                             double frequency, int LeftLimit, int RightLimit) {
        this.SpeedX = SpeedX;
        this.amplitude = amplitude;
        this.Centraldepth = Math.max(400 + amplitude, Centraldepth);
        this.frequency = frequency;
        this.time = 0;
        this.LeftLimit = LeftLimit;
        this.RightLimit = RightLimit;
    }
    
    @Override
    public void move(Balise balise) {
        time++;
        
        int newX = balise.getX() + (SpeedX * balise.getDirection());
        
        if (newX <= LeftLimit || newX >= RightLimit) {
            balise.setDirection(balise.getDirection() * -1);
            newX = balise.getX() + (SpeedX * balise.getDirection());
        }
        
        int newY = (int) (Centraldepth + Math.sin(time * frequency) * amplitude);
        
        newY = Math.max(400, newY);
        
        balise.setX(newX);
        balise.setY(newY);
    }
}