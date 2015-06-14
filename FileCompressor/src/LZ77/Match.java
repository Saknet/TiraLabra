
package LZ77;

/**
 * Class that contains data needed for LZ77's "match" object.
 */
public class Match {
    
    /**
     * length Integer, tells the length of the match.
     */
    private int length;
    
    /**
     * distance Integer, tells the starting location of the match from current location.
     */
    private int distance;
    
    /**
     * startPointer Integer, used for marking the current location.
     */
    private int startPointer;
    
    /**
     * All values are set to -1 when object is created.
     */
    public Match() {
        this.length = -1;
        this.distance = -1;
        this.startPointer = -1;
    }
    
    public int getLength() {
        return this.length;
    }
    
    public void setLength(int length) {
        this.length = length;
    }
    
    public int getDistance() {
        return this.distance;
    }
    
    public void setDistance(int distance) {
        this.distance = distance;
    }
    
    public int getStartPointer() {
        return this.startPointer;
    }
    
    public void setStartPointer(int startPointer) {
        this.startPointer = startPointer;
    }
}
