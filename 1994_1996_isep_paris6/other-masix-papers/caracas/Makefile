#
# Makefile pour compiler cette cochonnerie de these
#

CHAPS	=	article.tex 1a.tex 1b.tex

BIB	=	1.bib abbrev.bib amoeba.bib chorus.bib dos.bib mach.bib masix.bib \
		osf1.bib posix.bib reseaux.bib systemes.bib unix.bib v.bib

ARTICLE	=	article.ps

.SUFFIXES:	.bib .dvi .fig .ps .tex

.dvi.ps:
	dvips -D600 $* -o $*.ps

.fig.ps:
	fig2dev -Lps < $*.fig > $*.ps

.fig.tex:
	fig2dev -Llatex -l2 < $*.fig > $*.tex

all:		$(ARTICLE)

article.dvi:		$(CHAPS) $(FIGS) article.bbl
	latex article
	latex article
	latex article

article.bbl:		$(BIB)
	latex article
	bibtex article

clean:
	# cleanfig
	rm -f *~ *.aux *.dvi *.lof *.log *.toc *.bbl *.blg *.ps

