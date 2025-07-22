#!/bin/bash

# Modernize LaTeX 2.09 files to LaTeX2e
# This script converts old \documentstyle to modern \documentclass

echo "=== Modernizing LaTeX files ==="

# Function to modernize a LaTeX file
modernize_file() {
    local file=$1
    echo "Modernizing $file..."
    
    # Create backup
    cp "$file" "$file.backup"
    
    # Read the original documentstyle line to extract options
    local docstyle_line=$(grep "\\documentstyle" "$file.backup")
    local options=$(echo "$docstyle_line" | sed 's/.*\[\([^]]*\)\].*/\1/')
    
    # Create new documentclass line
    local new_docclass="\\documentclass[12pt,a4paper]{article}"
    
    # Replace documentstyle with documentclass
    sed -i '' "s/\\documentstyle.*{article}/$new_docclass/" "$file"
    
    # Add necessary packages based on original options
    local packages=""
    
    # Add packages after documentclass
    if echo "$options" | grep -q "french"; then
        packages="$packages\n\\usepackage[french]{babel}"
    fi
    
    if echo "$options" | grep -q "english"; then
        packages="$packages\n\\usepackage[english]{babel}"
    fi
    
    if echo "$options" | grep -q "epsf"; then
        packages="$packages\n\\usepackage{graphicx}"
        packages="$packages\n\\usepackage{epstopdf}"
    fi
    
    if echo "$options" | grep -q "fancyheadings"; then
        packages="$packages\n\\usepackage{fancyhdr}"
    fi
    
    if echo "$options" | grep -q "twoside"; then
        packages="$packages\n\\usepackage{twoside}"
    fi
    
    if echo "$options" | grep -q "fleqn"; then
        packages="$packages\n\\usepackage[fleqn]{amsmath}"
    fi
    
    # Add common packages
    packages="$packages\n\\usepackage{amsmath}"
    packages="$packages\n\\usepackage{amsfonts}"
    packages="$packages\n\\usepackage{amssymb}"
    packages="$packages\n\\usepackage{cite}"
    packages="$packages\n\\usepackage{url}"
    packages="$packages\n\\usepackage{geometry}"
    
    # Insert packages after documentclass
    sed -i '' "/\\documentclass/a\\$packages" "$file"
    
    # Replace \epsfbox with \includegraphics
    sed -i '' 's/\\epsfbox{\([^}]*\)\.ps}/\\includegraphics{\1.ps}/g' "$file"
    
    # Replace \epsfxsize and \epsfysize with \resizebox
    # This is a bit more complex, we'll handle it case by case
    
    echo "  Modernized $file"
}

# Modernize all LaTeX files
for dir in big_one caracas etat net osf subject; do
    if [ -d "$dir" ]; then
        echo "Processing $dir..."
        cd "$dir"
        
        for tex_file in *.tex; do
            if [ -f "$tex_file" ]; then
                modernize_file "$tex_file"
            fi
        done
        
        cd ..
    fi
done

echo "=== LaTeX modernization complete ===" 