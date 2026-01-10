# Java JSON Parser

A custom JSON parser built from scratch in Java to parse and validate JSON strings into Java data structures. This project avoids third-party JSON libraries to provide a clear understanding of how JSON parsing works internally.

## Features

- Parses JSON objects and arrays
- Supports strings, numbers, booleans, and null values
- Handles nested JSON structures
- Validates JSON syntax and reports parsing errors
- Lightweight and dependency-free

## How It Works

1. **Tokenization**  
   The tokenizer scans the input JSON string character by character and converts it into meaningful tokens such as braces, brackets, strings, numbers, and literals.

2. **Parsing**  
   The parser uses recursive descent parsing to process tokens and build the corresponding JSON object tree.

3. **Validation**  
   Syntax errors such as missing brackets, invalid values, or malformed strings are detected during parsing.
