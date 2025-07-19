| p g |
p:= Professeur new.
g:= Gestionnaire new.
g ecrireNom: 'gege'.
g lireNom afficher.
p lireNom afficher.
p ajouterPromotion: 'annee2' Par:g ;
  ajouterPromotion: 'annee1' Par:g ;
  ajouterPromotion: 'annee3' Par:g ;
  ajouterQcm: 'No 1' Par:g ;
  ajouterQcm: 'No 2' Par:g ;
  ecrireNom: 'boud'.
p lireNom afficher.
p supprimerPromotion: 'annee3' Par: g;
  supprimerQcm: 'No 2' Par: g.
p lireListePromotions ;
  lireListeQcm.


