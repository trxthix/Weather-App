/*
 * Сreated by Igor Pokrovsky. 2020/4/23
 */

package com.github.nigtime456.weather.utils.rx

import io.reactivex.Scheduler

/**
 * Интерфейс позволяющий сделать инжект Rx дистпетчеров,
 * позволяя подменять дистетчеры не прибегая к RxJavaPlugins.setIoSchedulerHandler() or etc
 */
interface SchedulerProvider {
    /**
     * UI поток
     */
    fun ui(): Scheduler

    /**
     * Вспомогательные потоки, для ресурсов, которым не нужна сихнронизация
     */
    fun io(): Scheduler

    fun single(): Scheduler

    fun database(): Scheduler
}