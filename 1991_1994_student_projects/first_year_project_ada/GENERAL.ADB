with Gestion_Dialogue;
use Gestion_Dialogue;

package body General is


------------------------------------------------------------------------------


-- *************************************************************************
-- *  la fonction val_serie determine l importance strategique d un terrain*
-- *  et ce en supposant que si le joueur est humain il est de niveau      *
-- *  investisseur.                                                        *
-- *   entre  : joueur : le joueur possedant le terrain                    *
-- *            soit prop   : le terrain dont on doit evaluer la valeur    *
-- *            soit couleur : la couleur de la serie a evaluer            *
-- *   sortie : la valeur du terrain (un T_Probabilite)                    *
-- *************************************************************************

Function Val_Serie (joueur : in T_Joueur; prop : in T_Proprietes)
                   return T_Probabilite is
v_r : T_Probabilite;

begin
      if tab_joueur(joueur) = null
      then v_r :=Valeur_strategique_terrains(prop,investisseur);
      else
      v_r :=tab_joueur(joueur).all.caracteristiques.valeur_terrains(prop);
      end if;
      return v_r;

end Val_Serie;

function val_serie (joueur : in t_joueur; couleur : in t_couleur)
		     return t_probabilite is
   case_dep,case_fin : t_cases;

begin
   bornes_serie(couleur,case_dep,case_fin);
   return val_serie(joueur,case_dep);
end val_serie;


-----------------------------------------------------------------------------


-- **************************************************************************
-- * La fonction proprietaire donne le nom du proprietaire d'un terrain.    *
-- * Elle renvoie transparent si c'est la banque qui possede ce terrain     *
-- *                                                                        *
-- * ENTREE   : la propriete dont on veut connaitre le proprietaire         *
-- * SORTIE   : le joueur                                                   *
-- **************************************************************************

function Proprietaire (propriete : in T_Proprietes) return T_Joueur is

j,joueur      : T_Joueur ;
statut_joueur : T_Statut_Joueur;
i             : integer;

begin

for j in T_joueur
loop
       Info_joueur(j,statut_joueur);

       for i in 1..statut_joueur.liste_proprietes.all'length
       loop
            if statut_joueur.liste_proprietes.all(i) = propriete
            then joueur := j;
	    end if;
       end loop;
end loop;

U_D (statut_joueur.liste_proprietes);

return joueur;
end Proprietaire;


------------------------------------------------------------------------------


-- **************************************************************************
-- * Cette procedure determine a quelle serie appartient une propriete et   *
-- * renvoie les deux cases delimitant cette serie.                         *
-- *                                                                        *
-- * ENTREE : la propriete.                                                 *
-- * SORTIE : les deux cases frontieres.                                    *
-- **************************************************************************

Procedure Determiner_Serie (propriete : in T_Proprietes;
                            couleur   : out T_Couleur;
                            case_depart,case_fin : out T_Cases) is
case_d, case_f : T_Cases;
color          : T_Couleur;

begin
    case propriete is
      when t_cyan =>
        case_d := t_cyan'first;
        case_f := t_cyan'last;
        color  := cyan;
      when t_bleu =>
        case_d := t_bleu'first;
        case_f := t_bleu'last;
        color  := bleu;
      when t_Mauve =>
        case_d := t_mauve'first;
        case_f := t_mauve'first;
        color  := mauve;
      when t_orange =>
        case_d := t_orange'first;
        case_f := t_orange'last;
        color  := orange;
      when t_rouge =>
        case_d := t_rouge'first;
        case_f := t_rouge'last;
        color  := rouge;
      when t_jaune =>
        case_d := t_jaune'first;
        case_f := t_jaune'last;
        color  := jaune;
      when t_vert =>
        case_d := t_vert'first;
        case_f := t_vert'last;
        color  := vert;
      when t_noir =>
        case_d := t_noir'first;
        case_f := t_noir'last;
        color  := noir;
      when t_gares =>
        case_d := t_gares'first;
        case_f := t_gares'last;
        color  := gare;
      when t_compagnies =>
        case_d := t_compagnies'first;
        case_f := t_compagnies'last;
        color  := compagnie;
      when others =>
	null;
    end case;

case_depart := case_d;
case_fin    := case_f;
couleur     := color;

end Determiner_Serie;


------------------------------------------------------------------------------


--****************************************************************************
--* la procedure borne_serie donne les cases extremes d'une serie indiquee   *
--* par sa couleur.                                                          *
--*ENTREE   : la couleur de la serie                                         *
--*SORTIE   : les deux bornes (T_Cases)                                      *
--****************************************************************************
procedure Bornes_Serie ( couleur : in t_couleur;
			case_depart,case_fin : out t_cases) is

begin
   case couleur is
      when cyan =>
	 case_depart := t_cyan'first;
	 case_fin := t_cyan'last;
      when bleu =>
	 case_depart := t_bleu'first;
	 case_fin := t_bleu'last;
      when mauve =>
	 case_depart := t_mauve'first;
	 case_fin := t_mauve'first;
      when orange =>
	 case_depart := t_orange'first;
	 case_fin := t_orange'last;
      when rouge =>
	 case_depart := t_rouge'first;
	 case_fin := t_rouge'last;
      when jaune =>
	 case_depart := t_jaune'first;
	 case_fin := t_jaune'last;
      when vert =>
	 case_depart := t_vert'first;
	 case_fin := t_vert'last;
      when noir =>
	 case_depart := t_noir'first;
	 case_fin := t_noir'last;
      when gare =>
	 case_depart := t_gares'first;
	 case_fin := t_gares'last;
      when compagnie =>
	 case_depart := t_compagnies'first;
	 case_fin := t_compagnies'last;
      when others =>
	 null;
   end case;
end bornes_serie;


-------------------------------------------------------------------------------


--**************************************************************************
--* La procedure nombre_de_terrains_dans_serie ramene le nombre de terrains*
--*possedes par le joueur specifie dans la serie de la propriete           *
--*ENTREE  : Joueur : le joueur a examiner                                 *
--*          case_dep,case_fin : les bornes de la serie                    *
--*          ou alors la couleur de la serie                               *
--*SORTIE  : Nombre_terrains : le nbe de terrains de cette serie possedes  *
--*              par le joueur                                             *
--*          Nombre_total_terrains : le nbe total de proprietes constituant*
--*              la serie                                                  *
--**************************************************************************

Procedure Nombre_Terrains_Dans_Serie (joueur                : in T_joueur;
                                      case_depart,case_fin  : in T_Cases;
                                      nombre_terrains       : out integer;
                                      nombre_total_terrains : out integer) is

statut_joueur     : T_Statut_Joueur;
i                 : integer := 1;
nb_terrains       : integer := 0;

begin
declare subtype T_Local is T_Cases range case_depart..case_fin;
begin
         Info_Joueur (joueur, statut_joueur);

         while (i < statut_joueur.liste_proprietes'length)
         loop
              if statut_joueur.liste_proprietes(i) in T_Local
              then nb_terrains := nb_terrains + 1;
	    end if;
	 end loop;

         nombre_terrains := nb_terrains;
         nombre_total_terrains :=
          (T_Local'pos (case_fin) - T_Local'pos (case_depart) + 1);
end;

U_D (statut_joueur.liste_proprietes);

end Nombre_Terrains_Dans_Serie;


procedure NOMBRE_TERRAINS_DANS_SERIE (JOUEUR                : in T_JOUEUR;
				      couleur               : in T_Couleur;
				      NOMBRE_TERRAINS       : out INTEGER;
				      NOMBRE_TOTAL_TERRAINS : out INTEGER) is

   case_dep,case_fin : T_Cases;
   nbe_terrains,nbe_tot_terrains : integer;
begin
   bornes_serie(couleur,case_dep,case_fin);
   nombre_terrains_dans_serie(joueur,case_dep,case_fin,nbe_terrains,
							 nbe_tot_terrains);
   nombre_terrains := nbe_terrains;
   nombre_total_terrains := nbe_tot_terrains;
end nombre_terrains_dans_serie;

------------------------------------------------------------------------------


--****************************************************************************
--* La fonction carte_serie donne les cartes un joueur dans une serie        *
--*ENTREE joueur : le joueur vise                                            *
--*       couleur : la couleur de la serie vise                              *
--*SORTIE un pointeur le tableau des proprietes                              *
--****************************************************************************
function carte_serie(  joueur : in t_joueur;
				 couleur : in t_couleur)
				    return t_liste_proprietes is

   case_dep,case_fin : t_cases;
   nbe_cartes,nbe_tot_cartes : integer;
   liste_prop : t_liste_proprietes;
   index : integer := 1;
begin
   bornes_serie(couleur,case_dep,case_fin);
   nombre_terrains_dans_serie(joueur,case_dep,case_fin,nbe_cartes,nbe_tot_cartes);
   if nbe_cartes /= 0 then
      liste_prop := new t_liste_contenu(1..nbe_cartes);
      for cases in case_dep.. case_fin loop
	 if proprietaire(cases) = joueur then
	    liste_prop.all(index):=cases;
	    index := index+1;
	 end if;
      end loop;
   end if;
   return liste_prop;
end carte_serie;


-------------------------------------------------------------------------------

--****************************************************************************
--* La fonction carte_serie_manquante donne les cartes manquantes chez un    *
--* joueur dans une serie.                                                   *
--*ENTREE joueur : le joueur vise                                            *
--*       couleur : la couleur de la serie vise                              *
--*SORTIE un pointeur le tableau des proprietes                              *
--****************************************************************************
function carte_serie_manquante(  joueur : in t_joueur;
				 couleur : in t_couleur)
				    return t_liste_proprietes is

   nbe_cartes_manquantes : integer := 0;
   case_dep,case_fin : t_cases;
   nbe_cartes,nbe_tot_cartes : integer;
   liste_prop : t_liste_proprietes;
   index : integer := 1;
begin
   bornes_serie(couleur,case_dep,case_fin);
   nombre_terrains_dans_serie(joueur,case_dep,case_fin,nbe_cartes,nbe_tot_cartes);
   nbe_cartes_manquantes:= nbe_tot_cartes-nbe_cartes;
   if nbe_cartes_manquantes /= 0 then
      liste_prop := new t_liste_contenu(1..nbe_cartes_manquantes);
      for cases in case_dep.. case_fin loop
	 if proprietaire(cases) /= joueur then
	    liste_prop.all(index):=cases;
	    index := index+1;
	 end if;
      end loop;
   end if;

   return liste_prop;
end carte_serie_manquante;

-------------------------------------------------------------------------------



-- ****************************************************************************
-- * Cette fonction determine si le joueur possede l'integralite des cartes   *
-- * de la serie delimitee par les deux cases.                                *
-- *                                                                          *
-- * ENTREE : le joueur.                                                      *
-- *          les deux cases frontieres.                                      *
-- * SORTIE : le resultat.                                                    *
-- ****************************************************************************

function Serie_Complete (joueur : in T_Joueur;
                         case_depart, case_fin : in T_Cases) return boolean is

subtype T_Local is T_Cases range case_depart..case_fin;
statut_joueur : T_Statut_Joueur;
i,nb_terrains : integer;
resultat      : boolean := false;

begin
Info_Joueur (joueur,statut_joueur);

for i in statut_joueur.liste_proprietes'range
loop
        if statut_joueur.liste_proprietes(i) in T_Local
        then nb_terrains := nb_terrains + 1;
        end if;
end loop;

if nb_terrains = (T_Local'pos(case_depart)-T_Local'pos(case_fin)+1)
then resultat := true;
end if;

U_D (statut_joueur.liste_proprietes);

return resultat;
end Serie_Complete;


-------------------------------------------------------------------------------

--  **************************************************************************
--  *  La fonction Carte_Complete verifie si une carte complete une des      *
--  *  series du joueur.                                                     *
--  * ENTREE : le joueur, la propriete a tester                              *
--  * SORTIE : oui ou non ( boolean)                                         *
--  **************************************************************************

Function Carte_Complete ( joueur : in t_joueur;
                          prop : in t_proprietes) return boolean is

statut_joueur        : T_statut_joueur;
i,nb,nb_total        : integer := 0;
case_depart,case_fin : t_cases;
couleur              : T_Couleur;
resultat             : boolean := false;

begin
Info_Joueur(joueur,statut_joueur);
Determiner_Serie (prop,couleur,case_depart,case_fin);
Nombre_Terrains_Dans_Serie (joueur,case_depart,case_fin,nb,nb_total);

-- S'il ne manque au joueur qu'un terrain de la serie,
if nb = nb_total-1
then
        declare subtype T_Local is T_Cases range case_depart..case_fin;
        begin
                nb := 0;
                --Boucle sur les proprietes du joueur.
                for i in statut_joueur.liste_proprietes'range
                loop
                        -- Si la propriete appartient a la serie
                        if  statut_joueur.liste_proprietes(i) in T_Local
                            -- et si elle est differente de la propriete
                            -- a tester,
                            and statut_joueur.liste_proprietes(i) /= prop

                        -- alors, on incremente le compteur.
                        then nb:= nb + 1;
                        end if;
                end loop;

                -- Si le joueur possede toutes les proprietes de la serie
                -- sauf la propriete a tester,
                if nb = nb_total-1

                -- alors cette propriete complete sa serie.
                then resultat := true;
                end if;
        end;
end if;

return resultat;
end Carte_Complete;


------------------------------------------------------------------------------


-- ************************************************************************
-- * La fonction Complete_Serie_Joueur trouve le joueur dont le terrain   *
-- * complete une serie. Elle retourne transparent si aucun joueur ne     *
-- * convient.                                                            *
-- * ENTREE  :  le terrain a tester.                                      *
-- * SORTIE  :  le nom du joueur                                          *
-- ************************************************************************

Function Complete_Serie_Joueur(prop : in T_Proprietes) return T_Joueur is

joueur : T_Joueur := transparent;
trouve : boolean := false;

begin
for j in T_joueur
loop
        if Carte_Complete(j,prop)
        then joueur := j;
             trouve := true;
	end if;
        if trouve
        then exit;
        end if;
end loop;

return joueur;
end Complete_Serie_Joueur;


-------------------------------------------------------------------------------


-- **************************************************************************
-- * La fonction Cartes_J1_Complete_Serie_J2 renvoie un pointeur sur la     *
-- * liste des proprietes du joueur 1 qui completent une des series d       *
-- * joueur 2.                                                              *
-- * ENTREE : le joueur possedants les proprietes.                          *
-- *          le joueur dont on doit determiner quelles series peuvent      *
-- *               etre completees par un des terrains du joueur 1.         *
-- * SORTIE : un pointeur vers le tableau des proprietes.                   *
-- **************************************************************************

Function Carte_J1_Complete_Serie_J2 (j1,j2 : in T_joueur)
                                     return T_Liste_Proprietes is
statut_joueur1 : T_Statut_Joueur;
nbe_prop       : integer := 0;
liste_prop     : t_liste_proprietes;
nb             : integer  := 0;
index          : positive := 1;

begin
      Info_Joueur(J1,statut_joueur1);
      for i in 1..statut_joueur1.liste_proprietes'length
      loop
            if Carte_Complete(j2,statut_joueur1.liste_proprietes(i))
            then nb := nb + 1;
            end if;
      end loop;

      if nb /= 0
      then liste_prop := new T_Liste_Contenu(1..nb);

           for i in 1..statut_joueur1.liste_proprietes'length
           loop
                if Carte_Complete(j2,statut_joueur1.liste_proprietes(i))
                then liste_prop(index):=statut_joueur1.liste_proprietes(i);
                     index :=index+1;
                end if;
           end loop;
       end if;

U_D(statut_joueur1.liste_proprietes);

return liste_prop;
end Carte_J1_Complete_Serie_J2;


------------------------------------------------------------------------------


-- ***************************************************************************
-- * Cette fonction determine si l'on peut encore construire sur une serie.  *
-- *                                                                         *
-- * ENTREE : les frontieres de la serie.                                    *
-- * SORTIE : le resultat.                                                   *
-- ***************************************************************************

function Serie_Completement_Construite (case_depart,case_fin : in T_Cases)
                                       return boolean is
resultat : boolean := true;
nb       : integer;

begin
-- Boucle sur les terrains de la serie.
for terrain in case_depart..case_fin
loop
        declare statut_propriete : T_Statut_Propriete(terrain);
        begin
                Info_Propriete(statut_propriete);
                nb := statut_propriete.nb_maisons;
                -- Si un terrain contient moins de 5 maisons,
                if nb /= 5
                -- alors on peut encore construire.
                then resultat := false;
                end if;
        end;
end loop;

return resultat;
end Serie_Completement_Construite;


-------------------------------------------------------------------------------

end General;
