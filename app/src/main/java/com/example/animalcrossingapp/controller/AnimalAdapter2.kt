import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.animalcrossingapp.R
import com.example.animalcrossingapp.database.Current
import com.example.animalcrossingapp.databinding.ItemCurrentBinding
import com.example.animalcrossingapp.databinding.ListviewitemBinding

class AnimalAdapter2(val items: List<Current>,
                     private val clickListener: (current: Current) -> Unit) :
    RecyclerView.Adapter<AnimalAdapter2.CurrentViewHolder>() {
    class CurrentViewHolder(val binding: ListviewitemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrentViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.listviewitem, parent, false)
        val viewHolder = CurrentViewHolder(ListviewitemBinding.bind(view))
        view.setOnClickListener {
            clickListener.invoke(items[viewHolder.adapterPosition])
        }
        return viewHolder
    }

    override fun getItemCount(): Int = items.size


    override fun onBindViewHolder(holder: CurrentViewHolder, position: Int) {
        holder.binding.current = items[position]
    }
}