%{

#include "auxlib.h"
#include "lyutils.h"

#define YY_USER_ACTION  { scanner_useraction (); }
#define IGNORE(THING)   { }

%}

%option 8bit
%option debug
%option nodefault
%option nounput
%option noyywrap
%option verbose
%option warn
%option ecs

LETTER       [A-Za-z_]
DIGIT        [0-9]
NUMBER       ({DIGIT}+)
IDENT        ({LETTER}({LETTER}|{DIGIT})*)
INVIDENT     ({NUMBER}+{IDENT})
CHARCON      ('([^\\'\n]|\\[\\'"0nt])')
STRINGCON    (\"([^\\"\n]|\\[\\'"0nt])*\")
BADSTRING    (\"[^\n\"\\]*(.|\n))
BADCHAR      ('[\n\'\\]'|'.[^\'])
PREPROC      (#{NUMBER}{STRING})

%%

"void"          { return yylval_token (TOK_VOID); }
"bool"          { return yylval_token (TOK_BOOL); }
"char"          { return yylval_token (TOK_CHAR); }
"int"           { return yylval_token (TOK_INT); }
"string"        { return yylval_token (TOK_STRING); }
"struct"        { return yylval_token (TOK_STRUCT); }
"if"            { return yylval_token (TOK_IF); }
"else"          { return yylval_token (TOK_ELSE); }
"while"         { return yylval_token (TOK_WHILE); }
"return"        { return yylval_token (TOK_RETURN); }
"false"         { return yylval_token (TOK_FALSE); }
"true"          { return yylval_token (TOK_TRUE); }
"null"          { return yylval_token (TOK_NULL); }
"ord"           { return yylval_token (TOK_ORD); }
"chr"           { return yylval_token (TOK_CHR); }
"new"           { return yylval_token (TOK_NEW); }

"#".*           { scanner_include(); }
[ \t]+          { IGNORE (white space) }
\n              { scanner_newline(); }

{NUMBER}        { return yylval_token (TOK_INTCON); }
{IDENT}         { return yylval_token (TOK_IDENT); }
{CHARCON}       { return yylval_token (TOK_CHARCON); }
{STRINGCON}     { return yylval_token (TOK_STRINGCON); }
{INVIDENT}      { scanner_badtoken (yytext); }
{BADCHAR}       { scanner_badtoken (yytext); } 
{BADSTRING}     { scanner_badtoken (yytext); }

"[]"            { return yylval_token (TOK_ARRAY); }
"("             { return yylval_token ('('); }
")"             { return yylval_token (')'); }
"["             { return yylval_token ('['); }
"]"             { return yylval_token (']'); }
"{"             { return yylval_token ('{'); }
"}"             { return yylval_token ('}'); }
";"             { return yylval_token (';'); }
","             { return yylval_token (','); }
"."             { return yylval_token ('.'); }
"="             { return yylval_token ('='); }
"=="            { return yylval_token (TOK_EQ); }
"!="            { return yylval_token (TOK_NE); }
"<"             { return yylval_token ('<'); }
"<="            { return yylval_token (TOK_LE); }
">"             { return yylval_token ('>'); }
">="            { return yylval_token (TOK_GE); }
"+"             { return yylval_token ('+'); }
"-"             { return yylval_token ('-'); }
"*"             { return yylval_token ('*'); }
"/"             { return yylval_token ('/'); }
"%"             { return yylval_token ('%'); }
"!"             { return yylval_token ('!'); }

.               { scanner_badchar (*yytext); }

%%
