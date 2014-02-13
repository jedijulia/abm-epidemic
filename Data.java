/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package denguesimulation;

/**
 *
 * @author juliam
 */
public class Data {
    int susceptibleCount;
    int infectedCount;
    int resistantCount;
    
    public Data(int susceptibleCount, int infectedCount, int resistantCount) {
        this.susceptibleCount = susceptibleCount;
        this.infectedCount = infectedCount;
        this.resistantCount = resistantCount;
    }
    
    public int getSusceptibleCount() {
        return susceptibleCount;
    }
    
    public int getInfectedCount() {
        return infectedCount;
    }
    
    public int getResistantCount() {
        return resistantCount;
    }
}
