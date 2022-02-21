package uz.ilkhomkhuja.validatorlibrary

import android.content.Context
import android.content.res.TypedArray
import android.text.Editable
import android.text.InputFilter
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import android.widget.EditText
import android.widget.LinearLayout

class CheckedPasswordValidator(context: Context, attributeSet: AttributeSet?) :
    LinearLayout(context, attributeSet) {

    private var typedArray: TypedArray =
        context.obtainStyledAttributes(attributeSet, R.styleable.CheckedPasswordValidator)
    private val isPassword: Boolean =
        typedArray.getBoolean(R.styleable.CheckedPasswordValidator_passwordEnabled, true)

    private var passwordEditText: EditText
    private var confirmPasswordEditText: EditText
    private var firstPassword = ""
    private var secondPassword = ""


    init {
        inflate(context, R.layout.password_layout, this)
        passwordEditText = findViewById(R.id.password_et)
        confirmPasswordEditText = findViewById(R.id.confirm_password_et)
        firstPassword = passwordEditText.text.toString()
        secondPassword = confirmPasswordEditText.text.toString()
        setFilter()
    }

    fun clear() {
        passwordEditText.text.clear()
        confirmPasswordEditText.text.clear()
    }

    fun isNotEmpty(): Boolean =
        (passwordEditText.text.isNotEmpty() && confirmPasswordEditText.text.isNotEmpty())

    private fun equals(): Boolean = (firstText() == secondText())

    private fun firstText(): String {
        firstPassword = passwordEditText.text.toString()
        return firstPassword
    }

    private fun secondText(): String {
        secondPassword = confirmPasswordEditText.text.toString()
        return secondPassword
    }

    private fun setFilter() {
        val maxLength = typedArray.getInt(R.styleable.CheckedPasswordValidator_maxLength, 25)
        val arrayOfInputFilters = arrayOfNulls<InputFilter>(1)
        arrayOfInputFilters[0] = InputFilter.LengthFilter(maxLength)
        passwordEditText.filters = arrayOfInputFilters
        confirmPasswordEditText.filters = arrayOfInputFilters
        if (isPassword) {
            passwordEditText.hint = "Enter password"
            confirmPasswordEditText.hint = "Re-enter this password"
        } else {
            passwordEditText.inputType = InputType.TYPE_CLASS_TEXT
            confirmPasswordEditText.inputType = InputType.TYPE_CLASS_TEXT
            passwordEditText.hint = "Enter text"
            confirmPasswordEditText.hint = "Enter text"
        }

        confirmPasswordEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0.toString().isNotEmpty() && p0.toString().length
                    == passwordEditText.text.toString().length
                ) {
                    if (p0.toString() == passwordEditText.text.toString() && isPassword) {
                        confirmPasswordEditText.setCompoundDrawablesWithIntrinsicBounds(
                            0,
                            0,
                            R.drawable.ic_check_circle,
                            0
                        )
                    } else if (p0.toString() != passwordEditText.text.toString() && isPassword) {
                        confirmPasswordEditText.setCompoundDrawablesWithIntrinsicBounds(
                            0,
                            0,
                            R.drawable.ic_error,
                            0
                        )
                    }
                } else {
                    confirmPasswordEditText.setCompoundDrawablesWithIntrinsicBounds(
                        0,
                        0,
                        0,
                        0
                    )
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })
    }


    private fun errorWithDrawable() {
        if (isPassword)
            confirmPasswordEditText.error = "Please re-enter password"
        confirmPasswordEditText.setCompoundDrawablesWithIntrinsicBounds(
            0,
            0,
            R.drawable.ic_error,
            0
        )
    }

    private fun errorWithDrawable(message: String) {
        confirmPasswordEditText.error = message
        confirmPasswordEditText.setCompoundDrawablesWithIntrinsicBounds(
            0,
            0,
            R.drawable.ic_error,
            0
        )
    }


    fun textAndSetError() {
        if (isPassword && !equals() || passwordEditText.text.isEmpty()) {
            errorWithDrawable()
        } else if ((passwordEditText.text.isEmpty() || confirmPasswordEditText.text.isEmpty())) {
            errorWithDrawable("Please re-enter")
        } else {
            confirmPasswordEditText.setCompoundDrawablesWithIntrinsicBounds(
                0,
                0,
                R.drawable.ic_check_circle,
                0
            )
        }
    }

}