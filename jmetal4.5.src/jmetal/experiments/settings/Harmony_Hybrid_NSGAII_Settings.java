/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jmetal.experiments.settings;

import java.util.HashMap;
import jmetal.core.Algorithm;
import jmetal.core.Operator;
import jmetal.experiments.Settings;
import jmetal.metaheuristics.nsgaII.Harmony_Hybrid_NSGAII;
import jmetal.metaheuristics.nsgaII.Harmony_NSGII;
import jmetal.operators.harmony.MemoryConsideration;
import jmetal.operators.harmony.PitchAdjustment;
import jmetal.operators.harmony.RandomConsideration;
import jmetal.operators.localSearch.MutationLocalSearch;
import jmetal.operators.mutation.Mutation;
import jmetal.operators.mutation.MutationFactory;
import jmetal.operators.project_and_cluster.ProjectNCluster;
import jmetal.problems.ProblemFactory;
import jmetal.util.JMException;

/**
 *
 * @author UsEr
 */
public class Harmony_Hybrid_NSGAII_Settings extends Settings {

    public int populationSize_;
    public int maxEvaluations_;
    public double memoryConsiderationRate_;
    public double pitchAdjustRate_;
    public double bandwidth_;
    public int numberOfCluster_;
    public double mutationProbability_;
    public double mutationDistributionIndex_;

    /**
     * Constructor
     */
    public Harmony_Hybrid_NSGAII_Settings(String problem) {
        super(problem);

        Object[] problemParams = {"Real"};
        try {
            problem_ = (new ProblemFactory()).getProblem(problemName_, problemParams);
        } catch (JMException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // Default experiments.settings
        populationSize_ = 100;
        maxEvaluations_ = 20000;
        memoryConsiderationRate_ = 0.96;
        bandwidth_ = 0.01;
        pitchAdjustRate_ = 0.4;
        numberOfCluster_ = 8;

        

    } // Harmony_NSGAII_Settings

    /**
     * Configure NSGAII with default parameter experiments.settings
     *
     * @return A NSGAII algorithm object
     * @throws jmetal.util.JMException
     */
    public Algorithm configure() throws JMException {
        Algorithm algorithm;
        MemoryConsideration memoryConOperator;
        RandomConsideration randomConOperator;
        PitchAdjustment pitchAjdOperator;
        Operator projection; //  operator
        Operator localSearch; //  operator
        Mutation mutation;



        HashMap parameters; // Operator parameters

        // Creating the algorithm. There are two choices: NSGAII and its steady-
        // state variant ssNSGAII
        algorithm = new Harmony_Hybrid_NSGAII(problem_);
        //algorithm = new ssNSGAII(problem_) ;

        // Algorithm parameters
        algorithm.setInputParameter("populationSize", populationSize_);
        algorithm.setInputParameter("maxEvaluations", maxEvaluations_);

        // Mutation and Crossover for Real codification
        parameters = new HashMap();
        parameters.put("memoryConsiderationRate", memoryConsiderationRate_);
        memoryConOperator = new MemoryConsideration(parameters);


        parameters = null;
        randomConOperator = new RandomConsideration(parameters);

        // Selection Operator
        parameters = new HashMap();
        parameters.put("pitchAdjustRate", pitchAdjustRate_);
        parameters.put("bandwidth", bandwidth_);
        pitchAjdOperator = new PitchAdjustment(parameters);


        parameters = new HashMap();
        parameters.put("solutionSize", populationSize_);
        parameters.put("numberOfCluster", numberOfCluster_);
        projection = new ProjectNCluster(parameters);

        mutationDistributionIndex_ = 20.0;
        mutationProbability_ = 1.0 / problem_.getNumberOfVariables();
        parameters = new HashMap();
        parameters.put("probability", mutationProbability_);
        parameters.put("distributionIndex", mutationDistributionIndex_);
        mutation = MutationFactory.getMutationOperator("PolynomialMutation", parameters);


        parameters = new HashMap();
        parameters.put("problem", problem_);
        parameters.put("improvementRounds", 4);
        parameters.put("mutation", mutation);
        localSearch = new MutationLocalSearch(parameters);

        // Add the operators to the algorithm
        algorithm.addOperator("memoryConOperator", memoryConOperator);
        algorithm.addOperator("randomConOperator", randomConOperator);
        algorithm.addOperator("pitchAjdOperator", pitchAjdOperator);
        algorithm.addOperator("projection", projection);
        algorithm.addOperator("improvement", localSearch);

        return algorithm;
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
     *
     * @return A NSGAII algorithm object
     */
//  @Override
//  public Algorithm configure(Properties configuration) throws JMException 
} // Harmony_Hybrid_NSGAII_Settings
