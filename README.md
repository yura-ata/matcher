# matcher
I'm bored. Simple pet project to play with fluent API.

Java does not (yet) have Pattern Matching, though many other languages have it (Scala, C#, Haskel, 
Java + vavr (though it is a bit weird)). But it would be nice to play around with an API which emulates 
Pattern Matching in Java (and it should be fun to try to make in O(1) were it is possible).

Goal is to write something like:
```
Matcher<Shape, Number> matcher = new MatcherImpl<>();
for (Shape shape : Arrays.asList(new Circle(2), new Square(3), new Rectangle(2, 5))) {
    Number value = matcher.match(shape)
            .with(Circle.class)
                .get(c -> Math.PI * c.r * c.r)
            .with(Square.class)
                .get(s -> s.a * s.a)
            .with(Rectangle.class).where(r -> r.b > r.a)
                .get(r -> r.a * r.b)
            .value();
    assertNotNull(value);
    System.out.println(value);
}
```
having following class hierarchy:
```
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
```