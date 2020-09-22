package com.example.madlevel3task2

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResultListener
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import kotlinx.android.synthetic.main.fragment_portals.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class PortalsFragment : Fragment() {
    private val portals = arrayListOf<Portal>()
    private val portalAdapter = PortalAdapter(portals)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_portals, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
    }

    private fun initViews() {
        rv_portals.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        rv_portals.adapter = portalAdapter

        observeAddPortalResult()
    }

    private fun observeAddPortalResult() {
        var portalTitle: String? = null
        setFragmentResultListener(PORTAL_REQUEST_KEY) { _, bundle ->
            bundle.getString(
                BUNDLE_PORTAL_TITLE_KEY
            )?.let {
                portalTitle = it
                bundle.getString(
                    BUNDLE_PORTAL_URL_KEY
                )?.let {
                    val newPortal = Portal(portalTitle!!, it)
                    portals.add(newPortal)
                    portalAdapter.notifyDataSetChanged()
                } ?: Log.e("PortalFragment", "Request triggered but empty portal url!")
            } ?: Log.e("PortalFragment", "Request triggered, but empty portal title!")
        }
    }
}