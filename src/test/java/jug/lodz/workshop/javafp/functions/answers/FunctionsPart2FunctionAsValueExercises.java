package jug.lodz.workshop.javafp.functions.answers;

import javaslang.Tuple;
import javaslang.Tuple2;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by pawel on 21.06.16.
 */
public class FunctionsPart2FunctionAsValueExercises {

    //LEVEL1

    @Test
    public void testSpecificMap() throws Exception {
        List<String> input = Arrays.asList("word", "longer", "sh");
        Function<String,Integer> length=s->s.length();

        Collection<Integer> result = specificMap(input, length);

        assertThat(result).containsExactly(4,6,2);
    }

    private Collection<Integer> specificMap(Collection<String> c, Function<String,Integer> f){
        Collection<Integer> result=new ArrayList<>(); //keep this in exercises
        for (String element : c) {
            result.add(f.apply(element));
        }
        return result;
    }

    @Test
    public void testSpecificFilter() throws Exception {
        List<String> input = Arrays.asList("word", "longer", "sh","three");

        Collection<String> result = specificFilter(input, s -> s.length() > 4);

        assertThat(result).containsExactly("longer","three");
    }

    private Collection<String> specificFilter(Collection<String> c, Function<String,Boolean> f){
        Collection<String> result=new ArrayList<>(); //keep this in exercises
        for (String element : c) {
            if(f.apply(element))
                result.add(element);
        }
        return result;
    }


    //Funcion<Function

    //LEVEL2

    @Test
    public void testGenericMap() throws Exception {
        List<Tuple2<String, BigDecimal>> transactions = Arrays.asList(
                Tuple.of("t1", new BigDecimal("20")),
                Tuple.of("t2", new BigDecimal("30")),
                Tuple.of("t3", new BigDecimal("60"))

        );

        BigDecimal tax = new BigDecimal("0.23");

        Collection<BigDecimal> netMoney = genericMap(transactions, t -> t._2);
        Collection<BigDecimal> grossMoney = genericMap(netMoney, m -> m.add(m.multiply(tax)));

        assertThat(grossMoney).containsExactly(
                new BigDecimal("24.60"),
                new BigDecimal("36.90"),
                new BigDecimal("73.80")
        );

    }

    private <A,B> Collection<B> genericMap(Collection<A> input, Function<A,B> f){
        Collection<B> result=new ArrayList<>();
        for (A elem : input) {
            result.add(f.apply(elem));
        }

        return result;
    }


    @Test
    public void testGenericFilter() throws Exception {
        List<Tuple2<String, BigDecimal>> transactions = Arrays.asList(
                Tuple.of("t1", new BigDecimal("20")),
                Tuple.of("t2", new BigDecimal("30")),
                Tuple.of("t1", new BigDecimal("60"))

        );

        Function<Tuple2<String,BigDecimal>,Boolean> t1s=t -> t._1.equals("t1");

        Collection<Tuple2<String,BigDecimal>> result = genericFilter(transactions,t1s);

        assertThat(result).containsExactly(
                Tuple.of("t1", new BigDecimal("20")),
                Tuple.of("t1", new BigDecimal("60"))
        );

    }

    private <A> Collection<A> genericFilter(Collection<A> c, Function<A,Boolean> f){
        Collection<A> result=new ArrayList<>(); //keep this in exercises
        for (A element : c) {
            if(f.apply(element))
                result.add(element);
        }
        return result;
    }


//filter


//map

    //cwiczenie na deklaracje wlasciwego typu przy funkcji, ktora przyjmuje funkcje
    //reduce
    //map reduce
    //loan pattern

}
