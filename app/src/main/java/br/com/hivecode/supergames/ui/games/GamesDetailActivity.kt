package br.com.hivecode.supergames.ui.games

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import br.com.hivecode.supergames.R
import br.com.hivecode.supergames.data.entity.Item
import com.bumptech.glide.Glide
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import kotlinx.android.synthetic.main.activity_games_detail.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


class GamesDetailActivity : AppCompatActivity() {

    private lateinit var viewModel : GamesViewModel

    companion object {
        const val ITEM: String = "item"

        fun newIntent(context: Context) : Intent {
            return Intent(context, GamesDetailActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_games_detail)
        init()
    }

    private fun init(){
        viewModel = ViewModelProviders.of(this).get(GamesViewModel::class.java)
        loadGame()
        fillGraph()
        setEvents()
    }

    private fun setEvents() {
        activity_games_detail_components_btn.setOnClickListener {
            val intent = ComponentsActivity.newIntent(this@GamesDetailActivity)
            startActivity(intent)
        }
    }

    private fun fillGraph() {

        with(chart){
            setDrawGridBackground(false);
            setVisibleXRangeMaximum(12f) // allow 5 values to be displayed at once on the x-axis, not more
            setVisibleXRangeMinimum(1f) // allow 5 values to be displayed at once on the x-axis, not less
            setDragOffsetX(10f)
            setBackgroundColor(Color.TRANSPARENT)
            setTouchEnabled(false)
            setDrawBorders(false)
            animateX(2000)
            invalidate()
            isDragEnabled = false
            isScaleYEnabled = false
            isScaleXEnabled = false
            description = null
            extraTopOffset = 10f
        }

        with(chart.legend){
            form = Legend.LegendForm.LINE
            textSize = 11f
            textColor = Color.BLUE
            verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
            horizontalAlignment = Legend.LegendHorizontalAlignment.LEFT
            orientation = Legend.LegendOrientation.HORIZONTAL
            setDrawInside(false)
        }

        with(chart.xAxis){
            isEnabled = true
            valueFormatter = createValueFormatter()
            setAvoidFirstLastClipping(true)
            setDrawGridLines(false)

        }

        with(chart.axisLeft){
           isEnabled = true
           isGranularityEnabled = true
            granularity = 10f
           setDrawGridLines(false)
        }

        with(chart.axisLeft){
            setStartAtZero(false)
            isEnabled = false
            setDrawGridLines(false)
        }

        setData()


//        val xAxis = chart.xAxis
//        xAxis.position = XAxis.XAxisPosition.TOP_INSIDE
//        xAxis.textSize = 10f
//        xAxis.textColor = Color.WHITE
//        xAxis.setDrawAxisLine(false)
//        xAxis.setDrawGridLines(false)
//        xAxis.textColor = Color.rgb(255, 192, 56)
//        xAxis.setCenterAxisLabels(false)
//        xAxis.granularity = 1f // one hour
//        xAxis.valueFormatter = createValueFormatter()
//
//        val leftAxis = chart.axisLeft
//        leftAxis.textColor = ColorTemplate.getHoloBlue()
//        leftAxis.axisMaximum = 5f
//        leftAxis.axisMinimum = 0f
//        leftAxis.setDrawGridLines(true)
//        leftAxis.isGranularityEnabled = true
//
//        val rightAxis = chart.getAxisRight()
//        rightAxis.setTextColor(Color.WHITE)
//        rightAxis.disableAxisLineDashedLine()
//        rightAxis.disableGridDashedLine()
//        chart.setViewPortOffsets(4f, 4f, 4f, 4f)




    }

    private fun createValueFormatter(): IAxisValueFormatter? {
        val mFormat = SimpleDateFormat("MMM/yy", Locale("pt","BR"))

        return IAxisValueFormatter { value, axis ->

            mFormat.format(Date(value.toLong()))
        }
    }

    private fun loadGame() {
        val itemGame = intent.extras.getSerializable(ITEM) as Item
        setValues(itemGame)
    }

    private fun setData() {
        val calendar = Calendar.getInstance()
        val values1 = ArrayList<Entry>()
        val values2 = ArrayList<Entry>()

        for (month in 1..10){
            calendar.add(Calendar.MONTH, month)
            val date1: Date = calendar.time
            values1.add(Entry(date1.time.toFloat(), 1.5f * month))
            values2.add(Entry(date1.time.toFloat(), 1.1f * month))
        }

        val set1 = LineDataSet(values1, "CDB - PINE")
        val set2 = LineDataSet(values2, "Poupança")

        with(set1){
            lineWidth = 2f
            fillAlpha = 65
            fillColor = ColorTemplate.JOYFUL_COLORS[1]
            highLightColor = Color.rgb(0, 255, 255)
            setDrawCircleHole(false)
        }

        with(set2){
            lineWidth = 2f
            fillAlpha = 65
            fillColor = ColorTemplate.JOYFUL_COLORS[2]
            highLightColor = Color.rgb(255, 255, 0)
            setDrawCircleHole(false)
        }



        val data = LineData(set1, set2)
        data.setValueTextColor(ColorTemplate.getHoloBlue())
        data.setValueTextSize(9f)

        chart.data = data
    }

    private fun setValues(itemGame: Item) {
        activity_games_detail_title_txt.text = itemGame.game.name
        activity_games_detail_viewers_txt.text = itemGame.viewers.toString()
        activity_games_detail_channels_txt.text = itemGame.channels.toString()
        Glide.with(this@GamesDetailActivity)
            .load(itemGame.game.logo?.large)
            .into(activity_games_detail_image)
    }
}