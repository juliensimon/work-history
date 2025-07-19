
FixedSizeCollection variableSubclass: #Array
  instanceVariableNames: ''
  classVariableNames: ''
  poolDictionaries: '' !

!Array class methods ! !


!Array methods !
 !

printOn: aStream
        "Append the ASCII representation
         of the receiver to aStream."
    (RecursiveSet includes: self)
        ifTrue: [^self printRecursionOn: aStream].
    RecursiveSet add: self.
    aStream nextPut: $(.
    1 to: self size - 1 do: [ :element |
        (self at: element) printOn: aStream.
        aStream space].
    self isEmpty
        ifFalse: [
            self last printOn: aStream].
    aStream nextPut: $).
    RecursiveSet remove: self ifAbsent: []!

storeOn: aStream
        "Append the ASCII representation of the
         receiver to aStream from which the
         receiver can be reinstantiated."
    | size index firstTime |
    (RecursiveSet includes: self)
        ifTrue: [
            RecursiveSet := Set new.
            ^self error: 'recursive storeOn: not allowed'].
    RecursiveSet add: self.
    size := self size.
    aStream
        nextPutAll: '((';
        nextPutAll: self class name;
        nextPutAll: ' new: '.
    size printOn: aStream.
    aStream nextPut: $).
    index := 1.
    firstTime := true.
    [index <= size]
        whileTrue: [
            firstTime
                ifFalse: [aStream nextPut: $;].
            firstTime := false.
            aStream nextPutAll: 'at: '.
            index printOn: aStream.
            aStream nextPutAll: ' put: '.
            (self at: index) storeOn: aStream.
            index := index + 1].
    firstTime
        ifFalse: [aStream nextPutAll: ';yourself'].
    aStream nextPut: $).
    RecursiveSet remove: self ifAbsent: []! !
