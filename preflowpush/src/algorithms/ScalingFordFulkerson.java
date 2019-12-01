package algorithms;

import graphs.Edge;
import graphs.SimpleGraph;
import graphs.Vertex;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;

public class ScalingFordFulkerson {

    private static String source;
    private static String sink;

    public static String calculateMaxFlow(SimpleGraph sg, String source, String sink) {
        ScalingFordFulkerson.source = source;
        ScalingFordFulkerson.sink = sink;
        return ScalingFordFulkerson(sg);
    }

    public static String ScalingFordFulkerson(SimpleGraph sg) {

        int maxflow = 0;
        long start, stop;
        // Current Time at the start of finding maxflow
        start = System.currentTimeMillis();
        int flow = 0;
        int cnt = 0;
        int delta = Integer.MAX_VALUE;
        //repeatedly find augmenting paths from source to sink using only edges
        while (delta > 0) {

            while ((flow = OtherPath(sg, delta)) > 0) {
                maxflow += flow;
            }
            delta /= 2;
        }
        // Current Time at the end of finding maxFlow
        stop = System.currentTimeMillis();
        // Return the result
        return "Runtime: " + (stop - start) + " ms" + "	Max Flow: " + maxflow;
    }

    public static int OtherPath(SimpleGraph graph, int d) {
        int flow = 0;
        Vertex s = null;
        for (Iterator ite = graph.vertices(); ite.hasNext();) {
            Vertex v = (Vertex) ite.next();
            if (v.getName().equals(source)) {
                s = v;
                break;
            }
        }
        ArrayList list = new ArrayList();
        ArrayList flist = new ArrayList();
        ArrayList last = new ArrayList();
        ArrayList edges = new ArrayList();
        Hashtable visited = new Hashtable();
        list.add(s);
        flist.add(Integer.MAX_VALUE);
        last.add(-1);
        edges.add(new Object());
        visited.put(s.getName(), s);
        for (int i = 0; i < list.size(); ++i) {
            Vertex v = (Vertex) list.get(i);
            boolean find = false;
            for (Iterator ite = v.incidentEdgeList.iterator(); ite.hasNext();) {
                Edge e = (Edge) ite.next();
                Vertex u = e.getSecondEndpoint();
                Vertex next = (Vertex) visited.get(u.getName());
                if (next == null && (Integer) e.getData() >= d) {
                    list.add(u);
                    flist.add(Integer.min((Integer) flist.get(i), (Integer) e.getData()));
                    edges.add(e);
                    last.add(i);
                    visited.put(u.getName(), u);
                    if (u.getName().equals(sink)) {
                        flow = Integer.min((Integer) flist.get(i), (Integer) e.getData());
                        find = true;
                        break;
                    }
                }
            }
            if (find) {
                break;
            }

        }
        //update the response graph
        for (int i = list.size() - 1; i != 0;) {
            int j = (Integer) last.get(i);
            Vertex u = (Vertex) list.get(i);
            Vertex v = (Vertex) list.get(j);
            Edge e = (Edge) edges.get(i);
            graph.insertEdge(u, v, flow, null);
            e.setData((Integer) e.getData() - flow);
            i = j;
        }

        return flow;
    }

}

