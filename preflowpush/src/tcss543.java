import algorithms.PreFlowPush;
import graphs.*;

import java.io.BufferedReader;
import java.util.Hashtable;

/*
 * Authors : Harnidh Kaur
 *           Fares Tabet
 * Description : Driver class for preflow push algorithm implementation
 */

public class tcss543
{
    public static void main(String[] args) {

        String[] inputs = null;
        inputs = new String[] { args[0] };
//        if (args.length == 0) {
//            inputs = new String[] { "-" };
//        } else if (args.length == 1) {
//            if (args[0].equals("-all")) {
//                BufferedReader in = InputLib.fopen("C:\\Users\\harni\\IdeaProjects\\TCSS543-master\\TCSS543-master\\inputs.txt");
//                String line = InputLib.getLine(in);
//                StringBuilder all = new StringBuilder();
//                while (line != null) {
//                    all.append(line);
//                    System.out.println(line);
//                    line = InputLib.getLine(in);
//                    if (line != null) {
//                        all.append(';');
//                    }
//                }
//                inputs = all.toString().split(";");
//            } else {
//                inputs = new String[] { args[0] };
//            }
//        } else {
//            System.out.println("Invalid arguments");
//            return;
//        }

        for (String input : inputs) {
            String source = null, sink = null;
            SimpleGraph sg;
            source = "s";
            sink = "t";
            sg = new SimpleGraph();
            GraphInput.LoadSimpleGraph(sg, input);
            System.out.println("<The graph in " + input + ">");
//            if (input.equals("-")) {
//                sg = new SimpleGraph();
//                Hashtable hash = GraphInput.LoadSimpleGraph(sg, input);
//                System.out.println("<The graph from user input>");
//            } else {
//                sg = new SimpleGraph();
//                GraphInput.LoadSimpleGraph(sg, input);
//                System.out.println("<The graph in " + input + ">");
//            }

//			System.out.println("---Ford Fulkerson Algorithm---");
//			for (int i = 0; i < 5; ++i) {
//				SimpleGraph rg1 = cloneGraph(sg);
//				start = System.currentTimeMillis();
//				ans = FordFulkerson.MaxFlow(rg1, source, sink);
//				stop = System.currentTimeMillis();
//				System.out.println("Max Flow: " + ans + "    Time: " + (stop - start) + " ms");
//			}
//
//			System.out.println("---Scaling Max-Flow Algorithm---");
//			for (int i = 0; i < 5; ++i) {
//				SimpleGraph rg2 = cloneGraph(sg);
//				start = System.currentTimeMillis();
//				ans = ScalingMaxFlow.MaxFlow(rg2, source, sink);
//				stop = System.currentTimeMillis();
//				System.out.println("Max Flow: " + ans + "    Time: " + (stop - start) + " ms");
//			}

            System.out.println("---Preflow Push Algorithm---");
            for (int i = 0; i < 5; ++i) {
                SimpleGraph rg3 = new SimpleGraph(sg);
                System.out.println("Run " + (i+1) + ": " + PreFlowPush.calculateMaxFlow(rg3, source, sink));
            }
        }
    }
}
