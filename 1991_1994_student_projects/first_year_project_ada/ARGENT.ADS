with Normalisation,Types;
use Normalisation,Types;

package Argent is

procedure Fortune (joueur         : in T_Joueur;
                   argent,capital : out T_Monnaie);

procedure Fortune_Relative (joueur                     : in T_Joueur;
                            rang_argent,rang_capital   : out integer;
                            ecart_argent,ecart_capital : out float);

function Cout_Case (kase   : T_Cases;
                    lancer : T_Lancer_Des) return T_Monnaie;

procedure Risque (joueur                          : in T_joueur;
                  cout_du_lancer_le_plus_probable : out T_Monnaie;
                  cout_du_lancer_le_plus_couteux  : out T_Monnaie;
                  probabilite_de_ce_lancer        : out T_Probabilite);

end Argent;
