/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jmetal.operators.diversity;

import java.util.HashMap;
import jmetal.core.Operator;

/**
 *
 * @author UsEr
 */
public abstract class Diverse extends Operator{
    
    	public Diverse(HashMap<String, Object> parameters) {
	  super(parameters);
        }
        
        public static boolean Check(double Qcurrent,double  Qbound){
            
//            double  Qbound;
//            
//            if(iteration % 5 == 0)
//                Qbound = Qcurrent/4;
            
            if(Qcurrent < Qbound )
                return false;
            return true;
        }
    
}
