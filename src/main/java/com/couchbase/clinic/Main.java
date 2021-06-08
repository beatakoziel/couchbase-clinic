package com.couchbase.clinic;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.CouchbaseCluster;
import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.document.json.JsonArray;
import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.query.N1qlQuery;
import com.couchbase.client.java.query.N1qlQueryResult;
import com.couchbase.client.java.query.N1qlQueryRow;
import com.couchbase.clinic.models.Department;
import com.couchbase.clinic.models.Doctor;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;
import java.util.regex.Pattern;

public class Main {
/*
    // Initialize the Connection
    Cluster cluster = CouchbaseCluster.create("localhost");
        cluster.authenticate("admin", "adminadmin");
    Bucket bucket = cluster.openBucket("bucketname");

    // Create a JSON Document
    JsonObject arthur = JsonObject.create()
            .put("name", "Arthur")
            .put("email", "kingarthur@couchbase.com")
            .put("interests", JsonArray.from("Holy Grail", "African Swallows"));

    // Store the Document
    String id = String.valueOf(UUID.randomUUID());
        bucket.upsert(JsonDocument.create(id, arthur));

    // Load the Document and print it
    // Prints Content and Metadata of the stored Document
        System.out.println(bucket.get(id));*/
public static void main(String[] args) throws IOException {
    // Initialize the Connection
    Cluster cluster = CouchbaseCluster.create("localhost");
    cluster.authenticate("admin", "adminadmin");
    Bucket doctorsMap = cluster.openBucket("doctors");
    Bucket departmentsMap = cluster.openBucket("departments");
    while (true) {
        Integer choice = printMenu();
        clearScreen();
        System.out.println(choice);
        if (choice > 0 && choice < 9) {
            switch (choice) {
                case 1:
                    addElementToDatabase(doctorsMap, departmentsMap);
                    break;
                case 2:
/*                    editElement(doctorsMap, departmentsMap);*/
                    break;
                case 3:
                    getElementById(doctorsMap, departmentsMap);
                    break;
                case 4:
                    getAll(doctorsMap, departmentsMap);
                    break;
                case 5:
                    removeElement(doctorsMap, departmentsMap);
                    break;
                case 6:
/*                    setSalary(doctorsMap);*/
                    break;
                case 7:
                    getElementByName(doctorsMap, departmentsMap);
                    break;
            }
            System.out.println("Press enter to continue...");
            System.in.read();
        } else System.out.println("Wrong number, choose again.");
    }
}

    private static void getAll(Bucket doctors, Bucket departments) throws IOException {
        System.out.println("Getting all values");
        Integer s = printSubMenu();
        if (s > 0 && s < 3) {
            switch (s) {
                case 1:
                    N1qlQueryResult result = doctors.query(N1qlQuery.simple("SELECT * FROM `doctors`"));
                    for (N1qlQueryRow row : result) {
                        String Name = row.value().getString("firstName");
                        System.out.println(Name);
                    }
                    break;
                case 2:
                    N1qlQueryResult result2 = departments.query(N1qlQuery.simple("SELECT * FROM `departments`"));
                    for (N1qlQueryRow row : result2) {
                        String Name = row.value().getString("firstName");
                        System.out.println(Name);
                    }
                    break;
            }
        } else System.out.println("Wrong number, choose again.");
    }

    private static void getElementById(Bucket doctors, Bucket departments) throws IOException {
        System.out.println("Getting by id");
        Integer s = printSubMenu();
        Scanner scanner = new Scanner(System.in);
        if (s > 0 && s < 3) {
            System.out.println("Write id:");
            switch (s) {
                case 1:
                    String playerId = scanner.next();
                    System.out.println(doctors.get(playerId));
                    break;
                case 2:
                    String departmentId = scanner.next();
                    System.out.println(departments.get(departmentId));
                    break;
            }
        } else System.out.println("Wrong number, choose again.");
    }

    private static void getElementByName(Bucket doctors, Bucket departments) throws IOException {
        System.out.println("Getting by name");
        Integer s = printSubMenu();
        Scanner scanner = new Scanner(System.in);
        if (s > 0 && s < 3) {
            System.out.println("Write name:");
            switch (s) {
                case 1:
                    String doctorName = scanner.next();
                    N1qlQueryResult result = doctors.query(N1qlQuery.simple(String.format("SELECT * FROM `doctors` d WHERE d.firstName = %s", doctorName)));
                    for (N1qlQueryRow row : result) {
                        String Name = row.value().getString("firstName");
                        System.out.println(Name);
                    }
                    break;
                case 2:
                    String departmentName = scanner.next();
                    N1qlQueryResult result2 = doctors.query(N1qlQuery.simple(String.format("SELECT * FROM `doctors` d WHERE d.firstName = %s", departmentName)));
                    for (N1qlQueryRow row : result2) {
                        String Name = row.value().getString("firstName");
                        System.out.println(Name);
                    }
                    break;
            }
        } else System.out.println("Wrong number, choose again.");
    }

    private static void editElement(Bucket doctors, Bucket departments) {
        System.out.println("Editing");
        Integer s = printSubMenu();
        Scanner scanner = new Scanner(System.in);
        if (s > 0 && s < 3) {
            System.out.println("Write id:");
            switch (s) {
                case 1:
                    String playerId = scanner.next();
                    Doctor doctor = getDoctorFromUser( scanner);
                    JsonObject arthur = JsonObject.create()
                            .put("firstName", doctor.getFirstName())
                            .put("lastName", doctor.getLastName());
                    doctors.upsert(JsonDocument.create(playerId, arthur));
                    break;
                case 2:
                    String departmentId = scanner.next();
                    Department department = getSportDepartment( scanner);
                    JsonObject arthur2 = JsonObject.create()
                            .put("name", department.getName());
                    doctors.upsert(JsonDocument.create(departmentId, arthur2));
                    break;
            }
        } else System.out.println("Wrong number, choose again.");
    }

    private static void removeElement(Bucket doctors, Bucket departments) {
        System.out.println("Removing");
        Integer s = printSubMenu();
        Scanner scanner = new Scanner(System.in);
        if (s > 0 && s < 3) {
            System.out.println("Write id:");
            switch (s) {
                case 1:
                    String playerId = scanner.next();
                    doctors.remove(playerId);
                    break;
                case 2:
                    String departmentId = scanner.next();
                    departments.remove(departmentId);
                    break;
            }
        } else System.out.println("Wrong number, choose again.");
    }

    private final static Pattern UUID_REGEX_PATTERN =
            Pattern.compile("^[{]?[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}[}]?$");

    private static void addElementToDatabase(Bucket doctors, Bucket departments) {
        System.out.println("Adding to database");
        Integer s = printSubMenu();
        Scanner scanner = new Scanner(System.in);
        if (s > 0 && s < 3) {
            switch (s) {
                case 1:
                    Doctor doctor = getDoctorFromUser( scanner);
                    JsonObject arthur = JsonObject.create()
                            .put("firstName", doctor.getFirstName())
                            .put("lastName", doctor.getLastName());
                    String id = String.valueOf(UUID.randomUUID());
                    doctors.upsert(JsonDocument.create(id, arthur));
                    break;
                case 2:
                    Department department = getSportDepartment( scanner);
                    JsonObject arthur2 = JsonObject.create()
                            .put("name", department.getName());
                    String id2 = String.valueOf(UUID.randomUUID());
                    doctors.upsert(JsonDocument.create(id2, arthur2));
                    break;
            }
        } else System.out.println("Wrong number, choose again.");
    }

/*    private static void setSalary(Bucket doctors) {
        System.out.println("Calculate average salary");
        doctors.updateMany(eq("surname", "Nowak"), set("salary", 10000));
    }*/

    private static Department getSportDepartment(Scanner scanner) {
        System.out.println("Write department name:");
        String name = scanner.next();
        return Department.builder()
                .name(name)
                .build();
    }

    private static Doctor getDoctorFromUser(Scanner scanner) {
        System.out.println("Write doctor first name:");
        String firstname = scanner.next();
        System.out.println("Write doctor surname:");
        String surname = scanner.next();
        return Doctor.builder()
                .firstName(firstname)
                .lastName(surname)
                .build();
    }

    private static Integer printMenu() {
        System.out.println("\nCLINIC - MONGODB");
        System.out.println("\nChoose operation:");
        System.out.println("1.ADD");
        System.out.println("2.EDIT");
        System.out.println("3.GET BY ID");
        System.out.println("4.GET ALL");
        System.out.println("5.REMOVE");
        System.out.println("6.GIVE BIGGER SALARY");
        System.out.println("7.GET BY NAME");
        Scanner scan = new Scanner(System.in);
        return scan.nextInt();
    }

    private static Integer printSubMenu() {
        System.out.println("\nChoose table:");
        System.out.println("1.DOCTORS");
        System.out.println("2.DEPARTMENTS");
        Scanner scan = new Scanner(System.in);
        return scan.nextInt();
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}

