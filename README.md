# comparandum
![Project Logo](https://github.com/SwissAS/comparandum/blob/master/misc/logo.png "Logo")
Framework to unit test renderers by visually comparing the generated output with a reference image.

## Introduction
Comparandum is a testing framework build on top of JUnit. It allows you to write unit tests for visual comparison. 
It works by comparing the output of a "renderer" with a predefined reference image.

## Motivation
When working on one of my hobby projects (a Java look and feel) I realized that it is very hard to write good unit tests 
for the rendering of the components. To prevent regression bugs I searched for a good solution to test UI rendering code 
and I found nothing on the web. Therefore I decided to write something on my own.

## The Solution
This library will provide you a framework to write unit tests for visual comparisons. It comes with the following features:

+ Execution of any kind of rendering code
+ Comparison to predefined reference images (a comparandum)
+ Executing the comparison as unit test
+ Creating of reports in HTML or XML
+ Automatically updating the reference images when needed 

## Project Status
The project is feature complete and used productively. 


## Dependencies

+ Java 1.5 but Java 1.6 or newer is recommended
+ JUnit 4 

## More documentation
For some simple usage examples look at the unit tests coming with the project. 
If you need further information you can read the JavaDoc.

Have fun,

Bernd Rosstauscher 