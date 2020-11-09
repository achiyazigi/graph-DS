package ex0;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;


public class Graph_DS implements graph {

    private HashMap<Integer,node_data> v;
    private int edges, MC;

    public Graph_DS(){
        this.v = new HashMap<Integer,node_data>();
        this.edges = this.MC = 0;
    }

    /**
     * copy constructor
     * @param g
     */
    public Graph_DS(graph g){
        this.v = new HashMap<Integer,node_data>();
        for (node_data n : g.getV()) {
            node_data new_node = new NodeData(n);
            this.addNode(new_node);
        }
        // new node(node_data other) doesn't copy other.ni. after all the nodes were copied, it's possible to reconnect them..
        for (node_data n : g.getV()) {
            int nkey = n.getKey();
            for (node_data d : n.getNi()){
                this.connect(nkey, d.getKey()); 
            }
        }
        this.edges = g.edgeSize();
        this.MC = g.getMC();
    }

    /**
	 * return the node_data by the node_id,
	 * @param key - the node_id
	 * @return the node_data by the node_id, null if none.
	 */
    @Override
    public node_data getNode(int key) {
        return this.v.get(key);
    }

    /**
	 * return true iff (if and only if) there is an edge between node1 and node2
	 * @param node1
	 * @param node2
	 * @return
	 */
    @Override
    public boolean hasEdge(int node1, int node2) {
        return this.v.get(node1).hasNi(node2);
    }

    /**
	 * add a new node to the graph with the given node_data.
	 * @param n
	 */
    @Override
    public void addNode(node_data n) {
        if(this.v.put(n.getKey(), n) != null)
            this.MC ++;
    }

    /**
    * Connect an edge between node1 and node2.
    * Note2: if the edge node1-node2 already exists - the method simply does nothing.
    */
    @Override
    public void connect(int node1, int node2) {
        node_data p = this.v.get(node1);
        node_data q = this.v.get(node2) ;
        if(p != null && q != null){
            if(!p.hasNi(node2) && p != q){// it's basicaly for the edges and MC counters, and to avoid node ni of itself..
                p.addNi(q);
                q.addNi(p);
                this.edges ++;
                this.MC ++;
            }
        }
    }

    /**
	 * This method return a pointer (shallow copy) for the
	 * collection representing all the nodes in the graph. 
	 * @return Collection<node_data>
	 */
    @Override
    public Collection<node_data> getV() {
        return this.v.values();
    }

    /**
	 * This method returns a collection containing all the
	 * nodes connected to node_id
	 * @return Collection<node_data>
	 */
    @Override
    public Collection<node_data> getV(int node_id) {
        node_data n = this.getNode(node_id);
        if(n != null)
            return n.getNi();
        return null;
    }

    /**
	 * Delete the node (with the given ID) from the graph -
	 * and removes all edges which starts or ends at this node.
	 * @return the data of the removed node (null if none). 
	 * @param key
	 */
    @Override
    public node_data removeNode(int key) {
        node_data cur = this.getNode(key);
        if(cur == null) return null;
        this.MC++;
        if(!cur.getNi().isEmpty()){
            Collection<node_data> col = new HashSet<node_data>(cur.getNi());

            for (node_data n : col) {
                this.removeEdge(key, n.getKey());
            }
        }
        return this.v.remove(key);
    }

    /**
	 * Delete the edge from the graph, 
	 * @param node1
	 * @param node2
	 */
    @Override
    public void removeEdge(int node1, int node2) {
        node_data p = this.v.get(node1);
        node_data q = this.v.get(node2);
        if(p!=null && q!=null && p.hasNi(node2)){
            p.removeNode(q);
            q.removeNode(p);
            this.edges --;
            this.MC ++;
        }
        

    }

    /** return the number of vertices (nodes) in the graph.
	 * @return
	 */
    @Override
    public int nodeSize() {
        return this.v.size();
    }

    /** 
	 * return the number of edges (undirectional graph).
	 * @return
	 */
    @Override
    public int edgeSize() {
        return this.edges;
    }

    /**
     * return the Mode Count - for testing changes in the graph.
     * Any change in the inner state of the graph should cause an increment in the ModeCount
     * @return
     */
    @Override
    public int getMC() {
        return this.MC;
    }
    
    @Override
    public String toString(){
        return this.v.toString();
    }
}