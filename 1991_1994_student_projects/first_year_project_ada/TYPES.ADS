
with Normalisation;
use Normalisation;

package types is

  type T_Couleur is (cyan,bleu,mauve,orange,rouge,jaune,vert,noir,
                     gare,compagnie);

  subtype T_Lancer_Des is integer range 2..12;
  type T_Reel is digits 3;
  subtype T_probabilite is T_Reel range 0.0 .. 1.0;

  Type T_Profil is (Pere_De_Famille,Investisseur,Golden_boy);

  Type T_Valeur_Terrains is array (T_Proprietes) of T_Probabilite;
                           
  type T_Caracteristiques_Profil is
    record
      risque_max             : T_Probabilite;
      Valeur_Terrains        : T_Valeur_Terrains;
    end record;

  type T_Joueur_Simule is
    record
      joueur                 : T_Joueur;
      profil                 : T_Profil;
      caracteristiques       : T_Caracteristiques_Profil;
    end record;

  type A_T_Joueur_simule is access T_Joueur_Simule;


--***********************Variables et constantes Globales du Prg************

Probabilites : constant array (T_lancer_des) of t_probabilite
	     :=(0.028,0.056,0.083,0.111,0.139,0.167,
		0.139,0.111,0.083,0.056,0.028);

Risque_Tolere : constant array (T_Profil) of T_Probabilite
                := (0.056,0.111,0.167);

Valeur_Strategique_Terrains : array (T_Proprietes,T_Profil) of T_Probabilite
           := ( (0.7,0.2,0.0)  , (0.7,0.2,0.0)  ,
	   (0.8,0.3,0.5)  , (0.8,0.3,0.5)  , (0.8,0.3,0.5)  ,
	   (1.0,0.5,0.15) , (1.0,0.5,0.15) , (1.0,0.5,0.15) ,
	   (0.7,0.75,0.3) , (0.7,0.75,0.3) , (0.7,0.75,0.3) ,
	   (0.5,1.0,0.6)  , (0.5,1.0,0.6)  , (0.5,1.0,0.6)  ,
	   (0.3,0.9,0.8)  , (0.3,0.9,0.8)  , (0.3,0.9,0.8)  ,
	   (0.1,0.8,0.95) , (0.1,0.8,0.95) , (0.1,0.8,0.95) ,
	   (0.0,0.65,1.0) , (0.0,0.65,1.0) ,
	   (0.4,0.20,0.0) , (0.4,0.2,0.0)  ,
           (0.8,0.3,0.1)  , (0.8,0.3,0.1)  ,(0.8,0.3,0.1) , (0.8,0.3,0.1) );


Tab_Joueur : array (T_joueur) of A_T_Joueur_simule;

end Types;
