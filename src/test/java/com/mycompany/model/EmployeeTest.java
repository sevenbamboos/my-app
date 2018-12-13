package com.mycompany.model;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.List;

/**
 * Unit test for Employee.
 */
public class EmployeeTest
    extends TestCase {
  /**
   * Create the test case
   *
   * @param testName name of the test case
   */
  public EmployeeTest(String testName) {
    super(testName);
  }

  /**
   * @return the suite of tests being tested
   */
  public static Test suite() {
    return new TestSuite(EmployeeTest.class);
  }

  public void testGetReportTo() {
    Employee e1 = new Employee(1, "e1", 0);
    Employee e2 = new Employee(2, "e2", 1);
    Employee e3 = new Employee(3, "e3", 4);
    Employee e4 = new Employee(4, "e4", 1);

    assertFalse(e1.getReportTo().isPresent());

    assertEquals(e1, e2.getReportToNotNull());
    assertEquals(e1, e4.getReportToNotNull());
    assertEquals(e4, e3.getReportToNotNull());

    List<Employee> bossOfE3 = e3.getAllReportTo();
    assertTrue(bossOfE3.contains(e1));
    assertTrue(bossOfE3.contains(e4));
  }
}
