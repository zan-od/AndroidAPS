package info.nightscout.androidaps.plugins.pump.omnipod.ui

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import info.nightscout.androidaps.db.OmnipodHistoryRecord
import info.nightscout.androidaps.logging.AAPSLogger
import info.nightscout.androidaps.plugins.pump.omnipod.util.AapsOmnipodUtil
import info.nightscout.androidaps.utils.resources.ResourceHelper
import javax.inject.Inject

class OmnipodHistoryRecordsAdapter @Inject constructor() : PagedListAdapter<OmnipodHistoryRecord, OmnipodHistoryRecordViewHolder>(DIFF_CALLBACK) {

    @Inject lateinit var aapsLogger: AAPSLogger
    @Inject lateinit var resourceHelper: ResourceHelper
    @Inject lateinit var aapsOmnipodUtil: AapsOmnipodUtil

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OmnipodHistoryRecordViewHolder {
        return OmnipodHistoryRecordViewHolder.create(parent ,aapsLogger, aapsOmnipodUtil, resourceHelper)
    }

    override fun onBindViewHolder(holder: OmnipodHistoryRecordViewHolder, position: Int) {
        val record: OmnipodHistoryRecord? = getItem(position)
        holder.bind(record)
    }

    companion object {

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<OmnipodHistoryRecord>() {

            override fun areItemsTheSame(oldItem: OmnipodHistoryRecord, newItem: OmnipodHistoryRecord) =
                oldItem.compareTo(newItem) == 0

            override fun areContentsTheSame(oldItem: OmnipodHistoryRecord, newItem: OmnipodHistoryRecord) =
                areItemsTheSame(oldItem,newItem)

        }

    }

}