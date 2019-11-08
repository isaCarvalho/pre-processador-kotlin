#include <fat.h>
#include <fib.h>
#define N 10
#define M 5

/* Funcao main() 
* @param argc
* @param argv
*/
int main(int argc, char *argv[])
{
	// Funcao fat
	int x = fat(N);
	// Funcao fib
	int y = fib(M);
	return 0;
}
