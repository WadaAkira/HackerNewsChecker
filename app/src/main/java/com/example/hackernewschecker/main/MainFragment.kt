package com.example.hackernewschecker.main

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.hackernewschecker.HackerNewsCheckerApplication
import com.example.hackernewschecker.databinding.MainFragmentBinding
import com.example.hackernewschecker.usecase.HackerNewsUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

/**
 * Hacker News API と通信し、結果を表示するフラグメント
 */
class MainFragment : Fragment(), CoroutineScope {
    private var _binding: MainFragmentBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException("MainFragmentBinding is null.")

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    @Inject
    internal lateinit var usecase: HackerNewsUseCase

    companion object {
        /**
         * フラグメントを生成する
         */
        fun newInstance(): MainFragment {
            return MainFragment()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        (activity?.application as? HackerNewsCheckerApplication)
            ?.appComponent
            ?.inject(this)
            ?: throw IllegalStateException("Application or Activity is null.")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        launch {
            withContext(Dispatchers.IO) {
                usecase.loadCurrentNewsIdList().enqueue(object : Callback<List<Int>> {
                    override fun onFailure(call: Call<List<Int>>, t: Throwable) {
                        Log.d("wada", "kitenai")
                    }

                    override fun onResponse(call: Call<List<Int>>, response: Response<List<Int>>) {
                        Log.d("wada", "kiteru")
                    }
                })
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}