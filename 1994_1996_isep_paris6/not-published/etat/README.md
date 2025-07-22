# État de l'art des serveurs Unix au dessus de Mach

## Paper Information

**Authors:** Rémy Card, Franck Mével, and Julien Simon  
**Institution:** Laboratoire MASI, Institut Blaise Pascal, Université Paris VI  
**Contact:** card@tsx-11.mit.edu, mevel@masi.ibp.fr, simon@freenix.fr  
**Date:** March 16, 1995  
**Language:** French  
**Type:** Technical Report / State of the Art  
**Pages:** 271 lines (LaTeX source)

## Abstract

This paper presents a comprehensive overview of systems that provide Unix emulation on top of the Mach microkernel. The authors classify these systems into three main categories:

1. **Monolithic systems** using Mach as lower layers (OSF/1 IK)
2. **Single-server systems** running in user mode above Mach (BSD Single Server, CMU Unix Server, OSF/1 MK, Lites, and Sprite)
3. **Multi-server systems** composed of cooperating servers (CMU Mach-US and GNU Hurd)

The paper provides detailed analysis of each approach, examining their architectures, system call handling mechanisms, process management, and I/O management strategies. It discusses the trade-offs between different design choices and their implications for performance, security, and maintainability.

**Keywords:** microkernel, Mach, Unix emulation, operating systems, system architecture, multi-server systems

## BibTeX Citation

```bibtex
@techreport{card1995etat,
  title={État de l'art des serveurs Unix au dessus de Mach},
  author={Card, Rémy and Mével, Franck and Simon, Julien},
  year={1995},
  month={March},
  institution={Laboratoire MASI, Institut Blaise Pascal, Université Paris VI},
  address={4 place Jussieu, 75252 PARIS CEDEX 05, FRANCE},
  note={Technical Report}
}
```

## Files

- **`etat.pdf`** - Complete paper in PDF format
- **`etat.ps`** - PostScript version
- **`etat.tex`** - LaTeX source code
- **Figures:**
  - `structosf1ik.png` - OSF/1 IK structure
  - `structosf1mk.png` - OSF/1 MK structure
  - `apsyssimp.png` - Simple system call in OSF/1 MK
  - `apsyscomp.png` - Complex system call in OSF/1 MK
  - `sprite.png` - Sprite structure on Mach
  - `machus.png` - Mach-US structure
  - `etat-0.png` to `etat-11.png` - Paper pages as images

## Key Contributions

1. **Comprehensive Classification**: Systematic categorization of Unix-on-Mach systems into three distinct architectural approaches
2. **Detailed Architecture Analysis**: In-depth examination of each system's design and implementation
3. **Performance and Security Trade-offs**: Analysis of the implications of different design choices
4. **Historical Context**: Documentation of the evolution of Unix-on-Mach systems in the early 1990s
5. **Technical Implementation Details**: Specific insights into system call handling, process management, and I/O systems

## System Categories Analyzed

### Monolithic Systems
- **OSF/1 IK**: Uses Mach 2.5 with traditional Unix structure
- **Characteristics**: Minimal use of Mach features, maintains Unix semantics

### Single-Server Systems
- **OSF/1 MK**: Based on Mach 3.0, inspired by BSD Single Server
- **BSD Single Server**: Carnegie Mellon University implementation
- **CMU Unix Server**: Alternative CMU implementation
- **Lites**: Lightweight Unix implementation
- **Sprite**: Distributed Unix system ported to Mach

### Multi-Server Systems
- **Mach-US**: Object-oriented multi-server system from CMU
- **GNU Hurd**: Free software multi-server implementation

## Technical Analysis

### System Call Handling
- **Library-based emulation**: Used in OSF/1 MK for binary compatibility
- **RPC mechanisms**: For complex system calls requiring kernel services
- **Security implications**: Analysis of emulation library security issues

### Process Management
- **Task and thread mapping**: How Unix processes map to Mach tasks/threads
- **Memory management**: Virtual memory handling through vnode pagers
- **Data structure maintenance**: Server-side Unix semantics preservation

### I/O Management
- **Device driver integration**: How Unix I/O subsystems interface with Mach
- **File system support**: UFS and other file system implementations
- **Network communication**: Socket and IPC mechanisms

## Design Trade-offs

### Monolithic vs. Multi-server
- **Performance**: Monolithic systems generally faster
- **Modularity**: Multi-server systems more modular and maintainable
- **Security**: Multi-server provides better isolation
- **Complexity**: Multi-server systems more complex to implement

### Binary Compatibility
- **Library emulation**: Provides compatibility but security risks
- **Direct implementation**: Better security but requires application recompilation
- **Performance impact**: Emulation overhead vs. native performance

## Historical Significance

This paper provides a crucial snapshot of Unix-on-Mach system development in the mid-1990s, documenting the various approaches taken by different research groups and commercial organizations. It serves as a reference for understanding the evolution of microkernel-based operating systems and the challenges in providing Unix compatibility on microkernel architectures.

## Related Work

The paper references and analyzes:
- Mach microkernel foundation and evolution
- BSD Unix implementations
- OSF/1 development efforts
- CMU research projects (Mach-US, BSD Single Server)
- Berkeley Sprite distributed system
- GNU Hurd development

## Impact

This state-of-the-art report influenced subsequent research in:
- Microkernel operating system design
- Unix compatibility layers
- Multi-server system architectures
- Performance optimization in microkernel systems
- Security models for modular operating systems

## Technical Details

The paper includes detailed figures showing:
- System architectures and component relationships
- System call flow diagrams
- Process and memory management structures
- I/O subsystem organization
- Inter-process communication mechanisms 