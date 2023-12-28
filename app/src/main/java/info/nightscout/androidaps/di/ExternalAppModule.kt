package info.nightscout.androidaps.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import info.nightscout.androidaps.plugins.general.externalAppCommunicator.AuthRequest

@Module
@Suppress("unused")
abstract class ExternalAppModule {

    @ContributesAndroidInjector abstract fun authRequestInjector(): AuthRequest
}