package jug.lodz.workshop.javafp.streams.answers;


import jug.lodz.workshop.Printer;

import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by pawel on 07.06.16.
 */
public class StreamsPart1IntroAnswer implements Printer {

    private static StreamsPart1IntroAnswer lab=new StreamsPart1IntroAnswer();

    public static void main(String[] args) {
        lab.demo();
    }




    private void demo() {
        println("STREAM CREATION : FROM COLLECTION");
        println("  * EXAMPLE1 foreach on  pure list");
        Arrays.asList(1,2,3,4,5).forEach(e->print(e+","));

        //transformation and termination
        println("\n\n  * EXAMPLE2 stream with mapping");
        Arrays.asList(1,2,3,4,5).stream()
                .map(e->e+1)
                .forEach(e->print(e+","));

        println("\n\n  * EXAMPLE3 : stream with collector - joinig ");
        String result = Arrays.asList(1, 2, 3, 4, 5).stream()
                .map(e -> e + 1)
                .map(e -> e.toString())
                .collect(Collectors.joining(","));

        print(result);

        println("\n\n  * EXAMPLE4 : mapping stream content with external functions");

        Function<String, Integer> parseInt = Integer::parseInt;
        Function<Integer,Integer> square=i->i*i;

        Stream<String> step1 = Arrays.asList("1", "2", "3", "4", "5").stream();
        Stream<Integer> step2 = step1.map(parseInt);
        Stream<Integer> step3 = step2.map(square);

        step3.map(e->e+" ").forEach(this::print);

        println("\n\n  * EXAMPLE4b : something very powerful");

        Function<String, String> composed = parseInt.andThen(square).andThen(e->e+" ");

        Stream<String> step1b = Arrays.asList("1", "2", "3", "4", "5").stream(); //why do we need to define stream1 once again?
        step1b.map(composed).forEach(this::print); // why it is the same


        //termination
        println("\n\n  * EXAMPLE5 : Reduce");
        Integer sum1 = Arrays.asList(1, 2, 3, 4, 5).stream().reduce(0, (e1, e2) -> e1 + e2);
        Integer sum2 = Arrays.asList(1, 2, 3, 4, 5).stream().reduce(0, Integer::sum);

        println(" sum1 : "+sum1);
        println(" sum2 : "+sum2);

        println("\n\n  * EXAMPLE6 : Reduce step by step");

        Arrays.asList(1,2,3,4,5).stream().reduce(0, (acc,elem)->{
            println("acc : "+acc+", elem : "+elem);
            return acc+elem;
        });


    }



}
