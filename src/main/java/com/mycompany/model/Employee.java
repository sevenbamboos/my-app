package com.mycompany.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class Employee {

  private static final Map<Long, Employee> cache = new HashMap<>();

  final long id;
  private final String name;
  private final long reportToID;

  private Set<Employee> subordinates = new HashSet<>();

  public Employee(long id, String name, long reportToID) {
    this.id = id;
    this.name = name;
    this.reportToID = reportToID;

    cache.put(id, this);
  }

  public String getName() {
    return name;
  }

  Employee add(Employee subordinate) {
    subordinates.add(subordinate);
    return this;
  }

  Set<Employee> getSubordinates() {
    return Collections.unmodifiableSet(subordinates);
  }

  void visit(Consumer<Employee> visitor) {
    visitor.accept(this);
    getSubordinates().forEach(su -> su.visit(visitor));
  }

  public Optional<Employee> getReportTo() {
    return Optional.ofNullable(cache.get(reportToID));
  }

  Employee getReportToNotNull() {
    return Objects.requireNonNull(cache.get(reportToID));
  }

  public List<Employee> getAllReportTo() {
    return _getAllReportTo(new LinkedList<>(), this);
  }

  private List<Employee> _getAllReportTo(LinkedList<Employee> allReportTo, Employee employee) {
    if (!employee.getReportTo().isPresent()) {
      return allReportTo;

    } else {
      Employee next = employee.getReportTo().get();
      allReportTo.addFirst(next);
      return _getAllReportTo(allReportTo, next);
    }
  }

  @Override
  public String toString() {
    return String.format("-> %s %s", getAllReportTo().stream().map(em -> "->").collect(Collectors.joining(" ")), name);
  }

  public String debugString() {
    return String.format("ID:%d\t Name:%s\t Report-to:%s", id, name, getReportTo().map(Employee::getName).orElse("No one"));
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Employee employee = (Employee) o;
    return id == employee.id;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  public static Optional<Employee> from(String s) {
    try {
      String[] tokens = s.split(",");
      Employee newInstance = new Employee(Long.parseLong(tokens[0]), tokens[1], Long.parseLong(tokens[2]));
      return Optional.of(newInstance);

    } catch (Exception e) {
      return Optional.empty();
    }
  }
}
