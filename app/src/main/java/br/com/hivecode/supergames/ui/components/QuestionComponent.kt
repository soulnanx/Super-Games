package br.com.hivecode.supergames.ui.components

import android.content.Context
import android.os.Handler
import android.support.v7.widget.CardView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.animation.DecelerateInterpolator
import android.widget.Button
import android.widget.TextView
import br.com.hivecode.supergames.R


class QuestionComponent(private val ctx: Context?,
                        private val viewGroup: ViewGroup?,
                        private val title : String?,
                        private val hint : String?,
                        private val callback: ((String) -> Unit?)?){

    var content : View? = LayoutInflater.from(ctx).inflate(R.layout.question_layout, viewGroup, false)


    init {
        val titleTextView = content?.findViewById<TextView>(R.id.question_title)
        titleTextView?.text = title

        val answerEditText = content?.findViewById<TextView>(R.id.question_answer_label)
        answerEditText?.hint = hint

        val card = content?.findViewById<CardView>(R.id.question_card)

        setAnimationIn(ctx!!, card!!)

        val okBtn = content?.findViewById<Button>(R.id.question_ok)
        okBtn?.setOnClickListener {
            callback?.invoke(answerEditText?.text.toString())

            card.animate()
                .setStartDelay(500)
                .scaleX(-10f)
                .scaleY(-10f)


                .setDuration(500)
                .interpolator = DecelerateInterpolator()
        }

        viewGroup?.addView(content)

    }

    private fun setAnimationOut(ctx: Context, card: View) {
        val resId = br.com.hivecode.supergames.R.anim.scale_out_anim
        val animation = AnimationUtils.loadAnimation(ctx, resId)
        card.animation = animation
        card.animation.start()
    }

    data class Builder(var ctx: Context? = null,
                       var viewGroup: ViewGroup? = null,
                       var title : String? = null,
                       var hint : String? = null,
                       var callback: ((String) -> Unit?)? = null){

        fun withContext(ctx: Context) = apply { this.ctx = ctx }
        fun insideOf(viewGroup: ViewGroup) = apply { this.viewGroup = viewGroup }
        fun withTitle(title: String) = apply { this.title = title }
        fun withHint(hint: String?) = apply { this.hint = hint }
        fun withCallback(callback: (String) -> Unit) = apply { this.callback = callback }

        fun create() {

            if (ctx == null){
                throw IllegalStateException("O contexto é obrigatorio")
            }

            QuestionComponent(ctx, viewGroup, title, hint, callback)

        }

    }

    private fun setAnimationIn(ctx: Context, view: View) {
        val resId = br.com.hivecode.supergames.R.anim.scale_anim
        val animation = AnimationUtils.loadAnimation(ctx, resId)
        view.animation = animation
        view.animation.start()
    }


}