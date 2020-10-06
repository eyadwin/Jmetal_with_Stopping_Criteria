/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jmetal.operators.project_and_cluster;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 *
 * @author UsEr
 */
public class ClusteringKMeans {
    
    
        double[][] centroid_ ;
        int [] indeces;
    
	public ArrayList<Cluster> doClustering(double[][] rawData, double[] attributes , int numClu) {

            ArrayList<Cluster> clusters = new ArrayList<Cluster>();
            
		try {
			//System.out.println("\nRaw data:\n");
			//ShowMatrix(rawData, rawData.length, true);

			int numAttributes = attributes.length; // 2 in this demo
													// (height,weight)
			int numClusters = numClu; // vary this to experiment (must be between 2
									// and number data tuples)
			int maxCount = 30; // trial and error

//			System.out.println("\nBegin clustering data with k = "
//					+ numClusters + " and maxCount = " + maxCount);
			int[] clustering = Cluster(rawData, numClusters, numAttributes,
					maxCount);
                        
//			System.out.println("\nClustering complete");
                        
                        Cluster temp;
                        for (int i = 0; i < numClusters; i++){
                            temp = new Cluster();
                            for(int j = 0; j < rawData.length; j++){
                                
                                if (clustering[j] == i){
                                    temp.getClusterElements().add(j);
                                }
                            }
                            clusters.add(temp);
                        }
                        
                        
                        for(int i=0;i<indeces.length;i++){
                            for(Cluster cl : clusters){
                                if( cl.getClusterElements().contains(indeces[i] ) )
                                    cl.setCentroid( indeces[i] );
                            }
                        }

//			System.out.println("\nClustering in internal format: \n");
//			ShowVector(clustering, true); // true -> newline after display
//
//			System.out.println("\nClustered data:");
//			ShowClustering(rawData, numClusters, clustering, true);
//
//			double[] outlier = Outlier(rawData, clustering, numClusters, 0);
//			System.out.println("Outlier for cluster 0 is:");
//			ShowVector(outlier, true);
//
//			System.out.println("\nEnd demo\n");
                        return clusters;

		} catch (Exception ex) {
			System.out.println(ex.getMessage());
                        return clusters;

		}

	}

	static void ShowMatrix(double[][] matrix, int numRows, boolean newLine) {
		for (int i = 0; i < numRows; ++i) {
			System.out.print("[" + i + "]  ");
			for (int j = 0; j < matrix[i].length; ++j)
				System.out.print(matrix[i][j] + "  ");
			System.out.println("");
		}
		if (newLine == true)
			System.out.println("");
	} // ShowMatrix

         int[] Cluster(double[][] rawData, int numClusters,
			int numAttributes, int maxCount) {
		boolean changed = true;
		int ct = 0;

		int numTuples = rawData.length;
		int[] clustering = InitClustering(numTuples, numClusters, 0); // 0 is a
																		// seed
																		// for
																		// random
		double[][] means = Allocate(numClusters, numAttributes); // just makes
																	// things a
																	// bit
																	// cleaner
		double[][] centroids = Allocate(numClusters, numAttributes);
		UpdateMeans(rawData, clustering, means); // could call this inside
													// UpdateCentroids instead
		UpdateCentroids(rawData, clustering, means, centroids);

		while (changed == true && ct < maxCount) {
			++ct;
			changed = Assign(rawData, clustering, centroids); // use centroids
																// to update
																// cluster
																// assignment
			UpdateMeans(rawData, clustering, means); // use new clustering to
														// update cluster means
			UpdateCentroids(rawData, clustering, means, centroids); // use new
																	// means to
																	// update
																	// centroids
		}
                

                centroid_ = centroids;
                indeces = new int[centroid_.length];
		
		for (double[] ds : centroid_) {
			for (double xs : ds) {
				//System.out.print(xs+"  ");
			}
			//System.out.println();
		}
		
		for(int i=0;i<centroid_.length;i++){
			for(int j=0;j<rawData.length;j++){
				if( Arrays.equals( rawData[j],centroid_[i] )){
					//System.err.print(j+"  ");
                                indeces[i] = j;
                                }
			}
		}
                
                
		// ShowMatrix(centroids, centroids.Length, true); // show the final
		// centroids for each cluster
		return clustering;
	}

	static boolean Assign(double[][] rawData, int[] clustering,
			double[][] centroids) {
		// assign each tuple to best cluster (closest to cluster centroid)
		// return true if any new cluster assignment is different from old/curr
		// cluster
		// does not prevent a state where a cluster has no tuples assigned. see
		// article for details
		int numClusters = centroids.length;
		boolean changed = false;

		double[] distances = new double[numClusters]; // distance from curr
														// tuple to each cluster
														// mean
		for (int i = 0; i < rawData.length; ++i) // walk thru each tuple
		{
			for (int k = 0; k < numClusters; ++k)
				// compute distances to all centroids
				distances[k] = Distance(rawData[i], centroids[k]);

			int newCluster = MinIndex(distances); // find the index == custerID
													// of closest
			if (newCluster != clustering[i]) // different cluster assignment?
			{
				changed = true;
				clustering[i] = newCluster;
			} // else no change
		}
		return changed; // was there any change in clustering?
	} // Assign

	static double[] Outlier(double[][] rawData, int[] clustering,
			int numClusters, int cluster) {
		// return the tuple values in cluster that is farthest from cluster
		// centroid
		int numAttributes = rawData[0].length;

		double[] outlier = new double[numAttributes];
		double maxDist = 0.0;

		double[][] means = Allocate(numClusters, numAttributes);
		double[][] centroids = Allocate(numClusters, numAttributes);
		UpdateMeans(rawData, clustering, means);
		UpdateCentroids(rawData, clustering, means, centroids);

		for (int i = 0; i < rawData.length; ++i) {
			int c = clustering[i];
			if (c != cluster)
				continue;
			double dist = Distance(rawData[i], centroids[cluster]);
			if (dist > maxDist) {
				maxDist = dist; // might also want to return (as an out param)
								// the index of rawData
				//Array.Copy(rawData[i], outlier, rawData[i].length);
				System.arraycopy( rawData[i], 0, outlier, 0, rawData[i].length );
				
			}
		}
		return outlier;
	}

	static void ShowVector(int[] vector, boolean newLine) {
		for (int i = 0; i < vector.length; ++i)
			System.out.print(vector[i] + " ");
		System.out.println("");
		if (newLine == true)
			System.out.println("");
	}

	static void ShowVector(double[] vector, boolean newLine) {
		for (int i = 0; i < vector.length; ++i)
			System.out.print(vector[i] + " ");
		System.out.println("");
		if (newLine == true)
			System.out.println("");
	}

	static void ShowClustering(double[][] rawData, int numClusters,
			int[] clustering, boolean newLine) {
		System.out.println("-----------------");
		for (int k = 0; k < numClusters; ++k) // display by cluster
		{
			for (int i = 0; i < rawData.length; ++i) // each tuple
			{
				if (clustering[i] == k) // curr tuple i belongs to curr cluster
										// k.
				{
					System.out.print("[" + i + "]");
					for (int j = 0; j < rawData[i].length; ++j)
						System.out.print(rawData[i][j] + "  ");
					System.out.println("");
				}
			}
			System.out.println("-----------------");
		}
		if (newLine == true)
			System.out.println("");
	}

	static int MinIndex(double[] distances) {
		// index of smallest value in distances[]
		int indexOfMin = 0;
		double smallDist = distances[0];
		for (int k = 0; k < distances.length; ++k) {
			if (distances[k] < smallDist) {
				smallDist = distances[k];
				indexOfMin = k;
			}
		}
		return indexOfMin;
	}

	static double Distance(double[] tuple, double[] vector) {
		// Euclidean distance between an actual data tuple and a cluster mean or
		// centroid
		double sumSquaredDiffs = 0.0;
		for (int j = 0; j < tuple.length; ++j)
			sumSquaredDiffs += Math.pow((tuple[j] - vector[j]), 2);
		return Math.sqrt(sumSquaredDiffs);
	}

	static void UpdateCentroids(double[][] rawData, int[] clustering,
			double[][] means, double[][] centroids) {
		// updates all centroids by calling helper that updates one centroid
		for (int k = 0; k < centroids.length; ++k) {
			double[] centroid = ComputeCentroid(rawData, clustering, k, means);
			centroids[k] = centroid;
		}
	}

	static double[] ComputeCentroid(double[][] rawData, int[] clustering,
			int cluster, double[][] means) {
		// the centroid is the actual tuple values that are closest to the
		// cluster mean
		int numAttributes = means[0].length;
		double[] centroid = new double[numAttributes];
		double minDist = Double.MAX_VALUE;
		for (int i = 0; i < rawData.length; ++i) // walk thru each data tuple
		{
			int c = clustering[i]; // if curr tuple isn't in the cluster we're
									// computing for, continue on
			if (c != cluster)
				continue;

			double currDist = Distance(rawData[i], means[cluster]); // call
																	// helper
			if (currDist < minDist) {
				minDist = currDist;
				for (int j = 0; j < centroid.length; ++j)
					centroid[j] = rawData[i][j];
			}
		}
		return centroid;
	}

	static void UpdateMeans(double[][] rawData, int[] clustering,
			double[][] means) {
		// assumes means[][] exists. consider making means[][] a ref parameter
		int numClusters = means.length;
		// zero-out means[][]
		for (int k = 0; k < means.length; ++k)
			for (int j = 0; j < means[k].length; ++j)
				means[k][j] = 0.0;

		// make an array to hold cluster counts
		int[] clusterCounts = new int[numClusters];

		// walk through each tuple, accumulate sum for each attribute, update
		// cluster count
		for (int i = 0; i < rawData.length; ++i) {
			int cluster = clustering[i];
			++clusterCounts[cluster];

			for (int j = 0; j < rawData[i].length; ++j)
				means[cluster][j] += rawData[i][j];
		}

		// divide each attribute sum by cluster count to get average (mean)
		for (int k = 0; k < means.length; ++k)
			for (int j = 0; j < means[k].length; ++j)
				means[k][j] /= clusterCounts[k]; // will throw if count is 0.
													// consider an error-check

		return;
	} // UpdateMeans

	static double[][] Allocate(int numClusters, int numAttributes) {
		// helper allocater for means[][] and centroids[][]
		double[][] result = new double[numClusters][];
		for (int k = 0; k < numClusters; ++k)
			result[k] = new double[numAttributes];
		return result;
	}

	static int[] InitClustering(int numTuples, int numClusters, int randomSeed) {
		// assign each tuple to a random cluster, making sure that there's at
		// least
		// one tuple assigned to every cluster
		Random random = new Random(randomSeed);
		int[] clustering = new int[numTuples];

		// assign first numClusters tuples to clusters 0..k-1
		for (int i = 0; i < numClusters; ++i)
			clustering[i] = i;
		// assign rest randomly
		for (int i = numClusters; i < clustering.length; ++i)
			clustering[i] = random.nextInt(numClusters);
		return clustering;
	}

}
