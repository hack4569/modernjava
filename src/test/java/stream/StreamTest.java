package stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Comparator.comparing;
import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.*;

public class StreamTest {
    @Test
    void contextLoads() {
    }
    private List<Dish> menu;

    @BeforeEach
    public void 초기화(){
        menu = Arrays.asList(
                new Dish("pork", false, 800, Dish.Type.MEAT),
                new Dish("beef", false, 300, Dish.Type.MEAT),
                new Dish("chicken", false, 500, Dish.Type.MEAT),
                new Dish("rice", true, 160, Dish.Type.OTHER),
                new Dish("salmon", true, 300, Dish.Type.FISH),
                new Dish("prawns", false, 600, Dish.Type.FISH)
        );
    }

    @Test
    void 제곱근_구하기() {
        int[] values = {1,2,3,4,5};
        List<Integer> list = Arrays.stream(values).boxed().map(i -> i*i).collect(Collectors.toList());
        List<Integer> real = List.of(1,4,9,16,25);
        Assertions.assertEquals(list, real);
    }

    @Test
    void 모든쌍의_리스트() {
        List<Integer> list1 = Arrays.asList(1,2,3);
        List<Integer> list2 = Arrays.asList(3,4);
        List<int[]> pairs = list1.stream()
                .flatMap(i -> list2.stream()
                        .map(j -> new int[]{i, j})
                )
                .collect(toList());
    }

    @Test
    void 모든쌍의_리스트_and_3으로나누어떨어지는경우() {
        List<Integer> list1 = Arrays.asList(1,2,3);
        List<Integer> list2 = Arrays.asList(3,4);
        List<int[]> pairs = list1.stream()
                .flatMap(i -> list2.stream()
                        .map(j -> new int[]{i, j})
                )
                .filter(i -> Arrays.stream(i).sum() % 3 == 0)
                .collect(toList());
        pairs.forEach(pair -> System.out.println(Arrays.toString(pair)));
    }

    @Test
    void takeWhile_활용() {
        menu = menu.stream().sorted(Comparator.comparing(Dish::getCalories)).collect(toList());
        List<Dish> menuList = menu.stream()
                .takeWhile(i -> i.getCalories() < 300)
                .collect(toList());
        System.out.println(menuList);
    }

    @Test
    void 더하기() {
        int[] items = {1,2,3,4,5};
        int sum = Arrays.stream(items).reduce(0, Integer::sum);
        int real = 15;
        Assertions.assertEquals(sum, real);
    }

    @Test
    void 요리개수() {
        long cnt = menu.stream().map(i -> i.getName())
                .distinct()
                .count();
        System.out.println(cnt);
    }
    @Test
    void OptionalInt() {
        OptionalInt maxCalories = menu.stream().mapToInt(Dish::getCalories).max();
        int max = maxCalories.orElse(1);
        System.out.println(max);
    }

    @Test
    void 스트림만들기() {
        //값으로 스트림 만들기
        Stream<String> stream = Stream.of("Modern", "Java","In");
        stream.map(String::toUpperCase).forEach(System.out::println);

        //null이 될 수 있는 객체로 스트림 만들기
        String homeValue = System.getProperty("home");
        Stream<String> homeValueStream = homeValue == null ? Stream.empty() : Stream.of(homeValue);
        Stream<String> homeValueStream1 = Stream.ofNullable(System.getProperty("home"));

        //배열을 스트림으로 만들기
        int[] numbers = {1,2,3,4};
        int sum = Arrays.stream(numbers).sum();

        //함수로 스트림 만들기
        Stream.iterate(0, n -> n+2).limit(10).forEach(System.out::println);
    }

    //요약 연산
    @Test
    void collectTest() {
        int sum = menu.stream().collect(summingInt(Dish::getCalories));
        System.out.println(sum);
        int sum2 = menu.stream().collect(reducing(0, Dish::getCalories, (i, j) ->i + j));
        System.out.println(sum2);

        IntSummaryStatistics summary = menu.stream().collect(summarizingInt(Dish::getCalories));
        System.out.println(summary);

        String menuName = menu.stream().map(Dish::getName).collect(Collectors.joining(", "));
        System.out.println(menuName);
    }

    public enum CaloricLevel {DIET, NORMAL, FAT};
    //그룹화
    @Test
    void groupTest() {
        Map<Dish.Type, List<Dish>> groupMap = menu.stream().collect(groupingBy(Dish::getType));
        System.out.println(groupMap);

        Map<Dish.Type, List<Dish>> groupMap1 = menu.stream().filter(i -> i.getCalories() < 300).collect(groupingBy(Dish::getType));
        System.out.println(groupMap1);

        Map<Dish.Type, List<Dish>> groupMap2 = menu.stream().collect(groupingBy(Dish::getType, filtering(i -> i.getCalories() < 300, toList())));
        System.out.println(groupMap2);

        Map<Dish.Type, Map<CaloricLevel, List<Dish>>> groupedDishes = menu.stream().collect(
                groupingBy(Dish::getType, groupingBy(dish -> {
                    if (dish.getCalories() < 400)
                        return CaloricLevel.DIET;
                    else if (dish.getCalories() < 700)
                        return CaloricLevel.NORMAL;
                    else return CaloricLevel.FAT;
                }))
        );
        System.out.println(groupedDishes);

        //각 타입별 최대값
        Map<Dish.Type, Dish> mostCaloricByType =
                menu.stream()
                        .collect(
                                groupingBy(Dish::getType, collectingAndThen(
                                        maxBy(comparingInt(Dish::getCalories)),
                                        Optional::get
                                ))
                        );
        System.out.println(mostCaloricByType);

    }

    @Test
    void 분할_테스트() {
        Map<Boolean, Dish> mostCaloricPartitionedByVegetarian =
                menu.stream().collect(
                        partitioningBy(Dish::isVegetarian,
                                collectingAndThen(
                                        maxBy(comparingInt(Dish::getCalories)),
                                        Optional::get
                                )
                        )
                );
        System.out.println(mostCaloricPartitionedByVegetarian);
    }
}




