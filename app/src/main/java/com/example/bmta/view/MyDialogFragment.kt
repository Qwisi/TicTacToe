package com.example.bmta.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import com.example.bmta.Game3x3
import com.example.bmta.R

class MyDialogFragment : DialogFragment() {
    private lateinit var inputFirstName: EditText
    private lateinit var inputSecondName: EditText
    private lateinit var okButton: Button
    private lateinit var cancelButton: Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.dialog_view, container, false)
        inputFirstName = view.findViewById(R.id.inputFirstName)
        inputSecondName = view.findViewById(R.id.inputSecondName)
        okButton = view.findViewById(R.id.okButton)
        cancelButton = view.findViewById(R.id.cancelButton)

        okButton.isEnabled = false

        buttonsListeners()
        inputsListeners()

        return view
    }

    private fun buttonsListeners(){
        okButton.setOnClickListener {
            val firstName = inputFirstName.text.toString()
            val secondName = inputSecondName.text.toString()

            val intent = Intent(context, Game3x3::class.java)
            intent.putExtra("firstPlayer", firstName)
            intent.putExtra("secondPlayer", secondName)
            startActivity(intent)
        }

        cancelButton.setOnClickListener {
            dismiss()
        }
    }

    private fun inputsListeners(){
        inputFirstName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(p0: Editable?) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                checkConditionsAndEnableButton()
            }
        })

        inputSecondName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(p0: Editable?) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                checkConditionsAndEnableButton()
            }
        })
    }

    private fun checkConditionsAndEnableButton(){
        // enable button if fields are not empty and not equals
        okButton?.isEnabled = inputFirstName.text.isNotEmpty() && inputSecondName.text.isNotEmpty()
                && inputFirstName.text.toString() != inputSecondName.text.toString()
    }
}
