/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jmetal.util.asf;

import jmetal.core.SolutionSet;
import jmetal.qualityIndicator.Hypervolume;
import jmetal.qualityIndicator.QualityIndicator;
import org.apache.commons.math3.distribution.ChiSquaredDistribution;

/**
 *
 * @author AIA
 */
public class OCD_HV 
{
    public static void getOCD_HC(QualityIndicator indicator, SolutionSet[] PF, OCD_HV_Param param)
    {
        int nPreGen = PF.length-1;
        double PI[][] = new double[3][nPreGen];// zeros(3,nPreGen+1);
        double pChi2New[] = new double[3]; //zeros(1,3);
        //boolean termCrit[] = new boolean[2];//false(1,2);
        //double pReg = 0;
        //double pChi2[]={1.0, 1.0, 1.0};
        //% PF        is a 1xnPreGen+1 vector of cell arrays holding the current and 
        //%           the last nPreGen Pareto front approximations
        SolutionSet PFi = PF[nPreGen];//%current pareto
        int d = PF[0].get(0).getNumberOfObjectives(); //size(PFi,2);

        double lb[] = new double[d];
        double ub[] = new double[d];
        for (int i = 0; i < d; i++) 
        {
            lb[i]=Double.MAX_VALUE;
            ub[i]=Double.MIN_VALUE;            
        }
        
        for (int i = 0; i < d; i++) 
        {
            for(int j=0;j<nPreGen+1; j++)
            {
                lb[i]=Math.min(lb[i],PF[j].get(0).getObjective(i) );
                ub[i]=Math.max(ub[i],PF[j].get(0).getObjective(i) );
            }
        }
        
        boolean PIindex[]={true,false,false};
        double alpha = 0.05;
        double VarLimit = 1e-3;
        
        int n=0;
        for(int i=0;i<PIindex.length;i++)
            if(PIindex[i])
                n++;
        double refValue=0.0;
        Hypervolume indicators = new Hypervolume();
        //String paretoFrontPath ="000__P_files";
        //double[][] trueFront =  new Hypervolume().utils_.readFront(paretoFrontPath);

        if( PIindex[0] )
        {
            refValue=indicator.getHypervolume(PFi);
            //double solutionFront[][]=getSolParetoFront(PFi);            
            //refValue=indicators.hypervolume(solutionFront, trueFront, d);
        }
        
        
        for (int k = 0; k<nPreGen;k++)
        {
            SolutionSet PFk = PF[k];
            for (int j = 0;j<3;j++)
            {
                if (PIindex[j])
                {
                    //% compute indicator values
                    if (j==0)
                    {
                        double hv=indicator.getHypervolume(PFk);
                        //double solutionFront[][]=getSolParetoFront(PFk);  
                        //double hv=indicators.hypervolume(solutionFront, trueFront, d);
                        PI[j][k] = refValue-hv;
                    }
                        
                    if (j==1)
                        throw new  RuntimeException("epsilonIndicator is not implemented");
                        //PI[j][k] = epsilonIndicator(PFk, PFi);                     
                    if (j==2)
                        throw new  RuntimeException("rIndicator is not implemented");
                        //PI[j][k]= rIndicator(PFk, PFi, lb, ub);                    
                }
            }        
        }
        
        param.termCrit[0]=false;
        param.termCrit[1]=false;
    
        for (int j = 0;j<3;j++)
            {
                if (PIindex[j])
                {
                    pChi2New[j] = chi2(PI[j], VarLimit); // % perform Chi^2 test
                    
                        
                    if (j==1)
                        throw new  RuntimeException("epsilonIndicator is not implemented");
                        //PI[j][k] = epsilonIndicator(PFk, PFi);                     
                    if (j==2)
                        throw new  RuntimeException("rIndicator is not implemented");
                        //PI[j][k]= rIndicator(PFk, PFi, lb, ub);
                    
                }
            } 
        
            
            int cnt=0;
            for (int i = 0; i < pChi2New.length; i++) 
            {
                if(pChi2New[i] <= alpha/n && param.pChi2[i] <= alpha/n)
                {
                    cnt++;
                }
            }
            if(cnt==pChi2New.length)
                param.termCrit[0] =true;  
            
            
            
            double pRegNew = 1.0;  //Reg(PI(PIindex,1:nPreGen)); % perform t-test
            //if((pRegNew > alpha) && (pReg > alpha))
             param.termCrit[1] = true;
             //% update p-values
            for (int i = 0; i < pChi2New.length; i++)
            {
                param.pChi2[i]=pChi2New[i];
            }
             //pChi2 = pChi2New; 
             param.pReg = pRegNew;

    }
    
    /*
    private static double[][] getSolParetoFront(SolutionSet PF_Sol)
    {
        double solutionFront[][]=new double[PF_Sol.size()][PF_Sol.get(0).getNumberOfObjectives()];
        for (int idx1 = 0; idx1 < solutionFront.length; idx1++) 
        {
            for (int idx2 = 0; idx2 < solutionFront[idx1].length; idx2++) 
            {
                solutionFront[idx1][idx2]=PF_Sol.get(idx1).getObjective(idx2);
            }
        }
        
        return solutionFront;
    }*/
    private static double chi2(double PI[], double VarLimit)
    {
        int N = PI.length-1;// % determine degrees of freedom
        double Chi = calcVariance(PI)*N/VarLimit ; //% compute test statistic
        
        //% look up p-value from Chi^2 distribution with N degrees of freedom
        
        ChiSquaredDistribution chiSqDist=new ChiSquaredDistribution(N);
        double p = chiSqDist.cumulativeProbability (Chi); 
        return p;
    
    }
    
    private static double calcVariance(double[] data)
    {
       // double[] data = {10.0,20.0,30.0,40.0,50.0,60.0,70.0,80,0,90.0,100.0};

        // The mean average
        double mean = 0.0;
        for (int i = 0; i < data.length; i++) {
                mean += data[i];
        }
        mean /= data.length;

        // The variance
        double variance=0.0;
        for (int i = 0; i < data.length; i++) {
            variance += (data[i] - mean) * (data[i] - mean);
        }
        variance /= data.length;

        // Standard Deviation
        //double std = Math.sqrt(variance);
        return variance;
    }
}
