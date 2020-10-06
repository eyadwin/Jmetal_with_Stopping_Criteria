/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jmetal.util.asf;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import jmetal.core.SolutionSet;

/**
 *
 * @author UsEr
 */
public class AchievementScalarizing {

    double ASF[];
    double zPar[];
    double wf[];
    double p = 1.0 / 1000000.0;

    public double getBestASF(SolutionSet population) {

        double[][] objectives = population.writeObjectivesToMatrix();
        wf = new double[objectives[0].length];
        zPar = new double[objectives[0].length];
        ASF = new double[objectives.length -1];
        

        double maxTemp = -1000000.0, minTemp = 1000000.0;

        for (int i = 0; i < objectives[0].length; i++) {
            for (int j = 0; j < objectives.length; j++) {
                if (objectives[j][i] > maxTemp) {
                    maxTemp = objectives[j][i];
                }
                if (objectives[j][i] < minTemp) {
                    minTemp = objectives[j][i];
                }
            }

            wf[i] = 1 / (maxTemp - minTemp);
            maxTemp = -1000000;
            minTemp =  1000000;
        }


        for (int i = 0; i < zPar.length; i++) {

            zPar[i] = objectives[0][i];
        }

        double sum = 0;
        double temp[] = new double[wf.length];

        for (int i = 1; i < ASF.length +1; i++) {

            for (int j = 0; j < wf.length; j++) {
                temp[j] = wf[j] * (objectives[i][j] - zPar[j]);
                sum += p * temp[j];
            }

            Arrays.sort(temp);
            sum += temp[temp.length -1];
            ASF[i -1] = sum;
            sum=0;
        }

        for (int i = 0; i < ASF.length; i++) {
            if(ASF[i] == 0)
                ASF[i] = 10000;
        }
        Arrays.sort(ASF);
        
        if(ASF.length == 0)
        System.out.println();
if(ASF.length > 0)
        return ASF[0];
else return 1;
    }
    
    
    
    
    public double getAvarageASF(double [] ASF_Val ){
        
        double sum = 0;
        for (double d : ASF_Val) {
            
            sum+=  Math.abs(d)  ;
        }
        
        return sum/ ASF_Val.length;
    }
}
