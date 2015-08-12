// $Id: inssort.h,v 1.1 2012-12-03 02:15:21-08 - - $
//
// Blase Maglaya
// bmaglaya@unix.ic.ucsc.edu
//
// Header file for generic sorting function with void*

#ifndef __INSSORT_H__
#define __INSSORT_H__

void inssort (void *base,
              size_t nelem,
              size_t size,
              int (*compar) (const void *, const void *));

#endif
