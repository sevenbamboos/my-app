package com.mycompany.model;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.ArrayList;
import java.util.List;

/**
 * Unit test for EmployeeTree.
 */
public class EmployeeTreeTest
    extends TestCase {
  /**
   * Create the test case
   *
   * @param testName name of the test case
   */
  public EmployeeTreeTest(String testName) {
    super(testName);
  }

  /**
   * @return the suite of tests being tested
   */
  public static Test suite() {
    return new TestSuite(EmployeeTreeTest.class);
  }

  public void testBuild() {

    Employee e1 = new Employee(1, "e1", 0);
    Employee e2 = new Employee(2, "e2", 1);
    Employee e3 = new Employee(3, "e3", 4);
    Employee e4 = new Employee(4, "e4", 1);

    List<Employee> allEmployees = new ArrayList<>();
    allEmployees.add(e1);
    allEmployees.add(e2);
    allEmployees.add(e3);
    allEmployees.add(e4);

    EmployeeTree tree = EmployeeTree.from(allEmployees);
    assertEquals(e1, tree.root());

  }
}
