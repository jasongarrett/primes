# primes
A prime number generator

## Requirements
This project requires Gradle and Java 1.8 or newer.

## Building and Running

(command line examples are for macOS)

* Clone the repository:
`git clone https://github.com/jasongarrett/primes.git`

* Navigate into the project directory:
`cd primes`

* Build using Gradle's installDist task:
`./gradlew installDist`

* Navigate to the directory with the generated scripts:
`cd build/install/primes/bin`

* Run **primes**, passing the starting value and ending value for the range of prime numbers to generate:
`./primes 7900 7920`
