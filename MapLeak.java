import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

public class MapLeak {
    private final Map<Key, String> cache = new ConcurrentHashMap<>();

    private final static int OPS = 5_000_000;

    public static void main(String[] args) throws Exception {
        MapLeak ml = new MapLeak();

        Supplier<Key> generator = MapLeak::makeBadKey;
        if (Boolean.getBoolean("useGoodKey")) {
            generator = MapLeak::makeGoodKey;
        }

        long totalLen = 0;
        for (int i = 0; i < OPS; i++) {
            if (i % 100000 == 0) {
                System.out.printf("Executing %d op\n", i);
            }
            totalLen += ml.get(generator.get()).length();
        }

        System.out.printf("Length after %d ops: %d\n", OPS, totalLen);
    }

    private static Key makeBadKey() {
        return new Key(0, "xxx");
    }

    private static Key makeGoodKey() {
        return new GoodKey(0, "xxx");
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

        Key(final int x, final String name) {
            this.x = x;
            this.name = name;
        }

    }

    static class GoodKey extends Key {
        GoodKey(final int x, final String name) {
            super(x, name);
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            final Key key = (Key) o;
            return x == key.x &&
                    Objects.equals(name, key.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, name);
        }
    }
}
