| prof p g |
prof:= Professeur new.
g:= Gestionnaire new.
p:= Promotion new .

p ecrireNom: 'annee2'.
p lireNomPromotion .

p ajouterElevePar: prof.
p ajouterElevePar: g.
(p lireListeEleves at: 1) lireNom afficher.
p supprimerElevePar: g.
(p lireListeEleves) size.






