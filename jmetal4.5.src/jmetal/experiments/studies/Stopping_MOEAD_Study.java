/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jmetal.experiments.studies;

import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import jmetal.core.Algorithm;
import jmetal.experiments.Experiment;
import jmetal.experiments.Settings;
import jmetal.experiments.settings.*;
import jmetal.experiments.util.Friedman;
import jmetal.metaheuristics.moead.Harmony_Hybrid_MOEAD;
import jmetal.metaheuristics.nsgaII.Harmony_Hybrid_NSGAII;
import jmetal.operators.harmony.StoppingCriteria;
import jmetal.util.JMException;

/**
 *
 * @author UsEr
 */
public class Stopping_MOEAD_Study extends Experiment {

    /**
     * Configures the algorithms in each independent run
     *
     * @param problemName The problem to solve
     * @param problemIndex
     * @param algorithm Array containing the algorithms to run
     * @throws ClassNotFoundException
     */
    public synchronized void algorithmSettings(String problemName,
            int problemIndex,
            Algorithm[] algorithm)
            throws ClassNotFoundException {
        try {
                int numberOfAlgorithms = algorithmNameList_.length;

                HashMap[] parameters = new HashMap[numberOfAlgorithms];

                for (int i = 0; i < numberOfAlgorithms; i++) {
                    parameters[i] = new HashMap();
                } // for

                if (!paretoFrontFile_[problemIndex].equals("")) {
                    for (int i = 0; i < numberOfAlgorithms; i++) {
                        parameters[i].put("paretoFrontFile_", paretoFrontFile_[problemIndex]);
                    }
                } // if

    //            parameters[0].put("crossoverProbability_", 1.0);
    //            parameters[1].put("crossoverProbability_", 0.9);
    //            parameters[2].put("crossoverProbability_", 0.8);
    //            parameters[3].put("crossoverProbability_", 0.7);

                if ((!paretoFrontFile_[problemIndex].equals(""))
                        || (paretoFrontFile_[problemIndex] == null)) 
                {
                    for (int i = 0; i < numberOfAlgorithms; i++) 
                    {
                        parameters[i].put("paretoFrontFile_", paretoFrontFile_[problemIndex]);
                    }
                } // if

                
                HashMap<String , Object> params0 = new HashMap<>();
                params0.put("step", 5);
                params0.put("stoppingCriteria",StoppingCriteria.OCD_HV );/*
                
                
                HashMap<String , Object> params1 = new HashMap<>();
                params1.put("step", 5);
                params1.put("stoppingCriteria",StoppingCriteria.MOVING_AVARAGE );

                HashMap<String , Object> params2 = new HashMap<>();
                params2.put("step", 5);
                params2.put("stoppingCriteria",StoppingCriteria.MGBM );

                HashMap<String , Object> params3 = new HashMap<>();
                params3.put("step", 5);
                params3.put("stoppingCriteria",StoppingCriteria.MGBM2 );*/

            
            algorithm[0] = new MOEAD_Settings(problemName).configure(parameters[0]);/*
            algorithm[1] = new MOEAD_Settings(problemName).configure(parameters[1]);
            algorithm[2] = new MOEAD_Settings(problemName).configure(parameters[2]);
            algorithm[3] = new MOEAD_Settings(problemName).configure(parameters[3]);*/
            
            algorithm[0] = new MOEAD_Settings(problemName).updateSettings(algorithm[0], params0);/*
            algorithm[1] = new MOEAD_Settings(problemName).updateSettings(algorithm[1], params1);
            algorithm[2] = new MOEAD_Settings(problemName).updateSettings(algorithm[2], params2);
            algorithm[3] = new MOEAD_Settings(problemName).updateSettings(algorithm[3], params3);*/
            
            
            
            
            
//            algorithm[0] = new Harmony_Hybrid_MOEAD_Settings(problemName).configure(parameters[0]);
//            algorithm[1] = new Harmony_Hybrid_MOEAD_Settings(problemName).configure(parameters[1]);
//            algorithm[2] = new Harmony_Hybrid_NSGAII_Settings(problemName).configure(parameters[2]);
//            algorithm[3] = new Harmony_Hybrid_NSGAII_Settings(problemName).configure(parameters[3]);

//            algorithm[1] = new Harmony_NSGAII_Settings(problemName).configure(parameters[1]);
//            algorithm[2] = new H_NSGAII_Settings(problemName).configure(parameters[2]);
//            algorithm[3] = new H_MOEAD_Settings(problemName).configure(parameters[3]);
            
//            algorithm[2] = new Harmony_Hybrid_NSGAII_Settings(problemName).updateSettings(algorithm[2], params);
//            algorithm[4] = new Harmony_NSGAII_Settings(problemName).updateSettings(algorithm[4], params);


        } catch (IllegalArgumentException ex) {
            Logger.getLogger(NSGAIIStudy.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(NSGAIIStudy.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JMException ex) {
            Logger.getLogger(NSGAIIStudy.class.getName()).log(Level.SEVERE, null, ex);
        }
    } // algorithmSettings

    public static void main(String[] args) throws JMException, IOException {
        Stopping_MOEAD_Study exp = new Stopping_MOEAD_Study(); // exp = experiment

        exp.algorithmNameList_ = new String[]{"OCD_HV"/*,"MovingAverag" ,"MGBM","MGBM2"*/};
        
        exp.problemList_ = new String[]{
             //"ZDT1" ,"ZDT2", "ZDT3" ,"ZDT4","ZDT6"
               // , "DTLZ1", "DTLZ2", "DTLZ3", "DTLZ4", "DTLZ5", "DTLZ6", "DTLZ7",
                "UF1","UF2","UF3","UF4","UF5","UF6","UF7","UF8","UF9","UF10"
            };
                
        exp.paretoFrontFile_ = new String[]{
          //  "ZDT1.pf" ,"ZDT2.pf", "ZDT3.pf" , "ZDT4.pf" , "ZDT6.pf"
           //     ,"DTLZ1.3D.pf", "DTLZ2.3D.pf", "DTLZ3.3D.pf", "DTLZ4.3D.pf", "DTLZ5.3D.pf", "DTLZ6.3D.pf", "DTLZ7.3D.pf",
                "UF1.pf","UF2.pf","UF3.pf","UF4.pf","UF5.pf","UF6.pf","UF7.pf","UF8.pf","UF9.pf","UF10.pf"
            };

//        exp.problemList_ = new String[]{ "ZDT4","ZDT1"};        
//        exp.paretoFrontFile_ = new String[]{"ZDT4.pf","ZDT1.pf"};
        
        exp.indicatorList_ = new String[]{"IGD","R2", "HV"}; //,"SPREAD","EPSILON"};

        int numberOfAlgorithms = exp.algorithmNameList_.length;

        exp.experimentName_ = "StoppingMOEAD_V11";
        exp.experimentBaseDirectory_ = "000__Output\\000__Results__MOEAD_V11\\"+ exp.experimentName_;
        exp.paretoFrontDirectory_ = "000__P_files";
        
        exp.algorithmSettings_ = new Settings[numberOfAlgorithms];

        exp.independentRuns_ = 1;

        exp.initExperiment();

        // Run the experiments
        int numberOfThreads= 1;
        exp.runExperiment( numberOfThreads );
        exp.generateQualityIndicators();

        // Generate latex tables (comment this sentence is not desired)
        exp.generateLatexTables();

        // Configure the R scripts to be generated
        int rows;
        int columns;
        String prefix;
        String[] problems;

        rows = 2;
        columns = 3;
        prefix = new String("Problems");
        problems = new String[]{//"ZDT1" ,"ZDT2", "ZDT3","ZDT4","ZDT6"
                                //, "DTLZ1", "DTLZ2", "DTLZ3", "DTLZ4", "DTLZ5", "DTLZ6", "DTLZ7",
                                "UF1","UF2","UF3","UF4","UF5","UF6","UF7","UF8","UF9","UF10"
        };

        boolean notch;
        exp.generateRBoxplotScripts(rows, columns, problems, prefix, notch = true, exp);
        exp.generateRWilcoxonScripts(problems, prefix, exp);

        // Applying Friedman test
        Friedman test = new Friedman(exp);
//        test.executeTest("EPSILON");
//        test.executeTest("HV");
//        test.executeTest("SPREAD");
    } // main
} // Final_Study