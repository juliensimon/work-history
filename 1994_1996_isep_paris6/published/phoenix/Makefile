

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


all:	phoenix

phoenix:	phoenix.ps


phoenix.dvi:	phoenix.tex masix2-us.ps name_resolve.ps libs.ps key-local.ps\
key-remote.ps a-b.ps


clean:
	rm -f *.aux *.dvi *.ps *.log *.bbl *.blg *.fd *.pdf *.gif
