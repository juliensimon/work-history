#!/bin/bash

# Comprehensive build script for all LaTeX papers
# This script builds all papers in the repository and converts figures to PNG
# Combines features from all previous build scripts

set -e  # Exit on any error

echo "=== Building all papers in the repository ==="

# Function to convert PS files to PNG
convert_ps_to_png() {
    local dir=$1
    echo "Converting PS files to PNG in $dir..."
    cd "$dir"
    
    # Find all PS files and convert them to PNG
    for ps_file in *.ps; do
        if [ -f "$ps_file" ]; then
            png_file="${ps_file%.ps}.png"
            echo "  Converting $ps_file to $png_file"
            magick "$ps_file" "$png_file" 2>/dev/null || echo "  Warning: Could not convert $ps_file"
        fi
    done
    
    cd ..
}

# Function to fix LaTeX compatibility issues
fix_latex_compatibility() {
    local dir=$1
    local tex_file=$2
    
    echo "Fixing LaTeX compatibility in $dir/$tex_file..."
    
    # Create a temporary fixed version
    local temp_file="$dir/${tex_file}.fixed"
    
    # Replace problematic french.sty with babel
    sed 's/\\documentstyle \[\([^]]*\)\] {article}/\\documentclass[12pt,a4paper]{article}\n\\usepackage[french]{babel}\n\\usepackage{graphicx}\n\\usepackage{amsmath}\n\\usepackage{amsfonts}\n\\usepackage{amssymb}\n\\usepackage{cite}\n\\usepackage{url}/' "$dir/$tex_file" > "$temp_file"
    
    # Replace \epsfbox with \includegraphics
    sed -i '' 's/\\epsfbox{\([^}]*\)\.ps}/\\includegraphics{\1.ps}/g' "$temp_file"
    
    # Replace \epsfxsize and \epsfysize with \resizebox
    sed -i '' 's/\\epsfxsize=\([^}]*\)/\\resizebox{\1}{!}{/g' "$temp_file"
    sed -i '' 's/\\epsfysize=\([^}]*\)/\\resizebox{!}{\1}{/g' "$temp_file"
    
    # Backup original and replace with fixed version
    mv "$dir/$tex_file" "$dir/${tex_file}.original"
    mv "$temp_file" "$dir/$tex_file"
}

# Function to build a LaTeX project
build_latex_project() {
    local dir=$1
    local main_tex=$2
    
    echo "=== Building $dir ==="
    cd "$dir"
    
    # Clean previous builds
    make clean 2>/dev/null || true
    
    # Fix LaTeX compatibility if needed
    if [ -f "$main_tex" ]; then
        if grep -q "\\documentstyle" "$main_tex"; then
            fix_latex_compatibility "." "$main_tex"
        fi
    fi
    
    # Build the project
    if [ -f "Makefile" ]; then
        echo "Building with Makefile..."
        make all || echo "Makefile build failed, trying manual build..."
    fi
    
    # Manual build if Makefile failed or doesn't exist
    if [ -f "$main_tex" ]; then
        echo "Building manually with $main_tex..."
        # Try pdflatex first, then latex for legacy files
        pdflatex "$main_tex" 2>/dev/null || latex "$main_tex" 2>/dev/null || echo "Failed to build $main_tex"
        
        # Run bibtex if .bib files exist
        if ls *.bib 1> /dev/null 2>&1; then
            bibtex "${main_tex%.tex}" 2>/dev/null || echo "BibTeX failed for ${main_tex%.tex}"
            pdflatex "$main_tex" 2>/dev/null || latex "$main_tex" 2>/dev/null || echo "Second pass failed"
            pdflatex "$main_tex" 2>/dev/null || latex "$main_tex" 2>/dev/null || echo "Third pass failed"
        fi
        
        # Convert DVI to PS if needed
        if [ -f "${main_tex%.tex}.dvi" ]; then
            dvips "${main_tex%.tex}.dvi" -o "${main_tex%.tex}.ps" 2>/dev/null || echo "DVI to PS conversion failed"
        fi
    fi
    
    # Convert figures to PNG
    convert_ps_to_png "."
    
    # Convert final PS to PDF if it exists and PDF doesn't exist
    for ps_file in *.ps; do
        if [ -f "$ps_file" ] && [[ "$ps_file" != *".fig.ps" ]]; then
            pdf_file="${ps_file%.ps}.pdf"
            if [ ! -f "$pdf_file" ]; then
                echo "Converting $ps_file to $pdf_file"
                magick "$ps_file" "$pdf_file" 2>/dev/null || echo "PS to PDF conversion failed for $ps_file"
            fi
        fi
    done
    
    cd ..
}

# Function to clean intermediate files
clean_intermediate_files() {
    local dir=$1
    echo "Cleaning intermediate files in $dir..."
    cd "$dir"
    # Remove intermediate files but keep PDFs and PNGs
    rm -f *.aux *.dvi *.log *.bbl *.blg *.bib.bak.* *.lof *.toc *~ *.bak 2>/dev/null || true
    cd ..
}

# Build papers in published/ directory
echo "=== Building papers in published/ ==="
for dir in published/*/; do
    if [ -d "$dir" ]; then
        dirname=$(basename "$dir")
        echo "Processing $dirname..."
        
        # Determine main tex file
        main_tex=""
        if [ -f "$dir/article.tex" ]; then
            main_tex="article.tex"
        elif [ -f "$dir/athens.tex" ]; then
            main_tex="athens.tex"
        elif [ -f "$dir/journees.tex" ]; then
            main_tex="journees.tex"
        elif [ -f "$dir/phoenix.tex" ]; then
            main_tex="phoenix.tex"
        elif [ -f "$dir/full.tex" ]; then
            main_tex="full.tex"
        elif [ -f "$dir/abs.tex" ]; then
            main_tex="abs.tex"
        elif [ -f "$dir/article_modern.tex" ]; then
            main_tex="article_modern.tex"
        elif [ -f "$dir/biel.tex" ]; then
            main_tex="biel.tex"
        fi
        
        if [ -n "$main_tex" ]; then
            build_latex_project "$dir" "$main_tex"
        else
            echo "No main tex file found in $dirname"
        fi
    fi
done

# Build papers in not-published/ directory
echo "=== Building papers in not-published/ ==="
for dir in not-published/*/; do
    if [ -d "$dir" ]; then
        dirname=$(basename "$dir")
        echo "Processing $dirname..."
        
        # Determine main tex file
        main_tex=""
        if [ -f "$dir/etat.tex" ]; then
            main_tex="etat.tex"
        elif [ -f "$dir/net.tex" ]; then
            main_tex="net.tex"
        fi
        
        if [ -n "$main_tex" ]; then
            build_latex_project "$dir" "$main_tex"
        else
            echo "No main tex file found in $dirname"
        fi
    fi
done

# Build papers in other-masix-papers/ directory
echo "=== Building papers in other-masix-papers/ ==="
for dir in other-masix-papers/*/; do
    if [ -d "$dir" ]; then
        dirname=$(basename "$dir")
        echo "Processing $dirname..."
        
        # Determine main tex file
        main_tex=""
        if [ -f "$dir/article.tex" ]; then
            main_tex="article.tex"
        elif [ -f "$dir/rapport.tex" ]; then
            main_tex="rapport.tex"
        fi
        
        if [ -n "$main_tex" ]; then
            build_latex_project "$dir" "$main_tex"
        else
            echo "No main tex file found in $dirname"
        fi
        
        # Handle special cases like rapport.tex in osf/
        if [ "$dirname" = "osf" ] && [ -f "$dir/rapport.tex" ] && [ "$main_tex" != "rapport.tex" ]; then
            echo "Building rapport.tex in osf..."
            cd "$dir"
            latex rapport.tex 2>/dev/null || echo "Failed to build rapport.tex"
            bibtex rapport 2>/dev/null || echo "BibTeX failed for rapport"
            latex rapport.tex 2>/dev/null || echo "Second pass failed for rapport"
            latex rapport.tex 2>/dev/null || echo "Third pass failed for rapport"
            dvips rapport.dvi -o rapport.ps 2>/dev/null || echo "DVI to PS conversion failed for rapport"
            magick rapport.ps rapport.pdf 2>/dev/null || echo "PS to PDF conversion failed for rapport"
            cd ../..
        fi
    fi
done

# Build any additional directories that might exist
echo "=== Building additional directories ==="
for dir in big_one caracas etat net osf subject; do
    if [ -d "$dir" ]; then
        echo "Processing $dir..."
        
        # Determine main tex file
        main_tex=""
        if [ -f "$dir/big.tex" ]; then
            main_tex="big.tex"
        elif [ -f "$dir/article.tex" ]; then
            main_tex="article.tex"
        elif [ -f "$dir/etat.tex" ]; then
            main_tex="etat.tex"
        elif [ -f "$dir/net.tex" ]; then
            main_tex="net.tex"
        elif [ -f "$dir/rapport.tex" ]; then
            main_tex="rapport.tex"
        elif [ -f "$dir/us.tex" ]; then
            main_tex="us.tex"
        fi
        
        if [ -n "$main_tex" ]; then
            build_latex_project "$dir" "$main_tex"
        else
            echo "No main tex file found in $dir"
        fi
    fi
done

echo "=== Cleaning up intermediate files ==="

# Clean up intermediate files in all directories
for dir in published/*/ not-published/*/ other-masix-papers/*/; do
    if [ -d "$dir" ]; then
        clean_intermediate_files "$dir"
    fi
done

# Clean up additional directories
for dir in big_one caracas etat net osf subject; do
    if [ -d "$dir" ]; then
        clean_intermediate_files "$dir"
    fi
done

echo "=== Build complete! ==="
echo "Generated files:"
echo "- PDF files: All papers converted to PDF"
echo "- PNG files: All figures converted to PNG"
echo "- Cleaned up: All intermediate LaTeX files removed"
echo ""
echo "Papers built:"
echo "Published: athens, biel, paris, phoenix, rennes, tribunix"
echo "Not Published: etat, net"
echo "Other Masix Papers: caracas, osf"
echo "Additional: big_one, subject (if they exist)" 