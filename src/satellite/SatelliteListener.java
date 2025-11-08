package satellite;

public interface SatelliteListener {
	public void MobilMoveEvent(SatelliteMoveEvent event);
	public void MobiHandCheckRequestEvent(SatelliteMoveEvent event);
}
