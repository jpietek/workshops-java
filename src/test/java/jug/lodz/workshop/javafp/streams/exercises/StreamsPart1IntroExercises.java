package jug.lodz.workshop.javafp.streams.exercises;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by pawel on 07.06.16.
 */
public class StreamsPart1IntroExercises {


    // Level 1

    /**
     *  1) map stream to add one to each element
     */
    @Test
    public void simpleMapping_JustAddOneToCreatedStream() throws Exception {
        List<Integer> start = Arrays.asList(1, 2, 3, 4, 5);

        Stream<Integer> result = start.stream().map(i -> i + 1);

        assertThat(result.collect(Collectors.toList())).containsExactly(2,3,4,5,6);
    }


    /**
     * 1) complete function getName so it returns product name
     */
    @Test
    public void singleFunctionObjectMapping_extractName() throws Exception {
        Function<Product,String> getName= p -> p.name;

        Stream<String> names = products().map(getName);

        assertThat(names.collect(Collectors.toList())).containsExactly("tv","console","mouse","speakers");
    }


    /**
     *  1) extract price which is String
     *  2) convert price to BigDecimal
     */
    @Test
    public void multipleFunctionsMapping_extractPriceAndConvertToBigDecimal() throws Exception {
        Function<Product,String> getPrice= p -> p.price;

        Function<String,BigDecimal> toBigDecimal= BigDecimal::new;

        Stream<BigDecimal> prices =
                products().map(getPrice).map(toBigDecimal);

        assertThat(prices.collect(Collectors.toList())).containsExactly(
                new BigDecimal("300.0"),new BigDecimal("200.0"),new BigDecimal("20.0"),new BigDecimal("45.5")
        );
    }

    //LEVEL 2
    /**
     *  1) extract price from Product  - > String
     *  2) convert price to BigDecimal
     *  3) sum all prices
     */
    @Test
    public void sumAllPrices_extractPricesChangeToBigDecimalAndReduce() throws Exception {
        Function<Product,String> getPrice= p -> p.price;
        Function<String,BigDecimal> toBigDecimal= BigDecimal::new;

        Function<Product, BigDecimal> bigDecimalPrice = getPrice.andThen(toBigDecimal);

        BigDecimal result = products().map(bigDecimalPrice).reduce(BigDecimal.ZERO, BigDecimal::add);

        assertThat(result).isEqualTo(new BigDecimal("565.5"));
    }

    /**
     * 1) write each name to mutable list "names"
     */
    @Test
    public void rewriteWithForeach() throws Exception {
        List<String> names=new LinkedList<>();

        products().forEach(p -> names.add(p.name)); // write each name to names list

        assertThat(names).containsExactly("tv","console","mouse","speakers");
    }


    /**
     *  1) map every element to one
     *  2) reduce by adding all values
     */
    @Test
    public void countNumberOfElementsWithMapReduce() throws Exception {
        Integer numberOfProducts = products()
                .<Integer>map(p -> 1)
                .reduce(0,Integer::sum);

        assertThat(numberOfProducts).isEqualTo(4);

    }


    // Level 3

    /**
     *  don't change test
     *  complete basicMapReduce method which is listed below
     */
    @Test
    public void mapReduceTest() throws Exception {
        List<String> input = Arrays.asList("1", "2", "3","4");
        Function<String,Integer> toInt = Integer::parseInt;
        BinaryOperator<Integer> multiply=(i1,i2)->i1*i2;

        Integer result = basicMapReduce(toInt, multiply, 1, input.stream());

        assertThat(result).isEqualTo(24);

    }

    //EXERCISE
    //Use 'map' on Stream and then 'reduce'
    private <A,B> B basicMapReduce(Function<A,B> map, BinaryOperator<B> reduce, B identity, Stream<A> input){
        return null;
    }

    /**
     * don't change test
     *  write map in terms of "forEach" - complete "mapInTermsOfForEach"
     */
    @Test
    public void  mapInTermsOfForEach() throws Exception {
        Collection<String> result = mapInTermsOfForEach(products(), p -> p.name);

        assertThat(result).containsExactly("tv","console","mouse","speakers");
    }

    // EXERCISE
    // you need to add mapped elements to result collection
    private <A,B> Collection<B>  mapInTermsOfForEach(Stream<A> input,Function<A,B> f){
        Collection<B> result=new LinkedList<>();

        input.forEach(i -> result.add(f.apply(i)));

        return result;
    }

    /**
     *  don't change test
     *  write map in terms of "reduce" - complete "mapInTermsOfReduce"
     */
    @Test
    public void mapsInTermsOfReduce() throws Exception {
        Collection<String> result = mapInTermsOfReduce(products(), p -> p.name);

        assertThat(result).containsExactly("tv","console","mouse","speakers");

    }

    // EXERCISE
    // complete two functions which are used in stream reduce operation
    private <A,B> Collection<B>  mapInTermsOfReduce(Stream<A> input,Function<A,B> f){
        List<B> identity=new LinkedList<>();
        BiFunction<List<B>,A,List<B>> accumulate=(l,e)-> {
            l.add(f.apply(e));
            return l;
        };

        BinaryOperator<List<B>> combine=(l1,l2)-> {
            l2.addAll(l1);
            return l2;
        };


        return input.reduce(identity,accumulate,combine);
    }



    //Products
    private Stream<Product> products(){
        Product tv=new Product("tv","300.0");
        Product console=new Product("console","200.0");
        Product mouse=new Product("mouse","20.0");
        Product speakers=new Product("speakers","45.5");

        return Arrays.asList(tv,console,mouse,speakers).stream();
    }

    class Product{
        final String name;
        final String price;

        public Product(String name, String price) {
            this.name = name;
            this.price = price;
        }
    }
}
