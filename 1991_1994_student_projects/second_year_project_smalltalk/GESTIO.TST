| g |

g:= Gestionnaire new.
g ecrireNom: 'souby'.
g lireNom afficher.

g ajouterPromotion: 'sup' Par:g ;
  ajouterPromotion: 'spe' Par:g ;
  ajouterQcm: 'No 1' Par:g ;
  ajouterQcm: 'No 2' Par:g .

g supprimerPromotion: 'sup' Par: g;
  supprimerQcm: 'No 1' Par: g.
g lireListePromotions ;
  lireListeQcm.

