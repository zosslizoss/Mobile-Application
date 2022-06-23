package com.example.herobattler

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.BaseAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.herobattler.databinding.CharacterCellLayoutBinding
import com.example.herobattler.databinding.FragmentCharactersBinding
import java.nio.file.Files.size

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class Characters : Fragment() {

    private var _binding: FragmentCharactersBinding? = null

    val baseCharacters = mutableListOf(
        Character("Logathius", "5", "Paladin", "Human", "45", "45"),
        Character("Alune", "4", "Ranger", "Elve", "24", "24"),
        Character("Orc1", "3", "Orc-Swordman", "Orc", "38", "38"),
        Character("Orc2", "3", "Orc-Bowman", "Orc", "30", "30"),
        Character("Orc3", "3", "Orc-Spearman", "Orc", "32", "32"),
    )


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentCharactersBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val model: CharactersModel by activityViewModels()

        //val adapter = CharactersAdapter(mutableListOf<Character>(), requireContext());
        //CharactersAdapter.setMyAdapter(CharactersAdapter(mutableListOf<Character>(), requireContext()))
        //val adapter = CharactersAdapter.getMyAdapter()
        val adapter = CharactersAdapter(mutableListOf<Character>(), requireContext());


        if(adapter?.characters.size == 0){
            context?.let {model.initCharacterList(baseCharacters, it)}
        }

        model.characters.observe(viewLifecycleOwner,
            Observer<MutableList<Character>> { newVal ->
                adapter?.characters.clear()
                adapter?.characters.addAll(newVal)
                adapter?.notifyDataSetChanged()
            }
        )

        context?.let { model.loadCharacter(it) }


        binding.listCharacterView.adapter = adapter


        binding.buttonAddChar.setOnClickListener {
            findNavController().navigate(R.id.action_characters_to_newCharacter)
        }

        binding.buttonDeleteChar.setOnClickListener {
            findNavController().navigate(R.id.action_characters_to_deleteCharacter)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

class CharactersAdapter(var characters: MutableList<Character>, val context: Context) : BaseAdapter() {
    var layoutInflater: LayoutInflater
    private var _binding: CharacterCellLayoutBinding? = null
    private val binding get() = _binding!!
    private var bindings = mutableMapOf<View, CharacterCellLayoutBinding>()

    init {
        layoutInflater = LayoutInflater.from(context)
    }

    override
    fun getCount(): Int { //number of elements to display
        return characters.size
    }

    override fun getItem(index: Int): Character { //item at index
        return characters.get(index)
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
            _binding = CharacterCellLayoutBinding.inflate(layoutInflater, viewGroup, false)
            view = binding.root;bindings[binding.root] = binding
        } else { //if yes, use the oldview
            view = oldView
            _binding = bindings[view]
        }
        val character = getItem(index) //get the data for this index
        binding.characterNr.text = (index + 1).toString()
        binding.characterName.text = character.characterName
        binding.characterLevel.text = character.characterLevel
        binding.characterClass.text = character.characterClass
        binding.characterRace.text = character.characterRace
        binding.characterHP.text = character.characterHP + " (" + character.characterMaxHP + ")"
        return view
    }

}

class Character(
    var characterName: String,
    var characterLevel: String,
    var characterClass: String,
    var characterRace: String,
    var characterHP: String,
    var characterMaxHP: String
) {}