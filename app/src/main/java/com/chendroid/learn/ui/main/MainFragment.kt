package com.chendroid.learn.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.chendroid.learn.R
import com.chendroid.learn.widget.TestView
import com.facebook.stetho.common.LogUtil

class MainFragment : Fragment() {

  companion object {
    fun newInstance() = MainFragment()
  }

  private lateinit var viewModel: MainViewModel

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    return inflater.inflate(R.layout.main_fragment, container, false)
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
    // TODO: Use the ViewModel
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    initView(view)
  }

  private fun initView(view: View) {

    LogUtil.d("zc_test", "initView()")
    val testView = view.findViewById<TestView>(R.id.testView)

    testView.post{
      LogUtil.d("zc_test", "这里获取到宽和高： ${testView.width}, height is ${testView.height}")
    }

  }

}