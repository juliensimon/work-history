with Normalisation,Unchecked_Deallocation,Types;
use Normalisation,Types;

Package General is

Procedure U_D is new unchecked_deallocation
                        (T_Liste_Contenu,T_Liste_Proprietes);

Function Val_Serie (joueur : in T_joueur; prop : in T_Proprietes)
                                                  return T_Probabilite;

Function Val_Serie (joueur : in T_Joueur; couleur : in T_couleur)
                   return T_probabilite;

Function Proprietaire (propriete : in T_Proprietes) return T_Joueur;

Procedure Determiner_Serie (propriete : in T_Proprietes;
                            couleur   : out T_Couleur;
                            case_depart,case_fin : out T_Cases);

Procedure Bornes_Serie (couleur : in T_Couleur;
                        case_depart,case_fin : out T_Cases);

Procedure Nombre_Terrains_Dans_Serie (joueur                  : in T_joueur;
                                      case_depart,case_fin    : in T_Cases;
                                      nombre_terrains         : out integer;
                                      nombre_total_terrains   : out integer);

Procedure Nombre_Terrains_Dans_Serie (joueur                  : in T_joueur;
				      couleur                 : in T_Couleur;
				      nombre_terrains         : out integer;
                                      nombre_total_terrains   : out integer);

Function Carte_Serie (joueur : in T_Joueur; couleur : in T_Couleur)
                     return T_Liste_Proprietes;

Function Carte_Serie_Manquante (joueur : in T_Joueur; couleur : in T_Couleur)
                                return T_Liste_Proprietes;

function Serie_Complete (joueur                 : in T_Joueur;
                         case_depart, case_fin  : in T_Cases) return boolean;

Function Carte_Complete ( joueur : in T_joueur;
                          prop : in T_proprietes) return boolean;

Function Complete_Serie_Joueur (prop : in t_proprietes) return T_joueur;

Function Carte_J1_Complete_Serie_J2 ( j1,j2 : in T_joueur)
                                     return T_liste_proprietes;

Function Serie_Completement_Construite (case_depart,case_fin : in T_Cases)
                                       return boolean;

end General;
