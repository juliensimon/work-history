
afficher

" Affiche une chaine dans la fenetre Transcript "

Transcript show: self;
                 cr.!

asFloat
    "   renvoie le nombre represente par le receveur.
        P.L. 25/5/90"

    | s ent dec n |
    s := self asStream.
    ent := (s upTo: $.) asString asInteger.
    dec := s nextLine asString.
    n := dec size.
    dec := dec asInteger asFloat / (10 raisedTo: n).
    ^ent + dec!

asInteger
        "Answer the integer corresponding to the receiver;
         the receiver is expected to be a pure sequence of
         digits without any other non digit character.
         excepte le signe negatif "
    | answer |
    answer := 0.
    (self at: 1) == $-
        ifTrue: [ self at: 1 put: $0 .
             self do: [ :character |
                   (character isDigit)
                      ifFalse: [^answer].
                   answer := answer * 10 + character digitValue].
             ^ (0 - answer) ]
        ifFalse: [
             self do: [ :character |
                   (character isDigit)
                      ifFalse: [^answer].
                   answer := answer * 10 + character digitValue].
             ^answer ]!

asNumber
    "   renvoie le nombre represente par le receveur.
        P.L. 25/5/90"

    ((self indexOf: $.) = 0)
        ifFalse: [^self asFloat]
        ifTrue:  [^self asInteger]!

ouiOuNon

" Sasit au clavier une reponse et retourne:
    - true si elle est affirmative
    - false dans le cas contraire "

|choix |

    choix := Prompter prompt: self default: ''.

^choix asLowerCase = 'o'!


saisirParDefaut: aString

^Prompter prompt: self default: aString!

