package simulation;

import java.awt.Color;
import java.awt.Dimension;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import balise.*;
import strategy.*;
import satellite.*;

import src.nicellipse.component.NiRectangle;
import src.nicellipse.component.NiSpace;

public class Main {
    
    public static void main(String[] args) throws InterruptedException, IOException {
        int LARGEUR_FENETRE = 800;
        
        NiSpace space = new NiSpace("Satellites et Balises", new Dimension(LARGEUR_FENETRE, 800));
        space.setBackground(new Color(135, 206, 250));
        NiRectangle mer = new NiRectangle();
        mer.setSize(LARGEUR_FENETRE, 400);
        mer.setBackground(Color.BLUE);
        mer.setLocation(0, 400);
        
        // ===== BALISES =====
        
        // Balise 1 : Déplacement horizontal (démarre vers la droite, direction=1)
        Balise balise1 = new Balise(50, 500, 1);
        BaliseView baliseView1 = new BaliseView(balise1);
        balise1.registerMoveEvent(baliseView1);
        balise1.setMoveStrategy(new HorizontalRightStrategy(balise1.getY(), 3, 0, LARGEUR_FENETRE-60));
        balise1.setName("Atalante");

        // Balise 2 : Déplacement vertical (démarre en montant, direction=1)
        Balise balise2 = new Balise(200, 550, 1);
        BaliseView baliseView2 = new BaliseView(balise2);
        balise2.registerMoveEvent(baliseView2);
        balise2.setMoveStrategy(new VerticalUpStrategy(200, 3, 400, 700));
        balise2.setName("Le Terrible");

        // Balise 3 : Déplacement sinusoïdal
        Balise balise3 = new Balise(50, 550, 1);
        BaliseView baliseView3 = new BaliseView(balise3);
        balise3.registerMoveEvent(baliseView3);
        balise3.setMoveStrategy(new SinusoidalStrategy(2, 80, balise3.getY(), 0.05, 0, LARGEUR_FENETRE-60));
        balise3.setName("Pourquoi pas ?");

        // Balise 4 : Immobile 
        Balise balise4 = new Balise(450, 600, 1);
        BaliseView baliseView4 = new BaliseView(balise4);
        balise4.registerMoveEvent(baliseView4);
        balise4.setMoveStrategy(new SinusoidalStrategy(2, 20, balise4.getY(), 0.45, 0, LARGEUR_FENETRE-60));
        balise4.setName("La Bretagne");

        // Balise 5 : Déplacement horizontal (démarre vers la gauche, direction=-1)
        Balise balise5 = new Balise(550, 650, -1);
        BaliseView baliseView5 = new BaliseView(balise5);
        balise5.registerMoveEvent(baliseView5);
        balise5.setMoveStrategy(new HorizontalLeftStrategy(balise5.getY(), 2, 0, LARGEUR_FENETRE-60));
        balise5.setName("Tonerre");

        // ===== SATELLITES =====
        
        Satellite satellite1 = new Satellite(20, 20, 1);
        SatelliteView satelliteView1 = new SatelliteView(satellite1);
        satellite1.registerMoveEvent(satelliteView1);
        satellite1.setName("Apollo");

        Satellite satellite2 = new Satellite(20, 60, -1);
        SatelliteView satelliteView2 = new SatelliteView(satellite2);
        satellite2.registerMoveEvent(satelliteView2);
        satellite2.setName("Saturne");

        space.add(satelliteView1);
        space.add(satelliteView2);
        
        space.add(baliseView1);
        space.add(baliseView2);
        space.add(baliseView3);
        space.add(baliseView4);
        space.add(baliseView5);
        
        space.add(mer);
        
        space.openInWindow();
        
        List<Balise> balises = Arrays.asList(balise1, balise2, balise3, balise4, balise5);
        List<Satellite> satellites = Arrays.asList(satellite1, satellite2);

        while (true) {
            satellite1.move(3);
            satellite2.move(2);

            for (Balise b : balises) {
                b.move();
                for (Satellite s : satellites) {
                    b.trySynchronize(s);
                }
            }

            Thread.sleep(20);
        }
    }
}