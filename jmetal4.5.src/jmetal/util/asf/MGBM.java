/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jmetal.util.asf;


import jmetal.core.SolutionSet;
import jmetal.util.Ranking;
import jmetal.core.Solution;

/**
 *
 * @author UsEr
 */
public class MGBM {
    
    public double getActualMDR(SolutionSet generation1 ,SolutionSet generation2){
        
        int score1 = 0;
        int score2 = 0;
        int rank1 = 0, rank2 = 0;
        boolean flag1 = false, flag2 = false;
        //generation1.add( new SolutionCustomize() );
        
        for(int i = 0 ; i< generation1.size() ; i++){
            generation1.get(i).setGenereration(1);
        }
                
                
        for(int i = 0 ; i< generation2.size() ; i++){
            generation2.get(i).setGenereration(2);
        }
        
        
        SolutionSet population = new SolutionSet(generation1.size() + generation2.size()); 
        
        for (int i = 0; i < generation1.size(); i++) {
            population.add( generation1.get(i) );
        }
        
        for (int i = 0; i < generation2.size(); i++) {
            population.add( generation2.get(i) );
        }
        
        Ranking ranking = new Ranking(population);
 
        population.clear();
        
        for (int i = 0; i < ranking.getNumberOfSubfronts(); i++) {
            
            SolutionSet front = ranking.getSubfront(i);
            
            for(int j =0 ; j < front.size(); j++){
                front.get(j).setRank(i);
                population.add( front.get(j) );
            }
        }
        
        ////////////////////////case1
//   try{        
        if(population.get(0).getGenereration() == 1){
            
            rank1 = population.get(0).getRank();
            for(int i=1;i< population.size();i++){
                
                if(population.get(i).getGenereration() == 2 && population.get(i).getRank() > rank1)
                score1 ++;
            }
            
            int x = 1;
            while(population.get(x).getGenereration() != 2){
                x++;
            }
            
            rank2 = population.get(x).getRank();
            for(int i=x;i< population.size();i++){
                
                if(population.get(i).getGenereration() == 1 && population.get(i).getRank() > rank2)
                score2 ++;
            }
            
        }
//   }
//   catch(Exception ex){
//       throw  ex;
//   }
        
        /////////////////////case2
        try{
       if(population.get(0).getGenereration() == 2){
         
            rank2 = population.get(0).getRank();
            for(int i=1;i< population.size();i++){
                
                if(population.get(i).getGenereration() == 1 && population.get(i).getRank() > rank2)
                score2 ++;
            }
            
            int x = 1;
            while(population.get(x).getGenereration() != 1){
                x++;
            }
            
            rank1 = population.get(x).getRank();
            for(int i=x;i< population.size();i++){
                
                if(population.get(i).getGenereration() == 2 && population.get(i).getRank() > rank1)
                score1 ++;
            }
           
        }
        }
          catch(Exception ex){
       
   }
        
        
        return ((score1 - score2)/generation1.size()) -  ((score2 - score1)/generation2.size());
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
