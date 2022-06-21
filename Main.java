import java.util.*;
import java.io.*;

public class Main {
    
    

    public static void main(String[] args) throws Exception {
        boolean doOnce = false;
        BufferedReader vhod = new BufferedReader(new InputStreamReader(System.in));
        HashMap<Integer, ArrayList<Integer>> graph = null;

        while (true) {

            System.out.println("*** ANALYZE GRAPH ***");
            System.out.println("- Press 0 to read the graph from a file");
            System.out.println("- Press 1 to print the adjacency matrix");
            System.out.println("- Press 2 to print num. of connections for a given node");
            System.out.println("- Press 3 to print clustering for a given node");
            System.out.println("- Press 4 to print a number of all connections");
            System.out.println("- Press 5 to print a node with most connections");
            System.out.println("- Press 6 to print the shortest path between two nodes");
            System.out.println("- Press 7 to print all nodes on a social distance 2 from a given node");
            System.out.println("- Press 8 to exit");
            System.out.println("*********************");
            int command = 15;
            boolean isInteger = false;

            while (!isInteger) {
                try {
                    System.out.print("Command: ");
                    command = Integer.parseInt(vhod.readLine().trim());
                    isInteger = true; 
                }
                catch (Exception e) {
                    System.out.println("\nEnter an integer!\n");
                }
            }

            switch (command) {
                case 0:
                    System.out.println();
                    System.out.println("Path to file:");
                    String filePath = vhod.readLine().trim().replace("\\","/"); //dvakrat izpise file not found
                    graph = Function.readFromFile(filePath);
                    System.out.println();
                    break;
                case 1:
                    if (graph != null) {
                        int[][] matrix = Function.adjacencyMatrix(graph);
                        System.out.println();
                        System.out.println("Adjacency matrix:");
                        for (int i = 0; i < matrix.length; i++) {
                            for (int j = 0; j < matrix[i].length; j++) {
                                System.out.print(matrix[i][j] + " ");
                            }
                            System.out.println();
                        }
                        System.out.println();
                    }
                    else {
                        System.out.println("\nNo graph has been read from a file\n");
                    }

                    
                    break;
                case 2:
                    if (graph != null) {
                        try {
                        
                            System.out.println("\nEnter a node: ");
                            int node = Integer.parseInt(vhod.readLine().trim());
                            int numOfConns = Function.nodeConnectionCount(graph, node);
                            System.out.println("\nNumber of connections for node " + node + " is: " + numOfConns + "\n");
                            
    
                        }
                        catch (Exception e) {
                            System.out.println("Enter an integer!");
                        }
                    }
                    else {
                        System.out.println("\nNo graph has been read from a file\n");
                    }

                    break;
                case 3:
                    if (graph != null) {
                        try {
                        
                            System.out.println("\nEnter a node: ");
                            int node = Integer.parseInt(vhod.readLine().trim());
                            double clustering = Function.nodeClustering(graph, node);
                            clustering = Math.round(clustering * 100.0) / 100.0;
                            System.out.println("\nClustering for node " + node + " is: " + clustering + "\n");
                            
    
                        }
                        catch (Exception e) {
                            System.out.println("Enter an integer!");
                        }
                    }
                    else {
                        System.out.println("\nNo graph has been read from a file\n");
                    }
                    break;
                case 4:
                    if (graph != null) {
                        int countAllConnections = Function.allConnectionsCount(graph);
                        System.out.println("\nGraph has a total number of " + countAllConnections + " connections.\n");
                    }
                    else {
                        System.out.println("\nNo graph has been read from a file\n");
                    }

                    break;
                case 5:
                    if (graph != null) {
                        int node = Function.nodeMaxConnectionCount(graph);
                        System.out.println("\nThe node with most connections is " + node + "\n");
                    }
                    else {
                        System.out.println("\nNo graph has been read from a file\n");
                    }

                    break;
                case 6:
                    if (graph != null) {
                        try {
                            
                            System.out.println("\nEnter the first node: ");
                            int node1 = Integer.parseInt(vhod.readLine().trim());
                            System.out.println("\nEnter the second node: ");
                            int node2 = Integer.parseInt(vhod.readLine().trim());
                            int len = Function.outputShortestDistance(graph, node1, node2, doOnce);
                            doOnce = true;
                            System.out.println("\nShortest path length between " + node1 + " and " + node2 + " is: " + len + "\n");
                    
                        }
                        catch (Exception e) {
                            System.out.println("Enter an integer!");
                        }
                    }
                    else {
                        System.out.println("\nNo graph has been read from a file\n");
                    }

                    break;
                case 7:
                    
                    if (graph != null) {
                        try {
                            
                            System.out.println("\nEnter the first node: ");
                            int node1 = Integer.parseInt(vhod.readLine().trim());
    
                            
                            ArrayList<Integer> nodes = Function.outputSocialDistance2(graph, node1, doOnce);
                            
                            System.out.println();
                            if (nodes.size() != 0) {
                                for (int node2 : nodes) {
                                    System.out.println("Node " + node2 + " is on a social distance of 2 from node " + node1);
                                }
                            }
                            else {
                                System.out.println("No nodes are on a social distance of 2 from node " + node1);
                            }
                            System.out.println();
                    
                        }
                        catch (Exception e) {
                            System.out.println("Enter an integer!");
                        }
                    }
                    else {
                        System.out.println("\nNo graph has been read from a file\n");
                        
                    }
                    
                    break;
                case 8:
                    return;
                default:
                    System.out.println("\nUnknown command!\n");
                    break;
            }

        }
  
    } 
    
}