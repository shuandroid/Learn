package com.chendroid.learn.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chendroid.learn.core.BaseFragment
import com.chendroid.learn.core.PreferenceHelper
import com.chendroid.learn.databinding.AccountInfoLayoutBinding
import com.chendroid.learn.databinding.LoginInLayoutBinding
import com.chendroid.learn.ui.vm.AccountViewModel
import com.chendroid.learn.util.DebugLog
import com.chendroid.learn.util.toast

/**
 * @author      : zhaochen
 * @date        : 2021/8/31
 * @description : 账号界面
 */
class AccountFragment : BaseFragment() {

    companion object {
        const val ACCOUNT_SP_NAME = "account_sp"
        const val LOGIN_KEY = "login"
        const val USERNAME_KEY = "username"
        const val PASSWORD_KEY = "password"

        /**
         * 在这里传递一些数据，通过 bundle 设置给 fragment
         */
        fun newInstance(): AccountFragment {
            val bundle = Bundle()
            bundle.putString("targetName", "ArticleListFragment")
            val fragment = AccountFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    // 获取 activity scope 的 accountViewModel
    private val accountViewModel: AccountViewModel by lazy {
        getActivityScopeViewModel(AccountViewModel::class.java)
    }

    private var isLogin: Boolean = PreferenceHelper.getValue(ACCOUNT_SP_NAME, LOGIN_KEY, false)

    private var _binding: AccountInfoLayoutBinding? = null
    private val binding get() = _binding!!

    private lateinit var loginView: LoginInLayoutBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = AccountInfoLayoutBinding.inflate(inflater, container, false)
        loginView = LoginInLayoutBinding.bind(binding.root)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViewModel()
        initView()
    }

    private fun bindViewModel() {
        accountViewModel.isLogin.observe(viewLifecycleOwner) {
            DebugLog.d("zc_test", "initViewModel() 收到结果，为 ${it}")
            if (it) {
                requireContext().toast("登陆成功了")
            }
            updateViewState(it)
        }
    }

    private fun initView() {
        DebugLog.d("zc_test", "initView() 开始")
        binding.root.setTargetView(binding.accountUserAvatar)
        initLoginView()
        initAccountInfoView()
    }

    private fun initLoginView() {
        if (isLogin) {
            loginView.loginConfirmButton.visibility = View.GONE
            loginView.loginAccountText.visibility = View.GONE
            loginView.loginPasswordText.visibility = View.GONE
            return
        }

        loginView.loginConfirmButton.setOnClickListener {
            // 登陆检查
            val username = loginView.loginAccountText.text.toString()
            val password = loginView.loginPasswordText.text.toString()
            if (username.isEmpty() || password.isEmpty()) {
                context?.toast("账号和密码不能为空")
                return@setOnClickListener
            }
            DebugLog.d("zc_test", "点击了登陆")
            loginIn(username, password)
        }
    }

    private fun initAccountInfoView() {
        if (!isLogin) {
            binding.accountTodo.visibility = View.GONE
            binding.accountCollect.visibility = View.GONE
        } else {
            loginView.loginConfirmButton.visibility = View.GONE
            loginView.loginAccountText.visibility = View.GONE
            loginView.loginPasswordText.visibility = View.GONE
            binding.accountTodo.visibility = View.VISIBLE
            binding.accountCollect.visibility = View.VISIBLE
        }
    }

    private fun updateViewState(isLoginSuccess: Boolean) {
        if (!isLoginSuccess) {
            binding.accountTodo.visibility = View.GONE
            binding.accountCollect.visibility = View.GONE
            binding.logoutButton.visibility = View.GONE
            loginView.loginConfirmButton.visibility = View.VISIBLE
            loginView.loginAccountText.visibility = View.VISIBLE
            loginView.loginPasswordText.visibility = View.VISIBLE
        } else {
            loginView.loginConfirmButton.visibility = View.GONE
            loginView.loginAccountText.visibility = View.GONE
            loginView.loginPasswordText.visibility = View.GONE
            binding.accountTodo.visibility = View.VISIBLE
            binding.accountCollect.visibility = View.VISIBLE
            binding.accountUserName.text = PreferenceHelper.getValue(
                AccountFragment.ACCOUNT_SP_NAME,
                AccountFragment.USERNAME_KEY, ""
            )

            binding.logoutButton.visibility = View.VISIBLE
            binding.logoutButton.setOnClickListener {
                logout()
            }

        }
    }

    private fun loginIn(username: String, password: String) {
        accountViewModel.loginIn(username, password)
    }

    private fun logout() {
        DebugLog.d("zc_test", "准备退出操作logout()")
        accountViewModel.logout()
    }

}