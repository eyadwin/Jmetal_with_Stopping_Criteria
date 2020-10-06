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
import jmetal.experiments.settings.H_MOEAD_Settings;
import jmetal.experiments.settings.H_NSGAII_Settings;
import jmetal.experiments.settings.Harmony_Hybrid_MOEAD_Settings;
import jmetal.experiments.settings.Harmony_Hybrid_NSGAII_Settings;
import jmetal.experiments.settings.Harmony_MOEAD_Settings;
import jmetal.experiments.settings.Harmony_NSGAII_Settings;
import jmetal.experiments.settings.MOEAD_Settings;
import jmetal.experiments.settings.NSGAII_Settings;
import jmetal.experiments.util.Friedman;
import jmetal.operators.harmony.StoppingCriteria;
import jmetal.util.JMException;


public class All_Harmony extends Experiment {


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
                    || (paretoFrontFile_[problemIndex] == null)) {
                for (int i = 0; i < numberOfAlgorithms; i++) {
                    parameters[i].put("paretoFrontFile_", paretoFrontFile_[problemIndex]);
                }
            } // if


            algorithm[0] = new Harmony_NSGAII_Settings(problemName).configure(parameters[0]);
            algorithm[1] = new NSGAII_Settings(problemName).configure(parameters[1]);
            algorithm[2] = new Harmony_MOEAD_Settings(problemName).configure(parameters[2]);
            algorithm[3] = new MOEAD_Settings(problemName).configure(parameters[3]);
            algorithm[4] = new Harmony_Hybrid_NSGAII_Settings(problemName).configure(parameters[4]);
            algorithm[5] = new H_NSGAII_Settings(problemName).configure(parameters[5]);
            algorithm[6] = new Harmony_Hybrid_MOEAD_Settings(problemName).configure(parameters[6]);
            algorithm[7] = new H_MOEAD_Settings(problemName).configure(parameters[7]);

            
            
            
            algorithm[8] = new Harmony_NSGAII_Settings(problemName).configure(parameters[8]);
            algorithm[9] = new NSGAII_Settings(problemName).configure(parameters[9]);
            algorithm[10] = new Harmony_MOEAD_Settings(problemName).configure(parameters[10]);
            algorithm[11] = new MOEAD_Settings(problemName).configure(parameters[11]);
            algorithm[12] = new Harmony_Hybrid_NSGAII_Settings(problemName).configure(parameters[12]);
            algorithm[13] = new H_NSGAII_Settings(problemName).configure(parameters[13]);
            algorithm[14] = new Harmony_Hybrid_MOEAD_Settings(problemName).configure(parameters[14]);
            algorithm[15] = new H_MOEAD_Settings(problemName).configure(parameters[15]);

            HashMap<String , Object> params1[] = new HashMap[8];
            for (int i=0;i<params1.length;i++)
            {
                params1[i]=new HashMap<>();
                params1[i].put("step", 5);
                params1[i].put("stoppingCriteria",StoppingCriteria.MOVING_AVARAGE );

            }
            
            algorithm[8] = new Harmony_NSGAII_Settings(problemName).updateSettings(algorithm[8],params1[0]);
            algorithm[9] = new NSGAII_Settings(problemName).updateSettings(algorithm[9],params1[1]);
            algorithm[10] = new Harmony_MOEAD_Settings(problemName).updateSettings(algorithm[10],params1[2]);
            algorithm[11] = new MOEAD_Settings(problemName).updateSettings(algorithm[11],params1[3]);
            algorithm[12] = new Harmony_Hybrid_NSGAII_Settings(problemName).updateSettings(algorithm[12],params1[4]);
            algorithm[13] = new H_NSGAII_Settings(problemName).updateSettings(algorithm[13],params1[5]);
            algorithm[14] = new Harmony_Hybrid_MOEAD_Settings(problemName).updateSettings(algorithm[14],params1[6]);
            algorithm[15] = new H_MOEAD_Settings(problemName).updateSettings(algorithm[15],params1[7]);

            

            
            algorithm[16] = new Harmony_NSGAII_Settings(problemName).configure(parameters[16]);
            algorithm[17] = new NSGAII_Settings(problemName).configure(parameters[17]);
            algorithm[18] = new Harmony_MOEAD_Settings(problemName).configure(parameters[18]);
            algorithm[19] = new MOEAD_Settings(problemName).configure(parameters[19]);
            algorithm[20] = new Harmony_Hybrid_NSGAII_Settings(problemName).configure(parameters[20]);
            algorithm[21] = new H_NSGAII_Settings(problemName).configure(parameters[21]);
            algorithm[22] = new Harmony_Hybrid_MOEAD_Settings(problemName).configure(parameters[22]);
            algorithm[23] = new H_MOEAD_Settings(problemName).configure(parameters[23]);
            
            HashMap<String , Object> params2[] = new HashMap[8];
            for (int i=0;i<params2.length;i++)
            {
                params2[i]=new HashMap<>();
                params2[i].put("step", 5);
                params2[i].put("stoppingCriteria",StoppingCriteria.MGBM );
            }
            
            algorithm[16] = new Harmony_NSGAII_Settings(problemName).updateSettings(algorithm[16],params2[0]);
            algorithm[17] = new NSGAII_Settings(problemName).updateSettings(algorithm[17],params2[1]);
            algorithm[18] = new Harmony_MOEAD_Settings(problemName).updateSettings(algorithm[18],params2[2]);
            algorithm[19] = new MOEAD_Settings(problemName).updateSettings(algorithm[19],params2[3]);
            algorithm[20] = new Harmony_Hybrid_NSGAII_Settings(problemName).updateSettings(algorithm[20],params2[4]);
            algorithm[21] = new H_NSGAII_Settings(problemName).updateSettings(algorithm[21],params2[5]);
            algorithm[22] = new Harmony_Hybrid_MOEAD_Settings(problemName).updateSettings(algorithm[22],params2[6]);
            algorithm[23] = new H_MOEAD_Settings(problemName).updateSettings(algorithm[23],params2[7]);

            
            algorithm[24] = new Harmony_NSGAII_Settings(problemName).configure(parameters[24]);
            algorithm[25] = new NSGAII_Settings(problemName).configure(parameters[25]);
            algorithm[26] = new Harmony_MOEAD_Settings(problemName).configure(parameters[26]);
            algorithm[27] = new MOEAD_Settings(problemName).configure(parameters[27]);
            algorithm[28] = new Harmony_Hybrid_NSGAII_Settings(problemName).configure(parameters[28]);
            algorithm[29] = new H_NSGAII_Settings(problemName).configure(parameters[29]);
            algorithm[30] = new Harmony_Hybrid_MOEAD_Settings(problemName).configure(parameters[30]);
            algorithm[31] = new H_MOEAD_Settings(problemName).configure(parameters[31]);

            HashMap<String , Object> params3[] = new HashMap[8];
            for (int i=0;i<params2.length;i++)
            {
                params3[i]=new HashMap<>();
                params3[i].put("step", 5);
                params3[i].put("stoppingCriteria",StoppingCriteria.MGBM2 );

            }
            
            algorithm[24] = new Harmony_NSGAII_Settings(problemName).updateSettings(algorithm[24],params3[0]);
            algorithm[25] = new NSGAII_Settings(problemName).updateSettings(algorithm[25],params3[1]);
            algorithm[26] = new Harmony_MOEAD_Settings(problemName).updateSettings(algorithm[26],params3[2]);
            algorithm[27] = new MOEAD_Settings(problemName).updateSettings(algorithm[27],params3[3]);
            algorithm[28] = new Harmony_Hybrid_NSGAII_Settings(problemName).updateSettings(algorithm[28],params3[4]);
            algorithm[29] = new H_NSGAII_Settings(problemName).updateSettings(algorithm[29],params3[5]);
            algorithm[30] = new Harmony_Hybrid_MOEAD_Settings(problemName).updateSettings(algorithm[30],params3[6]);
            algorithm[31] = new H_MOEAD_Settings(problemName).updateSettings(algorithm[31],params3[7]);

            

        } catch (IllegalArgumentException ex) {
            Logger.getLogger(NSGAIIStudy.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(NSGAIIStudy.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JMException ex) {
            Logger.getLogger(NSGAIIStudy.class.getName()).log(Level.SEVERE, null, ex);
        }
    } // algorithmSettings

    public static void main(String[] args) throws JMException, IOException {
        All_Harmony exp = new All_Harmony(); // exp = experiment

        exp.algorithmNameList_ = new String[]{
            //"HarmonyNSGAII" , "NSGAII","HarmonyMOEAD","MOEAD","HarmonyHybridNSGAII","HybridNSGAII","HarmonyHybridMOEAD","HybridMOEAD"
            "Harmony_NSGAII_NONE",
            "NSGAII_NONE",
            "Harmony_MOEAD_NONE",
            "MOEAD_NONE",
            "Harmony_Hybrid_NSGAII_NONE",
            "H_NSGAII_NONE",
            "Harmony_Hybrid_MOEAD_NONE",
            "H_MOEAD_NONE",
            
            "Harmony_NSGAII_MOVING_AVG",
            "NSGAII_MOVING_AVG",
            "Harmony_MOEAD_MOVING_AVG",
            "MOEAD_MOVING_AVG",
            "Harmony_Hybrid_NSGAII_MOVING_AVG",
            "H_NSGAII_MOVING_AVG",
            "Harmony_Hybrid_MOEAD_MOVING_AVG",
            "H_MOEAD_MOVING_AVG",
            
            "Harmony_NSGAII_MGBM",
            "NSGAII_MGBM",
            "Harmony_MOEAD_MGBM",
            "MOEAD_MGBM",
            "Harmony_Hybrid_NSGAII_MGBM",
            "H_NSGAII_MGBM",
            "Harmony_Hybrid_MOEAD_MGBM",
            "H_MOEAD_MGBM",
                
            "Harmony_NSGAII_MGBM2",
            "NSGAII_MGBM2",
            "Harmony_MOEAD_MGBM2",
            "MOEAD_MGBM2",
            "Harmony_Hybrid_NSGAII_MGBM2",
            "H_NSGAII_MGBM2",
            "Harmony_Hybrid_MOEAD_MGBM2",
            "H_MOEAD_MGBM2"
        };
        
        exp.problemList_ = new String[]
            {
              /*"ZDT1","ZDT2", "ZDT3","ZDT4","ZDT6", 
              "DTLZ1", "DTLZ2", "DTLZ3", "DTLZ4", "DTLZ5", "DTLZ6", "DTLZ7"    ,*/
              "UF1","UF2","UF3","UF4","UF5","UF6","UF7","UF8","UF9","UF10"    
            };
        
//        exp.problemList_ = new String[]{ "ZDT4","ZDT1"};        
//        exp.paretoFrontFile_ = new String[]{"ZDT4.pf","ZDT1.pf"};

        exp.paretoFrontFile_ = new String[]
        {
            /* "ZDT1.pf","ZDT2.pf", "ZDT3.pf", "ZDT4.pf" , "ZDT6.pf",
            "DTLZ1.3D.pf", "DTLZ2.3D.pf", "DTLZ3.3D.pf", "DTLZ4.3D.pf", "DTLZ5.3D.pf", "DTLZ6.3D.pf", "DTLZ7.3D.pf" , */
            "UF1.pf","UF2.pf","UF3.pf","UF4.pf","UF5.pf","UF6.pf","UF7.pf","UF8.pf","UF9.pf","UF10.pf" 
        };
        
        String[] problems;
        problems = new String[]
        { 
            /*"ZDT1","ZDT2", "ZDT3","ZDT4","ZDT6", 
            "DTLZ1", "DTLZ2", "DTLZ3", "DTLZ4", "DTLZ5", "DTLZ6", "DTLZ7"   ,*/
            "UF1","UF2","UF3","UF4","UF5","UF6","UF7","UF8","UF9","UF10"   
        };
        for(String ss:problems)
        {
            System.out.println(ss+"\t");
        }

//,"KUR","ZDT1","ZDT2", "ZDT3", "DTLZ1"};

        
        //exp.indicatorList_ = new String[]{"IGD", "SPREAD","HV"};
        exp.indicatorList_ = new String[]{"IGD", "R2","HV"};

        int numberOfAlgorithms = exp.algorithmNameList_.length;

        //exp.experimentBaseDirectory_ = "E:\\My work\\My_results\\"
        //        + exp.experimentName_;
        //exp.paretoFrontDirectory_ = "E:\\My work\\My_results\\p_files";
        //exp.experimentName_ = "HarmonyStudyOne555";
        exp.experimentName_ = "Study_All_methods_V10";
        exp.experimentBaseDirectory_ = "000__Output\\000__Results__MOEAD_V10\\"+ exp.experimentName_;
        exp.paretoFrontDirectory_ = "000__P_files";
        exp.algorithmSettings_ = new Settings[numberOfAlgorithms];

        exp.independentRuns_ = 5;

        exp.initExperiment();

        // Run the experiments
        int numberOfThreads= 4;
        exp.runExperiment(numberOfThreads );

        exp.generateQualityIndicators();

        // Generate latex tables (comment this sentence is not desired)
        exp.generateLatexTables();

        // Configure the R scripts to be generated
        int rows;
        int columns;
        String prefix;

        rows = 2;
        columns = 3;
        prefix = new String("Problems");
        
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

