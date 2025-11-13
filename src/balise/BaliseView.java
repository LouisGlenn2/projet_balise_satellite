package balise;

import java.awt.Color;
import src.nicellipse.component.NiRectangle;
import observer.*;

public class BaliseView extends NiRectangle implements BaliseListener {
    private Balise balise;
    
    public BaliseView(Balise balise) {
        this.balise = balise;
        this.setBackground(Color.YELLOW);
        this.setSize(20, 20);

        this.setLocation(balise.getX(), balise.getY());
    }

    @Override
    public void onBaliseMove(BaliseMoveEvent event) {
        Balise source = (Balise) event.getSource();
        int x = source.getX();
        int y = source.getY();
        
        this.setLocation(x, y);
        updateColor();
    }
    
    public Balise getBalise() {
        return balise;
    }
    private void updateColor() {
        switch (balise.getState()) {
            case Collect:
                this.setBackground(Color.YELLOW);
                break;
            case WaitSynchro:
                this.setBackground(Color.ORANGE);
                break;
            case Synchro:
                this.setBackground(Color.GREEN);
                break;
            case redescente:
                this.setBackground(Color.CYAN);
                break;
        }
    }

	@Override
	public void SynchroBalise(BaliseSynchroEvent event) {
		 Balise source = (Balise) event.getSource();
		 //Circle.setvisible(true);
		 updateColor();
		
	}
}