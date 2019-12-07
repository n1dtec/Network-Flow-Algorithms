# Network-Flow Algorithms

TCSS 543 Final Project(Fall 2019) for Prof. Ka Yee Yeung

Contributors:
1. Harnidh Kaur
2. Fares Tabet
3. Richard Brun
4. Jingru Zhao

This Java project contains the following:
1. The three network flow algorithms implemented by us - Ford Fulkerson, Scaling ford Fulkerson and Preflow push (src -> algorithms)
2. The test graphs which we used to test them on (src -> graphs)

You will find all the graphs we generated for testing in their respective folders under src —> graphs —> {Bipartite,FixedDegree,Random,Mesh} 
 
To test the algorithms, you need to follow the below steps:
1. Copy the path of the desired graph file
2. Pass the copied file path as an input argument to the class tcss543.java located in the src folder

The three algorithms will run 5 times each on the input graph file and display the following on the console for each run of the graph:
1. Runtime
2. Maximum flow
