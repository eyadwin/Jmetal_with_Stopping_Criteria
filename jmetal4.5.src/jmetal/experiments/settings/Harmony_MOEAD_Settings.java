/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jmetal.experiments.settings;

import java.util.HashMap;
import java.util.Properties;
import jmetal.core.Algorithm;
import jmetal.core.Operator;
import jmetal.experiments.Settings;
import jmetal.metaheuristics.moead.Harmony_MOEAD;
import jmetal.metaheuristics.moead.MOEAD;
import jmetal.operators.crossover.Crossover;
import jmetal.operators.crossover.CrossoverFactory;
import jmetal.operators.harmony.*;
import jmetal.operators.mutation.Mutation;
import jmetal.operators.mutation.MutationFactory;
import jmetal.operators.selection.Selection;
import jmetal.problems.ProblemFactory;
import jmetal.util.JMException;

/**
 *
 * @author UsEr
 */
public class Harmony_MOEAD_Settings extends Settings {

    
    public int populationSize_;
    public int maxEvaluations_;
    public double memoryConsiderationRate_;
    public double pitchAdjustRate_;
    public double bandwidth_;
    public String dataDirectory_;
    public int T_;
    public double delta_;
    public int nr_;

    /**
     * Constructor
     */
    public Harmony_MOEAD_Settings(String problem) {
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
        memoryConsiderationRate_ = 0.95;
        bandwidth_ = 0.01;
        pitchAdjustRate_ = 0.4;

        T_ = 20;
        delta_ = 0.9;
        nr_ = 2;

        // Directory with the files containing the weight vectors used in 
        // Q. Zhang,  W. Liu,  and H Li, The Performance of a New Version of MOEA/D 
        // on CEC09 Unconstrained MOP Test Instances Working Report CES-491, School 
        // of CS & EE, University of Essex, 02/2009.
        // http://dces.essex.ac.uk/staff/qzhang/MOEAcompetition/CEC09final/code/ZhangMOEADcode/moead0305.rar
        dataDirectory_ = "/Users/antelverde/Softw/pruebas/data/MOEAD_parameters/Weight";
    } // MOEAD_Settings

    /**
     * Configure the algorithm with the specified parameter experiments.settings
     *
     * @return an algorithm object
     * @throws jmetal.util.JMException
     */
    public Algorithm configure() throws JMException {
        Algorithm algorithm;
        MemoryConsiderationH memoryConOperator;
        RandomConsideration randomConOperator;
        PitchAdjustment pitchAjdOperator;

        HashMap parameters; // Operator parameters

        // Creating the problem
        algorithm = new Harmony_MOEAD(problem_);

        // Algorithm parameters
        algorithm.setInputParameter("populationSize", populationSize_);
        algorithm.setInputParameter("maxEvaluations", maxEvaluations_);
        algorithm.setInputParameter("dataDirectory", dataDirectory_);
        algorithm.setInputParameter("T", T_);
        algorithm.setInputParameter("delta", delta_);
        algorithm.setInputParameter("nr", nr_);



        parameters = new HashMap();
        parameters.put("memoryConsiderationRate", memoryConsiderationRate_);
        memoryConOperator = new MemoryConsiderationH(parameters);


        parameters = null;
        randomConOperator = new RandomConsideration(parameters);


        parameters = new HashMap();
        parameters.put("pitchAdjustRate", pitchAdjustRate_);
        parameters.put("bandwidth", bandwidth_);
        pitchAjdOperator = new PitchAdjustment(parameters);

        // Add the operators to the algorithm
        algorithm.addOperator("memoryConOperator", memoryConOperator);
        algorithm.addOperator("randomConOperator", randomConOperator);
        algorithm.addOperator("pitchAjdOperator", pitchAjdOperator);


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
     * Configure MOEAD with user-defined parameter experiments.settings
     *
     * @return A MOEAD algorithm object
     */
//  @Override
//  public Algorithm configure(Properties configuration) throws JMException {
//  
//  }
} // MOEAD_Settings
