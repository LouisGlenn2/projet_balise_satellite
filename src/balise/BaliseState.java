package balise;

public enum BaliseState {
    Collect,      // Phase de collecte de données en profondeur
    Remontee,     // Phase de remontée vers la surface
    WaitSynchro,  // En surface, immobile, en attente d'un satellite
    Synchro,      // En cours de synchronisation avec un satellite
    redescente    // Redescente pour reprendre la collecte
}