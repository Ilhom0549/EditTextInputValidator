package uz.ilkhomkhuja.edittextinputvalidator


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import uz.ilkhomkhuja.edittextinputvalidator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var countryList = arrayOf(
        "Australia",
        "Uzbekistan",
        "Italy",
        "Russia",
        "Spain",
        "UAE",
        "Qatar",
        "Kazakhstan",
        "United States",
        "China",
    )
    private lateinit var arrayAdapter: ArrayAdapter<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadData()
        setListeners()
    }

    private fun loadData() {
        countryList.sort()
        arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, countryList)
        binding.countrySpinnerEt.setAdapter(arrayAdapter)
    }

    private fun isFull() = (binding.name.isNotEmpty() && binding.emailAddressEt.isNotEmpty()
            && binding.phoneNumberEt.isNotEmpty() && binding.ipAddressEt.isNotEmpty()
            && binding.descriptionEt.isNotEmpty() && binding.yearEt.isNotEmpty())

    private fun checkDataFull() =
        (isFull() && binding.countrySpinnerEt.itemSelected() && binding.secondEt.isNotEmpty())

    private fun setListeners() {
        binding.apply {
            clearBtn.setOnClickListener {
                countrySpinnerEt.isSelected(false)
                emailAddressEt.text?.clear()
                name.text?.clear()
                phoneNumberEt.text?.clear()
                ipAddressEt.text?.clear()
                yearEt.text?.clear()
                descriptionEt.text?.clear()
                password.clear()
                secondEt.clear()
            }
            submitBtn.setOnClickListener {
                checkData()
                if (checkDataFull())
                    Toast.makeText(this@MainActivity, "Data Full", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun checkData() {
        binding.apply {
            secondEt.textAndSetError()
            password.textAndSetError()
            name.correctAndSetError()
            emailAddressEt.correctAndSetError()
            phoneNumberEt.correctAndSetError()
            ipAddressEt.correctAndSetError()
            descriptionEt.correctAndSetError()
            yearEt.correctAndSetError()
            countrySpinnerEt.itemSelected()
        }
    }
}