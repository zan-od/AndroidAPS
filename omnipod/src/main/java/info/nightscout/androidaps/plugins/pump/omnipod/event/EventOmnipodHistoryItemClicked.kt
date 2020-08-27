package info.nightscout.androidaps.plugins.pump.omnipod.event

import info.nightscout.androidaps.db.OmnipodHistoryRecord
import info.nightscout.androidaps.events.Event

data class EventOmnipodHistoryItemClicked(val record: OmnipodHistoryRecord) : Event()