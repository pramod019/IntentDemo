package com.pramodk.myapplication

import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.pramodk.myapplication.databinding.ActivityMainBinding
import java.io.Serializable

class MainActivity : AppCompatActivity(), View.OnClickListener {

    fun Context.gotoClass(targetType: Class<*>) = ComponentName(this, targetType)

    fun Context.startActivity(f: Intent.() -> Unit): Unit =
        Intent().apply(f).run(this::startActivity)

    inline fun <reified T : Activity> Context.start(
        noinline createIntent: Intent.() -> Unit = {}
    ) = startActivity {
        component = gotoClass(T::class.java)
        createIntent(this)
    }

    var arrayList = ArrayList<String>()

    private lateinit var search:String

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.btnSimpleIntent.setOnClickListener(this)
        binding.btnSimpleIntentAndData.setOnClickListener(this)
        binding.btnParcelableIntent.setOnClickListener(this)
        binding.btnSerializableIntent.setOnClickListener(this)
        binding.btnBrowserIntent.setOnClickListener(this)
        binding.btnMapsIntent.setOnClickListener(this)
        binding.btnGenericIntent.setOnClickListener(this)
        arrayList.add("Androidly")
        arrayList.add("Android")
        arrayList.add("Intents")

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            //to open the other activity
            R.id.btnSimpleIntent -> {
                val intent = Intent(this, OtherActivity::class.java)
                startActivity(intent)
            }
            R.id.btnSimpleIntentAndData -> {
                val intent = Intent(this, OtherActivity::class.java)
                with(intent)
                {
                    putExtra("keyString", "Androidly String data")
                    putStringArrayListExtra("arrayList", arrayList)
                    putExtra("keyBoolean", true)
                    putExtra("keyFloat", 1.2f)
                }
                startActivity(intent)
            }
            R.id.btnParcelableIntent -> {
                val student = Student("pramod",24)
                val intent = Intent(this, OtherActivity::class.java)
                intent.putExtra("studentData", student)
                startActivity(intent)
            }
            R.id.btnSerializableIntent -> {
                val blog = Blog("a", 1)
                val intent = Intent(this, OtherActivity::class.java)
                intent.putExtra("blogData", blog as Serializable)
                startActivity(intent)
            }
            R.id.btnBrowserIntent -> {
                val url = "https://developer.android.com/"
                val uri = Uri.parse(url)
                val intent = Intent(Intent.ACTION_VIEW, uri)
                if (intent.resolveActivity(packageManager) != null) {
                    startActivity(intent)
                } else {
                    Toast.makeText(applicationContext, "No application found", Toast.LENGTH_SHORT).show()
                }
            }
            R.id.btnMapsIntent -> {
                //val loc = "12.9538477,77.3507442"
                //val addressUri = Uri.parse("geo:0,0?q=" + loc)
                //val intent = Intent(Intent.ACTION_VIEW, addressUri)

                // Search for restaurants in San Francisco
                //val gmmIntentUri = Uri.parse("geo:37.7749,-122.4194?q=restaurants")
                search = binding.EdTxtSearch.text.toString()
                val gmmIntentUri = Uri.parse("geo:21.250000,81.629997?q=$search")
                val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                mapIntent.setPackage("com.google.android.apps.maps")
                if (mapIntent.resolveActivity(packageManager) != null) {
                    startActivity(mapIntent)
                } else {
                    Toast.makeText(applicationContext, "No application found", Toast.LENGTH_SHORT).show()
                }
            }
            else -> start<OtherActivity> {
                putExtra("keyString", "Androidly Generic Intent")
            }

        }
    }
}