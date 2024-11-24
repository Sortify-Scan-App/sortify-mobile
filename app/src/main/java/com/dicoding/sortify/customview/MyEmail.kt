package com.dicoding.sortify.customview

import android.content.Context
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Patterns
import androidx.appcompat.widget.AppCompatEditText

class MyEmail @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : AppCompatEditText(context, attrs) {
    init {
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                error = if (!isValid(text.toString())) {
                    "Invalid emails!"
                } else {
                    null
                }
            }
            override fun afterTextChanged(s: Editable?) {}
        })

    }
    private fun isValid(email: String): Boolean = !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
}