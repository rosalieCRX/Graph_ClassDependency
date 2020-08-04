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
import java.util.Set;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Filename: PackageManagerTest.java Project: p4 Authors: Rosalie Cai
 * 
 * @author rosaliecarrow
 *
 */
public class PackageManagerTest {
  // --------------------testing constructing graph--------------------------------------------
  /**
   * test the construct Graph for file not found exception
   */
  @Test
  public void test000_constructValidName() {
    try {
      PackageManager pkm = new PackageManager();
      pkm.constructGraph("valid.json");
    }
    // any exception unexpected
    catch (FileNotFoundException e) {
      fail("should not throw file not found exception for valid package name");
    } catch (Exception e) {
      fail("Should not throw any exception");
    }


  }

  // --------------------testing getting all packages--------------------------------------------
  /**
   * test if all vertices are parsed and stored
   */
  @Test
  public void test001_getPackage_valid() {
    try {
      PackageManager pkm = new PackageManager();
      pkm.constructGraph("valid.json");

      // check all packages when get all packages
      Set<String> all = pkm.getAllPackages();
      if (!all.contains("A") || !all.contains("B") || !all.contains("C") || !all.contains("D")
          || !all.contains("E")) {
        fail("Not all packages got");
      }

    }
    // any exception unexpected
    catch (Exception e) {
      fail("Should not throw any exception");
    }
  }


  // --------------------testing getting Installation
  // order--------------------------------------------
  /**
   * test if order for specific package is correct for valid.json
   */
  @Test
  public void test002_getInstallationOrder_valid() {
    try {
      PackageManager pkm = new PackageManager();
      pkm.constructGraph("valid.json");

      // check package C's installation order
      if (pkm.getInstallationOrder("C").size() != 1) {
        fail("wrong order for C");
      }
      // check package D's installation order
      if (pkm.getInstallationOrder("D").size() != 1) {
        fail("wrong order for D");
      }

      // check package B's installation order
      if (!pkm.getInstallationOrder("B").contains("C")
          || !pkm.getInstallationOrder("B").contains("D")) {
        fail("wrong order for B");
      }
    }
    // any exception unexpected
    catch (Exception e) {
      fail("Should not throw any exception");
    }
  }

  /**
   * test if package not found exception is thrown for getInstallationOrder
   */
  @Test
  public void test007_non_existent_pakcage_getInstallationOrder_valid() {
    try {
      PackageManager pkm = new PackageManager();
      pkm.constructGraph("valid.json");

      pkm.getInstallationOrder("BBBBB");
      fail("Should have thrown PackageNotFoundException");// unexpected
    } catch (PackageNotFoundException e) {
      // expected
    } catch (Exception e) {
      fail("Should throw PackageNotFoundException");// unexpected
    }
  }

  // --------------------testing getting Global Installation
  // order--------------------------------------------
  /**
   * test if global order is correct for valid.json
   */
  @Test
  public void test003_getGobalInstallationOrder_valid() {
    try {
      PackageManager pkm = new PackageManager();
      pkm.constructGraph("valid.json");


      ArrayList<String> order = (ArrayList<String>) pkm.getInstallationOrderForAllPackages();

      // check order, see if predecessor-successor relationship is violated
      if (order.indexOf("C") > order.indexOf("B") || order.indexOf("C") > order.indexOf("A")
          || order.indexOf("C") > order.indexOf("E") || order.indexOf("D") > order.indexOf("B")
          || order.indexOf("D") > order.indexOf("A") || order.indexOf("D") > order.indexOf("E")) {
        fail("Wrong global order");
      }

    }
    // any exception unexpected
    catch (Exception e) {
      fail("Should not throw any exception");
    }
  }

  // --------------------testing toInstall--------------------------------------------
  /**
   * test if toInstall is correct for valid.json
   */
  @Test
  public void test004_toInstall_valid() {
    try {
      PackageManager pkm = new PackageManager();
      pkm.constructGraph("valid.json");
      // check dependent packages
      ArrayList<String> newOrder = (ArrayList<String>) pkm.toInstall("A", "B");

      // check content
      if (newOrder.contains("C") || newOrder.contains("D") || newOrder.contains("B")
          || !newOrder.contains("A")) {
        fail("Wrong stallation order");
      }


      // check independent packages
      ArrayList<String> newOrder1 = (ArrayList<String>) pkm.toInstall("A", "E");

      // check content
      if (newOrder1.contains("C") || newOrder1.contains("D") || newOrder1.contains("B")
          || !newOrder1.contains("A") || newOrder1.size() != 1) {
        fail("Wrong stallation order");
      }


    }
    // any exception unexpected
    catch (Exception e) {
      fail("Should not throw any exception");
    }
  }

  /**
   * test if package not found exception is thrown for toInstall
   */
  @Test
  public void test008_non_existent_pakcage_toInstall_valid() {
    // test for non existent package 1
    try {
      PackageManager pkm = new PackageManager();
      pkm.constructGraph("valid.json");

      pkm.toInstall("BBBBB", "B");
      fail("Should have thrown PackageNotFoundException");// unexpected
    } catch (PackageNotFoundException e) {
      // expected
    } catch (Exception e) {
      fail("Should throw PackageNotFoundException");// unexpected
    }
    // test for non existent package 2
    try {
      PackageManager pkm = new PackageManager();
      pkm.constructGraph("valid.json");

      pkm.toInstall("B", "BBBBB");
      fail("Should have thrown PackageNotFoundException");// unexpected
    } catch (PackageNotFoundException e) {
      // expected
    } catch (Exception e) {
      fail("Should throw PackageNotFoundException");// unexpected
    }
  }



  /**
   * test if max dependency is correct for sharedDependncies.json
   */
  @Test
  public void test010_toInstall_sharedDep() {
    try {
      PackageManager pkm = new PackageManager();
      pkm.constructGraph("shared_dependencies.json");

      // check content for [A,B]
      ArrayList<String> newOrder = (ArrayList<String>) pkm.toInstall("A", "B");
      if (!newOrder.contains("C") || !newOrder.contains("A") || newOrder.size() != 2) {
        fail("Wrong to install order");
      }

      // check content for [A,C]
      ArrayList<String> newOrder1 = (ArrayList<String>) pkm.toInstall("A", "C");
      if (!newOrder1.contains("A") || !newOrder1.contains("B") || newOrder1.size() != 2) {
        fail("Wrong to install order");
      }

    }
    // any exception unexpected
    catch (Exception e) {
      fail("Should not throw any exception");
    }
  }

  // --------------------testing
  // getPackageWithMaxDependencies--------------------------------------------
  /**
   * test if max dependency is correct for valid.json
   */
  @Test
  public void test005_getPackageWithMaxDependencies_valid() {
    try {
      PackageManager pkm = new PackageManager();
      pkm.constructGraph("valid.json");

      // check content
      if (!pkm.getPackageWithMaxDependencies().equals("A")) {
        fail("Wrong max dependency");
      }
    }
    // any exception unexpected
    catch (Exception e) {
      fail("Should not throw any exception");
    }
  }



  /**
   * test if max dependency is correct for sharedDependncies.json
   */
  @Test
  public void test009_getPackageWithMaxDependencies_sharedDep() {
    try {
      PackageManager pkm = new PackageManager();
      pkm.constructGraph("shared_dependencies.json");

      // check content
      if (!pkm.getPackageWithMaxDependencies().equals("A")) {
        fail("Wrong max dependency");
      }
    }
    // any exception unexpected
    catch (Exception e) {
      fail("Should not throw any exception");
    }
  }

  // --------------------testing cycles--------------------------------------------
  /**
   * test if cycle is detected for cyclic.json
   */
  @Test
  public void test006_checkCycle_installation_cyclic() {
    // check vertex A
    try {
      PackageManager pkm = new PackageManager();
      pkm.constructGraph("cyclic.json");

      pkm.getInstallationOrder("A");

      fail("Should thorw cycle exception");// unexpected

    } catch (CycleException e) {
      // expected
    } catch (Exception e) {
      fail("Should throw cycle exception");// unexpected

    }

    // check vertex B
    try {
      PackageManager pkm = new PackageManager();
      pkm.constructGraph("cyclic.json");

      pkm.getInstallationOrder("B");

      fail("Should thorw cycle exception");// unexpected
    } catch (CycleException e) {
      // expected
    } catch (Exception e) {
      fail("Should throw cycle exception");// unexpected
    }
  }


}
