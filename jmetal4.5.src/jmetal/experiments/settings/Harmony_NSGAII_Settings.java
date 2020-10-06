/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jmetal.experiments.settings;

import java.util.HashMap;
import jmetal.core.Algorithm;
import jmetal.experiments.Settings;
import jmetal.metaheuristics.nsgaII.Harmony_NSGII;
import jmetal.operators.harmony.MemoryConsideration;
import jmetal.operators.harmony.PitchAdjustment;
import jmetal.operators.harmony.RandomConsideration;
import jmetal.problems.ProblemFactory;
import jmetal.util.JMException;

/**
 *
 * @author UsEr
 */
public class Harmony_NSGAII_Settings extends Settings {
  public int populationSize_                 ;
  public int maxEvaluations_                 ;
  public double memoryConsiderationRate_     ;
  public double pitchAdjustRate_             ;
  public double bandwidth_                   ;


  /**
   * Constructor
   */
  public Harmony_NSGAII_Settings(String problem) {
    super(problem) ;

    Object [] problemParams = {"Real"};
    try {
	    problem_ = (new ProblemFactory()).getProblem(problemName_, problemParams);
    } catch (JMException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
    }
    // Default experiments.settings
    populationSize_              = 100  ;
    maxEvaluations_              = 5000 ;
    memoryConsiderationRate_     = 0.95   ;
    bandwidth_                   = 0.01   ;
    pitchAdjustRate_             = 0.4   ;
    
  } // Harmony_NSGAII_Settings


  /**
   * Configure NSGAII with default parameter experiments.settings
   * @return A NSGAII algorithm object
   * @throws jmetal.util.JMException
   */
  public Algorithm configure() throws JMException {
    Algorithm algorithm ;
    MemoryConsideration  memoryConOperator ;
    RandomConsideration  randomConOperator ;
    PitchAdjustment      pitchAjdOperator  ;

    HashMap  parameters ; // Operator parameters

    // Creating the algorithm. There are two choices: NSGAII and its steady-
    // state variant ssNSGAII
    algorithm = new Harmony_NSGII(problem_) ;
    //algorithm = new ssNSGAII(problem_) ;

    // Algorithm parameters
    algorithm.setInputParameter("populationSize",populationSize_);
    algorithm.setInputParameter("maxEvaluations",maxEvaluations_);

   
    parameters = new HashMap() ;
    parameters.put("memoryConsiderationRate", memoryConsiderationRate_) ;
    memoryConOperator = new MemoryConsideration(parameters);

    
    parameters = null ;
    randomConOperator = new RandomConsideration( parameters);

    // Selection Operator
    parameters = new HashMap() ;
    parameters.put("pitchAdjustRate", pitchAdjustRate_) ;
    parameters.put("bandwidth", bandwidth_) ;
    pitchAjdOperator = new PitchAdjustment( parameters) ;

    // Add the operators to the algorithm
    algorithm.addOperator("memoryConOperator",memoryConOperator);
    algorithm.addOperator("randomConOperator",randomConOperator);
    algorithm.addOperator("pitchAjdOperator",pitchAjdOperator);

    return algorithm ;
  } // configure

   public Algorithm updateSettings(Algorithm algorithm, HashMap<String, Object> params) {

        Object stoppingCriteria = params.get("stoppingCriteria");
        Object step = params.get("step");


        algorithm.setInputParameter("stoppingCriteria", stoppingCriteria);
        algorithm.setInputParameter("step", step);

        return algorithm;
    }
 /**
  * Configure NSGAII with user-defined parameter experiments.settings
  * @return A NSGAII algorithm object
  */

} // Harmony_NSGAII_Settings
