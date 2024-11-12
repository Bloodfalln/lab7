package com.example.lab7.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lab7.R
import com.example.lab7.data.Client

class ClientAdapter(private val _clients: List<Client>, private val onItemClick: (Client) -> Unit) :
    RecyclerView.Adapter<ClientAdapter.DishViewHolder>() {

    inner class DishViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val fullName: TextView = itemView.findViewById(R.id.client_full_name)

        fun bind(client: Client) {
            fullName.text = "${client.lastName} ${client.firstName}"
            itemView.setOnClickListener { onItemClick(client) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DishViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return DishViewHolder(view)
    }

    override fun onBindViewHolder(holder: DishViewHolder, position: Int) {
        holder.bind(_clients[position])
    }

    override fun getItemCount(): Int = _clients.size


}