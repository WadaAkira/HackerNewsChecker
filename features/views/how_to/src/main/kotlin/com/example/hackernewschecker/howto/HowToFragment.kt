package com.example.hackernewschecker.howto

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.hackernewschecker.howto.databinding.HowToFragmentBinding

/**
 * 使い方を表示するフラグメント
 */
class HowToFragment : Fragment() {
    companion object {
        /**
         * フラグメントを生成する
         */
        fun newInstance(): HowToFragment {
            return HowToFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = HowToFragmentBinding.inflate(inflater, container, false).root
}