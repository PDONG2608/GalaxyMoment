package com.example.galaxymoment.utils

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.content.res.Resources
import android.view.View

class AnimationHelper {
    companion object {

        /**
         * This function makes an animation that increases the height of a view from the given heightFrom to the given heightTo.
         * The animation duration is given in milliseconds.
         * The view will be invisible until the animation starts.
         * The height is given in dp.
         *
         * @param view The view which height will be animated.
         * @param heightFrom The initial height of the view in dp.
         * @param heightTo The final height of the view in dp.
         * @param duration The duration of the animation in milliseconds.
         */
        fun makeAnimationChangeHeight(view: View, heightFrom: Int, heightTo: Int, duration: Long) {
            view.visibility = View.VISIBLE
            val animator = ValueAnimator.ofInt(heightFrom.dpToPx(), heightTo.dpToPx())

            animator.addUpdateListener { valueAnimator ->
                val animatedValue = valueAnimator.animatedValue as Int
                val layoutParams = view.layoutParams
                layoutParams.height = animatedValue
                view.layoutParams = layoutParams
            }

            animator.duration = duration
            animator.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    if (heightTo == 0) {
                        view.visibility = View.GONE
                    }
                }
            })
            animator.start()
        }


        /**
         * This function makes an animation that moves a view up or down.
         * The direction of the animation is determined by the sign of the height parameter.
         * A positive height will move the view down, a negative height will move the view up.
         * The animation duration is given in milliseconds.
         *
         * @param view The view which will be animated.
         * @param height The height of the animation in dp. If positive, the view will move down, if negative, the view will move up.
         * @param duration The duration of the animation in milliseconds.
         */
        fun makeAnimationUpDown(view: View, height: Int, duration: Long) {
            val currentY = view.y
            val animator = ValueAnimator.ofFloat(currentY, currentY - height)
            animator.duration = duration
            animator.addUpdateListener { animation ->
                val value = animation.animatedValue as Float
                view.y = value
            }
            animator.start()
        }

        private fun Int.dpToPx(): Int {
            return (this * Resources.getSystem().displayMetrics.density).toInt()
        }
    }
}