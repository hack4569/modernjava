package stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class StreamTest {
    @Test
    void contextLoads() {
    }
    private List<Dish> menu;

    @BeforeEach
    public void 호출할때마다(){
        List<Dish> menu = Arrays.asList(
                new Dish("pork", false, 800, Dish.Type.MEAT),
                new Dish("beef", false, 300, Dish.Type.MEAT),
                new Dish("chicken", false, 500, Dish.Type.MEAT),
                new Dish("rice", true, 660, Dish.Type.OTHER),
                new Dish("salmon", true, 300, Dish.Type.FISH),
                new Dish("prawns", false, 100, Dish.Type.FISH)
        );
    }

    @Test
    void test() {
        menu.stream().filter(i -> i.getCalories() > 300)
                .map(Dish::getName)
                .collect(toList());
    }

    @Test
    void 제곱근_구하기() {
        int[] values = {1,2,3,4,5};
        List<Integer> list = Arrays.stream(values).boxed().map(i -> i*i).collect(Collectors.toList());
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
}
