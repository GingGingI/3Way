package c.gingdev.a3way.vm

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import c.gingdev.a3way.model.ArrivalData
import c.gingdev.a3way.model.TrafficDataModel
import c.gingdev.a3way.model.WalkDataModel
import c.gingdev.a3way.utils.retrofit.retrofits
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Response
import java.util.concurrent.TimeUnit

class InnerItemVM<DATA>(val data: DATA)
    : ViewModel() {
    private val disposable = CompositeDisposable()
    private val liveData: MutableLiveData<ArrivalData> = MutableLiveData()

    init {
        ObserveServerForData()
    }

    private fun ObserveServerForData() {
        when(data) {
            is TrafficDataModel -> {
                disposable.add(TrafficObservable
                    .subscribe({
                        liveData.value = it
                    }, {
                        it.printStackTrace()
                    })
                )
            }
            is WalkDataModel -> { /** Do nothing */ }
        }
    }

    fun clearViewModel() {
        if (disposable.size() > 0)
            disposable.clear()
//        ViewModel Cleared
    }

// etc.
    private fun Any?.notNull(f: ()-> Unit) {
        if (this != null) f()
    }
    private fun Any?.isNull(f: ()-> Unit) {
        if (this == null) f()
    }

//    Observable
    private val TrafficObservable =
        Observable.interval(0, 10, TimeUnit.SECONDS)
            .flatMap {
                return@flatMap Observable.create<ArrivalData> { subscriber ->
                    retrofits.getInstance()
                        .getRetrofitService()
                        .getDatas()
                        .enqueue(object : retrofit2.Callback<ArrivalData> {
                            override fun onFailure(call: Call<ArrivalData>, t: Throwable) {
                                subscriber.onError(t)
                            }

                            override fun onResponse(call: Call<ArrivalData>, response: Response<ArrivalData>) {
                                response.body().notNull { subscriber.onNext(response.body()!!) }
                            }
                        })
                }.subscribeOn(Schedulers.io())
            }
}