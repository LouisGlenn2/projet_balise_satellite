package observer;

import announcer.AbstractEvent;

public class BaliseSynchroEvent extends AbstractEvent{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BaliseSynchroEvent(Object source) {
		super(source);
	}
	
    @Override
    public void sentTo(Object target) {
        ((BaliseListener) target).SynchroBalise (this);
    }

}
