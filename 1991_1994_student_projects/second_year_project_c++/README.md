# FastFood Restaurant Simulation Project

This is a C++ project from 1992-1993 that simulates a restaurant queue system. The project was developed during engineering studies at **Institut Supérieur d'Electronique de Paris (ISEP)** and demonstrates object-oriented programming concepts with a custom class hierarchy.

## Historical Context

This project dates back to 1992-1993, during second-year engineering studies at ISEP. It represents early C++ development practices from the pre-standardization era, when:
- C++ was still evolving (before C++98 standard)
- Object-oriented programming was gaining mainstream adoption
- Academic institutions were beginning to teach C++ as a primary programming language
- DOS/Windows development environments were dominant

The code demonstrates sophisticated concepts for student work of that era, including event-driven simulation, queue theory, and custom class hierarchies.

## Project Description

The FastFood simulation models a restaurant with:
- Multiple servers
- Customer arrival events
- Queue management
- Service time simulation
- Event-driven architecture

## Building the Project

### Prerequisites
- C++ compiler (clang++ or g++)
- Make

### Build Instructions

1. **Clean build** (recommended for first build):
   ```bash
   make clean && make
   ```

2. **Incremental build**:
   ```bash
   make
   ```

3. **Run the simulation**:
   ```bash
   ./fastfood
   ```

### Build Targets

- `make all` - Build the fastfood simulation program (default)
- `make clean` - Remove build artifacts
- `make install` - Install to /usr/local/bin
- `make uninstall` - Remove from /usr/local/bin
- `make run` - Build and run the program
- `make help` - Show help message

## Project Structure

### Main Files
- `FASTFOOD.CPP` - Main program entry point
- `RESTAURA.CPP/H` - Restaurant simulation class
- `SERVEUR.CPP/H` - Server class
- `FILEATTE.CPP/H` - Queue management
- `TEMPS.CPP/H` - Time management
- `ARRIVEEC.CPP/H` - Customer arrival events
- `LIBERATI.CPP/H` - Server liberation events

### Class Hierarchy
- `Object` - Base class for all objects
- `Collection` - Base class for collections
- `IndexedCollection` - Indexed collections
- `OrderedCollection` - Ordered collections
- `SortedCollection` - Sorted collections
- `Magnitude` - Comparable objects

### Compatibility Headers
The project uses old-style C++ headers. Modern compatibility headers have been created:
- `iostream.h` - Compatibility for old iostream
- `String.h` - Compatibility for old string headers
- `new.h` - Compatibility for old new headers
- `OrderedCollection.h` - Symbolic link to ORDEREDC.H
- `Magnitude.h` - Symbolic link to MAGNITUD.H
- `IndexedCollection.h` - Symbolic link to INDEXEDC.H
- `Collection.h` - Symbolic link to COLLECTI.H

## Usage

When you run the program, it will prompt you for:
1. **Simulation duration** (in minutes)
2. **Number of servers**
3. **Average time between customer arrivals** (in minutes)

The simulation will then run and display statistics about the restaurant's performance.

## Technical Notes

- This project uses C++98 standard
- Compiler flags include `-fpermissive` for legacy code compatibility
- The project demonstrates early object-oriented design patterns
- All file names are in uppercase (DOS/Windows style)

## C++ Evolution: 30 Years of Changes

Building this 1992-1993 project on modern systems reveals significant changes in C++ over three decades:

### What Was Different in 1992-1993:
- **Headers**: Used `.h` extension (`iostream.h`, `String.h`) instead of modern headers
- **Main Function**: `void main()` was acceptable (now requires `int main()`)
- **Namespace**: No `std::` namespace usage - everything was in global scope
- **String Handling**: Used `<String.h>` instead of modern `<string>`
- **Memory Management**: Used `set_new_handler()` directly instead of `std::set_new_handler()`
- **File Naming**: DOS-style uppercase filenames were standard

### Modern Compatibility Challenges:
1. **Header Compatibility**: Created modern wrapper headers for old-style includes
2. **Compiler Standards**: Used `-std=c++98` and `-fpermissive` flags for legacy code
3. **Pointer Casting**: Fixed pointer-to-integer casting warnings
4. **Variable Scope**: Updated for-loop variable declarations to modern C++ standards
5. **Include Paths**: Resolved case-sensitivity issues between include statements and actual filenames

### What Hasn't Changed:
- **Object-Oriented Design**: The class hierarchy and inheritance patterns remain valid
- **Core Language Features**: Basic syntax, pointers, and memory management concepts
- **Problem-Solving Approach**: The simulation logic and algorithms are timeless

This project serves as a time capsule, showing both how far C++ has evolved and how well-designed code can remain functional across decades.

## Troubleshooting

If you encounter build issues:
1. Ensure you have a C++ compiler installed
2. Try `make clean && make` for a fresh build
3. Check that all compatibility headers are present

## Academic Context

This project was developed as part of the second-year engineering curriculum at ISEP (Institut Supérieur d'Electronique de Paris) in 1992-1993. It represents a significant piece of early C++ educational work that demonstrates:

- **Early OOP Adoption**: The project shows how object-oriented programming was being taught in engineering schools in the early 1990s
- **Simulation Design**: Demonstrates practical application of queue theory and event-driven programming
- **Software Engineering**: Shows structured approach to problem-solving and code organization
- **Historical Value**: Preserves coding practices and design patterns from the pre-standardization C++ era

## License

This is an educational project from 1992-1993 developed during engineering studies at ISEP. Please respect the original academic context and the author's rights. 