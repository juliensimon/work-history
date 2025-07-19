# SchlumbergerSema (2003)

This directory contains legacy work artifacts from Julien Simon's time at **SchlumbergerSema**, where he worked on Java Card reverse engineering tools.

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

## Technical Highlights

### Disassembler Capabilities
- **CAP File Analysis**: Parses all CAP file components (Header, Directory, Import, Export, etc.)
- **Bytecode Disassembly**: Converts Java Card bytecode to readable assembly
- **Symbol Resolution**: Resolves method names, class names, and constant references
- **Multiple Formats**: Supports both JAR and TLV CAP file formats

## Historical Context

This work represents early efforts in Java Card reverse engineering from 2003. The tools demonstrate:

- Early Java Card reverse engineering techniques
- Practical applications of Java Card technology
- Development of tools for smart card analysis
- Comprehensive understanding of Java Card bytecode

## About Julien Simon

For more information about Julien Simon and his current work, visit **[julien.org](https://julien.org)**

---

*This directory contains legacy work artifacts preserved for archival and educational purposes.* 