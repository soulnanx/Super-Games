package br.com.hivecode.supergames.ui.components

import android.content.Context
import android.os.Build
import android.support.annotation.RequiresApi
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import android.widget.LinearLayout
import br.com.hivecode.supergames.R
import kotlinx.android.synthetic.main.custom_loading.view.*


class CustomLoading @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
    defStyleRes: Int = 0
) : LinearLayout(context, attrs, defStyle, defStyleRes) {

    init {

        LayoutInflater.from(context)
            .inflate(R.layout.custom_loading, this, true)

        orientation = VERTICAL

        loading.let {
            val rotateAnimation = RotateAnimation(
                0f,
                360f,
                Animation.RELATIVE_TO_SELF,
                0.5f, Animation.RELATIVE_TO_SELF,
                0.5f
            )
            rotateAnimation.duration = 700
            rotateAnimation.interpolator = LinearInterpolator()
            rotateAnimation.repeatCount = Animation.INFINITE
            it.startAnimation(rotateAnimation)
        }
    }
}
