package com.yuriia.matcher;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static com.yuriia.matcher.Matcher.matcher;

/**
 * Matcher demo.
 *
 * @author yuriia
 */
public class Demo {

    @Test
    public void demoTest() {
        List<Shape> shapes = Arrays.asList(new Circle(2), new Square(3), new Rectangle(2, 5), new Shape() {});

        Matcher<Shape, Number> matcher = matcher().from(Shape.class).to(Number.class)
                .when(Circle.class)
                    .then(c -> c.r * c.r * Math.PI)
                .when(Square.class).where(s -> s.a > 2)
                    .then(s -> s.a * s.a)
                .orDefault(42);


        for (Shape shape : shapes) {
            System.out.println(matcher.match(shape));
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
}
