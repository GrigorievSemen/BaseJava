package ru.mystudies.basejava;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.partitioningBy;
import static java.util.stream.Collectors.toList;
//for test
public class MainStreams {
    public static void main(String[] args) throws IOException {
        System.out.println(minValue(new int[]{1, 2, 3, 3, 2, 3})); //123
        System.out.println(minValue(new int[]{9, 8})); //89

        System.out.println(oddOrEven(new ArrayList<>(List.of(1, 2, 3, 4, 5, 6)))); // [2, 4, 6]
        System.out.println(oddOrEven(new ArrayList<>(List.of(2, 2, 2, 2, 2, 4)))); // []
        System.out.println(oddOrEven(new ArrayList<>(List.of(3, 3, 3, 3, 3, 1)))); // [3, 3, 3, 3, 3, 1]
    }

    public static int minValue(int[] numbers) {
        return Arrays.stream(numbers)
                .distinct()
                .sorted()
                .reduce((x, y) -> x * 10 + y)
                .orElseThrow();
    }

    public static List<Integer> oddOrEven(List<Integer> integers) {
        // разделяем коллекцию на две части четные/нечетные по соответствию условия и возвращаем их как Map<Boolean, List>
        Map<Boolean, List<Integer>> map = integers.stream()
                .collect(partitioningBy(x -> x % 2 != 0, toList()));
        return map.get(map.get(true).size() % 2 == 0);
    }
}

