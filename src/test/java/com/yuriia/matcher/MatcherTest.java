package com.yuriia.matcher;

import org.junit.Test;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.yuriia.matcher.Matcher.matcher;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

/**
 * Matcher simple tests.
 *
 * @author yuriia
 */
public class MatcherTest {

    @Test
    public void testSimpleUseCases() {
        List<Map.Entry<Object, String>> objects = asList(
                pair("str", "predicate matcher"), pair("long str", "long string: long str"),
                pair(5, "integer: 5"), pair("string", "long string: string"), pair(new Date(), "nothing")
        );
        Matcher<Object, String> matcher = matcher().to(String.class)
                .when(String.class).where(s -> s.length() > 5)
                    .then(s -> "long string: " + s)
                .when(Integer.class)
                    .then(i -> "integer: " + i)
                .when(v -> v.equals("str"))
                    .then(v -> "predicate matcher")
                .orDefault("nothing");
        for (Map.Entry<Object, String> object : objects) {
            String value = matcher.match(object.getKey());
            assertEquals(object.getValue(), value);
        }


        List<Map.Entry<Number, String>> numbers = asList(pair(77777L, "Long: 77777"),
                pair(3, "Integer: 3"), pair(3.3f, "nothing"));
        for (Map.Entry<Number, String> number : numbers) {
            String value = matcher().from(Number.class).to(String.class)
                    .when(Integer.class)
                        .then(i -> "Integer: " + i)
                    .when(Long.class)
                        .then(l -> "Long: " + l)
                    .when(Float.class).where(f -> f < 3)
                        .then(f -> "Float: " + f)
                    .orDefault("nothing")
                    .match(number.getKey());
            assertEquals(number.getValue(), value);
        }

        List<Map.Entry<Demo.Shape, Number>> shapes = asList(pair(new Demo.Circle(2), 12.566370614359172),
                pair(new Demo.Square(3), 9), pair(new Demo.Rectangle(2, 5), 10));
        Matcher<Demo.Shape, Number> matcher2 = matcher().from(Demo.Shape.class).to(Number.class)
                .when(Demo.Circle.class)
                    .then(c -> Math.PI * c.r * c.r)
                .when(Demo.Square.class)
                    .then(s -> s.a * s.a)
                .when(Demo.Rectangle.class)
                    .then(r -> r.a * r.b);
        for (Map.Entry<Demo.Shape, Number> shape : shapes) {
            Number value = matcher2.match(shape.getKey());
            assertEquals(shape.getValue(), value);
        }

        List<Map.Entry<Number, String>> numbers2 = asList(pair(777L, "instanceof Long: 777"), pair(3, "constant 3"));
        for (Map.Entry<Number, String> number : numbers2) {
            String value = matcher().from(Number.class).to(String.class)
                    .when(3)
                        .then(i -> "constant 3")
                    .when(Long.class)
                        .then(l -> "instanceof Long: " + l)
                    .when(Float.class).where(f -> f < 3)
                        .then(f -> "Float: " + f)
                    .orDefault(() -> "nothing")
                    .match(number.getKey());
            assertEquals(number.getValue(), value);
        }
    }

    @Test
    public void testInstanceOfVsWhen() {
       String value = matcher().to(String.class)
                .when(Number.class)
                    .then(n -> "Should not be returned, we check types directly")
                .when(Long.class)
                    .then(l -> "correct")
                .match(42L);

       assertEquals("correct", value);

        value = matcher().to(String.class)
                .instanceOf(Number.class)
                    .then(n -> "correct")
                .when(Long.class)
                    .then(l -> "Should not be returned, we check instanceof")
                .match(42L);

        assertEquals("correct", value);
    }

    @Test
    public void testDefaultValues() {
        String value = matcher().to(String.class)
                .when(Boolean.class)
                    .then(n -> "boolean")
                .when(Long.class)
                    .then(l -> "long")
                .match("string");
        assertNull(value);

        value = matcher().to(String.class)
                .when(Boolean.class)
                    .then(n -> "boolean")
                .when(Long.class)
                    .then(l -> "long")
                .orDefault(() -> "default")
                .match("string");
        assertEquals("default", value);

        value = matcher().to(String.class)
                .when(Boolean.class)
                    .then(n -> "boolean")
                .when(Long.class)
                    .then(l -> "long")
                .orDefault("default")
                .match("string");
        assertEquals("default", value);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testExceptionAsDefault() {
        matcher().get()
                .when(Boolean.class)
                    .then(n -> "boolean")
                .when(Long.class)
                    .then(l -> "long")
                .orThrow(new IllegalArgumentException())
                .match("string");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testExceptionSupplierAsDefault() {
        matcher().get()
                .when(Boolean.class)
                    .then(n -> "boolean")
                .when(Long.class)
                    .then(l -> "long")
                .orThrow(IllegalArgumentException::new)
                .match("string");
    }

    @Test
    public void testNullResult() {
        String value = matcher().to(String.class)
                .when(Boolean.class)
                    .then(n -> "boolean")
                .when(String.class)
                    .then(s -> null)
                .orThrow(new IllegalArgumentException())
                .match("string");
        assertNull(value);
    }

    @Test
    public void testMatchAsOptional() {
        Optional<String> value = matcher().to(String.class)
                .when(Boolean.class)
                    .then(n -> "boolean")
                .when(Long.class)
                    .then(l -> "long")
                .matchAsOptional("string");
        assertFalse(value.isPresent());
    }

    private static <K, V> Map.Entry<K, V> pair(K key, V value) {
        return new Map.Entry<K, V>() {

            @Override
            public K getKey() {
                return key;
            }

            @Override
            public V getValue() {
                return value;
            }

            @Override
            public V setValue(V value) {
                throw new UnsupportedOperationException();
            }
        };
    }
}