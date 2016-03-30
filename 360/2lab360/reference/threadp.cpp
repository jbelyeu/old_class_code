#include <stdio.h>
#include <iostream>
#include <unistd.h>
#include <queue>

#include <pthread.h>
#include <semaphore.h>

using namespace std;

struct threadInfo
{
	int threadID;
	int stuff;
	double dubStuff;
};

const int THREADS = 20;
sem_t mutex; //mutual exclusion
sem_t space_on_q;
sem_t num_of_tasks;

queue<int > tasks;

void* serve(void* pointer)
{
	// for the consumer threads to do
	struct threadInfo* myInfo = (struct threadInfo* ) pointer;
	
	while ( 1 )
	{
		sem_wait(&num_of_tasks); //while there are no tasks, wait
		
		//wait for queue to be available
		sem_wait(&mutex);
		cout << "I am thread: " << myInfo->threadID << endl;
		int thing = tasks.front();
		tasks.pop();
		cout << "things to do : " << thing << endl;
		sem_post(&mutex); //let go of queue

		sem_post(&space_on_q);

		//read request
		//parse headers
		//get file
		//write response to client
		//close() socket
	}
}

int main(int argc, char* argv[])
{
	cout << "main" << endl;
	struct threadInfo* myThreads[THREADS];
	pthread_t threads [THREADS];
	
	sem_init(&space_on_q, 0, 100);
	sem_init(&num_of_tasks, 0, 0);
	sem_init(&mutex, 0, 1);
	
	
	for (int i = 0; i < THREADS; ++i)
	{
		sem_wait(&mutex);
		
		cout << "creating thread " << i << endl;
		myThreads[i]->threadID = i;
		pthread_create(&threads[i], NULL, serve, &myThreads);
		sem_post(&mutex);
	}

	
	int counter = 0;
	while(1)
	{
		sem_wait(&space_on_q); //limits the number of requests allowed, down to queue size
		
		//this is where the producer works
		sem_wait(&mutex);
		
		tasks.push(counter);
		sem_post(&mutex);
		sem_post(&num_of_tasks); //there is a task now

		counter++;
	}
}
