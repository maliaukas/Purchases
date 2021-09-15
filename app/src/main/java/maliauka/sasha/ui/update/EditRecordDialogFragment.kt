package maliauka.sasha.ui.update

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import by.kirich1409.viewbindingdelegate.CreateMethod
import by.kirich1409.viewbindingdelegate.viewBinding
import kotlinx.datetime.LocalDate
import maliauka.sasha.databinding.RecordDataBinding
import maliauka.sasha.model.Purchase


class EditRecordDialogFragment(
    private val p: Purchase,
    private val onSubmit: (p: Purchase) -> Unit
) : DialogFragment() {

    private val binding: RecordDataBinding by viewBinding(createMethod = CreateMethod.INFLATE)

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)

            builder.setView(binding.root)

            with(binding) {
                inputName.setText(p.name)
                inputCost.setText("${p.cost}")
                inputDate.init(
                    p.date.year,
                    p.date.monthNumber,
                    p.date.dayOfMonth
                ) { _: DatePicker, _: Int, _: Int, _: Int -> }
            }

            builder
                .setTitle("Edit the record")
                .setPositiveButton("Submit") { _, _ -> }
                .setNegativeButton(
                    "Cancel"
                ) { dialog, _ ->
                    dialog.cancel()
                }

            // Create the AlertDialog object and return it
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    override fun onResume() {
        super.onResume()
        val dialog = dialog as AlertDialog
        dialog.getButton(Dialog.BUTTON_POSITIVE).setOnClickListener {
            var toUpdate = true

            val name = binding.inputName.text.toString()
            if (name.isBlank()) {
                binding.inputName.error = "Name should not be empty!"
                toUpdate = false
            } else {
                binding.inputName.error = null
            }

            val cost = binding.inputCost.text.toString()
            if (cost.isBlank()) {
                binding.inputCost.error = "Cost should not be empty!"
                toUpdate = false
            } else {
                binding.inputCost.error = null
            }

            val date = LocalDate(
                binding.inputDate.year,
                binding.inputDate.month,
                binding.inputDate.dayOfMonth
            )

            if (toUpdate) {
                val updatedPurchase = p.copy(name = name, cost = cost.toLong(), date = date)
                updatedPurchase.id = p.id
                onSubmit(updatedPurchase)

                dialog.cancel()
            }
        }
    }
}
