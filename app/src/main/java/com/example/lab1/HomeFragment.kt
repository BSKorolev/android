import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lab1.PhonesData
import com.example.lab1.R

class HomeFragment : Fragment() {
        private val adapter = PhonesAdapter()

        override fun onCreateView(
                inflater: LayoutInflater,
                container: ViewGroup?,
                savedInstanceState: Bundle?
        ): View {
                val root = inflater.inflate(R.layout.fragment_home, container, false)
                val recyclerView = root.findViewById<RecyclerView>(R.id.recyclerView)

                recyclerView.layoutManager = LinearLayoutManager(requireContext())
                recyclerView.adapter = adapter
                loadData()

                return root
        }

        private fun loadData() {
                adapter.setupPhones(PhonesData.phonesArr)
        }
}
