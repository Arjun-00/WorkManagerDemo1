package com.example.workmanagerdemo1

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.work.*

class MainActivity : AppCompatActivity() {
    private lateinit var textView:TextView

    companion object{
        const val KEY_COUNT_VALUE = "key_count"
    }
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val button : Button = findViewById<Button>(R.id.button)
        textView = findViewById<TextView>(R.id.textView);
        button.setOnClickListener {
            setOnwTimeWorkRequest()
        }
    }

    @SuppressLint("SuspiciousIndentation")
    private fun setOnwTimeWorkRequest(){
        val workManager = WorkManager.getInstance(applicationContext)
        //for constraints
        val constraints = Constraints.Builder()
            .setRequiresCharging(true)
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()


        //Input and output
        val data:Data = Data.Builder()
            .putInt(KEY_COUNT_VALUE,125)
            .build()


        val uploadRequest = OneTimeWorkRequest.Builder(UploadWorker::class.java)
            //set constraints
            //.setConstraints(constraints)
                //Input and output
            .setInputData(data)
            .build()



       workManager.enqueue(uploadRequest)

        //get Status of workmanager
        workManager.getWorkInfoByIdLiveData(uploadRequest.id)
            .observe(this, Observer {
            // here we got the status of workmanager

            textView.text = it.state.name
            //2.constraints are used for work space work on diffrent condition

                if(it.state.isFinished){
                    val data = it.outputData
                    val message = data.getString(UploadWorker.KEY_WORKER)
                    Toast.makeText(applicationContext,message.toString(),Toast.LENGTH_LONG).show()
                }
        })
    }
}