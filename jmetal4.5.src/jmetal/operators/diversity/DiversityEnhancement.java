/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jmetal.operators.diversity;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import jmetal.core.Operator;
import jmetal.core.Solution;
import jmetal.core.SolutionSet;
import jmetal.encodings.solutionType.ArrayRealSolutionType;
import jmetal.encodings.solutionType.RealSolutionType;
import jmetal.util.JMException;
import jmetal.util.Ranking;
import jmetal.util.comparators.CrowdingComparator;


/**
 *
 * @author UsEr
 */
public class DiversityEnhancement extends Diverse {
    
    
    private static final List VALID_TYPES = Arrays.asList(RealSolutionType.class,
  		                                            ArrayRealSolutionType.class) ;
    //private int Plocal;
    
        
    public DiversityEnhancement(HashMap<String, Object> parameters) {
		super(parameters) ;
	
//        if (parameters.get("Plocal") != null)
//  		Plocal = (int) parameters.get("Plocal") ;  
                 
	
	} 
    
    

    @Override
    public Object execute(Object object ) throws JMException {
         SolutionSet population = (SolutionSet)object;
         SolutionSet Q;
         SolutionSet union;
          int evaluations=0;

        Operator mutationOperator;
        Operator crossoverOperator;
        Operator selectionOperator;
        
//        mutationOperator = operators_.get("mutation");
//        crossoverOperator = operators_.get("crossover");
//        selectionOperator = operators_.get("selection");
//        
//        
//            while (evaluations < maxEvaluations) {
//
//      // Create the offSpring solutionSet      
//      offspringPopulation = new SolutionSet(populationSize);
//      Solution[] parents = new Solution[2];
//      for (int i = 0; i < (populationSize / 2); i++) {
//        if (evaluations < maxEvaluations) {
//          //obtain parents
//          parents[0] = (Solution) selectionOperator.execute(population);
//          parents[1] = (Solution) selectionOperator.execute(population);
//          Solution[] offSpring = (Solution[]) crossoverOperator.execute(parents);
//          mutationOperator.execute(offSpring[0]);
//          mutationOperator.execute(offSpring[1]);
//          problem_.evaluate(offSpring[0]);
//          problem_.evaluateConstraints(offSpring[0]);
//          problem_.evaluate(offSpring[1]);
//          problem_.evaluateConstraints(offSpring[1]);
//          offspringPopulation.add(offSpring[0]);
//          offspringPopulation.add(offSpring[1]);
//          evaluations += 2;
//        } // if                            
//      } // for
//
//      // Create the solutionSet union of solutionSet and offSpring
//      union = ((SolutionSet) population).union(offspringPopulation);
        
        
        
        //<<Project and Cluster Code ************************************************>>
        
//              Ranking ranking = new Ranking(union);
//
//      int remain = populationSize;
//      int index = 0;
//      SolutionSet front = null;
//      population.clear();
//
//      // Obtain the next front
//      front = ranking.getSubfront(index);
//
//      while ((remain > 0) && (remain >= front.size())) {
//        //Assign crowding distance to individuals
//        distance.crowdingDistanceAssignment(front, problem_.getNumberOfObjectives());
//        //Add the individuals of this front
//        for (int k = 0; k < front.size(); k++) {
//          population.add(front.get(k));
//        } // for
//
//        //Decrement remain
//        remain = remain - front.size();
//
//        //Obtain the next front
//        index++;
//        if (remain > 0) {
//          front = ranking.getSubfront(index);
//        } // if        
//      } // while
//
//      // Remain is less than front(index).size, insert only the best one
//      if (remain > 0) {  // front contains individuals to insert                        
//        distance.crowdingDistanceAssignment(front, problem_.getNumberOfObjectives());
//        front.sort(new CrowdingComparator());
//        for (int k = 0; k < remain; k++) {
//          population.add(front.get(k));
//        } // for
//
//        remain = 0;
//      } // if    
        
         
         return population;
    }
    
}
