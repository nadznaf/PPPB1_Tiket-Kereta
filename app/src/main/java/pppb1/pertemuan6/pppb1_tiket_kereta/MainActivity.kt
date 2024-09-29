package pppb1.pertemuan6.pppb1_tiket_kereta

import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Intent
import android.icu.util.Calendar
import android.os.Bundle
import android.text.format.DateFormat
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.DialogFragment
import pppb1.pertemuan6.pppb1_tiket_kereta.databinding.ActivityMainBinding
import pppb1.pertemuan6.pppb1_tiket_kereta.databinding.DialogConfirmBinding

class MainActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    lateinit var binding: ActivityMainBinding
    private lateinit var destinations: Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bookingButton.setOnClickListener {
            val name = binding.txtName.text.toString()
            val time = binding.btnTimePicker.text.toString()
            val date = binding.btnDatePicker.text.toString()
            val destination = binding.spinnerDestinations.selectedItem.toString()
            val intent = Intent(this@MainActivity, SecondActivity::class.java).apply {
                putExtra("NAME", name)
                putExtra("TIME", time)
                putExtra("DATE", date)
                putExtra("DESTINATION", destination)
            }
            startActivity(intent)
        }

        binding.btnTimePicker.setOnClickListener {
            val btnTimePicker = DialogTimePicker()
            btnTimePicker.show(supportFragmentManager, "time")
        }

        binding.btnDatePicker.setOnClickListener {
            val btnDatePicker = DialogDatePicker()
            btnDatePicker.show(supportFragmentManager, "date")
        }

        destinations = resources.getStringArray(R.array.destinations)

        val adapterDestinations = ArrayAdapter(
            this@MainActivity,
            androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
            destinations
        )
        binding.spinnerDestinations.adapter = adapterDestinations
        binding.bookingButton.setOnClickListener {
            val dialogConfirm = DialogConfirm()
            dialogConfirm.show(supportFragmentManager, "dialogConfirm")
        }
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, day: Int) {
        val selectedDate = "$day/${month + 1}/$year"
        binding.btnDatePicker.text = selectedDate
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        val selectedTime = "$hourOfDay:$minute"
        binding.btnTimePicker.text = selectedTime
    }
}

class DialogTimePicker : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendar = Calendar.getInstance()
        val hourOfDay = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)
        return TimePickerDialog(
            requireActivity(),
            activity as TimePickerDialog.OnTimeSetListener,
            hourOfDay,
            minute,
            DateFormat.is24HourFormat(activity)
        )
    }
}

class DialogDatePicker : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val monthOfYear = calendar.get(Calendar.MONTH)
        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
        return DatePickerDialog(
            requireActivity(),
            activity as DatePickerDialog.OnDateSetListener,
            year,
            monthOfYear,
            dayOfMonth
        )
    }
}

class DialogConfirm : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireActivity())
        val inflater = requireActivity().layoutInflater
        val binding = DialogConfirmBinding.inflate(inflater)

        val activityBinding = (activity as MainActivity).binding

        with(binding) {
            val name = activityBinding.txtName.text.toString()
            val time = activityBinding.btnTimePicker.text.toString()
            val date = activityBinding.btnDatePicker.text.toString()
            val destination = activityBinding.spinnerDestinations.selectedItem.toString()

            // Handle "Buy" button click
            btnBeli.setOnClickListener {
                // Redirect to SecondActivity
                val intent = Intent(activity, SecondActivity::class.java).apply {
                    putExtra("NAME", name)
                    putExtra("TIME", time)
                    putExtra("DATE", date)
                    putExtra("DESTINATION", destination)
                }
                startActivity(intent)
                dismiss() // Close the dialog
            }

            // Handle "Cancel" button click
            btnBatal.setOnClickListener {
                dismiss() // Close the dialog
            }
        }

        builder.setView(binding.root)
        return builder.create()
    }
}