//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Title: (Package Manager)
// Description: (manage the storage of packages and their relationships)
// Course: (001 FALL 2019)
//
// Author: (Rosalie CAI)
// Email: (rcai25@wisc.edu)
/////////////////////////////// 80 COLUMNS WIDE ///////////////////////////////

import org.json.simple.parser.ParseException;
import org.junit.After;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Random;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Filename: GraphTest.java Project: p4 Authors: Rosalie Cai
 * 
 * @author rosaliecarrow
 *
 */
public class GraphTest {

  /**
   * test if an empty graph has the correct beginning values, 0 edge, 0 vertex
   */
  @Test
  public void test000_emptyGraph() {
    Graph g = new Graph();
    // check size
    if (g.size() != 0) {
      fail("Size should be zero for an empty graph");
    }
    // check order
    if (g.order() != 0) {
      fail("order should be zero for an empty graph");
    }
    // check if get all vertices is correct
    if (!g.getAllVertices().isEmpty()) {
      fail("empty graph should be empty");
    }
  }
  // ---------------------TESTING ADD VERTEX------------------------------


  /**
   * test if adding null vertex has set the correct values
   */
  @Test
  public void test008_addVertex_null_exception() {
    Graph g = new Graph();

    g.addVertex(null);
    // check size
    if (g.size() != 0) {
      fail("Size should be zero");
    }
    // check order
    if (g.order() != 0) {
      fail("order should be 0");
    }
    // check if get all vertices is correct
    if (!g.getAllVertices().isEmpty()) {
      fail("graph should be empty");
    }
  }

  /**
   * test if adding one vertex has set the correct values
   */
  @Test
  public void test001_addOneV() {
    Graph g = new Graph();

    g.addVertex("V1");
    // check size
    if (g.size() != 0) {
      fail("Size should be zero");
    }
    // check order
    if (g.order() != 1) {
      fail("order should be 1");
    }
    // check if get all vertices is correct
    if (g.getAllVertices().isEmpty()) {
      fail("graph should not be empty");
    }
  }


  // ---------------------TESTING ADD EDGE------------------------------

  /**
   * test if adding null edges has set the correct values
   */
  @Test
  public void test010_addEdge_null_exception() {
    Graph g = new Graph();
    g.addVertex("V1");
    g.addVertex("V2");
    g.addEdge(null, null);

    // check size
    if (g.size() != 0) {
      fail("Size should be zero");
    }
    // check order
    if (g.order() != 2) {
      fail("order should be 2");
    }
    // check if get all vertices is correct
    if (g.getAllVertices().size() != 2) {
      fail("graph get all vertices size incorrect");
    }
  }

  /**
   * test if adding two vertex and no edge has set the correct values
   */
  @Test
  public void test002_addtwoV_no_edge() {
    Graph g = new Graph();

    g.addVertex("V1");
    g.addVertex("V2");

    // check size
    if (g.size() != 0) {
      fail("Size should be zero");
    }
    // check order
    if (g.order() != 2) {
      fail("order should be 2");
    }
    // check if get all vertices is correct
    if (g.getAllVertices().size() != 2) {
      fail("graph get all vertices size incorrect");
    }
  }

  /**
   * test if adding two vertex and 1 edge has set the correct values
   */
  @Test
  public void test003_addtwoV_oneEdge() {
    Graph g = new Graph();

    g.addVertex("V1");
    g.addVertex("V2");
    g.addEdge("V1", "V2");

    // check size
    if (g.size() != 1) {
      fail("Size should be 1");
    }
    // check order
    if (g.order() != 2) {
      fail("order should be 2");
    }
    // check if get all vertices is correct
    if (g.getAllVertices().size() != 2) {
      fail("graph get all vertices size incorrect");
    }
  }


  /**
   * test if adding two vertex and 2 edge has set the correct values
   */
  @Test
  public void test004_addtwoV_twoEdge() {
    Graph g = new Graph();

    g.addVertex("V1");
    g.addVertex("V2");
    g.addEdge("V1", "V2");
    g.addEdge("V2", "V1");

    // check size
    if (g.size() != 2) {
      fail("Size should be 2");
    }
    // check order
    if (g.order() != 2) {
      fail("order should be 2");
    }
    // check if get all vertices is correct
    if (g.getAllVertices().isEmpty()) {
      fail("graph should not be empty");
    }
  }



  // ---------------------TESTING REMOVING VERTEX------------------------------



  /**
   * test if removing null vertex has set the correct values
   */
  @Test
  public void test009_removeVertex_null_exception() {
    Graph g = new Graph();
    g.addVertex("V1");
    g.addVertex("V2");
    g.removeVertex(null);

    // check size
    if (g.size() != 0) {
      fail("Size should be zero");
    }
    // check order
    if (g.order() != 2) {
      fail("order should be 2");
    }
    // check if get all vertices is correct
    if (g.getAllVertices().isEmpty()) {
      fail("graph should not be empty");
    }
  }

  /**
   * test if removing one vertex set the correct values
   */
  @Test
  public void test005_removOneV() {
    Graph g = new Graph();

    g.addVertex("V1");
    g.removeVertex("V1");

    // check size
    if (g.size() != 0) {
      fail("Size should be zero");
    }
    // check order
    if (g.order() != 0) {
      fail("order should be 0");
    }
    // check if get all vertices is correct
    if (!g.getAllVertices().isEmpty()) {
      fail("graph should be empty");
    }
  }

  /**
   * test if removing one vertex also removes edges pointing towards it
   */
  @Test
  public void test007_removeV_check_Edge_removed() {
    Graph g = new Graph();

    g.addVertex("V1");
    g.addVertex("V2");
    g.addEdge("V1", "V2");
    g.addEdge("V2", "V1");
    g.removeVertex("V1");

    // check size
    if (g.size() != 0) {
      fail("Size should be 0");
    }

  }

  // ---------------------TESTING REMOVE EDGE------------------------------
  /**
   * test if adding null edges has set the correct values
   */
  @Test
  public void test011_removeEdge_null_exception() {
    Graph g = new Graph();
    g.addVertex("V1");
    g.addVertex("V2");
    g.removeEdge(null, null);

    // check size
    if (g.size() != 0) {
      fail("Size should be zero");
    }
    // check order
    if (g.order() != 2) {
      fail("order should be 2");
    }
    // check if get all vertices is correct
    if (g.getAllVertices().isEmpty()) {
      fail("graph should not be empty");
    }
  }

  /**
   * test if removing an edge has set the correct values
   */
  @Test
  public void test006_removEdge() {
    Graph g = new Graph();

    g.addVertex("V1");
    g.addVertex("V2");
    g.addEdge("V1", "V2");
    g.addEdge("V2", "V1");
    g.removeEdge("V1", "V2");

    // check size
    if (g.size() != 1) {
      fail("Size should be 1");
    }
    // check order
    if (g.order() != 2) {
      fail("order should be 2");
    }
    // check if get all vertices is correct
    if (g.getAllVertices().isEmpty()) {
      fail("graph should not be empty");
    }
  }

  // --------general functionality--------------------------------
  /**
   * test if adding a bunch of vertices has set the correct values
   */
  @Test
  public void test012_add_remove_Bunch_V() {
    Graph g = new Graph();
    // add a bunch
    for (int i = 0; i < 100; i++) {
      g.addVertex("V" + i);
    }

    // check size
    if (g.size() != 0) {
      fail("Size should be zero");
    }
    // check order
    if (g.order() != 100) {
      fail("order should be 100");
    }
    // check if get all vertices is correct
    if (g.getAllVertices().isEmpty()) {
      fail("graph should not be empty");
    }

    // remove a bunch
    for (int i = 0; i < 100; i++) {
      g.removeVertex("V" + i);
    }

    // check size
    if (g.size() != 0) {
      fail("Size should be zero");
    }
    // check order
    if (g.order() != 0) {
      fail("order should be 0");
    }
    // check if get all vertices is correct
    if (!g.getAllVertices().isEmpty()) {
      fail("graph should be empty");
    }


  }
}
