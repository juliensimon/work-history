with Normalisation,Unchecked_Deallocation,Types;
use Normalisation,Types;

package Construction is

type T_Serie;
type T_Liste_Series is access T_Serie;
type T_Serie is
record
        case_depart    : T_Cases;
        case_fin       : T_Cases;
        serie_suivante : T_Liste_Series;
end record;

Tete_Liste_Series : constant T_Liste_Series := null;

Procedure Un_De is new unchecked_deallocation
                        (T_Serie,T_Liste_Series);

function Series_Constructibles (pointeur_joueur_simule : in A_T_Joueur_Simule)
                               return T_Liste_Series;

procedure Choisir_Serie_A_Construire(pointeur_joueur_simule
                                                   : in A_T_Joueur_Simule;
                                     liste         : in out T_Liste_Series);

function Choisir_Terrain_A_Construire (case_depart,case_fin : in T_Cases)
                             return T_Terrains;

end Construction;
