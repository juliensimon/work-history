
with normalisation,types,gestion_dialogue,general,g_l_troc,
	 unchecked_deallocation;
use normalisation,types,general,g_l_troc;

package body g_troc is
   type t_tab_troc_contenu is array (integer range <>) of t_troc;
   type t_tab_troc is access t_tab_troc_contenu;
procedure u_d is new unchecked_deallocation(t_tab_troc_contenu,t_tab_troc);

--**************************************************************************
--* la function determine_liste_prop_desirees donne la liste des terrains  *
--* que le joueur en entree peut desiree                                   *
--* ENTREE   : le nom du joueur (T_Joueur)                                 *
--* SORTIE   : la liste de terrains (T_Liste_Proprietes)                   *
--**************************************************************************
function determine_liste_prop_desirees(joueur: in t_joueur)
					     return t_liste_proprietes is

   tab_couleur : array (1..10) of t_couleur := (cyan,bleu,mauve,orange,rouge,
					     jaune,vert,noir,
					     gare,compagnie);
   tab_prop_manquantes : array (t_couleur) of t_liste_proprietes;
   tampon_couleur : t_couleur;
   nbe_cartes_desirees : integer := 0;
   prop_interessantes : t_liste_proprietes;
   index : integer := 1;
   nbe_terrains,nbe_tot_terrains : integer;
begin
   -- on classe le tableau des couleur par ordre decroissant
   -- on utilise le tri a bulle sur la valeur des series
   for i in 1..10 loop
      for j in i+1 ..10 loop
	 if val_serie(joueur,tab_couleur(i))<val_serie(joueur,tab_couleur(j))
			then
	    tampon_couleur := tab_couleur(j);
	    tab_couleur(j) := tab_couleur(i);
	    tab_couleur(i):=tampon_couleur;
	 end if;
      end loop;
   end loop;
   -- on determine les prop manquantes dans chaque serie non complete
   -- et on les comptes
   for c in t_couleur loop
      nombre_terrains_dans_serie(joueur,c,nbe_terrains,nbe_tot_terrains);
      if nbe_tot_terrains-nbe_terrains /= 0 then
	 tab_prop_manquantes(c) := carte_serie_manquante(joueur,c);
	 nbe_cartes_desirees := nbe_cartes_desirees + 
				    nbe_tot_terrains-nbe_terrains;
      end if;
   end loop;
   if nbe_cartes_desirees /= 0 then
      -- on cree le tableau des proprietes interressantes
      prop_interessantes := new t_liste_contenu(1..nbe_cartes_desirees);
      -- puis on le remplis d'abord avec les cartes des series ou il 
      -- ne manque que une carte. on place d'abord les cartes des series les
      --plus importantes d'ou l'utilisation du tableau classe.
      for i in 1 ..10 loop
	 if (tab_prop_manquantes(tab_couleur(i))/= null ) and
		  (tab_prop_manquantes(tab_couleur(i)).all'length = 1) then
	    prop_interessantes.all(index) :=
			      tab_prop_manquantes(tab_couleur(i)).all(1);
	    index := index +1;
	 end if;
      end loop;
      -- on fait de meme avec les serie ou il manque deux cartes.
      for i in 1 ..10 loop
	 if (tab_prop_manquantes(tab_couleur(i))/= null ) and
		  (tab_prop_manquantes(tab_couleur(i)).all'length = 2) then
	    prop_interessantes.all(index..index+1) :=
			      tab_prop_manquantes(tab_couleur(i)).all(1..2);
	    index := index +2;
	 end if;
      end loop;
   end if;
   -- on elimine maintenant tous les tableaux pointer en memoire dont on n a
   -- plus usage
   for c in t_couleur loop
      if tab_prop_manquantes(c)/= null then
	 u_d(tab_prop_manquantes(c));
      end if;
   end loop;
   return prop_interessantes;
end determine_liste_prop_desirees;
--**************************************************************************
--* la function determine_liste_prop_trocables donne la liste des terrains *
--* que le joueur en entree peut cedes                                     *
--* ENTREE   : le nom du joueur (T_Joueur)                                 *
--* SORTIE   : la liste de terrains (T_Liste_Proprietes)                   *
--**************************************************************************
function determine_liste_prop_trocables(joueur: in t_joueur) 
					     return t_liste_proprietes is

   tab_couleur : array (1..10) of t_couleur := (cyan,bleu,mauve,orange,rouge,
					     jaune,vert,noir,
					     gare,compagnie);
   tab_prop : array (t_couleur) of t_liste_proprietes;
   tampon_couleur : t_couleur;
   nbe_cartes_trocables : integer := 0;
   prop_trocables : t_liste_proprietes;
   index : integer := 1;
   nbe_terrains,nbe_tot_terrains : integer;
begin
   -- on classe le tableau des couleur par ordre croissant
   -- on utilise le tri a bulle sur la valeur des series
   for i in 1..10 loop
      for j in i+1 ..10 loop
	 if val_serie(joueur,tab_couleur(i))>val_serie(joueur,tab_couleur(j))
				 then
	    tampon_couleur := tab_couleur(j);
	    tab_couleur(j) := tab_couleur(i);
	    tab_couleur(i):=tampon_couleur;
	 end if;
      end loop;
   end loop;
   -- on determine les prop trocables dans chaque serie non complete
   -- et on les comptes
   for c in t_couleur loop
      nombre_terrains_dans_serie(joueur,c,nbe_terrains,nbe_tot_terrains);
      if nbe_tot_terrains-nbe_terrains /= 0 then
	 tab_prop(c) := carte_serie(joueur,c);
	 nbe_cartes_trocables := nbe_cartes_trocables +
				    nbe_tot_terrains-nbe_terrains;
      end if;
   end loop;
   if nbe_cartes_trocables /= 0 then
      -- on cree le tableau des proprietes trocables
      prop_trocables := new t_liste_contenu(1..nbe_cartes_trocables);
      -- puis on le remplis d'abord avec les cartes des series ou il
      -- y a une carte. on place d'abord les cartes des series les
      --moins importantes d'ou l'utilisation du tableau classe.
      for i in 1 ..10 loop
	 if (tab_prop(tab_couleur(i))/= null ) and
		  (tab_prop(tab_couleur(i)).all'length = 1) then
	    prop_trocables.all(index) :=
				       tab_prop(tab_couleur(i)).all(1);
	    index := index +1;
	 end if;
      end loop;
      -- on fait de meme avec les serie ou il y a deux cartes.
      for i in 1 ..10 loop
	 if (tab_prop(tab_couleur(i))/= null ) and
		  (tab_prop(tab_couleur(i)).all'length = 2) then
	    prop_trocables.all(index..index+1) :=
				    tab_prop(tab_couleur(i)).all(1..2);
	    index := index +2;
	 end if;
      end loop;
   end if;
   -- on elimine maintenant tous les tableaux pointes en memoire dont on n a
   -- plus usage
   for c in t_couleur loop
      if tab_prop(c)/= null then
	 u_d(tab_prop(c));
      end if;
   end loop;
   return prop_trocables;
end determine_liste_prop_trocables;

--**************************************************************************
--* Cette fonction determine quel sont les trocs interessant pour le joueur*
--* parmis tous les trocs possibles.                                       *
--*ENTREE  : le nom du joueur (T_Joueur)                                   *
--*          les prop trocables et les prop desirees (T_Liste_Proprietes)  *
--*SORTIE  : un pointeur vers le tableau des trocs (T_Tab_Troc)            *
--**************************************************************************
function quels_trocs_interessant(joueur: in t_joueur;
				 prop_desirees,
				 prop_trocables : in t_liste_proprietes)
					  return t_tab_troc is
    borne_max_tab : constant integer 
		  :=prop_desirees.all'length*prop_trocables.all'length;
   tab_troc_local : 
	 array(1..borne_max_tab) of t_troc;
   index :integer := 0;
   le_troc : t_troc;
   tab_troc_r : t_tab_troc;
begin
   for i in 1..prop_desirees.all'length loop
      for j in 1..prop_trocables.all'length loop
	 if reponse_troc_s(proprietaire(prop_desirees.all(i)),
			   prop_desirees.all(i),
			   joueur,
			   prop_trocables.all(j)) then
	    le_troc.j1 := proprietaire(prop_desirees.all(i));
	    le_troc.T1 := prop_desirees.all(i);
	    le_troc.J2 := joueur;
	    le_troc.T2 := prop_trocables(j);
	    index := index +1;
	    tab_troc_local (index) := le_troc;
	 end if;
      end loop;
   end loop;
   if index /=0 then
      tab_troc_r := new t_tab_troc_contenu(1..index);
      while index /= 0 loop
	 tab_troc_r.all(index) := tab_troc_local(index);
	 index := index-1;
      end loop;
      -- ici il refuse l'instruction
      -- tab_troc_r.all(1..index) := tab_troc_local(1..index)
      -- Marrant!
   end if;
   return tab_troc_r;
end quels_trocs_interessant;

--**************************************************************************
--* Cette function evalue quel troc serait acceptable par la partie adverse*
--* et propose le plus interessant, en tenant compte de ceux deja proposes *
--*ENTREE  : liste troc (T_Tab_Troc)                                       *
--*SORTIE  : le troc ( T_Troc)                                             *
--**************************************************************************
function premier_trocs_acceptables(liste_troc_interessant : in t_tab_troc)
					  return t_troc is
   troc_retour : t_troc := troc_null;
   index_max : constant integer := liste_troc_interessant.all'length;
   index : integer := 1;
begin
   if (liste_troc_interessant /= null) then
      while index <= index_max or troc_retour = troc_null loop
	 if reponse_troc_s(liste_troc_interessant.all(index).j1,
			      liste_troc_interessant.all(index).T1,
			      liste_troc_interessant.all(index).J2,
			      liste_troc_interessant.all(index).T2) then
	       -- le troc en juge bien
	    if nbe_troc(liste_troc_interessant.all(index)) = 0 then
	       -- il n'a jamais ete propose
	       troc_retour := liste_troc_interessant.all(index);
	    elsif integer(nbe_troc(liste_troc_interessant.all(index))/3)=
			   nbe_troc(liste_troc_interessant.all(index))*3 then
	       -- sinon on le propose tous les trois trocs
	       troc_retour := liste_troc_interessant.all(index);
	    else
	       ajout_troc(liste_troc_interessant.all(index));
	    -- on augmente le nombre d'essais mais on ne le selectionne pas
	    end if;

	 end if;
	 index := index +1;
      end loop;
   end if;
   return troc_retour;
end premier_trocs_acceptables;

--**************************************************************************
--*  La procedure jouer_troc realise le troc en lui meme                   *
--**************************************************************************

Procedure jouer_troc ( joueur : in T_Joueur) is
   prop_desirees,prop_trocables : t_liste_proprietes;
   liste_trocs_interessants : t_tab_troc;
   troc_propose : t_troc;
   j1 : t_joueur;
begin
   prop_desirees  := determine_liste_prop_desirees(joueur);
     --determine quels terrains je veux et les classe par ordre strategique
     -- decroissant.
   prop_trocables := determine_liste_prop_trocables(joueur);
     --determine quels terrains je pret a donner et les classe par ordre
     -- strategique croissant
   liste_trocs_interessants := quels_trocs_interessant(joueur,prop_desirees,
				    prop_trocables);
     -- determine quel troc sont interessant pour moi et les classe du plus
     -- vers le moins interressant;
   troc_propose := premier_trocs_acceptables(liste_trocs_interessants);
     -- determine quels trocs parmis la liste est succeptible d'interresser
     --un autre joueur et prend le premier qui n'a pas deja ete propose.
   if troc_propose /= Troc_null then
     -- s'il existe effectivement un troc repondant aux criteres, 
     -- alors on le propose
      if Gestion_dialogue.reponse_troc_gd(troc_propose.j1,
					  troc_propose.t1,
					  troc_propose.j2,
					  troc_propose.t2) then
	 detruit_troc(troc_propose);
      else
	 ajout_troc(troc_propose);
      end if;
   end if;
   u_d(prop_desirees);
   u_d(prop_trocables);
   u_d(liste_trocs_interessants);
end jouer_troc;

--**************************************************************************
--*  La Procedure Reponse_Troc repond a une proposition de troc             *
--**************************************************************************
  function reponse_troc_s ( joueur : in t_joueur;
			    prop_joueur : in t_proprietes;
			    joueur_ordi : in t_joueur;
			    prop_ordi : in t_proprietes )  return boolean is

  --*************************************************************************
  --* variable de reponse_troc                                              *
  --*************************************************************************
    v_r : boolean := false;
    joueur_tierce_complete : t_joueur;
	      -- joueur dont la carte complete une des series.
    liste_prop_tierce : t_liste_proprietes;
    val_serie_joueur,val_serie_joueur_ordi,val_serie_moyenne : T_Probabilite;
    accord_echange : boolean;
    case_depart,case_fin: t_cases;
    couleur : t_couleur;
  begin
    determiner_serie(prop_ordi,couleur,case_depart,case_fin);
    if not(serie_complete(joueur_ordi,case_depart,case_fin)) then
      -- On verifie tout de meme que le joueur ne demande pas une carte
      -- de mes series completes
      if carte_complete(joueur_ordi,prop_joueur) then
	-- Sa carte complete une de mes series
	if carte_complete ( joueur,prop_ordi) then
	  -- ma carte complete une de ces series
	  if abs( Val_serie(joueur,prop_joueur)
                    -val_serie(joueur_ordi,prop_ordi)) <=0.20 then
	    -- La comparaison des valeurs strategique des terrains est bonne
	    -- donc on accepte
	    v_r := True;
	  end if;   -- sinon on refuse
	else
	  -- ma carte ne complete pas une de ces series
	  joueur_tierce_complete :=complete_serie_joueur(prop_ordi);
	  if joueur_tierce_complete /= transparent then
	    -- ma carte complete une serie d'un autre joueur.
	    liste_prop_tierce := carte_j1_complete_Serie_J2(
				 joueur_tierce_complete,joueur);
		-- renvoie la liste de proriete du tierce joueur qui complete
		-- celle du joueur qui a propose le troc.
	    if liste_prop_tierce.all'length /= 0 then
	      --le tierce joueur possede au moins une carte completant une des
	      --series du joueur.
	      val_serie_joueur := val_serie(joueur,prop_joueur);
	      val_serie_joueur_ordi := val_serie(joueur_ordi,prop_ordi);
	      for i in 1..liste_prop_tierce.all'length loop
                val_serie_moyenne := T_Probabilite(
		     (val_serie(joueur_tierce_complete,liste_prop_tierce.all(i))
                     +val_serie_joueur)/2.0);
		accord_echange := accord_echange and
                        (abs(val_serie_joueur_ordi-val_serie_moyenne)<=0.20);
	      end loop;
	      -- si l'ecart entre la valeur de la serie de joueur_ordi et la
	      -- medianne de celles des deux autres joueur ne depasse pas 20
	      -- alors l'echange se fait.
	      v_r := accord_echange;
	    else
	      -- le tierce joueur ne possede pas de carte completeant un
	      -- des series du joueur.
	      -- on accepte le troc.
	      v_r := true;
	    end if;
	  else
	    -- ma carte ne complete pas la serie d'un tierce joueur
	    v_r := True;
	  end if;
	end if;
      else
	-- sa carte ne complete pas une de mes series.
	if carte_complete(joueur,prop_ordi) then
	  -- ma carte complete une de ces series
	  joueur_tierce_complete :=complete_serie_joueur(prop_joueur);
	  if joueur_tierce_complete /= transparent then
	    -- sa carte complete une serie d'un autre joueur.
	    liste_prop_tierce := carte_j1_complete_Serie_J2(
				 joueur_tierce_complete,joueur_ordi);
		-- renvoie la liste de proriete du tierce joueur qui complete
		-- celle du joueur_ordi.
	    if liste_prop_tierce.all'length /= 0 then
	      --le tierce joueur possede au moins une carte completant une de
	      -- mes series.
	      val_serie_joueur := val_serie(joueur,prop_joueur);
	      val_serie_joueur_ordi := val_serie(joueur_ordi,prop_ordi);
	      for i in 1..liste_prop_tierce.all'length loop
                val_serie_moyenne := T_Probabilite(
		     (val_serie(joueur_tierce_complete,liste_prop_tierce.all(i))
                     +val_serie_joueur)/2.0);
		accord_echange := accord_echange and
                        (abs(val_serie_joueur_ordi-val_serie_moyenne)<=0.2);
	      end loop;
	      -- si l'ecart entre la valeur de la serie de joueur_ordi et la
	      -- medianne de celles des deux autres joueur ne depasse pas 20
	      -- alors l'echange se fait.
	      v_r := accord_echange;
	    end if;
	  else
	    -- sa carte ne complete pas la serie d'un tierce joueur: on refuse
	    null;
	  end if;
	else
	  -- ma carte ne complete pas une des ces series.
	  joueur_tierce_complete :=complete_serie_joueur(prop_ordi);
	  if joueur_tierce_complete /= transparent then
	    -- ma carte complete une serie d'un autre joueur.
	    liste_prop_tierce := carte_j1_complete_Serie_J2(
				 joueur_tierce_complete,joueur);
		-- renvoie la liste de proriete du tierce joueur qui complete
		-- celle du joueur qui a propose le troc.
	    if liste_prop_tierce.all'length /= 0 then
	      --le tierce joueur possede au moins une carte completant une des
	      --series du joueur.
	      -- on refuse le troc.
	      v_r := false;
	    else
	      -- les deux carte ne completent aucune series.
	      if abs( Val_serie(joueur,prop_joueur)
                    -val_serie(joueur_ordi,prop_ordi)) <=0.20 then
		    -- La comparaison des valeurs strategique des terrains est
		    -- bonne donc on accepte
		v_r := True;
	      end if;   -- sinon on refuse
	    end if;
	  end if;
	end if;
      end if;
    end if;
    return v_r;
  end reponse_troc_s;

end g_troc;
