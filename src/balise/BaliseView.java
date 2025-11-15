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
    
    /**
     * Met à jour la couleur selon l'état de la balise
     */
    private void updateColor() {
        switch (balise.getState()) {
            case Collect:
                // Jaune pendant la collecte
                this.setBackground(Color.YELLOW);
                break;
            case Remontee:
                // Bleu clair pendant la remontée
                this.setBackground(new Color(135, 206, 250)); // Light Sky Blue
                break;
            case WaitSynchro:
                // Orange en attente de synchronisation
                this.setBackground(Color.ORANGE);
                break;
            case Synchro:
                // Vert pendant la synchronisation
                this.setBackground(Color.GREEN);
                break;
            case redescente:
                // Cyan pendant la redescente
                this.setBackground(Color.CYAN);
                break;
        }
    }

    @Override
    public void SynchroBalise(BaliseSynchroEvent event) {
        Balise source = (Balise) event.getSource();
        updateColor();
    }
}