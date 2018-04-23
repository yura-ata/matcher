package com.yuriia.matcher;

import com.yuriia.matcher.impl.MatcherImpl;
import org.junit.Test;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertNotNull;

/**
 * @author yuriia
 */
public class MatchersTest {

    @Test
    public void exampleTest() {

        for (Object value : Arrays.asList("str", "long str", 5, "string", new Date())) {
            String a = Matchers.match(value, String.class)
                    .with(String.class).where(s -> s.length() > 5)
                        .get(s -> "long string: " + s)
                    .with(Integer.class)
                        .get(i -> "integer: " + i)
                    .with(v -> v.equals("str"))
                        .get(v -> "predicate matcher")
                    .defaultCase("nothing")
                    .value();
            assertNotNull(a);
            System.out.println(a);
        }


        for (Number number : Arrays.asList(77777L, 3, 3.3f)) {
            String value = Matchers.match(number, String.class)
                    .with(Integer.class)
                        .get(i -> "Integer: " + i)
                    .with(Long.class)
                        .get(l -> "Long: " + l)
                    .with(Float.class).where(f -> f < 3)
                        .get(f -> "Float: " + f)
                    .defaultCase("nothing")
                    .value();
            assertNotNull(value);
            System.out.println(value);
        }

        Matcher<Shape, Number> matcher = new MatcherImpl<>();
        for (Shape shape : Arrays.asList(new Circle(2), new Square(3), new Rectangle(2, 5))) {
            Number value = matcher.match(shape)
                    .with(Circle.class)
                        .get(c -> Math.PI * c.r * c.r)
                    .with(Square.class)
                        .get(s -> s.a * s.a)
                    .with(Rectangle.class)
                        .get(r -> r.a * r.b)
                    .value();
            assertNotNull(value);
            System.out.println(value);
        }
    }


    interface Shape {}

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