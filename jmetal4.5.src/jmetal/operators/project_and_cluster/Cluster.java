/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jmetal.operators.project_and_cluster;

import java.util.ArrayList;

/**
 *
 * @author UsEr
 */
public class Cluster {

    public int getCentroid() {
        return centroid;
    }

    public void setCentroid(int centroid) {
        this.centroid = centroid;
    }


    public int getClusterSize() {
        return clusterElements.size();
    }

    
    public ArrayList<Integer> getClusterElements() {
        return clusterElements;
    }
    
    
    public void setClusterElements(ArrayList<Integer> clusterElements) {
        this.clusterElements = clusterElements;
    }
    
    public ArrayList<Integer> clusterElements;
    private int clusterSize,centroid;
    
    public Cluster(){
        clusterElements = new ArrayList<>();
    }
    
    public Cluster(ArrayList<Integer> cluster, int centroid){
        clusterElements = new ArrayList<>();
        this.clusterElements = cluster;
        this.clusterSize = clusterElements.size();
        this.centroid = centroid;
        
    }
    
    
}
