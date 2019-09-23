/**
 * CSE 373 Summer
 * August 4, 2017
 * @Author: Yeun-Yuan(Jessie) Kuo
 */
import java.util.*;

/**
 * A representation of a graph.
 * Assumes that we do not have negative cost edges in the graph.
 */
public class MyGraph implements Graph {
	// A map that stores a vertex and all edges with the vertex as source.
    private Map<Vertex, Set<Edge>> vertexEdgeRelations;
    // A copy of all edges.
    private Set<Edge> edgeSet;
    
    /**
     * Creates a MyGraph object with the given collection of vertices
     * and the given collection of edges.
     * @param v a collection of the vertices in this graph
     * @param e a collection of the edges in this graph
     * @throws IllegalArgumentException when v and e is null.
     */
    public MyGraph(Collection<Vertex> v, Collection<Edge> e) {
    	if (v == null || e == null) {
    		throw new IllegalArgumentException();
    	}
    	edgeSet = new HashSet<Edge>();
    	vertexEdgeRelations = new HashMap<Vertex, Set<Edge>>();
    	for (Vertex currVertex: v) {
    		if (!vertexEdgeRelations.containsKey(currVertex)) {
    			vertexEdgeRelations.put(currVertex, new HashSet<Edge>());
    		}
    	}
    	addEdge(e);
    }
    
    /** 
     * Return the collection of vertices of this graph
     * @return the vertices as a collection (which is anything iterable)
     */
    public Collection<Vertex> vertices() {
    	return vertexEdgeRelations.keySet();
    }

    /** 
     * Return the collection of edges of this graph
     * @return the edges as a collection (which is anything iterable)
     */
    public Collection<Edge> edges() {
    	return edgeSet;
    }

    /**
     * Return a collection of vertices adjacent to a given vertex v.
     *   i.e., the set of all vertices w where edges v -> w exist in the graph.
     * Return an empty collection if there are no adjacent vertices.
     * @param v one of the vertices in the graph
     * @return an iterable collection of vertices adjacent to v in the graph
     * @throws IllegalArgumentException if v does not exist.
     */
    public Collection<Vertex> adjacentVertices(Vertex v) {
    	if (v == null || !vertexEdgeRelations.containsKey(v)) {
    		throw new IllegalArgumentException();
    	}
    	Set<Vertex> adjVertex = new HashSet<Vertex>();
    	for (Edge edge: vertexEdgeRelations.get(v)) {
    		adjVertex.add(edge.getDestination());
    	}
    	return adjVertex;
    }

    /**
     * Test whether vertex b is adjacent to vertex a (i.e. a -> b) in a directed graph.
     * Assumes that we do not have negative cost edges in the graph.
     * @param a one vertex
     * @param b another vertex
     * @return cost of edge if there is a directed edge from a to b in the graph, 
     * return -1 otherwise.
     * @throws IllegalArgumentException if a or b do not exist.
     */
    public int edgeCost(Vertex a, Vertex b) {
    	if (a == null ||!vertexEdgeRelations.containsKey(a) || 
    			 b == null || !vertexEdgeRelations.containsKey(b)) {
    		throw new IllegalArgumentException();
    	}
    	if (vertexEdgeRelations.get(a) != null) {
    		for (Edge edge: vertexEdgeRelations.get(a)) {
        		if (edge.getDestination().equals(b)) {
        			return edge.getWeight();
        		}
        	}
    	}
    	return -1;
    }

    /**
     * Returns the shortest path from a to b in the graph, or null if there is
     * no such path.  Assumes all edge weights are nonnegative.
     * Uses Dijkstra's algorithm.
     * @param a the starting vertex
     * @param b the destination vertex
     * @return a Path where the vertices indicate the path from a to b in order
     *   and contains a (first) and b (last) and the cost is the cost of 
     *   the path. Returns null if b is not reachable from a.
     * @throws IllegalArgumentException if a or b is null or does not exist.
     */
    public Path shortestPath(Vertex a, Vertex b) {
    	if (a == null ||!vertexEdgeRelations.containsKey(a) || 
    			 b == null || !vertexEdgeRelations.containsKey(b)) {
    		throw new IllegalArgumentException();
    	} 
    	
    	// A map that stores mini path that contains the cost and previous node for a vertex.
    	// e.g. for vertex a:
    	//		vertices -> [Vertex a, Vertex "previous node of a"]
    	//	 	cost -> cost from source to a
    	Map<Vertex, Path> map = new HashMap<>();
    	
    	// To store and keep track of the known vertices.
    	Set<Vertex> knownSet = new HashSet<>();
    	
    	// Assign a cost of infinity and previous node of null to all vertices.
    	for (Vertex v: vertexEdgeRelations.keySet()) {
    		List<Vertex> vertexPrev = new ArrayList<Vertex>();
    		vertexPrev.add(v);
    		vertexPrev.add(null);
    		Path state = new Path(vertexPrev, Integer.MAX_VALUE);
    		map.put(v, state);
    	}
    	// Use Dijkstra's algorithm to update all the vertices' cost and previous node.
    	dijkstra(map, knownSet, a);
    	// Check if the destination is reachable.
    	if (knownSet.contains(b)) {
	    	List<Vertex> path = new ArrayList<>();
	    	path.add(new Vertex(b.toString()));
	    	if (a.equals(b)) {
	    		return new Path(path, 0);
	    	} else {
	    		Vertex prev = map.get(b).vertices.get(1);
	    		while(prev != null) {
	    			path.add(new Vertex(prev.toString()));
	    			Vertex newPrev = map.get(prev).vertices.get(1);
	    			prev = newPrev;
	    		}
	    		Collections.reverse(path);
	        	return new Path(path, map.get(b).cost);
	    	}
    	} else {
    		return null;
    	}
    }
    
    /**
     * Using Dijkstra's algorithm to find the shortest path from a node to all other nodes.
     * @param map a map that store's the cost and previous node for a vertex.
     * @param knownSet a set that stores vertices that are known.
     * @param a the source vertex.
     */
    private void dijkstra (Map<Vertex, Path> map, Set<Vertex> knownSet, Vertex a) {
    	PriorityQueue<Path> pq = new PriorityQueue<Path>(new CompareState());
    	map.get(a).cost = 0;
    	pq.add(map.get(a));
    	
    	while (!pq.isEmpty()) {
    		Path curr = pq.remove();
    		// Put the checked nodes with shortest path to knownSet.
    		Vertex currVertex = curr.vertices.get(0);
    		knownSet.add(currVertex);
    		if (vertexEdgeRelations.get(currVertex) != null) {
    			// Find all the neighbors of the current vertex.
    			for (Edge edge : vertexEdgeRelations.get(currVertex)) {
    				if (!knownSet.contains(edge.getDestination())) {
	        			int c1 = curr.cost + edge.getWeight();
	        			int c2 = map.get(edge.getDestination()).cost;
	        			// Update the cost of the vertex and it's previous node if a shorter path is found.
	        			if (c1 < c2) {
	        				Path currPath = map.get(edge.getDestination());
	        				currPath.cost = c1;
	        				currPath.vertices.set(1, currVertex);
	        			}
	        			// Only if the vertex is not already inside our priority queue then add, 
	        			// otherwise the cost and previous node is already in the queue and updated.
	        			if (!knownSet.contains(edge.getDestination())) {
	        				pq.add(map.get(edge.getDestination()));
	        			}
    				}
        		}
    		}
    	}
    }
    
    /**
     * Adds the edges to the vertex.
     * @param e a collection of the edges in this graph
     * @throws IllegalArgumentException when the edge weight is negative,
     *         or when the source or destination for the edge isn't in
     *         the vertices of the graph.
     */
    private void addEdge(Collection<Edge> e) {
    	for (Edge currEdge: e) {
    		if (currEdge.getWeight() < 0 || 
					!vertexEdgeRelations.containsKey(currEdge.getSource()) ||
					!vertexEdgeRelations.containsKey(currEdge.getDestination())) {
    			throw new IllegalArgumentException();
    		}
    		// Check if there is a redundant edge with same weight.
    		if (!Redundant(currEdge)) {
    			edgeSet.add(currEdge);
    			vertexEdgeRelations.get(currEdge.getSource()).add(currEdge);
    		}
    	}
    }
    
    /**
     * Check if there is an edge that appears redundantly with the same weight.
     * @param edge the edge in Collection<Edge> e that is being checked.
     * @return true if there is the same directed edge with the same weight, false otherwise.
     * @throws IllegalArgumentException when there is the same directed edge but
     * 		   with a different weight.
     */
    private boolean Redundant(Edge edge) {
    	boolean redundantState = false;
    	for (Edge curr: vertexEdgeRelations.get(edge.getSource())) {
    		if (edge.getDestination().equals(curr.getDestination()) && 
    				edge.getWeight() != curr.getWeight()) {
    			throw new IllegalArgumentException();
    		} else if (edge.getDestination().equals(curr.getDestination()) &&
    				edge.getWeight() == curr.getWeight()) {
    			redundantState = true;
    		}
    	}
    	return redundantState;
    }

}