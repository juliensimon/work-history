"Simple CLI test for Squeak"
Transcript clear.
Transcript show: 'Hello from Squeak CLI!'; cr.
Transcript show: 'Testing basic Smalltalk...'; cr.

| testObj |
testObj := Object new.
Transcript show: 'Created object: '; show: testObj; cr.

Transcript show: 'Test completed!'; cr.
Smalltalk quit. 