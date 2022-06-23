package com.example.assignment06

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ListView
import androidx.navigation.fragment.findNavController
import com.example.assignment06.databinding.FragmentSecondBinding
import com.example.assignment06.databinding.StockCellLayoutBinding

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null

    val items = mutableListOf(
        Stock("Apple", "AAPL", 115.69),
        Stock("Microsoft", "MSFT", 214.36),
        Stock("Google", "GOOGL", 1519.45),
        Stock("Salesforce", "CRM", 255.52),
        Stock("Facebook", "FB", 260.02),
        Stock("Amazon", "AMZN", 3201.86),
        Stock("eBay", "EBAY", 54.05),
        Stock("Twitter", "TWTR", 45.41),
        Stock("Snapchat", "SNAP", 28.11),
        Stock("testlongstringabcdefghijklmnopqrstuvwxyz", "SNAP", 28.11)
    )

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.listStockView.adapter = StockAdapter(items, requireContext())


        binding.buttonSecond.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

class StockAdapter(var stocks: MutableList<Stock>, val context: Context) : BaseAdapter() {
    var layoutInflater: LayoutInflater
    private var _binding: StockCellLayoutBinding? = null
    private val binding get() = _binding!!
    private var bindings = mutableMapOf<View, StockCellLayoutBinding>()

    init {
        layoutInflater = LayoutInflater.from(context)
    }

    override
    fun getCount(): Int { //number of elements to display
        return stocks.size
    }

    override fun getItem(index: Int): Stock { //item at index
        return stocks.get(index)
    }

    override fun getItemId(index: Int): Long { //itemId for index
        return index.toLong()
    }

    override fun getView(
        index: Int, oldView: View?,
        viewGroup: ViewGroup?
    ): View {
        var view: View
        if (oldView == null) { //check if we get a view to recycle
            _binding = StockCellLayoutBinding.inflate(layoutInflater, viewGroup, false)
            view = binding.root;bindings[binding.root] = binding
        } else { //if yes, use the oldview
            view = oldView
            _binding = bindings[view]
        }
        val stock = getItem(index) //get the data for this index
        binding.stockName.text = stock.name
        binding.stockSymbol.text = stock.symbol
        binding.stockValue.text = stock.value.toString()
        return view
    }
}

class Stock(
    var name: String, var symbol: String, var value:
    Double
) {

}
