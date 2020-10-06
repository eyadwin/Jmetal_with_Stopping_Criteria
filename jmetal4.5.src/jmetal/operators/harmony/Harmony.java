/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jmetal.operators.harmony;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import jmetal.core.Algorithm;
import jmetal.core.Operator;
import jmetal.core.Problem;
import jmetal.core.SolutionSet;
import jmetal.problems.DTLZ.DTLZ1;
import jmetal.problems.DTLZ.DTLZ2;
import jmetal.problems.DTLZ.DTLZ3;
import jmetal.problems.DTLZ.DTLZ4;
import jmetal.problems.DTLZ.DTLZ5;
import jmetal.problems.DTLZ.DTLZ6;
import jmetal.problems.DTLZ.DTLZ7;
import jmetal.problems.ZDT.ZDT1;
import jmetal.problems.ZDT.ZDT2;
import jmetal.problems.ZDT.ZDT3;
import jmetal.problems.ZDT.ZDT4;
import jmetal.problems.ZDT.ZDT6;
import jmetal.problems.cec2009Competition.UF1;
import jmetal.problems.cec2009Competition.UF10;
import jmetal.problems.cec2009Competition.UF2;
import jmetal.problems.cec2009Competition.UF3;
import jmetal.problems.cec2009Competition.UF4;
import jmetal.problems.cec2009Competition.UF5;
import jmetal.problems.cec2009Competition.UF6;
import jmetal.problems.cec2009Competition.UF7;
import jmetal.problems.cec2009Competition.UF8;
import jmetal.problems.cec2009Competition.UF9;
import jmetal.qualityIndicator.QualityIndicator;

/**
 *
 * @author UsEr
 */
public abstract class Harmony extends Operator {

    public Harmony(HashMap<String, Object> parameters) {
        super(parameters);
    }

    public static void writeOnFile(String text, String fileName) {
        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter( /*"000__Output\\"+*/ fileName + ".txt", true)))) {
            out.println(text);
        } catch (IOException e) {
            //exception handling left as an exercise for the reader
            System.err.println("EEEERRORRRR     " + e.getMessage());
            //new File("C:\\Directory1").mkdir();
        }
    }
    
    
        public static void functionWrite(Problem problem, Algorithm algorithm, String directory, SolutionSet population, int index) {

        String className = "";
        if (problem.getClass() == ZDT1.class) {
            className = "ZDT1.pf";
        }

        if (problem.getClass() == ZDT2.class) {
            className = "ZDT2.pf";
        }

        if (problem.getClass() == ZDT3.class) {
            className = "ZDT3.pf";
        }

        if (problem.getClass() == ZDT4.class) {
            className = "ZDT4.pf";
        }
        if (problem.getClass() == ZDT6.class) {
            className = "ZDT6.pf";
        }
        if (problem.getClass() == DTLZ1.class) {
            className = "DTLZ1.3D.pf";
        }
        if (problem.getClass() == DTLZ2.class) {
            className = "DTLZ2.3D.pf";
        }
        if (problem.getClass() == DTLZ3.class) {
            className = "DTLZ3.3D.pf";
        }
        if (problem.getClass() == DTLZ4.class) {
            className = "DTLZ4.3D.pf";
        }
        if (problem.getClass() == DTLZ5.class) {
            className = "DTLZ5.3D.pf";
        }
        if (problem.getClass() == DTLZ6.class) {
            className = "DTLZ6.3D.pf";
        }
        if (problem.getClass() == DTLZ7.class) {
            className = "DTLZ7.3D.pf";
        }
        if (problem.getClass() == UF1.class) {
            className = "UF1.pf";
        }
        if (problem.getClass() == UF2.class) {
            className = "UF2.pf";
        }
        if (problem.getClass() == UF3.class) {
            className = "UF3.pf";
        }
        if (problem.getClass() == UF4.class) {
            className = "UF4.pf";
        }
        if (problem.getClass() == UF5.class) {
            className = "UF5.pf";
        }
        if (problem.getClass() == UF6.class) {
            className = "UF6.pf";
        }
        if (problem.getClass() == UF7.class) {
            className = "UF7.pf";
        }
        if (problem.getClass() == UF8.class) {
            className = "UF8.pf";
        }
        if (problem.getClass() == UF9.class) {
            className = "UF9.pf";
        }
        if (problem.getClass() == UF10.class) {
            className = "UF10.pf";
        }
        ///////////////////////////////////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////////////////////////
        
        QualityIndicator indicators = new QualityIndicator(problem, directory+"/" + className);
        algorithm.setInputParameter("indicators", indicators);
        if (indicators != null) {
//      System.out.println("Quality indicators") ;
//      System.out.println("Hypervolume: " + indicators.getHypervolume(population)) ;
//      System.out.println("GD         : " + indicators.getGD(population)) ;
            Path path = Paths.get(directory+"\\..\\000__Output\\IGD\\" + algorithm.getClass().getName());
            if (!Files.exists( path )){
                try{
                new File(directory+"\\..\\000__Output\\IGD\\" + algorithm.getClass().getName()).mkdirs();
                }
                catch(Exception ex){
                   System.err.println(ex.getMessage()) ;
                }
               
            }
       
            Harmony.writeOnFile(index + ", " + indicators.getIGD(population), directory+"\\..\\000__Output\\IGD\\" + algorithm.getClass().getName() + "\\" + className);

        }

    }
    
//        public static void printEachItration(String text, String fileName) {
//        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(fileName + ".txt", true)))) {
//            out.println("\n" + text);
//        } catch (IOException e) {
//            //exception handling left as an exercise for the reader
//        }
//    }
}
