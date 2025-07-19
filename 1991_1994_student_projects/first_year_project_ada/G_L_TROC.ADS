
with NORMALISATION, TYPES;
use NORMALISATION, TYPES;

package G_L_TROC is

   type T_TROC is
      record
         J1, J2 : T_JOUEUR;
         T1, T2 : T_PROPRIETES;
      end record;
   troc_null : t_troc := (transparent,transparent,belleville,belleville);
     -- ce troc_null est defini pour faciliter les comparaisons plus tard;

   procedure AJOUT_TROC (TROC : in T_TROC);
   function NBE_TROC (TROC : in T_TROC) return NATURAL;
   procedure DETRUIT_TROC (TROC : in T_TROC);
   procedure sauvegarder_troc( nom : in string);
   procedure restaurer_troc (nom : in string);
end G_L_TROC;
