package maliauka.sasha.ui.list

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import maliauka.sasha.PurchaseApp
import maliauka.sasha.R
import maliauka.sasha.databinding.FragmentListBinding
import maliauka.sasha.model.Purchase
import maliauka.sasha.ui.update.EditRecordDialogFragment
import maliauka.sasha.ui.viewmodel.PurchaseViewModel
import maliauka.sasha.ui.viewmodel.PurchaseViewModelFactory


class ListFragment : Fragment(R.layout.fragment_list), OnPurchaseClickListener {
    private val binding: FragmentListBinding by viewBinding()

    private val purchaseAdapter = PurchaseAdapter(this)

    private val viewModel: PurchaseViewModel by activityViewModels {
        PurchaseViewModelFactory(((activity?.application) as PurchaseApp).repository)
    }

    override fun onResume() {
        super.onResume()
//        viewModel.setSortColumn(sortByColumn())
//        viewModel.setSortOrder(sortOrder())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }

        viewModel.setSortColumn(sortByColumn())
        viewModel.setSortOrder(sortOrder())

        viewModel.purchases.observe(viewLifecycleOwner) {
            purchaseAdapter.submitList(it)
        }

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = purchaseAdapter
            SwipeHelper {
                viewModel.delete(it)
            }.attachToRecyclerView(this)
        }

        val filterBtn = activity?.findViewById<ImageButton>(R.id.filter_btn)
        filterBtn?.isVisible = true
    }

    private val sharedPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(context)
    }

    private fun sortByColumn(): String {
        return sharedPreferences.getString(
            getString(R.string.prefs_sort_column_key),
            getString(R.string.prefs_sort_column_default)
        ) ?: ""
    }


    private fun sortOrder(): Boolean {
        return sharedPreferences.getBoolean(
            getString(R.string.prefs_sort_order_key),
            getString(R.string.prefs_sort_order_default) == "true"
        )
    }

    override fun onClick(p: Purchase) {
        val dialog = EditRecordDialogFragment(p) {
            viewModel.update(it)
        }
        dialog.show(parentFragmentManager, "EditRecordDialogFragment")
    }
}

