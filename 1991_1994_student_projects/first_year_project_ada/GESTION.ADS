With Normalisation;
Use Normalisation;

Package Gestion_Dialogue is

Function Nombre_Joueurs Return Positive;

Procedure Info_Joueur ( Joueur : In T_Joueur ;
			Statut_Joueur : Out T_Statut_Joueur);

Procedure Info_Propriete (Statut_Propriete : In Out T_Statut_Propriete);

Function Successeur_case (Cases : in T_Cases)
					return T_Cases;

Function Info_Carte Return Boolean;

Procedure Achat_Titre;

Procedure Achat_Maison ( Terrain : In T_Terrains);

Procedure Vente_Maison ( Terrain : In T_Terrains);

Procedure Hypotheque ( Propriete : In T_Proprietes);

Procedure Deshypotheque ( Propriete : In T_Proprietes);

Procedure Afficher_Message ( Message : In T_Message);

Function Reponse_Troc_GD ( Joueur :In T_Joueur ;
			   Propriete_desiree:In T_Proprietes ;
                           Joueur_Simule : in T_Joueur;
			   Propriete_Echangee:In T_Proprietes) Return Boolean;

End Gestion_Dialogue;
