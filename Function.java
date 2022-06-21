
import java.util.*;
import java.io.*;

public class Function {

    
    public static void transformFile(String pathToFile) throws Exception {
        String content = "";
        try {
            BufferedReader dat = new BufferedReader(new FileReader(pathToFile));
            boolean transformed = false;
            while (dat.ready()) {
                String line1 = dat.readLine().trim();
                String line2 = dat.readLine().trim();
                if (!line1.equals("") && !line2.equals("")) {
                    line1 = line1.replaceAll("\\s","");
                    line2 = line2.replaceAll("\\s","");
                    if ((line1.charAt(0) == line2.charAt(1)) && (line1.charAt(1) == line2.charAt(0))) {
                        transformed = true;
                        break;
                    }
                }
                break;
            }
            dat.close();
            if (!transformed) {
                dat = new BufferedReader(new FileReader(pathToFile));
                while (dat.ready()) {
                    String line = dat.readLine().trim();
                    if (!line.equals("")) {
                        content += line + "\n";                    
                        line = line.replaceAll("\\s","");
                        int node2 = Integer.parseInt(line.charAt(0) + "");
                        int connectionNode2 = Integer.parseInt(line.charAt(1) + "");
                        content += (connectionNode2 + " " + node2 + "\n");
                    }
                }
                dat.close();
                PrintWriter datW = new PrintWriter(new FileWriter(pathToFile));
                datW.println(content);
                datW.close();
            }

        }
        catch (FileNotFoundException fnfe) {
            System.out.println("File not found!");
        }
        
    }


    public static HashMap<Integer, ArrayList<Integer>> readFromFile(String pathToFile) throws Exception {
        HashMap<Integer, ArrayList<Integer>> graph = null;
        ArrayList<Integer> arrayList = null;
        ArrayList<Integer> nodes = null;
        try {
            transformFile(pathToFile);
            BufferedReader dat = new BufferedReader(new FileReader(pathToFile));
            graph = new HashMap<Integer, ArrayList<Integer>>();
            nodes = new ArrayList<Integer>();
            while (dat.ready()) {
                String line = dat.readLine();
                if (!line.equals("")) {
                    line = line.replaceAll("\\s","");
                    int node = Integer.parseInt(line.charAt(0) + "");
                    boolean exists = false;
                    for (int i = 0; i < nodes.size(); i++) {
                        if (node == nodes.get(i)) {
                            exists = true;
                            break;
                        }
                    }
                    if (!exists) {
                        nodes.add(node);
                    }
                }
            }
            dat.close();
            for (int i = 0; i < nodes.size(); i++) {
                int node = nodes.get(i);
                arrayList = new ArrayList<Integer>();
                dat = new BufferedReader(new FileReader(pathToFile));
                while (dat.ready()) {
                    String line = dat.readLine();
                    if (!line.equals("")) {
                        line = line.replaceAll("\\s","");
                        int node2 = Integer.parseInt(line.charAt(0) + "");
                        int connectionNode2 = Integer.parseInt(line.charAt(1) + "");
                        
                        if (node2 == node) {
                            boolean exists = false;
                            for (int j = 0; j < arrayList.size(); j++) {
                                if (arrayList.get(j) == connectionNode2) {
                                    exists = true;
                                    break;
                                }
                            }
                            if (!exists) {
                                arrayList.add(connectionNode2);
                            }
                        }
                    }
                }
                graph.put(node, arrayList);
            }
            dat.close();
        }
        catch (FileNotFoundException fnfe) {
            System.out.println("File not found");
        }

        return graph;
    }

    public static int nodeCount(HashMap<Integer, ArrayList<Integer>> graph) {
        return graph.size();
    }

    public static int[][] adjacencyMatrix(HashMap<Integer, ArrayList<Integer>> graph) {
        int numOfNodes = nodeCount(graph);
        int[][] matrix = new int[numOfNodes][numOfNodes];
        boolean startsWith0 = false;

        for (int node : graph.keySet()) { //we need to check if graph starts from 0 or 1
            if (node == 0) {
                startsWith0 = true;
                break;
            }
        }

        for (int node : graph.keySet()) {
            
            if (startsWith0) {
                for (int connectionNode : graph.get(node)) {
                    matrix[node][connectionNode] = 1;
                }
            }
            else {
                for (int connectionNode : graph.get(node)) {
                    matrix[node-1][connectionNode-1] = 1;
                }
            }
 
        }

        return matrix; 
    }

    public static int nodeConnectionCount(HashMap<Integer, ArrayList<Integer>> graph, int node) {
        int output = 0;

        for (int i : graph.keySet()) {
            if (i == node) {
                output = graph.get(i).size();
                break;
            }
        }

        return output;
    }


    public static int nodeMaxConnectionCount(HashMap<Integer, ArrayList<Integer>> graph) {
        int output = 0;
        int maxConnections = 0;
       
        for (int i : graph.keySet()) {
            int connectionCount = nodeConnectionCount(graph, i);
            if (connectionCount > maxConnections) {
                maxConnections = connectionCount;
                output = i;
            }

        }

        return output;
    }


    public static int allConnectionsCount(HashMap<Integer, ArrayList<Integer>> graph) {
        int output = 0;

        String connectionString, connectionStringReverse;
        boolean obstaja;
        ArrayList<String> connections = new ArrayList<String>();
        
        for (int i : graph.keySet()) {
            for (int connection : graph.get(i)) {
                connectionString = "" + i + "" + connection;
                connectionStringReverse = "" + connection + "" + i;
                obstaja = false;
                for (int j = 0; j < connections.size(); j++) {
                    if (connections.get(j).equals(connectionString) || connections.get(j).equals(connectionStringReverse)) {
                        obstaja = true;
                        break;
                    }
                }
                if (!obstaja) {
                    connections.add(connectionString);
                }
            }
        }

        output = connections.size();
        return output;
    }

    public static double nodeClustering(HashMap<Integer, ArrayList<Integer>> graph, int node) {
        double output = 0.0;

        int graphAllConnectionsCount = allConnectionsCount(graph);
        int nodeConnectionCount = nodeConnectionCount(graph, node);
        int nodeCount = nodeCount(graph);

        output = ((double)(graphAllConnectionsCount - nodeConnectionCount) / (double)(((nodeCount - 1) * (nodeCount - 2)) / 2));
    
        return output;
    }

    
    public static int outputShortestDistance(HashMap<Integer, ArrayList<Integer>> graph, int node1, int node2, boolean doOnce) {
        ArrayList<ArrayList<Integer>> connections = convertHashToList(graph);
        int length;
        boolean startsWith0 = false;

        for (int node : graph.keySet()) { //we need to check if graph starts from 0 or 1
            if (node == 0) {
                startsWith0 = true;
                break;
            }
        }
        
        if (startsWith0 == false) {
            
            if (doOnce == false) {
                for (int i = 0; i < connections.size(); i++) { //-1 so it is like we start the graph from 0 BUT ONLY ONCE
                    for (int j = 0; j < connections.get(i).size(); j++) {
                        connections.get(i).set(j, connections.get(i).get(j) - 1);
                     }
                }
            }
            length = calculateShortestDistance(connections, node1 - 1, node2 - 1, connections.size());
           
        }
        else {
            length = calculateShortestDistance(connections, node1, node2, connections.size());
        }
        
        return length;

    }

    public static ArrayList<ArrayList<Integer>> convertHashToList(HashMap<Integer, ArrayList<Integer>> graph) {
        
        int length = graph.size();
        ArrayList<ArrayList<Integer>> connections = new ArrayList<ArrayList<Integer>>(length);

        for (ArrayList<Integer> conns : graph.values()) {
            connections.add(conns);
        }

        return connections;
    }

    private static int calculateShortestDistance(ArrayList<ArrayList<Integer>> connections, int node1, int node2, int numNodes) {

        int pred[] = new int[numNodes];
        int dist[] = new int[numNodes];

        if (breathFirstSearch(connections, node1, node2, numNodes, pred, dist) == false) {
            if (node1 == node2) {
                System.out.println("\nNo min path for same node!");
            }
            else {
                System.out.println("\nThe two nodes are not connected!");
            }
            
            return 0;
        }

        LinkedList<Integer> path = new LinkedList<Integer>();
        int crawl = node2;
        path.add(crawl);
        while (pred[crawl] != -1) {
            path.add(pred[crawl]);
            crawl = pred[crawl];
        }

		return dist[node2];
    }

    private static boolean breathFirstSearch(ArrayList<ArrayList<Integer>> connections, int node1, int node2, int numNodes, int pred[], int dist[]) {

        LinkedList<Integer> queue = new LinkedList<Integer>();

        boolean visited[] = new boolean[numNodes];

        for (int i = 0; i < numNodes; i++) {
            visited[i] = false;
            dist[i] = Integer.MAX_VALUE;
            pred[i] = -1;
        }

        visited[node1] = true;
        dist[node1] = 0;
        queue.add(node1);

        
        while (!queue.isEmpty()) {
            int u = queue.remove();
            for (int i = 0; i < connections.get(u).size(); i++) {
                if (visited[connections.get(u).get(i)] == false) {
                    visited[connections.get(u).get(i)] = true;
                    dist[connections.get(u).get(i)] = dist[u] + 1;
                    pred[connections.get(u).get(i)] = u;
                    queue.add(connections.get(u).get(i));

                    if (connections.get(u).get(i) == node2)
                        return true;
                }
            }
        }
        return false;
    }

    
    public static ArrayList<Integer> outputSocialDistance2 (HashMap<Integer, ArrayList<Integer>> graph, int node, boolean doOnce){

        ArrayList<Integer> list = new ArrayList<Integer>();
        
        for (int node1 : graph.keySet()) {
            if (node1 != node) {
                int length = outputShortestDistance(graph, node, node1, doOnce);
                if (length == 2) {
                    list.add(node1);
                }
            }
        }

        return list;
    }
    

}


