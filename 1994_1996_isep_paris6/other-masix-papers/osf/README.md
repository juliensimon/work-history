# The MASIX Multi-Server Operating System

## Paper Information

**Authors:** Rémy Card, Éric Commelin, Stéphane Dayras, and Franck Mével  
**Institution:** Laboratoire MASI, Institut Blaise Pascal, Université Pierre et Marie Curie  
**Contact:** {card,commelin,dayras,mevel}@masi.ibp.fr  
**Date:** 1994  
**Conference:** OSF Workshop  
**Type:** Workshop Paper  
**Pages:** 519 lines (LaTeX source)

## Abstract

Traditional operating systems have been designed as monolithic kernels implementing the whole set of system services, leading to important maintenance and evolution problems. This paper discusses a new design methodology where the operating system is implemented as a set of servers using the services provided by a microkernel.

The Masix system is made up of three layers:
1. **Host system**: Provides services for dynamic inclusion of new servers and different user interfaces
2. **Unix environment**: Contains servers providing Unix abstractions and semantics based on optimized implementations using Mach microkernel facilities
3. **Virtual system**: Made up of servers providing distributed features, dynamically included using host system services

The resulting structure is innovative and easy to extend, serving as the basis for future distributed operating system research.

**Keywords:** Multi-server operating system, Microkernel, Mach, Distributed systems, Unix compatibility

## BibTeX Citation

```bibtex
@inproceedings{card1994masix,
  title={The MASIX Multi-Server Operating System},
  author={Card, R{\'e}my and Commelin, {\'E}ric and Dayras, St{\'e}phane and M{\'e}vel, Franck},
  booktitle={OSF Workshop},
  year={1994},
  organization={Laboratoire MASI, Institut Blaise Pascal, Université Pierre et Marie Curie},
  address={4 place Jussieu, 75252 Paris Cedex 05, France}
}
```

## Files

- **`article.pdf`** - Complete paper in PDF format
- **`article.ps`** - PostScript version
- **`article.tex`** - LaTeX source code
- **`rapport.pdf`** - Rapport version in PDF format
- **`rapport.ps`** - Rapport PostScript version
- **`rapport.tex`** - Rapport LaTeX source code
- **Figures:**
  - `article-0.png` to `article-9.png` - Article pages as images
  - `rapport-0.png` to `rapport-10.png` - Rapport pages as images

## Key Contributions

1. **Multi-Server Architecture**: Novel design methodology using cooperating servers on microkernel foundation
2. **Three-Layer System Design**: Host system, Unix environment, and virtual system architecture
3. **Dynamic System Extension**: Ability to add new servers and environments while system is running
4. **Performance Optimizations**: Techniques to minimize context switch overhead
5. **Distributed Extensions**: Network resource optimization for communicating stations

## System Architecture

### Design Principles

#### Multi-Server Approach
- **Modularity**: Each server written and tested independently
- **Maintainability**: Smaller, focused server components
- **Dynamic Extension**: Easy addition of new servers to running system
- **Protection**: Each server in separate address space

#### Microkernel Foundation
- **Mach microkernel**: Chosen for portability, availability, and research base
- **User-mode implementation**: System services implemented in user space
- **Minimal kernel**: Kernel provides only essential resource management

### Three-Layer Architecture

#### Host System
- **Dynamic service management**: Allows inclusion of new servers
- **Multiple user interfaces**: Support for different environments
- **Generic services**: Common functionality for all environments
- **Server coordination**: Management of inter-server communication

#### Unix Environment
- **Unix compatibility**: Full Unix interface and semantics
- **Optimized implementations**: New implementations using Mach facilities
- **Server-based design**: Unix services split across multiple servers
- **Binary compatibility**: Support for existing Unix applications

#### Virtual System
- **Distributed features**: Network resource optimization
- **Dynamic inclusion**: Servers added as needed
- **Transparent distribution**: Hide network complexity from applications
- **Resource sharing**: Optimal use of networked stations

## Technical Implementation

### Server Categories

#### Interface Servers
- **User process interaction**: Receive requests from user processes
- **System call handling**: Implement system call semantics
- **Client interface**: Provide user-facing services

#### Internal Servers
- **Inter-server support**: Provide features needed by other servers
- **Shared functionality**: Common services used across the system
- **Backend services**: Core system functionality

#### Generic Servers
- **Host system services**: Services needed to run multiple environments
- **Environment management**: Support for different operating system personalities
- **Dynamic loading**: Runtime server management

### Naming and Protection

#### Object-Based Design
- **Typed objects**: Each server manages specific object types
- **Fixed dialog protocols**: Standardized communication patterns
- **Port-based identification**: Mach ports used as object identifiers
- **Server responsibility**: Each server manages its own object types

#### Protection Mechanisms
- **Send rights**: Access control through Mach port rights
- **Server authorization**: Servers control access to their objects
- **Port-based security**: No send right means no access
- **Type safety**: Objects have well-defined interfaces

## System Categories

### Traditional Monolithic Systems
- **OSF/1 IK**: Uses Mach as basis for low-level layers
- **Locus, Sprite, Unix**: Traditional kernel structures
- **Characteristics**: Single kernel program in protected mode

### Single-Server Systems
- **OSF/1 MK**: Unix semantics in single task
- **Characteristics**: Simpler but less modular approach

### Multi-Server Systems
- **CMU Unix Multi Server**: Several cooperating tasks
- **Masix**: Novel multi-server approach with three-layer design
- **Characteristics**: Maximum modularity and extensibility

## Performance Optimizations

### Context Switch Reduction
- **Minimal server involvement**: Reduce number of servers per system call
- **Optimized message passing**: Efficient inter-server communication
- **System call reduction**: Fewer system calls requiring server interaction
- **Batch operations**: Group related operations

### Communication Efficiency
- **Message optimization**: Minimize message size and frequency
- **Port set usage**: Efficient handling of multiple communication channels
- **Thread scheduling**: Optimize thread allocation for communication patterns
- **Memory management**: Efficient buffer handling for messages

## Distributed Features

### Network Integration
- **Transparent distribution**: Hide network topology from applications
- **Resource optimization**: Optimal use of networked resources
- **Load distribution**: Balance load across network nodes
- **Fault tolerance**: Handle network failures gracefully

### Dynamic Extension
- **Runtime server addition**: Add new servers without system restart
- **Environment switching**: Support multiple operating system personalities
- **Protocol adaptation**: Support different network protocols
- **Service discovery**: Dynamic finding of available services

## Advantages and Trade-offs

### Advantages
- **Modularity**: Independent server development and testing
- **Maintainability**: Smaller, focused components
- **Extensibility**: Easy addition of new features
- **Protection**: Strong isolation between components
- **Evolution**: System can evolve without major redesign

### Challenges
- **Performance overhead**: Context switches and message exchanges
- **Complexity**: More complex than monolithic systems
- **Shared data**: Difficult to implement shared data structures
- **Coordination**: Complex inter-server communication patterns

## Mach Microkernel Choice

### Selection Criteria
- **Portability**: Available on numerous computer platforms
- **Availability**: Freely available from Carnegie Mellon University
- **Research base**: Foundation for several research projects
- **Maturity**: Well-established microkernel with active development

### Mach Features Utilized
- **Task management**: Process and thread support
- **Message communication**: Port-based IPC mechanisms
- **Device management**: Hardware abstraction layer
- **Memory management**: Virtual memory and physical memory management

## Historical Context

This paper represents a significant contribution to microkernel-based operating system design in the early 1990s. It demonstrates the feasibility of building complex, distributed systems using the multi-server approach and influenced subsequent research in modular operating systems and distributed computing.

## Related Work

The paper builds upon and extends:
- Mach microkernel foundation
- Multi-server operating system research
- Distributed systems design
- Unix compatibility layers
- Performance optimization in microkernel systems

## Impact

This work influenced:
- Design of modern microkernel-based systems
- Multi-server operating system architectures
- Distributed operating system research
- Unix compatibility layer development
- Performance optimization techniques for modular systems

## Technical Details

The paper includes detailed analysis of:
- System call implementation as remote procedure calls
- Server communication patterns and protocols
- Object-based naming and protection mechanisms
- Performance optimization strategies
- Distributed system integration techniques 