Absolutely! I’ve updated the **Infrastructure section** to highlight your **CCNA certification** as evidence of your hands-on networking skills, and kept the ScrumMaster and CCNA mention in the Scaling section as well. Here is the full revised narrative:

---

# Building a Global Digital Music Distribution Platform

**Julien Simon — VP Engineering, Digiplug (2007–2009)**

When I joined **Digiplug** in early 2007 as **Vice President of Engineering**, the company—recently acquired by Accenture—was already a pioneer in digital supply chain services for the media industry. My mission was clear: to scale both the **technical infrastructure** and the **engineering team** behind our SaaS platform, which powered the distribution of music and video content for the world’s biggest record labels: **Universal Music Group, Sony Music**, and **Warner Music Group**.

At the time, the music industry was undergoing a massive transformation—moving from physical CD distribution to digital channels. Digiplug sat at the center of this shift, enabling content ingestion, media transformation, metadata management, and multi-platform delivery for the majors. To support that growth, we had to completely rebuild our infrastructure and organizational processes.

---

## Designing the Infrastructure at Scale

One of my first projects was the **design and deployment of a €2 million, dual-site infrastructure** spanning two **TelecityGroup** data centers in Europe. These facilities offered the physical security, connectivity, and redundancy required by clients with strict availability and compliance needs.

The platform we built included:

* Approximately **150 servers** (Dell rack-mounted systems),
* High-throughput **Cisco** networking gear, managed and optimized with hands-on knowledge gained through my **CCNA certification** earned in July 2008,
* Distributed, scale-out **Isilon NAS** storage for large media files,
* A mission-critical **Oracle 10g RAC** cluster for metadata and transactional services.

The goal was **>99.9% service availability**, or less than 9 hours of downtime per year. We implemented a **fully documented Disaster Recovery Plan (DRP)** with regular failover exercises between the two sites. If one data center went dark, traffic would reroute within minutes. We also put in place automated monitoring, alerting, and SLA dashboards.

---

## Securing the Platform: ISO 27001 Certification

Our platform hosted **sensitive and often pre-release content**, making security a top concern for clients like Universal Music. In 2008, I led the design and implementation of an **Information Security Management System (ISMS)** based on **ISO/IEC 27001**, the international standard for information security.

We addressed:

* Secure authentication and access policies,
* End-to-end encryption,
* Incident response protocols,
* Vendor and data center compliance (Telecity also operated under ISO 27001 standards at the facility level).

We successfully passed external audits and obtained **ISO 27001 certification**, one of the first in our industry for a SaaS media delivery platform. This was a competitive differentiator and a requirement for several client contracts.

---

Thanks for flagging that! Let me revise that paragraph to make it clearer and more natural:

---

## Growing the Engineering Team and Professional Development

When I joined, the engineering team was a tight-knit group of just 12 people. Over the next two years, I expanded the team to more than 50 engineers across multiple disciplines—including backend development, operations, media processing, quality assurance, and business intelligence.

To support this rapid growth and improve our delivery process, I pursued key professional certifications in **July 2008**:

* I became a **[Certified ScrumMaster (PDF)](Julien%20Simon-ScrumAlliance_CSM_Certificate.pdf)** after training with **Jeff Sutherland**, one of the founders of Scrum, which helped me embed Agile principles across the teams and improve collaboration, transparency, and delivery speed.
* I also earned the **[Cisco Certified Network Associate (CCNA) certification (PDF)](CCNA_certificate.pdf)**, which reinforced my hands-on expertise with Cisco networking gear—vital for managing our complex data center infrastructure.

With this foundation, I introduced Agile frameworks like Scrum, continuous integration pipelines, and a culture of peer reviews and shared ownership, enabling us to maintain high-quality delivery as the team and platform scaled.

---

## Delivering Music and Video to the World

At the core of our platform was a **multi-tenant SaaS application** that managed the entire **digital supply chain** for our music label partners. We ingested assets (audio, video, artwork, metadata), transcoded them into various formats, and distributed them to over 100 partner endpoints—online retailers, streaming platforms, and mobile operators.

In 2008, **Accenture announced a global digital supply chain deal with Universal Music Group**, with Digiplug at the center:

> “Digiplug will collate and store Universal Music Group’s audio and video content, artwork and metadata to distribute to the music company’s mobile and digital business partners.”

We managed **tens of thousands of media assets** each week, across multiple languages, formats, and legal jurisdictions. Clients accessed our portal and APIs to:

* Upload and monitor content,
* Review metadata,
* Schedule distributions,
* Analyze deliveries and downloads in real time.

Our platform delivered content to **hundreds of mobile and digital retailers worldwide**, including major platforms like **iTunes**, and mobile operators across Europe, Asia, and the Americas.

---

## Technologies We Used

Here’s a snapshot of the technical environment:

* **Java EE**, Spring, Hibernate, Tomcat,
* Oracle 10g RAC,
* Linux (CentOS, Debian),
* Isilon NAS for media storage,
* Cisco networking equipment,
* SVN/Git version control,
* Jenkins CI/CD,
* Nagios and Centreon monitoring tools.

---

# References

1. Uptime Institute — Availability and Uptime Metrics: uptimeinstitute.com
2. ISO/IEC 27001 Information Security Standard: iso.org/isoiec-27001-information-security.html
3. Dell EMC Isilon Scale-out NAS Storage: dell.com/en-us/dt/storage/isilon/index.htm
4. TelecityGroup Data Center Standards (archived): web.archive.org/web/20080418005646/[http://www.telecitygroup.com/services/standards.htm](http://www.telecitygroup.com/services/standards.htm)
5. Scrum Alliance — Certified ScrumMaster Overview: scrumalliance.org/community/profile/jsutherland
6. Cisco Certified Network Associate (CCNA) Overview: cisco.com/c/en/us/training-events/training-certifications/certifications/associate/ccna.html
7. Accenture Press Release, June 2008 — Accenture and Universal Music Group digital supply chain deal: newsroom.accenture.com/news/accenture-and-universal-music-group-sign-digital-supply-chain-agreement.htm
8. Accenture Acquisition of Digiplug, 2008: newsroom.accenture.com/news/accenture-to-acquire-digiplug-expanding-digital-media-capabilities.htm
9. Accenture Digital Media Services Overview: accenture.com/us-en/services/communications-media/digital-media-services
