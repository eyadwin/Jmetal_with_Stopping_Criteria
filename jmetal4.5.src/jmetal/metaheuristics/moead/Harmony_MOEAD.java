/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jmetal.metaheuristics.moead;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import jmetal.core.*;
import jmetal.metaheuristics.nsgaII.Harmony_NSGII;
import jmetal.operators.harmony.Harmony;
import jmetal.operators.harmony.HarmonyUnit;
import jmetal.operators.harmony.StoppingCriteria;
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
import jmetal.util.PseudoRandom;
import jmetal.util.Ranking;
import jmetal.util.asf.AchievementScalarizing;
import jmetal.util.asf.MGBM;
import jmetal.util.asf.MGBM2;
import jmetal.util.asf.OCD_HV;
import jmetal.util.asf.OCD_HV_Param;
import jmetal.util.comparators.CrowdingComparator;
import jmetal.util.wrapper.XReal;

/**
 *
 * @author UsEr
 */
public class Harmony_MOEAD extends Algorithm {
    //String Dir = "E:\\My work\\My_results\\p_files";
    String Dir="000__P_files";
    int its=0;

    int nPop=100;    
    int OCD_nPreGen=10;
    int OCD_IterationNo=0;
    SolutionSet PF[]=new SolutionSet[OCD_nPreGen];
    OCD_HV_Param ocd_hv_param=new OCD_HV_Param();
    String pfName=problem_.getName()+".pf";
    QualityIndicator indicators = new QualityIndicator(problem_, Dir+"/" + pfName);


    SolutionSet generation1, generation2;//////NEW CODE
    double KalmanGain, Z, S;//////NEW CODE
    public boolean flag = false;//////NEW CODE
    
    private int populationSize_;
    StoppingCriteria stoppingCriteria = StoppingCriteria.NONE;//////NEW CODE
    int step;//////NEW CODE
    double ASF_val[];//////NEW CODE
    int counter = 0;//////NEW CODE
    /**
     * Stores the population
     */
    private SolutionSet population_;
    /**
     * Z vector (ideal point)
     */
    double[] z_;
    /**
     * Lambda vectors
     */
    //Vector<Vector<Double>> lambda_ ; 
    double[][] lambda_;
    /**
     * T: neighbour size
     */
    int T_;
    /**
     * Neighborhood
     */
    int[][] neighborhood_;
    /**
     * delta: probability that parent solutions are selected from neighbourhood
     */
    double delta_;
    /**
     * nr: maximal number of solutions replaced by each child solution
     */
    int nr_;
    Solution[] indArray_;
    String functionType_;
    int evaluations_;
    /**
     * Operators
     */
    
    Operator memoryConOperator;//////NEW CODE
    Operator randomConOperator;//////NEW CODE
    Operator pitchAjdOperator;//////NEW CODE
    SolutionSet offspringPopulation;//////NEW CODE
    //Operator crossover_; //////REMOVED CODE
    //Operator mutation_;  //////REMOVED CODE
    String dataDirectory_;

    /**
     * Constructor
     *
     * @param problem Problem to solve
     */
    public Harmony_MOEAD(Problem problem) {
        super(problem);

        functionType_ = "_TCHE1";

    } // DMOEA

    public SolutionSet execute() throws JMException, ClassNotFoundException {
        int maxEvaluations;


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
            
                

        
        evaluations_ = 0;
        maxEvaluations = ((Integer) this.getInputParameter("maxEvaluations")).intValue();
        populationSize_ = ((Integer) this.getInputParameter("populationSize")).intValue();

    
//   ///////////////////////////////////////////////////////////
        if (problem_.getClass() == ZDT1.class || problem_.getClass() == ZDT2.class
                || problem_.getClass() == ZDT3.class || problem_.getClass() == ZDT4.class
                || problem_.getClass() == ZDT6.class) {
            maxEvaluations = 15000;
        }

        if (problem_.getClass() == DTLZ1.class || problem_.getClass() == DTLZ7.class) {
            maxEvaluations = 20000;
        }

        if (problem_.getClass() == DTLZ2.class || problem_.getClass() == DTLZ6.class) {
            maxEvaluations = 5000;
        }
        if (problem_.getClass() == DTLZ3.class) {
            maxEvaluations = 40000;
        }
        if (problem_.getClass() == DTLZ4.class) {
            maxEvaluations = 15000;
        }
        if (problem_.getClass() == DTLZ5.class) {
            maxEvaluations = 10000;
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

            
            populationSize_=210;
            
        }
//************************************************************************************
        

        dataDirectory_ = this.getInputParameter("dataDirectory").toString();
        System.out.println("POPSIZE: " + populationSize_);

        population_ = new SolutionSet(populationSize_);
        indArray_ = new Solution[problem_.getNumberOfObjectives()];

        T_ = ((Integer) this.getInputParameter("T")).intValue();
        nr_ = ((Integer) this.getInputParameter("nr")).intValue();
        delta_ = ((Double) this.getInputParameter("delta")).doubleValue();

        /*
         * T_ = (int) (0.1 * populationSize_); delta_ = 0.9; nr_ = (int) (0.01 *
         * populationSize_);
         */
        neighborhood_ = new int[populationSize_][T_];
      
        z_ = new double[problem_.getNumberOfObjectives()];
        //lambda_ = new Vector(problem_.getNumberOfObjectives()) ;
        lambda_ = new double[populationSize_][problem_.getNumberOfObjectives()];

        /////// New Code
        memoryConOperator = operators_.get("memoryConOperator");
        randomConOperator = operators_.get("randomConOperator");
        pitchAjdOperator = operators_.get("pitchAjdOperator");



        // STEP 1. Initialization
        // STEP 1.1. Compute euclidean distances between weight vectors and find T
        initUniformWeight();
        //for (int i = 0; i < 300; i++)
        // 	System.out.println(lambda_[i][0] + " " + lambda_[i][1]) ;

        initNeighborhood();

        // STEP 1.2. Initialize population
        initPopulation();

        // STEP 1.3. Initialize z_
        initIdealPoint();

        // STEP 2. Update
        do {
            int[] permutation = new int[populationSize_];
            Utils.randomPermutation(permutation, populationSize_);

            for (int i = 0; i < populationSize_; i++) {
                int n = permutation[i]; // or int n = i;
                //int n = i ; // or int n = i;
                int type;
                double rnd = PseudoRandom.randDouble();

                // STEP 2.1. Mating selection based on probability
                if (rnd < delta_) // if (rnd < realb)    
                {
                    type = 1;   // neighborhood
                } else {
                    type = 2;   // whole population
                }
                Vector<Integer> p = new Vector<Integer>();
                matingSelection(p, n, 2, type);

                // STEP 2.2. Reproduction
                Solution child = null;
                Solution[] parents = new Solution[3];

                parents[0] = population_.get(p.get(0));
                parents[1] = population_.get(p.get(1));
                parents[2] = population_.get(n);
                
                ////// New Code
                offspringPopulation = new SolutionSet(3);
                offspringPopulation.add(parents[0]);
                offspringPopulation.add(parents[1]);
                offspringPopulation.add(parents[2]);

                boolean bad = true;

                while (bad) {
                    //System.err.println("...HARMONY...");

                    HarmonyUnit hUnit = (HarmonyUnit) memoryConOperator.execute(offspringPopulation);
                    //System.err.println("...MEMORY_C...>>>>>>>> TRUE");
                    hUnit = (HarmonyUnit) randomConOperator.execute(hUnit);
                    //System.err.println("...RANDOM_C...>>>>>>>> TRUE");
                    child = (Solution) pitchAjdOperator.execute(hUnit);
                    //System.err.println("...PITCH_C...>>>>>>>> TRUE");
                    int lasPos = offspringPopulation.size() - 1;
                    offspringPopulation = sort(offspringPopulation);

                    if (isBetter(child, offspringPopulation.get(0))) {

                        //System.out.println(">>>>>> >>>>>>>> New  Chiled Added");
                        bad = false;
                    }

                }


                // Evaluation
                problem_.evaluate(child);

                evaluations_++;

                // STEP 2.3. Repair. Not necessary

                // STEP 2.4. Update z_
                updateReference(child);

                // STEP 2.5. Update of solutions
                updateProblem(child, n, type);

                /////

                /*
                 *
                 *
                 *
                 *
                 */
                ////
                if (stoppingCriteria == StoppingCriteria.OCD_HV) 
                {
                    if(evaluations_ % populationSize_ == 0)
                    {
                        //if(OCD_IterationNo%OCD_nPreGen==0)
                        {
                            System.out.println("OCD_HV IterNo="+OCD_IterationNo);
                            Ranking rankingTemp_ = new Ranking(population_);
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
                                System.out.println("[" + problem_.getName() + " ] >>>>>> OCD_HV STOPED AT [ evalNo:" + evaluations_ + " ] [ IterNo:"+OCD_IterationNo +"  ] <<<<<<");
                                Harmony.writeOnFile(problem_.getName() + " : " + evaluations_, "000__Output\\"+getClass().getName() + "  " + stoppingCriteria);
                                evaluations_ = maxEvaluations;
                            }
                            else if (evaluations_ == maxEvaluations) {
                            Harmony.writeOnFile(problem_.getName() + " : " + evaluations_, "000__Output\\"+getClass().getName() + "  " + stoppingCriteria);
                        }

                        }

                        OCD_IterationNo++;
                    }
                }
                else if (stoppingCriteria == StoppingCriteria.MOVING_AVARAGE) 
                {
                    Ranking rankingTemp_ = new Ranking(population_);
                    SolutionSet frontTemp_ = rankingTemp_.getSubfront(0);
                    Distance distance = new Distance();
                    distance.crowdingDistanceAssignment(frontTemp_, problem_.getNumberOfObjectives());
                    frontTemp_.sort(new CrowdingComparator());

                    if (evaluations_ % (populationSize_ * step) == 0 && evaluations_ != populationSize_) {

                        double v = new AchievementScalarizing().getAvarageASF(ASF_val);
                    
                        if (v <= (5.0 / 10000.0)) {
                            System.out.println("[" + problem_.getName() + " ] >>>>>>  STOPED AT [ " + evaluations_ + " ] and avarage = " + new AchievementScalarizing().getAvarageASF(ASF_val) + " <<<<<<");
                           Harmony.writeOnFile( problem_.getName()+ " : " + evaluations_, getClass().getName()+ "  "+stoppingCriteria );
                            evaluations_ = maxEvaluations;
                        }

                     //       System.out.println("00                       " + evaluations_ + "              " + v);

                    }

                    if(evaluations_ == maxEvaluations)
                        Harmony.writeOnFile( problem_.getName()+ " : " + evaluations_, getClass().getName() + "  "+stoppingCriteria);
//                     
//                    }

                    if (evaluations_ % populationSize_ == 0) {

                        ASF_val[counter % step] = new AchievementScalarizing().getBestASF(frontTemp_);
                        counter++;
                    }
                    //////////////////////////////////////////////////////////////////////////////
                    ///////////////////////////////////////////////////////////////////////////////
                } else if (stoppingCriteria == StoppingCriteria.MGBM) {
                    double ss = 0;
                    if (evaluations_ == populationSize_ * 2) {
                        S = 1;
                        KalmanGain = 0;
                        generation2 = new Ranking(population_).getSubfront(0);
                        Z = 1;
                    }

                    if (evaluations_ >= ((populationSize_ * 3))) {


                        if (evaluations_ % populationSize_ == 0) {

                            generation1 = generation2;
                            generation2 = new Ranking(population_).getSubfront(0);

                            ss = S;
                            KalmanGain = new MGBM().getKalmanGain(Z, S);
                            if (generation2.size() > 0 && generation1.size() > 0) {
                                Z = new MGBM().getActualMDR(generation1, generation2);
                                S = new MGBM().getPredectedMDR(S, Z, KalmanGain);
                            }



                      //      System.out.println("00                       " + evaluations_ + "              " + S);
                            
                            if(evaluations_ == (maxEvaluations-populationSize_))
                             Harmony.writeOnFile( problem_.getName()+ " : " + maxEvaluations, getClass().getName() + "  "+stoppingCriteria);
                            if (ss != 1) {
                             if (Math.abs(S - ss) < (1.0 / 100000)) {
                 //               if (S < 0) {
                                    System.out.println("[" + problem_.getName() + " ] >>>>>>  STOPED AT [ " + evaluations_ + " ] and avarage = " + new AchievementScalarizing().getAvarageASF(ASF_val) + " <<<<<<");
                                    Harmony.writeOnFile( problem_.getName()+ " : " + evaluations_, getClass().getName() + "  "+stoppingCriteria);
                                    evaluations_ = maxEvaluations;
                                }
                            }
                        }

                    }
                }
else if (stoppingCriteria == StoppingCriteria.MGBM2) 
                {
                    double ss = 0;
                    if (evaluations_ == populationSize_ * 2) {
                        S = 1;
                        KalmanGain = 0;
                        generation2 = new Ranking(population_).getSubfront(0);
                        Z = 1;
                    }

                    if (evaluations_ >= ((populationSize_ * 3))) {
                        if (evaluations_ % populationSize_ == 0) 
                        {
                            generation1 = generation2;
                            generation2 = new Ranking(population_).getSubfront(0);

                            ss = S;
                            KalmanGain = new MGBM2().getKalmanGain(Z, S);
                            if (/*generation2.size() > 0 &&*/ generation1.size() > 0) {
                                Z = new MGBM2().getFHI(/*generation1 ,*/ generation2);//Eq. 15
                                S = new MGBM2().getPredectedMDR(S, Z, KalmanGain);//Eq. 17
                            }
//                              System.out.println("00                       " + evaluations_ + "              " + S);
                            if (evaluations_ == maxEvaluations) {
                                Harmony.writeOnFile(problem_.getName() + " : " + evaluations_, "000__Output\\"+getClass().getName() + "  " + stoppingCriteria);
                            }
                            if (ss != 1) {
                                if (Math.abs(S - ss) < (1.0 / 100000)) {
                                    System.out.println("[" + problem_.getName() + " ] >>>>>>  MGBM2 STOPED AT [ " + evaluations_ + " ] and avarage = " + new AchievementScalarizing().getAvarageASF(ASF_val) + " <<<<<<");
                                    Harmony.writeOnFile(problem_.getName() + " : " + evaluations_, "000__Output\\"+getClass().getName() + "  " + stoppingCriteria);
                                    evaluations_ = maxEvaluations;
                                }
                            }

                        }

                    }
                }

                if (evaluations_ % populationSize_ == 0) 
                {
                    its++;
                    Harmony.functionWrite(problem_, this, Dir, population_, its);
                }
            } // for 
        } while (evaluations_ < maxEvaluations);

        return population_;
    }

    /**
     * initUniformWeight
     */
    public void initUniformWeight() {
        if ((problem_.getNumberOfObjectives() == 2) && (populationSize_ <= 300)) {
            for (int n = 0; n < populationSize_; n++) {
                double a = 1.0 * n / (populationSize_ - 1);
                lambda_[n][0] = a;
                lambda_[n][1] = 1 - a;
            } // for
        } // if
        else {
            String dataFileName;
            dataFileName = "W" + problem_.getNumberOfObjectives() + "D_"
                    + populationSize_ + ".dat";

            try {
                // Open the file
                FileInputStream fis = new FileInputStream(dataDirectory_ + "/" + dataFileName);
                InputStreamReader isr = new InputStreamReader(fis);
                BufferedReader br = new BufferedReader(isr);

                int numberOfObjectives = 0;
                int i = 0;
                int j = 0;
                String aux = br.readLine();
                while (aux != null) {
                    StringTokenizer st = new StringTokenizer(aux);
                    j = 0;
                    numberOfObjectives = st.countTokens();
                    while (st.hasMoreTokens()) {
                        double value = (new Double(st.nextToken())).doubleValue();
                        lambda_[i][j] = value;
                        //System.out.println("lambda["+i+","+j+"] = " + value) ;
                        j++;
                    }
                    aux = br.readLine();
                    i++;
                }
                br.close();
            } catch (Exception e) {
//        System.out.println("initUniformWeight: failed when reading for file: " + dataDirectory_ + "/" + dataFileName);
//        e.printStackTrace();
            }
        } // else

        //System.exit(0) ;
    } // initUniformWeight

    /**
     *
     */
    public void initNeighborhood() {
        double[] x = new double[populationSize_];
        int[] idx = new int[populationSize_];

        for (int i = 0; i < populationSize_; i++) {
            // calculate the distances based on weight vectors
            for (int j = 0; j < populationSize_; j++) {
                x[j] = Utils.distVector(lambda_[i], lambda_[j]);
                //x[j] = dist_vector(population[i].namda,population[j].namda);
                idx[j] = j;
                //System.out.println("x["+j+"]: "+x[j]+ ". idx["+j+"]: "+idx[j]) ;
            } // for

            // find 'niche' nearest neighboring subproblems
            Utils.minFastSort(x, idx, populationSize_, T_);
            //minfastsort(x,idx,population.size(),niche);

            System.arraycopy(idx, 0, neighborhood_[i], 0, T_);
        } // for
    } // initNeighborhood

    /**
     *
     */
    public void initPopulation() throws JMException, ClassNotFoundException {
        for (int i = 0; i < populationSize_; i++) {
            Solution newSolution = new Solution(problem_);

            problem_.evaluate(newSolution);
            evaluations_++;
            population_.add(newSolution);
        } // for
    } // initPopulation

    /**
     *
     */
    void initIdealPoint() throws JMException, ClassNotFoundException {
        for (int i = 0; i < problem_.getNumberOfObjectives(); i++) {
            z_[i] = 1.0e+30;
            indArray_[i] = new Solution(problem_);
            problem_.evaluate(indArray_[i]);
            evaluations_++;
        } // for

        for (int i = 0; i < populationSize_; i++) {
            updateReference(population_.get(i));
        } // for
    } // initIdealPoint

    /**
     *
     */
    public void matingSelection(Vector<Integer> list, int cid, int size, int type) {
        // list : the set of the indexes of selected mating parents
        // cid  : the id of current subproblem
        // size : the number of selected mating parents
        // type : 1 - neighborhood; otherwise - whole population
        int ss;
        int r;
        int p;

        ss = neighborhood_[cid].length;
        while (list.size() < size) {
            if (type == 1) {
                r = PseudoRandom.randInt(0, ss - 1);
                p = neighborhood_[cid][r];
                //p = population[cid].table[r];
            } else {
                p = PseudoRandom.randInt(0, populationSize_ - 1);
            }
            boolean flag = true;
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i) == p) // p is in the list
                {
                    flag = false;
                    break;
                }
            }

            //if (flag) list.push_back(p);
            if (flag) {
                list.addElement(p);
            }
        }
    } // matingSelection

    /**
     *
     * @param individual
     */
    void updateReference(Solution individual) {
        for (int n = 0; n < problem_.getNumberOfObjectives(); n++) {
            if (individual.getObjective(n) < z_[n]) {
                z_[n] = individual.getObjective(n);

                indArray_[n] = individual;
            }
        }
    } // updateReference

    /**
     * @param individual
     * @param id
     * @param type
     */
    void updateProblem(Solution indiv, int id, int type) {
        // indiv: child solution
        // id:   the id of current subproblem
        // type: update solutions in - neighborhood (1) or whole population (otherwise)
        int size;
        int time;

        time = 0;

        if (type == 1) {
            size = neighborhood_[id].length;
        } else {
            size = population_.size();
        }
        int[] perm = new int[size];

        Utils.randomPermutation(perm, size);

        for (int i = 0; i < size; i++) {
            int k;
            if (type == 1) {
                k = neighborhood_[id][perm[i]];
            } else {
                k = perm[i];      // calculate the values of objective function regarding the current subproblem
            }
            double f1, f2;

            f1 = fitnessFunction(population_.get(k), lambda_[k]);
            f2 = fitnessFunction(indiv, lambda_[k]);

            if (f2 < f1) {
                population_.replace(k, new Solution(indiv));
                //population[k].indiv = indiv;
                time++;
            }
            // the maximal number of solutions updated is not allowed to exceed 'limit'
            if (time >= nr_) {
                return;
            }
        }
    } // updateProblem

    double fitnessFunction(Solution individual, double[] lambda) {
        double fitness;
        fitness = 0.0;

        if (functionType_.equals("_TCHE1")) {
            double maxFun = -1.0e+30;

            for (int n = 0; n < problem_.getNumberOfObjectives(); n++) {
                double diff = Math.abs(individual.getObjective(n) - z_[n]);

                double feval;
                if (lambda[n] == 0) {
                    feval = 0.0001 * diff;
                } else {
                    feval = diff * lambda[n];
                }
                if (feval > maxFun) {
                    maxFun = feval;
                }
            } // for

            fitness = maxFun;
        } // if
        else {
            System.out.println("MOEAD.fitnessFunction: unknown type " + functionType_);
            System.exit(-1);
        }
        return fitness;
    } // fitnessEvaluation

    /////////////////////////////////////////
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
        double [] s1= new double [size];
        double [] s2= new double [size];
        try {
        for (int i = 0; i < size; i++) {

               s1[i] = x1.getValue(i);
               s2[i] = x2.getValue(i);
                }
        }
        catch (JMException ex) {
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
        if( front.size() == 1){
            if(isEqual(front.get(0), newSol)) {
                return true;
            }         
     }
        else{
        distance_.crowdingDistanceAssignment(front, problem_.getNumberOfObjectives());
           front.sort(crowdingComparator_);
            if(isEqual(front.get(0), newSol)) {
                return true;
            }         
        
        }
        return false;
     }


    
/*
    boolean isEqual(Solution sol1, Solution sol2) {

        XReal x1 = new XReal(sol1);
        XReal x2 = new XReal(sol2);
        int size = x1.getNumberOfDecisionVariables();

        boolean flag = true;
        try {
            for (int i = 0; i < size; i++) {

                if (x1.getValue(i) != x2.getValue(i)) {
                    flag = false;
                }
            }

        } catch (JMException ex) {
            Logger.getLogger(Harmony_NSGII.class.getName()).log(Level.SEVERE, null, ex);
        }

        return true;
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


        front = ranking.getSubfront(index);

        while ((remain > 0) && (remain >= front.size())) {
            //Asign crowding distance to individuals
            distance_.crowdingDistanceAssignment(front, problem_.getNumberOfObjectives());
            //Add the individuals of this front
            for (int k = 0; k < front.size(); k++) {
                popu.add(front.get(k));
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
                popu.add(front.get(k));
            } // for

            remain = 0;
        }


        if (isEqual(popu.get(0), newSol)) {
            return true;
        } else if (isEqual(popu.get(0), worstSol)) {
            return false;
        }
        return true;
    }*/
} // MOEAD

