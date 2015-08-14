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
#include "stringset.h"

const string CPP = "/usr/bin/cpp";
const size_t LINESIZE = 1024;

char *remove_ext (char* mystr, char dot, char sep) {
    char *retstr, *lastdot, *lastsep;

    // Error checks and allocate string.

    if (mystr == NULL)
        return NULL;
    if ((retstr = (char*) malloc (strlen (mystr) + 1)) == NULL)
        return NULL;

    // Make a copy and find the relevant characters.

    strcpy (retstr, mystr);
    lastdot = strrchr (retstr, dot);
    lastsep = (sep == 0) ? NULL : strrchr (retstr, sep);

    // If it has an extension separator.

    if (lastdot != NULL) {
        // and it's before the extenstion separator.

        if (lastsep != NULL) {
            if (lastsep < lastdot) {
                // then remove it.

                *lastdot = '\0';
            }
        } else {
            // Has extension separator with no path separator.

            *lastdot = '\0';
        }
    }

    // Return the modified string.

    return retstr;
}

char* stringcat(char *s1, char *s2) {
    size_t len1 = strlen(s1);
    size_t len2 = strlen(s2);
    char *result = (char*) malloc(len1+len2+1);//+1 for the zero-terminator
    assert (result != NULL);
    memcpy(result, s1, len1);
    memcpy(result+len1, s2, len2+1);//+1 to copy the null-terminator
    return result;
}

// Chomp the last character from a buffer if it is delim.
void chomp (char* string, char delim) {
   size_t len = strlen (string);
   if (len == 0) return;
   char* nlpos = string + len - 1;
   if (*nlpos == delim) *nlpos = '\0';
}

// Run cpp against the lines of the file.
void cpplines (FILE* pipe, char* filename) {
   int linenr = 1;
   char inputname[LINESIZE];
   strcpy (inputname, filename);
   for (;;) {
      char buffer[LINESIZE];
      char* fgets_rc = fgets (buffer, LINESIZE, pipe);
      if (fgets_rc == NULL) break;
      chomp (buffer, '\n');
      //printf ("%s:line %d: [%s]\n", filename, linenr, buffer);
      // http://gcc.gnu.org/onlinedocs/cpp/Preprocessor-Output.html
      int sscanf_rc = sscanf (buffer, "# %d \"%[^\"]\"",
                              &linenr, filename);
      if (sscanf_rc == 2) {
         //printf ("DIRECTIVE: line %d file \"%s\"\n", linenr, filename);
         continue;
      }
      char* savepos = NULL;
      char* bufptr = buffer;
      for (int tokenct = 1;; ++tokenct) {
         char* token = strtok_r (bufptr, " \t\n", &savepos);
         bufptr = NULL;
         if (token == NULL) break;
         const string* str = intern_stringset (token);
         DEBUGF ('m', "intern (\"%s\") returned %p->\"%s\"\n",
                 token, str, str->c_str());
         //printf ("token %d.%d: [%s]\n",
         //        linenr, tokenct, token);
      }
      ++linenr;
   }
}

void scan_opts (int argc, char** argv) {
   int option;
   opterr = 0;
   //yy_flex_debug = 0;
   //yydebug = 0;
   string cpp_command = CPP;
   for(;;) {
      option = getopt (argc, argv, "ly@:D:");
      if (option == EOF) break;
      switch (option) {
         case 'D': cpp_command = cpp_command + " " + optarg;
                   break;
         case '@': set_debugflags (optarg);  break;
         case 'l': break;
         case 'y': break;
         default:  errprintf ("%:bad option (%c)\n", optopt); break;
      }
   }
   if (optind > argc || argc - optind != 1) {
      errprintf ("Usage: %s [-ly] [-@ flags] [-D string] [filename] \n",
                 get_execname());
      exit (get_exitstatus());
   }

   char* filename = argv[optind];

   char* pExt = strrchr (filename, '.');
   if (pExt == NULL || strcmp (pExt, ".oc") != 0) {
      errprintf ("File must be of type .oc\n");
      exit (get_exitstatus());
   }

   cpp_command = cpp_command + " " + filename;
   FILE* pipe = popen (cpp_command.c_str(), "r");

   if (pipe == NULL) {
      syserrprintf (cpp_command.c_str());
      exit (get_exitstatus());
   } else {
      cpplines (pipe, filename);
      int pclose_rc = pclose (pipe);
      eprint_status (cpp_command.c_str(), pclose_rc);
     
      char* outFile = remove_ext (filename, '.', '/');
      outFile = strcat (outFile, ".str");

      FILE* pFile = fopen (outFile, "w");
      dump_stringset (pFile);
      fclose (pFile);
      free (outFile);
   }
}

int main (int argc, char** argv) {
   set_execname (argv[0]);
   scan_opts (argc, argv);

   return get_exitstatus();
}
