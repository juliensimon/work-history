
with Normalisation;
use Normalisation;

package g_troc is
  Procedure Jouer_Troc ( joueur : in t_joueur);

  Function Reponse_troc_s( joueur      : in t_joueur;
			   prop_joueur : in t_proprietes;
			   joueur_ordi : in t_joueur;
			   prop_ordi   : in t_proprietes ) return boolean;

end g_troc;
