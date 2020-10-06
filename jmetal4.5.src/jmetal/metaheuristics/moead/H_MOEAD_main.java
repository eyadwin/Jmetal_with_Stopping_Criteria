/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jmetal.metaheuristics.moead;

import java.io.IOException;
import java.util.HashMap;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import jmetal.core.Algorithm;
import jmetal.core.Operator;
import jmetal.core.Problem;
import jmetal.core.SolutionSet;
import jmetal.operators.crossover.CrossoverFactory;
import jmetal.operators.localSearch.MutationLocalSearch;
import jmetal.operators.mutation.MutationFactory;
import jmetal.operators.project_and_cluster.ProjectNCluster;
import jmetal.operators.selection.SelectionFactory;
import jmetal.problems.DTLZ.DTLZ1;
import jmetal.problems.Kursawe;
import jmetal.problems.ProblemFactory;
import jmetal.qualityIndicator.QualityIndicator;
import jmetal.util.Configuration;
import jmetal.util.JMException;

/**
 *
 * @author UsEr
 */
public class H_MOEAD_main {
   public static Logger      logger_ ;      // Logger object
  public static FileHandler fileHandler_ ; // FileHandler object

  /**
   * @param args Command line arguments. The first (optional) argument specifies 
   *      the problem to solve.
   * @throws JMException 
   * @throws IOException 
   * @throws SecurityException 
   * Usage: three options
   *      - jmetal.metaheuristics.moead.MOEAD_main
   *      - jmetal.metaheuristics.moead.MOEAD_main problemName
   *      - jmetal.metaheuristics.moead.MOEAD_main problemName ParetoFrontFile
   * @throws ClassNotFoundException 
 
   */
  public static void main(String [] args) throws JMException, SecurityException, IOException, ClassNotFoundException {
    Problem   problem   ;         // The problem to solve
    Algorithm algorithm ;         // The algorithm to use
    Operator  crossover ;         // Crossover operator
    Operator  mutation  ;         // Mutation operator
    
    Operator projection; //  operator
    Operator localSearch; //  operator
    Operator selection; // Selection operator
    Operator mutation2; // Mutation operator
    Operator crossover2;

     
    QualityIndicator indicators ; // Object to get quality indicators

    HashMap  parameters ; // Operator parameters

    // Logger object and file to store log messages
    logger_      = Configuration.logger_ ;
    fileHandler_ = new FileHandler("MOEAD.log"); 
    logger_.addHandler(fileHandler_) ;
    
    indicators = null ;
    if (args.length == 1) {
      Object [] params = {"Real"};
      problem = (new ProblemFactory()).getProblem(args[0],params);
    } // if
    else if (args.length == 2) {
      Object [] params = {"Real"};
      problem = (new ProblemFactory()).getProblem(args[0],params);
      indicators = new QualityIndicator(problem, args[1]) ;
    } // if
    else { // Default problem
      //problem = new Kursawe("Real", 3); 
      //problem = new Kursawe("BinaryReal", 3);
      //problem = new Water("Real");
      //problem = new ZDT1("ArrayReal", 100);
      //problem = new ConstrEx("Real");
      problem = new DTLZ1("Real");
      //problem = new OKA2("Real") ;
    } // else

    algorithm = new H_MOEAD(problem);
    //algorithm = new MOEAD_DRA(problem);
    
    // Algorithm parameters
    algorithm.setInputParameter("populationSize",300);  //300
    algorithm.setInputParameter("maxEvaluations",150000);  //150000
    
    // Directory with the files containing the weight vectors used in 
    // Q. Zhang,  W. Liu,  and H Li, The Performance of a New Version of MOEA/D 
    // on CEC09 Unconstrained MOP Test Instances Working Report CES-491, School 
    // of CS & EE, University of Essex, 02/2009.
    // http://dces.essex.ac.uk/staff/qzhang/MOEAcompetition/CEC09final/code/ZhangMOEADcode/moead0305.rar
    algorithm.setInputParameter("dataDirectory",
    "/Users/antelverde/Softw/pruebas/data/MOEAD_parameters/Weight");

    algorithm.setInputParameter("finalSize", 300) ; // used by MOEAD_DRA

    algorithm.setInputParameter("T", 20) ;
    algorithm.setInputParameter("delta", 0.9) ;
    algorithm.setInputParameter("nr", 2) ;

    // Crossover operator 
    parameters = new HashMap() ;
    parameters.put("CR", 1.0) ;
    parameters.put("F", 0.5) ;
    crossover = CrossoverFactory.getCrossoverOperator("DifferentialEvolutionCrossover", parameters);                   
    
    // Mutation operator
    parameters = new HashMap() ;
    parameters.put("probability", 1.0/problem.getNumberOfVariables()) ;
    parameters.put("distributionIndex", 20.0) ;
    mutation = MutationFactory.getMutationOperator("PolynomialMutation", parameters);  
    
    
    
    
    
        parameters = new HashMap();
        parameters.put("probability", 0.9);
        parameters.put("distributionIndex", 20.0);
        crossover2 = CrossoverFactory.getCrossoverOperator("SBXCrossover", parameters);

        parameters = new HashMap();
        parameters.put("probability", 1.0 / problem.getNumberOfVariables());
        parameters.put("distributionIndex", 20.0);
        mutation2 = MutationFactory.getMutationOperator("PolynomialMutation", parameters);
        
        parameters = new HashMap();
        parameters.put("solutionSize", 100);
        parameters.put("numberOfCluster", 12);
        projection = new ProjectNCluster(parameters);
        
        
        parameters = new HashMap();
        parameters.put("problem", problem);
        parameters.put("improvementRounds", 1);
        parameters.put("mutation", mutation);
        localSearch = new MutationLocalSearch(parameters);

        // Selection Operator 
        parameters = null;
        selection = SelectionFactory.getSelectionOperator("BinaryTournament2", parameters);
    
    
    
    
    algorithm.addOperator("crossover",crossover);
    algorithm.addOperator("mutation",mutation);
    
        algorithm.addOperator("crossover2", crossover2);
        algorithm.addOperator("mutation2", mutation2);
        algorithm.addOperator("selection", selection);
        algorithm.addOperator("projection", projection);
        algorithm.addOperator("improvement", localSearch);
    
    // Execute the Algorithm
    long initTime = System.currentTimeMillis();
    SolutionSet population = algorithm.execute();
    long estimatedTime = System.currentTimeMillis() - initTime;
    
    // Result messages 
    logger_.info("Total execution time: "+estimatedTime + "ms");
    logger_.info("Objectives values have been writen to file FUN");
    population.printObjectivesToFile("FUN");
    logger_.info("Variables values have been writen to file VAR");
    population.printVariablesToFile("VAR");      
    
     indicators= new QualityIndicator(problem, "D:\\000_MyData\\OneDrive\\00_MyProjects\\000__Iyad\\Dr. Iyad__MOO Harmony Journal\\The code\\Jmetal\\Data_and_Results\\p_files\\"+"DTLZ1.3D.pf");
     algorithm.setInputParameter("indicators", indicators);
    
    if (indicators != null) {
      logger_.info("Quality indicators") ;
      logger_.info("Hypervolume: " + indicators.getHypervolume(population)) ;
      logger_.info("EPSILON    : " + indicators.getEpsilon(population)) ;
      logger_.info("GD         : " + indicators.getGD(population)) ;
      logger_.info("IGD        : " + indicators.getIGD(population)) ;
      logger_.info("Spread     : " + indicators.getSpread(population)) ;
    } // if          
  } //main
} // MOEAD_main
