package info.nightscout.androidaps.plugins.pump.omnipod.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import androidx.recyclerview.widget.DividerItemDecoration
import dagger.android.support.AndroidSupportInjection
import info.nightscout.androidaps.db.OmnipodHistoryRecord
import info.nightscout.androidaps.interfaces.DatabaseHelperInterface
import info.nightscout.androidaps.logging.AAPSLogger
import info.nightscout.androidaps.logging.LTag
import info.nightscout.androidaps.plugins.pump.omnipod.R
import info.nightscout.androidaps.plugins.pump.omnipod.data.OmnipodHistoryRecordDataSource
import info.nightscout.androidaps.plugins.pump.omnipod.manager.AapsPodStateManager
import info.nightscout.androidaps.utils.resources.ResourceHelper
import javax.inject.Inject

/**
 * A fragment representing a list of Items.
 */
class PodHistoryListFragment @Inject constructor() : Fragment() {

    @Inject lateinit var aapsLogger: AAPSLogger
    @Inject lateinit var resourceHelper: ResourceHelper
    @Inject lateinit var databaseHelper: DatabaseHelperInterface
    @Inject lateinit var podStateManager: AapsPodStateManager
    @Inject lateinit var omnipodHistoryRecordsAdapter: OmnipodHistoryRecordsAdapter

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        try {
            aapsLogger.info(LTag.PUMP, "Creating list view fragment")

            val view = inflater.inflate(R.layout.omnipod_fragment_pod_history_list, container, false)

            val recyclerView: RecyclerView = view.findViewById(R.id.omnipod_history_recyclerview)
            recyclerView.setHasFixedSize(true)
            //linearLayoutManager = LinearLayoutManager(this)
            recyclerView.layoutManager = LinearLayoutManager(activity as Context)
            recyclerView.adapter = omnipodHistoryRecordsAdapter
            recyclerView.addItemDecoration(DividerItemDecoration(recyclerView.context, DividerItemDecoration.VERTICAL))
            //view.findViewById().visibility = View.GONE

            val dataSourceFactory = object : DataSource.Factory<Long, OmnipodHistoryRecord>() {
                override fun create(): DataSource<Long, OmnipodHistoryRecord> {
                    return OmnipodHistoryRecordDataSource(databaseHelper, aapsLogger)
                }
            }

            val records = LivePagedListBuilder(dataSourceFactory, PagedList.Config.Builder().setPageSize(30).build()).build()

            records.observe(activity as FragmentActivity, Observer<PagedList<OmnipodHistoryRecord>> { pagedList -> omnipodHistoryRecordsAdapter.submitList(pagedList) })

            aapsLogger.info(LTag.PUMP, "Created list view fragment")

            return view
        } catch (e : Exception) {
            aapsLogger.error("Error: ",e)
            throw e
        }
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            PodHistoryListFragment()
    }
}