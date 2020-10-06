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
import jmetal.experiments.settings.Harmony_Hybrid_MOEAD_Settings;
import jmetal.experiments.settings.Harmony_MOEAD_Settings;
import jmetal.experiments.settings.MOEAD_Settings;
import jmetal.experiments.util.Friedman;
import jmetal.operators.harmony.StoppingCriteria;
import jmetal.util.JMException;


public class Old_All_MOEAD_Methods extends Experiment {


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


            if ((!paretoFrontFile_[problemIndex].equals(""))
                    || (paretoFrontFile_[problemIndex] == null)) {
                for (int i = 0; i < numberOfAlgorithms; i++) {
                    parameters[i].put("paretoFrontFile_", paretoFrontFile_[problemIndex]);
                }
            } // if

            //MOEAD
            algorithm[0] = new MOEAD_Settings(problemName).configure(parameters[0]);
            algorithm[1] = new MOEAD_Settings(problemName).configure(parameters[1]);
            algorithm[2] = new MOEAD_Settings(problemName).configure(parameters[2]);
            algorithm[3] = new MOEAD_Settings(problemName).configure(parameters[3]);
            algorithm[4] = new MOEAD_Settings(problemName).configure(parameters[4]);
            //Harmony_MOEAD
            algorithm[5] = new Harmony_MOEAD_Settings(problemName).configure(parameters[5]);
            algorithm[6] = new Harmony_MOEAD_Settings(problemName).configure(parameters[6]);
            algorithm[7] = new Harmony_MOEAD_Settings(problemName).configure(parameters[7]);
            algorithm[8] = new Harmony_MOEAD_Settings(problemName).configure(parameters[8]);
            algorithm[9] = new Harmony_MOEAD_Settings(problemName).configure(parameters[9]);
            //Hybrid_MOEAD
            /*
            algorithm[10] = new H_MOEAD_Settings(problemName).configure(parameters[10]);
            algorithm[11] = new H_MOEAD_Settings(problemName).configure(parameters[11]);
            algorithm[12] = new H_MOEAD_Settings(problemName).configure(parameters[12]);
            algorithm[13] = new H_MOEAD_Settings(problemName).configure(parameters[13]);
            algorithm[14] = new H_MOEAD_Settings(problemName).configure(parameters[14]);
            */
            //Harmony_Hybrid_MOEAD
            algorithm[10] = new Harmony_Hybrid_MOEAD_Settings(problemName).configure(parameters[10]);
            algorithm[11] = new Harmony_Hybrid_MOEAD_Settings(problemName).configure(parameters[11]);
            algorithm[12] = new Harmony_Hybrid_MOEAD_Settings(problemName).configure(parameters[12]);
            algorithm[13] = new Harmony_Hybrid_MOEAD_Settings(problemName).configure(parameters[13]);
            algorithm[14] = new Harmony_Hybrid_MOEAD_Settings(problemName).configure(parameters[14]);
                    

            HashMap<String , Object> params[][] = new HashMap[4][4];
            
            for (int i=0;i<params[0].length;i++)
            {
                params[0][i]=new HashMap<>();
                params[0][i].put("step", 5);
                params[0][i].put("stoppingCriteria",StoppingCriteria.MOVING_AVARAGE );
                
                params[1][i]=new HashMap<>();
                params[1][i].put("step", 5);
                params[1][i].put("stoppingCriteria",StoppingCriteria.MGBM );
                
                params[2][i]=new HashMap<>();
                params[2][i].put("step", 5);
                params[2][i].put("stoppingCriteria",StoppingCriteria.MGBM2 );
                
                params[3][i]=new HashMap<>();
                params[3][i].put("step", 5);
                params[3][i].put("stoppingCriteria",StoppingCriteria.OCD_HV );

            }
            
            for (int i = 0,X=0; i < params[0].length-1; i++,X+=5) 
            {
                algorithm[1+X] = new MOEAD_Settings(problemName).updateSettings(algorithm[1+X],params[0][i]);
                algorithm[2+X] = new Harmony_MOEAD_Settings(problemName).updateSettings(algorithm[2+X],params[1][i]);
                //algorithm[3+X] = new H_MOEAD_Settings(problemName).updateSettings(algorithm[3+X],params[2][i]);
                algorithm[3+X] = new Harmony_Hybrid_MOEAD_Settings(problemName).updateSettings(algorithm[3+X],params[2][i]);
            }
            

        } catch (IllegalArgumentException ex) {
            Logger.getLogger(NSGAIIStudy.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(NSGAIIStudy.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JMException ex) {
            Logger.getLogger(NSGAIIStudy.class.getName()).log(Level.SEVERE, null, ex);
        }
    } // algorithmSettings

    public static void main(String[] args) throws JMException, IOException {
        All_MOEAD_Methods exp = new All_MOEAD_Methods(); // exp = experiment

        exp.algorithmNameList_ = new String[]{
            "MOEAD_NONE",
            "MOEAD_MOVING_AVG",
            "MOEAD_MGBM",
            "MOEAD_MGBM2",
            "MOEAD_OCD_HV",

            "Harmony_MOEAD_NONE",
            "Harmony_MOEAD_MOVING_AVG",
            "Harmony_MOEAD_MGBM",
            "Harmony_MOEAD_MGBM2",
            "Harmony_MOEAD_OCD_HV",

           /* "Hybrid_MOEAD_NONE",
            "Hybrid_MOEAD_MOVING_AVG",
            "Hybrid_MOEAD_MGBM",
            "Hybrid_MOEAD_MGBM2",
            "Hybrid_MOEAD_OCD_HV",*/

            "Harmony_Hybrid_MOEAD_NONE",
            "Harmony_Hybrid_MOEAD_MOVING_AVG",
            "Harmony_Hybrid_MOEAD_MGBM",
            "Harmony_Hybrid_MOEAD_MGBM2",
            "Harmony_Hybrid_MOEAD_OCD_HV"
        };
        
        exp.problemList_ = new String[]
            {
              //"ZDT1","ZDT2", "ZDT3" ,"ZDT4","ZDT6", 
              //"DTLZ1", "DTLZ2", "DTLZ3", "DTLZ4", "DTLZ5", "DTLZ6", "DTLZ7"    ,
               "UF1","UF2" ,"UF3","UF4","UF5","UF6","UF7","UF8","UF9","UF10"    
            };
        

        exp.paretoFrontFile_ = new String[]
        {
            //"ZDT1.pf","ZDT2.pf", "ZDT3.pf", "ZDT4.pf" , "ZDT6.pf",
            //"DTLZ1.3D.pf", "DTLZ2.3D.pf", "DTLZ3.3D.pf", "DTLZ4.3D.pf", "DTLZ5.3D.pf", "DTLZ6.3D.pf", "DTLZ7.3D.pf" , 
            "UF1.pf","UF2.pf" ,"UF3.pf","UF4.pf","UF5.pf","UF6.pf","UF7.pf","UF8.pf","UF9.pf","UF10.pf" 
        };
        
        String[] problems;
        problems = new String[]
        { 
            //"ZDT1","ZDT2", "ZDT3","ZDT4","ZDT6", 
            //"DTLZ1", "DTLZ2", "DTLZ3", "DTLZ4", "DTLZ5", "DTLZ6", "DTLZ7"   ,
            "UF1","UF2" ,"UF3","UF4","UF5","UF6","UF7","UF8","UF9","UF10"   
        };
        for(String ss:problems)
        {
            System.out.println(ss+"\t");
        }

        
        //exp.indicatorList_ = new String[]{"IGD", "SPREAD","HV"};
        exp.indicatorList_ = new String[]{"IGD", "R2","HV"};

        int numberOfAlgorithms = exp.algorithmNameList_.length;

        //exp.experimentBaseDirectory_ = "E:\\My work\\My_results\\"
        //        + exp.experimentName_;
        //exp.paretoFrontDirectory_ = "E:\\My work\\My_results\\p_files";
        //exp.experimentName_ = "HarmonyStudyOne555";
        exp.experimentName_ = "Study_All_methods_V11";
        exp.experimentBaseDirectory_ = "000__Output\\000__Results__MOEAD_V11\\"+ exp.experimentName_;
        exp.paretoFrontDirectory_ = "000__P_files";
        exp.algorithmSettings_ = new Settings[numberOfAlgorithms];

        exp.independentRuns_ = 10;

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

