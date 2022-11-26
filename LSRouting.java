import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.lang.*;
import java.net.*;
import java.util.*;
import javax.swing.*;

public class LSRouting {

  JTextArea ta;

  //Constructor , GUI code
  LSRouting() {
    JFrame frame = new JFrame("Link State Routing");
    ta = new JTextArea();
    JScrollPane TAS = new JScrollPane(ta);
    JLabel h1 = new JLabel("Routing Tables Display Area");
    h1.setBounds(50, 10, 450, 50);
    TAS.setBounds(50, 50, 600, 700);

    frame.add(h1);
    frame.add(TAS);

    frame.setSize(1500, 800);
    frame.setLayout(null);
    frame.setVisible(true);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }


  //Function to Display Routing Table Calculators.
  public void display(String s) {
    ta.append(s);
  }


  //Function to calculate Routing Tables Using BFA.
  public static String routingcal(int[][] graph, int source) {
    StringBuilder sb = new StringBuilder();
    int count = graph.length;

    boolean[] visitedVertex = new boolean[count];
    int[] distance = new int[count];
    for (int i = 0; i < count; i++) {
      visitedVertex[i] = false;
      distance[i] = Integer.MAX_VALUE;
    }


    distance[source] = 0;
    for (int i = 0; i < count; i++) {
      int u = findMinDistance(distance, visitedVertex);
      visitedVertex[u] = true;

      for (int v = 0; v < count; v++) {
        if (
          !visitedVertex[v] &&
          graph[u][v] != 0 &&
          (distance[u] + graph[u][v] < distance[v])
        ) {
          distance[v] = distance[u] + graph[u][v];
        }
      }
    }

    for (int i = 0; i < distance.length; i++) {
      if (i == 0) {
        sb.append(String.format("ROUTING TABLE FOR NODE %s\n\n", source));
        sb.append("Source Node    Destination Node    Least Cost\n");
      }
      sb.append(
        String.format(
          "     %s                          %s                                %s\n",
          source,
          i,
          distance[i]
        )
      );
      if (i == distance.length - 1) {
        return sb.toString();
      }
    }

    return sb.toString();
  }

  //Function to find minimum distance.
  private static int findMinDistance(int[] distance, boolean[] visitedVertex) {
    int minDistance = Integer.MAX_VALUE;
    int minDistanceVertex = -1;
    for (int i = 0; i < distance.length; i++) {
      if (!visitedVertex[i] && distance[i] < minDistance) {
        minDistance = distance[i];
        minDistanceVertex = i;
      }
    }
    return minDistanceVertex;
  }

  





//Main Function
  public static void main(String[] args) {
    int graph[][] = new int[][] {
      { 0, 3, 0, 0, 0, 0, 0, 8, 0 },
      { 3, 0, 8, 0, 0, 0, 0, 10, 0 },
      { 0, 8, 0, 7, 0, 4, 0, 0, 2 },
      { 0, 0, 7, 0, 10, 14, 0, 0, 0 },
      { 0, 0, 0, 10, 0, 10, 0, 0, 0 },
      { 0, 0, 4, 0, 10, 0, 2, 0, 0 },
      { 0, 0, 0, 14, 0, 2, 0, 1, 6 },
      { 8, 10, 0, 0, 0, 0, 1, 0, 7 },
      { 0, 0, 2, 0, 0, 0, 6, 7, 0 }
    }; //Initalizing the Graph

    LSRouting T = new LSRouting(); //Instantiating the object.

    String str = new String();
    for (int m = 0; m < graph.length; m++) {
      str+=T.routingcal(graph, m);
    }
    T.display(str); //Calling Display Function
  }
}