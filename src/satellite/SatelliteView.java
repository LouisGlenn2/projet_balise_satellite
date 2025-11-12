package satellite;

import java.awt.Color;

import observer.*;
import src.nicellipse.component.NiRectangle;


public class SatelliteView extends NiRectangle implements SatelliteListener{

	private Satellite mobi;
	
	public SatelliteView(Satellite mobi) {
		this.mobi = mobi;
		this.setBackground(Color.red);
	}
	
	@Override
	public void SatelliteMoveEvent(SatelliteMoveEvent event) {
		Satellite source = (Satellite) event.getSource();
		int x = source.getX();
		int y = source.getY();
		
		int pw = this.getParent().getWidth();
		int w = this.getWidth();
		int maxX = pw - w;
		
		x = ((x % maxX) + maxX) % maxX;
		
		this.setLocation(x, y);
	}
	@Override
	public void SattelliteSynchroEvent(SatelliteSynchroEvent event) {
		//Circle.setvisible(true);
		
	}
}
