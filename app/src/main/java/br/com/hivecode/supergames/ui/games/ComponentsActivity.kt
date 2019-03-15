package br.com.hivecode.supergames.ui.games

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import br.com.hivecode.supergames.R
import br.com.hivecode.supergames.data.entity.Question
import br.com.hivecode.supergames.ui.components.QuestionComponent
import kotlinx.android.synthetic.main.activity_games_components.*

class ComponentsActivity : AppCompatActivity(){

    companion object {
        fun newIntent(context: Context) : Intent {
            return Intent(context, ComponentsActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_games_components)
        init()
    }

    private fun init() {
        setQuestions()
    }

    private fun setQuestions() {
        val list = getQuestionListFake()
        createQuestionComponents(list)
    }

    private fun createQuestionComponents(list: List<Question>) {
        list.forEachIndexed { index, question ->  createComponent(index, question)}
    }

    private fun createComponent(index : Int, question: Question) {

        Handler().postDelayed({
            QuestionComponent.Builder()
                .withContext(this@ComponentsActivity)
                .insideOf(activity_games_components_content)
                .withHint(question.hint)
                .withTitle(question.title)
                .withCallback { showValue(question) }
                .create()
        }, (80 * index).toLong())


    }

    private fun showValue(question: Question) {
        Toast.makeText(this@ComponentsActivity, question.title, Toast.LENGTH_SHORT).show()
    }

    private fun getQuestionListFake() : List<Question> {
        return listOf<Question>(
            Question("Qual é o seu nome?", "nome completo", 1),
            Question("Qual é a sua idade?", "ex 18", 1),
            Question("Qual é o seu endereço?", "Rua flor do campo", 1),
            Question("Qual é a sua formação academica?", "", 1),
            Question("Qual é o seu esporte favorito?", "futebol", 1)
        )
    }
}