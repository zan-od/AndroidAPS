package info.nightscout.androidaps.plugins.pump.omnipod.ui

import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import info.nightscout.androidaps.activities.NoSplashAppCompatActivity
import info.nightscout.androidaps.logging.AAPSLogger
import info.nightscout.androidaps.plugins.bus.RxBusWrapper
import info.nightscout.androidaps.plugins.pump.omnipod.R
import info.nightscout.androidaps.plugins.pump.omnipod.databinding.OmnipodPodHistoryActivityBinding
import info.nightscout.androidaps.plugins.pump.omnipod.event.EventOmnipodHistoryItemClicked
import info.nightscout.androidaps.utils.FabricPrivacy
import info.nightscout.androidaps.utils.extensions.plusAssign
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class PodHistoryActivity @Inject constructor() : NoSplashAppCompatActivity() {

    @Inject lateinit var aapsLogger: AAPSLogger
    @Inject lateinit var rxBus: RxBusWrapper
    @Inject lateinit var fabricPrivacy: FabricPrivacy

    private var disposables: CompositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<OmnipodPodHistoryActivityBinding>(this, R.layout.omnipod_pod_history_activity)
    }

    override fun onResume() {
        super.onResume()
        disposables += rxBus
            .toObservable(EventOmnipodHistoryItemClicked::class.java)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                val detailFragment = PodHistoryDetailFragment.newInstance(it.record)
                detailFragment.show(supportFragmentManager,detailFragment.tag)
            }, { fabricPrivacy.logException(it) })
    }

    override fun onPause() {
        super.onPause()
        disposables.clear()
    }

}
