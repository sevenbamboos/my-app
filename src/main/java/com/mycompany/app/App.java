package com.mycompany.app;

import com.mycompany.model.Employee;
import com.mycompany.model.EmployeeTree;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 *
 */
public class App {
  public static void main(String[] args) throws IOException {
    Path filePath = FileSystems.getDefault().getPath(
        System.getProperty("user.dir"), "src/main/resources/com/mycompany/app", "sample1.csv");

    List<Employee> employees = new ArrayList<>();
    readFromLocalFile(filePath, line -> EmployeeTree.readFrom(line).ifPresent(employees::add));

    EmployeeTree.from(employees).visit(System.out::println);
  }

  private static void readFromLocalFile(Path path, Consumer<String> consumer) throws IOException {
    try (Stream<String> lines = Files.lines(path, Charset.forName("UTF-8"))) {
      lines.forEach(consumer::accept);
    }
  }
}
