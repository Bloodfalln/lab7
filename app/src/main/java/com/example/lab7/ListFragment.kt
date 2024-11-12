package com.example.lab7

import android.R
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lab7.adapters.ClientAdapter
import com.example.lab7.data.Client
import com.example.lab7.databinding.FragmentListBinding
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale

class ListFragment : Fragment() {
    private lateinit var _binding: FragmentListBinding
    private var _listener: OnDataPassListener? = null
    private lateinit var _clients: ArrayList<Client>
    private lateinit var filteredClients: ArrayList<Client>

    interface OnDataPassListener {
        fun openDetailFragment(client: Client)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnDataPassListener) {
            _listener = context
        } else {
            throw RuntimeException("$context must implement OnDataPassListener")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            _clients = it.getParcelableArrayList<Client>(ARG_CLIENTS) ?: ArrayList()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListBinding.inflate(inflater)
        val view = _binding.root

        filteredClients = ArrayList(_clients)

        val recyclerView: RecyclerView = _binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
        val adapter = ClientAdapter(filteredClients) { client ->
            _listener?.openDetailFragment(client)
        }
        recyclerView.adapter = adapter

        setupSortSpinner(adapter)

        val itemDecoration = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        recyclerView.addItemDecoration(itemDecoration)

        updateEmptyViewVisibility()

        return view
    }

    override fun onDetach() {
        super.onDetach()
        _listener = null
    }

    companion object {
        private const val ARG_CLIENTS = "clients"

        fun newInstance(stringList: ArrayList<Client>): ListFragment {
            val fragment = ListFragment()
            val args = Bundle()
            args.putParcelableArrayList(ARG_CLIENTS, stringList)
            fragment.arguments = args
            return fragment
        }
    }

    private fun setupSortSpinner(adapter: ClientAdapter) {
        val sortOptions = arrayOf("За алфавітом", "За датою", "Без сортування")

        _binding.sortSpinner.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, sortOptions)

        _binding.sortSpinner.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                when (position) {
                    0 -> sortClientsByName()  // Сортування за іменем
                    1 -> sortClientsByDate()  // Сортування за датою
                    2 -> filteredClients = ArrayList(_clients)  // Без сортування
                }
                adapter.notifyDataSetChanged()
                updateEmptyViewVisibility()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        })
    }


    private fun sortClientsByName() {
        filteredClients.sortBy { it.firstName + " " + it.lastName }
    }

    private fun sortClientsByDate() {
        val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())

        filteredClients.sortBy { client ->
            try {
                dateFormat.parse(client.schedule)
            } catch (e: ParseException) {
                null
            }
        }
    }

    private fun updateEmptyViewVisibility() {
        if (filteredClients.isEmpty()) {
            _binding.recyclerView.visibility = View.GONE
            _binding.emptyView.visibility = View.VISIBLE
        } else {
            _binding.recyclerView.visibility = View.VISIBLE
            _binding.emptyView.visibility = View.GONE
        }
    }
}


