
Object subclass: #Boolean
  instanceVariableNames: ''
  classVariableNames: ''
  poolDictionaries: '' !

!Boolean methods !

afficher

" permet d'afficher un booleen "

| aStream |

aStream:= WriteStream on: ''.
self printOn: aStream.
aStream  contents afficher.!
