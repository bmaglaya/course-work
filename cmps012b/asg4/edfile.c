// $Id: edfile.c,v 1.89 2012-11-25 21:59:19-08 - - $
//
// Blase Maglaya
// bmaglaya@unix.ic.ucsc.edu
//
// A simple text editor

#include <assert.h>
#include <libgen.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>

#include "debugf.h"
#include "list.h"

bool want_echo = false;

void badline (int stdincount, char *stdinline) {
   fflush (NULL);
   fprintf (stderr, "Bad input line %d: %s\n", stdincount, stdinline);
   fflush (NULL);
}

void print_current (list_ref list, char *buff) {
   assert (is_list (list));
   if (strlen (buff) > 1) {
      printf ("Invalid input\n");
      return;
   }
   if (empty_list (list)) {
      printf ("Empty\n");
      return;
   }
   printf ("%s\n", viewcurr_list (list));
}

void setcurr_last (list_ref list, char *buff) {
   assert (is_list (list));
   if (strlen (buff) > 1) {
      printf ("Invalid input\n");
      return;
   }
   if (empty_list (list)) {
      printf ("Empty\n");
      return;
   }
   setmove_list (list, MOVE_LAST);
   print_current (list, buff);   
}

void setcurr_head (list_ref list, char *buff) {
   assert (is_list (list));
   if (strlen (buff) > 1) {
      printf ("Invalid input\n");
      return;
   }
   if (empty_list (list)) {
      printf ("Empty\n");
      return;
   }
   setmove_list (list, MOVE_HEAD);
   print_current (list, buff);
}

void setcurr_prev (list_ref list, char *buff) {
   assert (is_list (list));
   if (strlen (buff) > 1) {
      printf ("Invalid input\n");
      return;
   }
   if (empty_list (list)) {
      printf ("Empty\n");
      return;
   }
   setmove_list (list, MOVE_PREV);
   print_current (list, buff);
}

void setcurr_next (list_ref list, char *buff) {
   assert (is_list (list));
   if (strlen (buff) > 1) {
      printf ("Invalid input\n");
      return;
   }
   if (empty_list (list)) {
      printf ("Empty\n");
      return;
   }
   setmove_list (list, MOVE_NEXT);
   print_current (list, buff);
}

void print_all (list_ref list, char *buff) {
   assert(is_list (list));
   if (strlen (buff) > 1) {
      printf ("Invalid input\n");
      return;
   }
   if (empty_list (list)) {
      printf ("Empty\n");
      return;
   }
   setmove_list (list, MOVE_HEAD);
   print_current (list, buff);
   while (setmove_list (list, MOVE_NEXT)) {
      print_current (list, buff);
   }
}

void insert_after (list_ref list, char *newline, char *buf) {
   assert (is_list (list));
   char noopt[1024];
   strcpy (noopt, &buf[1]);
   newline = strdup (noopt);
   assert (newline != NULL);
   insert_list (list, newline);
}

void insert_before (list_ref list, char *newline, char *buf) {
   assert (is_list (list));
   char noopt[1024];
   strcpy (noopt, &buf[1]);
   newline = strdup (noopt);
   assert (newline != NULL);
   setmove_list (list, MOVE_PREV);
   insert_list (list, newline);
}

void delete_line (list_ref list, char *buff) {
   assert (is_list (list));
   if (strlen (buff) > 1) {
      printf ("Invalid input\n");
      return;
   }
   if (empty_list (list)) {
      printf ("Empty\n");
      return;
   }
   delete_list (list); 
}

void readin_file (list_ref list, char *newline, char *buf) {
   assert (is_list (list));
   char gline[1024];
   strcpy (gline, &buf[1]);
   newline = strdup (gline);
   assert (newline != NULL);
   setmove_list (list, MOVE_LAST);
   int lines = putinlist (list, newline) - 1;
   printf ("Lines read in: %d\n", lines);
}

void editfile (list_ref list) {
   char stdinline[1024];
   int stdincount = 0;
   for(;; ++stdincount) {
      printf ("%s: ", Exec_Name);
      char *linepos = fgets (stdinline, sizeof stdinline, stdin);
      if (linepos == NULL) break; 
      if (want_echo) printf ("%s", stdinline);
      linepos = strchr (stdinline, '\n');
      if (linepos == NULL || stdinline[0] == '\0') {
         badline (stdincount, stdinline);
      } else {
         *linepos = '\0';
         switch (stdinline[0]) {
            case '$': setcurr_last (list, stdinline); break;
            case '*': print_all (list, stdinline); break;
            case '.': print_current (list, stdinline); break;
            case '0': setcurr_head (list, stdinline); break;
            case '<': setcurr_prev (list, stdinline); break;
            case '>': setcurr_next (list, stdinline); break;
            case '@': debugdump_list (list); break;
            case 'a': insert_after (list, linepos, stdinline); break;
            case 'd': delete_line (list, stdinline); break;
            case 'i': insert_before (list, linepos, stdinline); break;
            case 'r': readin_file (list, linepos, stdinline); break;
            case 'w': save_file (list, linepos, stdinline); break;
            default : badline (stdincount, stdinline);
         }
      }
   }
   printf ("^D\n");
}


void usage_exit() {
   fflush (NULL);
   fprintf (stderr, "Usage: %s filename\n", Exec_Name);
   fflush (NULL);
   exit (EXIT_FAILURE);
}

int putinlist (list_ref list, char *filename) {
   FILE *input = fopen (filename, "r");
   int linenr;
   if (input == NULL) {
      fflush (NULL);
      fprintf (stderr, "%s: %s: Failed to open\n", Exec_Name, filename);
      fflush (NULL);
      Exit_Status = EXIT_FAILURE;
      return 1;
   } else {
      char buffer[1024];
      for (linenr = 1; ; ++linenr) {
         char *linepos = fgets (buffer, sizeof buffer, input);
         if (linepos == NULL) break;
         linepos = strchr (buffer, '\n');
         if (linepos == NULL) {
            fflush (NULL);
            fprintf (stderr, "%s: %s[%d]: unterminated line\n",
                     Exec_Name, filename, linenr);
            fflush (NULL);
            Exit_Status = EXIT_FAILURE;
         } else {
            *linepos = '\0';
         }
         linepos = strdup (buffer);
         assert (linepos != NULL);
         insert_list (list, linepos);
      }
      fclose (input);
   }
   return linenr;
}

void save_file (list_ref list, char *newline, char *filename) {
   char gline[1024];
   strcpy (gline, &filename[1]);
   newline = strdup (gline);
   assert (newline != NULL);

   FILE *saveto;
   saveto = fopen (newline, "w+");
   int linenum;
   if (saveto == NULL) {
      fflush (NULL);
      fprintf (stderr,"%s: %s: Failed to open\n", Exec_Name, newline);
      fflush (NULL);
      Exit_Status = EXIT_FAILURE;
      return;
   } else {
      setmove_list (list, MOVE_HEAD);
      fprintf (saveto, "%s\n", viewcurr_list (list));
      for (linenum = 1; setmove_list (list, MOVE_NEXT) == true 
           ; ++linenum) {
         fprintf (saveto, "%s\n", viewcurr_list (list));
      }
   }
   fclose (saveto);
   printf ("File saved\n");
   printf ("%d lines written\n", linenum);  
}
   
int main (int argc, char **argv) {
   Exec_Name = basename (argv[0]);
   if (argc != 2) usage_exit();
   want_echo = ! (isatty (fileno (stdin)) && isatty (fileno (stdout)));
   list_ref list = new_list();
   putinlist (list, argv[1]); 
   editfile (list);
   while (!empty_list (list)) {
      delete_list (list);
   }
   free_list (list); 
   list = NULL;
   return Exit_Status;
}
