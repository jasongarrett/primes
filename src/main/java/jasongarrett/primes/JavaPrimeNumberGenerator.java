package jasongarrett.primes;

import java.util.ArrayList;
import java.util.List;

/**
 * A prime number generator.
 */
public class JavaPrimeNumberGenerator implements PrimeNumberGenerator {

    private List<Integer> primes = new ArrayList<>();

    /**
     * @return all of the prime numbers between startingValue and endingValue, inclusive
     */
    @Override
    public List<Integer> generate(int startingValue, int endingValue) {
        if(primes.isEmpty()) {
            // always start with 2; we'll calculate the rest
            primes.add(2);
        }

        // make sure our working range is from the smaller value to the larger value
        int start = Math.min(startingValue, endingValue);
        int end = Math.max(startingValue, endingValue);

        calculatePrimes(end);

        return getPrimesInRange(start, end);
    }

    /**
     * Returns true if and only if value is prime.  Requires that primes less than value have already been generated.
     */
    @Override
    public boolean isPrime(int value) {
        if(primes.contains(value)) {
            return true;
        }

        // if value is in the range of primes we've calculated but isn't one of the primes we've calculated, it isn't prime
        if(value < primes.get(primes.size() - 1)) {
            return false;
        }

        // divide value by each of the primes, up to value/2; it is prime if it isn't evenly divisible by any of them
        int primeIdx = 0;
        while(primes.get(primeIdx) <= (value / 2)) {
            if((value % primes.get(primeIdx)) == 0) {
                return false;
            }
            primeIdx++;
        }
        return true;
    }

    /**
     * calculate the primes that haven't already been calculated, up to and including max
     */
    private void calculatePrimes(int max) {
        int largestCalculatedPrime = primes.get(primes.size() - 1);
        for(int i = largestCalculatedPrime + 1; i <= max; i++) {
            if(isPrime(i)) {
                primes.add(i);
            }
        }
    }

    /**
     * find and return all of the calculated primes that are between start and end (inclusive)
     */
    private List<Integer> getPrimesInRange(int start, int end) {
        List<Integer> result = new ArrayList<>();
        for(int idx = 0; (idx < primes.size()) && (primes.get(idx) <= end); idx++) {
            if(primes.get(idx) >= start) {
                result.add(primes.get(idx));
            }
        }
        return result;
    }

    protected void run(String[] args) {
        if(args.length != 2) {
            System.out.println("Please specify a starting value and an ending value.");
            System.out.println("Example:  primes 7900 7920");
            return;
        }

        int startVal = 0;
        int endVal = 0;
        try{
            startVal = Integer.parseInt(args[0]);
            endVal = Integer.parseInt(args[1]);
        } catch(NumberFormatException ignored) {
            System.out.println("The starting value and ending value must be integers.");
            System.out.println("Example:  primes 7900 7920");
            return;
        }

        List<Integer> primes = generate(startVal, endVal);
        System.out.println("The prime numbers between " + startVal + " and " + endVal + ":  " + primes.toString());
    }

    public static void main(String[] args) {
        JavaPrimeNumberGenerator generator = new JavaPrimeNumberGenerator();
        generator.run(args);
    }
}
