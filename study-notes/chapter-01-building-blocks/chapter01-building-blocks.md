# Java Certification Chapter 1 - Study Notes

## Core Object-Oriented Concepts

### Object
An **object** is an instance of a class that exists in memory. It represents a real-world entity with state (data) and behavior (methods). Objects are created using the `new` keyword.

### Instance
An **instance** is a specific occurrence of a class. When you create an object, you create an instance of that class. Each instance has its own copy of instance variables but shares class methods.

### Reference
A **reference** is a variable that stores the memory address of an object, not the object itself. In Java, all objects are accessed through references. Multiple references can point to the same object.

```java
String str = new String("Hello"); // str is a reference to a String object
```

### Methods
**Methods** are functions that belong to a class and define the behavior of objects. They can access and modify object state.

### Fields
**Fields** are variables that belong to a class and represent the state/data of objects. Also called instance variables when they belong to an instance.

### Class Members
**Class members** include all components of a class: fields, methods, constructors, initializers, nested classes, and nested interfaces. They can be instance members or static members.

## Top-Level Types and Java File Rules

### Top-Level Types
- **Classes**: Blueprint for creating objects
- **Interfaces**: Contract defining methods that implementing classes must provide
- **Enums**: Special classes for defining constants
- **Annotations**: Metadata providers

### Java File Rules
1. **One public top-level type per file**
2. **Filename must match the public class name** (case-sensitive)
3. **File extension must be `.java`**
4. **Multiple non-public top-level types allowed in same file**
5. **Package declaration (if present) must be first non-comment line**
6. **Import statements come after package, before class declarations**

## Valid Main Methods

### Standard Main Method Signatures
```java
// Most common
public static void main(String[] args)

// Alternative valid forms
public static void main(String args[])
public static void main(String... args)
static public void main(String[] args)  // order doesn't matter for modifiers
```

### Main Method Rules
1. **Must be `public static void`**
2. **Method name must be exactly "main"**
3. **Must accept String array parameter**
4. **Parameter name can be anything** (args, arguments, data, etc.)
5. **Can have additional modifiers** (`final`, `synchronized`)
6. **Can throw exceptions**

### Valid String Array Representations
- `String[] args`
- `String args[]`
- `String... args` (varargs)

## Packages and Imports

### Packages
**Packages** are namespaces that organize related classes and interfaces. They prevent naming conflicts and control access.

```java
package com.company.project;  // Package declaration
```

### Package Rules
1. **Must be first non-comment line in file**
2. **Only one package declaration per file**
3. **Uses dot notation for hierarchy**
4. **Package names should be lowercase**

### Import Statements
**Import statements** allow you to use classes from other packages without fully qualified names.

```java
import java.util.List;           // Single type import
import java.util.*;              // Wildcard import
import static java.lang.Math.PI; // Static import
```

### Import Rules
1. **Come after package declaration, before class declaration**
2. **Wildcard (*) imports all classes in package**
3. **Static imports allow importing static members**
4. **java.lang package is automatically imported**

## Compilation and Execution

### Compiling Java Code in Packages
```bash
# Compile with package structure
javac -d . com/company/MyClass.java

# Compile all java files in package
javac com/company/*.java
```

### Compiling to Another Directory
```bash
javac -d /path/to/output com/company/MyClass.java
```

### Using Classpath
```bash
# Set classpath during compilation
javac -cp /path/to/classes MyClass.java

# Set classpath during execution
java -cp /path/to/classes com.company.MyClass
```

### Important javac Options
- `-d <directory>`: Specify destination for class files
- `-cp <path>`: Set classpath
- `-classpath <path>`: Alternative to -cp
- `-sourcepath <path>`: Specify source file locations
- `-verbose`: Verbose output

### Important java Options
- `-cp <path>`: Set classpath
- `-classpath <path>`: Alternative to -cp
- `-D<property>=<value>`: Set system properties
- `-verbose`: Verbose output

### Creating JAR Files
```bash
# Create JAR file
jar cf myapp.jar com/company/*.class

# Create JAR with manifest
jar cfm myapp.jar manifest.txt com/company/*.class

# Extract JAR contents
jar xf myapp.jar
```

### JAR Options
- `c`: Create archive
- `f`: Specify archive filename
- `m`: Include manifest information
- `x`: Extract files
- `t`: List archive contents
- `v`: Verbose output

## Class Element Ordering Rules

### Required Order in Class Declaration
1. **Package declaration**
2. **Import statements**
3. **Class declaration**
4. **Fields (instance and static variables)**
5. **Constructors**
6. **Methods**
7. **Nested classes/interfaces**

**Note**: Static initializers and instance initializers can appear anywhere among fields, constructors, and methods.

## Creating Objects and Constructors

### Constructors
**Constructors** are special methods used to initialize objects when they're created.

```java
public class Dog {
    String name;
    
    // Default constructor
    public Dog() {
        name = "Unknown";
    }
    
    // Parameterized constructor
    public Dog(String name) {
        this.name = name;
    }
}
```

### Constructor Rules
1. **Same name as class**
2. **No return type (not even void)**
3. **Can be overloaded**
4. **If no constructor provided, compiler creates default constructor**
5. **Default constructor has no parameters and empty body**

### Object Fields
**Object fields** (instance variables) belong to each instance of a class and store the object's state.

### Instance Initializers
**Instance initializers** are blocks of code that run when an object is created, before the constructor.

```java
public class Example {
    private int value;
    
    // Instance initializer
    {
        value = 10;
        System.out.println("Instance initializer");
    }
    
    public Example() {
        System.out.println("Constructor");
    }
}
```

### Order of Initialization
1. **Static variables and static initializers** (in order they appear)
2. **Instance variables and instance initializers** (in order they appear)
3. **Constructor**

## Primitive Data Types

### The 8 Primitive Types

| Type | Size | Min Value | Max Value | Default |
|------|------|-----------|-----------|---------|
| `byte` | 8 bits | -128 | 127 | 0 |
| `short` | 16 bits | -32,768 | 32,767 | 0 |
| `int` | 32 bits | -2,147,483,648 | 2,147,483,647 | 0 |
| `long` | 64 bits | -9,223,372,036,854,775,808 | 9,223,372,036,854,775,807 | 0L |
| `float` | 32 bits | ±1.4E-45 | ±3.4E38 | 0.0f |
| `double` | 64 bits | ±4.9E-324 | ±1.8E308 | 0.0 |
| `char` | 16 bits | 0 (\\u0000) | 65,535 (\\uffff) | \\u0000 |
| `boolean` | 1 bit | - | - | false |

### Literals and Underscores

#### Numeric Literals
```java
int decimal = 1_000_000;        // Decimal with underscores
int hex = 0xFF_FF;              // Hexadecimal
int octal = 0_777;              // Octal
int binary = 0b1010_1010;       // Binary
long longValue = 1_000L;        // Long literal
float floatValue = 3.14f;       // Float literal
double doubleValue = 3.14;      // Double literal
```

#### Underscore Rules
1. **Cannot be at beginning or end of number**
2. **Cannot be adjacent to decimal point**
3. **Cannot be adjacent to F, L, or D suffix**
4. **Cannot be adjacent to 0x, 0b, or 0 prefix**

## Reference Types

**Reference types** are any type that's not a primitive. They include:
- **Classes** (String, Object, custom classes)
- **Interfaces**
- **Arrays**
- **Enums**

Reference types store memory addresses, not actual values. Default value is `null`.

## Text Blocks

**Text blocks** (introduced in Java 15) provide a way to write multi-line strings.

```java
String textBlock = """
    This is a text block
    with multiple lines
    and preserved formatting
    """;
```

### Text Block Rules
1. **Must start with `"""`**
2. **Opening `"""` must be followed by line terminator**
3. **Closing `"""` can be on same line as content or separate line**
4. **Leading whitespace is automatically stripped**
5. **Trailing whitespace on each line is removed**

## Identifiers

**Identifiers** are names for variables, methods, classes, etc.

### 4 Rules for Identifiers
1. **Must begin with letter, $, or underscore (_)**
2. **Can contain letters, digits, $, or underscore**
3. **Cannot be Java keywords or reserved words**
4. **Case sensitive**

Valid: `myVariable`, `_temp`, `$value`, `className2`
Invalid: `2variable`, `class`, `my-var`

## Variable Declaration and Initialization

### Declaring Variables
```java
int x;                    // Single variable
int a, b, c;             // Multiple variables of same type
int d = 5, e, f = 10;    // Mixed initialization
```

### Multiple Variable Declaration Rules
1. **All variables must be same type**
2. **Can mix initialized and uninitialized**
3. **Type declared once for all variables**

### Variable Types and Initialization

#### Local Variables
- **Declared inside methods, constructors, or blocks**
- **Must be initialized before use**
- **No default values**
- **Scope limited to declaring block**

```java
public void method() {
    int localVar; // Must initialize before use
    // System.out.println(localVar); // Compilation error
}
```

#### Final Local Variables
- **Cannot be reassigned after initialization**
- **Can be initialized later (not at declaration)**
- **Must be initialized before use**

```java
final int x;  // OK - can initialize later
x = 10;       // OK - first assignment
// x = 20;    // Compilation error
```

#### Instance Variables
- **Belong to object instances**
- **Automatically initialized to default values**
- **Accessible throughout class (based on access modifiers)**

#### Class Variables (Static)
- **Belong to class, not instances**
- **Shared among all instances**
- **Automatically initialized to default values**

### var Keyword Rules
The `var` keyword provides local variable type inference.

```java
var message = "Hello";     // Inferred as String
var number = 42;          // Inferred as int
var list = new ArrayList<String>(); // Inferred type
```

#### var Rules
1. **Can only be used for local variables**
2. **Must be initialized at declaration**
3. **Cannot be used with null initializer**
4. **Cannot be used in method parameters**
5. **Cannot be used in field declarations**
6. **Cannot be used with array initializers**

## Variable Scope

**Variable scope** determines where a variable can be accessed.

### Scope Rules
1. **Local variables**: Scope is the block where declared
2. **Method parameters**: Scope is entire method
3. **Instance variables**: Scope is entire class (non-static contexts)
4. **Class variables**: Scope is entire class

```java
public class ScopeExample {
    static int classVar = 1;     // Class scope
    int instanceVar = 2;         // Instance scope
    
    public void method(int param) { // Parameter scope
        int localVar = 3;        // Local scope
        
        if (true) {
            int blockVar = 4;    // Block scope
        }
        // blockVar not accessible here
    }
}
```

## Garbage Collection and Object Destruction

### Eligibility for Garbage Collection
An object becomes eligible for garbage collection when there are **no more reachable references** to it.

### Rules for GC Eligibility
1. **Object goes out of scope**
2. **Reference is set to null**
3. **Reference is reassigned to another object**
4. **Circular references with no external references**

```java
public void gcExample() {
    String str1 = new String("Hello");  // Object created
    String str2 = str1;                 // Two references to same object
    
    str1 = null;                        // One reference removed
    str2 = null;                        // Object eligible for GC
    
    String str3 = new String("World");
    str3 = new String("Java");          // "World" object eligible for GC
}
```

### Important GC Notes
- **Garbage collection is automatic**
- **Cannot force immediate garbage collection**
- **`System.gc()` is a suggestion, not a command**
- **Objects are destroyed in any order**
- **Finalize methods are deprecated and unreliable**

---

## Key Points for Certification
- Memorize primitive type ranges and default values
- Understand reference vs primitive type behavior
- Know constructor and initialization order
- Master variable scoping rules
- Understand when objects become eligible for GC
- Practice identifying valid identifiers and main methods
- Know compilation and classpath concepts