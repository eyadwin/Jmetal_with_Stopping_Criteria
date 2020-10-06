/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jmetal.operators.harmony;

import java.util.HashMap;
import java.util.Random;
import jmetal.util.JMException;
import jmetal.util.wrapper.XReal;

/**
 *
 * @author UsEr
 */
public class PitchAdjustment extends Harmony{
    
    double pitchAdjustRate;
    double bandwidth;
    int itration;
    
    public PitchAdjustment(HashMap<String, Object> parameters) {
  	super (parameters) ;
  	
  	if (parameters.get("pitchAdjustRate") != null)
  		pitchAdjustRate = (Double) parameters.get("pitchAdjustRate") ; 

        if (parameters.get("bandwidth") != null)
  		bandwidth = (Double) parameters.get("bandwidth") ;  
		
  }

    @Override
    public Object execute(Object object) throws JMException {
        
        HarmonyUnit hUnit = (HarmonyUnit) object;
        XReal offs = new XReal(hUnit.getSolution());
        int variablesLength = offs.getNumberOfDecisionVariables();
        
        itration =  Math.round(  (float)(pitchAdjustRate * variablesLength));
        
        int i =0;
        //if(itration == 0) itration =1;
        while (i < itration) {   
            
            int r = new Random().nextInt( hUnit.getMemoryIndeces().size() );
            
            int trueIndex = hUnit.getMemoryIndeces().get(r);
            
            double epc =   -1 + (2*  new Random().nextDouble());  
            
            double newValue = offs.getValue(trueIndex) + (epc * bandwidth) ;
                     
                            
            if (newValue < offs.getLowerBound(trueIndex))
                newValue = offs.getLowerBound(trueIndex);
            
            else if (newValue > offs.getUpperBound(trueIndex))
                newValue = offs.getUpperBound(trueIndex);
            
            offs.setValue(trueIndex, newValue);
            
            i++;
            
        }
        
        
        
        return hUnit.getSolution();
    }
}
