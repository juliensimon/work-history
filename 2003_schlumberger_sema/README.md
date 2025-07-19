# SchlumbergerSema (2003)

This directory contains legacy work artifacts from Julien Simon's time at **SchlumbergerSema**, where he worked on Java Card reverse engineering tools and filesystem implementations.

## Contents

### 🔍 Java Card Disassembler
A comprehensive Java Card bytecode disassembler tool that can analyze and reverse engineer Java Card applets.

**Key Components:**
- **Disassembler.java** - Main disassembly engine
- **CapFile.java** - CAP file format parser
- **Bytecode.java** - Java Card bytecode definitions
- **Component.java** - CAP file component handling
- **Cappuccino.java** - Main application entry point

**Features:**
- Parses Java Card CAP (Converted Applet) files
- Disassembles Java Card bytecode to human-readable format
- Supports all Java Card instruction sets
- Handles constant pool resolution
- Provides method and class name resolution

### 💾 Java Card Filesystem
A complete filesystem implementation for Java Card applications.

**Key Components:**
- **File.java** - Base file system class
- **DedicatedFile.java** - Directory implementation
- **TransparentFile.java** - Binary file implementation
- **RecordFile.java** - Record-based file implementation
- **CyclicRecordFile.java** - Cyclic record file implementation

**Features:**
- Hierarchical file structure
- Multiple file types (transparent, record, cyclic)
- Access control mechanisms
- File sharing capabilities
- Memory-efficient implementation

### 📁 File Applet
A demonstration applet showcasing the filesystem implementation.

**Components:**
- **FileApplet.java** - Main applet implementation
- **FileAppletScript.java** - Test script for applet functionality

## Technical Highlights

### Disassembler Capabilities
- **CAP File Analysis**: Parses all CAP file components (Header, Directory, Import, Export, etc.)
- **Bytecode Disassembly**: Converts Java Card bytecode to readable assembly
- **Symbol Resolution**: Resolves method names, class names, and constant references
- **Multiple Formats**: Supports both JAR and TLV CAP file formats

### Filesystem Architecture
- **Object-Oriented Design**: Clean inheritance hierarchy for different file types
- **Memory Management**: Efficient use of Java Card's limited memory
- **Security Integration**: Built-in access control mechanisms
- **Standards Compliance**: Follows ISO 7816 file system standards

## Historical Context

This work represents early efforts in Java Card reverse engineering and filesystem development from 2003. The tools and implementations demonstrate:

- Early Java Card reverse engineering techniques
- Comprehensive filesystem design for constrained environments
- Practical applications of Java Card technology
- Development of tools for smart card analysis

## About Julien Simon

For more information about Julien Simon and his current work, visit **[julien.org](https://julien.org)**

---

*This directory contains legacy work artifacts preserved for archival and educational purposes.* 