with Gestion_Dialogue,General;
use Gestion_Dialogue,General;

package body Argent is

------------------------------------------------------------------------------

-- **************************************************************************
-- *  Cette procedure calcule le compte en banque et le capital immobilier  *
-- *  detenus par un joueur.                                                *
-- *                                                                        *
-- *  ENTREE : le joueur dont on souhaite calculer la fortune.              *
-- *  SORTIE : le compte en banque et le capital immobilier.                *
-- **************************************************************************

procedure Fortune (joueur         : in T_Joueur;
                   argent,capital : out T_Monnaie) is

statut_joueur                 : T_Statut_Joueur;
cash,patrimoine,prix_maisons  : T_Monnaie;
i                             : integer;

begin
patrimoine := 0;
Info_Joueur (joueur,statut_joueur);

cash := statut_joueur.compte;   -- Compte en banque du joueur.

-- On calcule maintenant le capital immobilier du joueur,
-- qui se compose de la valeur des proprietes qu'il possede
-- et des eventuelles maisons qui s'y trouvent.

for i in statut_joueur.liste_proprietes.all'range
-- boucle sur toutes les proprietes possedees par le joueur
loop

      declare
      statut_propriete : T_Statut_Propriete (statut_joueur.liste_proprietes(i));

      begin
        Info_Propriete (statut_propriete);
        -- si la propriete est hypothequee, elle vaut deux fois moins.
        if statut_propriete.hypotheque
        then patrimoine := patrimoine + statut_propriete.prix/2;
        else patrimoine := patrimoine + statut_propriete.prix;
        end if;

        -- si la propriete est un terrain, il faut prendre en compte
        -- les maisons qui pourraient s'y trouver.
        if statut_joueur.liste_proprietes(i) in T_Terrains
        then prix_maisons:= statut_propriete.Prix_Maison
                            * T_Monnaie(statut_propriete.Nb_Maisons);
        end if;
        patrimoine := patrimoine + prix_maisons;
      end;

end loop;

U_D (statut_joueur.liste_proprietes);

end Fortune;


------------------------------------------------------------------------------


-- ****************************************************************************
-- * Cette procedure renvoie le classement d'un joueur face a ses adversaires *
-- * en ce qui concerne le compte en banque et le capital immobilier.         *
-- * Elle calcule egalement l'ecart du compte en banque et du capital         *
-- * immobilier du joueur par rapport a la moyenne des joueurs.               *
-- *                                                                          *
-- * ENTREE : le joueur qu'on souhaite classer.                               *
-- * SORTIE : les rang qu'il occupe pour l'argent et pour le capital.         *
-- *          les ecarts par rapport a la moyenne.                            *
-- ****************************************************************************

procedure Fortune_Relative (joueur                     : in T_Joueur;
                            rang_argent,rang_capital   : out integer;
                            ecart_argent,ecart_capital : out float) is

nb_joueurs                      : positive;
j                               : T_Joueur;
argent_joueur1,capital_joueur1  : T_Monnaie;
argent_joueur2,capital_joueur2  : T_Monnaie;
moyenne_argent,moyenne_capital  : float ;
r_argent,r_capital              :  integer := 1;

begin
nb_joueurs := Nombre_Joueurs;

-- Calcul du compte en banque et du capital du joueur
-- que l'on veut classer.
Fortune (joueur,argent_joueur1,capital_joueur1);
moyenne_argent  := float(argent_joueur1);
moyenne_capital := float(capital_joueur1);

-- Calcul du compte en banque et du capital de tous les joueurs
for j in T_Joueur'val(1)..T_Joueur'val(nb_joueurs)
loop
      -- Il faut eviter de recalculer la fortune de joueur
      -- qu'on veut classer.
      if j /= joueur
      then Fortune (j,argent_joueur2,capital_joueur2);

      -- Calcul du compte en banque total et du capital total
      moyenne_argent := moyenne_argent + float(argent_joueur2);
      moyenne_capital := moyenne_capital + float(capital_joueur2);

      -- Determination du rang du joueur
      if argent_joueur2 > argent_joueur1
      then r_argent := r_argent + 1;
      end if;

      if capital_joueur2 > capital_joueur1
      then r_capital := r_capital + 1;
      end if;
      end if;
end loop;

rang_argent := r_argent;
rang_capital := r_capital;
moyenne_argent := moyenne_argent / float(nb_joueurs);
moyenne_capital:= moyenne_capital / float(nb_joueurs);

-- Calcul de l'ecart par rapport a la moyenne
ecart_argent := 100.0*(float(argent_joueur1)/moyenne_argent-1.0);
ecart_capital:= 100.0*(float(capital_joueur2)/moyenne_capital-1.0);

end Fortune_Relative;



-- ***************************************************************************
-- * Cette fonction calcule combien il coute de tomber sur telle ou telle    *
-- * case du plateau.                                                        *
-- *                                                                         *
-- * ENTREE : la case.                                                       *
-- *          le lancer de des qui conduit a cette case.                     *
-- *                                                                         *
-- * SORTIE : la depense.                                                    *
-- ***************************************************************************

function Cout_Case (kase   : T_Cases;
                    lancer : T_Lancer_Des) return T_Monnaie is

cout         : T_Monnaie :=0;
proprio      : T_joueur;
couleur      : T_Couleur;
nb, nb_total : integer;

begin

-- Cas ou la case est une propriete
if kase in T_Terrains
then declare
     statut_propriete : T_Statut_Propriete (kase);
     begin
              Info_Propriete (statut_propriete);
              if not statut_propriete.hypotheque
              then cout:=statut_propriete.loyers(statut_propriete.nb_maisons+1);
              end if;
     end;

-- Cas ou la case est une gare
elsif kase in T_Gares
   then declare
        statut_gare          : T_Statut_Propriete (kase);
        case_depart,case_fin : T_Cases;
        begin
                 Info_Propriete (statut_gare);

                 -- Determination du nombre de gares detenues par
                 -- le proprietaire de cette gare
                 proprio := Proprietaire (kase);
                 Determiner_Serie (kase,couleur,case_depart,case_fin);
                 Nombre_Terrains_Dans_Serie (proprio,case_depart,case_fin,
                                             nb,nb_total);
                 cout := statut_gare.nb_gares(nb);
        end;

-- Cas ou la case est une compagnie
elsif kase in T_Compagnies
   then declare
        statut_compagnie     : T_Statut_Propriete (kase);
        case_depart,case_fin : T_Cases;
        begin
                 Info_Propriete (statut_compagnie);

                 -- Determination du nombre de compagnies detenues par
                 -- le proprietaire de cette compagnie
                 proprio := Proprietaire (kase);
                 Determiner_Serie (kase,couleur,case_depart,case_fin);
                 Nombre_Terrains_Dans_Serie (proprio,case_depart,case_fin,
                                             nb,nb_total);
                 cout := T_Monnaie(lancer)*statut_compagnie.des(nb);
        end;

-- Cas ou la case est une case speciale
elsif kase in T_Cases_Speciales
   then if kase=Impot_Revenu
        then cout := 200;
        else cout := 100;
        end if;
end if;
return cout;
end Cout_Case;

------------------------------------------------------------------------------

-- ***************************************************************************
-- * Cette procedure evalue differents parametres indispensables au          *
-- * joueur simule pour decider de l'achat eventuel du terrain sur lequel    *
-- * il se trouve.                                                           *
-- *                                                                         *
-- * ENTREE : le joueur.                                                     *
-- * SORTIE : la depense causee par le lancer de des le plus probable.       *
-- *          la depense la plus elevee que le joueur puisse avoir a faire   *
-- *                au prochain lancer de des.                               *
-- *          la probabilite d'obtenir ce lancer.                            *
-- ***************************************************************************

procedure Risque (joueur                          : in T_joueur;
                  cout_du_lancer_le_plus_probable : out T_Monnaie;
                  cout_du_lancer_le_plus_couteux  : out T_Monnaie;
                  probabilite_de_ce_lancer        : out T_Probabilite) is

i                  : T_Lancer_Des;
kase               : T_Cases;
cases_suivantes    : array (T_Lancer_Des) of T_Cases;
statut_joueur      : T_Statut_Joueur;
cout,cout_max      : T_Monnaie := 0;
lancer_cout_max    : T_Lancer_Des;

begin
Info_Joueur (joueur,statut_joueur);

-- On saute la case qui suit directement celle ou le joueur se trouve
-- car il ne peut pas tomber dessus.
kase := successeur_case(statut_joueur.position);

-- Boucle sur les 11 cases suivantes
for i in T_Lancer_Des
loop
        kase := successeur_case(kase);
        cases_suivantes(i) := kase;

        -- Determination de la depense qu'occasionne chaque case
        -- et de la depense maximale.
        cout := Cout_Case(kase,i);
        if cout > cout_max
        then cout_max := cout;
             lancer_cout_max := i;
        end if;
end loop;

-- Le lancer le plus probable est le 7.
cout_du_lancer_le_plus_probable := Cout_Case (cases_suivantes(7),7);

cout_du_lancer_le_plus_couteux := cout_max;
probabilite_de_ce_lancer := Probabilites (lancer_cout_max);

U_D (statut_joueur.liste_proprietes);

end Risque;

------------------------------------------------------------------------------

end Argent;

