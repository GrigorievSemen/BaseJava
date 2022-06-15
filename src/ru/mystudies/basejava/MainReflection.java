package ru.mystudies.basejava;

import ru.mystudies.basejava.model.Resume;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MainReflection {

    public static void main(String[] args) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Resume r = new Resume();
        Field field = r.getClass().getDeclaredFields()[0];
        field.setAccessible(true);
        System.out.println(field.getName());
        System.out.println(field.get(r));
        field.set(r, "new_uuid");

        // TODO : invoke r.toString via reflection
        Class<Resume> clazz = (Class<Resume>) r.getClass();
        Method method = clazz.getMethod("toString");
        Object ob = method.invoke(r);
        System.out.println("Result - " + ob);
    }
}