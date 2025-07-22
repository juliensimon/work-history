# Pixmania (2009-2010)

[![Years](https://img.shields.io/badge/Years-2009--2010-orange.svg)](https://github.com/juliensimon/work-history)
[![Role](https://img.shields.io/badge/Role-CTO-blue.svg)](https://github.com/juliensimon/work-history)
[![Technologies](https://img.shields.io/badge/Technologies-Oracle%20RAC%2C%20Linux%2C%20SaaS%2C%20E-commerce-green.svg)](https://github.com/juliensimon/work-history)

This directory contains artifacts from **Julien Simon's** time as **CTO at Pixmania**, one of Europe's fastest-growing online retailers, where he led the transformation from reactive firefighting to proactive engineering and professionalized processes.

## 🏢 Company Overview

When I joined Pixmania in May 2009 as Chief Technology Officer, I found myself entrusted with the technology foundation of one of Europe's fastest‑growing online retailers—a business already processing over €2 billion in gross merchant value each year through more than 600 Linux servers spread across seven data centers. From the outset, my mandate was to move Pixmania from reactive firefighting to proactive engineering, professionalize our processes, and prepare our platform to serve not only Pixmania's direct customers but also our sister banners under DSG International—Currys, PC World and Dixons—through a new white‑label SaaS offering.

My first priority was to introduce rigor and cadence to our software delivery.  Drawing on the rich guidance from the Scrum community, I partnered with seasoned agile coaches to roll out Scrum across 15 cross‑functional teams encompassing 150 engineers—front‑office PHP and PL/SQL developers, QA analysts, business‑intelligence specialists, and operations staff.  Within six months, our sprint‑based planning and continuous‑integration pipelines had doubled our release frequency, materially reducing the number of emergency patches in production and instilling a culture of shared ownership and iterative improvement.

While our development velocity surged, I recognized that our infrastructure needed equal attention.  To guarantee uninterrupted service for Pixmania’s 35 million monthly visitors—and to support the portfolios of Currys and PC World—I led the design and deployment of an active‑active Oracle RAC cluster across our two primary sites at Equinix Paris and Interxion Nanterre.  By leveraging Oracle’s shared‑storage Real Application Cluster architecture, we achieved true zero‑downtime failover with sub‑second switchovers, ensuring that maintenance windows never impacted customer experiences.  We tied the cluster together with a private dark‑fiber/MPLS backbone, so data replications and transaction commits remained seamless no matter which node processed the request.

Storage scale and performance were equally critical.  We partnered with NetApp, a world leader in data storage and cloud data management, to house over one petabyte of photo content for mypix.com on high‑performance filer arrays.  NetApp's mature snapshot and replication technologies allowed us to protect critical assets without slowing our web tier.  This architecture supported our photo storage platform while Oracle underpinned Pixmania's catalog—which had grown to more than 1.4 million SKUs across consumer electronics, telecommunications and home appliances—and the bespoke storefronts we launched for Currys, PC World and Dixons under our new white‑label SaaS platform.

![Currys Storefront Launch](images/launch-currys.png)
![PC World Storefront Launch](images/launch-pc-world.png)
![Dixons Storefront Launch](images/launch-dixons.png)

Managing costs while scaling at this pace was a constant challenge.  With ownership of a €6 million annual IT budget, I instituted strict cost‑optimization measures and transparent supplier‑management practices.  Through careful contract renegotiations, vendor consolidation, and utilization audits, we drove a 20 percent reduction in operating expenses within twelve months, all without compromising SLAs or system capacity.

Quality, compliance and support standards were the last piece of the puzzle.  Recognizing the importance of predictable service for our B2B partners, I led the implementation of ISO 9001 and ITIL frameworks within our technical‑support organization.  We formalized incident‑management processes, introduced key performance metrics, and aligned escalation paths—improvements that drove a 35 percent reduction in ticket‑to‑resolution time and delivered consistently higher satisfaction scores from our reseller network.

## 🛠️ Technologies & Expertise

### Infrastructure & Architecture
- **Oracle RAC**: Active-active cluster across multiple data centers
- **Linux Servers**: 600+ servers across seven data centers
- **NetApp Storage**: Petabyte-scale storage for photo content
- **Dark Fiber/MPLS**: Private backbone for data replication

### Development & Operations
- **Scrum Methodology**: Agile development across 15 teams
- **Continuous Integration**: Automated testing and deployment
- **PHP/PL/SQL**: Front-office and database development
- **Quality Assurance**: Automated testing and validation

### Business Systems
- **E-commerce Platform**: Multi-banner SaaS offering
- **White-label Solutions**: Currys, PC World, and Dixons storefronts
- **Business Intelligence**: Analytics and reporting systems
- **ITIL Framework**: Service management and support processes

## 📊 Business Impact

### Scale & Performance
- **€2 Billion GMV**: Annual gross merchant value processed
- **35 Million Visitors**: Monthly platform traffic
- **1.4 Million SKUs**: Product catalog across multiple categories
- **Zero Downtime**: Active-active failover with sub-second switchovers

### Process Improvements
- **Doubled Release Frequency**: Sprint-based planning and CI/CD
- **20% Cost Reduction**: IT budget optimization and vendor consolidation
- **35% Faster Resolution**: Improved ticket-to-resolution times
- **Emergency Patch Reduction**: Proactive engineering approach

### Platform Expansion
- **Multi-banner Support**: Currys, PC World, and Dixons integration
- **SaaS Platform**: White-label e-commerce solutions
- **Photo Storage**: mypix.com platform with petabyte-scale storage
- **B2B Partnerships**: Reseller network and partner satisfaction

## 📄 Archive Notes

- Blog post demonstrates technical leadership and process improvement
- Images show successful platform launches for multiple brands
- CSPO certification shows commitment to agile methodologies
- Project showcases large-scale e-commerce platform transformation

## 🔗 Related Links

- **[Blog Post](./2009-03-12-les-meilleures-excuses-des-equipes-IT.html)** - Technical leadership insights
- **[Images](./images/)** - Platform launch screenshots
- **[CSPO Certificate](./Julien Simon-ScrumAlliance_CSPO_Certificate.pdf)** - Certified Scrum Product Owner
- **[Main Repository](../../README.md)** - Complete work history overview
- **[julien.org](https://julien.org)** - Julien's current work and expertise

---

*This period demonstrates Julien's ability to transform large-scale e-commerce platforms, implement agile methodologies, and deliver significant business impact through technical leadership.*
