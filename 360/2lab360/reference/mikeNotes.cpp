#include <stdio.h>
#include <iostream>
#include <pthread.h>
#include <semaphore.h>
#include <unistd.h>
#include <queue>

#define THREADS 20

using namespace std;

sem_t mutex;
sem_t space_on_q;
sem_t n_of_tasks;

queue<int> tasks;

struct thread_info
{
    int thread_id;
};

void* serve(void* ptr)
{
    struct thread_info* my_info = (struct thread_info*) ptr;
    
    while(1)
    {
        sem_wait(&n_of_tasks);
        sem_wait(&mutex);
        
        int thing_i_should_do = tasks.front();
        tasks.pop();
        cout << "I am thread: " << my_info->thread_id << "\t" << endl;
        cout << thing_i_should_do << endl;
        
        sem_post(&mutex);
        sem_post(&space_on_q);
    }
}

int main(int argv, char* argc[])
{
    //initialize the semaphore(s)
    sem_init(&mutex, 0, 1);
    sem_init(&space_on_q, 0, 100);
    sem_init(&n_of_tasks, 0, 0);
    
    pthread_t threads[THREADS];
    struct thread_info my_threads[THREADS];
    
    for(int i = 0; i < THREADS; i++)
    {
        sem_wait(&mutex);
        
        cout << "creating thread: " << i << endl;
        
        sem_post(&mutex);
        
        my_threads[i].thread_id = i;
        pthread_create(&threads[i], NULL, serve, (void*) &my_threads[i]);
    }
    
    int counter = 0;
    while(1)
    {
        // this is where the producer will do work
        
        //get socket
        // counter = accept(...);
        
        counter = counter + 1;
        
        sem_wait(&space_on_q);
        sem_wait(&mutex);
        
        tasks.push(counter); //file descriptor instead of counter
        
        sem_post(&mutex);
        sem_post(&n_of_tasks);
        
    }
}
