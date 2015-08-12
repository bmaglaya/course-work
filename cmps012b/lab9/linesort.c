//# $Id: linesort.c,v 1.1 2012-12-03 02:15:09-08 - - $
//
// Blase Maglaya
// bmaglaya@unix.ic.ucsc.edu
//
//

#include <stdio.h>
#include <stdlib.h>
#include <libgen.h>
#include <assert.h>
#include <string.h>

#include "inssort.h"

int MAX_LENGTH = 1000;

int string_cmp (const void *a, const void *b) {
  const char **ia = (const char **)a;
  const char **ib = (const char **)b;
  int result = strcmp (*ia, *ib);
  return result;
}

int main (int argc, char **argv) {
  char *progname = basename (argv[0]);
  char buffer[MAX_LENGTH];
  int linenr;
  int i = 0;
  char *array[MAX_LENGTH];

  for (linenr = 1; ; ++linenr) {
    char *gotline = fgets (buffer, sizeof buffer, stdin);
    if (gotline == NULL) break;

    char *nlpos = strchr (buffer, '\n');
    if (nlpos != NULL) {
      *nlpos = '\0';
    } else {
      printf ("unterminated line\n");
    };

    char *line = strdup (buffer);
    array[i] = line;
    if (i >= MAX_LENGTH) break;
    i++;
  };
  
  inssort (array, i ,sizeof (char *), string_cmp);

  for (int j = 0; j < i; ++j) {
    printf ("%d: %s\n", j, array[j]);
    free (array[j]);
  }

  return EXIT_SUCCESS;
}
