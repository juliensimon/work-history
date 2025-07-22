

.SUFFIXES:    .bib .dvi .fig .ps .tex


.fig.ps:
	fig2dev -Lps $*.fig > $*.ps
	
.dvi.ps:
	dvips -t a4 -D600 $*.dvi -o $*.ps

.tex.dvi:
	latex $*.tex 
	bibtex $*
	latex $*.tex
	latex $*.tex


all:	abs full

abs:	abs.ps

full:	full.ps

abs.dvi: abs.tex

full.dvi:	full.tex masix2.ps name_resolve-fr.ps libs-fr.ps key-local.ps\
key-remote.ps a-b.ps


clean:
	rm -f *.aux *.dvi *.ps *.log *.bbl *.blg *.fd *.pdf *.gif
