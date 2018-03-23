package com.crisolutions.commonlibraryktx

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.view.View
import android.view.View.GONE
import android.view.View.MeasureSpec.UNSPECIFIED
import android.view.View.MeasureSpec.makeMeasureSpec
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.animation.Animation

/**
 * Created by Parshav on 3/23/18.
 */

class AnimationTools {
    companion object {
        val DEFAULT_DURATION = 400L

        fun collapseView(view: View, forHeight: Boolean, insant: Boolean, animationFinishCallback: Runnable?) {
            var value = if (forHeight) view.height else view.width

            val animator = ValueAnimator.ofInt(value, 0)

            if (insant) {
                val params = view.layoutParams
                if (forHeight) {
                    params.height = 0
                } else {
                    params.width = 0
                }
                view.layoutParams = params
            } else {
                animator.addListener(object: AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator?) {
                        view.visibility = GONE
                        animationFinishCallback?.run()
                    }
                })
                animator.addUpdateListener {
                    val params = view.layoutParams
                    if (forHeight) {
                        params.height = it.animatedValue as Int
                    } else {
                        params.width = it.animatedValue as Int
                    }
                    view.layoutParams = params
                }

                animator.run {
                    duration = AnimationTools.DEFAULT_DURATION
                    start()
                }
            }
        }

        fun expandView(view: View, forHeight: Boolean, instant: Boolean, animationFinishCallback: Runnable?) {
            view.visibility = VISIBLE
            view.measure(makeMeasureSpec(0, UNSPECIFIED), makeMeasureSpec(0, UNSPECIFIED))
            var value = if (forHeight) view.measuredHeight else view.measuredWidth
            if (instant) {
                val params = view.layoutParams
                if (forHeight) {
                    params.height = value
                } else {
                    params.width = value
                }
                view.layoutParams = params
            } else {
                val animator = ValueAnimator.ofInt(0, value)
                animationFinishCallback?.let {
                    animator.addListener(object: AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator?) {
                            animationFinishCallback.run()
                        }
                    })
                }
                animator.addUpdateListener {
                    val params = view.layoutParams
                    if (forHeight) {
                        params.height = it.animatedValue as Int
                    } else {
                        params.width = it.animatedValue as Int
                    }
                    view.layoutParams = params
                }
                animator.run {
                    duration = AnimationTools.DEFAULT_DURATION
                    start()
                }
            }
        }
    }
}

fun View.animateHeight(collapse: Boolean) {
    this.animateHeight(collapse)
}

fun View.animateHeight(collapse: Boolean, animationFinishCallback: Runnable?) {
    if (collapse) {
        AnimationTools.collapseView(this, true, false, animationFinishCallback)
    } else {
        AnimationTools.expandView(this, true, false, animationFinishCallback)
    }
}

fun View.animateWidth(collapse: Boolean, instant: Boolean) {
    if (collapse) {
        AnimationTools.collapseView(this, false, instant, null)
    } else {
        AnimationTools.expandView(this, false, instant, null)
    }
}
