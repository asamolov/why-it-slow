import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MapLeak {
    private final Map<Key, String> cache = new ConcurrentHashMap<>();

    private final static int OPS = 5_000_000;

    public static void main(String[] args) {
        MapLeak ml = new MapLeak();

        long totalLen = 0;
        for (int i = 0; i < OPS; i++) {
            if (i % 100000 == 0) {
                System.out.printf("Executing %d op\n", i);
            }
            totalLen += ml.get(new Key(0, "xxx")).length();
        }

        System.out.printf("Length after %d ops: %d\n", OPS, totalLen);
    }

    public String get(Key key) {
        return cache.computeIfAbsent(key, (k) -> obtainValue(k));
    }

    private String obtainValue(Key k) {
        return "x";
    }

    static class Key {
        final int x;
        final String name;

        public Key(final int x, final String name) {
            this.x = x;
            this.name = name;
        }
    }
}
