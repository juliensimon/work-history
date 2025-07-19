

with NORMALISATION, TYPES, UNCHECKED_DEALLOCATION,direct_io;
use NORMALISATION, TYPES;

package body G_L_TROC is

   type T_CELLULE_TROC;
   type LIEN_CELLULE_TROC is access T_CELLULE_TROC;
   type T_CELLULE_TROC is
      record
	 DATA : T_TROC;
	 NBE  : NATURAL;
	 SUCC : LIEN_CELLULE_TROC;
      end record;
  package iotroc is new direct_io(t_cellule_troc);
   --******************************variable global au pakage******************

   TETE_LISTE_TROC : LIEN_CELLULE_TROC;
      -- Le debut de la liste, visible par toutes les procedures mais par par
      -- les autres pakages.

   --**************************************************************************
   --* la fonction Nbe_Troc donne le nobre d'essai deja tente pour un troc    *
   --*elle retourne 0 si celui-ci n'a jamais ete propose.                     *
   --*ENTREE    : le troc a tester du type T_TROC                             *
   --*SORTIE    : le nombre d'essais deja fait (INTEGER)                      *
   --**************************************************************************

   function NBE_TROC (TROC : in T_TROC) return NATURAL is
      PTR : LIEN_CELLULE_TROC := TETE_LISTE_TROC;
      V_R : INTEGER           := 0;
   begin
      while (PTR /= null) and (V_R = 0) loop
	 if PTR.all.DATA = TROC then
	    V_R := PTR.all.NBE;
         end if;
	 PTR := PTR.all.SUCC;
      end loop;
      return V_R;
   end NBE_TROC;

   --************************************************************************
   --* La procedure Ajout_Troc augmente le nombre d'essai enffectuer sur un *
   --*troc de un ou le rajout a la liste si celui-ci n'a jamais ete tente.  *
   --*ENTREE  : le troc a ajouter                                           *
   --*SORTIE : aucune visible de l'exterieur                                *
   --************************************************************************

   procedure AJOUT_TROC (TROC : in T_TROC) is
      PTR    : LIEN_CELLULE_TROC := TETE_LISTE_TROC;
      NEWPTR : LIEN_CELLULE_TROC;
   begin
      if NBE_TROC (TROC) = 0 then
	 NEWPTR := new T_CELLULE_TROC'(TROC, 1, PTR);
	 ptr := newptr;
	   -- on insere le nouveau troc en tete de liste.
      else
	 while PTR.all.DATA /= TROC loop
	    PTR := PTR.all.SUCC;
         end loop;
	 PTR.all.NBE := PTR.all.NBE + 1;
      end if;
   end AJOUT_TROC;
   --************************************************************************
   --* La procedrue Detruit_Troc sert a eliminer un troc de la liste        *
   --*ENTREE   : le troc a eliminer                                         *
   --*SORTIE   : rien de visible a l'exterieur                              *
   --************************************************************************

   procedure DETRUIT_TROC (TROC : in T_TROC) is
      procedure U_D is new UNCHECKED_DEALLOCATION (T_CELLULE_TROC, LIEN_CELLULE_TROC);

      PTRPRED, PTR : LIEN_CELLULE_TROC := TETE_LISTE_TROC;

   begin
      while (PTR /= null) and (PTR.all.DATA /= TROC) loop
         PTRPRED := PTR;
	 PTR     := PTR.all.SUCC;
      end loop;
	-- ptr point maintenant sur le troc a eliminer, si le troc specifier
	-- n'est pas dans la liste, ptr vaut null;
      if PTR /= null then
	 PTRPRED.all.SUCC := PTR.all.SUCC;
	 U_D (PTR);
	 -- on a eliminer le troc designe.
      end if;
   end DETRUIT_TROC;

   --************************************************************************
   --* La procedure sauvegarder_troc sauvegarde la liste des trocs dans un   *
   --* fichier a acces direct dont le nom est donne en entree               *
   --*ENTREE : nom : le nom du fichier                                      *
   --************************************************************************
   procedure sauvegarder_troc (nom : in string) is
      nom_fichier_t : string(1..nom'length) := nom & ".S_T";
      ref_fichier : iotroc.file_type;
      ptr : lien_cellule_troc := tete_liste_troc;
      index : iotroc.positive_count;
   begin
      iotroc.create (ref_fichier,iotroc.inout_file,nom_fichier_t);
      while ptr /= null loop
	 index := iotroc.positive_count(integer(index)+1);
	 iotroc.write (ref_fichier,ptr.all,index);
	 ptr := ptr.all.succ;
      end loop;
      iotroc.close (ref_fichier);
   end sauvegarder_troc;

   --************************************************************************
   --* La procedure restaurer_troc charge la liste des trocs d'un fichier   *
   --* a acces direct dont le nom est donne en entree                       *
   --*ENTREE : nom : le nom du fichier                                      *
   --************************************************************************
   procedure restaurer_troc (nom : in string) is
      nom_fichier_t : string(1..nom'length+4) := nom & ".S_T";
      ref_fichier : iotroc.file_type;
      taille : iotroc.count ;
      cellule_troc : t_cellule_troc;
      ptr : lien_cellule_troc := tete_liste_troc;
      ptr_tampon : lien_cellule_troc;
   begin
      while ptr /= null loop
	 ptr_tampon := ptr.all.succ;
	 detruit_troc(ptr.all.data);
	 ptr := ptr_tampon;
      end loop;
      iotroc.open (ref_fichier,iotroc.inout_file,nom_fichier_t);
      taille := iotroc.size(ref_fichier);
      if integer(taille) /= 0 then
	 for pos in 1.. taille loop
	    iotroc.read(ref_fichier,cellule_troc,pos);
	    if ptr /= null then
	       ptr.all.succ := new t_cellule_troc'(cellule_troc);
	    else
	       ptr :=new t_cellule_troc'(cellule_troc);
	    end if;
	 end loop;
      end if;
      iotroc.close(ref_fichier);
   end restaurer_troc;

end G_L_TROC;



