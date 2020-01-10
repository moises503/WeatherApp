package com.sngular.core.arch.executor

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import com.sngular.core.arch.threads.UIScheduler

class UIThread: UIScheduler {
    override fun getScheduler(): Scheduler = AndroidSchedulers.mainThread()
}