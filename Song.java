// Krish Doshi
// 12/4/2024
// CSE 123 
// C3: Better Than Spam
// TA: Cynthia

import java.util.*;

// This class represents a Song storing information about the danceability and energy value
// associated with the Song
public class Song implements Classifiable{
    public static final Set<String> FEATURES = Set.of("danceability", "energy");
    private static final int INDEX_OF_DANCEABILITY = 11;
    private static final int INDEX_OF_ENERGY = 12;


    private double danceability;
    private double energy;

    // Behavior: 
    //          - creates a new Song from the provided danceability and energy feature values
    // Parameters:
    //          - 'danceability': the danceability value associated with the given song (assumed
    //                            to be non-null and are values that should fall within 
    //                            "0.0" to "1.0")
    //          - 'energy': the energy value associated with the given song (assumed
    //                      to be non-null and are values that should fall within 
    //                      "0.0" to "1.0")
    public Song(String danceability, String energy) {
        this.danceability = Double.parseDouble(danceability);
        this.energy = Double.parseDouble(energy);
    }


    // Behavior: 
    //          - Provides the value of the feature that is requested by the client
    // Exceptions:
    //          - Throws an IllegalArgumentException if the requested feature isn't one of the 
    //          - features associated with Song (if it isn't danceability or energy)
    // Parameters:
    //          - 'feature': feature that client is requesting from the current Song (assumed to
    //                       be non-null)
    // Returns: 
    //          - 'double': the value of the feature requested by the client. Returns the value of
    //                      danceability or energy.
    @Override
    public double get(String feature) {

        if (!FEATURES.contains(feature)) {
            throw new IllegalArgumentException(
                    String.format("Invalid feature [%s], not within possible features [%s]",
                                  feature, FEATURES.toString()));
        }
        
        if (feature.equals("danceability")) {
            return danceability;
        }else if(feature.equals("energy")){
            return energy;
        }else{
            return 0.0;
        }        
    }

    // Behavior: 
    //          - Provides the features associated with a Song
    // Returns: 
    //          - 'Set<String>': the features associated with a Song
    @Override
    public Set<String> getFeatures() {
        return FEATURES;
    }


    // Behavior: 
    //          - Creates a split between the current Song and the provided Song. The split
    //            occurs either halfway between the two energy values or the two danceability 
    //            (decided by which one has the larger difference between the two values)
    // Exceptions:
    //          - Throws an IllegalArgumentException if the provided piece of data isn't a Song
    // Parameters:
    //          - 'other': a provided Song that will be used to create a split between that song
    //                     and the current song (assumed to be non-null)
    // Returns: 
    //          - 'Split': the Split between the current Song and the other Song
    @Override
    public Split partition(Classifiable other) {
        if (!(other instanceof Song)) {
            throw new IllegalArgumentException("Provided 'other' not instance of Song.java");
        }

        Song otherSong = (Song) other;

        double danceabilityDiff = this.danceability - other.get("danceability");
        double energyDiff = this.energy - other.get("energy");

        // Calculate halfway between the two points of whichever feature (danceability or energy)
        // has a larger difference (if the difference is equal then it uses danceability)
        double halfway = danceabilityDiff >= energyDiff ? Split.midpoint(this.danceability,
                otherSong.get("danceability")): Split.midpoint(this.energy, 
                        otherSong.get("energy"));

        return danceabilityDiff >= energyDiff ? new Split("danceability", halfway): 
                new Split("energy", halfway);
    }

    // Behavior: 
    //          - Takes the data from a row of the training file and converts it into a Song with
    //            the associated danceability and energy value
    // Parameters:
    //          - 'row': data from a row of the training file (assumed to be non-null)
    // Returns: 
    //          - 'Classifiable': Song that is associated with the provided row that contains
    //                            the associated danceability and energy level of that song
    public static Classifiable toClassifiable(List<String> row) {
        return new Song(row.get(INDEX_OF_DANCEABILITY), row.get(INDEX_OF_ENERGY));
    } 
}
