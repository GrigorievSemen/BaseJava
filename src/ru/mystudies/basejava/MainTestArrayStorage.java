package ru.mystudies.basejava;

import ru.mystudies.basejava.model.Resume;
import ru.mystudies.basejava.storage.ArrayStorage;

import java.io.IOException;
//for test
public class MainTestArrayStorage {
    static final ArrayStorage ARRAY_STORAGE = new ArrayStorage();
    // static final MapStorage MAP_STORAGE = new MapStorage();

    public static void main(String[] args) throws IOException {
        Resume r1 = new Resume("uuid1", "Петров Петр Петрович");
        Resume r2 = new Resume("uuid2", "Иванов Иван Иванович");
        Resume r3 = new Resume("uuid3", "Сидоров Сергей Сергеевич");

//        MAP_STORAGE.save(r1);
//        printAll();
//        MAP_STORAGE.save(r2);
//        printAll();
//        MAP_STORAGE.save(r3);
//        printAll();


        ARRAY_STORAGE.save(r1);
        ARRAY_STORAGE.save(r2);
        ARRAY_STORAGE.save(r3);

        System.out.println("Get r1: " + ARRAY_STORAGE.get(r1.getUuid()));
        System.out.println("Size: " + ARRAY_STORAGE.size());

        // System.out.println("Get dummy: " + ARRAY_STORAGE.get("dummy"));

        printAll();
        ARRAY_STORAGE.delete(r1.getUuid());
        printAll();
        ARRAY_STORAGE.clear();
        printAll();

        System.out.println("Size: " + ARRAY_STORAGE.size());
    }

    static void printAll() throws IOException {
        System.out.println("\nGet All");
        for (Resume r : ARRAY_STORAGE.getAllSorted()) {
            System.out.println(r);
        }
    }
//    static  void printAll(){
//        System.out.println("\nGet All");
//        for (Resume r : MAP_STORAGE.getAll()) {
//            System.out.println(r);
//        }
//    }
}
