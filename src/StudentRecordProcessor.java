import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StudentRecordProcessor {
    // Поля для хранения данных
    private final List<Student> students = new ArrayList<>();

    // _____реализуйте класс Student ниже в этом же файле______

    private double averageScore;
    private Student highestStudent;


    /**
     * Task 1 + Task 2 + Task 5 + Task 6
     */
    public void readFile() {
        // TODO: реализуйте чтение файла здесь
        try (BufferedReader bf = new BufferedReader(new FileReader("input/students.txt"))){
            String line;
            while ((line = bf.readLine()) != null){
                try{
                    String[] parts = line.split(",");

                    if(parts.length != 2){
                        throw new InvalidScoreException("Invalid format");
                    }

                    String name = parts[0];
                    int score = Integer.parseInt(parts[1]);

                    if (score < 0 || score > 100){
                        throw new InvalidScoreException("Score out of range");
                    }
                    students.add(new Student(name,score));
                    System.out.println("Valid: " + line);
                }
                catch (NumberFormatException | InvalidScoreException e){
                    System.out.println("Invalid data: " + line);
                }
            }
        }
        catch (FileNotFoundException e){
            System.out.println("File is not found");
        }
        catch (IOException e){
            System.out.println("Something went wrong");
        }
    }

    /**
     * Task 3 + Task 8
     */
    public void processData() {
        // TODO: обработка данных и сортировка здесь
        if(students.isEmpty()){
            return;
        }
        int sum = 0;
        highestStudent = students.get(0);

        for (Student s : students){
            sum += s.getScore();

            if(s.getScore() > highestStudent.getScore()){
                highestStudent = s;
            }
        }

        averageScore = (double) sum / students.size();
        Collections.sort(students);
        System.out.println(students);
    }

    /**
     * Task 4 + Task 5 + Task 8
     */
    public void writeFile() {
        // TODO: запись результата в файл здесь
        try(BufferedWriter bw = new BufferedWriter(new FileWriter("output/report.txt"))){
            if (students.isEmpty()) {
                System.out.println("No data to write");
                return;
            }

            bw.write("Average: " + averageScore);
            bw.newLine();

            bw.write("Highest: " + highestStudent.getName() + " - " + highestStudent.getScore());
            bw.newLine();

            for (Student s : students){
                bw.write(s.getName() + " - " + s.getScore());
                bw.newLine();
            }
        }
        catch (IOException e){
            System.out.println("Something went wrong");
        }
    }

    public static void main(String[] args) {
        StudentRecordProcessor processor = new StudentRecordProcessor();

        try {
            processor.readFile();
            processor.processData();
            processor.writeFile();
            System.out.println("Processing completed. Check output/report.txt");
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
        }
    }
}

// class InvalidScoreException реализуйте меня

class InvalidScoreException extends Exception{
    public InvalidScoreException(String msg){
        super(msg);
    }
}

class Student implements Comparable<Student>{
    private String name;
    private int score;

    public Student(String name,int score){
        this.name = name;
        this.score = score;
    }

    @Override
    public int compareTo(Student other){
        return Integer.compare(other.getScore(), score);
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", score=" + score +
                '}';
    }
}

