#include <stdio.h>
#include <iostream>
#include <unistd.h>
#include <pthread.h>
#include <semaphore.h>

#define THREADS 10


sem_t work_mutex;

struct thread_info
{
    int thread_id;
    int another_number;
};

void* serve( void* in_data )
{
    struct thread_info* t_info = ( struct thread_info* ) in_data;
    int tid = t_info->thread_id;

    while( 1 )
    {
        sem_wait( &work_mutex );
        
        std::cout << "I am thread: " << tid << "\t" << std::endl;

        sem_post( &work_mutex );
    }
}

int main( int argv, char* argc[] )
{
    std::cout << "This is an example of pthreads!" << std::endl;

    sem_init( &work_mutex, 0, 1 );

    pthread_t threads[ THREADS ];

    struct thread_info all_thread_info[ THREADS ];

    for( int i = 0; i < THREADS; i++ )
    {
        sem_wait( &work_mutex );
 
        std::cout << "creating thread: " << i << "\t" << std::endl;
        all_thread_info[ i ].thread_id = i;
        pthread_create( &threads[ i ], NULL, serve, ( void* ) &all_thread_info[ i ] );
 
        sem_post( &work_mutex );
    }

    while( 1 )
    {
        // spin your wheels
    }

    return 0;
}

