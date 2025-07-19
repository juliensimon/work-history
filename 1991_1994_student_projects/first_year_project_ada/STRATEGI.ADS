with Normalisation,Types,Construction;
use Normalisation,Types,Construction;

package Strategie is

function Decision_Achat_Propriete (pointeur_joueur_simule
                                                 : in A_T_Joueur_Simule)
                                  return boolean;

procedure Decision_Achat_Maisons (pointeur_joueur_simule : in A_T_Joueur_Simule;
                                  liste : in T_Liste_Series);

procedure Mise_A_Jour_Valeur (pointeur_joueur_simule  : in A_T_Joueur_Simule;
                              propriete               : in T_Proprietes) ;

Procedure Vendre_Maison (pointeur_joueur_simule : in A_T_Joueur_Simule;
                         maison_vendue          : out boolean);

Procedure Hypothequer_Propriete (pointeur_joueur_simule : in A_T_Joueur_Simule;
                                 terrain_hypotheque     : out boolean);

Procedure Deshypothequer_Propriete (pointeur_joueur_simule
                                                  : in A_T_Joueur_Simule);

end Strategie;

