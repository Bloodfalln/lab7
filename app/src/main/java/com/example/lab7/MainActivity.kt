package com.example.lab7

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.lab7.data.Client
import com.example.lab7.data.ClientDatabaseHelper
import com.example.lab7.data.ClientRepository

class MainActivity : AppCompatActivity(),
    FormFragment.OnDataPassListener,
    ListFragment.OnDataPassListener,
    DetailFragment.OnDataPassListener
{
    private lateinit var clientRepository: ClientRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dbHelper = ClientDatabaseHelper(this)
        clientRepository = ClientRepository(dbHelper)

        renderFormAndList()
    }

    override fun add(newValue: Client) {
        clientRepository.addClient(newValue)
        renderFormAndList()
    }

    override fun openDetailFragment(client: Client) {
        val formFragment = DetailFragment.newInstance(client)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.firstFragment, formFragment)
            .remove(supportFragmentManager.findFragmentById(R.id.secondFragment) ?: return)
            .addToBackStack(null)
            .commit()
    }

    override fun back() {
        supportFragmentManager.popBackStack()
    }

    override fun saveChanges(client: Client) {
        clientRepository.updateClient(client)
        renderFormAndList()
    }

    override fun delete(clientId: String) {
        clientRepository.deleteClient(clientId)
        renderFormAndList()
    }

    private fun renderFormAndList() {
        val clients = clientRepository.getAllClients()

        val formFragment = FormFragment.newInstance()
        val listFragment = ListFragment.newInstance(ArrayList(clients))

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.firstFragment, formFragment)
            .replace(R.id.secondFragment, listFragment)
            .addToBackStack(null)
            .commit()
    }
}