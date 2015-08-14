#include <string>
#include <vector>
using namespace std;

#include <assert.h>
#include <errno.h>
#include <libgen.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <wait.h>

#include "auxlib.h"
#include "astree.h"
#include "stringset.h"
#include "lyutils.h"

const string CPP = "/usr/bin/cpp";
const size_t LINESIZE = 1024;

void fill_string_table() {
    //Reads yyin, fills stringtable
    unsigned token_type;
    while((token_type = yylex())){
        if (token_type == YYEOF)
            break;
    }
}

char* append_ext (char* filename, string ext) {
   char out_fname[1024];
   strcpy (out_fname, basename (filename));
   auto dot_index = strrchr (out_fname, '.');
   *dot_index = '\0';
   strcat (out_fname, ext.c_str());
   return (strdup (out_fname));
}

void scan_opts (int argc, char** argv) {
   int option;
   opterr = 0;
   string cpp_command = CPP;
   for(;;) {
      option = getopt (argc, argv, "ly@:D:");
      if (option == EOF) break;
      switch (option) {
         case 'D': cpp_command = cpp_command + " " + optarg;
                   break;
         case '@': set_debugflags (optarg);  break;
         case 'l': yy_flex_debug = 1;        break;
         case 'y': yydebug = true;           break;
         default:  errprintf ("%:bad option (%c)\n", optopt); break;
      }
   }
   if (optind > argc || argc - optind != 1) {
      errprintf ("Usage: %s [-ly] [-@ flags] [-D string] [filename] \n",
                 get_execname());
      exit (get_exitstatus());
   }

   char* filename = argv[optind];

   if (!strstr (filename, ".oc")) {
      errprintf ("File must be an .oc file\n");
      exit (get_exitstatus());
   }
   FILE* tmp = fopen (filename, "r");
   if (tmp == NULL) {
      errprintf ("File not found\n");
      exit (get_exitstatus());
   }
   fclose (tmp);

   //File parsing
   char* str_fname = append_ext (filename, ".str");
   char* tok_fname = append_ext (filename, ".tok");

   //Dump str to file
   FILE* strfile = fopen (str_fname, "w");
   //Data is dumped to tokfile via scanner and lyutils
   tokfile = fopen (tok_fname, "w");

   cpp_command = cpp_command + " " + filename;
   yyin = popen (cpp_command.c_str(), "r");  
   fill_string_table ();
   fclose (yyin);
   dump_stringset (strfile);
   fclose (strfile);
   fclose (tokfile);

}

int main (int argc, char** argv) {
   set_execname (argv[0]);
   scan_opts (argc, argv);

   return get_exitstatus();
}
