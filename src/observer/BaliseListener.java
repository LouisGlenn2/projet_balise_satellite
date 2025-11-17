package observer;

public interface BaliseListener {
    void onBaliseMove(BaliseMoveEvent event);
    void SynchroBalise (BaliseSynchroEvent event);
}