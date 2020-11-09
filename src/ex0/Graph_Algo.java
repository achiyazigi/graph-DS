package ex0;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Graph_Algo implements graph_algorithms {
    private graph g;

    /**
     * create graph_Algo Object and init graph g
     * @param g
     */
    public Graph_Algo(graph g){ //thats here for the first version test
        this.g = g;
    }

    public Graph_Algo(){
        this.g = new Graph_DS();
    } 

    
    /** 
     * Init the graph on which this set of algorithms operates on.
     * @param g_to_init
     */
    @Override
    public void init(graph g_to_init) {
        this.g = g_to_init;
    }

    
    /** 
	 * Compute a deep copy of this graph.
     * @return graph
     */
    @Override
    public graph copy() {
        graph new_graph = new Graph_DS(this.g);
        return new_graph;
    }

    
    /** 
     * Returns true if and only if (iff) there is a valid path from EVREY node to each
     * other node. NOTE: assume ubdirectional graph.
     * @return boolean
     */
    @Override
    public boolean isConnected() { //   *BFS* i'll count element pops from queue and compare it to |v|
        zeroTags(); // J.I.C. tags not zeroed allready...
        Queue<node_data> q = new LinkedList<node_data>();
        Collection<node_data> col = this.g.getV();
        int counter = 0;
        if(!col.isEmpty()){ //if g isn't empty
            node_data first = col.iterator().next();
            first.setTag(1); // coloring
            q.add(first);
            while(!q.isEmpty()){
                first = q.poll();
                Iterator<node_data> i = first.getNi().iterator(); //taking first node in queue and adding its uncolored neighbors
                counter++;
                while(i.hasNext()){ //adding the neighbors
                    node_data to_add = i.next();
                    if(to_add.getTag() == 0){
                        to_add.setTag(1); // coloring
                        q.add(to_add);
                    }
                }
            }
            return counter == col.size();
        }
        
        return true; //empty graph is connected
    }

    
    /** 
     * returns the length of the shortest path between src to dest
    * Note: if no such path --> returns -1
    * @param src - start node
    * @param dest - end (target) node
    * @return List<node_data>
    */
    @Override
    public int shortestPathDist(int src, int dest) {
        List<node_data> sp = this.shortestPath(src, dest);
        if(sp == null) return -1; // no such path
        return sp.size()-1; // num of nodes in shortestPath - 1
    }

    /**
	 * returns the the shortest path between src to dest - as an ordered List of nodes:
	 * src--> n1-->n2-->...dest
	 * Note if no such path --> returns null;
	 * @param src - start node
	 * @param dest - end (target) node
	 * @return
	 */    
    @Override
    public List<node_data> shortestPath(int src, int dest) {
        zeroTags(); // J.I.C. tags not zeroed allready...
        Queue<node_data> q = new LinkedList<node_data>();
        node_data start = this.g.getNode(src);
    
        if(this.g != null && start != null){ //if g isn't empty
            q.add(start);
            if(src == dest){ // if the shortestPath is just one node
                LinkedList<node_data> res = new LinkedList<node_data>();
                res.push(start);
                return res;
            }
            while(!q.isEmpty()){
                node_data head = q.poll();
                Iterator<node_data> i = head.getNi().iterator();
                while(i.hasNext()){ //adding the neighbors
                    node_data to_add = i.next();
                    if(to_add.getTag() == 0 && !to_add.equals(start)){
                        to_add.setTag(head.getTag()+1); // coloring
                        q.add(to_add);
                    }
                    if(to_add.getKey() == dest){
                        return BuildPath(dest, src);
                    }
                }
            }
        }
        return null;
    }
    

    /**
     * reset all tags to zero
     */  
    private void zeroTags() {
        Collection<node_data> col = this.g.getV();
        for (node_data n : col) {
            n.setTag(0);
        }
    }

    
    /** 
     * backtracking the SP after initializing tags in shortestPath().
     * this private method assumes the path exists and is called by shortestPath() only.
     * @param src
     * @param dest
     * @return LinkedList<node_data> of every node contained in the shortest path.
     */
    private LinkedList<node_data> BuildPath(int src, int dest) {
        LinkedList<node_data> res = new LinkedList<node_data>();
        node_data head = this.g.getNode(src);
        while(head.getTag()> 0){
            res.push(head);
            Iterator<node_data> i = head.getNi().iterator();
            while(i.hasNext()){
                node_data candi = i.next(); // candidate for being the desired node in path.
                if(candi.getTag() + 1 == head.getTag()&& !(candi.getTag() == 0 && candi != this.g.getNode(dest))){
                //last condition avoids adding node != dest with tag = 0.
                // it may happen when shortestPath() found dest before coloring every node in the graph.
                    head = candi;
                    break;
                }
            }
        }
        res.push(head);
        return res;
    }

}
