GCC        = gcc -g -O0 -Wall -Wextra -std=gnu99

SOURCE     = bcat.c
OBJECT     = ${SOURCE:.c=.o}
EXECBIN    = bcat

all : ${EXECBIN}

${EXECBIN} : ${OBJECT}
	${GCC} -o ${EXECBIN} ${OBJECT}

%.o : %.c
	${GCC} -c $<
