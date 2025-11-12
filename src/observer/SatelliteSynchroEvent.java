package observer;

import announcer.AbstractEvent;

public class SatelliteSynchroEvent extends AbstractEvent{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SatelliteSynchroEvent(Object source) {
		super(source);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void sentTo(Object target) {
		((SatelliteListener) target).SattelliteSynchroEvent(this);
	}
}


