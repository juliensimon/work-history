Package Normalisation is

Type T_Cases is (Belleville,Lecourbe,Vaugirard,Courcelles,Republique,Villette,
               Neuilly,Paradis,Mozart,Saint_Michel,Pigalle,Matignon,Malsherbes,
               Henri_Martin,Saint_Honore,Bourse,Lafayette,Breteuil,Foch,
               Capucines,Champs_Elysees,Paix,Electricite,Eaux,Montparnasse,Lyon,
               Nord,Saint_Lazare,Depart,Communaute1,Communaute2,Communaute3,
               Impot_Revenu,Taxe_Luxe,Chance1,Chance2,Chance3,Prison,
               Parc_Gratuit,Allez_Prison);
Subtype T_Proprietes is T_Cases Range Belleville .. Saint_Lazare;
Subtype T_Cases_Speciales is T_Cases Range Depart .. Allez_Prison;
Subtype T_Communaute is T_Cases_Speciales Range Communaute1 .. Communaute3;
Subtype T_Chance is T_Cases_Speciales Range Chance1 .. Chance3;
Subtype T_Taxes is T_Cases_Speciales Range Impot_Revenu .. Taxe_Luxe;
Subtype T_Depart is T_Cases_Speciales Range Depart .. Depart;
Subtype T_Prison is T_Cases_Speciales Range Prison .. Prison;
Subtype T_Autres is T_Cases_Speciales Range Parc_Gratuit .. Allez_Prison;
Subtype T_Gares is T_Proprietes Range  Montparnasse .. Saint_Lazare;
Subtype T_Compagnies is T_Proprietes Range Electricite .. Eaux;
Subtype T_Terrains is T_Proprietes Range Belleville .. Paix;
Subtype T_Cyan is T_Terrains Range Belleville .. Lecourbe;
Subtype T_Bleu is T_Terrains Range Vaugirard .. Republique;
Subtype T_Mauve is T_Terrains Range Villette .. Paradis;
Subtype T_Orange is T_Terrains Range Mozart .. Pigalle;
Subtype T_Rouge is T_Terrains Range Matignon .. Henri_Martin;
Subtype T_Jaune is T_Terrains Range Saint_Honore .. Lafayette;
Subtype T_Vert is T_Terrains Range Breteuil .. Capucines;
Subtype T_Noir is T_Terrains Range Champs_Elysees .. Paix;
Type T_Monnaie is New Integer;
Type T_Joueur is (Bleu,Vert,Blanc,Rouge,Noir,Jaune,Transparent);
Type T_Loyers is Array(Positive Range 1..6) Of T_Monnaie;
Type T_Nb_Gares is Array(Positive Range 1..4) Of T_Monnaie;
Type T_Des is Array(Positive Range 1..2) Of T_Monnaie;

Type T_Statut_Propriete (Propriete : T_Proprietes) is Record
     Prix : T_Monnaie;
     Hypotheque : Boolean;
     Case Propriete is
          When T_Terrains =>
          Nb_Maisons : Integer Range 0 .. 5;
          Loyers : T_Loyers;
          Prix_Maison : T_Monnaie;
          When T_Compagnies =>
          Des : T_Des;
          When Others =>
          Nb_Gares : T_Nb_Gares;
     End Case;
End Record;

Type T_Liste_Contenu is Array (Positive Range <>) Of T_Proprietes;
Type T_Liste_Proprietes is Access T_Liste_Contenu;
Type T_Statut_Joueur is Record
     Compte : T_Monnaie;
     Position : T_Cases;
     Liste_Proprietes : T_Liste_Proprietes;
End Record;
Type T_Message is New String(1 .. 40);

End Normalisation;
