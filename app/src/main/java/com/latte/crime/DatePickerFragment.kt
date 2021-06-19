package com.latte.crime

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import java.util.*

private const val TAG = "DatePickerFragment"
private const val ARG_DATE = "date"

class DatePickerFragment: DialogFragment() {

    interface Callbacks{
        fun onDateSelected(date: Date)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        Log.d(TAG,"onCreateDialog!!")

        val dateListener = DatePickerDialog.OnDateSetListener { _: DatePicker, year: Int, month: Int, day: Int ->
            val resultDate: Date = GregorianCalendar(year,month,day).time

            targetFragment?.let{
                fragment -> (fragment as Callbacks).onDateSelected(resultDate)
            }
        }



        var date = arguments?.getSerializable(ARG_DATE) as Date
        val calendar = Calendar.getInstance()
        calendar.time = date
        val initYear = calendar.get(Calendar.YEAR)
        val initMonth = calendar.get(Calendar.MONTH)
        val initDay = calendar.get(Calendar.DAY_OF_MONTH)

        return DatePickerDialog(requireContext(),dateListener, initYear,initMonth,initDay)
    }

    companion object{
        fun newInstance(date: Date): DatePickerFragment{
            Log.d(TAG,"new Instance")
            val args = Bundle().apply {
                putSerializable(ARG_DATE,date)
            }

            return DatePickerFragment().apply {
                arguments = args
            }
        }




    }
}