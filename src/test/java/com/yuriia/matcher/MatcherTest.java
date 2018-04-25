package com.yuriia.matcher;

import com.yuriia.matcher.impl.MatcherImpl;
import org.junit.Test;

import java.util.Date;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

/**
 * TODO: add more tests, some features and corner cases are not tested at all
 *
 * @author yuriia
 */
public class MatcherTest {

    @Test
    public void exampleTest() {
        List<Map.Entry<Object, String>> objects = asList(
                pair("str", "predicate matcher"), pair("long str", "long string: long str"),
                pair(5, "integer: 5"), pair("string", "long string: string"), pair(new Date(), "nothing")
        );
        for (Map.Entry<Object, String> object : objects) {
            String value = Matcher.match(object.getKey(), String.class)
                    .is(String.class).where(s -> s.length() > 5)
                        .get(s -> "long string: " + s)
                    .is(Integer.class)
                        .get(i -> "integer: " + i)
                    .with(v -> v.equals("str"))
                        .get(v -> "predicate matcher")
                    .orGet("nothing");
            assertEquals(object.getValue(), value);
        }


        List<Map.Entry<Number, String>> numbers = asList(pair(77777L, "Long: 77777"),
                pair(3, "Integer: 3"), pair(3.3f, "nothing"));
        for (Map.Entry<Number, String> number : numbers) {
            String value = Matcher.match(number.getKey(), String.class)
                    .is(Integer.class)
                        .get(i -> "Integer: " + i)
                    .is(Long.class)
                        .get(l -> "Long: " + l)
                    .is(Float.class).where(f -> f < 3)
                        .get(f -> "Float: " + f)
                    .orGet("nothing");
            assertEquals(number.getValue(), value);
        }

        List<Map.Entry<Shape, Number>> shapes = asList(pair(new Circle(2), 12.566370614359172),
                pair(new Square(3), 9), pair(new Rectangle(2, 5), 10));
        Matcher<Shape, Number> matcher = new MatcherImpl<>();
        for (Map.Entry<Shape, Number> shape : shapes) {
            Number value = matcher.match(shape.getKey())
                    .is(Circle.class)
                        .get(c -> Math.PI * c.r * c.r)
                    .is(Square.class)
                        .get(s -> s.a * s.a)
                    .is(Rectangle.class)
                        .get(r -> r.a * r.b)
                    .get();
            assertEquals(shape.getValue(), value);
        }

        List<Map.Entry<Number, String>> numbers2 = asList(pair(777L, "instanceof Long: 777"), pair(3, "constant 3"));
        for (Map.Entry<Number, String> number : numbers2) {
            String value = Matcher.match(number.getKey(), String.class)
                    .is(3)
                        .get(i -> "constant 3")
                    .with(Long.class)
                        .get(l -> "instanceof Long: " + l)
                    .is(Float.class).where(f -> f < 3)
                        .get(f -> "Float: " + f)
                    .orGet(() -> "nothing");
            assertEquals(number.getValue(), value);
        }
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

    interface Shape {
    }

    static final class Circle implements Shape {
        int r;
        Circle(int r) {
            this.r = r;
        }
    }

    static final class Square implements Shape {
        int a;
        Square(int a) {
            this.a = a;
        }
    }

    static final class Rectangle implements Shape {
        int a, b;
        Rectangle(int a, int b) {
            this.a = a;
            this.b = b;
        }
    }
}