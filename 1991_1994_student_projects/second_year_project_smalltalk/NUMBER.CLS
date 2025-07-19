
Magnitude subclass: #Number
  instanceVariableNames: ''
  classVariableNames: ''
  poolDictionaries: '' !

!Number methods !

afficher

" Affiche un nombre dans la fenetre Transcript "

self asString afficher!

asString

"transforme un nombre en une chaine de caracteres
 que l''on pourra facilement afficher par le message
 afficher qui s''applique aux instances du type String "

| aStream |

    aStream:= WriteStream on: ''.
    self printOn: aStream.

^aStream contents!

