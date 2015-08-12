//# $Id: numsort.c,v 1.4 2012-12-03 02:08:17-08 - - $
//
// Blase Maglaya
// bmaglaya@unix.ic.ucsc.edu
//
//

#include <stdio.h>
#include <stdlib.h>
#include <assert.h>
#include <libgen.h>
#include "inssort.h"

int MAX_LENGTH = 1000;

int doub_compar (const void *a, const void *b) {
  if ( *(double*)a - *(double*)b == 0) {
    return 0;
  } else if ( *(double*)a - *(double*)b < 0) {
    return -1;
  } else {
    return 1;
  }
}

int main (int argc, char **argv) {
  char *progname = basename (argv[0]);
  char err_buffer[256];
  char err_buf_fmt[16];
  double array[MAX_LENGTH];
  int i = 0;
  sprintf (err_buf_fmt, "%%%lds", sizeof err_buffer - 1);

  for (;;) {
    double num;
    int scancount = scanf ("%lg", &num);

    if (scancount == EOF) {
      break;
    } else if (scancount == 1) {
      array[i] = num;
      if (i >= MAX_LENGTH) break;
      i++;
    } else {
      scancount = scanf (err_buf_fmt, err_buffer);
      assert (scancount == 1);
      printf ("bad number \"%s\"\n", err_buffer);
      break;
    };
  };
  
  inssort (array, i, sizeof (double), doub_compar);

  for (int j = 0; j < i; ++j) {
    printf ("%d: %lf\n", j, array[j]);
  }

  return EXIT_SUCCESS;
}
