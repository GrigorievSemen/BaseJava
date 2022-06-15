package ru.mystudies.basejava;


import java.io.File;
import java.util.Objects;

public class MainFile {
    private static int count = 0;

    public static void main(String[] args) throws Exception {
        File file = new File("HW/src");
        showAllFiles(file);
    }

    public static void showAllFiles(File f) {
        File files[] = Objects.requireNonNull(f.listFiles());

        if (count != 0) {
            count++;
        }

        for (int i = 0; i < files.length; i++) {
            if (files[i].isDirectory()) {
                System.out.print("\t".repeat(count++));
                System.out.println(files[i].getAbsolutePath());
                showAllFiles(new File(files[i].getAbsolutePath()));
            } else {
                System.out.print("\t".repeat(count));
                System.out.println(files[i].getAbsolutePath());
                if (i + 1 == files.length) {
                    count = count - 2;
                }
            }
        }
    }
}


