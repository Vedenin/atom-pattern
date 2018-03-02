# Atom pattern (draft, in progress)

Atom pattern is combination of Proxy/Facade/Wrapper pattern. Main idea of Atom pattern reduce complexity 
of third party libraries in your business code.
 
### Main problem of common Java project

1. Difficult to change one third party library to other library in big project,

2. Some of third party libraries is very complicated (it have a lot of type, class and method), but 
 you need only small part of this in your project, 

3. Difficult to add logging, debugging, change methods of third party libraries and so on, 

4. High coupling between your code and third party libraries

5. You can't use Dependency injection with a third party library (if library author is not provide DI support)

## Main idea 

Do not use third party libraries directly, but add to your project some Proxy/Atoms (in separate module or package)
and call only Atom from your project. 

Common approach:

![common way](https://github.com/Vedenin/atom-pattern/blob/master/image/сommon-way.png?raw=true)
  
Atom-pattern approach:

![atom way](https://github.com/Vedenin/atom-pattern/blob/master/image/atom-way.png?raw=true)

