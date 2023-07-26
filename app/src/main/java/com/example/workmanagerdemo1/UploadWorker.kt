package com.example.workmanagerdemo1

import android.content.Context
import android.util.Log
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.text.SimpleDateFormat
import java.util.*

class UploadWorker(context : Context,params : WorkerParameters):Worker(context,params) {
    companion object{
        const val KEY_WORKER = "key_worker"
    }

    override fun doWork(): Result {
      try{
          //Input and output
          val count:Int = inputData.getInt(MainActivity.KEY_COUNT_VALUE,0)

          for(i : Int in 0 until count){
              Log.i("MYTAG","Uploading $i")
          }
          //Input and Output
          val time = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
          val curentDate = time.format(Date())
          val outPutDate = Data.Builder().putString(KEY_WORKER,curentDate).build()
          return Result.success(outPutDate)

      }catch (e:Exception){
          return Result.failure()
      }
    }
}
