import algorithms.FordFulkerson;
import algorithms.PreFlowPush;
import algorithms.ScalingFordFulkerson;
import graphs.*;

/*
 * Authors : Harnidh Kaur
 *           Fares Tabet
 *           Jingru Zhao
 *           Richard Brun
 * Description : Driver class for network flow algorithms implementation
 */

public class tcss543
{
    public static void main(String[] args) {

        String[] inputs = null;
        inputs = new String[] { args[0] };

        for (String input : inputs) {
            String source = null, sink = null;
            SimpleGraph sg;
            source = "s";
            sink = "t";
            sg = new SimpleGraph();
            GraphInput.LoadSimpleGraph(sg, input);
            System.out.println("<The graph in " + input + ">");

			System.out.println("---Ford Fulkerson Algorithm---");
			for (int i = 0; i < 5; ++i) {
                SimpleGraph rg3 = new SimpleGraph(sg);
                System.out.println("Run " + (i+1) + ": " + FordFulkerson.calculateMaxFlow(rg3, source, sink));
			}

			System.out.println("---Scaling Max-Flow Algorithm---");
			for (int i = 0; i < 5; ++i) {
                SimpleGraph rg3 = new SimpleGraph(sg);
                System.out.println("Run " + (i+1) + ": " + ScalingFordFulkerson.calculateMaxFlow(rg3, source, sink));
			}

            System.out.println("---Preflow Push Algorithm---");
            for (int i = 0; i < 5; ++i) {
                SimpleGraph rg3 = new SimpleGraph(sg);
                System.out.println("Run " + (i+1) + ": " + PreFlowPush.calculateMaxFlow(rg3, source, sink));
            }
        }
    }
}
