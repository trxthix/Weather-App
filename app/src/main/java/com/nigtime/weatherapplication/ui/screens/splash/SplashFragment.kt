/*
 * Сreated by Igor Pokrovsky. 2020/4/23 
 */

package com.nigtime.weatherapplication.ui.screens.splash

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import com.nigtime.weatherapplication.R
import com.nigtime.weatherapplication.ui.animation.BackgroundColorProperty
import com.nigtime.weatherapplication.ui.screens.common.BaseFragment
import com.nigtime.weatherapplication.ui.screens.common.ExtendLifecycle
import com.nigtime.weatherapplication.ui.screens.common.NavigationController
import com.nigtime.weatherapplication.ui.screens.common.Screen
import com.nigtime.weatherapplication.ui.tools.ThemeHelper
import kotlinx.android.synthetic.main.fragment_splash.*

/**
 * TODO поправь сплэш
 * Сплэш должен отображать анимацию при старте и показать её бесконечно, пока
 * не будет выполнена инициализация. (инициализация может продлиться долго)
 * Так же надо предусмотреть минимальный тайминг отображения.
 */
class SplashFragment : BaseFragment<SplashView, WrongSplashPresenter, SplashFragment.Listener>(),
    SplashView {

    interface Listener : NavigationController

    override fun provideMvpPresenter(): WrongSplashPresenter {
        //TODO mock
        return WrongSplashPresenter()
    }

    override fun provideListenerClass(): Class<Listener>? = Listener::class.java

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attach(this, lifecycleBus, ExtendLifecycle.DESTROY_VIEW)
        presenter.checkAndSetCitiesDictionary()
    }

    override fun playAnimation() {
        val backgroundAnimator = getBackgroundAnimator()
        val rotateAnimator = getRotateAnimator()

        AnimatorSet().apply {
            play(backgroundAnimator).with(rotateAnimator)
            start()
        }

    }

    private fun getRotateAnimator(): Animator {
        return ObjectAnimator.ofFloat(fragmentSplashIco, View.ROTATION, 0f, 360f).apply {
            repeatCount = ValueAnimator.INFINITE
            repeatMode = ValueAnimator.REVERSE
            interpolator = LinearInterpolator()
            duration = resources.getInteger(R.integer.splash_rotate_anim).toLong()
        }
    }

    private fun getBackgroundAnimator(): ObjectAnimator {
        val startColor = ThemeHelper.getColor(requireContext(), R.attr.themeNightColor)
        val endColor = ThemeHelper.getColor(requireContext(), R.attr.themeDayColor)
        return ObjectAnimator.ofArgb(
            fragmentSplashRoot,
            BackgroundColorProperty(),
            startColor,
            endColor
        ).apply {
            duration = resources.getInteger(R.integer.splash_rotate_color_transition).toLong()
        }
    }

    override fun delayedLoadPagerScreen() {
        listener?.navigateTo(Screen.LIST_CITIES)
    }

    override fun delayedLoadSearchCityScreen() {

    }

}