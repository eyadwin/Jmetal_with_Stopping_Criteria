/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jmetal.experiments.settings;

import java.util.HashMap;
import jmetal.core.Algorithm;
import jmetal.core.Operator;
import jmetal.experiments.Settings;
import jmetal.metaheuristics.moead.H_MOEAD;
import jmetal.metaheuristics.moead.MOEAD;
import jmetal.operators.crossover.CrossoverFactory;
import jmetal.operators.localSearch.MutationLocalSearch;
import jmetal.operators.mutation.MutationFactory;
import jmetal.operators.project_and_cluster.ProjectNCluster;
import jmetal.operators.selection.SelectionFactory;
import jmetal.problems.ProblemFactory;
import jmetal.util.JMException;

/**
 *
 * @author UsEr
 */
public class H_MOEAD_Settings extends Settings {

    public double CR_;
    public double F_;
    public int populationSize_;
    public int maxEvaluations_;
    public double mutationProbability_;
    public double mutationDistributionIndex_;
    public String dataDirectory_;
    public int T_;
    public double delta_;
    public int nr_;
    
    public double crossoverProbability_;
    public double crossoverDistributionIndex_;
    public int numberOfCluster_;
    
    

    /**
     * Constructor
     */
    public H_MOEAD_Settings(String problem) {
        super(problem);
        Object[] problemParams = {"Real"};
        try {
            problem_ = (new ProblemFactory()).getProblem(problemName_, problemParams);
        } catch (JMException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // Default experiments.settings
        CR_ = 0.5;
        F_ = 0.5;
        populationSize_ = 100;
        maxEvaluations_ = 20000;

        mutationProbability_ = 1.0 / problem_.getNumberOfVariables();
        mutationDistributionIndex_ = 20;

        T_ = 20;
        delta_ = 0.9;
        nr_ = 2;
        
        crossoverProbability_ = 0.9;
        crossoverDistributionIndex_ = 20.0;
        numberOfCluster_ = 16;

        // Directory with the files containing the weight vectors used in 
        // Q. Zhang,  W. Liu,  and H Li, The Performance of a New Version of MOEA/D 
        // on CEC09 Unconstrained MOP Test Instances Working Report CES-491, School 
        // of CS & EE, University of Essex, 02/2009.
        // http://dces.essex.ac.uk/staff/qzhang/MOEAcompetition/CEC09final/code/ZhangMOEADcode/moead0305.rar
        dataDirectory_ = "/Users/antelverde/Softw/pruebas/data/MOEAD_parameters/Weight";
    } // MOEAD_Settings
    

    @Override
    public Algorithm configure() throws JMException {
        Algorithm algorithm;
        Operator crossover;
        Operator mutation;
        
     Operator projection; //  operator
    Operator localSearch; //  operator
    Operator selection; // Selection operator
    Operator mutation2; // Mutation operator
    Operator crossover2;

        HashMap parameters; // Operator parameters

        // Creating the problem
        algorithm = new H_MOEAD(problem_);

        // Algorithm parameters
        algorithm.setInputParameter("populationSize", populationSize_);
        algorithm.setInputParameter("maxEvaluations", maxEvaluations_);
        algorithm.setInputParameter("dataDirectory", dataDirectory_);
        algorithm.setInputParameter("T", T_);
        algorithm.setInputParameter("delta", delta_);
        algorithm.setInputParameter("nr", nr_);

        // Crossover operator 
        parameters = new HashMap();
        parameters.put("CR", CR_);
        parameters.put("F", F_);
        crossover = CrossoverFactory.getCrossoverOperator("DifferentialEvolutionCrossover", parameters);

        // Mutation operator
        parameters = new HashMap();
        parameters.put("probability", mutationProbability_);
        parameters.put("distributionIndex", mutationDistributionIndex_);
        mutation = MutationFactory.getMutationOperator("PolynomialMutation", parameters);
        
        
        
                // Selection Operator
        parameters = null;
        selection = SelectionFactory.getSelectionOperator("BinaryTournament2", parameters);


        parameters = new HashMap();
        parameters.put("solutionSize", populationSize_);
        parameters.put("numberOfCluster", numberOfCluster_);
        projection = new ProjectNCluster(parameters);


        parameters = new HashMap();
        parameters.put("problem", problem_);
        parameters.put("improvementRounds", 1);
        parameters.put("mutation", mutation);
        localSearch = new MutationLocalSearch(parameters);
        
        
        
                // Mutation and Crossover for Real codification
        parameters = new HashMap();
        parameters.put("probability", crossoverProbability_);
        parameters.put("distributionIndex", crossoverDistributionIndex_);
        crossover2 = CrossoverFactory.getCrossoverOperator("SBXCrossover", parameters);

        parameters = new HashMap();
        parameters.put("probability", mutationProbability_);
        parameters.put("distributionIndex", mutationDistributionIndex_);
        mutation2 = MutationFactory.getMutationOperator("PolynomialMutation", parameters);
        

        algorithm.addOperator("crossover", crossover);
        algorithm.addOperator("mutation", mutation);
        algorithm.addOperator("crossover2", crossover2);
        algorithm.addOperator("mutation2", mutation2);
        algorithm.addOperator("selection", selection);
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
}
