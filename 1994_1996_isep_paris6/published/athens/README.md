# Building a Distributed Generic Layer for Multiple Personality Support on top of the Mach Microkernel

## Paper Information

**Authors:** Franck Mével and Julien Simon  
**Institution:** Laboratoire MASI, Institut Blaise Pascal, Université Paris VI  
**Location:** 4 place Jussieu, 75252 PARIS CEDEX 05, FRANCE  
**Contact:** {Franck.Mevel,Julien.Simon}@masi.ibp.fr  
**Homepage:** http://www-masi.ibp.fr/masix

## Abstract

Masix is a distributed multi-server operating system, based on the Mach microkernel, with multiple personality support. Its main feature is a **Distributed Generic Layer** (DGL), which offers distributed services to the personalities. The distributed multi-server architecture of Masix grants it a high modularity, but also raises many issues, which cannot be solved without adequate communication services. Thus, after a brief description of the structure of Masix, we study some of the features of the communication services offered by the DGL: location transparency, multi-protocol support, reliability, confidentiality and performance. Then, we describe a higher level feature of the DGL, i.e. migration support. Thanks to the definition of a new entity called **Generic Process**, our migration mechanism is transparent to the personalities, and induces minimal residual dependencies.

**Keywords:** distributed system, microkernel, Mach, personality, communication, migration

## BibTeX Citation

```bibtex
@inproceedings{mevel1995building,
  title={Building a distributed generic layer for multiple personality support on top of the Mach microkernel},
  author={M{\'e}vel, Franck and Simon, Julien},
  booktitle={Proceedings of the International Conference on Parallel and Distributed Processing Techniques and Applications (PDPTA'95)},
  year={1995},
  organization={CSREA},
  address={University of Georgia, Athens, USA},
  note={This work has been funded by the Institut Sup{\'e}rieur d'Electronique de Paris}
}
```

## Files

- **`athens.pdf`** - Complete paper in PDF format
- **`athens.ps`** - PostScript version
- **Figures:**
  - `comm2.png` - Communication model diagram
  - `generic-create.png` - Generic process creation
  - `libs.png` - Library structure
  - `masix2-us.png` - Overall Masix system structure

## Key Contributions

1. **Distributed Generic Layer (DGL)**: A novel architecture for providing distributed services to multiple operating system personalities
2. **Generic Process Concept**: A new entity that enables transparent migration with minimal residual dependencies
3. **Multi-Protocol Support**: Communication services that handle multiple network protocols simultaneously
4. **Location Transparency**: Seamless communication between local and remote processes using Mach IPC semantics
5. **Security Features**: Encryption and authentication mechanisms for distributed communications

## Technical Details

The paper presents the Masix system architecture built on Mach 3.0 microkernel, featuring:

- **Multi-server model** for high modularity
- **Two-layer architecture**: personalities (Unix, DOS, OS/2, Win32) and DGL
- **Generic Network Server (GNS)** for transparent distributed communications
- **Migration support** through Generic Processes
- **Fault tolerance** through server replication and group communication

## Related Work

The paper builds upon and extends:
- Mach microkernel foundation
- Distributed systems research
- Multi-personality operating systems
- Process migration techniques

## Impact

This work represents a significant contribution to distributed operating system design, particularly in the area of multiple personality support and transparent distributed services. The concepts introduced have influenced subsequent research in microkernel-based distributed systems. 