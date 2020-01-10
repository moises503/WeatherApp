package com.sngular.core.navigation

import androidx.fragment.app.Fragment
import com.sngular.core.util.requireArguments


open class BaseFragment : Fragment() {
    fun <T : BaseKey> getKey(): T? = requireArguments.getParcelable<T>("KEY")
}