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

    public static int calculateMaxFlow(SimpleGraph sg, String source, String sink) {
        ScalingFordFulkerson.source = source;
        ScalingFordFulkerson.sink = sink;
        return calculateMaxFlow(sg);
    }

    public static int calculateMaxFlow(SimpleGraph sg) {

        int maxflow = 0;
        int flow = 0;
        int cnt = 0;
        int delta = Integer.MAX_VALUE;
        while (delta > 0) {

            while ((flow = AugmentPath(sg, delta)) > 0) {
                maxflow += flow;
            }
            delta /= 2;
        }
        return maxflow;
    }

    public static int AugmentPath(SimpleGraph sg, int delta) {
        int flow = 0;
        Vertex s = null;
        for (Iterator ite = sg.vertices(); ite.hasNext();) {
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
                if (next == null && (Integer) e.getData() >= delta) {
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
        updateResGraph(sg, list, last, edges, flow);
        return flow;
    }

    private static void updateResGraph(SimpleGraph sg, ArrayList list, ArrayList last, ArrayList edges, int flow) {

        for (int i = list.size() - 1; i != 0;) {
            int j = (Integer) last.get(i);
            Vertex u = (Vertex) list.get(i);
            Vertex v = (Vertex) list.get(j);
            Edge e = (Edge) edges.get(i);
            sg.insertEdge(u, v, flow, null);
            e.setData((Integer) e.getData() - flow);
            i = j;
        }
    }
}

