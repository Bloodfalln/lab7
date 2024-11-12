package com.example.lab7

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.lab7.data.Client
import com.example.lab7.databinding.FragmentFormBinding
import java.util.UUID

class FormFragment : Fragment() {
    private lateinit var _binding: FragmentFormBinding
    private var _listener: OnDataPassListener? = null

    interface OnDataPassListener {
        fun add(newValue: Client)
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
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFormBinding.inflate(inflater)
        val view = _binding.root

        _binding.addClientButton.setOnClickListener {
            val client = Client(
                id = UUID.randomUUID().toString(),
                firstName = _binding.firstNameInput.text.toString(),
                lastName = _binding.lastNameInput.text.toString(),
                email = _binding.emailInput.text.toString(),
                age = _binding.ageInput.text.toString(),
                gender = _binding.genderInput.text.toString(),
                schedule = _binding.scheduleInput.text.toString(),
                contactInfo = _binding.contactInfoInput.text.toString()
            )

            _listener?.add(client)
        }

        return view
    }

    override fun onDetach() {
        super.onDetach()
        _listener = null
    }

    companion object {
        fun newInstance(): FormFragment {
            return FormFragment()
        }
    }
}