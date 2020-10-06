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
public class RandomConsideration extends Harmony {

    int itration;

    public RandomConsideration(HashMap<String, Object> parameters) {
        super(parameters);
    }

    @Override
    public Object execute(Object object) throws JMException {

        HarmonyUnit hUnit = (HarmonyUnit) object;
        int variablesLength = new XReal(hUnit.getSolution()).getNumberOfDecisionVariables();
        
        XReal offs = new XReal(hUnit.getSolution());
        
        itration = variablesLength - hUnit.getMemoryIndeces().size();

        System.out.print("");

            for (int j = 0; j < variablesLength; j++) {
                if(!hUnit.getMemoryIndeces().contains(j)){
                    
                    double yl= offs.getLowerBound(j) ;
                    double yu = offs.getUpperBound(j) ;
                    
                    double value = yl + (yu - yl)*new Random().nextDouble();
                    offs.setValue(j, value);

                }
            }

        return hUnit;
    }
}
