
.SUFFIXES:    .bib .dvi .fig .ps .tex


.fig.ps:
	fig2dev -Lps $*.fig > $*.ps
	
.dvi.ps:
	dvips -D600 $*.dvi -o $*.ps

.tex.dvi:
	latex $*.tex 
	bibtex $*
	latex $*.tex
	latex $*.tex


all:	journees.ps




journees.dvi:	journees.tex distributed.bib mach.bib masix.bib abbrev.bib \
		masix2.ps

clean:
	rm -f *.aux *.dvi *.ps *.log *.bbl *.blg *.bib.bak.* *.fd *.pdf
	rm -f masix2_*.ps
	rm -f *.gif

