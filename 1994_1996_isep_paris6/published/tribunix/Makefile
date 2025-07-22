

CHAPS	=	article_modern.tex

ARTICLE	=	article_modern.ps article_modern.pdf

.SUFFIXES:	.bib .bbl .dvi .fig .ps .pdf .tex

.dvi.ps:
	dvips $* -o $*.ps

.dvi.pdf:
	dvipdf $* $*.pdf

.fig.ps:
	fig2dev -Lps < $*.fig > $*.ps

.fig.tex:
	fig2dev -Llatex -l2 < $*.fig > $*.tex

.tex.bbl:
	latex $*
	bibtex $*

all:		$(ARTICLE)

article_modern.dvi:	$(CHAPS)
	latex article_modern
	latex article_modern
	latex article_modern

article_modern.ps:	article_modern.dvi
	dvips article_modern -o article_modern.ps

article_modern.pdf:	article_modern.dvi
	dvipdf article_modern.dvi article_modern.pdf

clean:
	# cleanfig
	rm -f *~ *.aux *.dvi *.lof *.log *.toc *.bbl *.blg *.ps *.fd *.gif

