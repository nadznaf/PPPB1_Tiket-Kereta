package pppb1.pertemuan6.pppb1_tiket_kereta

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import pppb1.pertemuan6.pppb1_tiket_kereta.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySecondBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val name = intent.getStringExtra("NAME")
        with(binding) {
            viewName.text = name
            txtTime.text = intent.getStringExtra("TIME")
            txtDate.text = intent.getStringExtra("DATE")
            txtDestination.text = intent.getStringExtra("DESTINATION")

        }
    }
}