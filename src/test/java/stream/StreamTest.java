package stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

public class StreamTest {
    @Test
    void contextLoads() {
    }
    private List<Dish> menu;

    @BeforeEach
    public void 호출할때마다(){
        menu = Arrays.asList(
                new Dish("pork", false, 800, Dish.Type.MEAT),
                new Dish("beef", false, 300, Dish.Type.MEAT),
                new Dish("chicken", false, 500, Dish.Type.MEAT),
                new Dish("rice", true, 660, Dish.Type.OTHER),
                new Dish("salmon", true, 300, Dish.Type.FISH),
                new Dish("prawns", false, 100, Dish.Type.FISH)
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
}
