package balise;
/**
 * Énumération représentant les différents états du cycle de vie d'une balise sous-marine.
 * 
 * <p>Le cycle complet est le suivant :</p>
 * <ol>
 *   <li>{@link #Collect} : Collecte initiale de données</li>
 *   <li>{@link #Remontee} : Remontée déclenchée lorsque la mémoire est pleine</li>
 *   <li>{@link #WaitSynchro} : Attente en surface d'un satellite disponible</li>
 *   <li>{@link #Synchro} : Transmission des données au satellite</li>
 *   <li>{@link #redescente} : Retour à la profondeur pour un nouveau cycle</li>
 * </ol>
 * 
 * @see Balise
 */
public enum BaliseState {
    Collect,      
    Remontee,     
    WaitSynchro,  
    Synchro,      
    redescente   
}