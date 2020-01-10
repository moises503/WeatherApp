package com.sngular.core.navigation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.zhuinden.simplestack.BackstackDelegate
import com.zhuinden.simplestack.History
import com.zhuinden.simplestack.StateChange
import com.zhuinden.simplestack.StateChanger

@Suppress("DEPRECATION")
abstract class BaseNavigationActivity : AppCompatActivity(), StateChanger {

    private lateinit var backStackDelegate: BackstackDelegate
    private lateinit var fragmentStateChanger: FragmentStateChanger

    override fun onCreate(savedInstanceState: Bundle?) {
        backStackDelegate = BackstackDelegate()
        val defaultFirstKey = defaultFirstKey()
        backStackDelegate.onCreate(savedInstanceState,
            lastCustomNonConfigurationInstance,
            History.single(defaultFirstKey))
        backStackDelegate.registerForLifecycleCallbacks(this)
        super.onCreate(savedInstanceState)
        initCode()
        setContentView(getLayout())
        fragmentStateChanger = FragmentStateChanger(supportFragmentManager, getFragmentContainer())
        backStackDelegate.setStateChanger(this)
        initViews()
    }

    abstract fun getLayout() : Int

    abstract fun getFragmentContainer() : Int

    abstract fun defaultFirstKey() : BaseKey

    open fun initViews() {
        //TODO: here init your required views
    }

    open fun initCode() {
        //TODO: here init your required code
    }

    override fun onRetainCustomNonConfigurationInstance() =
        backStackDelegate.onRetainCustomNonConfigurationInstance()

    override fun onBackPressed() {
        if (!backStackDelegate.onBackPressed()) {
            super.onBackPressed()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        backStackDelegate.backstack.goBack()
        return true
    }

    override fun handleStateChange(stateChange: StateChange, completionCallback: StateChanger.Callback) {
        if (stateChange.topNewState<Any>() == stateChange.topPreviousState()) {
            completionCallback.stateChangeComplete()
            return
        }
        fragmentStateChanger.handleStateChange(stateChange)
        completionCallback.stateChangeComplete()
    }


    fun navigateTo(key: BaseKey) {
        backStackDelegate.backstack.goTo(key)
    }

    fun replaceHistory(rootKey: BaseKey) {
        backStackDelegate.backstack.setHistory(History.single(rootKey), StateChange.REPLACE)
    }
}
