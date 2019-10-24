package jasongarrett.primes

import spock.lang.Specification

class PrimeNumberGeneratorTest extends Specification {

	def "the first few primes"() {
		setup:
			PrimeNumberGenerator generator = new JavaPrimeNumberGenerator()
			List<Integer> result

		when: "just 2"
			result = generator.generate(2, 2)
		then:
			result == [2]

		when: "1 through 10"
			result = generator.generate(1, 10)
		then:
			result == [2, 3, 5, 7]
	}

	def "order of inputs"() {
		setup:
			PrimeNumberGenerator generator = new JavaPrimeNumberGenerator()
			List<Integer> result

		when: "endValue is smaller than startValue"
			result = generator.generate(10, 1)
		then: "result is the correct primes in the correct order"
			result == [2, 3, 5, 7]
	}

	def "range does not start at 2"() {
		setup:
			PrimeNumberGenerator generator = new JavaPrimeNumberGenerator()
			List<Integer> result

		when: "startValue is greater than 2"
			result = generator.generate(4, 8)
		then: "returned primes are in the specified range"
			result == [5, 7]
	}

	def "first 26 primes"() {
		setup:
			PrimeNumberGenerator generator = new JavaPrimeNumberGenerator()
			List<Integer> result

		when:
			result = generator.generate(1, 101)
		then: "the first 26 primes are returned"
			result == [2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101]
	}

	def "the required test range"() {
		setup:
			PrimeNumberGenerator generator = new JavaPrimeNumberGenerator()
			List<Integer> result

		when: "the required test range is specified"
			result = generator.generate(7900, 7920)
		then: "the expected primes are returned"
			result == [7901, 7907, 7919]
	}

	def "no duplicates are generated"() {
		setup:
			PrimeNumberGenerator generator = new JavaPrimeNumberGenerator()
			List<Integer> result
		when: "the same range is generated twice"
			generator.generate(10, 1)
			result = generator.generate(10, 1)
		then: "no duplicates are returned"
			result == [2, 3, 5, 7]
	}

	def "ranges that do not contain prime numbers"() {
		setup:
			PrimeNumberGenerator generator = new JavaPrimeNumberGenerator()
			List<Integer> result

		when: "the entire range is less than 2"
			result = generator.generate(-10, -3)
		then: "an empty list is returned"
			result == []

		when: "there are no primes in the range"
			result = generator.generate(32, 36)
		then: "an empty list is returned"
			result == []
	}

	def "isPrime works as expected within the generated range"() {
		given:
			PrimeNumberGenerator generator = new JavaPrimeNumberGenerator()
			generator.generate(1, 10)
		expect:
			!generator.isPrime(1)
			generator.isPrime(2)
			generator.isPrime(3)
			!generator.isPrime(4)
			generator.isPrime(5)
			!generator.isPrime(6)
			generator.isPrime(7)
			!generator.isPrime(8)
			!generator.isPrime(9)
			!generator.isPrime(10)
	}

	def "main"() {
		when:
			JavaPrimeNumberGenerator.main("1", "2")
		then: "no exception"
			notThrown(Exception)
	}

	def "run"() {
		setup:
			JavaPrimeNumberGenerator generator = Spy(new JavaPrimeNumberGenerator())

		when: "no args"
			generator.run()
		then: "generate() is not called"
			0 * generator.generate(_, _)

		when: "one arg"
			generator.run(["1"] as String[])
		then: "generate() is not called"
			0 * generator.generate(_, _)

		when: "two int string args"
			generator.run(["1", "10"] as String[])
		then: "generate is called"
			1 * generator.generate(1, 10)

		when: "more than two args"
			generator.run(["1", "2", "3"] as String[])
		then: "generate is not called"
			0 * generator.generate(_, _)

		when: "first arg cannot be parsed as an int"
			generator.run(["foo", "10"] as String[])
		then: "generate is not called"
			0 * generator.generate(_, _)

		when: "second arg cannot be parsed as an int"
			generator.run(["1", "foo"] as String[])
		then: "generate is not called"
			0 * generator.generate(_, _)
	}
}
