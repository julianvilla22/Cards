package es.uam.eps.dadm.cards

import android.graphics.Color
import android.os.Bundle
import android.provider.ContactsContract
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import es.uam.eps.dadm.cards.database.CardDatabase
import es.uam.eps.dadm.cards.databinding.FragmentStatisticsBinding
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.concurrent.Executors


class StatisticsFragment : Fragment() {
    lateinit var binding: FragmentStatisticsBinding
    private var dataMap = HashMap<LocalDate, Float>()
    private val executor = Executors.newSingleThreadExecutor()
    private lateinit var cards: List<Card>
    private val viewModel: StatisticsViewModel by lazy {
        ViewModelProvider(this).get(StatisticsViewModel::class.java)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_statistics, container, false)

        executor.execute {
            cards = context?.let { CardDatabase.getInstance(it).cardDao.getCardsList() }!!
            binding.barrasDias.data = barCharGetData(viewLifecycleOwner)
        }

        binding.barrasDias.legend.isEnabled=true
        binding.barrasDias.legend.formToTextSpace
        binding.barrasDias.animateY(1000)


        return binding.root
    }
    fun barCharGetData(lifecycleOwner: LifecycleOwner): BarData {

        val bargrouplist = ArrayList<BarDataSet>()
        val returnData = BarData()
        cards.forEach() {
            var date = LocalDateTime.parse(it.nextPracticeDate).toLocalDate()
            dataMap[date] = dataMap.getOrDefault(date, 0F) + 1F
        }
        var a = 3
        var i = 0F;
        dataMap.keys.sorted().forEach() {
            var barentry = ArrayList<BarEntry>()

            if (dataMap[it] != null) {
                dataMap[it]?.let { it1 -> BarEntry(i, it1, it) }
                    ?.let { it2 -> barentry.add(it2) }
                i++
                bargrouplist.add(BarDataSet(barentry, it.toString()))
            }

        }
        bargrouplist.forEach{bg ->

            bg.addColor(Color.parseColor("#AF5FFF"))
            bg.addColor(Color.parseColor("#7C5FFF"))
            bg.addColor(Color.parseColor("#FF5FE9"))
            bg.addColor(Color.parseColor("#FAFF5F"))
            bg.addColor(Color.parseColor("#FFC05F"))
            bg.valueTextSize = 16f
            returnData.addDataSet(bg)
        }

        return returnData
    }


}