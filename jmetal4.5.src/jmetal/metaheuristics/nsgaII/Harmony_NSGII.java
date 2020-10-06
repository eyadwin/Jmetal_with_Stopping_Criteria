/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jmetal.metaheuristics.nsgaII;

import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;
import jmetal.core.*;
import jmetal.operators.harmony.HarmonyUnit;
import jmetal.qualityIndicator.QualityIndicator;
import jmetal.util.Distance;
import jmetal.util.JMException;
import jmetal.util.Ranking;
import jmetal.util.comparators.CrowdingComparator;
import jmetal.util.wrapper.XReal;
import java.util.Arrays;
import jmetal.operators.harmony.Harmony;
import jmetal.operators.harmony.StoppingCriteria;
import jmetal.problems.DTLZ.*;
import jmetal.problems.ZDT.ZDT1;
import jmetal.problems.ZDT.ZDT2;
import jmetal.problems.ZDT.ZDT3;
import jmetal.problems.ZDT.ZDT4;
import jmetal.problems.ZDT.ZDT6;
import jmetal.problems.cec2009Competition.*;
import jmetal.util.asf.AchievementScalarizing;
import jmetal.util.asf.MGBM;
import jmetal.util.asf.MGBM2;
import jmetal.util.asf.OCD_HV;
import jmetal.util.asf.OCD_HV_Param;

/**
 *
 * @author UsEr
 */
public class Harmony_NSGII extends Algorithm {

    SolutionSet generation1, generation2;
    double KalmanGain, Z, S;
    String Dir = "000__P_files";
    int its = 0;

    
    int nPop=100;    
    int OCD_nPreGen=10;
    int OCD_IterationNo=0;
    SolutionSet PF[]=new SolutionSet[OCD_nPreGen];
    OCD_HV_Param ocd_hv_param=new OCD_HV_Param();
    String pfName=problem_.getName()+".pf";
    QualityIndicator indicators = new QualityIndicator(problem_, Dir+"/" + pfName);

    
    /**
     * Constructor
     *
     * @param problem Problem to solve
     */
    public Harmony_NSGII(Problem problem) {
        super(problem);
    } // NSGAII

    /**
     * Runs the NSGA-II algorithm.
     *
     * @return a <code>SolutionSet</code> that is a set of non dominated
     * solutions as a result of the algorithm execution
     * @throws JMException
     */
    public SolutionSet execute() throws JMException, ClassNotFoundException {

        int populationSize;
        int maxEvaluations;
        int evaluations;
        StoppingCriteria stoppingCriteria = StoppingCriteria.NONE;
        int step;
        double ASF_val[];
        int counter = 0;
        QualityIndicator indicators; // QualityIndicator object
        int requiredEvaluations; // Use in the example of use of the
        // indicators object (see below)

        SolutionSet population;
        SolutionSet offspringPopulation;
        SolutionSet union;


        Operator memoryConOperator;
        Operator randomConOperator;
        Operator pitchAjdOperator;

        Distance distance = new Distance();
        step = 0;
        ASF_val = new double[step];



        /////////////////
        //////////////    NEW CODE
        ////////////////
        try 
        {
            stoppingCriteria = (StoppingCriteria) this.getInputParameter("stoppingCriteria"); //***
            if(stoppingCriteria != StoppingCriteria.NONE)
            {
                step = ((Integer) this.getInputParameter("step")).intValue();                      //***
                ASF_val = new double[step];
            }
        } catch (Exception e) {
            System.out.println("[[NO Stopping Criteria are used]]");
        }
        //////////////
        ///////////////
        /////////////
            



            





        //Read the parameters
        populationSize = ((Integer) getInputParameter("populationSize")).intValue();
        maxEvaluations = ((Integer) getInputParameter("maxEvaluations")).intValue();
        indicators = (QualityIndicator) getInputParameter("indicators");
///////////////////////////////////////////////////////////
/*        if (problem_.getClass() == ZDT1.class || problem_.getClass() == ZDT2.class
                || problem_.getClass() == ZDT3.class || problem_.getClass() == ZDT4.class
                || problem_.getClass() == ZDT6.class || problem_.getClass() == DTLZ1.class
                || problem_.getClass() == DTLZ7.class) {
            maxEvaluations = 20000;
        }

        if (problem_.getClass() == DTLZ2.class || problem_.getClass() == DTLZ4.class
                || problem_.getClass() == DTLZ5.class) {
            maxEvaluations = 5000;
        }
        if (problem_.getClass() == DTLZ3.class) {
            maxEvaluations = 30000;
        }
        if (problem_.getClass() == DTLZ6.class) {
            maxEvaluations = 15000;
        }
        if (problem_.getClass() == UF1.class || problem_.getClass() == UF2.class
                || problem_.getClass() == UF3.class || problem_.getClass() == UF4.class
                || problem_.getClass() == UF5.class || problem_.getClass() == UF6.class
                || problem_.getClass() == UF7.class || problem_.getClass() == UF8.class
                || problem_.getClass() == UF9.class || problem_.getClass() == UF10.class) {
            maxEvaluations = 50000;
        }

        /////////////////////////////////////////////////////////    
        //************************************************************************************
        if (problem_.getClass() == DTLZ1.class || problem_.getClass() == DTLZ2.class
                || problem_.getClass() == DTLZ3.class || problem_.getClass() == DTLZ4.class
                || problem_.getClass() == DTLZ5.class || problem_.getClass() == DTLZ6.class
                || problem_.getClass() == DTLZ7.class || problem_.getClass() == UF8.class
                || problem_.getClass() == UF9.class || problem_.getClass() == UF10.class) {


            populationSize = 200;

        }
        */
//************************************************************************************
        //Initialize the variables
        population = new SolutionSet(populationSize);
        evaluations = 0;

        requiredEvaluations = 0;

        //Read the operators
//        mutationOperator = operators_.get("mutation");
//        crossoverOperator = operators_.get("crossover");
//        selectionOperator = operators_.get("selection");



        memoryConOperator = operators_.get("memoryConOperator");
        randomConOperator = operators_.get("randomConOperator");
        pitchAjdOperator = operators_.get("pitchAjdOperator");


        // Create the initial solutionSet
        Solution newSolution;
        for (int i = 0; i < populationSize; i++) {
            newSolution = new Solution(problem_);
            problem_.evaluate(newSolution);
            problem_.evaluateConstraints(newSolution);
            evaluations++;
            population.add(newSolution);
        } //for       


        /**
         * *****************************
         */
        // 1.  hUnit  = MemoryCon.exec(pop)
        // 2.  RandomCon.exec(hUnit)
        // 3.  newSolution = pitAdj.exec(hUnit)
        // 4.  pop.replace( pop.getWorst(), newSolution )
        // Generations 
        while (evaluations < maxEvaluations) {

            // Create the offSpring solutionSet      
            offspringPopulation = new SolutionSet(populationSize);
            Solution newHarmonySolution;

//            for (int i = 0; i < population.size(); i++) {
//                offspringPopulation.add(population.get(i));
//            }


            population = sort(population);
            int i = 0;
            while (i < populationSize) {

                HarmonyUnit hUnit = (HarmonyUnit) memoryConOperator.execute(population);
                hUnit = (HarmonyUnit) randomConOperator.execute(hUnit);
                newHarmonySolution = (Solution) pitchAjdOperator.execute(hUnit);
                problem_.evaluate(newHarmonySolution);
                problem_.evaluateConstraints(newHarmonySolution);
                int lasPos = population.size() - 1;


                if (isBetter(newHarmonySolution, population.get(/*0)))//*/(int) (populationSize * 0.1)))) {

                    offspringPopulation.add(newHarmonySolution);
                    i++;
                    evaluations++;
                }

            }


            /**
             * *************************************************
             */
            // Create the solutionSet union of solutionSet and offSpring
            union = ((SolutionSet) population).union(offspringPopulation);

            // Ranking the union
            Ranking ranking = new Ranking(union);

            int remain = populationSize;
            int index = 0;
            SolutionSet front = null;
            population.clear();

            // Obtain the next front
            front = ranking.getSubfront(index);

            while ((remain > 0) && (remain >= front.size())) {
                //Assign crowding distance to individuals
                distance.crowdingDistanceAssignment(front, problem_.getNumberOfObjectives());
                //Add the individuals of this front
                for (int k = 0; k < front.size(); k++) {
                    population.add(front.get(k));
                } // for

                //Decrement remain
                remain = remain - front.size();

                //Obtain the next front
                index++;
                if (remain > 0) {
                    front = ranking.getSubfront(index);
                } // if        


            } // while

            // Remain is less than front(index).size, insert only the best one
            if (remain > 0) {  // front contains individuals to insert                        
                distance.crowdingDistanceAssignment(front, problem_.getNumberOfObjectives());
                front.sort(new CrowdingComparator());
                for (int k = 0; k < remain; k++) {
                    population.add(front.get(k));
                } // for

                remain = 0;
            } // if                               

            // This piece of code shows how to use the indicator object into the code
            // of NSGA-II. In particular, it finds the number of evaluations required
            // by the algorithm to obtain a Pareto front with a hypervolume higher
            // than the hypervolume of the true Pareto front.
            if ((indicators != null)
                    && (requiredEvaluations == 0)) {
                double HV = indicators.getHypervolume(population);
                if (HV >= (0.98 * indicators.getTrueParetoFrontHypervolume())) {
                    requiredEvaluations = evaluations;
                } // if
            } // if
            if (evaluations % populationSize == 0) {
                its++;
                Harmony.functionWrite(problem_, this, Dir, population, its);
            }

                    if (stoppingCriteria == StoppingCriteria.OCD_HV) 
            {
                if(evaluations % populationSize == 0)
                {
                    //if(OCD_IterationNo%OCD_nPreGen==0)
                    {
                        System.out.println("OCD_HV IterNo="+OCD_IterationNo);
                        Ranking rankingTemp_ = new Ranking(population);
                        int PF_Idx=(int)OCD_IterationNo; //  /OCD_nPreGen;

                        if (PF_Idx < OCD_nPreGen)
                        {
                            PF[PF_Idx]=rankingTemp_.getSubfront(0);                        
                        }
                        else
                        {
                            for (int idx = 1; idx<OCD_nPreGen;idx++)
                            {
                                PF[idx-1] = PF[idx];
                            }
                            PF[OCD_nPreGen-1]=rankingTemp_.getSubfront(0);                            
                        }

                        if (PF_Idx >= OCD_nPreGen-1)
                        {
                            OCD_HV.getOCD_HC(indicators, PF, ocd_hv_param);
                            //System.out.println(problem_.getName()+" EvalNo="+evaluations_+"  IterNo = "+OCD_IterationNo+"  OCD_HV pChi2 = "+ocd_hv_param.pChi2[0]);
                        }




                        if (ocd_hv_param.termCrit[0] && ocd_hv_param.termCrit[1]) 
                        {
                            System.out.println("[" + problem_.getName() + " ] >>>>>> OCD_HV STOPED AT [ evalNo:" + evaluations + " ] [ IterNo:"+OCD_IterationNo +"  ] <<<<<<");
                            Harmony.writeOnFile(problem_.getName() + " : " + evaluations, "000__Output\\"+getClass().getName() + "  " + stoppingCriteria);
                            evaluations = maxEvaluations;
                        }
                        else if (evaluations == maxEvaluations) {
                        Harmony.writeOnFile(problem_.getName() + " : " + evaluations, "000__Output\\"+getClass().getName() + "  " + stoppingCriteria);
                    }

                    }

                    OCD_IterationNo++;
                }
            }
            else if (stoppingCriteria == StoppingCriteria.MOVING_AVARAGE) 
            {

                Ranking rankingTemp_ = new Ranking(population);
                SolutionSet frontTemp_ = rankingTemp_.getSubfront(0);
                Distance distance2 = new Distance();
                distance2.crowdingDistanceAssignment(frontTemp_, problem_.getNumberOfObjectives());
                frontTemp_.sort(new CrowdingComparator());

                if (evaluations % (populationSize * step) == 0 && evaluations != populationSize) {

                    double v = new AchievementScalarizing().getAvarageASF(ASF_val);


                    if (v <= (5.0 / 10000.0)) {
                        //   System.out.println("[" + problem_.getName() + " ] >>>>>>  STOPED AT [ " + evaluations + " ]");
                        System.out.println("[" + problem_.getName() + " ] >>>>>>  STOPED AT [ " + evaluations + " ] and avarage = " + new AchievementScalarizing().getAvarageASF(ASF_val) + " <<<<<<");
                        Harmony.writeOnFile(problem_.getName() + " : " + evaluations, getClass().getName() + "  " + stoppingCriteria);
                        evaluations = maxEvaluations;
                    }

//                        System.out.println("00                       " + evaluations + "              " + v);


                }

                if (evaluations == (maxEvaluations - populationSize)) {
                    Harmony.writeOnFile(problem_.getName() + " : " + maxEvaluations, getClass().getName() + "  " + stoppingCriteria);
                }


                if (evaluations % populationSize == 0) {
                    if (frontTemp_.size() > 0) {
                        ASF_val[counter % step] = new AchievementScalarizing().getBestASF(frontTemp_);
                    }
                    counter++;
                }
                //////////////////////////////////////////////////////////////////////////////
                ///////////////////////////////////////////////////////////////////////////////
            } 
            else if (stoppingCriteria == StoppingCriteria.MGBM) 
            {
                double ss = 0;
                if (evaluations == populationSize * 2) {
                    S = 1;
                    KalmanGain = 0;
                    generation2 = new Ranking(population).getSubfront(0);
                    Z = 1;
                }

                if (evaluations >= ((populationSize * 3))) {


                    if (evaluations % populationSize == 0) {

                        generation1 = generation2;
                        generation2 = new Ranking(population).getSubfront(0);

                        ss = S;
                        KalmanGain = new MGBM().getKalmanGain(Z, S);
                        if (generation2.size() > 0 && generation1.size() > 0) {
                            Z = new MGBM().getActualMDR(generation1, generation2);
                            S = new MGBM().getPredectedMDR(S, Z, KalmanGain);
                        }



                        //                     System.out.println("00                       " + evaluations + "              " + S);
                        if (evaluations == (maxEvaluations - populationSize)) {
                            Harmony.writeOnFile(problem_.getName() + " : " + maxEvaluations, getClass().getName() + "  " + stoppingCriteria);
                        }
                        if (ss != 1) {
                            if (Math.abs(S - ss) < (1.0 / 100000)) {
//                        if (S <=0){
                                System.out.println("[" + problem_.getName() + " ] >>>>>>  STOPED AT [ " + evaluations + " ] and avarage = " + new AchievementScalarizing().getAvarageASF(ASF_val) + " <<<<<<");
                                Harmony.writeOnFile(problem_.getName() + " : " + evaluations, getClass().getName() + "  " + stoppingCriteria);
                                evaluations = maxEvaluations;
                            }
                        }

                    }

                }
            }
            else if (stoppingCriteria == StoppingCriteria.MGBM2) 
                {
                    double ss = 0;
                    if (evaluations == populationSize * 2) {
                        S = 1;
                        KalmanGain = 0;
                        generation2 = new Ranking(population).getSubfront(0);
                        Z = 1;
                    }

                    if (evaluations >= ((populationSize * 3))) {
                        if (evaluations % populationSize == 0) 
                        {
                            generation1 = generation2;
                            generation2 = new Ranking(population).getSubfront(0);

                            ss = S;
                            KalmanGain = new MGBM2().getKalmanGain(Z, S);
                            if (/*generation2.size() > 0 &&*/ generation1.size() > 0) {
                                Z = new MGBM2().getFHI(/*generation1 ,*/ generation2);//Eq. 15
                                S = new MGBM2().getPredectedMDR(S, Z, KalmanGain);//Eq. 17
                            }
//                              System.out.println("00                       " + evaluations_ + "              " + S);
                            if (evaluations == maxEvaluations) {
                                Harmony.writeOnFile(problem_.getName() + " : " + evaluations, "000__Output\\"+getClass().getName() + "  " + stoppingCriteria);
                            }
                            if (ss != 1) {
                                if (Math.abs(S - ss) < (1.0 / 100000)) {
                                    System.out.println("[" + problem_.getName() + " ] >>>>>>  MGBM2 STOPED AT [ " + evaluations + " ] and avarage = " + new AchievementScalarizing().getAvarageASF(ASF_val) + " <<<<<<");
                                    Harmony.writeOnFile(problem_.getName() + " : " + evaluations, "000__Output\\"+getClass().getName() + "  " + stoppingCriteria);
                                    evaluations = maxEvaluations;
                                }
                            }

                        }

                    }
                }


        } // while

        // Return as output parameter the required evaluations
        setOutputParameter("evaluations", requiredEvaluations);

        // Return the first non-dominated front
        Ranking ranking = new Ranking(population);
        ranking.getSubfront(0).printFeasibleFUN("FUN_NSGAII");

        return ranking.getSubfront(0);
    } // execute

    SolutionSet sort(SolutionSet offspringPop) {

        Comparator crowdingComparator_ =
                new CrowdingComparator();
        Distance distance_ = new Distance();
        int populationSize = offspringPop.size();
        SolutionSet result = new SolutionSet(populationSize);

        //->Ranking the union
        Ranking ranking = new Ranking(offspringPop);

        int remain = populationSize;
        int index = 0;
        SolutionSet front = null;
        //population.clear();

        //-> Obtain the next front
        front = ranking.getSubfront(index);

        while ((remain > 0) && (remain >= front.size())) {
            //Asign crowding distance to individuals
            distance_.crowdingDistanceAssignment(front, problem_.getNumberOfObjectives());
            //Add the individuals of this front
            for (int k = 0; k < front.size(); k++) {
                result.add(front.get(k));
            } // for

            //Decrement remaint
            remain = remain - front.size();

            //Obtain the next front
            index++;
            if (remain > 0) {
                front = ranking.getSubfront(index);
            } // if        
        } // while

        //-> remain is less than front(index).size, insert only the best one
        if (remain > 0) {  // front containt individuals to insert                        
            distance_.crowdingDistanceAssignment(front, problem_.getNumberOfObjectives());
            front.sort(crowdingComparator_);
            for (int k = 0; k < remain; k++) {
                result.add(front.get(k));
            } // for

            remain = 0;
        } // if

        return result;


    }

    boolean isEqual(Solution sol1, Solution sol2) {

        XReal x1 = new XReal(sol1);
        XReal x2 = new XReal(sol2);
        int size = x1.getNumberOfDecisionVariables();
        double[] s1 = new double[size];
        double[] s2 = new double[size];
        try {
            for (int i = 0; i < size; i++) {

                s1[i] = x1.getValue(i);
                s2[i] = x2.getValue(i);
            }
        } catch (JMException ex) {
            Logger.getLogger(Harmony_NSGII.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Arrays.equals(s1, s2);

    }

    boolean isBetter(Solution newSol, Solution worstSol) {

        Comparator crowdingComparator_ = new CrowdingComparator();
        SolutionSet popu = new SolutionSet(2);
        popu.add(newSol);
        popu.add(worstSol);
        Distance distance_ = new Distance();

        //->Ranking the union
        Ranking ranking = new Ranking(popu);

        int remain = 2;
        int index = 0;
        SolutionSet front = null;
        popu.clear();


        front = ranking.getSubfront(0);
        if (front.size() == 1) {
            if (isEqual(front.get(0), newSol)) {
                return true;
            }
        } else {
            distance_.crowdingDistanceAssignment(front, problem_.getNumberOfObjectives());
            front.sort(crowdingComparator_);
            if (isEqual(front.get(0), newSol)) {
                return true;
            }

        }
        return false;
    }
} // NSGA-II
