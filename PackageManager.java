//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Title: (Package Manager)
// Description: (manage the storage of packages and their relationships)
// Course: (001 FALL 2019)
//
// Author: (Rosalie CAI)
// Email: (rcai25@wisc.edu)
/////////////////////////////// 80 COLUMNS WIDE ///////////////////////////////

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Filename: PackageManager.java Project: p4 Authors: Rosalie Cai
 * 
 * PackageManager is used to process json package dependency files and provide function that make
 * that information available to other users.
 * 
 * Each package that depends upon other packages has its own entry in the json file.
 * 
 * Package dependencies are important when building software, as you must install packages in an
 * order such that each package is installed after all of the packages that it depends on have been
 * installed.
 * 
 * For example: package A depends upon package B, then package B must be installed before package A.
 * 
 * This program will read package information and provide information about the packages that must
 * be installed before any given package can be installed. all of the packages in
 * 
 * You may add a main method, but we will test all methods with our own Test classes.
 */

public class PackageManager {

  private Graph graph;// an internal graph that shows dependencies
  private ArrayList<Package> dependList;// keeps track of how each package depend on each

  /*
   * Package Manager default no-argument constructor.
   */
  public PackageManager() {

  }

  /**
   * Takes in a file path for a json file and builds the package dependency graph from it.
   * 
   * @param jsonFilepath the name of json data file with package dependency information
   * @throws FileNotFoundException if file path is incorrect
   * @throws IOException           if the give file cannot be read
   * @throws ParseException        if the given json cannot be parsed
   */
  public void constructGraph(String jsonFilepath)
      throws FileNotFoundException, IOException, ParseException {
    // instantiate graph
    graph = new Graph();
    dependList = new ArrayList<Package>();

    // parse JSON objects
    Object obj = new JSONParser().parse(new FileReader(jsonFilepath));
    JSONObject jo = (JSONObject) obj;
    JSONArray packages = (JSONArray) jo.get("packages");


    // add vertex and edges
    for (int i = 0; i < packages.size(); i++) {
      try {
        JSONObject jsonPackage = (JSONObject) packages.get(i);
        String name = (String) jsonPackage.get("name");
        JSONArray dependencies = (JSONArray) jsonPackage.get("dependencies");

        String[] depend = new String[dependencies.size()];
        // add edges, dependency points to this node
        graph.addVertex(name);
        for (int j = 0; j < dependencies.size(); j++) {
          graph.addEdge((String) dependencies.get(j), name);// add to graph
          depend[j] = (String) dependencies.get(j);// add to adjList
        }

        // add to adjList
        dependList.add(new Package(name, depend));
      } catch (Exception e) {
        // skip this iteration and continue parsing valid data
      }

    }


  }

  /**
   * Helper method to get all packages in the graph.
   * 
   * @return Set<String> of all the packages
   */
  public Set<String> getAllPackages() {
    return graph.getAllVertices();
  }

  /**
   * Given a package name, returns a list of packages in a valid installation order.
   * 
   * Valid installation order means that each package is listed before any packages that depend upon
   * that package.
   * 
   * @return List<String>, order in which the packages have to be installed
   * 
   * @throws CycleException           if you encounter a cycle in the graph while finding the
   *                                  installation order for a particular package. Tip: Cycles in
   *                                  some other part of the graph that do not affect the
   *                                  installation order for the specified package, should not throw
   *                                  this exception.
   * 
   * @throws PackageNotFoundException if the package passed does not exist in the dependency graph.
   */
  public List<String> getInstallationOrder(String pkg)
      throws CycleException, PackageNotFoundException {
    Stack<String> vist = new Stack<String>();// keeps track of what is waiting to be visited
    ArrayList<String> order = new ArrayList<String>();// record the order
    getInstallationOrder(pkg, order, vist);
    return order;


    // // get topological ordering
    // ArrayList<String> topo = topoOrder(pkg);
    // // //get all vertices
    // // ArrayList<String> allV = (ArrayList<String>) graph.getAllVertices();
    // // //stores the order
    // // ArrayList<String> order = new ArrayList();
    //
    // return topo.subList(topo.indexOf(pkg), topo.size() - 1);
  }

  /**
   * private helper method for getting installation order
   * 
   * @param pkg   the package needed to install
   * @param order the arrayList that stores the installation order
   * @throws CycleException           if there is a cycle
   * @throws PackageNotFoundException if package is not in the graph
   */
  private void getInstallationOrder(String pkg, ArrayList<String> order, Stack<String> st)
      throws CycleException, PackageNotFoundException {
    // if the package is not in the graph, return immediately
    if (!graph.getAllVertices().contains(pkg)) {
      throw new PackageNotFoundException();
    }


    // if the package is on the stack, then there is a cycle
    if (st.contains(pkg)) {
      throw new CycleException();
    }

    boolean noPredsesor = true;
    String[] depend = null;
    // if there is not predessor, add it to the order
    for (int i = 0; i < dependList.size(); i++) {
      if (dependList.get(i).getName().equals(pkg)) {
        noPredsesor = false;
        depend = dependList.get(i).getDependencies();// get dependency list
        break;
      }
    }
    st.add(pkg);// add to stack

    if (noPredsesor) {
      if (!order.contains(pkg)) {
        order.add(pkg);
      }
      st.pop();
      return;
    }
    // if it has predessor, travel up
    for (int i = 0; i < depend.length; i++) {
      getInstallationOrder(depend[i], order, st);
    }

    // adding the package after all its predecessor are added
    if (!order.contains(pkg)) {
      order.add(pkg);
    }
    st.pop();
  }



  // private ArrayList<String> getInstallationOrder(String pkg, ArrayList<String> order) throws
  // CycleException{
  //// get dependency list
  // ArrayList<String> dependency = (ArrayList<String>) graph.getAdjacentVerticesOf(pkg);
  // //check for cycles
  // for(int i=0;i<dependency.size();i++) {
  // if((dependency.get(i)) {
  //
  // throw new CycleException();
  // }
  // order.add(dependency.get(i));
  //
  // getInstallationOrder((dependency.get(i)),order);
  //
  // }
  //
  // return dependency;
  // }



  /**
   * Given two packages - one to be installed and the other installed, return a List of the packages
   * that need to be newly installed.
   * 
   * For example, refer to shared_dependecies.json - toInstall("A","B") If package A needs to be
   * installed and packageB is already installed, return the list ["A", "C"] since D will have been
   * installed when B was previously installed.
   * 
   * @return List<String>, packages that need to be newly installed.
   * 
   * @throws CycleException           if you encounter a cycle in the graph while finding the
   *                                  dependencies of the given packages. If there is a cycle in
   *                                  some other part of the graph that doesn't affect the parsing
   *                                  of these dependencies, cycle exception should not be thrown.
   * 
   * @throws PackageNotFoundException if any of the packages passed do not exist in the dependency
   *                                  graph.
   */
  public List<String> toInstall(String newPkg, String installedPkg)
      throws CycleException, PackageNotFoundException {
    // throws PackageNotFoundException if any of the packages passed do not exist in the dependency
    // graph
    if (!graph.getAllVertices().contains(newPkg)
        && !graph.getAllVertices().contains(installedPkg)) {
      throw new PackageNotFoundException();
    }

    // the list newPkg need
    ArrayList<String> totalNew = (ArrayList<String>) getInstallationOrder(newPkg);

    // the list oldPkg need
    ArrayList<String> totalOld = (ArrayList<String>) getInstallationOrder(installedPkg);


    // delete those installed
    for (int i = 0; i < totalOld.size(); i++) {
      if (totalNew.contains(totalOld.get(i))) {
        totalNew.remove(totalOld.get(i));
      }
    }

    //
    // // the list that keeps track of uninstalled packages
    // ArrayList<String> toInstall = new ArrayList<String>();
    // // get installation order
    // ArrayList<String> order = (ArrayList<String>) getInstallationOrder(newPkg);
    //
    // // if installed, skip; if not installed, put into list to install
    // for (int i = 0; i < order.size(); i++) {
    // if (!graph.getAllVertices().contains(order.get(i))) {
    // toInstall.add(order.get(i));
    // }
    // }


    return totalNew;
  }


  /**
   * Return a valid global installation order of all the packages in the dependency graph.
   * 
   * assumes: no package has been installed and you are required to install all the packages
   * 
   * returns a valid installation order that will not violate any dependencies
   * 
   * @return List<String>, order in which all the packages have to be installed
   * @throws CycleException           if you encounter a cycle in the graph
   * @throws PackageNotFoundException
   */
  public List<String> getInstallationOrderForAllPackages()
      throws CycleException, PackageNotFoundException {
    ArrayList<String> order = new ArrayList<String>();// keeps track of the order

    // get all packages
    ArrayList<String> allV = new ArrayList<String>();
    Set<String> set = graph.getAllVertices();
    for (String x : set) {
      allV.add(x);
    }

    // check for all the elements that are in the graph to ge the global order
    for (int i = 0; i < allV.size(); i++) {
      // add its own installation order if it is not in the order
      if (!order.contains(allV.get(i))) {
        ArrayList<String> toInstall = (ArrayList<String>) getInstallationOrder(allV.get(i));
        toInstall.removeAll(order);
        order.addAll(toInstall);// add to the end all that is not in the order
      }
    }

    return order;
  }


  // private helper for get global installation order
  // private void getInstallationOrderForAllPackages(String pkg, ArrayList<String> order,
  // Stack<String> st) throws CycleException, PackageNotFoundException {
  // // if the package is on the stack, then there is a cycle
  // if (st.contains(pkg)) {
  // throw new CycleException();
  // }
  //
  // boolean noPredsesor = true;
  // String[] depend = null;
  // // if there is not predessor, add it to the order
  // for (int i = 0; i < dependList.size(); i++) {
  // if (dependList.get(i).getName().equals(pkg)) {
  // noPredsesor = false;
  // depend = dependList.get(i).getDependencies();// get dependency list
  // break;
  // }
  // }
  // st.add(pkg);// add to stack
  //
  // // if there is no predecessor, pop the vertex off the stack and record it into the order
  // // ArrayList
  // if (noPredsesor) {
  // if (!order.contains(pkg)) {
  // order.add(pkg);
  // }
  // st.pop();
  // return;
  // }
  // // if it has predessor, travel up
  // for (int i = 0; i < depend.length; i++) {
  // getInstallationOrder(depend[i], order, st);
  // }
  //
  // }

  // /**
  // * get its topological order
  // *
  // * @param pkg the starting vertex
  // * @return the topologocal ordering of the reverse graph
  // * @throws CycleException
  // */
  // private ArrayList<String> topoOrder(String pkg) throws CycleException {
  // int num = graph.order();// number of vertices
  // Stack<String> st = new Stack<String>();// a stack for popping and pushing vertices
  //
  //
  // ArrayList<String> allOrder = new ArrayList<>(); // an array list prepared to store all vertices
  // // in order
  // // initiate the arraylist
  // for (int i = 0; i <= num; i++) {
  // allOrder.add("");
  // }
  //
  // // get all vertices
  // ArrayList<String> allV = new ArrayList<String>();
  // Set<String> set = graph.getAllVertices();
  // for (String x : set) {
  // allV.add(x);
  // }
  //
  // // ---------------------prepare visit list--------------------------------------------------
  // Map<String, Boolean> visitList = new HashMap<>();// keeps track of visited vertices
  // // set all state of visit to false
  // for (int i = 0; i < allV.size(); i++) {
  // visitList.put(allV.get(i), false);
  // }
  //
  // // // ----------------------reverse the graph--------------------------------------------------
  // // // get reverse graph
  // // Graph reverseGraph = new Graph();
  // //
  // //
  // // // reverse the edges
  // // for (int i = 0; i < allV.size(); i++) {
  // // ArrayList<String> adj = (ArrayList<String>) reverseGraph.getAdjacentVerticesOf(allV.get(i));
  // // // add edge
  // // for (int j = 0; j < adj.size(); j++) {
  // // reverseGraph.addEdge(adj.get(i), allV.get(i));
  // // }
  // //
  // // // if the vertex is not pointing towards any node
  // // if (reverseGraph.getAdjacentVerticesOf(allV.get(i)).size() == 0) {
  // //
  // // }
  // // }
  //
  //
  // // if the package does not depend on any previous installations, add to stack
  // for (int i = 0; i < dependList.size(); i++) {
  // if (dependList.get(i).getDependencies().length == 0) {
  // st.push(allV.get(i));// push to stack
  // visitList.replace(allV.get(i), true);// mark as visited
  // }
  // }
  //
  // // --------------------start pushing and poping--------------------------------------------
  // // begin building topological order
  // while (!st.isEmpty()) {
  // String c = (String) st.peek();
  // ArrayList<String> adjList = (ArrayList<String>) graph.getAdjacentVerticesOf(c);
  //
  // for (int i = 0; i < adjList.size(); i++) {
  // if (!visitList.get(adjList.get(i))) {
  // if (st.contains(adjList.get(i))) {
  // throw new CycleException();
  // } // if there is a cycle
  //
  // visitList.replace(adjList.get(i), true);// mark as visited
  // st.push(adjList.get(i));
  // } // if successor is unvisited
  // else if (i == adjList.size() - 1) {
  // c = (String) st.pop();
  // allOrder.add(num, c);
  // num--;
  // } // if all successor visited
  // }
  // }
  //
  // return allOrder;
  //
  // }

  /**
   * Find and return the name of the package with the maximum number of dependencies.
   * 
   * Tip: it's not just the number of dependencies given in the json file. The number of
   * dependencies includes the dependencies of its dependencies. But, if a package is listed in
   * multiple places, it is only counted once.
   * 
   * Example: if A depends on B and C, and B depends on C, and C depends on D. Then, A has 3
   * dependencies - B,C and D.
   * 
   * @return String, name of the package with most dependencies.
   * @throws CycleException           if you encounter a cycle in the graph
   * @throws PackageNotFoundException if package not found
   */
  public String getPackageWithMaxDependencies() throws CycleException, PackageNotFoundException {
    // get the max number of dependencies
    int max = -1;
    String maxPack = null;
    // traverse and find the package with max dependencies
    for (int i = 0; i < dependList.size(); i++) {
      int size = getInstallationOrder(dependList.get(i).getName()).size();
      if (size > max) {
        max = size;// update max
        maxPack = dependList.get(i).getName();// update max package name
      }
    }
    //
    //
    //
    // ArrayList<String> allV = new ArrayList();
    // Set<String> set = graph.getAllVertices();
    // for (String x : set) {
    // allV.add(x);
    // }
    //
    // Graph reverseGraph = new Graph();
    //
    // // get the max number of dependencies
    // int max = -1;
    // String maxPack = null;
    //
    // // reverse the edges
    // for (int i = 0; i < allV.size(); i++) {
    // ArrayList<String> adj = (ArrayList<String>) reverseGraph.getAdjacentVerticesOf(allV.get(i));
    //
    // // add edge
    // for (int j = 0; j < adj.size(); j++) {
    // reverseGraph.addEdge(adj.get(i), allV.get(i));
    // }
    //
    // // search for max
    // if (reverseGraph.getAdjacentVerticesOf(allV.get(i)).size() > max) {
    // max = reverseGraph.getAdjacentVerticesOf(allV.get(i)).size();
    // maxPack = allV.get(i);
    // }
    // }

    return maxPack;
  }


  /**
   * the main method
   * 
   * @param args command line arguemnt
   */
  public static void main(String[] args) {
    System.out.println("PackageManager.main()");
  }

}
