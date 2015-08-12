//# $Id: inssort.c,v 1.3 2012-12-03 02:15:09-08 - - $
//
// Blase Maglaya
// bmaglaya@unix.ic.ucsc.edu
//
// Generic sorting function with void*

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "inssort.h"

void inssort (void *base, size_t nelem, size_t size,
              int (*compar) (const void *, const void *)) {

  for (int sorted = 1; sorted < nelem; ++sorted) {
    int slot = sorted;
    const void *copy = malloc (size);
    memcpy (copy, (char*) base + slot * size, size);
    for (; slot > 0; --slot) {
      int cmp = compar (copy, (char*) base + (slot - 1) * size);
      if (cmp > 0) break;
      memcpy ((char*) base + slot * size,
              (char*) base + (slot - 1) * size,
              size);
    }
    memcpy ((char*) base + slot * size, copy, size);
    free (copy);
  }

}

