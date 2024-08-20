package stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Map.entry;

/**
 * <pre>
 *
 * </pre>
 *
 * @author Hong GilDong
 */
public class MapTest {
    private Map<String, Object> map = new HashMap<>();

    @BeforeEach
    public void 초기화() {
        map.put("name", "lsh");
        map.put("age", "33");
        map.put("hobby", "running");
        map.put("like", "health");
        map.put("address", "seoul");
        map.put("love", "marie");
        map.put("precious", "fashion");
        map.put("movies", null);
    }

    @Test
    public void foreach() {
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey();
            System.out.println(key + ":" + entry.getValue());
        }
    }

    @Test
    public void compareKey() {
        map.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .forEachOrdered(System.out::println);
    }

    @Test
    public void computeIfPresent_regacy() {
        String movies = "movies";
        List<String> moviesLabel = (List<String>)map.get(movies);
        if (moviesLabel == null) {
            moviesLabel = new ArrayList<>();
            moviesLabel.add("개츠비");
            moviesLabel.add("노트북");
            map.put(movies, moviesLabel);
        }
        System.out.println(moviesLabel);
    }

    @Test
    public void computeIfPresent_modern() {
        List<String> list = (List<String>)map.computeIfAbsent("movies", movies -> new ArrayList<>());
        list.add("노트북");
        System.out.println(list);
    }

    @Test
    public void merge() {
        Map<String, Object> everyOne = new HashMap<>(map);
        Map<String, Object> friends = Map.ofEntries(
                entry("name", "lff"), entry("personality", "INFP")
        );
        friends.forEach((k, v) ->
                everyOne.merge(k, v, (value1, value2) -> value1 + " & " + value2));
        System.out.println(everyOne);
    }

    @Test
    public void 초기화검사_regacy() {
        Map<String, Integer> moviesToCount = new HashMap<>();
        String movieName = "notebook";
        int count = moviesToCount.get(movieName);
        if (count == 0) {
            moviesToCount.put(movieName, 1);
        } else {
            moviesToCount.put(movieName, count + 1);
        }
    }

    @Test
    public void 초기화검사_modern() {
        Map<String, Long> moviesToCount = new HashMap<>();
        String movieName = "notebook";

        moviesToCount.merge(movieName, 1L, (key, count) -> count + 1L);
    }
}
