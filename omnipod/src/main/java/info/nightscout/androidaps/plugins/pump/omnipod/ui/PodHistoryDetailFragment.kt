package info.nightscout.androidaps.plugins.pump.omnipod.ui

import android.os.Bundle
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import info.nightscout.androidaps.db.OmnipodHistoryRecord
import info.nightscout.androidaps.plugins.pump.omnipod.R
import info.nightscout.androidaps.plugins.pump.omnipod.databinding.OmnipodFragmentPodHistoryDetailListBinding

const val ARG_RECORD = "omnipod_history_record"

class PodHistoryDetailFragment : BottomSheetDialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<OmnipodFragmentPodHistoryDetailListBinding>(inflater,R.layout.omnipod_fragment_pod_history_detail_list, container, false)
        binding.record = requireArguments().getSerializable(ARG_RECORD) as OmnipodHistoryRecord
        return binding.root
    }

    companion object {

        fun newInstance(record: OmnipodHistoryRecord): PodHistoryDetailFragment =
            PodHistoryDetailFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_RECORD,record)
                }
            }

    }
}
