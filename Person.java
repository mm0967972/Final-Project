import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Person {
    //attributes
    private String name;
    private int age;
    private String dob;
    private String ssn;
    private ArrayList<String> pets;

    //constructor
    public Person(String name, int age, String dob, String ssn, ArrayList<String> pets) {
        this.name = name;
        this.age = age;
        this.dob = dob;
        this.ssn = ssn;
        this.pets = pets;
    }

    //getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public ArrayList<String> getPets() {
        return pets;
    }

    public void setPets(ArrayList<String> pets) {
        this.pets = pets;
    }

    //method to greet
    public void Greet() {
        String petMessage;
        //check if person has pets
        if (pets.isEmpty()) {
            petMessage = "I do not have any pets.";
        } else {
            petMessage = "my pets are " + String.join(", ", pets) + ".";
        }
        //print greeting message
        System.out.printf("Hello! My name is %s, I am %d years old, and %s%n", name, age, petMessage);
    }

    //method to save persons to a csv file
    public static void saveToCSV(String filePath, ArrayList<Person> people) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            //write header
            writer.write("Name,Age,DOB,SSN,Pets");
            writer.newLine();

            //write each person's data
            for (Person person : people) {
                String petsAsString = String.join(";", person.getPets()); //pets are separated by semicolons
                writer.write(String.format("%s,%d,%s,%s,%s",
                        person.getName(),
                        person.getAge(),
                        person.getDob(),
                        person.getSsn(),
                        petsAsString));
                writer.newLine();
            }

            //confirmation message
            System.out.println("Data saved to " + filePath);
        } catch (IOException e) {
            //error handling
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    //method to load persons from a csv file
    public static ArrayList<Person> loadFromCSV(String filePath) {
        ArrayList<Person> people = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line = reader.readLine(); //read header
            while ((line = reader.readLine()) != null) {
                //split line into parts
                String[] parts = line.split(",", -1);
                String name = parts[0];
                int age = Integer.parseInt(parts[1]);
                String dob = parts[2];
                String ssn = parts[3];
                ArrayList<String> pets = new ArrayList<>(Arrays.asList(parts[4].split(";")));

                //create person object and add to list
                people.add(new Person(name, age, dob, ssn, pets));
            }

            //confirmation message
            System.out.println("Data loaded from " + filePath);
        } catch (IOException e) {
            //error handling
            System.err.println("Error reading from file: " + e.getMessage());
        }

        return people;
    }

    public static void main(String[] args) {
        //create person objects
        ArrayList<String> pets1 = new ArrayList<>(Arrays.asList("Dog", "Cat"));
        ArrayList<String> pets2 = new ArrayList<>(Arrays.asList("Parrot"));
        ArrayList<String> pets3 = new ArrayList<>(); //no pets
        ArrayList<String> pets4 = new ArrayList<>(Arrays.asList("Fish", "Hamster"));
        ArrayList<String> pets5 = new ArrayList<>(Arrays.asList("Turtle", "Rabbit"));

        Person person1 = new Person("John", 25, "01-01-1998", "123-45-6789", pets1);
        Person person2 = new Person("Jane", 30, "02-14-1994", "987-65-4321", pets2);
        Person person3 = new Person("Alice", 28, "12-05-1995", "555-55-5555", pets3);
        Person person4 = new Person("Bob", 35, "07-07-1988", "444-44-4444", pets4);
        Person person5 = new Person("Charlie", 22, "10-10-2002", "333-33-3333", pets5);

        //create arraylist and add person objects
        ArrayList<Person> people = new ArrayList<>();
        people.add(person1);
        people.add(person2);
        people.add(person3);
        people.add(person4);
        people.add(person5);

        //file path for csv
        String filePath = "people.csv";

        //save to csv
        saveToCSV(filePath, people);

        //load from csv
        ArrayList<Person> loadedPeople = loadFromCSV(filePath);

        //greet loaded people
        for (Person person : loadedPeople) {
            person.Greet();
        }
    }
}
