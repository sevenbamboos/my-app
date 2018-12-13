package com.mycompany.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

public final class EmployeeTree {

  private static final Map<Long, Employee> cache = new HashMap<>();

  private final Employee root;

  private EmployeeTree(Employee root) {
    this.root = root;
  }

  public static EmployeeTree from(List<Employee> employees) {

    Employee root = null;

    for (Employee em : employees) {
      if (em.getReportTo().isPresent()) {
        Employee reportTo = em.getReportTo().get();
        reportTo.add(em);

      } else {
        if (root != null) {
          throw new RuntimeException("More than two root for a tree:" + em.debugString());
        }
        root = em;
      }
    }

    return new EmployeeTree(root);
  }

  public void visit(Consumer<Employee> visitor) {
    root.visit(visitor);
  }

  public static Optional<Employee> readFrom(String s) {

    Optional<Employee> employee = Employee.from(s);
    if (employee.isPresent()) {
      Employee em = employee.get();
      cache.put(em.id, em);
    }

    return employee;
  }

  static Optional<Employee> getEmployeeByID(long id) {
    return Optional.ofNullable(cache.get(id));
  }
}
