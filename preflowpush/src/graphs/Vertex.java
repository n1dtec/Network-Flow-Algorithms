package graphs;/*
 * Written by Ed Hong UWT Feb. 19, 2003.
 * Modified by Donald Chinn May 14, 2003.
 * Modified by Donald Chinn December 11, 2003.
 */

import java.util.*;

/**
 * Class that represents a vertex in a graph.
 * A name (usually a string, but it can be an arbitrary object)
 * can be associated with the vertex.
 * 
 * Data (also represented by an object (e.g., a string)) can also be
 * associated with a vertex.  This could be useful, for example, if you
 * need to mark a vertex as being visited in some graph traversal.
 * 
 * @author edhong
 * @version 0.0
 */
public class Vertex {
    /** the edge list for this vertex */
    public List<Edge> incidentEdgeList;

    private Integer data;               // an data associated with this vertex
    private Integer height;             // the height of a vertex
    private Integer e_flow;             // the excess flow of a vertex
    private Object name;            // a name associated with this vertex
    
    /**
     * Constructor that allows data and a name to be associated
     * with the vertex.
     * @param data     a data to be associated with this vertex
     * @param name     a name to be associated with this vertex
     */
    public Vertex(Integer data, Object name) {
        this.data = data;
        this.name = name;
        this.incidentEdgeList = new LinkedList();
    }
    
    /**
     * Return the name associated with this vertex.
     * @return  the name of this vertex
     */
    public Object getName(){
        return this.name;
    }
    
    /**
     * Return the data associated with this vertex.
     * @return  the data of this vertex
     */
    public Integer getData() {
        return this.data;
    }
    
    /**
     * Set the data associated with this vertex.
     * @param data  the data of this vertex
     */
    public void setData(Integer data) {
        this.data = data;
    }

    /**
     * Return the height associated with this vertex.
     * @return  the height of this vertex
     */
    public Integer getHeight() {
        return this.height;
    }

    /**
     * Set the height associated with this vertex.
     * @param height the data of this vertex
     */
    public void setHeight(Integer height) {
        this.height = height;
    }

    /**
     * Return the excess flow associated with this vertex.
     * @return  the excess flow of this vertex
     */
    public int getExcessFlow() {
        return this.e_flow;
    }

    /**
     * Set the data associated with this vertex.
     * @param e_flow  the data of this vertex
     */
    public void setExcessFlow(int e_flow) {
        this.e_flow = e_flow;
    }
}
