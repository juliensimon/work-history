with Gestion_Dialogue,General;
use Gestion_Dialogue,General;

package body Construction is

----------------------------------------------------------------------------


-- *************************************************************************
-- * Cette fonction determine les series sur lequel le joueur peut         *
-- * construire et renvoie un pointeur sur une liste des series            *
-- * constructibles.                                                       *
-- *                                                                       *
-- * ENTREE : un pointeur sur le joueur simule.                            *
-- * SORTIE : un pointeur sur la liste des series.                         *
-- *************************************************************************

function Series_Constructibles (pointeur_joueur_simule : in A_T_Joueur_Simule)
                               return T_Liste_Series is
statut_joueur        : T_Statut_Joueur;
i                    : integer;
couleur              : T_Couleur;
couleur_deja_traitee : array (T_Couleur) of boolean;
case_depart,case_fin : T_Cases;
pointeur             : T_Liste_Series
                       := Tete_Liste_Series;
nouveau_pointeur     : T_Liste_Series;


begin

for couleur in cyan..noir
loop couleur_deja_traitee(couleur) := false;
end loop;

-- Les gares et les compagnies ne sont pas constructibles.
couleur_deja_traitee(gare) := true;
couleur_deja_traitee(compagnie) := true;

Info_Joueur (pointeur_joueur_simule.joueur,statut_joueur);

-- Boucle sur les proprietes que possede le joueur
for i in statut_joueur.liste_proprietes'range
loop
declare
statut_propriete : T_Statut_Propriete (statut_joueur.liste_proprietes(i));
begin
Info_Propriete (statut_propriete);

-- On ne peut pas construire sur un terrain hypotheque.
if not statut_propriete.hypotheque
then
     -- Determination de la serie a laquelle appartient la propriete
     Determiner_Serie (statut_joueur.liste_proprietes(i),
                      couleur,case_depart,case_fin);

     -- Si cette serie n'a pas encore ete traitee
     if not couleur_deja_traitee(couleur)
     -- et si le joueur possede la serie complete
     then if Serie_Complete (pointeur_joueur_simule.joueur,case_depart,case_fin)
             -- et qu'il y reste de la place
             and not Serie_Completement_Construite(case_depart,case_fin)
          -- alors on ajoute cette serie a la liste
          then nouveau_pointeur
               := new T_Serie'(case_depart,case_fin,pointeur);
               pointeur := nouveau_pointeur;
          end if;
     end if;
end if;

-- La serie a ete traitee.
couleur_deja_traitee(couleur) := true;
end;
end loop;

U_D (statut_joueur.liste_proprietes);
Un_De (nouveau_pointeur);

return pointeur;
end Series_Constructibles;


-------------------------------------------------------------------------------


-- ****************************************************************************
-- * Cette procedure determine la serie dont la valeur strategique est la     *
-- * plus elevee sur laquelle le joueur peut construire.                      *
-- *                                                                          *
-- * ENTREE : le joueur.                                                      *
-- *          le pointeur sur la liste des series constructibles.             *
-- * SORTIE : le pointeur sur la serie sur laquelle il faut construire.       *
-- ****************************************************************************

procedure Choisir_Serie_A_Construire (pointeur_joueur_simule
                                                    : in A_T_Joueur_Simule;
                                      liste         : in out T_Liste_Series) is
propriete      : T_Proprietes;
valeur_max     : T_Probabilite := 0.0;
nouvelle_liste : T_Liste_Series;

begin

-- Boucle sur les series constructibles.
while liste /= null

loop
-- Si la serie a une valeur superieure aux precedentes,
if pointeur_joueur_simule.caracteristiques.valeur_terrains(liste.case_depart)
   > valeur_max
   -- et si il y reste de la place,
   and not Serie_Completement_Construite (liste.case_depart,liste.case_fin)

-- Alors, il faut y construire.
then
valeur_max
:=pointeur_joueur_simule.caracteristiques.valeur_terrains(liste.case_depart);
nouvelle_liste := liste;
end if;

liste := liste.serie_suivante;

end loop;

end Choisir_Serie_A_Construire;


-------------------------------------------------------------------------------






-- ***************************************************************************
-- * Cette fonction determine le terrain sur lequel il faut construire.      *
-- * Il faut en effet repartir les maisons entre les terrains d'une          *
-- * meme serie.                                                             *
-- *                                                                         *
-- * ENTREE : les frontieres de la serie                                     *
-- *                   sur laquelle le joueur veut construire.               *
-- * SORTIE : le terrain sur lequel il faut construire.                      *
-- ***************************************************************************

function Choisir_Terrain_A_Construire (case_depart,case_fin : in T_Cases)
                             return T_Terrains is
terrain,terrain_min : T_Terrains;
nb,nb_min           : integer := 0;

begin
-- Boucle sur les terrains de la serie.
for terrain in case_depart..case_fin
loop
        declare statut_propriete : T_Statut_Propriete(terrain);
        begin
                Info_Propriete(statut_propriete);
                nb := statut_propriete.nb_maisons;
        end;

-- On recherche le terrain contenant le moins de maisons.
if nb < nb_min
then terrain_min := terrain;
end if;
end loop;

return terrain_min;
end Choisir_Terrain_A_Construire;

---------------------------------------------------------------------------

end Construction;
