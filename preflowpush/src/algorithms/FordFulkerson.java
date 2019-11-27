package algorithms;

import java.util.*;
//import static testFFa.GraphInput.LoadSimpleGraph;
//import testFFa.Vertex.*;
/**
 *
 * @author Vishaal
 */

import graphs.Edge;
import graphs.SimpleGraph;
import graphs.Vertex;

public class FordFulkerson {


    private static final ArrayList vertices = new ArrayList(); //contains all the vertices of the graph
    private static String source; //the source node
    private static String sink; //the sink node

    //The following function computes the max-flow of the given graph from all the possible augmented paths
    public static int computeMaxFlow(Hashtable inputGraph)
    {
        int maxflow = 0;
        String u,v;
        Hashtable residualGraph = new Hashtable(); //residual graph
        try
        {
            getVertices(inputGraph);
            residualGraph = inputGraph;
            Hashtable parent = new Hashtable(); //contains the parent node of each nodes
            while(augumentPath(residualGraph,parent))
            {
                int temp = Integer.MAX_VALUE;
                for(v=sink;!v.equalsIgnoreCase(source);v=(String)parent.get(v))
                {
                    u = (String)parent.get(v);
                    int t1 = getResValue(u, v, residualGraph);
                    temp = Math.min(t1, temp); //bottle neck of for the given augmented path
                }
                for(v=sink;!v.equalsIgnoreCase(source);v=(String)parent.get(v))
                {
                    u =(String)parent.get(v);
                    setResidualValues(u,v,temp,residualGraph); //updates the residual graph
                }
                maxflow += temp;
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return maxflow;
    }

    //the following function finds the possible augmented paths from source to the sink using BFS
    public static Boolean augumentPath(Hashtable inputGraph, Hashtable parent)
    {
        Hashtable visited = new Hashtable(); //contains the visited state of a node
        try
        {
            for(int i=0;i<vertices.size();i++)
            {
                visited.put(vertices.get(i), false); //intially setting the visited state of all the node to false
            }
            LinkedList que = new LinkedList();
            que.add(source);
            visited.replace(source, true);
            parent.put(source, -1);
            while(!que.isEmpty())
            {
                String head = (String)que.poll(); //element at the top of the que
                for(Enumeration en = inputGraph.keys();en.hasMoreElements();)
                {
                    String key = (String)en.nextElement();
                    if((boolean)visited.get(key)==false && getResValue(head,key,inputGraph) > 0)
                    {
                        que.add(key);
                        parent.put(key, head);
                        visited.put(key,true);
                    }
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return ((boolean)visited.get(sink));
    }

    //the following function updates the residual graph
    public static void setResidualValues(String u, String v, int val, Hashtable inputGraph)
    {
        try
        {
            //forward edge value from the residual graph for the given edge
            Vertex vertex = (Vertex)inputGraph.get(u);
            LinkedList linkedList = vertex.incidentEdgeList;
            Iterator it = (Iterator)linkedList.iterator();
            while(it.hasNext())
            {
                Edge edge = (Edge)it.next();
                Vertex secondVertex = (Vertex)edge.getSecondEndpoint();
                String secondVertexName = (String)secondVertex.getName();
                if(secondVertexName.equalsIgnoreCase(v))
                {
                    int tempVal = (int)edge.getData();
                    tempVal -= val;
                    edge.setData(tempVal);
                }
            }

            //reverse edge value from the residual graph for the given edge
            vertex = (Vertex)inputGraph.get(v);
            linkedList = vertex.incidentEdgeList;
            it = (Iterator)linkedList.iterator();
            while(it.hasNext())
            {
                Edge edge = (Edge)it.next();
                Vertex secondVertex = (Vertex)edge.getSecondEndpoint();
                String secondVertexName = (String)secondVertex.getName();
                if(secondVertexName.equalsIgnoreCase(u))
                {
                    int tempVal = (int)edge.getData();
                    tempVal += val;
                    edge.setData(tempVal);
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    //the following function retrieves the forward edge value of the give edge from the residual graph
    public static int getResValue(String u, String v, Hashtable inputGraph)
    {
        int value = 0;
        try
        {
            Vertex vertex = (Vertex)inputGraph.get(u);
            LinkedList linkedList = vertex.incidentEdgeList;
            Iterator it = (Iterator)linkedList.iterator();
            while(it.hasNext())
            {
                Edge edge = (Edge)it.next();
                Vertex secondVertex = (Vertex)edge.getSecondEndpoint();
                String secondVertexName = (String)secondVertex.getName();
                if(secondVertexName.equalsIgnoreCase(v))
                {
                    value = (Integer) edge.getData();
                    break;
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            value = 0;
        }
        return value;
    }


    //the following function retrieves all the vertices of the generated graph
    public static void getVertices(Hashtable inputGraph)
    {
        try
        {
            for(Enumeration en=inputGraph.keys();en.hasMoreElements();)
            {
                vertices.add((String)en.nextElement());
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public static int calculateMaxFlow(SimpleGraph sg) {
        return calculateMaxFlow(sg, "s", "t");
    }
    public static int calculateMaxFlow(SimpleGraph sg, String s, String t) {
        int maxflow = 0;
        LinkedList<Vertex> V = sg.vertexList;
        Hashtable hash = new Hashtable<>();
        for (Vertex v : V) {
            hash.put(v.getName(), v);
        }

        source = s;
        sink = t;
        maxflow = computeMaxFlow(hash);
        return maxflow;
    }
}

