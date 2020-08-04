//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Title: (Package Manager)
// Description: (manage the storage of packages and their relationships)
// Course: (001 FALL 2019)
//
// Author: (Rosalie CAI)
// Email: (rcai25@wisc.edu)
/////////////////////////////// 80 COLUMNS WIDE ///////////////////////////////

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * Filename: Graph.java Project: p4 Authors: Rosalie Cai
 * 
 * Directed and unweighted graph implementation
 */
public class Graph implements GraphADT {
  // adjacent list--name and its associated edges
  private Map<String, ArrayList<String>> adjList = new HashMap<>();


  /*
   * Default no-argument constructor
   */
  public Graph() {

  }

  /**
   * Add new vertex to the graph.
   *
   * If vertex is null or already exists, method ends without adding a vertex or throwing an
   * exception.
   * 
   * Valid argument conditions: 1. vertex is non-null 2. vertex is not already in the graph
   */
  public void addVertex(String vertex) {
    // method end if null vertex
    if (vertex == null) {
      return;
    }
    // method end if vertex already in the graph
    if (adjList.containsKey(vertex)) {
      return;
    }
    // insert vertex
    adjList.put(vertex, new ArrayList<String>());

  }

  /**
   * Remove a vertex and all associated edges from the graph.
   * 
   * If vertex is null or does not exist, method ends without removing a vertex, edges, or throwing
   * an exception.
   * 
   * Valid argument conditions: 1. vertex is non-null 2. vertex is not already in the graph
   */
  public void removeVertex(String vertex) {
    // method end if null vertex
    if (vertex == null) {
      return;
    }
    // method end if vertex already in the graph
    if (!adjList.containsKey(vertex)) {
      return;
    }

    // remove associated edges
    adjList.forEach((k, v) -> {
      v.remove(vertex);
    });
    // remove vertex
    adjList.remove(vertex);

  }

  /**
   * Add the edge from vertex1 to vertex2 to this graph. (edge is directed and unweighted) If either
   * vertex does not exist, add vertex, and add edge, no exception is thrown. If the edge exists in
   * the graph, no edge is added and no exception is thrown.
   * 
   * Valid argument conditions: 1. neither vertex is null 2. both vertices are in the graph 3. the
   * edge is not in the graph
   */
  public void addEdge(String vertex1, String vertex2) {
    // vertex must not be null
    if (vertex1 == null || vertex2 == null) {
      return;
    }

    // add vertex if does not exist in the graph
    addVertex(vertex1);
    addVertex(vertex2);

    // add edge if there is no existing edge
    if (!adjList.get(vertex1).contains(vertex2)) {
      adjList.get(vertex1).add(vertex2);
    }
  }

  /**
   * Remove the edge from vertex1 to vertex2 from this graph. (edge is directed and unweighted) If
   * either vertex does not exist, or if an edge from vertex1 to vertex2 does not exist, no edge is
   * removed and no exception is thrown.
   * 
   * Valid argument conditions: 1. neither vertex is null 2. both vertices are in the graph 3. the
   * edge from vertex1 to vertex2 is in the graph
   */
  public void removeEdge(String vertex1, String vertex2) {
    // vertex must not be null, and must be in the graph
    if (vertex1 == null || vertex2 == null || (!adjList.containsKey(vertex1))
        || (!adjList.containsKey(vertex2))) {
      return;
    }

    // remove edge
    adjList.get(vertex1).remove(vertex2);
  }

  /**
   * Returns a Set that contains all the vertices
   * 
   */
  public Set<String> getAllVertices() {
    return adjList.keySet();
  }

  /**
   * Get all the neighbor (adjacent) vertices of a vertex
   *
   */
  public List<String> getAdjacentVerticesOf(String vertex) {
    return adjList.get(vertex);
  }

  /**
   * Returns the number of edges in this graph.
   */
  public int size() {
    int size = 0;

    // traversal through all values and add up their sizes to get the size of the graph
    Iterator itr = adjList.entrySet().iterator();
    while (itr.hasNext()) {
      Map.Entry mapElement = (Map.Entry) itr.next();
      size += ((ArrayList<String>) mapElement.getValue()).size();
    }

    return size;
  }

  /**
   * Returns the number of vertices in this graph.
   */
  public int order() {
    return adjList.size();
  }


}
