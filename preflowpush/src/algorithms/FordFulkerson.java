package algorithms;

import java.util.*;

import graphs.Edge;
import graphs.SimpleGraph;
import graphs.Vertex;

// COURSE: TCSS 543
// INSTRUCTOR: KA YEE YEUNG
// ASSIGNMENT: Final Project
// AUTHOR: Richard Brun
// VERSION: 1.0

public class FordFulkerson {

    //the ArrayList will contain all the vertices of a graph
    private static final ArrayList vertices = new ArrayList();
    //the source node
    private static String source;
    //the sink node
    private static String sink;

    //This function will compute the max-flow of a graph from all possible augmented paths
    public static int theMaximumFlow(Hashtable theGraph)
    {
        // set initial flow to zero
        int maxflow = 0;

        // these will store the vertices of the graph
        String u,v;

        // using a hashtable to store the values of the constructed residual graph
        Hashtable theResidualGraph = new Hashtable();

        try
        {
            getAllVertices(theGraph);
            theResidualGraph = theGraph;

            //this will contain the parent node of each nodes
            Hashtable theParentNode = new Hashtable();

            // this loop will go through all possible augmented paths with BFS
            while(theAugumentedPath(theResidualGraph,theParentNode))
            {
                // store the maximum value of augmented path
                int theTemp = Integer.MAX_VALUE;

                // as long as sink does not equal the source execute
                for(v=sink;!v.equalsIgnoreCase(source);v=(String)theParentNode.get(v))
                {
                    //
                    u = (String)theParentNode.get(v);
                    int t1 = getResidualValues(u, v, theResidualGraph);

                    // the is the bottle neck of the given augmented path
                    theTemp = Math.min(t1, theTemp);
                }
                for(v=sink;!v.equalsIgnoreCase(source);v=(String)theParentNode.get(v))
                {
                    u =(String)theParentNode.get(v);
                    //this will update the residual graph
                    setResidualValuesToCurrent(u,v,theTemp,theResidualGraph);
                }
                maxflow += theTemp;
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return maxflow;
    }

    //the following function finds the possible augmented paths from source to the sink using BFS
    public static Boolean theAugumentedPath(Hashtable theInputGraph, Hashtable theParent)
    {
        // Hashtable will contain the visited state of nodes
        Hashtable theVisitedNodes = new Hashtable();
        try
        {
            for(int i=0;i<vertices.size();i++)
            {
                // Set state of all visited nodes to false
                theVisitedNodes.put(vertices.get(i), false);
            }

            // the linked list is used to store edges of the path that can be used and setting them to true
            LinkedList theQueue = new LinkedList();
            theQueue.add(source);
            theVisitedNodes.replace(source, true);
            theParent.put(source, -1);
            while(!theQueue.isEmpty())
            {
                // the element at the top of the queue
                String theHead = (String)theQueue.poll();

                for(Enumeration en = theInputGraph.keys();en.hasMoreElements();)
                {
                    String key = (String)en.nextElement();
                    if((boolean)theVisitedNodes.get(key)==false && getResidualValues(theHead,key,theInputGraph) > 0)
                    {
                        theQueue.add(key);
                        theParent.put(key, theHead);
                        theVisitedNodes.put(key,true);
                    }
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return ((boolean)theVisitedNodes.get(sink));
    }

    //this function will update the residual graph
    public static void setResidualValuesToCurrent(String u, String v, int theValue, Hashtable theInputGraph)
    {
        try
        {
            //forward edge value from the residual graph for the given edge
            Vertex theVertex = (Vertex)theInputGraph.get(u);
            LinkedList theLinkedList = theVertex.incidentEdgeList;
            Iterator theIterator = (Iterator)theLinkedList.iterator();
            while(theIterator.hasNext())
            {
                Edge theEdge = (Edge)theIterator.next();
                Vertex theSecondVertex = (Vertex)theEdge.getSecondEndpoint();
                String theSecondVertexName = (String)theSecondVertex.getName();
                if(theSecondVertexName.equalsIgnoreCase(v))
                {
                    int tempValue = (int)theEdge.getData();
                    tempValue -= theValue;
                    theEdge.setData(tempValue);
                }
            }

            //reverse edge value from the residual graph for the given edge
            theVertex = (Vertex)theInputGraph.get(v);
            theLinkedList = theVertex.incidentEdgeList;
            theIterator = (Iterator)theLinkedList.iterator();
            while(theIterator.hasNext())
            {
                Edge theEdge = (Edge)theIterator.next();
                Vertex theSecondVertex = (Vertex)theEdge.getSecondEndpoint();
                String theSecondVertexName = (String)theSecondVertex.getName();
                if(theSecondVertexName.equalsIgnoreCase(u))
                {
                    int tempValue = (int)theEdge.getData();
                    tempValue += theValue;
                    theEdge.setData(tempValue);
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    //the following function retrieves the forward edge value of the give edge from the residual graph
    public static int getResidualValues(String u, String v, Hashtable theInputGraph)
    {
        int theValue = 0;
        try
        {
            Vertex theVertex = (Vertex)theInputGraph.get(u);
            LinkedList theLinkedList = theVertex.incidentEdgeList;
            Iterator it = (Iterator)theLinkedList.iterator();
            while(it.hasNext())
            {
                Edge theEdge = (Edge)it.next();
                Vertex theSecondVertex = (Vertex)theEdge.getSecondEndpoint();
                String theSecondVertexName = (String)theSecondVertex.getName();
                if(theSecondVertexName.equalsIgnoreCase(v))
                {
                    theValue = (Integer) theEdge.getData();
                    break;
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            theValue = 0;
        }
        return theValue;
    }


    //the following function retrieves all the vertices of the generated graph
    public static void getAllVertices(Hashtable theInputGraph)
    {
        try
        {
            for(Enumeration en=theInputGraph.keys();en.hasMoreElements();)
            {
                vertices.add((String)en.nextElement());
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    // helper method
    public static String calculateMaxFlow(SimpleGraph theSimpleGraph)
    {
        return calculateMaxFlow(theSimpleGraph, "s", "t");
    }

    // calculates the maximum flow
    public static String calculateMaxFlow(SimpleGraph theSimpleGraph, String theSource, String theSink) {
        int maxflow = 0;

        // these will store the start time and the end time to compute a final running time
        long start, stop;

        // Current time at the start of finding maxflow
        start = System.currentTimeMillis();

        LinkedList<Vertex> V = theSimpleGraph.vertexList;
        Hashtable theHashValue = new Hashtable<>();

        for (Vertex v : V) {
            theHashValue.put(v.getName(), v);
        }

        source = theSource;
        sink = theSink;
        maxflow = theMaximumFlow(theHashValue);

        // Current Time at the end of finding maxFlow
        stop = System.currentTimeMillis();

        return "Runtime: " + (stop - start) + " ms" + "	Max Flow: " + maxflow;
    }
}

