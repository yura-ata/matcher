package com.yuriia.matcher;

import org.junit.Test;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.yuriia.matcher.Matcher.matcher;
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

        List<Map.Entry<Shape, Number>> shapes = asList(pair(new Circle(2), 12.566370614359172),
                pair(new Square(3), 9), pair(new Rectangle(2, 5), 10));
        Matcher<Shape, Number> matcher2 = matcher().from(Shape.class).to(Number.class)
                .when(Circle.class)
                    .then(c -> Math.PI * c.r * c.r)
                .when(Square.class)
                    .then(s -> s.a * s.a)
                .when(Rectangle.class)
                    .then(r -> r.a * r.b);
        for (Map.Entry<Shape, Number> shape : shapes) {
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










    @Test(expected = IllegalArgumentException.class)
    public void demoTest() {
        List<Shape> shapes = Arrays.asList(new Circle(2), new Square(3), new Rectangle(2, 5), new Shape() {});
        for (Shape shape : shapes) {

        }
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

//            Number area = Matcher.match(shape, Number.class)
//                    .when(Circle.class).where(c -> c.r > 0)
//                        .then(c -> Math.PI * c.r * c.r)
//                    .when(Square.class)
//                        .then(s -> s.a * s.a)
//                    .when(Rectangle.class)
//                        .then(r -> r.a * r.b)
//                    .orThrow(() -> new IllegalArgumentException("Unknown Shape"));
//            System.out.println(area);

}