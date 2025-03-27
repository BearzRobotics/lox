package com.craftinginterpreters.lox;

// I'm not yet really sure what half of these do.
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Cool I didn't know how to do an import in java
import static com.craftinginterpreters.lox.TokenType.*;

class Scanner {
    private final String source; 
    private final List<Token> tokens = new ArrayList<>();


    // start and current are offsets into the string
    private int start = 0; // first character in the lexeme being parsed
    private int current = 0; // character currently being considered
    private int line = 0; // tracks what line current is on

        // set our keywords

    private static final Map<String, TokenType> keywords;

    static {
        keywords = new HashMap<>();
        keywords.put("and",     AND);
        keywords.put("class",   CLASS);
        keywords.put("else",    ELSE);
        keywords.put("false",   FALSE);
        keywords.put("for",     FOR);
        keywords.put("fun",     FUN);
        keywords.put("if",      IF);
        keywords.put("nil",     NIL);
        keywords.put("or",      OR);
        keywords.put("print",   PRINT);
        keywords.put("return",  RETURN);
        keywords.put("super",   SUPER);
        keywords.put("this",    THIS);
        keywords.put("true",    TRUE);
        keywords.put("var",     VAR);
        keywords.put("while",   WHILE);
    }

    Scanner(String source) {
        this.source = source;
    }

    List<Token> scanTokens() {
        while (!isAtEnd()) {
            // we are at the beginning of the next lexeme.
            start = current;
            scanToken();
        }

        tokens.add(new Token(EOF, "", null, line));
        return tokens;
    }

    private void scanToken() {
        char c = advance();
        switch(c) {
            case '(': addToken(LEFT_PARN); break;
            case ')': addToken(RIGHT_PAREN); break;
            case '{': addToken(LEFT_BRACE); break;
            case '}': addToken(RIGHT_BRACE); break;
            case ',': addToken(COMMA); break;
            case '-': addToken(MINUS); break;
            case '+': addToken(PLUS); break;
            case ';': addToken(SEMICOLON); break;
            case '*': if (match('/')) {
                break;  // the match for / looks for /* as well
                        // it keeps going until it finds */
            } else {
                addToken(STAR);
            }
            case '!': addToken(match('=') ? BANG_EQUALS : BANG); break;
            case '=': addToken(match('=') ? EQUAL_EQUAL : EQUAL); break;
            case '<': addToken(match('=') ? LESS_EQUAL : LESS);
            case '>': addToken(match('=') ? GREATER_EQUAL : GREATER); break;
            case '/': if (match('/')) {
                // a comment goes to the end of the line.
                while(peek() != '\n' && !isAtEnd()) advance();
            } else if (match('*')) {
                while(cStyleComments() && !isAtEnd()) advance();
            } else{
                addToken(SLASH);
            }
            case ' ':
            case '\r':
            case '\t':
                // Ignore whitespace
                break;
            // This is great in a language that doesn't support \n in strings,
            // How would I say allow print to do print "Hello\nWorld!".
            // derditer I would escape the \n, but that seems cumbrusme
            case '\n': 
                line++;
            case '"': 
                string(c); 
                break;
            default: 
                if (isDigit(c)) {
                    number();
                } else if (isAlpha(c)) { 
                    identifier();
                } else {
                    Lox.error(line, "Unexpected character."); break;     
                }
        }
    }

    //tells us if we consumed all the characters
    private boolean isAtEnd() {
        return current >= source.length();
    }

    // some helper functions
    // addvance is for input
    private char advance(){
        return source.charAt(current++);
    }

    // addToken for output.
    // I don't understand how we can have two functions named addToken()
    private void addToken(TokenType type){
        addToken(type, null);
    }

    private void addToken(TokenType type, Object literal) {
        String text = source.substring(start, current);
        tokens.add(new Token(type, text, literal, line));
    }

    //Mathc looks ahead at the next character to deterime if we got a set or not
    private boolean match(char expected) {
        if (isAtEnd()) return false; // If were EOF then no match
        // Does this mean when we call match we move ahead one char.
        if (source.charAt(current) != expected) return false;

        // should set the char pass the match.
        // eg. != Switch triggers ! and match = to we return to whatever is pass =
        current++;
        return true;
    }

    private char peek() {
        if (isAtEnd()) return '\0';
        return source.charAt(current);
    }

    // if lox suport escape sequence we would have to deal with them here e.g \n
    private void string(char c) {
        while(peek() != c && !isAtEnd()) {
            if (peek() == '\n') line++;
            advance();
        }

        if(isAtEnd()) {
            Lox.error(line, "unterminated string.");
            return;
        }

        // the closing ".
        advance();
    
        // trim the surrounding quotes
        String value = source.substring(start + 1, current - 1);
        addToken(STRING, value);
    }

    // just like with strings we have a special method for dealing with numbers
    // it is here that we would add support for hex, octal, binrary, etc.
    // 0b010002 = binary
    // 0x034 = hex
    // 0o343 = Octal and so on
    private void number() {
        while (isDigit(peek())) advance();

        // look for a fractional part.
        if (peek() == '.' && isDigit(peekNext())) {
            // consume the "."
            advance();
            while (isDigit(peek())) advance();
        }

        addToken(NUMBER, Double.parseDouble(source.substring(start, current)));
    }
    private char peekNext() {
        if (current + 1 >= source.length()) return '\0';
        return source.charAt(current + 1);
    }
    
    private boolean isForwardSlash(char c) {
        return c == '/';
    }

    private boolean cStyleComments() {
        if (peek() == '*'  && isForwardSlash(peekNext())) {
            return true;
        } 
        return false;
    }

    private boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }

    private void identifier() {
        while (isAlphaNumeric(peek())) advance();

        // checks to see if the identifer matches a keyword
        String text = source.substring(start, current);
        TokenType type = keywords.get(text);
        if (type == null) type = IDENTIFIER;
        addToken(type);
    }

    private boolean isAlpha(char c) {
        return  (c >= 'a' && c <= 'z') ||
                (c >= 'A' && c <= 'Z') ||
                c == '_';
    }

    private boolean isAlphaNumeric(char c) {
        return isAlpha(c) || isDigit(c);
    }
}

