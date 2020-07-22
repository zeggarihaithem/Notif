package com.example.tdm2_td02_exo2.ui.main.configuration

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tdm2_td02_exo2.R
import com.example.tdm2_td02_exo2.adapter.ContactAdapter
import com.example.tdm2_td02_exo2.broadcastReceiver.SmsBroadcastReceiver
import com.example.tdm2_td02_exo2.data.entity.Contact
import kotlinx.android.synthetic.main.configuration_fragment.*

class ConfigurationFragment : Fragment() {

    companion object {
        fun newInstance() = ConfigurationFragment()
    }

    private lateinit var viewModel: ConfigurationViewModel
    private lateinit var viewModelFactory: ConfigurationViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.configuration_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (checkPermission(Manifest.permission.READ_CONTACTS)) {
            activity?.title = "Configuration"
            //set recycle view adapter
            val recyclerView: RecyclerView = recycler_view as RecyclerView
            recyclerView.layoutManager = LinearLayoutManager(activity)
            recyclerView.setHasFixedSize(true)

            val adapter = ContactAdapter()
            recyclerView.adapter = adapter

            //set ViewModel
            viewModelFactory = ConfigurationViewModelFactory(requireActivity().application)
            viewModel =
                ViewModelProviders.of(this, viewModelFactory)
                    .get(ConfigurationViewModel::class.java)
            viewModel.getAllContact()
                .observe(
                    viewLifecycleOwner,
                    Observer { contacts ->
                        adapter.setContacts(contacts as List<Contact>)
                        SmsBroadcastReceiver.listContact = contacts as ArrayList<Contact>
                    })

            //add contact on click
            adapter.setOnItemClickListener(object : ContactAdapter.OnItemClickListener {
                override fun onItemClick(contact: Contact?) {
                    viewModel.addContact(contact!!)
                    Toast.makeText(activity, "" + contact.name + " est ajouté", Toast.LENGTH_SHORT)
                        .show()
                }
            })
        }


    }

    private fun checkPermission(permission: String): Boolean {
        val check = ContextCompat.checkSelfPermission(requireActivity(), permission)
        return (check == PackageManager.PERMISSION_GRANTED)
    }

}
