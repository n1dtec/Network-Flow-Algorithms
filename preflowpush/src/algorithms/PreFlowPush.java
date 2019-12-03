package algorithms;

import graphs.Edge;
import graphs.SimpleGraph;
import graphs.Vertex;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

/*
 * Authors : Harnidh Kaur
 *           Fares Tabet
 * Description : Driver class for preflow push algorithm implementation
 */

public class PreFlowPush {
    static String s;
    static String t;

    public static String calculateMaxFlow(SimpleGraph sg, String s, String t) {
        PreFlowPush.s = s;
        PreFlowPush.t = t;
        return PreFlowPush(sg);
    }

    private static void push(SimpleGraph sg, HashMap<Vertex, Integer> excessFlowMap, Vertex v, Vertex w, Edge e) {

        int flowToBePushed = Integer.min((Integer) e.getData(), excessFlowMap.get(v));
        if (flowToBePushed > 0) {

            // Subrtract the flow leaving from v
            excessFlowMap.put(v, excessFlowMap.get(v) - flowToBePushed);
            // Add the flow entering w
            excessFlowMap.put(w, excessFlowMap.get(w) + flowToBePushed);

            // Update the residual graph
            e.setData((Integer) e.getData() - flowToBePushed);
            sg.insertEdge(w, v, flowToBePushed, null);
        }
    }

    private static void relabel(HashMap<Vertex, Integer> heightMap, Vertex v, int minHeight) {
        heightMap.put(v, minHeight + 1);
    }

    static public String PreFlowPush(SimpleGraph sg) {
        int maxFlow = 0;
        long start, stop;
        // Current Time at the start of finding maxflow
        start = System.currentTimeMillis();
        // The hash map to store excess flow
        HashMap<Vertex, Integer> excessFlowMap = new HashMap<>();
        // The hash map to store height of all vertices
        HashMap<Vertex, Integer> heightMap = new HashMap<>();

        LinkedList<Vertex> adjacentNodes = new LinkedList();

        Vertex s = null;
        Vertex t = null;

        // Initialization of excessFlowMap and heightMap
        for (Iterator ite = sg.vertices(); ite.hasNext();) {
            Vertex v = (Vertex) ite.next();
            excessFlowMap.put(v, 0);
            if (v.getName().equals("s")) {
                s = v;
                heightMap.put(v, sg.numVertices());
            } else {
                heightMap.put(v, 0);
            }
            if (v.getName().equals("t")) {
                t = v;
            }
        }

        // Initialise the flow of every node adjacent to source
        for (int i = 0; i < s.incidentEdgeList.size(); i++) {
            Edge e = (Edge) s.incidentEdgeList.get(i);
            Vertex w = e.getSecondEndpoint();
            adjacentNodes.add(w);
            int sEdgeCapacity = (Integer) e.getData();
            excessFlowMap.put(s, excessFlowMap.get(s) - sEdgeCapacity);
            excessFlowMap.put(w, excessFlowMap.get(w) + sEdgeCapacity);

            //Update the residual graph
            e.setData((Integer) e.getData() - sEdgeCapacity);
            sg.insertEdge(w, s, sEdgeCapacity, null);
        }

        while (!adjacentNodes.isEmpty()) {

            Vertex v = adjacentNodes.pop();
            // If node v has excess flow, and is not t(sink)
            while ( !v.getName().equals("t") && excessFlowMap.get(v) > 0 ) {
                boolean skipRelabel = false;
                int minHeight = Integer.MAX_VALUE;
                for (Object o : v.incidentEdgeList) {
                    Edge e = (Edge) o;
                    Vertex w = e.getSecondEndpoint();
                    // Find the minimum height amongst the adjacent nodes
                    if ((Integer) e.getData() > 0) {
                        minHeight = Integer.min(minHeight, heightMap.get(w));
                    }

                    //push if the adjacent edge has capacity and v has greater height than w
                    if ((Integer) e.getData() > 0 && heightMap.get(v) > heightMap.get(w)) {
                        push(sg, excessFlowMap, v, w, e);
                        skipRelabel = true;
                        if (excessFlowMap.get(w) > 0) {
                            adjacentNodes.add(w);
                        }
                    }
                }
                //relabel if adjacent edges don't have capacity
                if ( !skipRelabel && excessFlowMap.get(v) > 0 ) {
                    relabel(heightMap, v, minHeight);
                }
            }

        }

        maxFlow = excessFlowMap.get(t);

        // Current Time at the end of finding maxFlow
        stop = System.currentTimeMillis();

        // Return the result
        return "Runtime: " + (stop - start) + " ms" + "	Max Flow: " + maxFlow;
    }
}
