package com.chendroid.learn.ui.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chendroid.learn.core.PreferenceHelper
import com.chendroid.learn.core.ResponseResult
import com.chendroid.learn.ui.data.LoginUseCase
import com.chendroid.learn.ui.fragment.AccountFragment
import com.chendroid.learn.util.DebugLog
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * @author      : zhaochen
 * @date        : 2021/9/1
 * @description : 获取该 accountViewModel； Activity 间生效
 */
class AccountViewModel : ViewModel() {

    // accountFragment 的状态，true: 代表显示；false: 代表隐藏
    private val _accountState: MutableLiveData<Boolean> = MutableLiveData(true)
    val accountState: LiveData<Boolean> get() = _accountState

    // 是否登陆了账号
    private val _isLogin: MutableLiveData<Boolean> = MutableLiveData()

    init {
        updateIsLogin(
            PreferenceHelper.getValue(
                AccountFragment.ACCOUNT_SP_NAME,
                AccountFragment.LOGIN_KEY, false
            )
        )
    }

    val isLogin: LiveData<Boolean> get() = _isLogin

    private val loginUseCase: LoginUseCase by lazy {
        LoginUseCase()
    }

    fun updateAccountState(state: Boolean) {
        _accountState.value = state
    }

    fun updateIsLogin(isLogin: Boolean) {
        _isLogin.value = isLogin
    }

    fun loginIn(username: String, password: String) {

        viewModelScope.launch(IO) {
            val loginResult = loginUseCase.loginAccount(username, password)
            if (loginResult is ResponseResult.Success) {
                withContext(Main) {
                    DebugLog.d("zc_test", "登陆成功")
                    PreferenceHelper.setValue(
                        AccountFragment.ACCOUNT_SP_NAME,
                        AccountFragment.LOGIN_KEY, true
                    )
                    PreferenceHelper.setValue(
                        AccountFragment.ACCOUNT_SP_NAME,
                        AccountFragment.USERNAME_KEY, username
                    )
                    PreferenceHelper.setValue(
                        AccountFragment.ACCOUNT_SP_NAME,
                        AccountFragment.PASSWORD_KEY, password
                    )
                    updateIsLogin(true)
                }
            } else if (loginResult is ResponseResult.Error) {
                DebugLog.d("zc_test", "登陆失败，error is ${loginResult.exception.toString()}")

            }
        }
    }

    fun logout() {
        viewModelScope.launch(IO) {
            val logoutResult = loginUseCase.logoutAccount()
            if (logoutResult is ResponseResult.Success) {
                withContext(Main) {
                    DebugLog.d("zc_test", "退出成功")
                    PreferenceHelper.setValue(
                        AccountFragment.ACCOUNT_SP_NAME,
                        AccountFragment.LOGIN_KEY, false
                    )
                    PreferenceHelper.setValue(
                        AccountFragment.ACCOUNT_SP_NAME,
                        AccountFragment.USERNAME_KEY, ""
                    )
                    PreferenceHelper.setValue(
                        AccountFragment.ACCOUNT_SP_NAME,
                        AccountFragment.PASSWORD_KEY, ""
                    )
                    updateIsLogin(false)
                }
            } else {
                DebugLog.d("zc_test", "退出失败了， error is ${logoutResult.toString()}")

            }

        }
    }


}