// $Id: list.c,v 1.50 2012-11-25 21:59:19-08 - - $
//
// Blase Maglaya
// bmaglaya@unix.ic.ucsc.edu
//
// Doubly linked-list data structure

#include <assert.h>
#include <stdbool.h>
#include <stdio.h>
#include <stdlib.h>

#include "debugf.h"
#include "list.h"

static char *list_tag = "struct list";
static char *listnode_tag = "struct listnode";

typedef struct listnode *listnode_ref;

struct list {
   //
   // INVARIANT:  Both head and last are NULL or neither are NULL.
   // If neither are null, then following listnode next links from
   // head will get to last, and prev links from last gets to head.
   //
   char *tag;
   listnode_ref head;
   listnode_ref curr;
   listnode_ref last;
};

struct listnode {
   char *tag;
   char *line;
   listnode_ref prev;
   listnode_ref next;
};

void debugdump_list (list_ref list) {
   listnode_ref itor = NULL;
   assert (is_list (list));
   fflush (NULL);
   fprintf (stderr,
            "\n[%p]->struct list {tag=[%p]->[%s];"
            " head=[%p]; curr=[%p]; last=[%p]; }\n",
            list, list->tag, list->tag,
            list->head, list->curr, list->last);
   for (itor = list->head; itor != NULL; itor = itor->next) {
      fprintf (stderr,
               "[%p]->struct listnode {tag=[%p]->[%s];"
               " line=[%p]=[%s]; prev=[%p]; next=[%p]; }\n",
               itor, itor->tag, itor->tag, itor->line, itor->line,
               itor->prev, itor->next);
   }
   fflush (NULL);
}


list_ref new_list (void) {
   //
   // Creates a new struct list.
   //
   list_ref list = malloc (sizeof (struct list));
   assert (list != NULL);
   list->tag = list_tag;
   list->head = NULL;
   list->curr = NULL;
   list->last = NULL;
   return list;
}

void free_list (list_ref list) {
   assert (is_list (list));  
   assert (empty_list (list));
   //memset (list, 0, sizeof (struct list));
   free (list);
}

bool move_head (list_ref list) {
   assert (is_list (list));
   list->curr = list->head;
   return true;
}

bool move_last (list_ref list) {
   assert (is_list (list));
   list->curr = list->last;
   return true;
}

bool move_prev (list_ref list) {
   assert (is_list (list));
   if (list->curr != list->head) {
      list->curr = list->curr->prev;
      return true;
   }
   return false;
}

bool move_next (list_ref list) {
   assert (is_list (list));
   if (list->curr != list->last) {
      list->curr = list->curr->next;
      return true;
   }
   return false;
}

bool setmove_list (list_ref list, list_move move) {
   assert (is_list (list));
   switch (move) {
      case MOVE_HEAD:
           return move_head (list);
           break;
      case MOVE_PREV:
           if (move_prev (list)) return true;
           break;
      case MOVE_NEXT:
           if (move_next (list)) return true;
           break;
      case MOVE_LAST:
           return move_last (list);
           break;
      default: assert (false);
   }
   return false;
}

char *viewcurr_list (list_ref list) {
   assert (is_list (list));
   if (list->curr != NULL) {
      char *line = list->curr->line;
      return line;
   }
   return NULL;
}


void insert_list (list_ref list, char *line) {
   assert (is_list (list));
   listnode_ref new = malloc (sizeof (struct listnode));
   new->tag = listnode_tag;
   new->line = line;
   new->prev = NULL;
   new->next = NULL;

   if (list->head == NULL) {
      list->head = new;
      list->last = new;
      list->curr = new;
   } else {
      if (list->curr->next == NULL) {
         list->curr->next = new;
         new->prev = list->curr;
         list->last = new;
         list->curr = new;
     // } else if (list->curr->prev == NULL) {
       //  list->curr->prev = new;
       //  new->next = list->curr;
       //  list->head = new;
       //  list->curr = new;
      } else {
         list->curr->next->prev = new;
         new->next = list->curr->next;
         list->curr->next = new;
         new->prev = list->curr;
         list->curr = new;
      }
   }
}

void delete_list (list_ref list) {
   assert (is_list (list));
   if (list->head == NULL) return;
   assert (! empty_list (list));
   listnode_ref n1, n2;
 
   if (list->curr->prev == NULL) {
      if (list->curr->next == NULL) {
         list->head = list->last = NULL;
         free (list->curr->line);
         free (list->curr);
         list->curr = NULL;
         return;
      }
      n1 = list->curr->next;
      n1->prev = NULL;
      list->head = n1;
      free (list->curr->line);
      free (list->curr);
      list->curr = list->head;
      return;
   } else if (list->curr->next == NULL) {
      n1 = list->curr->prev;
      n1->next = NULL;
      list->last = n1;
      free (list->curr->line);
      free (list->curr);
      list->curr = list->last;
      return;
   }
   n1 = list->curr->next;
   n2 = list->curr->prev;
   n2->next = list->curr->next;
   n1->prev = list->curr->prev;
   free (list->curr->line);
   free (list->curr);
   list->curr = n1;
}

bool empty_list (list_ref list) {
   assert (is_list (list));
   return list->head == NULL;
}

bool is_list (list_ref list) {
   return list != NULL && list->tag == list_tag;
}

