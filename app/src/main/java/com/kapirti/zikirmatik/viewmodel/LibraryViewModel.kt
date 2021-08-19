package com.kapirti.zikirmatik.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.kapirti.zikirmatik.model.ZikirModel
import android.widget.Toast
import com.kapirti.zikirmatik.servis.ZikirApiServise
import com.kapirti.zikirmatik.servis.ZikirDatabase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch


class LibraryViewModel(application: Application): BaseViewModel(application) {

    private val zikirApiServise= ZikirApiServise()
    private val disposable = CompositeDisposable()

    val zkdescription = MutableLiveData<List<ZikirModel>>()
    val loading = MutableLiveData<Boolean>()
    val error = MutableLiveData<Boolean>()

    fun takeDataFromRoom() {
        loading.value = true
        launch {
            val zikirList = ZikirDatabase(getApplication()).zikirDao().getAllZikir()
            showZikir(zikirList)
            Toast.makeText(getApplication(),"Zikir from Room",Toast.LENGTH_LONG).show()
        }
    }
    fun takeDataFromInternet() {
        loading.value = true
        disposable.add(
            zikirApiServise.getData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<ZikirModel>>() {
                    override fun onSuccess(t: List<ZikirModel>) {
                        hideZikirinSqlite(t)
                        Toast.makeText(getApplication(), "Zikir froom internet", Toast.LENGTH_LONG)
                            .show()
                    }

                    override fun onError(e: Throwable) {
                        error.value = true
                        loading.value = true
                        e.printStackTrace()
                    }
                })
        )
    }
    private fun hideZikirinSqlite(zikirList:List<ZikirModel>){
        launch {
            val dao=ZikirDatabase(getApplication()).zikirDao()
            dao.deleteAllZikir()
            val uuidList=dao.insertAll(*zikirList.toTypedArray())
            var i=0
            while(i<zikirList.size){
                zikirList[i].uuid=uuidList[i].toInt()
                i=i+1
            }
            showZikir(zikirList)
        }
    }
    private fun showZikir(zikirList: List<ZikirModel>){
        zkdescription.value=zikirList
        error.value=false
        loading.value=false
    }
}

