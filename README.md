# Concurrency-multithreading-
In this task I compare 2 ways of calculation of 10 000 000 elements in array.
So I created 2 methods: withConcurrency and withoutConcurrency.
The withConcurrency method is more effective than withoutConcurrency method. 
Why? Because of multithreading, here I created two threads, which makes parallel calculation. 
As a result needed time is about twice less.
