package com.example.todo.view.fragment

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.todo.R
import com.example.todo.model.enums.BundleKey
import java.util.*


class DatePickerFragment : DialogFragment() {

    var callBack: CallBack? = null

    companion object {
        fun newInstance(date: Date): DatePickerFragment {
            val args = Bundle().apply {
                putSerializable(BundleKey.DATE.name, date)
            }
            return DatePickerFragment().apply {
                arguments = args
            }
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendar = Calendar.getInstance()


        val date = arguments?.getSerializable(BundleKey.DATE.name) as Date
        calendar.time = date

        val initializeYear = calendar.get(Calendar.YEAR)
        val initializeMonth = calendar.get(Calendar.MONTH)
        val initializeDay = calendar.get(Calendar.DAY_OF_MONTH)

        return DatePickerDialog(
            requireContext(),
            R.style.testPicker,
            { _, year, month, day ->

                val resultDate = GregorianCalendar(year, month, day).time
                callBack?.onDateSelected(resultDate)

            },
            initializeYear,
            initializeMonth,
            initializeDay
        ).also {
            it.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel",
                DialogInterface.OnClickListener { dialog, which ->
                    if (which == DialogInterface.BUTTON_NEGATIVE) {
                        callBack?.onCancel()
                    }
                })
        }

    }

    interface CallBack {
        fun onDateSelected(date: Date)
        fun onCancel()
    }

}