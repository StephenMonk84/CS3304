import java.util.*;

class Graph {
    // Adjacency list representation: node -> list of (neighbor, weight)
    private final Map<Integer, List<Edge>> adjacencyList = new HashMap<>();

    // Add edge (undirected or directed depending on your use case)
    public void addEdge(int source, int destination, int weight) {
        adjacencyList.putIfAbsent(source, new ArrayList<>());
        adjacencyList.putIfAbsent(destination, new ArrayList<>());
        adjacencyList.get(source).add(new Edge(destination, weight));
        // If roads are bidirectional, uncomment:
        // adjacencyList.get(destination).add(new Edge(source, weight));
    }

    public Map<Integer, List<Edge>> getAdjacencyList() {
        return adjacencyList;
    }
}

class Edge {
    int destination;
    int weight;

    public Edge(int destination, int weight) {
        this.destination = destination;
        this.weight = weight;
    }
}

class Dijkstra {
    public static Map<Integer, Integer> shortestPath(Graph graph, int source) {
        Map<Integer, List<Edge>> adj = graph.getAdjacencyList();

        // Handle empty graph
        if (adj.isEmpty()) {
            System.out.println("Graph is empty.");
            return Collections.emptyMap();
        }

        // Distance map: node -> shortest distance from source
        Map<Integer, Integer> distances = new HashMap<>();
        for (Integer node : adj.keySet()) {
            distances.put(node, Integer.MAX_VALUE);
        }
        distances.put(source, 0);

        // Priority queue for greedy selection (min-heap based on distance)
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));
        pq.offer(new int[]{source, 0});

        while (!pq.isEmpty()) {
            int[] current = pq.poll();
            int currentNode = current[0];
            int currentDistance = current[1];

            // Skip outdated entries
            if (currentDistance > distances.get(currentNode)) continue;

            // Explore neighbors
            for (Edge edge : adj.getOrDefault(currentNode, Collections.emptyList())) {
                int newDist = currentDistance + edge.weight;
                if (newDist < distances.get(edge.destination)) {
                    distances.put(edge.destination, newDist);
                    pq.offer(new int[]{edge.destination, newDist});
                }
            }
        }

        return distances;
    }
}

public class NavigationSystem {
    public static void main(String[] args) {
        Graph roadNetwork = new Graph();

        // Example road network (nodes represent intersections)
        roadNetwork.addEdge(0, 1, 4);
        roadNetwork.addEdge(0, 2, 1);
        roadNetwork.addEdge(2, 1, 2);
        roadNetwork.addEdge(1, 3, 1);
        roadNetwork.addEdge(2, 3, 5);

        int source = 0;
        Map<Integer, Integer> shortestPaths = Dijkstra.shortestPath(roadNetwork, source);

        // Print results
        for (Map.Entry<Integer, Integer> entry : shortestPaths.entrySet()) {
            int node = entry.getKey();
            int distance = entry.getValue();
            if (distance == Integer.MAX_VALUE) {
                System.out.println("Node " + node + " is unreachable from source " + source);
            } else {
                System.out.println("Shortest distance from " + source + " to " + node + " = " + distance);
            }
        }
    }
}