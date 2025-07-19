
Bag subclass: #Reponse
  instanceVariableNames: 
    'texteReponse bonneReponse '
  classVariableNames: ''
  poolDictionaries: '' !

!Reponse class methods !

new

" creation d'une nouvelle instance de Reponse
  et initialisation des variables"

|reponse|

    reponse := super new.
    reponse ecrireTexte: '';
            ecrireBonneReponse: false.

    ^reponse! !


!Reponse methods !

afficherReponse

" Affiche la reponse possible "

    texteReponse afficher.!

ecrireBonneReponse: aBoolean

" Affecte a la reponse le booleen:
    - true si elle est exacte
    - false sinon "

    bonneReponse:=aBoolean.!

ecrireTexte: aString

" Affecte au texte de la reponse la chaine passee en parametre "

    texteReponse := aString.!

editerReponse

" Saisit l'enonce et l'exactitude de la reponse
  creee par le professeur "

texteReponse := 'Entrez le texte de la reponse'
                saisirParDefaut: texteReponse.

     'Cette reponse possible est-elle correcte (o/n) ?' ouiOuNon
          ifTrue: [bonneReponse:= true]!

estBonne

" Retourne le booleen true si elle est correcte et false sinon"

    ^bonneReponse!

lireReponse

" Retourne le texte de la reponse "

    ^texteReponse! !
