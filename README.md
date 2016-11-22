#the Severely Lacking Programming Language.
In this project, I will design and implement - or partially implement - a programming language that I have named the _Severely Lacking Programming Language_, __slpl__ for short. I will implement slpl using Java.

slpl will be an imperative language with some features typically present in functional languages, such as high order functions and
lambda functions.

I would like for slpl to have the following features (subject to change):

- __static__ typing
- variables
- primitive number types, such as integer and float, supporting basic arithmetic, using __infix__ notation
- a primitive boolean type
- if-statements
- comments (single-line and multi-line)
- number comparison operators
- while-loops
- for-loops
- high order functions
- lambda functions
- a primitive character type
- objects
- a string type
- mutable state
- tuples
- lists
- closures
- modules

After completing the project, I hope to have a better understanding of the following stuffs:

- lexers
- parsers
- stack frames
- more



Resources:
[google](https://www.google.no/)

Schedule: TBA

Dypere beskrivelse av prosjektet, om nødvendig (kan evt. være lang). F.eks. hvis du vil lage ditt eget programmeringsspråk, så kan du gi eksempler og beskrive konseptet for språket. imperative

A list of sample slpl statements:

intPair: (int, int) = (1, 2);
intTriple: (int, int, int) = (1, 2, 3);

// assign to __a__ the first element of intTriple, to __b__ the second,  
// and to __c__ the third.
// note that we declare __a__ and __b__ in the second line,
// and reassign to __c__, which has already been declared and defined.
c: int = 5;
(a: int, b: int, c) = intTriple; 

// access tuple elements by their index
intTriple: (int, int, int) = (1, 2, 3);
first: int = intTriple[0], second: int = intTriple[1], third: int = intTriple[2];

return (1, 2); // return an (int, int) literal in a function with return type (int, int);

(a: int, b: int, _) = intTriple; // _ tells slpl to do nothing with the third value
return (a, b);

quadruple: (char, bool, int, string);
quadruple = ('a', true, 1, "hello");
quadruple = ('a', true, "hi", "hello"); // type error

// declare and define intList, a list of int values.
intList: int[] = [1, 2, 3];

a: int = intList[2];







