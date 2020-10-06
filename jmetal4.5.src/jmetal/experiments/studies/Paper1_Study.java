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
import jmetal.experiments.settings.MOEAD_Settings;
import jmetal.experiments.settings.NSGAII_Settings;
import jmetal.experiments.util.Friedman;
import jmetal.util.JMException;

/**
 *
 * @author UsEr
 */
public class Paper1_Study extends Experiment {

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
                    || (paretoFrontFile_[problemIndex] == null)) {
                for (int i = 0; i < numberOfAlgorithms; i++) {
                    parameters[i].put("paretoFrontFile_", paretoFrontFile_[problemIndex]);
                }
            } // if


            algorithm[0] = new NSGAII_Settings(problemName).configure(parameters[0]);
//            algorithm[1] = new MOEAD_Settings(problemName).configure(parameters[1]);
//            algorithm[2] = new H_NSGAII_Settings(problemName).configure(parameters[2]);
//            algorithm[3] = new H_MOEAD_Settings(problemName).configure(parameters[3]);


        } catch (IllegalArgumentException ex) {
            Logger.getLogger(NSGAIIStudy.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(NSGAIIStudy.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JMException ex) {
            Logger.getLogger(NSGAIIStudy.class.getName()).log(Level.SEVERE, null, ex);
        }
    } // algorithmSettings

    public static void main(String[] args) throws JMException, IOException {
        Paper1_Study exp = new Paper1_Study(); // exp = experiment

        exp.experimentName_ = "PaperOneStudy";
        exp.algorithmNameList_ = new String[]{
            "NSGAII"};
        exp.problemList_ = new String[]{
              "Schaffer","Fonseca","Kursawe", "ZDT1","ZDT2", "ZDT3","ZDT4","ZDT6"};
        exp.paretoFrontFile_ = new String[]{
            "Schaffer.pf","Fonseca.pf","Kursawe.pf", "ZDT1.pf","ZDT2.pf", "ZDT3.pf", "ZDT4.pf" , "ZDT6.pf"};
        exp.indicatorList_ = new String[]{"GD", "SPREAD"};

        int numberOfAlgorithms = exp.algorithmNameList_.length;

        exp.experimentBaseDirectory_ = ""
                + exp.experimentName_;
        exp.paretoFrontDirectory_ = "";

        exp.algorithmSettings_ = new Settings[numberOfAlgorithms];

        exp.independentRuns_ = 10;

        exp.initExperiment();

        // Run the experiments
        int numberOfThreads;
        exp.runExperiment(numberOfThreads = 4);

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
        problems = new String[]{ "SCH","FON","KUR","ZDT1","ZDT2", "ZDT3", "DTLZ1"};

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

