package com.example.lab7

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.lab7.data.Client
import com.example.lab7.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {
    private lateinit var _binding: FragmentDetailBinding
    private var _listener: OnDataPassListener? = null
    private lateinit var _client: Client

    interface OnDataPassListener {
        fun back()
        fun saveChanges(client: Client)
        fun delete(clientId: String)
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
            _client = it.getParcelable(ARG_CLIENT) ?: Client()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBinding.inflate(inflater)
        val view = _binding.root

        _binding.firstNameInput.setText(_client.firstName)
        _binding.lastNameInput.setText(_client.lastName)
        _binding.emailInput.setText(_client.email)
        _binding.ageInput.setText(_client.age)
        _binding.genderInput.setText(_client.gender)
        _binding.scheduleInput.setText(_client.schedule)
        _binding.contactInfoInput.setText(_client.contactInfo)

        _binding.backButton.setOnClickListener {
            _listener?.back()
        }

        _binding.saveChangesButton.setOnClickListener {
            val client = Client(
                id = _client.id,
                firstName = _binding.firstNameInput.text.toString(),
                lastName = _binding.lastNameInput.text.toString(),
                email = _binding.emailInput.text.toString(),
                age = _binding.ageInput.text.toString(),
                gender = _binding.genderInput.text.toString(),
                schedule = _binding.scheduleInput.text.toString(),
                contactInfo = _binding.contactInfoInput.text.toString()
            )

            _listener?.saveChanges(client)
        }
        _binding.deleteButton.setOnClickListener {
            _listener?.delete(_client.id)
        }

        return view
    }

    override fun onDetach() {
        super.onDetach()
        _listener = null
    }

    companion object {
        private const val ARG_CLIENT = "client"

        fun newInstance(client: Client): DetailFragment {
            val fragment = DetailFragment()
            val args = Bundle()
            args.putParcelable(ARG_CLIENT, client)
            fragment.arguments = args
            return fragment
        }
    }
}