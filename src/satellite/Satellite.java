package satellite;

import announcer.Announcer;
import observer.SatelliteMoveEvent;
import observer.SatelliteSynchroEvent;

public class Satellite {
	private int x,y;
	private int direction;
	Announcer announcer;
	private SatelliteState state;
    private int synchroDuration;   
    private int synchroCounter;    
	
	public Satellite (int x, int y, int direction) {
		this.x = x;
		this.y = y;
		this.direction = direction;
		this.announcer = new Announcer();
		this.state=SatelliteState.Libre;
		this.synchroDuration = 20;
	    this.synchroCounter = 0;
	}
	
	/*public void move(int gap) {
		this.x = this.x + (direction*gap);
		announcer.announce(new SatelliteMoveEvent(this));
	}*/
	 // --- Déplacement ---
    public void move(int gap) {
        if (state == SatelliteState.Libre) {
            this.x = this.x + (direction * gap);
            announcer.announce(new SatelliteMoveEvent(this));
        } else if (state == SatelliteState.Synchro) {
            // pendant synchro, le satellite ne bouge pas
            synchroCounter++;
            if (synchroCounter >= synchroDuration) {
                // fin synchro → retour à Libre
                this.state = SatelliteState.Libre;
                synchroCounter = 0;
            }
        }
    }
	
    public void announceSynchro() {
        announcer.announce(new SatelliteSynchroEvent(this));
    }

	public void registerMoveEvent(Object o) {
		this.announcer.register(o, SatelliteMoveEvent.class);
		this.announcer.register(o, SatelliteSynchroEvent.class);
	}
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}
	
	 public SatelliteState getState() { 
		 return state; 
	}
	 
	public void setState(SatelliteState state) { 
		this.state = state;
	}

	
}
