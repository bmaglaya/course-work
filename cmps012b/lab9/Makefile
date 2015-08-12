#$Id: Makefile,v 1.1 2012-12-01 22:54:27-08 - - $

GCC      = gcc -g -O0 -Wall -Wextra -std=gnu99

CSOURCE1 = numsort.c inssort.c
CSOURCE2 = linesort.c inssort.c
CHEADER  = inssort.h
OBJECT1  = ${CSOURCE1:.c=.o}
OBJECT2  = ${CSOURCE2:.c=.o}
SOURCES  = ${CHEADER} ${CSOURCE1} ${CSOURCE2} Makefile README

all : numsort linesort

numsort : ${OBJECT1}
	${GCC} -o $@ ${CSOURCE1}

linesort : ${OBJECT2}
	${GCC} -o $@ ${CSOURCE2}

%.o : %.c
	${GCC} -c $<
