package com.sngular.core.arch.threads

import io.reactivex.Scheduler

interface UIScheduler {
    fun getScheduler(): Scheduler
}