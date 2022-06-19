package ru.mystudies.basejava;

import java.io.File;
import java.util.Objects;

public class MainFile {
    public static void main(String[] args) throws Exception {
        File file = new File("HW/src/ru/mystudies/basejava");
        showAllFiles(file, "");
    }

    public static void showAllFiles(File f, String offset) {
        File files[] = Objects.requireNonNull(f.listFiles());
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    System.out.println(offset + "F: " + file.getName());
                } else {
                    System.out.println(offset + "D: " + file.getName());
                    showAllFiles(file, offset + "  ");
                }
            }
        }
    }
}


