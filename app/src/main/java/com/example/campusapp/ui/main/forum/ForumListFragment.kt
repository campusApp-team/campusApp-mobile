package com.example.campusapp.ui.main.forum

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.campusapp.R

/**
 * A fragment representing a list of Items.
 * Activities containing this fragment MUST implement the
 * [ForumListFragment.OnForumFragmentInteractionListener] interface.
 */
class ForumListFragment : Fragment() {

    // TOD: Customize parameters
    private var columnCount = 2

    private var listener: OnForumFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.forum_list, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
                adapter =
                    ForumsRecyclerViewAdapter(
                        listener
                    )
            }
        }
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnForumFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnListFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     * See the Android Training lesson
     * [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnForumFragmentInteractionListener {
        // TOD Update argument type and name
        fun onForumFragmentInteraction(id: String)
    }

    // ithinte onnum aavashyilla....pinne oyvaakkaam
    companion object {
        // TOD Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"
        // TOD Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            ForumListFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}
