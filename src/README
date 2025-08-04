// Black Friday Order Manager


				PROGRAM EXECUTION

	Initially, the main thread will launch the P level 1 threads.
They will share the scanner of the file "orders.txt" and will read it
in parallel according to the atomic variable "turn". It will dictate
whose thread it is the turn to read the current line from the file.
Thus, an approximately equal division of the number of orders to
process will be made.
	Furthermore, each thread will try to get as many level 2 threads
as possible from the P available ones, depending on the value of the atomic
variable "levelTwoThreads". Of course, the case in which a thread does not
manage to get any level 2 threads is also handled. Basically, it will wait
for at least one thread to be released before moving on.
	After level 2 threads have been launched, they will start looking
in "order_products.txt" for the products they are responsible for and
marking them as "shipped".
	Finally, after all products have been marked "shipped" by the
level 2 thread within the order handled by the parent level 1 thread,
the latter will be able to mark the order as delivered.