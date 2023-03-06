package com.mioyn;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Difference between FileReader and BufferedReader
 * <p>
 * FileReader and BufferedReader are both classes in Java used for reading character-based input streams, but they have some key differences.
 * <p>
 * Functionality: FileReader reads characters from a file, while BufferedReader reads characters from a character-based input stream such as a file, network socket, or string. FileReader is designed for simple file input operations, whereas BufferedReader provides additional functionality such as buffering and the ability to read an entire line of text at once.
 * <p>
 * Buffering: FileReader does not perform any buffering, which means that each read() operation will directly access the file, potentially resulting in many small file system calls. BufferedReader, on the other hand, reads data from an internal buffer and only accesses the underlying input stream when the buffer is empty. This can improve performance by reducing the number of file system calls.
 * <p>
 * Efficiency: FileReader is not as efficient as BufferedReader when reading large amounts of data. BufferedReader uses an internal buffer to minimize disk access, which can significantly improve performance when reading large files.
 * <p>
 * Line-oriented reading: BufferedReader provides methods such as readLine() which allow for efficient line-oriented reading of text, whereas FileReader does not.
 * <p>
 * In summary, FileReader is suitable for simple file input operations, while BufferedReader provides additional functionality and better performance for reading character-based input streams. If you are reading large files or need to perform line-oriented reading, BufferedReader is typically the better choice.
 *
 * Difference between BufferedReader and Files.lines
 *
 * Both BufferedReader and Files.lines are used for reading text files in Java, but they have some key differences.
 *
 * Functionality: BufferedReader is a general-purpose class for reading character-based input streams, whereas Files.lines is a convenience method introduced in Java 8 specifically for reading lines from a text file. Files.lines returns a Stream of lines, which can be processed using stream operations such as map and filter.
 *
 * Efficiency: BufferedReader is more efficient when reading large text files because it reads data from an internal buffer and only accesses the underlying input stream when the buffer is empty. Files.lines, on the other hand, reads data directly from the file, potentially resulting in many small file system calls.
 *
 * Convenience: Files.lines is more convenient for simple use cases where you just want to read the lines of a file and process them using stream operations. BufferedReader is more flexible and can be used for more complex use cases, such as reading CSV files or parsing custom file formats.
 *
 * Exception handling: BufferedReader requires explicit exception handling in the code, whereas Files.lines handles exceptions internally and returns a stream of lines as long as the file exists and is readable.
 *
 * In summary, BufferedReader is a more flexible and efficient way to read text files, especially when dealing with large files or custom file formats. Files.lines is a convenient way to read lines from a text file when you want to use stream operations for further processing.
 *
 *
 * difference between BufferedInputStream and bufferedReader?
 *
 * Both BufferedInputStream and BufferedReader are Java classes that provide buffering functionality for input streams. However, they have different use cases and are optimized for different types of input data.
 *
 * BufferedInputStream is used for reading binary data, such as images or audio files, from an input stream. It reads data from the underlying input stream in chunks and stores it in an internal buffer. This can improve performance by reducing the number of reads from the underlying stream. The BufferedInputStream provides methods like read() and read(byte[] b, int off, int len) to read data from the buffer.
 *
 * BufferedReader, on the other hand, is used for reading character data, such as text files, from an input stream. It reads data from the underlying input stream in chunks, converts it to characters using a specified character encoding, and stores it in an internal buffer. This can improve performance by reducing the number of reads from the underlying stream. The BufferedReader provides methods like read() and readLine() to read data from the buffer.
 *
 * In summary, BufferedInputStream is used for reading binary data from an input stream, while BufferedReader is used for reading character data from an input stream. Both classes provide buffering functionality, which can improve performance when reading data from an input stream.
 *
 */
public class ReadFile {
    public static void main(String[] args) {


        String user = System.getenv("USER");
        StringBuilder content = new StringBuilder();
        try (Scanner scanner = new Scanner(Path.of("C:\\Users\\" + user + "\\work\\sample_data\\male_teams.csv"))) {
            while (scanner.hasNextLine()) {
                content.append(scanner.nextLine()).append("\n");
            }
            System.out.println("Read with Scanner--> " + content.substring(0, 10) + "....");
        } catch (IOException e) {
            System.err.println("Something went wrong! " + e);
        }

        String str;
        try {
            str = Files.readString(Path.of("C:\\Users\\" + user + "\\work\\sample_data\\male_teams.csv"), StandardCharsets.UTF_8);
            System.out.println("Read with Files.readString --> " + str.substring(0, 10) + "....");
        } catch (IOException e) {
            System.err.println("Something went wrong! " + e);
        }
        List<String> maleTeams = new ArrayList<>();
        try {
            maleTeams = Files.readAllLines(Path.of("C:\\Users\\" + user + "\\work\\sample_data\\male_coaches.csv"),
                    StandardCharsets.UTF_8);
            System.out.println("Read with Files.readAllLines --> " + maleTeams.get(0));
        } catch (IOException e) {
            System.err.println("Something went wrong! " + e);
        }


        Map<String, String> collect = maleTeams.stream()
                .collect(Collectors.toMap(s -> s.split(",")[6], s -> s.split(",")[6], (s1, s2) -> s1));
        System.out.println(collect);

        try (BufferedReader reader = Files.newBufferedReader(Path.of("C:\\Users\\" + user + "\\work\\sample_data\\male_players.csv"),
                StandardCharsets.UTF_8)) {
            reader.mark(50);
            String line = reader.readLine();
            System.out.println("Read with Files.newBufferedReader --> " + line);

        } catch (IOException e) {
            System.err.println("Something went wrong! " + e);
        }

        try (BufferedWriter writer = Files.newBufferedWriter(Path.of("C:\\Users\\" + user + "\\work\\sample_data\\male_players_filter.csv"))) {

            try (Stream<String> lines = Files.lines(Path.of("C:\\Users\\" + user + "\\work\\sample_data\\male_players.csv"))) {

                lines.filter(s -> {
                            if (s.isEmpty()) {
                                return false;
                            }
                            String[] split = s.split(",");
                            return collect.get(split[27]) != null;
                        })
                        .forEach(s -> {
                            try {
                                writer.write(s);
                                writer.newLine();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        });
            }
        } catch (IOException e) {
            System.err.println("Something went wrong! " + e);
        }
    }
}
