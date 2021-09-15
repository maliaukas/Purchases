package maliauka.sasha.ui.add

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.datetime.LocalDate
import maliauka.sasha.PurchaseApp
import maliauka.sasha.R
import maliauka.sasha.databinding.FragmentAddRecordBinding
import maliauka.sasha.model.Purchase
import maliauka.sasha.ui.viewmodel.PurchaseViewModel
import maliauka.sasha.ui.viewmodel.PurchaseViewModelFactory

class AddRecordFragment : Fragment(R.layout.fragment_add_record) {

    private val binding: FragmentAddRecordBinding by viewBinding()

    private val viewModel: PurchaseViewModel by activityViewModels {
        PurchaseViewModelFactory(((activity?.application) as PurchaseApp).repository)
    }

    @SuppressLint("NewApi")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonAdd.setOnClickListener {
            var toInsert = true

            val name = binding.included.inputName.text.toString()
            if (name.isBlank()) {
                binding.included.inputName.error = "Name should not be empty!"
                toInsert = false
            } else {
                binding.included.inputName.error = null
            }

            val cost = binding.included.inputCost.text.toString()
            if (cost.isBlank()) {
                binding.included.inputCost.error = "Cost should not be empty!"
                toInsert = false
            } else {
                binding.included.inputCost.error = null
            }

            val date = LocalDate(
                binding.included.inputDate.year,
                binding.included.inputDate.month,
                binding.included.inputDate.dayOfMonth
            )

            if (toInsert) {
                viewModel.insert(Purchase(name = name, date = date, cost = cost.toLong()))

                Snackbar.make(
                    binding.included.inputCost,
                    "Inserted successfully!",
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }

        val filterBtn = activity?.findViewById<ImageButton>(R.id.filter_btn)
        filterBtn?.isVisible = false
    }
}