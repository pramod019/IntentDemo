package com.pramodk.myapplication

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.pramodk.myapplication.databinding.ActivityOtherBinding

class OtherActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOtherBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_other)
        val bundle: Bundle? = intent.extras
        bundle?.let {
            bundle.apply {
                //Intent with data
                val string: String? = getString("keyString")
                binding.textView.text = string

                val myArray: ArrayList<String>? = getStringArrayList("myArray")
                showToast(message = "MyArrayList size:${myArray?.size}")
                val arrayList: ArrayList<String>? = getStringArrayList("arrayList")
                showToast(message = "ArrayList size:${arrayList?.size}")
                val float: Float? = bundle.get("keyFloat") as Float?
                var boolean = bundle.get("boolean") as? Boolean
                showToast(message = "Float data is:$float")
                showToast(message = "Boolean data is:$boolean")
                boolean = bundle.get("keyBoolean") as? Boolean
                showToast(message = "Boolean correct key data is:$boolean")
            }
            bundle.apply {
                //Serializable Data
                val blog = getSerializable("blogData") as Blog?
                if (blog != null) {
                    binding.textView.text = "Blog name is ${blog?.name}. Year started: ${blog?.year}"
                }
            }
            bundle.apply {
                //Parcelable Data
                val student: Student? = getParcelable("studentData")
                if (student != null) {
                    binding.textView.text = "Name is ${student?.name}. Age: ${student?.age}"
                }
            }
        }
    }
    private fun showToast(context: Context = applicationContext, message: String, duration: Int = Toast.LENGTH_SHORT) {
        if (!message.contains("null"))
            Toast.makeText(context, message, duration).show()
    }
}
