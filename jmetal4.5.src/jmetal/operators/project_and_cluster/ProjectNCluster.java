/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jmetal.operators.project_and_cluster;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import jmetal.core.Solution;
import jmetal.core.SolutionSet;
import jmetal.encodings.solutionType.ArrayRealSolutionType;
import jmetal.encodings.solutionType.RealSolutionType;
import jmetal.util.JMException;

/**
 *
 * @author UsEr
 */
public class ProjectNCluster extends Projection{
    
    private static final List VALID_TYPES = Arrays.asList(RealSolutionType.class,
  		                                            ArrayRealSolutionType.class) ;
    
    private int solutionSize;
    private int numberOfCluster;
    public static double q=0;
  
    
    
    public ProjectNCluster(HashMap<String, Object> parameters) {
		super(parameters) ;
	
        if (parameters.get("solutionSize") != null)
  		solutionSize = (int) parameters.get("solutionSize") ;  		
  	if (parameters.get("numberOfCluster") != null)
  		numberOfCluster = (int) parameters.get("numberOfCluster") ;  		

	} 
    
    
    
    

    public  double[][] transposeMatrix(double [][] m){
        double[][] temp = new double[m[0].length][m.length];
        for (int i = 0; i < m.length; i++)
            for (int j = 0; j < m[0].length; j++)
                temp[j][i] = m[i][j];
        return temp;
    }


    
    
    
    

    @Override
    public Object execute(Object object) throws JMException {
       
        SolutionSet population = (SolutionSet)object;
        
        double maxTemp = 0, magnitude=0;

        double[][] objectives = population.writeObjectivesToMatrix();
        
        double[]ws = new double[objectives[0].length];
        
        
        for (int i = 0 ; i < objectives[0].length;i++) {
            for(int j=0;j<objectives.length;j++){
                if(objectives[j][i] > maxTemp) maxTemp= objectives[j][i] ;
            }
            if(maxTemp == 0)maxTemp = 0.000001;
            ws[i]= 1/maxTemp;
            maxTemp=0;   
        }
        
        for(double item : ws)
            magnitude+= item*item;
        
        
        double[][] transpose = transposeMatrix(objectives);
        
        

        double[] dotProduct = new double[transpose[0].length];
        
        for(int i=0;i<dotProduct.length;i++)
            dotProduct[i]=0;
        
        for(int i=0;i<transpose[0].length;i++)
            for(int j=0;j<transpose.length;j++)
                dotProduct[i]+=ws[j]*transpose[j][i];
        
        for(int i=0;i<dotProduct.length;i++)
            dotProduct[i] = (1-dotProduct[i]) /magnitude;
        
        double[][] newObjectives = new double[dotProduct.length][ws.length];
        
        for(int i=0;i<dotProduct.length;i++)
            for(int j=0;j<ws.length;j++)
                newObjectives[i][j]=dotProduct[i]*ws[j];
        
        double[][] finalObjectives = new double[dotProduct.length][ws.length];
        
        for(int i=0;i<dotProduct.length;i++)
            for(int j= 0;j<ws.length;j++)
                finalObjectives[i][j]= newObjectives[i][j]+objectives[i][j];
        
        
        ArrayList<Cluster> clusters = new ClusteringKMeans().doClustering(finalObjectives, finalObjectives[0], numberOfCluster);
        

        double[] t,c;
        q=0;
        double sum;
        for(int i=0;i<clusters.size();i++){
            
            sum=0;
            
            for(int j=0;j<clusters.get(i).getClusterSize();j++){
                t = finalObjectives[ clusters.get(i).clusterElements.get(j) ];
                c = finalObjectives[ clusters.get(i).getCentroid()  ];
                sum+= distance( t ,  c    );
                
            }
            sum = sum/clusters.get(i).getClusterSize() ;
            q+=sum;
            
        }
        

        ArrayList<SolutionSet> populations = new ArrayList<>();
        
        SolutionSet tempset;
        for (int i = 0; i < clusters.size(); i++) {
            
            tempset = new SolutionSet(clusters.get(i).clusterElements.size());
            
            for (int in : clusters.get(i).clusterElements) {
                tempset.add(population.get( in ));
            }
            populations.add(tempset);
            
        }
        
        
        return populations;
    }
    
    
    
    	public double distance(double[] tuple, double[] vector) {
		// Euclidean distance between an actual data tuple and a cluster mean or
		// centroid
		double sumSquaredDiffs = 0.0;
		for (int j = 0; j < tuple.length; ++j)
			sumSquaredDiffs += Math.pow((tuple[j] - vector[j]), 2);
		return Math.sqrt(sumSquaredDiffs);
	}
    
}
