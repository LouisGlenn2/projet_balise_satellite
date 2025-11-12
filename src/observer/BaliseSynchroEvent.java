package observer;

import announcer.AbstractEvent;

public class BaliseSynchroEvent extends AbstractEvent{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BaliseSynchroEvent(Object source) {
		super(source);
		// TODO Auto-generated constructor stub
	}
	
    @Override
    public void sentTo(Object target) {
        ((BaliseListener) target).SynchroBalise (this);
    }

}
