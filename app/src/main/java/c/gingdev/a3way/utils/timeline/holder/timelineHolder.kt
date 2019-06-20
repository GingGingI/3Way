package c.gingdev.a3way.utils.timeline.holder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewStub
import android.widget.TextView
import c.gingdev.a3way.R
import androidx.annotation.LayoutRes
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import c.gingdev.a3way.vm.InnerItemVM

class timelineHolder(@LayoutRes val parentView: Int, val parent: ViewGroup)
    : RecyclerView.ViewHolder(LayoutInflater.from(parent.context)
        .inflate(parentView, parent, false)) {
//    InnerView
    private lateinit var innerView: View

    fun bind(@LayoutRes innerView: Int,
             data: ViewModel) {

//        Put innerView
        itemView.run {
            val view = findViewById<ViewStub>(R.id.timelineItemView)
                .apply { layoutResource = innerView }
            this@timelineHolder.innerView = view.inflate()
        }

        itemView.findViewById<TextView>(R.id.timelineContent).text = "출발지 : 집"
    }

//    LiveDataObserver 추가

}