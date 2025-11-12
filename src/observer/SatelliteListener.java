package observer;

public interface SatelliteListener {
	public void SatelliteMoveEvent(SatelliteMoveEvent event);
	public void SattelliteSynchroEvent(SatelliteSynchroEvent event);
}
