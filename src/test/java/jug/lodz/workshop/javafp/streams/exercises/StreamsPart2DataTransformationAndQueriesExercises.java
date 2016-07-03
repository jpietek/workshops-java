package jug.lodz.workshop.javafp.streams.exercises;

import jug.lodz.workshop.javafp.streams.Transactions;
import org.junit.Test;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by pawel on 13.06.16.
 */
public class StreamsPart2DataTransformationAndQueriesExercises {


    //LEVEL1

    /**
     *  1)remove header
     *  2)parse to int
     *  3)count sum
     */
    @Test
    public void skipThenMapAndCalculateSum() throws Exception {
        Stream<String> stream = Arrays.asList("header", "7", "9", "2", "13", "5").stream();

        Integer sum = stream
//                .skip(???)
                .<Integer>map(null)
                .reduce(null, null);

        assertThat(sum).isEqualTo(36);

    }

    @Test
    /**
     * 1)remove header
     * 2)parse string
     * 3)find min and max
     * -- can you use only one stream
     */
    public void testMinAndMaksAmount() throws Exception {
        List<String> array = Arrays.asList("header", "7", "9", "2", "13", "5");
        Stream<String> stream1 = array.stream();
        Stream<String> stream2 = array.stream();

        Optional<Integer> max = stream1.skip(1).map(Integer::parseInt).max(null);
        Optional<Integer> min = null;

        
        assertThat(min.orElse(0)).isEqualTo(2);
        assertThat(max.orElse(0)).isEqualTo(13);
    }

    @Test
    /**
     * 1)remove header
     * 2)parse string
     * 3) find first even number
     */
    public void findFirstEven() throws Exception {
        List<String> array = Arrays.asList("header", "7", "9", "2", "13", "5");

        //uncomment and complete
        Optional<Integer> firstEven = null;
//                array.stream()
//                .skip(???)
//                .map(???)
//                .filter(???)
//                .find???();

        assertThat(firstEven).isPresent();
        assertThat(firstEven.get()).isEqualTo(2);
    }

    @Test
    /**
     * implement "allMatch" so it checks if all amounts in transactions are greater than zero
     */
    public void areAllTransactionsPositive() throws Exception {
        boolean allGreaterThanZero = readTransactions().allMatch(null);

        assertThat(allGreaterThanZero).isTrue();
    }

    @Test
    /**
     * 1) use filter to leave only transactions with amount = 0
     * 2) use findFirst
     */
    public void tryToFindFirstTransactionWithAmountZero() throws Exception {
        Optional<Transactions.FlatTransaction> firstTransactionWithAmountZero = readTransactions()
                .filter(null)
                .findFirst();


        assertThat(firstTransactionWithAmountZero).isEmpty();
    }

    //LEVEL2

    @Test
    /**
     * 1) use readTransactions to construct stream
     * 2) find whole transaction with biggest amount
     * 3) use Comparator.comparing
     */
    public void findTransactionWithBiggestAmount() throws Exception {
        Optional<Transactions.FlatTransaction> maxTransaction = null;
//        readTransactions()
//                .max(???);   //Comparator.comparing(???)


        assertThat(maxTransaction).isPresent();
        assertThat(maxTransaction.get().id).isEqualTo(9);

    }

    @Test
    /**
     * filter stream to leave only transactions with "accountFrom" 1
     * map transaction to id
     * collect to list
     */
    public void findAllTransactionsIdsFromAccount1() throws Exception {
        List<Integer> result = readTransactions()
                .filter(null)
                .<Integer>map(null)
                .collect(Collectors.toList());

        assertThat(result).containsExactly(1,2,7,8);
    }

    @Test
    /**
     * 1) filter by account id
     * 2) filter by date
     * 3) map to amount
     * 4) sum with reduce
     */
    public void sumAllAmountsIntransactionfromAccount2on01022016() throws Exception {
        int accountId=2;
        LocalDate searchDate = LocalDate.of(2016, 2, 1);

        Integer result = readTransactions()
                .filter(t -> t.accountFrom ==   null)
                .filter(t -> t.date.isEqual(null))
                .<Integer>map(null)
                .reduce(null, null);

        assertThat(result).isEqualTo(716);

    }

    private Stream<Transactions.FlatTransaction> readTransactions() {
        return Transactions.transactions.stream()
                .skip(1)  // KATA
                .map(line -> line.split(","))
                .filter(cels->cels.length==5) // KATA
                .filter(cels->!cels[0].equals("aaa") && !cels[1].equals("bbb")) // KATA
                .filter(cels->!cels[0].isEmpty())  // KATA
                .map(Transactions.FlatTransaction::new);
    }
    //LEVEL3


    /**
     * Implement "parseTransaction" method
     */
    @Test
    public void parseTransactionsWithFlatMap() throws Exception {
        List<Transactions.FlatTransaction> ts = readTransactionswithoutFilters().collect(Collectors.toList());

        assertThat(ts).hasSize(9);
    }

    private Stream<Transactions.FlatTransaction> readTransactionswithoutFilters() {
        return Transactions.transactions.stream()
                .skip(1)
                .map(line -> line.split(","))
                .flatMap(this::parseTransaction);
    }

    //EXERCISE
    private Stream<Transactions.FlatTransaction> parseTransaction(String[] fields){
        try{
            return null;
        }catch(ArrayIndexOutOfBoundsException | NumberFormatException e){
            return null;
        }catch(Exception e){
            throw new RuntimeException("SOMETHING UNEXPECTED!!!",e);
        }
    }


    @Test
    /**
     * Implement "customFilter"
     */
    public void testCustomFilter() throws Exception {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6);
        List<String> strings = Arrays.asList("aaa", "bbbb", "ccccc", "dddddd");

        assertThat(customFilter(numbers,n->n>3)).containsExactly(4,5,6);
        assertThat(customFilter(strings,s->s.length()>5)).containsExactly("dddddd");
    }

    private <A> Collection<A> customFilter(Collection<A> c, Predicate<A> f){
        return null;
    }

    @Test
    public void testCustomSort() throws Exception {
        List<Integer> numbers = Arrays.asList(3,2,5,6,1,4);
        List<String> strings = Arrays.asList("ccccc", "bbbb", "aaa", "dddddd");


        Comparator<String> lengthComparator=(s1,s2) -> {
          Integer s1Length=s1.length();
          Integer s2Length=s2.length();
          return Integer.compare(s1Length,s2Length);
        };

        assertThat(customSort(numbers,Integer::compare)).containsExactly(1,2,3,4,5,6);
        assertThat(customSort(strings,lengthComparator)).containsExactly("aaa","bbbb","ccccc","dddddd");

    }

    private <A> Collection<A> customSort(Collection<A> col, Comparator<A> com){
        return null;
    }
}