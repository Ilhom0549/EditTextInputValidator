package uz.ilkhomkhuja.validatorlibrary

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import android.widget.SpinnerAdapter
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout

class SpinnerValidator(context: Context, attributeSet: AttributeSet?) :
    ConstraintLayout(context, attributeSet) {

    private var attributes: TypedArray =
        context.obtainStyledAttributes(attributeSet, R.styleable.SpinnerValidator)
    private val hint = attributes.getString(R.styleable.SpinnerValidator_hint)
    private var spinner: Spinner
    private var textView: TextView


    init {
        inflate(context, R.layout.spinner_validator, this)
        spinner = findViewById(R.id.spinner)
        textView = findViewById(R.id.tv)
        textView.text = hint
    }

    fun setAdapter(adapter: SpinnerAdapter) {
        spinner.adapter = adapter
        var count = 0
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if (count == 0) {
                    textView.visibility = View.VISIBLE
                    count++
                } else {
                    textView.visibility = View.GONE
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }
    }

    fun getAdapter(): SpinnerAdapter = spinner.adapter!!

    fun setHint(hint: String) {
        textView.text = hint
    }


    private fun setErrorDrawable(message: String) {
        textView.text = message
        textView.visibility = View.VISIBLE
        textView.setTextColor(Color.RED)
        textView.setCompoundDrawablesWithIntrinsicBounds(
            0,
            0,
            R.drawable.ic_error,
            0
        )
    }


    fun selectedItem(): Any? = if (textView.visibility == View.GONE)
        spinner.selectedItem
    else
        null

    fun selectedItemPosition(): Int? = if (textView.visibility == View.GONE)
        spinner.selectedItemPosition
    else
        null

    fun selectedItemId(): Long? = if (textView.visibility == View.GONE)
        spinner.selectedItemId
    else
        null

    fun itemSelected(): Boolean {
        if (textView.visibility == View.VISIBLE) {
            setErrorDrawable("< Please select one >")
        }
        return textView.visibility == View.GONE
    }

    fun isSelected(boolean: Boolean) {
        if (boolean) {
            textView.visibility = View.GONE
        } else {
            textView.visibility = View.VISIBLE
        }

    }
}