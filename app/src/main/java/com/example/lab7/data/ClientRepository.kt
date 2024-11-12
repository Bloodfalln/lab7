package com.example.lab7.data

import android.content.ContentValues

class ClientRepository(private val dbHelper: ClientDatabaseHelper) {

    fun addClient(client: Client) {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("id", client.id)
            put("firstName", client.firstName)
            put("lastName", client.lastName)
            put("email", client.email)
            put("age", client.age)
            put("gender", client.gender)
            put("schedule", client.schedule)
            put("contactInfo", client.contactInfo)
        }
        db.insert("clients", null, values)
    }

    fun getAllClients(): List<Client> {
        val clients = mutableListOf<Client>()
        val db = dbHelper.readableDatabase
        val cursor = db.query("clients", null, null, null, null, null, null)

        with(cursor) {
            while (moveToNext()) {
                val client = Client(
                    id = getString(getColumnIndexOrThrow("id")),
                    firstName = getString(getColumnIndexOrThrow("firstName")),
                    lastName = getString(getColumnIndexOrThrow("lastName")),
                    email = getString(getColumnIndexOrThrow("email")),
                    age = getString(getColumnIndexOrThrow("age")),
                    gender = getString(getColumnIndexOrThrow("gender")),
                    schedule = getString(getColumnIndexOrThrow("schedule")),
                    contactInfo = getString(getColumnIndexOrThrow("contactInfo"))
                )
                clients.add(client)
            }
        }
        cursor.close()
        return clients
    }

    fun updateClient(client: Client) {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("firstName", client.firstName)
            put("lastName", client.lastName)
            put("email", client.email)
            put("age", client.age)
            put("gender", client.gender)
            put("schedule", client.schedule)
            put("contactInfo", client.contactInfo)
        }
        db.update("clients", values, "id = ?", arrayOf(client.id))
    }

    fun deleteClient(clientId: String) {
        val db = dbHelper.writableDatabase
        db.delete("clients", "id = ?", arrayOf(clientId))
    }
}
