package com.example.myapplication

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.myapplication.databinding.FragmentSecondBinding

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val zero: String = "0"
    private val plus: String = "+"
    private val minus: String = "-"
    private val multi: String = "*"
    private val div: String = "/"


    private val values: MutableList<Int> = ArrayList()
    private val operators: MutableList<String> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonSecond.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }

        binding.button0.setOnClickListener { onNumberButtonPressed(0) }
        binding.button1.setOnClickListener { onNumberButtonPressed(1) }
        binding.button2.setOnClickListener { onNumberButtonPressed(2) }
        binding.button3.setOnClickListener { onNumberButtonPressed(3) }
        binding.button4.setOnClickListener { onNumberButtonPressed(4) }
        binding.button5.setOnClickListener { onNumberButtonPressed(5) }
        binding.button6.setOnClickListener { onNumberButtonPressed(6) }
        binding.button7.setOnClickListener { onNumberButtonPressed(7) }
        binding.button8.setOnClickListener { onNumberButtonPressed(8) }
        binding.button9.setOnClickListener { onNumberButtonPressed(9) }

        binding.buttonSlash.setOnClickListener { onCalcButtonPressed(0) }
        binding.buttonStar.setOnClickListener { onCalcButtonPressed(1) }
        binding.buttonPlus.setOnClickListener { onCalcButtonPressed(2) }
        binding.buttonMinus.setOnClickListener { onCalcButtonPressed(3) }
        binding.buttonC.setOnClickListener { onCalcButtonPressed(4) }
        binding.buttonEquals.setOnClickListener { onCalcButtonPressed(5) }
    }

    fun onNumberButtonPressed(number: Int){

        var value = binding.numberView.text.toString()

        if(value == zero){
            binding.numberView.text = number.toString()
        }else{
            binding.numberView.text = value + number.toString()
        }
    }

    fun onCalcButtonPressed(number: Int){

        var value = binding.numberView.text.toString()

        when (number) {
            0 -> {
                values.add(value.toInt())
                operators.add("/")
                binding.numberView.text = "0"
            }
            1 -> {
                values.add(value.toInt())
                operators.add("*")
                binding.numberView.text = "0"
            }
            2 -> {
                values.add(value.toInt())
                operators.add("+")
                binding.numberView.text = "0"
            }
            3 -> {
                values.add(value.toInt())
                operators.add("-")
                binding.numberView.text = "0"
            }
            4 -> {
                values.clear()
                operators.clear()
                binding.numberView.text = "0"
            }
            5 -> {
                val listIterator = values.listIterator()
                var result = listIterator.next()

                operators.forEach {
                    when (it) {
                        plus -> {
                            result += listIterator.next()
                        }
                        minus -> {
                            result -= listIterator.next()
                        }
                        multi -> {
                            result *= listIterator.next()
                        }
                        div -> {
                            result /= listIterator.next()
                        }
                    }
                }
                binding.numberView.text = result.toString()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}