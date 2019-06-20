package c.gingdev.a3way.utils.timeline.adapter

import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import c.gingdev.a3way.utils.timeline.holder.timelineHolder
import c.gingdev.a3way.utils.timeline.model.timelineViewModel
import c.gingdev.a3way.vm.InnerItemVM
import com.google.gson.JsonArray

class timelineAdapter
    : RecyclerView.Adapter<timelineHolder>() {
    private val items = ArrayList<Pair<timelineViewModel, ViewModel>>()

    override fun onCreateViewHolder(parent: ViewGroup, parentView: Int): timelineHolder
        = timelineHolder(
            parent = parent,
            parentView = parentView)

    override fun getItemCount(): Int
        = items.size

    override fun onBindViewHolder(holder: timelineHolder, position: Int) {
        holder.bind(
            innerView = items[position].first.viewType,
            data = items[position].second)
    }

    fun clearItem() {
        items.forEach {
            if (it.second is InnerItemVM<*>)
                (it.second as InnerItemVM<*>).clearViewModel()
        }
//        ViewModel Cleared
    }
}