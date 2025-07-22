# Répartition de Charge au dessus du Micro-noyau Mach : Intégration de la Migration dans le Système Masix

## Paper Information

**Authors:** Franck Mével and Julien Simon  
**Institution:** Laboratoire MASI, Institut Blaise Pascal, Université Paris VI  
**Location:** 4 place Jussieu, 75252 PARIS CEDEX 05, FRANCE  
**Contact:** Franck.Mevel@masi.ibp.fr, Julien.Simon@freenix.fr  
**Language:** French  
**Conference:** Journées de Recherche sur le Placement Dynamique et la Répartition de Charge (JRPRC)  
**Date:** May 1995  
**Location:** Université Paris VI, Paris, France  
**Pages:** 77-80

## Abstract

La distribution de charge est un problème étudié depuis longtemps. Différentes approches de placement et de migration de processus ont été implémentées, soit à l'intérieur du système d'exploitation, soit à l'extérieur. Depuis quelques années, la conception de systèmes s'oriente vers des approches à base de micro-noyau, ce qui facilite l'intégration de la migration à l'intérieur du système. Cependant, la collecte d'informations pour le calcul de la charge est très délicate. En effet, les systèmes basés sur un micro-noyau sont souvent composés de plusieurs serveurs qui ne possèdent chacun qu'une partie de ces informations.

Notre étude s'inscrit dans le cadre du système d'exploitation multi-serveurs Masix, basé sur le micro-noyau Mach, en cours de développement au laboratoire MASI. Il est conçu pour utiliser les ressources d'un réseau de stations de travail de manière optimale. Il comporte donc des extensions réparties qui permettent aux stations de mettre leurs ressources en commun (processeurs, mémoire, périphériques, fichiers).

De plus, Masix autorise l'exécution simultanée de plusieurs environnements de travail. Il est possible d'exécuter parallèlement sur une même station des applications Unix, DOS, OS/2, Win32, etc.

**Keywords:** répartition de charge, migration, micro-noyau, Mach, système multi-serveurs

## BibTeX Citation

```bibtex
@inproceedings{mevel1995repartition,
  title={R{\'e}partition de charge au dessus du micro-noyau Mach : int{\'e}gration de la migration dans le syst{\`e}me Masix},
  author={M{\'e}vel, Franck and Simon, Julien},
  booktitle={Journ{\'e}es de Recherche sur le Placement Dynamique et la R{\'e}partition de Charge (JRPRC)},
  year={1995},
  organization={Universit{\'e} Paris VI},
  address={Paris, France},
  pages={77--80},
  language={French}
}
```

## Files

- **`journees.pdf`** - Complete paper in PDF format
- **`journees.ps`** - PostScript version
- **Figures:**
  - `masix2.png` - Structure du système Masix

## Key Contributions

1. **Load Distribution in Microkernel Systems**: Novel approach to load balancing in multi-server microkernel architectures
2. **Generic Process Migration**: Introduction of the Generic Process concept for transparent migration
3. **Multi-Criteria Load Balancing**: Algorithm considering CPU time, memory usage, and communication patterns
4. **Distributed Information Collection**: Framework for gathering load information from distributed servers
5. **Residual Dependency Minimization**: Techniques to reduce dependencies after process migration

## Technical Details

The paper presents:

- **Multi-server architecture** based on Mach 3.0 microkernel
- **Two-layer system structure**: environments (personalities) and Distributed Generic Layer (SAGR)
- **Generic Process (PG) concept**: Global entities that can migrate between nodes
- **Load distribution algorithm**: Multi-criteria decision making for migration
- **Information collection framework**: Distributed gathering of system state from multiple servers

## System Architecture

Masix consists of:
- **Environments**: Implement traditional OS semantics (Unix, DOS, OS/2, Win32)
- **Distributed Generic Layer (SAGR)**: Provides distributed services to environments
- **Generic Processes**: Migratable entities with global naming
- **Local and Global Processes**: Classification based on migration capability

## Migration Mechanism

The migration system features:
- **Transparent migration** through Generic Processes
- **Global naming service** for process identification
- **Minimal residual dependencies** after migration
- **Multi-criteria load balancing** algorithm
- **Distributed information collection** from various servers

## Related Work

This work extends:
- Mach microkernel research
- Process migration techniques
- Load balancing in distributed systems
- Multi-server operating system design

## Impact

This research contributes to the understanding of load distribution in microkernel-based distributed systems, particularly addressing the challenges of information collection and process migration in multi-server architectures. 