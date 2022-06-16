package adapter

import android.graphics.Color
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mstart004.exchangeapp.R
import com.mstart004.exchangeapp.databinding.ItemRvBinding
import model.Exchange
import java.util.*

class UserAdapter(var list: List<Exchange>) :
    RecyclerView.Adapter<UserAdapter.Vh>() {

    inner class Vh(var itemRv: ItemRvBinding) : RecyclerView.ViewHolder(itemRv.root) {
        fun onBind(user: Exchange, position: Int) {
            itemRv.rvRate.text = user.Rate
            itemRv.rvDiff.text = user.Diff.toString()
            itemRv.rvRegionName.text = user.CcyNm_UZ
            itemRv.rvFlag.text = getFlag(user.Ccy.substring(0, 2))

            if (user.Diff < 1) {
                itemRv.imageDifference.setImageResource(R.drawable.ic_baseline_trending_down_24)
                itemRv.rvDiff.setTextColor(Color.parseColor("#FF3700B3"))
            }

            val valute = user.Rate.toDouble()

            itemRv.rvExchange.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int,
                ) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                    if (s!!.isNotEmpty()) {
                        val counts = itemRv.rvExchange.text.toString().toInt()
                        val total = String.format("%.2f", counts * valute)
                        itemRv.rvRate.text = total
                    } else {
                        itemRv.rvRate.text = user.Rate
                    }
                }

                override fun afterTextChanged(s: Editable?) {

                }

            })
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemRvBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position], position)
    }

    override fun getItemCount(): Int {
        return try {
            list.size
        } catch (ex: Exception) {
            return 0
        }
    }

    fun getFlag(countryCode: String): String {
        return countryCode
            .uppercase(Locale.US).map { char ->
                Character.codePointAt("$char", 0) - 0x41 + 0x1F1E6
            }
            .map { codePoint ->
                Character.toChars(codePoint)
            }
            .joinToString(separator = "") { charArray ->
                String(charArray)
            }
    }

}