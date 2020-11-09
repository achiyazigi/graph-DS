package ex0;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

public class NodeData implements node_data {
    private HashMap<Integer,node_data> ni;
    private int key,tag;
    private String info;
    private static int id = 0;
    public NodeData(){
        this.ni = new HashMap<Integer,node_data>();
        this.key = id;
        this.info = "";
        this.tag = 0;
        id++;
    }

    /**
     * copy constructor, shell be called by Graph_DS only.
     * @param other
     */
    public NodeData(node_data other){
        this.ni = new HashMap<Integer,node_data>();//Graph_DS(graph g) is needed to copy neighbors. fmi, see notes in Graph_DS(graph g)
        this.key = other.getKey();
        this.info = other.getInfo();
        this.tag = other.getTag();
    }

    /**
	 * Return the key (id) associated with this node.
	 * Note: each node_data should have a unique key.
	 * @return
	 */
    @Override
    public int getKey() {
        return this.key;
    }

    /**
     * method to get the neighbors of this node
     * @return a collection with all the Neighbor nodes of this node_data
     */
    @Override
    public Collection<node_data> getNi() {
        return this.ni.values();
    }

    /**
     * return true iff this<==>key are adjacent, as an edge between them.
	 * @param key
     * @return
     */
    @Override
    public boolean hasNi(int key) {
        return this.ni.containsKey(key);

    }

    /** 
     * This method adds the node_data (t) to this node_data.
	 */
    @Override
    public void addNi(node_data t) {
        this.ni.put(t.getKey(),t);
    }

    /**
     * Removes the edge this-node,
	 * @param node
	 */
    @Override
    public void removeNode(node_data node) {
        this.ni.remove(node.getKey());
    }

    /**
	 * return the remark (meta data) associated with this node.
	 * @return
	 */
    @Override
    public String getInfo() {
        return this.info;
    }

    /**
	 * Allows changing the remark (meta data) associated with this node.
	 * @param s
	 */
    @Override
    public void setInfo(String s) {
        this.info = s;

    }

    /**
	 * Temporal data (aka color: e,g, white, gray, black) 
	 * which can be used be algorithms 
	 * @return
	 */
    @Override
    public int getTag() {
        return this.tag;
    }

    /** 
	 * Allow setting the "tag" value for temporal marking an node - common 
	 * practice for marking by algorithms.
	 * @param t - the new value of the tag
	 */
    @Override
    public void setTag(int t) {
        this.tag = t;
    }
    
    @Override
    public String toString() {
        String ans = "(tag="+this.tag+"  |";
        Iterator<node_data> i = this.ni.values().iterator();
        while(i.hasNext()){
            ans+=i.next().getKey()+"|";
        }
        return ans+")";
    }
}
