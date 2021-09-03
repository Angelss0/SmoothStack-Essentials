Run the assignment1.java's main function to show the output of the functions.

# Lambda and functional interfaces and streams. 
#### Q1. Basic lambdas. Make an array containing a few Strings. Sort it by: length, reverse length, alphabetically, by strings that contain "e" as the first character, etc.

A1. Look at BasicLambdas.java's `basicLambdaSorting()` function.

#### Q2. Using Java 8 features write a method that returns a comma separated string based on a given list of integers.

A2. Look at BasicLambdas.java's `returnCommaSeperatedString()` function.

#### Q3. Given a list of Strings, write a method that returns a list of all strings that start with the letter 'a' (lower case) and have exactly 3 letters.

A3. Look at BasicLambdas.java's `return3LengthAStrings()` function.

# Date-Time API
#### Q1. Which class would you use to store your birthday in years, months, days, seconds, and nanoseconds?

A1. The `Instant` class in the `java.time` package.

#### Q2. Given a random date, how would you find the date of the previous Thursday?

A2. Look at DateAPI.java's `getPreviousThursday()` function.

#### Q3. What is the difference between a ZoneId and a ZoneOffset?

A3. `ZoneID` is a class that provides a time zone identifier that can be used for converting an Instant to a LocalDateTime. `ZoneOffset` is a child class of `ZoneID` that represents the fixed offset from UTC. Unlike `ZoneID` which provides offsets for certain regions, `ZoneOffset` can set the offset to an arbritrary amount.

#### Q4. How would you convert an Instant to a ZonedDateTime? How would you convert a ZonedDateTime to an Instant?
    
A4. You can convert an `Instant` to a `ZonedDateTime` by using the `atZone(id)` method and setting the `id` value to a Zone ID or to `ZoneId.systemDefault()`. You can convert a `ZonedDateTime` to an `Instant` by using the `toInstant()` method.

#### Q5. Write an example that, for a given year, reports the length of each month within that year.

A5. Look at DateAPI.java's `getMonthLengthsFromYear` function.

#### Q6. Write an example that, for a given month of the current year, lists all of the Mondays in that month.

A6. Look at DateAPI.java's `getMondaysCountFromMonth` function.

#### Q7. Write an example that tests whether a given date occurs on Friday the 13th.

A7. Look at DateAPI.java's `isDateFridayThe13th` function.