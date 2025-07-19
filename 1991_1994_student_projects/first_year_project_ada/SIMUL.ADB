with Gestion_Dialogue,Types,General,Argent,Construction,Strategie,
     Calendar,Direct_Io,G_Troc,G_L_Troc;

use Gestion_Dialogue,Types,General,Argent,Construction,Strategie;

package body Simulation is

package iojoueur is new direct_io(t_joueur_simule);


----------------------------------------------------------------------------


Procedure Creer_Joueur (Joueur :In T_Joueur) is

le_moment   : long_integer;
type_joueur : long_integer;
profil      : T_profil;

begin
le_moment := long_integer(calendar.seconds(calendar.clock));
type_joueur := le_moment - long_integer(le_moment/3);

case type_joueur is
      when 0 =>
	 profil := pere_de_famille;
      when 1 =>
	 profil := investisseur;
      When 2 =>
	 profil := golden_boy;
      when others =>
	 null;
end case;

tab_joueur(joueur) := new t_joueur_simule;
tab_joueur(joueur).all.joueur := joueur;
tab_joueur(joueur).all.profil := profil;
tab_joueur(joueur).all.caracteristiques.risque_max := risque_tolere(profil);

for i in t_proprietes
loop
      tab_joueur(joueur).all.caracteristiques.valeur_terrains(i)
			:= valeur_strategique_terrains(i,profil);
      -- cela a cause de l'erreur improper slice.
end loop;
end Creer_Joueur;


----------------------------------------------------------------------------


Function Sauvegarder (Joueur : In T_Joueur) Return String is
nom_fichier : string (1..8);
lg          : integer := 8;

begin
   nom_fichier := integer'image(integer(calendar.seconds(calendar.clock)));
   while ( nom_fichier(1) = ' ')
   loop
      nom_fichier (1..lg-1) := nom_fichier(2..lg);
      lg := lg-1;
   end loop;
   --le nom est maintenant sans espaces.
   g_l_troc.sauvegarder_troc(nom_fichier(1..lg));
   declare
	 nom_fichier_j : string(1..lg+4) := nom_fichier & ".S_J";
	 ref_fichier : iojoueur.file_type;
	 index : iojoueur.positive_count := 1;

      begin
	 iojoueur.create (ref_fichier,iojoueur.inout_file,nom_fichier_j);
         for j in t_joueur
         loop
            if tab_joueur(j) /= null
            then iojoueur.write (ref_fichier,tab_joueur(j).all,index);
                 index := iojoueur.positive_count(integer(index)+1);
	    end if;
	 end loop;
	 iojoueur.close(ref_fichier);
      end;
   return nom_fichier(1..lg);
end Sauvegarder;


-------------------------------------------------------------------------------


Procedure Restaurer (Joueur : In T_Joueur ; Nom_Fichier : In String) is

   nom_fichier_j : string(1..nom_fichier'length+4) := nom_fichier & ".S_J";
   ref_fichier : iojoueur.file_type;
   taille : iojoueur.count ;
   joueur_simule : t_joueur_simule;


begin
   g_l_troc.restaurer_troc(nom_fichier);
   iojoueur.open (ref_fichier,iojoueur.inout_file,nom_fichier_j);
   taille := iojoueur.size(ref_fichier);
   if integer(taille) /= 0 then
      for pos in 1.. taille loop
	 iojoueur.read(ref_fichier,joueur_simule,pos);
	 tab_joueur (joueur_simule.joueur) :=
				    new t_joueur_Simule'(joueur_simule);
      end loop;
   end if;
   iojoueur.close(ref_fichier);
end Restaurer;


----------------------------------------------------------------------------


Procedure Jouer (joueur : in T_Joueur ; double : in Boolean) is

statut_joueur       : T_Statut_Joueur;
liste_series        : T_Liste_Series;
maison_vendue       : boolean;
terrain_hypotheque  : boolean;

begin
Info_Joueur (joueur,statut_joueur);

-- si le joueur n'est pas en faillite,
if statut_joueur.compte > 0
-- il tente de deshypothequer une propriete
then Deshypothequer_Propriete(Tab_Joueur(joueur));
     -- si la propriete sur laquelle il se trouve n'appartient a personne,
     if Proprietaire (statut_joueur.position) /= transparent
     -- il reflechit.
     then if Decision_Achat_Propriete (Tab_Joueur(joueur))
          then Achat_Titre;
               Mise_A_Jour_Valeur (Tab_Joueur(joueur),statut_joueur.position);
          end if;
          liste_series := Series_Constructibles (Tab_Joueur(joueur));
          -- si le joueur peut construire sur une serie,
          if liste_series /= null
          -- il determine la serie et le terrain sur
          -- lesquels il doit construire.
          then Choisir_Serie_A_Construire (Tab_Joueur(joueur),liste_series);
               Decision_Achat_Maisons(Tab_Joueur(joueur),liste_series);
          end if;
      end if;

-- sinon, il vend des maisons et hypotheque des terrains.
else while statut_joueur.compte < 0 and maison_vendue
     loop
     Vendre_Maison (Tab_Joueur(joueur),maison_vendue);
     end loop;

     while statut_joueur.compte < 0 and terrain_hypotheque
     loop
     Hypothequer_Propriete (Tab_Joueur(joueur),terrain_hypotheque);
     end loop;

end if;

u_d (statut_joueur.liste_proprietes);

end Jouer;


----------------------------------------------------------------------------


Function Choix_Chance (joueur : in T_Joueur) return boolean is
decision : boolean := false;

begin
   declare
   statut_joueur : T_Statut_Joueur;
   begin
         Info_Joueur (joueur,statut_joueur);
         if statut_joueur.compte < 20
         then decision := true;
         end if;
         u_d (statut_joueur.liste_proprietes);
   end;

return decision;
end Choix_Chance;


----------------------------------------------------------------------------


Function Choix_Prison (Joueur : In T_Joueur ;
                       Nb_Tour_Prison : In Positive) Return Boolean is
decision : boolean := true;
begin
   declare
   statut_joueur : T_Statut_Joueur;
   begin
         Info_Joueur (joueur,statut_joueur);
         if statut_joueur.compte < 100
         then decision := false;
         end if;
         u_d (statut_joueur.liste_proprietes);
   end;

return decision;
end Choix_Prison;


----------------------------------------------------------------------------


Function Utiliser_Carte (Joueur : In T_Joueur ;
                         Nb_Tour_Prison : In Positive) Return Boolean is
decision : boolean := false;
begin
   declare
   statut_joueur : T_Statut_Joueur;
   begin
         Info_Joueur (joueur,statut_joueur);
         if statut_joueur.compte < 100
         then decision := true;
         end if;
         u_d (statut_joueur.liste_proprietes);
   end;

return decision;
end Utiliser_Carte;

----------------------------------------------------------------------------


Function Troc_Possible Return Boolean is
begin
return true;
end Troc_Possible;


----------------------------------------------------------------------------


Function Reponse_Troc_S (Joueur : In T_Joueur ;
                         Propriete_Joueur : In T_Proprietes;
                         Joueur_Ordinateur : In T_Joueur;
                         Propriete_Ordinateur : In T_Proprietes)
                         Return Boolean is

begin

return g_troc.Reponse_Troc_S (Joueur , Propriete_Joueur ,
                              Joueur_Ordinateur ,Propriete_Ordinateur );
end Reponse_Troc_S;


----------------------------------------------------------------------------

Procedure Jouer_Troc (Joueur : In T_Joueur) is
begin
   g_troc.jouer_troc(joueur);
end Jouer_Troc;

----------------------------------------------------------------------------

end Simulation;
