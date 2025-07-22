# An Overview of the Masix Distributed System

## Paper Information

**Authors:** Franck Mével and Julien Simon  
**Institution:** Laboratoire MASI, Institut Blaise Pascal, Université Paris VI  
**Location:** 4 place Jussieu, 75252 PARIS CEDEX 05, FRANCE  
**Contact:** {Franck.Mevel,Julien.Simon}@masi.ibp.fr  
**Homepage:** http://www-masi.ibp.fr/masix  
**Conference:** SIPAR Workshop on Parallel and Distributed Computing (SIPAR'95)  
**Date:** October 5, 1995  
**Location:** Biel School of Engineering, Biel-Bienne, Switzerland  
**Pages:** 83-86

## Abstract

A current trend in operating systems research is to use microkernels as a basis for new systems. Indeed, microkernels offer many advantages compared with monolithic systems, such as portability, modularity and dynamicity.

Mach has been widely used to build many Unix-like systems, like Lites and Mach-US. Other personalities have been implemented on top of Mach, like DOS or OS/2. Nevertheless, to the best of our knowledge, none of the operating systems built on top of a microkernel has really investigated the issues related to multiple personality support.

Masix is a distributed operating system based on the Mach microkernel, currently under development at the MASI Laboratory. The goal of this project is to investigate extensively the problems related to multiple personality support. We are particularly interested in providing transparent distributed mechanisms to the user processes, such as load distribution, fault tolerance or distributed files. For this purpose, we have designed a Distributed Generic Layer, made of several servers, interposed between the microkernel and the personalities.

In this paper, after a brief overview of Mach, we present the design principles of Masix and its overall structure. Then, we focus on two major services provided by the DGL, i.e. naming and communications.

**Keywords:** distributed systems, microkernel, Mach, multiple personality, naming, communications

## BibTeX Citation

```bibtex
@inproceedings{mevel1995overview,
  title={An overview of the Masix distributed system},
  author={M{\'e}vel, Franck and Simon, Julien},
  booktitle={SIPAR Workshop on Parallel and Distributed Computing (SIPAR'95)},
  year={1995},
  organization={Biel School of Engineering},
  address={Biel-Bienne, Switzerland},
  pages={83--86}
}
```

## Files

- **`biel.lj`** - Complete paper in LaTeX Job format
- **Figures:**
  - `masix2-us.fig` - Overall structure of the Masix System

## Key Contributions

1. **Comprehensive System Overview**: Complete presentation of the Masix distributed operating system architecture
2. **Design Principles**: Detailed explanation of multi-server approach and its advantages/disadvantages
3. **Naming and Protection**: Novel object-based naming system using Mach ports
4. **Distributed Generic Layer**: Architecture for providing distributed services to multiple personalities
5. **Administration and Configuration**: Server management and security mechanisms

## Technical Details

The paper presents the Masix system architecture:

- **Multi-server model** with high modularity and protection
- **Two-layer architecture**: personalities (Unix, DOS, OS/2, Win32) and Distributed Generic Layer (DGL)
- **Object-based naming** using Mach ports as object references
- **Administration and Configuration Server (ACS)** for server management
- **Generic Network Server (GNS)** for transparent distributed communications
- **Security mechanisms** including digital signatures and secret key algorithms

## System Architecture

Masix consists of:
- **Personalities**: Implement traditional OS semantics (Unix, DOS, OS/2, Win32)
- **Distributed Generic Layer (DGL)**: Provides distributed services to personalities
- **Administration and Configuration Server (ACS)**: Manages server registration and name resolution
- **Generic Network Server (GNS)**: Handles transparent distributed communications

## Design Principles

The multi-server approach offers:
- **Modularity**: Each server has its own interface and address space
- **Protection**: Minimal interactions between servers
- **Maintainability**: Smaller, focused server components
- **Dynamicity**: Easy addition of new servers

## Naming and Protection

The system uses:
- **Mach ports as object references** for high-level protection
- **Typed objects** with fixed dialog protocols
- **Send/receive rights** for access control
- **Server-based object management** with distributed information

## Distributed Generic Layer

The DGL provides:
- **Distributed services** to multiple personalities
- **Communication mechanisms** for inter-server and inter-node communication
- **Fault tolerance** through server replication
- **Load distribution** capabilities
- **Shared memory** and process management

## Related Work

This work builds upon:
- Mach microkernel foundation
- Multi-server operating system design
- Distributed systems research
- Multiple personality operating systems

## Impact

This paper provides a comprehensive overview of the Masix distributed operating system, serving as an introduction to the broader Masix research project. It establishes the foundation for understanding the system's architecture and design principles that are explored in detail in subsequent papers.

## Historical Context

This paper was presented at the SIPAR Workshop in October 1995, providing an early overview of the Masix system before the more detailed technical papers on specific aspects (communications, load distribution, etc.) were published later that year. 