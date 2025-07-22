# Services de Communication Répartis dans le Système Masix

## Paper Information

**Authors:** Franck Mével and Julien Simon  
**Institution:** Laboratoire MASI, Institut Blaise Pascal, Université Paris VI  
**Location:** 4 place Jussieu, 75252 PARIS CEDEX 05, FRANCE  
**Contact:** {Franck.Mevel,Julien.Simon}@masi.ibp.fr  
**Homepage:** http://www-masi.ibp.fr/masix  
**Language:** French  
**Conference:** Deuxièmes Journées Jeunes Chercheurs en Systèmes Répartis (JJCSR)  
**Date:** October 9-10, 1995  
**Location:** IRISA, Rennes, France

## Abstract

Depuis quelques années, la conception de systèmes d'exploitation répartis s'oriente vers des approches à base de micro-noyaux. En effet, ceux-ci offrent de nombreux avantages par rapport aux systèmes monolithiques, comme la portabilité, la modularité et la dynamicité. Cependant, les systèmes basés sur un micro-noyau posent de nouveaux problèmes, liés notamment à la décentralisation des informations. Afin de résoudre ces problèmes de manière efficace, le système d'exploitation doit disposer de services de communication répartis adaptés.

Masix est un système d'exploitation réparti basé sur le micro-noyau Mach, en cours de développement au Laboratoire MASI. Après une brève présentation des services de communication offerts par Mach, de la structure de Masix et de travaux similaires aux nôtres, nous étudions quelques-uns des besoins du système Masix en termes de communications, à savoir la transparence, la sécurité et de bonnes performances. Puis, nous détaillons les solutions que nous avons mises au point.

**Keywords:** systèmes répartis, micro-noyau, Mach, communications, sécurité, transparence, performances

## BibTeX Citations

### Full Paper
```bibtex
@inproceedings{mevel1995services,
  title={Services de communication r{\'e}partis dans le syst{\`e}me Masix},
  author={M{\'e}vel, Franck and Simon, Julien},
  booktitle={Deuxi{\`e}mes Journ{\'e}es Jeunes Chercheurs en Syst{\`e}mes R{\'e}partis (JJCSR)},
  year={1995},
  organization={IRISA},
  address={Rennes, France},
  language={French}
}
```

### Abstract
```bibtex
@inproceedings{mevel1995services-abstract,
  title={Services de communication r{\'e}partis dans le syst{\`e}me Masix},
  author={M{\'e}vel, Franck and Simon, Julien},
  booktitle={Deuxi{\`e}mes Journ{\'e}es Jeunes Chercheurs en Syst{\`e}mes R{\'e}partis (JJCSR) - Abstract},
  year={1995},
  organization={IRISA},
  address={Rennes, France},
  language={French},
  note={Abstract version}
}
```

## Files

### Full Paper
- **`full.pdf`** - Complete paper in PDF format
- **`full.ps`** - PostScript version

### Abstract
- **`abs.pdf`** - Abstract in PDF format
- **`abs.ps`** - PostScript version

### Figures
- `a-b.png` - Architecture comparison diagram
- `key-local.png` - Local key management
- `key-remote.png` - Remote key management
- `libs-fr.png` - Library structure (French version)
- `libs.png` - Library structure
- `masix2.png` - Masix system structure
- `name_resolve-fr.png` - Name resolution protocol (French version)
- `name_resolve.png` - Name resolution protocol

## Key Contributions

1. **Distributed Communication Services**: Comprehensive framework for distributed communications in microkernel-based systems
2. **Transparency Mechanisms**: Seamless integration of local and remote communications using Mach IPC semantics
3. **Security Framework**: Advanced security measures including encryption, authentication, and digital signatures
4. **Performance Optimization**: Techniques for achieving good performance in microkernel environments
5. **Multi-Protocol Support**: Handling of multiple network protocols for different personality requirements

## Technical Details

The paper presents the Masix communication architecture:

- **Multi-server model** with high modularity
- **Two-layer architecture**: environments (personalities) and Distributed Generic Layer
- **Generic Network Server (GNS)** for transparent distributed communications
- **Global name resolution protocol** for location transparency
- **Security mechanisms**: encryption, authentication, and digital signatures
- **Performance optimization** techniques for microkernel environments

## Communication Model

The extended Mach communication model features:

- **Location transparency** through proxy ports and global naming
- **Transparent encryption** of all remote communications
- **Authentication mechanisms** for local and remote entities
- **Multi-protocol support** for different personality requirements
- **Efficient name resolution** with caching and multicast

## Security Features

The security framework addresses:

- **Eavesdropping prevention** through systematic message encryption
- **Message tampering protection** via cryptographic integrity checks
- **Replay attack prevention** using timestamp mechanisms
- **Masquerading prevention** through entity authentication
- **Digital signature support** for secure name resolution

## Performance Considerations

The paper addresses performance challenges in microkernel-based distributed systems:

- **Microkernel-specific optimizations** for network services
- **Efficient name resolution** with caching and multicast
- **Protocol-specific optimizations** for different personality requirements
- **Communication overhead minimization** in distributed environments

## System Architecture

Masix consists of:
- **Environments**: Implement traditional OS semantics (Unix, DOS, OS/2, Win32)
- **Distributed Generic Layer**: Provides distributed services to environments
- **Generic Network Server (GNS)**: Handles transparent distributed communications
- **Security services**: Encryption, authentication, and access control

## Related Work

This work builds upon:
- Mach microkernel communication primitives
- Distributed name services
- Network security protocols
- Microkernel performance research
- Multi-personality operating systems

## Impact

This research significantly advances the state of distributed communication in microkernel-based systems, particularly in the areas of transparency, security, and performance optimization for multi-personality environments. The French version makes these contributions accessible to the French research community. 