# Makefile for FastFood Restaurant Simulation Project
# C++ project from 1991-1994

CXX = clang++
CXXFLAGS = -std=c++98 -Wno-deprecated -Wno-write-strings -fpermissive
TARGET = fastfood
SOURCES = FASTFOOD.CPP RESTAURA.CPP SERVEUR.CPP FILEATTE.CPP TEMPS.CPP \
          ARRIVEEC.CPP LIBERATI.CPP OBJECT.CPP RANDOM.CPP \
          ORDEREDC.CPP INDEXEDC.CPP SORTEDCO.CPP

OBJECTS = $(SOURCES:.CPP=.o)

# Default target
all: $(TARGET)

# Link the executable
$(TARGET): $(OBJECTS)
	$(CXX) $(OBJECTS) -o $(TARGET)

# Compile source files
%.o: %.CPP
	$(CXX) $(CXXFLAGS) -c $< -o $@

# Clean build artifacts
clean:
	rm -f $(OBJECTS) $(TARGET)

# Install (copy to /usr/local/bin)
install: $(TARGET)
	cp $(TARGET) /usr/local/bin/

# Uninstall
uninstall:
	rm -f /usr/local/bin/$(TARGET)

# Run the program
run: $(TARGET)
	./$(TARGET)

# Show help
help:
	@echo "Available targets:"
	@echo "  all       - Build the fastfood simulation program"
	@echo "  clean     - Remove build artifacts"
	@echo "  install   - Install to /usr/local/bin"
	@echo "  uninstall - Remove from /usr/local/bin"
	@echo "  run       - Build and run the program"
	@echo "  help      - Show this help message"

.PHONY: all clean install uninstall run help 