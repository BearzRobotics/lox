JAVAC = javac
JAVA = java
SRC_DIR = src
OUT_DIR = bin

# Find all Java files
SOURCES = $(shell find $(SRC_DIR) -name "*.java")

# Default target: compile everything
all:
	mkdir -p $(OUT_DIR)
	$(JAVAC) -d $(OUT_DIR) $(SOURCES)

# Run the interpreter
run: all
	$(JAVA) -cp $(OUT_DIR) com.craftinginterpreters.lox.Lox

# Clean compiled files
clean:
	rm -rf $(OUT_DIR)
