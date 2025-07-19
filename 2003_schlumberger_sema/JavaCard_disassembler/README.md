# Java Card Disassembler

A comprehensive Java Card bytecode disassembler tool for analyzing and reverse engineering Java Card applets.

## Overview

This tool can parse Java Card CAP (Converted Applet) files and disassemble the bytecode into human-readable format, making it possible to analyze Java Card applets without source code.

## Architecture

### 🔍 CAP File Parser
- **CapFile.java** - Main CAP file parser supporting multiple formats
- **JarCapFile.java** - JAR format CAP file handling
- **TlvCapFile.java** - TLV format CAP file handling
- **Component.java** - Base component class for CAP file parts

### 📋 Component Handlers
- **HeaderComponent.java** - CAP file header parsing
- **DirectoryComponent.java** - Directory component handling
- **ImportComponent.java** - Import component parsing
- **ExportComponent.java** - Export component parsing
- **ClassComponent.java** - Class component analysis
- **MethodComponent.java** - Method component disassembly
- **ConstantPoolComponent.java** - Constant pool resolution
- **DescriptorComponent.java** - Descriptor component handling
- **RefLocationComponent.java** - Reference location handling
- **StaticFieldComponent.java** - Static field component parsing

### 🔧 Disassembly Engine
- **Disassembler.java** - Main disassembly engine
- **Bytecode.java** - Java Card bytecode definitions and mnemonics
- **JCStack.java** - Stack simulation for disassembly
- **Util.java** - Utility functions for hex conversion and formatting

## Features

### CAP File Analysis
- **Multi-Format Support**: JAR and TLV CAP file formats
- **Component Parsing**: All CAP file components (Header, Directory, Import, Export, etc.)
- **Symbol Resolution**: Method names, class names, constant references
- **Structure Analysis**: File structure and component relationships

### Bytecode Disassembly
- **Complete Instruction Set**: All Java Card bytecode instructions
- **Control Flow**: Branch instruction analysis with target addresses
- **Method Calls**: Virtual, static, and interface method resolution
- **Object Creation**: New object and array creation analysis
- **Type Checking**: Checkcast and instanceof instruction handling

### Output Features
- **Hex Dumps**: Byte-level analysis with hex representation
- **Symbolic Names**: Human-readable method and class names
- **Stack Simulation**: Stack state tracking during disassembly
- **Branch Targets**: Branch instruction target address resolution

## Usage

### Main Application
- **Cappuccino.java** - Main application entry point
- **ExportFile.java** - Export functionality for disassembly results
- **ExportJarReader.java** - JAR file reading utilities

### Command Line Interface
The disassembler provides a command-line interface for:
- Loading CAP files
- Disassembling specific components
- Exporting results
- Batch processing multiple files

## Technical Details

### Java Card Bytecode Support
- **Load/Store Instructions**: Local variable and stack operations
- **Arithmetic Instructions**: Mathematical operations
- **Control Flow**: Conditional and unconditional branches
- **Method Invocation**: Virtual, static, and interface calls
- **Object Operations**: Creation, casting, and field access
- **Array Operations**: Array creation and manipulation

### Memory Management
- **Efficient Parsing**: Memory-conscious CAP file parsing
- **Stream Processing**: Large file handling without excessive memory usage
- **Component Isolation**: Independent component analysis

## Development Context

This disassembler represents early Java Card reverse engineering work from 2003, demonstrating:

- Deep understanding of Java Card bytecode
- CAP file format expertise
- Reverse engineering tool development
- Smart card security analysis capabilities

## About Julien Simon

For more information about Julien Simon and his current work, visit **[julien.org](https://julien.org)**

---

*This directory contains legacy work artifacts preserved for archival and educational purposes.* 