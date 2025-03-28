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

# run the tools
# Each line of a mke file is ran in its own shell so cd by itself will never change directories.
# https://unix.stackexchange.com/questions/150786/why-current-directory-doesnt-change-in-makefile
gen:
	cd bin/ && java  com/craftinginterpreters/tool/GenerateAst ../src/com/craftinginterpreters/lox/

# Clean compiled files
clean:
	rm -rf $(OUT_DIR)
