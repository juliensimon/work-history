# Distributed Communication Services in the Masix System

## Paper Information

**Authors:** Franck Mével and Julien Simon  
**Institution:** Laboratoire MASI, Institut Blaise Pascal, Université Paris VI  
**Location:** 4 place Jussieu, 75252 PARIS CEDEX 05, FRANCE  
**Contact:** {Franck.Mevel,Julien.Simon}@masi.ibp.fr  
**Homepage:** http://www-masi.ibp.fr/masix  
**Conference:** IEEE Conference (Phoenix)

## Abstract

A current trend in operating system research consists in implementing the system in user mode with a minimal kernel. This kernel, called a microkernel, provides resource management and some elementary services, e.g. physical memory management or process management. Many operating systems, both centralized and distributed, have been built on top of a microkernel. Nevertheless, none of them has really investigated the problems related to multiple personality support, that is, running simultaneously on the same workstation applications from worlds as different as Unix, DOS, or OS/2.

Masix is a distributed multi-server operating system based on the Mach microkernel, with multiple personality support. Its main feature is a **Distributed Generic Layer** (DGL), which offers distributed services to the personalities. The distributed multi-server architecture of Masix grants it a high modularity, but also raises many issues, such as transparency, security and performance, which cannot be solved without adequate communication services.

To provide total transparency, we extend the traditional Mach communication model to a workstation network by interposing a **Generic Network Server** (GNS) between the tasks and the microkernel. We defined a global name service, based on a name resolution protocol, which allows any pair of Mach remote tasks to communicate transparently, using the local Mach IPC semantics. Our name server also provides local and remote authentication mechanisms, based on digital signatures and a secret key algorithm. To prevent eavesdropping, all remote communications are transparently encrypted by the GNS, using a public key algorithm. These security measures can be easily merged into the name service, to yield a secure distributed name resolution protocol.

Microkernel based-systems are traditionally criticized for their relatively poor performance. As far as network services are concerned, experiments show that a good performance level can be reached, provided that the distinctive features of microkernels are taken into account.

**Keywords:** distributed systems, microkernel, Mach, communication, security, transparency, performance

## BibTeX Citation

```bibtex
@inproceedings{mevel1995distributed,
  title={Distributed communication services in the Masix system},
  author={M{\'e}vel, Franck and Simon, Julien},
  booktitle={IEEE Conference Proceedings},
  year={1995},
  organization={IEEE},
  address={Phoenix, AZ, USA},
  note={Laboratoire MASI, Institut Blaise Pascal, Universit{\'e} Paris VI}
}
```

## Files

- **`phoenix.pdf`** - Complete paper in PDF format
- **`phoenix.ps`** - PostScript version
- **Figures:**
  - `a-b.png` - Architecture comparison diagram
  - `key-local.png` - Local key management
  - `key-remote.png` - Remote key management
  - `libs.png` - Library structure
  - `masix2-us.png` - Masix system structure
  - `name_resolve.png` - Name resolution protocol

## Key Contributions

1. **Generic Network Server (GNS)**: Novel communication layer extending Mach IPC to distributed environments
2. **Global Name Service**: Distributed name resolution protocol for transparent remote communication
3. **Security Framework**: Comprehensive security measures including encryption and authentication
4. **Transparency Mechanisms**: Seamless integration of local and remote communications
5. **Performance Optimization**: Techniques for achieving good performance in microkernel-based distributed systems

## Technical Details

The paper presents the Masix communication architecture:

- **Multi-server model** with high modularity
- **Two-layer architecture**: personalities and Distributed Generic Layer (DGL)
- **Generic Network Server (GNS)** for transparent distributed communications
- **Global name resolution protocol** for location transparency
- **Security mechanisms**: encryption, authentication, and digital signatures
- **Performance optimization** techniques for microkernel environments

## Communication Model

The extended Mach communication model features:

- **Location transparency** through proxy ports
- **Global naming service** with multicast name resolution
- **Transparent encryption** of all remote communications
- **Authentication mechanisms** for local and remote entities
- **Multi-protocol support** for different personality requirements

## Security Features

The security framework includes:

- **Eavesdropping prevention** through systematic message encryption
- **Message tampering protection** via cryptographic integrity checks
- **Replay attack prevention** using timestamp mechanisms
- **Masquerading prevention** through entity authentication
- **Digital signature support** for secure name resolution

## Performance Considerations

The paper addresses performance challenges:

- **Microkernel-specific optimizations** for network services
- **Efficient name resolution** with caching and multicast
- **Protocol-specific optimizations** for different personality requirements
- **Communication overhead minimization** in distributed environments

## Related Work

This work builds upon:
- Mach microkernel communication primitives
- Distributed name services
- Network security protocols
- Microkernel performance research

## Impact

This research significantly advances the state of distributed communication in microkernel-based systems, particularly in the areas of transparency, security, and performance optimization for multi-personality environments. 