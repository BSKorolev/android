import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lab1.PhoneModel
import com.example.lab1.R

class PhonesAdapter : RecyclerView.Adapter<PhonesAdapter.PhoneViewHolder>() {

    private var phonesList: ArrayList<PhoneModel> = ArrayList()

    fun setupPhones(newPhones: List<PhoneModel>) {
        phonesList.clear()
        phonesList.addAll(newPhones)
        notifyDataSetChanged()
    }

    inner class PhoneViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvName: TextView = itemView.findViewById(R.id.Name)
        private val tvPrice: TextView = itemView.findViewById(R.id.Price)
        private val tvDate: TextView = itemView.findViewById(R.id.ReleaseDate)
        private val tvScore: TextView = itemView.findViewById(R.id.CameraScore)

        fun bind(mPhone: PhoneModel) {
            tvName.text = mPhone.name
            tvPrice.text = mPhone.price
            tvDate.text = mPhone.date
            tvScore.text = mPhone.score
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhoneViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_item, parent, false)
        return PhoneViewHolder(view)
    }

    override fun onBindViewHolder(holder: PhoneViewHolder, position: Int) {
        (holder as PhoneViewHolder).bind(phonesList[position])
    }

    override fun getItemCount(): Int = phonesList.size

}