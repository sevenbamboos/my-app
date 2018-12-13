package com.mycompany.model;

import java.util.List;
import java.util.function.Consumer;

public final class EmployeeTree {

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

  Employee root() {
    return root;
  }

  public void visit(Consumer<Employee> visitor) {
    root.visit(visitor);
  }
}
