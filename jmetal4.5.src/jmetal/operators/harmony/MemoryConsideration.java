/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jmetal.operators.harmony;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import jmetal.core.Solution;
import jmetal.core.SolutionSet;
import jmetal.util.JMException;
import jmetal.util.wrapper.XReal;

/**
 *
 * @author UsEr
 */
public class MemoryConsideration extends Harmony{
    
    double memoryConsiderationRate;
    int itration;
    
    public MemoryConsideration(HashMap<String, Object> parameters) {
  	super (parameters) ;
  	
  	if (parameters.get("memoryConsiderationRate") != null)
  		memoryConsiderationRate = (Double) parameters.get("memoryConsiderationRate") ;  		
		
  }

    @Override
    public Object execute(Object object) throws JMException {
        
        SolutionSet population = (SolutionSet) object;
        Solution newSolution = new Solution( population.get( 0 ) );
        ArrayList<Integer> selectedSolutions = new ArrayList<>();
        HarmonyUnit hUnit = new HarmonyUnit();
        
        XReal offs = new XReal(newSolution);
        int len = newSolution.getDecisionVariables().length;
      //  int len2 = offs.getNumberOfDecisionVariables();
        
        itration = Math.round(  (float)(memoryConsiderationRate * len));
        int i = 0;
        if(itration == 0) itration =1;
        while (i < itration) {            
            
            int r = new Random().nextInt(population.size());
            if (r >= (population.size()/4))
                r = new Random().nextInt(population.size());
 //           if(!selectedSolutions.contains(r)){
                
//                selectedSolutions.add(r);
                int colIndex = new Random().nextInt(offs.getNumberOfDecisionVariables());
                
                while ( hUnit.getMemoryIndeces().contains(colIndex) ) {                    
                    colIndex = new Random().nextInt(offs.getNumberOfDecisionVariables());
                }
                
                hUnit.AddMemoryIndex(colIndex);
                
                double value  = new XReal( population.get(r) ).getValue(colIndex);
                
                offs.setValue(colIndex, value);

                i++;
                
//            }
            
        }
        
        
        hUnit.setSolution(newSolution);
        
        return hUnit;
    }
    
}
