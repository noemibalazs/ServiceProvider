package com.noemi.android.serviceprovider.di

import com.noemi.android.serviceprovider.workManager.remoteDataSource.ImgurRemoteData
import com.noemi.android.serviceprovider.workManager.remoteDataSource.ImgurRemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RemoteDataModule {

    @Binds
    @Singleton
    abstract fun bindsRemoteData(remoteDataSource: ImgurRemoteDataSource): ImgurRemoteData
}
