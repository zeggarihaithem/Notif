package com.example.tdm2_td02_exo2.ui.main.listContact

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tdm2_td02_exo2.R
import com.example.tdm2_td02_exo2.adapter.ContactAdapter
import com.example.tdm2_td02_exo2.broadcastReceiver.SmsBroadcastReceiver
import com.example.tdm2_td02_exo2.data.entity.Contact
import kotlinx.android.synthetic.main.list_contact_fragment.*


class ListContactFragment : Fragment() {

    companion object {
        fun newInstance() =
            ListContactFragment()
    }

    private lateinit var viewModel: ListContactViewModel
    private lateinit var viewModelFactory: ListContactViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.list_contact_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)
        //set recycle view adapter
        val recyclerView: RecyclerView = recycler_view as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.setHasFixedSize(true)

        val adapter = ContactAdapter()
        recyclerView.adapter = adapter

        //set ViewModel
        viewModelFactory = ListContactViewModelFactory(requireActivity().application)
        viewModel =
            ViewModelProviders.of(this, viewModelFactory)
                .get(ListContactViewModel::class.java)
        viewModel.getContact()
            .observe(
                viewLifecycleOwner,
                Observer { contacts ->
                    adapter.setContacts(contacts as List<Contact>)
                    SmsBroadcastReceiver.listContact = contacts as ArrayList<Contact>
                })

        //delete contact on swiped
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(//Drag and drop methode
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                adapter.getContactAt(viewHolder.adapterPosition)
                    ?.let { viewModel.deleteContact(it) }
                Toast.makeText(activity, "Contact supprimé", Toast.LENGTH_SHORT).show()
            }
        }).attachToRecyclerView(recyclerView)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_contact_top_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.configuration -> {
                val navController: NavController =
                    Navigation.findNavController(requireActivity(), R.id.navigation_fragment)
                navController.navigate(R.id.list_to_configuration_action)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}
