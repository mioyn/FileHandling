package com.mioyn;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Objects;

public class WithFile {

    public static void main(String... args) {
        String user = System.getenv("USER");
        System.out.println("-------------------From File---------------------------------------");
        File file = new File("C:\\Users\\" + user + "\\work\\sample_data\\male_teams.csv");
        printFileStats(file);

        System.out.println("-------------------From URI---------------------------------------");
        try {
            URI uri = new URI("file:///C:/Users/" + user + "/work/sample_data/male_teams.csv");
            File f = new File(uri);
            printFileStats(f);
        } catch (URISyntaxException use) {
            System.err.println("Malformed URI, no file there"+ Arrays.toString(use.getStackTrace()));
        }

        System.out.println("----------------------A directory----------------------------------");
        System.out.println("A directory");
        File d = new File("C:\\Users\\" + user + "\\work\\sample_data");
        Arrays.stream(Objects.requireNonNull(d.listFiles())).forEach(ff ->
                System.out.println("\t File Name : "+ ff.getName()));

        System.out.println("----------------------Rename------------------------------------");
        File of = new File(
                "C:\\Users\\" + user + "\\work\\sample_data\\renamed.txt");
        File renamed = new File(
                "C:\\Users\\" + user + "\\work\\sample_data\\created.txt");
        boolean result = of.renameTo(renamed);
        System.out.println("Renaming succeeded? : "+ result);


    }

    private static void printFileStats(File f) {
        if (f.exists()) {
            System.out.println("File Details:");
            System.out.println("Type : " + (f.isFile() ? "file" : "directory or symlink"));
            System.out.println("Location :"+ f.getAbsolutePath());
            System.out.println("Parent :"+ f.getParent());
            System.out.println("Name : "+ f.getName());
            double kilobytes = f.length() / (double)1024;
            System.out.println("Size : "+ kilobytes);
            System.out.println("Is Hidden : "+ f.isHidden());
            System.out.println("Is Readable? : "+ f.canRead());
            System.out.println("Is Writable? : "+ f.canWrite());

        }
    }
}
