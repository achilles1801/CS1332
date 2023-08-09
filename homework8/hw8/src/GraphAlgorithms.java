import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

/**
 * Your implementation of various different graph algorithms.
 *
 * @author Majd Khawaldeh
 * @version 1.0
 * @userid majd
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 * <p>
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class GraphAlgorithms {

    /**
     * Performs a breadth first search (bfs) on the input graph, starting at
     * the parameterized starting vertex.
     * <p>
     * When exploring a vertex, explore in the order of neighbors returned by
     * the adjacency list. Failure to do so may cause you to lose points.
     * <p>
     * You may import/use java.util.Set, java.util.List, java.util.Queue, and
     * any classes that implement the aforementioned interfaces, as long as they
     * are efficient.
     * <p>
     * The only instance of java.util.Map that you may use is the
     * adjacency list from graph. DO NOT create new instances of Map
     * for BFS (storing the adjacency list in a variable is fine).
     * <p>
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the bfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph
     */
    public static <T> List<Vertex<T>> bfs(Vertex<T> start, Graph<T> graph) {

        if (start == null || graph == null) {
            throw new IllegalArgumentException("no");
        }
        Map<Vertex<T>, List<VertexDistance<T>>> adjlist = graph.getAdjList();
        if (!adjlist.containsKey(start)) {
            throw new IllegalArgumentException("something");
        }
        Set<Vertex<T>> visited = new HashSet<>();
        List<Vertex<T>> visitedOrder = new ArrayList<>();
        Queue<Vertex<T>> queue = new LinkedList<>();


        queue.add(start);
        while (!queue.isEmpty()) {
            Vertex<T> curr = queue.remove();
            if (!visited.contains(curr)) {
                visited.add(curr);
                visitedOrder.add(curr);

                List<VertexDistance<T>> neighbors = adjlist.get(curr);
                for (VertexDistance<T> neighbor : neighbors) {
                    queue.add(neighbor.getVertex());
                }
            }
        }
        return visitedOrder;
    }

//        if (start == null || graph == null) {
//            throw new IllegalArgumentException("Inputs cannot be null");
//        }
//
//        Map<Vertex<T>, List<VertexDistance<T>>> adjList = graph.getAdjList();
//        if (!adjList.containsKey(start)) {
//            throw new IllegalArgumentException("Start vertex does not exist in the graph");
//        }
//
//        List<Vertex<T>> visitedOrder = new ArrayList<>();
//        Set<Vertex<T>> visited = new HashSet<>();
//        Queue<Vertex<T>> queue = new LinkedList<>();
//
//        queue.add(start);
//
//        while (!queue.isEmpty()) {
//            Vertex<T> current = queue.remove();
//            if (!visited.contains(current)) {
//                visitedOrder.add(current);
//                visited.add(current);
//
//                List<VertexDistance<T>> neighbors = adjList.get(current);
//                for (VertexDistance<T> neighbor : neighbors) {
//                    queue.add(neighbor.getVertex());
//                }
//            }
//        }
//        return visitedOrder;
    }


    /**
     * Performs a depth first search (dfs) on the input graph, starting at
     * the parameterized starting vertex.
     * <p>
     * When exploring a vertex, explore in the order of neighbors returned by
     * the adjacency list. Failure to do so may cause you to lose points.
     * <p>
     * *NOTE* You MUST implement this method recursively, or else you will lose
     * all points for this method.
     * <p>
     * You may import/use java.util.Set, java.util.List, and
     * any classes that implement the aforementioned interfaces, as long as they
     * are efficient.
     * <p>
     * The only instance of java.util.Map that you may use is the
     * adjacency list from graph. DO NOT create new instances of Map
     * for DFS (storing the adjacency list in a variable is fine).
     * <p>
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the dfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph
     */
    public static <T> List<Vertex<T>> dfs(Vertex<T> start, Graph<T> graph) {
        if (start == null || graph == null) {
            throw new IllegalArgumentException("Inputs cannot be null");
        }

        Map<Vertex<T>, List<VertexDistance<T>>> adjList = graph.getAdjList();
        if (!adjList.containsKey(start)) {
            throw new IllegalArgumentException("Start vertex does not exist in the graph");
        }

        List<Vertex<T>> visitedOrder = new ArrayList<>();
        Set<Vertex<T>> visited = new HashSet<>();
        dfsHelper(start, graph, visitedOrder, visited);

        return visitedOrder;
    }

    /**
     * A helper method for depth-first search (DFS) that visits each vertex recursively.
     *
     * @param <T>          the generic typing of the data
     * @param vertex       the current vertex to begin the DFS on
     * @param graph        the graph to search through
     * @param visitedOrder list of vertices in visited order
     * @param visited      set of vertices that have been visited
     */
    private static <T> void dfsHelper(Vertex<T> vertex, Graph<T> graph, List<Vertex<T>> visitedOrder, Set<Vertex<T>> visited) {
        if (!visited.contains(vertex)) {
            visited.add(vertex);
            visitedOrder.add(vertex);

            List<VertexDistance<T>> neighbors = graph.getAdjList().get(vertex);
            for (VertexDistance<T> neighbor : neighbors) {
                dfsHelper(neighbor.getVertex(), graph, visitedOrder, visited);
            }
        }
    }

    /**
     * Finds the single-source shortest distance between the start vertex and
     * all vertices given a weighted graph (you may assume non-negative edge
     * weights).
     * <p>
     * Return a map of the shortest distances such that the key of each entry
     * is a node in the graph and the value for the key is the shortest distance
     * to that node from start, or Integer.MAX_VALUE (representing
     * infinity) if no path exists.
     * <p>
     * You may import/use java.util.PriorityQueue,
     * java.util.Map, and java.util.Set and any class that
     * implements the aforementioned interfaces, as long as your use of it
     * is efficient as possible.
     * <p>
     * You should implement the version of Dijkstra's where you use two
     * termination conditions in conjunction.
     * <p>
     * 1) Check if all of the vertices have been visited.
     * 2) Check if the PQ is empty yet.
     * <p>
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the Dijkstra's on (source)
     * @param graph the graph we are applying Dijkstra's to
     * @return a map of the shortest distances from start to every
     * other node in the graph
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph.
     */
    public static <T> Map<Vertex<T>, Integer> dijkstras(Vertex<T> start, Graph<T> graph) {
        if (start == null || graph == null) {
            throw new IllegalArgumentException("Start or graph cannot be null");
        }

        if (!graph.getVertices().contains(start)) {
            throw new IllegalArgumentException("Start vertex is not in the graph");
        }
        Map<Vertex<T>, Integer> distances = new HashMap<>();
        PriorityQueue<VertexDistance<T>> queue = new PriorityQueue<>();
        Set<Vertex<T>> visited = new HashSet<>();
        for (Vertex<T> vertex : graph.getVertices()) {
            distances.put(vertex, vertex.equals(start) ? 0 : Integer.MAX_VALUE);
        }
        queue.add(new VertexDistance<>(start, 0));
        while (!queue.isEmpty()) {
            VertexDistance<T> current = queue.poll();
            if (!visited.add(current.getVertex())) continue;

            for (VertexDistance<T> neighbor : graph.getAdjList().get(current.getVertex())) {
                int oldDistance = distances.get(neighbor.getVertex());
                int newDistance = distances.get(current.getVertex()) + neighbor.getDistance();

                if (newDistance < oldDistance) {
                    distances.put(neighbor.getVertex(), newDistance);
                    queue.add(new VertexDistance<>(neighbor.getVertex(), newDistance));
                }
            }
        }
        return distances;
    }

//        Map<Vertex<T>, Integer> distances = new HashMap<>();
//        PriorityQueue<VertexDistance<T>> queue = new PriorityQueue<>();
//        Set<Vertex<T>> visited = new HashSet<>();
//        for (Vertex<T> vertex : graph.getVertices()) {
//            if (vertex.equals(start)) {
//                distances.put(vertex, 0);
//                queue.add(new VertexDistance<>(vertex, 0));
//            } else {
//                distances.put(vertex, Integer.MAX_VALUE);
//            }
//        }
//        while (!queue.isEmpty() && visited.size() != graph.getVertices().size()) {
//            VertexDistance<T> current = queue.poll();
//            visited.add(current.getVertex());
//
//            for (VertexDistance<T> neighbor : graph.getAdjList().get(current.getVertex())) {
//                if (!visited.contains(neighbor.getVertex())) {
//                    int oldDistance = distances.get(neighbor.getVertex());
//                    int newDistance = distances.get(current.getVertex()) + neighbor.getDistance();
//
//                    if (newDistance < oldDistance) {
//                        distances.put(neighbor.getVertex(), newDistance);
//                        queue.add(new VertexDistance<>(neighbor.getVertex(), newDistance));
//                    }
//                }
//            }
//        }
//        return distances;


    /**
     * Runs Kruskal's algorithm on the given graph and returns the Minimal
     * Spanning Tree (MST) in the form of a set of Edges. If the graph is
     * disconnected and therefore no valid MST exists, return null.
     * <p>
     * You may assume that the passed in graph is undirected. In this framework,
     * this means that if (u, v, 3) is in the graph, then the opposite edge
     * (v, u, 3) will also be in the graph, though as a separate Edge object.
     * <p>
     * The returned set of edges should form an undirected graph. This means
     * that every time you add an edge to your return set, you should add the
     * reverse edge to the set as well. This is for testing purposes. This
     * reverse edge does not need to be the one from the graph itself; you can
     * just make a new edge object representing the reverse edge.
     * <p>
     * You may assume that there will only be one valid MST that can be formed.
     * <p>
     * Kruskal's will also require you to use a Disjoint Set which has been
     * provided for you. A Disjoint Set will keep track of which vertices are
     * connected given the edges in your current MST, allowing you to easily
     * figure out whether adding an edge will create a cycle. Refer
     * to the DisjointSet and DisjointSetNode classes that
     * have been provided to you for more information.
     * <p>
     * You should NOT allow self-loops or parallel edges into the MST.
     * <p>
     * By using the Disjoint Set provided, you can avoid adding self-loops and
     * parallel edges into the MST.
     * <p>
     * You may import/use java.util.PriorityQueue,
     * java.util.Set, and any class that implements the aforementioned
     * interfaces.
     * <p>
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param graph the graph we are applying Kruskals to
     * @return the MST of the graph or null if there is no valid MST
     * @throws IllegalArgumentException if any input is null
     */

    public static <T> Set<Edge<T>> kruskals(Graph<T> graph) {
        if (graph == null) {
            throw new IllegalArgumentException("Input graph cannot be null");
        }

        Set<Edge<T>> result = new HashSet<>();

        PriorityQueue<Edge<T>> queue = new PriorityQueue<>(graph.getEdges());

        DisjointSet<Vertex<T>> disjointSet = new DisjointSet<>();

        while (!queue.isEmpty() && result.size() < (graph.getVertices().size() - 1) * 2) {
            Edge<T> edge = queue.poll();

            Vertex<T> u = disjointSet.find(edge.getU());
            Vertex<T> v = disjointSet.find(edge.getV());

            if (!u.equals(v)) {
                result.add(edge);

                result.add(new Edge<>(edge.getV(), edge.getU(), edge.getWeight()));

                disjointSet.union(u, v);
            }
        }

        if (result.size() < (graph.getVertices().size() - 1) * 2) {
            return null;
        }

        return result;
    }
}
