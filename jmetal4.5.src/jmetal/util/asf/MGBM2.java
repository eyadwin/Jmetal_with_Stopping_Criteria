package jmetal.util.asf;

import jmetal.core.SolutionSet;
import jmetal.util.Ranking;

public class MGBM2 {
    
    public double getFHI(SolutionSet generation1 /*,SolutionSet generation2*/){
        double i_hom=0.0;
        double ObjCnt= generation1.get(0).getNumberOfObjectives();
        
        for(int OIdx=0; OIdx < ObjCnt ;OIdx++)
        {
            double minFx=9999999999999999999999999999999999999999.9;
            double maxFx=-999999999999999999999999999999999999999.9;

            for(int i = 0 ; i< generation1.size() ; i++)
            {
                if(minFx>generation1.get(i).getObjective(OIdx))
                    minFx=generation1.get(i).getObjective(OIdx);

                if(maxFx<generation1.get(i).getObjective(OIdx))
                    maxFx=generation1.get(i).getObjective(OIdx);            
            }

            double scaledFx1[]=new double[generation1.size()];
            //double scaledFx2[]=new double[generation2.size()];
            double sumScaledFx1=0.0; //,sumScaledFx2=0.0;
            double avgScaledFx1; //,avgScaledFx2=0.0;

            for(int i = 0 ; i< scaledFx1.length ; i++)
            {
                double currFx=generation1.get(i).getObjective(OIdx);
                scaledFx1[i]=(currFx-minFx)/maxFx;
                sumScaledFx1+=scaledFx1[i];
            }
            avgScaledFx1=sumScaledFx1/scaledFx1.length;

            double sumSqr1=0.0;
            for(int i = 0 ; i< scaledFx1.length ; i++)
            {
                sumSqr1+=Math.pow( scaledFx1[i]-avgScaledFx1 , 2 );
            }
            
            i_hom += Math.sqrt(sumSqr1/scaledFx1.length);
        }
        i_hom = i_hom/ObjCnt;
        return i_hom;
    }
    
    // s* = s` + K(z - s`)
     public double getPredectedMDR(double  sPredected , double zReal ,double KalmanGain){
        
        return (sPredected + KalmanGain * (zReal - sPredected));
    }
     
     
     public double getKalmanGain(double z, double s){
         
         double m = (z+s)/2;
         
         double p = ((z-m)*(z-m)   +   (s-m)*(s-m))/2;
         
         return p/(p+.1);
     }
    
}
