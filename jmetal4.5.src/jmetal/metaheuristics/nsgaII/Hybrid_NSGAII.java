/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jmetal.metaheuristics.nsgaII;

import java.util.ArrayList;
import java.util.Random;
import jmetal.core.*;
import jmetal.operators.diversity.Diverse;
import jmetal.operators.harmony.Harmony;
import jmetal.operators.harmony.StoppingCriteria;
import jmetal.operators.localSearch.LocalSearch;
import jmetal.operators.project_and_cluster.ProjectNCluster;
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
import jmetal.util.Distance;
import jmetal.util.JMException;
import jmetal.util.Ranking;
import jmetal.util.archive.CrowdingArchive;
import jmetal.util.asf.AchievementScalarizing;
import jmetal.util.asf.MGBM;
import jmetal.util.asf.MGBM2;
import jmetal.util.asf.OCD_HV;
import jmetal.util.asf.OCD_HV_Param;
import jmetal.util.comparators.CrowdingComparator;

/**
 *
 * @author UsEr
 */
public class Hybrid_NSGAII extends Algorithm {

    CrowdingArchive archive_;
    SolutionSet generation1, generation2;
    double KalmanGain, Z, S;
    //String Dir = "C:\\Users\\yucc\\Desktop\\2014\\Research\\Muhammed Batained Thesis\\The code\\result\\p_files";
    String Dir="000__P_files";
    int its=0;
    
    
    int nPop=100;    
    int OCD_nPreGen=10;
    int OCD_IterationNo=0;
    SolutionSet PF[]=new SolutionSet[OCD_nPreGen];
    OCD_HV_Param ocd_hv_param=new OCD_HV_Param();
    String pfName=problem_.getName()+".pf";
    QualityIndicator indicators = new QualityIndicator(problem_, Dir+"/" + pfName);

    
    

    public Hybrid_NSGAII(Problem problem) {
        super(problem);
    }

    @Override
    public SolutionSet execute() throws JMException, ClassNotFoundException {


        int populationSize;
        int maxEvaluations;
        int evaluations;
        int iteration = 0;
        double Qbound = 0;
        boolean isDiverse = false;
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

        Operator mutationOperator;
        Operator crossoverOperator;
        Operator selectionOperator;
        Operator projectionOperator;
        Operator localSearchOperator;



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
        if(problem_.getClass() == ZDT1.class || problem_.getClass() == ZDT2.class
                || problem_.getClass() == ZDT3.class || problem_.getClass() == ZDT4.class
                || problem_.getClass() == ZDT6.class || problem_.getClass() == DTLZ1.class
                || problem_.getClass() == DTLZ7.class)
        {
            maxEvaluations=20000;
        }
        
        if(problem_.getClass() == DTLZ2.class || problem_.getClass() == DTLZ4.class
                || problem_.getClass() == DTLZ5.class)
            
            maxEvaluations=5000;
        if(problem_.getClass() == DTLZ3.class)
            maxEvaluations=30000;
        if(problem_.getClass() == DTLZ6.class)
            maxEvaluations=15000;
        if(problem_.getClass() == UF1.class || problem_.getClass() == UF2.class
                || problem_.getClass() == UF3.class || problem_.getClass() == UF4.class
                || problem_.getClass() == UF5.class || problem_.getClass() == UF6.class
                || problem_.getClass() == UF7.class || problem_.getClass() == UF8.class
                || problem_.getClass() == UF9.class || problem_.getClass() == UF10.class)
             maxEvaluations=50000;
        
 ///////////////////////////////////////////////////////// 
        //************************************************************************************
        if (problem_.getClass() == DTLZ1.class || problem_.getClass() == DTLZ2.class
                || problem_.getClass() == DTLZ3.class || problem_.getClass() == DTLZ4.class
                || problem_.getClass() == DTLZ5.class || problem_.getClass() == DTLZ6.class
                || problem_.getClass() == DTLZ7.class || problem_.getClass() == UF8.class
                || problem_.getClass() == UF9.class || problem_.getClass() == UF10.class) {

            
            populationSize=200;
            
        }
//************************************************************************************
    

        //Initialize the variables
        population = new SolutionSet(populationSize);
        evaluations = 0;

        requiredEvaluations = 0;

        //Read the operators
        mutationOperator = operators_.get("mutation");
        crossoverOperator = operators_.get("crossover");
        selectionOperator = operators_.get("selection");
        projectionOperator = operators_.get("projection");
        localSearchOperator = (LocalSearch) operators_.get("improvement");

      //  localSearchOperator.setParameter("archive", archive_);
        

        // Create the initial solutionSet
        Solution newSolution;
        for (int i = 0; i < populationSize; i++) {
            newSolution = new Solution(problem_);
            problem_.evaluate(newSolution);
            problem_.evaluateConstraints(newSolution);
            evaluations++;
            population.add(newSolution);
        } //for       

        // Generations 
        while (evaluations < maxEvaluations) {

            // Create the offSpring solutionSet      
            offspringPopulation = new SolutionSet(populationSize);
            Solution[] parents = new Solution[2];
            for (int i = 0; i < (populationSize / 2); i++) {
                if (evaluations < maxEvaluations) {
                    //obtain parents
                    parents[0] = (Solution) selectionOperator.execute(population);
                    parents[1] = (Solution) selectionOperator.execute(population);
                    Solution[] offSpring = (Solution[]) crossoverOperator.execute(parents);
                    mutationOperator.execute(offSpring[0]);
                    mutationOperator.execute(offSpring[1]);
                    problem_.evaluate(offSpring[0]);
                    problem_.evaluateConstraints(offSpring[0]);
                    problem_.evaluate(offSpring[1]);
                    problem_.evaluateConstraints(offSpring[1]);
                    offspringPopulation.add(offSpring[0]);
                    offspringPopulation.add(offSpring[1]);
                    evaluations += 2;
                } // if                            
            } // for

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
                int j = front.size() - 1;
                for (int i = 0; i < 75; i++) {
                    if (remain > 0) {

                        population.add(front.get(j));
                        remain--;
                        j--;
                    } else {
                        break;
                    }
                }
                for (int k = 1; k <= remain; k++) {
                    population.add(front.get(k));
                } // for
                remain = 0;
            } // if                               


if (evaluations % populationSize == 0) {
                its++;
                Harmony.functionWrite(problem_, this, Dir, population, its);
            }


            //******************************************//
            projectionOperator.execute(population);

            if (iteration % 5 == 0) {
                Qbound = ProjectNCluster.q / 4;
            }

            if (iteration > 0) {
                isDiverse = Diverse.Check(ProjectNCluster.q, Qbound);
            }

            if (isDiverse) {



                if(iteration%3 == 0){
                    
                    /***local search***/
                    int ind = new Random().nextInt( populationSize );
                    Solution individual = (Solution)localSearchOperator.execute( population.get(ind) );
                    population.replace(ind, individual)  ;
                    
                }


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
                iteration++;
                continue;
            }

            //******************************************//



            // Create the offSpring solutionSet      
            offspringPopulation = new SolutionSet(populationSize);
            Solution[] parentsN = new Solution[2];
            for (int i = 0; i < (populationSize / 2); i++) {
                if (evaluations < maxEvaluations) {
                    //obtain parents
                    parentsN[0] = (Solution) selectionOperator.execute(population);
                    parentsN[1] = (Solution) selectionOperator.execute(population);
                    Solution[] offSpring = (Solution[]) crossoverOperator.execute(parentsN);
                    mutationOperator.execute(offSpring[0]);
                    mutationOperator.execute(offSpring[1]);
                    problem_.evaluate(offSpring[0]);
                    problem_.evaluateConstraints(offSpring[0]);
                    problem_.evaluate(offSpring[1]);
                    problem_.evaluateConstraints(offSpring[1]);
                    offspringPopulation.add(offSpring[0]);
                    offspringPopulation.add(offSpring[1]);
                    evaluations += 2;
                } // if                            
            } // for


            union = ((SolutionSet) population).union(offspringPopulation);

            ArrayList<SolutionSet> populations = (ArrayList<SolutionSet>) projectionOperator.execute(union);
            ArrayList<Ranking> clustersRanking = new ArrayList<>();
            for (SolutionSet solutionSet : populations) {

                Ranking tempRanking = new Ranking(solutionSet);
                clustersRanking.add(tempRanking);

            }

            remain = populationSize;
            index = 0;
            front = null;
            population.clear();
            Object op = clustersRanking;
            int kkd = 0;
            front = clustersRanking.get(0).getSubfront(0);

            for (Ranking ranking1 : clustersRanking) {
                if(ranking1.getNumberOfSubfronts() > 0)
                front = front.union(ranking1.getSubfront(index));
            }


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



                if (remain > 0 &&  clustersRanking.get(0).getNumberOfSubfronts() >  index ) {

                    front = clustersRanking.get(0).getSubfront(index);
                    for (Ranking ranking1 : clustersRanking) {
                        if( ranking1.getNumberOfSubfronts() >  index )
                        front = front.union(ranking1.getSubfront(index));
                    }
                } // if        
            } // while




            // Remain is less than front(index).size, insert only the best one
            if (remain > 0) {  // front contains individuals to insert                        
                distance.crowdingDistanceAssignment(front, problem_.getNumberOfObjectives());
                front.sort(new CrowdingComparator());
                int j = front.size() - 1;
                for (int i = 0; i < 75; i++) {
                    if (remain > 0) {

                        population.add(front.get(j));
                        remain--;
                        j--;
                    } else {
                        break;
                    }
                }
                for (int k = 1; k <= remain; k++) {
                    population.add(front.get(k));
                } // for
//        int k=front.size()-1;
//        int i=0;
//        while(i< remain)
//        {
//            population.add(front.get(k));
//            k--;
//            i++;
//        }

                remain = 0;
            } // if      


            //******************************************//

            // This piece of code shows how to use the indicator object into the code
            // of NSGA-II. In particular, it finds the number of evaluations required
            // by the algorithm to obtain a Pareto front with a hypervolume higher
            // than the hypervolume of the true Pareto front.
         
            
//            if ((indicators != null)
//                    && (requiredEvaluations == 0)) {
//                double HV = indicators.getHypervolume(population);
//                if (HV >= (0.98 * indicators.getTrueParetoFrontHypervolume())) {
//                    requiredEvaluations = evaluations;
//                } // if
//            } // if


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
            else if (stoppingCriteria == StoppingCriteria.MOVING_AVARAGE) {

                Ranking rankingTemp_ = new Ranking(population);
                SolutionSet frontTemp_ = rankingTemp_.getSubfront(0);
                Distance distance2 = new Distance();
                distance2.crowdingDistanceAssignment(frontTemp_, problem_.getNumberOfObjectives());
                frontTemp_.sort(new CrowdingComparator());

                if (evaluations % (populationSize * step) == 0 && evaluations != populationSize) {

                    double v = new AchievementScalarizing().getAvarageASF(ASF_val);

                    if (v <= (5.0 / 10000.0)) {
                        System.out.println("[" + problem_.getName() + " ] >>>>>>  STOPED AT [ " + evaluations + " ] and avarage = " + new AchievementScalarizing().getAvarageASF(ASF_val) + " <<<<<<");
                        Harmony.writeOnFile(problem_.getName() + " : " + evaluations, getClass().getName() + "  " + stoppingCriteria);
                        evaluations = maxEvaluations;
                    }

//                        System.out.println("00                       "+evaluations + "              "+v);

                }

                if (evaluations == (maxEvaluations - populationSize)) {
                    Harmony.writeOnFile(problem_.getName() + " : " + maxEvaluations, getClass().getName() + "  " + stoppingCriteria);
                }
//                     
//                    }

                if (evaluations % populationSize == 0) {
                    if (frontTemp_.size() > 0) {
                        ASF_val[counter % step] = new AchievementScalarizing().getBestASF(frontTemp_);
                    }
                    counter++;
                }

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
//                              System.out.println("00                       " + evaluations + "              " + S);
                        if (evaluations == (maxEvaluations - populationSize)) {
                            Harmony.writeOnFile(problem_.getName() + " : " + maxEvaluations, getClass().getName() + "  " + stoppingCriteria);
                        }
                        if (ss != 1) {
                            if (Math.abs(S - ss) < (1.0 / 100000)) {
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

            ////////////////////////
            ///////////////////

            //******************************************//

            iteration++;
            if (evaluations % populationSize == 0) {
                its++;
                Harmony.functionWrite(problem_, this, Dir, population, its);
            }


        } // end big while

        // Return as output parameter the required evaluations
        setOutputParameter("evaluations", requiredEvaluations);

        // Return the first non-dominated front
        Ranking ranking = new Ranking(population);
        ranking.getSubfront(0).printFeasibleFUN("FUN_NSGAII");

        return ranking.getSubfront(0);
        // return population;


    }
}
