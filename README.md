[![Build Status](https://travis-ci.org/luismcl/simple-flight-search.svg?branch=master)](https://travis-ci.org/luismcl/simple-flight-search)

# Flight search

## Problem

Your goal is to implement a simple flight search.

**Given**

* airport of origin
* airport of destination
* date of departure
* number of adult, child and infant passengers

**When** you perform the search

**Then** you get the list of:

* flight code matching the route
* total flight price (for all passengers)

## Implementation considerations

We ask you to solve it using the **Java** programming language. You are only allowed to use test libraries (JUnit, TestNG, Hamcrest, ...) and nothing else.

You are free to implement any mechanism for feeding input into your solution (for example, using hard coded data within a unit test).

Remember that the **automated and self-checking tests** are mandatory. You should provide sufficient evidence that your solution is complete by, as a minimum, indicating that it works correctly against the supplied test data.

We kindly ask you to put your code on a public project in `github.com` under your account. Use Maven or Gradle as automation tool and link it to a build service likeTravis CI.  

## What we value most?

The goal is to provide us with a full understanding of your coding style and skills. We’ll pay particular attention to:

* readable and well-structured code
* design and domain modeling
* quality of tests

## Business rules and constraints

* Search only for direct flights (no stopovers)
* Assume there is always seat availability
* All flights leave every day
* There is only one currency (€)

## Pricing rules

### Days to departure date
| days prior to the departure date | % of the base price |
|----------------------------------|---------------------|
| more than 30 (i.e. >= 31)        | 80%                 |
| 30 - 16                          | 100%                |
| 15 - 3                           | 120%                |
| less that 3 (i.e. <= 2)          | 150%                |

### Passenger type
| passenger type | price                                                                                          |
|----------------|------------------------------------------------------------------------------------------------|
| adult          | full price (i.e. price resulting from the *days to departure date* rule)                       |
| child          | 33% discount of the price calculated according to the *days to departure date* rule            |
| infant         | fixed price depending on the airline. Rule *days to departure date* is not applied for infants |

## Examples

* 1 adult, 31 days to the departure date, flying AMS -> FRA

  flights:

    * TK2372, 157.6 €
    * TK2659, 198.4 €
    * LH5909, 90.4 €

* 2 adults, 1 child, 1 infant, 15 days to the departure date, flying LHR -> IST

  flights:

    * TK8891, 806 € (2 * (120% of 250) + 67% of (120% of 250) + 5)
    * LH1085, 481.19 € (2 * (120% of 148) + 67% of (120% of 148) + 7)

* 1 adult, 2 children, 2 days to the departure date, flying BCN -> MAD

  flights:

    * IB2171, 909.09 € (150% of 259 + 2 * 67% of (150% of 259))
    * LH5496, 1028.43 € (150% of 293 + 2 * 67% of (150% of 293))

* CDG -> FRA

  no flights available

## Additional data

### Airports
| IATA code | city       |
|-----------|------------|
| MAD       | Madrid     |

### Airlines with infant prices
| IATA code | name             | infant price |
|-----------|------------------|--------------|
| IB        | Iberia           | 10 €         |


### Flights

In CSV format
origin,destination,airline,base price (€)
CPH,FRA,IB2818,186