package com.mioyn;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.stream.Stream;

public class WithPaths {
    public static void main(String[] args) {
        String user = System.getenv("USER");
        System.out.println("----------------------Absolute path------------------------------------");
        Path path1 = Path.of("C:\\Users\\" + user + "\\work\\sample_data\\male_teams.csv");
        System.out.println(path1);
        pathProperties(path1);

        System.out.println("----------------------Not an Absolute path------------------------------------");
        Path path2 = Path.of("work","sample_data", "male_teams.csv");
        System.out.println(path2);
        pathProperties(path2);

        Path outputPath = FileSystems.getDefault()
                .getPath("C:\\Users\\" + user + "\\work\\sample_data\\tmp1");
        try {
            System.out.println("----------------------createDirectory------------------------------------");
            Files.createDirectory(outputPath);
            System.out.println("Type: Directory " + (Files.isDirectory(outputPath) ? "yes" : "no"));

            System.out.println("----------------------Files.copy------------------------------------");
            Path destPath = Path.of(outputPath.toAbsolutePath().toString(),
                    "sample.txt");
            Path path = Path.of("C:\\Users\\" + user + "\\work\\sample_data",".male_coaches.csv");
            Files.copy(path, destPath);
            double kilobytes = Files.size(destPath) / (double)1024;
            System.out.println("Size : "+ kilobytes);
            System.out.println("----------------------createFile------------------------------------");
            Path newFilePath = Path.of(outputPath.toAbsolutePath().toString(),
                    "sample3.txt");
            Files.createFile(newFilePath);
            System.out.println("Type: isRegularFile " + (Files.isRegularFile(newFilePath) ? "yes" : "no"));
            System.out.println("Type: isSymbolicLink " + (Files.isSymbolicLink(newFilePath) ? "yes" : "no"));
            System.out.println("Is Hidden: " + (Files.isHidden(newFilePath) ? "yes" : "no"));
            System.out.println("Is Readable: " + (Files.isReadable(newFilePath) ? "yes" : "no"));
            System.out.println("Is Writable: " + (Files.isWritable(newFilePath) ? "yes" : "no"));

            System.out.println("----------------------Files.move------------------------------------");
            Path copyFilePath = Path.of("C:\\Users\\" + user + "\\work\\sample_data","sample4.txt");
            Files.move(destPath, copyFilePath);
            System.out.println("Exists? : " + (Files.exists(copyFilePath)? "yes": "no"));
            System.out.println("File moved to: " + copyFilePath.toAbsolutePath());

            System.out.println("----------------------deleteIfExists------------------------------------");
            Files.deleteIfExists(copyFilePath);

            System.out.println("----------------------deleteDirectory with files in------------------------------------");
            try (Stream<Path> walk = Files.walk(outputPath)) {
                walk.sorted(Comparator.reverseOrder()).forEach(WithPaths::deleteDirectory);
            }
        } catch (FileAlreadyExistsException e) {
            System.err.println("Creation failed!" + e);
        } catch (IOException e) {
            System.err.println("Something unexpected happened!"+ e);
        }
    }
    public static void deleteDirectory(Path path) {
        try {
            Files.delete(path);
        } catch (IOException e) {
            System.err.printf("Unable to delete this path : %s%n%s", path, e);
        }
    }
    private static void pathProperties(Path path) {
        System.out.println("Location :" + path.toAbsolutePath());
        System.out.println("Is Absolute? : " + path.isAbsolute());
        System.out.println("Parent :" + path.getParent());

        System.out.println("Root :" + path.getRoot());
        System.out.println("FileName : " + path.getFileName());
        System.out.println("FileSystem : " + path.getFileSystem());
    }
}
