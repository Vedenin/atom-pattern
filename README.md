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

### Main idea 

Do not use third party libraries directly, but add to your project some Proxy/Atoms (in separate module or package)
and call only Atom from your project. 

Common approach:

![common way](https://github.com/Vedenin/atom-pattern/blob/master/image/сommon-way.png?raw=true)
  
Atom-pattern approach:

![atom way](https://github.com/Vedenin/atom-pattern/blob/master/image/atom-way.png?raw=true)

### Small example

     @Atom(Document.class)
     @Molecule({ElementAtom.class, ListAtom.class})
     @Contract("Provide information about HTML pages")
     public class DocumentAtom {
         private final Document original;

         @Contract("Should returns elements according this CSS Query")
         public ListAtom<ElementAtom> select(String cssQuery) {
             ListAtom<ElementAtom> result = ListAtom.create();
             result.addAll(original.select(cssQuery).stream().map(ElementAtom::getAtom).collect(ListAtom.getCollector()));
             return result;
         }

         // -------------- Just boilerplate code for Atom -----------------
         @BoilerPlate
         private DocumentAtom(Document original) {
             this.original = original;
         }

         @BoilerPlate
         static DocumentAtom getAtom(Document original) {
             return new DocumentAtom(original);
         }

     }


### Possible Atom types

#### 1. Atom with Exceptions proxy

![atom way](https://github.com/Vedenin/atom-pattern/blob/master/image/atom-exception-proxy.png?raw=true)

**Description:** All Exceptions (checked or unchecked) from a third party libraries classes 
catch in Atoms and throw as AtomException.

**For example:**
 
     public boolean createNewFile() {
         try {
             return file.createNewFile();
         } catch (IOException exp) {
             throw new IOAtomException(exp);
         }
     }
     
**Usage:**

1. Easy debug business logic in your application, because you can split exceptions in your own classes and exceptions 
from a third party libraries,
 
2. Reduce code to catch checked exception in your business (if you need), 

#### 3. Atoms-Facade

#### 4. Atoms with one Entry point (Molecule)

In progress 

#### 5. Atoms that proxy group of related classes (Molecule)

In progress 

#### 6. Polymorphic Atom 

In progress 

#### 7. Join Atom 

In progress 

#### 8. Repeat-Join Atom 

In progress 