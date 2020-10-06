/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jmetal.operators.harmony;

import java.util.ArrayList;
import jmetal.core.Solution;

/**
 *
 * @author UsEr
 */
public class HarmonyUnit {


    
    private Solution solution;
    private ArrayList<Integer> memoryIndeces  = new ArrayList<>();


    
    public HarmonyUnit(){

    }
    
    public Solution getSolution() {
        return solution;
    }

    public void setSolution(Solution solution) {
        this.solution = solution;
    }
    
    public ArrayList<Integer> getMemoryIndeces() {
        return memoryIndeces;
    }
    
    public void AddMemoryIndex(int index) {
        memoryIndeces.add(index);
    }
            
    
}
