package info.nightscout.androidaps.utils.buildHelper

import info.nightscout.androidaps.BuildConfig
import info.nightscout.androidaps.Config
import info.nightscout.androidaps.R
import info.nightscout.androidaps.plugins.general.maintenance.LoggerUtils
import info.nightscout.androidaps.utils.sharedPreferences.SP
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BuildHelper @Inject constructor(private val config: Config,
                                      private val sp: SP) {

    private var devBranch = false
    private var engineeringMode = false

    init {
        val extFilesDir = LoggerUtils.getLogDirectory()
        val engineeringModeSemaphore = File(extFilesDir, "engineering_mode")

        engineeringMode = engineeringModeSemaphore.exists() && engineeringModeSemaphore.isFile || sp.getBoolean(R.string.key_engineering_mode, false)
        devBranch = BuildConfig.VERSION.contains("-") || BuildConfig.VERSION.matches(Regex(".*[a-zA-Z]+.*"))
    }

    fun isEngineeringModeOrRelease(): Boolean =
        if (!config.APS) true else engineeringMode || !devBranch

    fun isEngineeringMode(): Boolean =
        engineeringMode

    fun isDev(): Boolean = devBranch
}