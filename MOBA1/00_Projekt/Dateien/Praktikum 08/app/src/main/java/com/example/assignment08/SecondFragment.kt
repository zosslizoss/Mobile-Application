package com.example.assignment08

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.navigation.fragment.findNavController
import com.beust.klaxon.Json
import com.example.assignment08.databinding.FragmentSecondBinding
import com.example.assignment08.databinding.StockCellLayoutBinding
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer


/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null

    val items = mutableListOf(
        Stock("AAPL", "115.69"),
        Stock("MSFT", "214.36"),
        Stock("GOOGL", "1519.45"),
        Stock("CRM", "255.52"),
        Stock("FB", "260.02")
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

        val model: SecondFragmentModel by activityViewModels()

        val adapter = StockAdapter(mutableListOf<Stock>(), requireContext());

        context?.let {model.initStockList(items, it)}

        model.stocks.observe(viewLifecycleOwner,
            Observer<MutableList<Stock>> { newVal ->
                adapter?.stocks.clear()
                adapter?.stocks.addAll(newVal)
                adapter?.notifyDataSetChanged()
            }
        )

        context?.let { model.loadStock(it) }


        binding.buttonAdd.setOnClickListener {
            Log.i("tag", "Button clicked!")

            val symbol: String = binding.addSymbolName.text.toString()
            val value: String = binding.addSymbolValue.text.toString()

            context?.let { it1 ->
                model.addStock(symbol, value,
                    it1
                )
            }
        }

        binding.listStockView.adapter = adapter

        binding.buttonClear.setOnClickListener {
            binding.addSymbolName.text.clear()
            binding.addSymbolValue.text.clear()

        }

        binding.buttonSecond.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
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
        binding.stockName.text = (index + 1).toString()
        binding.stockSymbol.text = stock.symbol
        binding.stockValue.text = stock.value
        return view
    }

}

class Stock(
    @Json(name = "01. symbol")
    var symbol: String,
    @Json(name = "05. price")
    var value: String

) {}

class StockKlaxonBase(
    @Json(name = "Global Quote")
    val globalQuote: Stock
)
