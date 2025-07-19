with Gestion_Dialogue,General,Argent;
use Gestion_Dialogue,General,Argent;

package body Strategie is

------------------------------------------------------------------------------

-- ***************************************************************************
-- * Le joueur simule decide ici s'il achete ou pas le terrain sur lequel    *
-- * il se trouve.                                                           *
-- *                                                                         *
-- * ENTREE : un pointeur sur le joueur simule.                              *
-- * SORTIE : la decision.                                                   *
-- ***************************************************************************

function Decision_Achat_Propriete (pointeur_joueur_simule : in A_T_Joueur_Simule)
                                return boolean is
valeur_strategique_prop    : T_Probabilite;
statut_joueur              : T_Statut_Joueur;
decision                   : boolean := false;
prix_propriete             : T_Monnaie;
depense_la_plus_probable   : T_Monnaie;
depense_la_plus_elevee     : T_Monnaie;
probabilite                : T_Probabilite;
pos                        : T_Proprietes;

begin
Info_Joueur(pointeur_joueur_simule.all.joueur,statut_joueur);
declare
statut_propriete : T_Statut_Propriete(T_Proprietes(statut_joueur.position));
begin
Info_Propriete (statut_propriete);
prix_propriete := statut_propriete.prix;

-- Si le joueur a assez d'argent pour acheter la propriete,
-- il evalue les risques.
if statut_joueur.compte > prix_propriete
then pos := T_Proprietes (statut_joueur.position);
     valeur_strategique_prop
        := pointeur_joueur_simule.caracteristiques.valeur_terrains(pos);
     Risque (pointeur_joueur_simule.joueur,
        depense_la_plus_probable,depense_la_plus_elevee,probabilite);

     -- Le joueur suppose qu'il a achete le terrain.
     -- Si le joueur risque de faire faillite au tour suivant,
     if depense_la_plus_elevee > (statut_joueur.compte - prix_propriete)

    -- si la probabilite de faire faillite plus faible
    -- que le risque maximum tolere par le joueur,
    then if probabilite
                < pointeur_joueur_simule.caracteristiques.risque_max

         --  ou si le terrain vaut la peine de prendre le risque,
         or probabilite < valeur_strategique_prop

         -- alors le joueur achete.
         then decision := true;
         end if;

        -- Sinon, il l'achete si elle l'interesse.
        else if valeur_strategique_prop > 0.5
             then decision := true;
             end if;
    end if;
end if;                                            
end;

U_D (statut_joueur.liste_proprietes);

return decision;
end Decision_Achat_Propriete;


------------------------------------------------------------------------------


-- ***************************************************************************
-- * Cette procedure decide de la construction de maisons sur les series     *
-- * completes que possede le joueur.                                        *
-- *                                                                         *
-- * ENTREE : un pointeur sur le joueur simule.                              *
-- *          la liste des series constructibles.                            *
-- ***************************************************************************

procedure Decision_Achat_Maisons (pointeur_joueur_simule : in A_T_Joueur_Simule;
                                  liste : in T_Liste_Series) is

terrain        : T_Terrains;
statut_joueur  : T_Statut_Joueur;
nouvelle_liste : T_Liste_Series := liste;

begin
Info_Joueur(pointeur_joueur_simule.joueur,statut_joueur);
Choisir_Serie_A_Construire (pointeur_joueur_simule,nouvelle_liste);
loop
     terrain := Choisir_Terrain_A_Construire (nouvelle_liste.case_depart,
                                              nouvelle_liste.case_fin);
     declare statut_propriete : T_Statut_Propriete(terrain);
     begin
           Info_Propriete (statut_propriete);
           if statut_propriete.prix_maison < statut_joueur.compte
           then Achat_Maison(terrain);
           else exit;
           end if;
     end;
end loop;

U_D (statut_joueur.liste_proprietes);
Un_De (nouvelle_liste);

end Decision_Achat_Maisons;



------------------------------------------------------------------------------


-- ***************************************************************************
-- * Cette procedure met a jour la valeur strategique de la propriete que le *
-- * joueur vient d'acheter.                                                 *
-- *                                                                         *
-- * ENTREE : un pointeur sur le joueur simule.                              *
-- *          la propriete.                                                  *
-- ***************************************************************************

procedure Mise_A_Jour_Valeur (pointeur_joueur_simule : in A_T_Joueur_Simule;
                              propriete              : in T_Proprietes) is
case_depart,case_fin : T_Cases;
prop                 : T_Proprietes;
couleur              : T_Couleur;
nb, nb_total         : integer;
increment            : T_Probabilite;

begin

-- Determination du nombre de terrains de la meme serie deja possedes
-- par le joueur.
Determiner_Serie (propriete,couleur,case_depart,case_fin);
Nombre_Terrains_Dans_Serie (pointeur_joueur_simule.joueur,case_depart,case_fin,
                            nb,nb_total);

-- Calcul de la plus-value
increment := 0.25* T_Probabilite(nb/nb_total);

-- Boucle sur les terrains de la serie;
for prop in case_depart..case_fin
loop
-- La plus-value entraine-t'elle un depassement de la valeur maximale de 1.0 ?
if pointeur_joueur_simule.caracteristiques.valeur_terrains(prop)
   + increment <= 1.0

then pointeur_joueur_simule.caracteristiques.valeur_terrains(prop)
      := pointeur_joueur_simule.caracteristiques.valeur_terrains(prop)
         + increment;

else pointeur_joueur_simule.caracteristiques.valeur_terrains(prop) := 1.0;
end if;
end loop;

end Mise_A_Jour_Valeur;


------------------------------------------------------------------------------


-- ****************************************************************************
-- * Cette procedure tente de vendre une maison du joueur.                    *
-- *                                                                          *
-- * ENTREE : un pointeur sur le joueur simule.                               *
-- * SORTIE : le resultat de la transaction.                                  *
-- ****************************************************************************

procedure Vendre_Maison (pointeur_joueur_simule : in A_T_Joueur_Simule;
                         maison_vendue          : out boolean) is

i               : integer;
valeur          : T_Probabilite;
prop_min        : T_Proprietes;
valeur_min      : T_Probabilite := 1.0;
statut_joueur   : T_Statut_Joueur;
resultat        : boolean := false;

begin
Info_Joueur(pointeur_joueur_simule.joueur,statut_joueur);

-- Boucle sur les proprietes possedees par le joueur.
for i in statut_joueur.liste_proprietes'range
loop
     -- Seuls les terrains sont susceptibles de contenir des maisons.
     if statut_joueur.liste_proprietes(i) in T_Terrains
     then declare
     statut_propriete : T_Statut_Propriete (statut_joueur.liste_proprietes(i));
     begin
             -- S'il y a des maisons sur le terrain,
             if statut_propriete.nb_maisons /= 0

             -- determiner sa valeur strategique.
             then valeur := Val_Serie (pointeur_joueur_simule.joueur,
                                       statut_joueur.liste_proprietes(i));

                  -- si sa valeur est inferieure a celle des autres terrains,
                  if valeur < valeur_min

                  -- une de ses maisons sera vendue.
                  then valeur_min := valeur;
                       prop_min   := statut_joueur.liste_proprietes(i);
                       resultat   := true;
                  end if;
             end if;
      end;
      end if;
end loop;

-- Si l'on a trouve une maison a vendre,
if resultat

-- on la vend.
then Vente_Maison (T_Terrains(prop_min));
     maison_vendue := true;
end if;

U_D (statut_joueur.liste_proprietes);

end Vendre_Maison;


-------------------------------------------------------------------------------


-- ****************************************************************************
-- * Cette procedure tente d'hypothequer une propriete du joueur.             *
-- *                                                                          *
-- * ENTREE : un pointeur sur le joueur simule.                               *
-- * SORTIE : le resultat de la transaction.                                  *
-- ****************************************************************************

procedure Hypothequer_Propriete (pointeur_joueur_simule : in A_T_Joueur_Simule;
                                 terrain_hypotheque     : out boolean) is
i               : integer;
valeur          : T_Probabilite;
prop_min        : T_Proprietes;
valeur_min      : T_Probabilite := 1.0;
statut_joueur   : T_Statut_Joueur;
resultat        : boolean := false;

begin
Info_Joueur(pointeur_joueur_simule.joueur,statut_joueur);

-- Boucle sur les proprietes possedees par le joueur.
for i in statut_joueur.liste_proprietes'range
loop
     declare
     statut_propriete : T_Statut_Propriete (statut_joueur.liste_proprietes(i));
     begin
           -- Si la propriete n'est pas deja hypothequee,
           if not statut_propriete.hypotheque

           -- determiner sa valeur.
           then valeur := Val_Serie (pointeur_joueur_simule.joueur,
                                     statut_joueur.liste_proprietes(i));

                -- si sa valeur est inferieure a celle des autres terrains,
                if valeur < valeur_min

                -- elle sera hypothequee.
                then valeur_min := valeur;
                     prop_min   := statut_joueur.liste_proprietes(i);
                     resultat   := true;
                end if;
           end if;
     end;
end loop;

-- Si l'on a trouve une propriete a hypothequer,
if resultat

-- on l'hypotheque.
then Hypotheque (prop_min);
     terrain_hypotheque := true;
end if;

U_D (statut_joueur.liste_proprietes);

end Hypothequer_Propriete;


-------------------------------------------------------------------------------


-- ****************************************************************************
-- * Cette procedure tente de deshypothequer une propriete du joueur.          *
-- *                                                                          *
-- * ENTREE : un pointeur sur le joueur simule.                               *
-- ****************************************************************************


Procedure Deshypothequer_Propriete (pointeur_joueur_simule :
                                                  in A_T_Joueur_Simule) is

i               : integer;
valeur          : T_Probabilite;
prop_max        : T_Proprietes;
valeur_max      : T_Probabilite := 0.0;
statut_joueur   : T_Statut_Joueur;

begin
Info_Joueur(pointeur_joueur_simule.joueur,statut_joueur);

-- Boucle sur les proprietes possedees par le joueur.
for i in statut_joueur.liste_proprietes'range
loop
     declare
     statut_propriete : T_Statut_Propriete (statut_joueur.liste_proprietes(i));
     begin
           -- Si la propriete est hypothequee,
           if statut_propriete.hypotheque

           -- determiner sa valeur.
           then valeur := Val_Serie (pointeur_joueur_simule.joueur,
                                     statut_joueur.liste_proprietes(i));

                -- si sa valeur est superieure a celle des autres terrains,
                if valeur > valeur_max

                -- et si le joueur a assez d'argent pour la deshypothequer,
                and float(statut_joueur.compte)
                    > float(1.1*(float(statut_propriete.prix/2)))

                -- elle sera deshypothequee.
                then valeur_max := valeur;
                     prop_max   := statut_joueur.liste_proprietes(i);
                end if;
           end if;
     end;
end loop;

Deshypotheque (prop_max);

U_D (statut_joueur.liste_proprietes);

end Deshypothequer_Propriete;


-------------------------------------------------------------------------------

end Strategie;
