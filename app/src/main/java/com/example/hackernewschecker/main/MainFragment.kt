package com.example.hackernewschecker.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.hackernewschecker.databinding.MainFragmentBinding
import com.example.hackernewschecker.usecase.HackerNewsUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}