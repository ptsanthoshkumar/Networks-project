# Networks-project
The end semester project for Computer Networks
Distance Vector Routing Protocol (DVRP) is a dynamic routing protocol in which each node (router) repeatedly shares its knowledge about the network only with those routers with the explicit connection. The sharing of this information takes place at regular intervals. The underlying principle for this protocol is Bellman-Ford Algorithm.
Link State Routing Protocol (LSRP) is a dynamic routing protocol in which each router shares knowledge of its neighbors with every other router in the network. Information exchange takes place only when there is a change. The underlying principle for this protocol is Dijkstra’s Algorithm. 
Here we design the Routing Table Calculator. This routing table calculator will be provided with the information of nodes which includes distance and the connections with others. we will get output which will contain routing tables of every node.

Compiling the LSRouting.java file is pretty straight forward: javac LSROuting.java
Running LSRouting.java: java LSRouting

In the case of Distance vector routing protocol:
              Compiling DVRPServer first: javac DVRPServer.java
              Running DVRPServer first: java DVRPServer
              Compiling DVRPClient afterthe port is generated: javac DVRPClient.java
              Running DVRPClient: java DVRPClient
Enter inputs with ASCII inputs, if any node is entered with an error we can delete it with the delete function in the GUI.
For the routing table to appear
