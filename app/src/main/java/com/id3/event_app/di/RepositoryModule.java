package com.id3.event_app.di;

import com.id3.event_app.data.repository.CommentRepositoryImpl;
import com.id3.event_app.data.repository.EventDetailRepositoryImpl;
import com.id3.event_app.data.repository.EventRepositoryImpl;
import com.id3.event_app.data.repository.LiveStatusRepositoryImpl;
import com.id3.event_app.domain.repository.CommentRepository;
import com.id3.event_app.domain.repository.EventDetailRepository;
import com.id3.event_app.domain.repository.EventRepository;
import com.id3.event_app.domain.repository.LiveStatusRepository;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract EventRepository bindEventRepository(EventRepositoryImpl impl);

    @Binds
    @Singleton
    abstract EventDetailRepository bindEventDetailRepository(EventDetailRepositoryImpl impl);

    @Binds
    @Singleton
    abstract CommentRepository bindCommentRepository(CommentRepositoryImpl impl);

    @Binds
    @Singleton
    abstract LiveStatusRepository bindLiveStatusRepository(LiveStatusRepositoryImpl impl);
}
