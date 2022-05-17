package android.example.taskmaster;

import android.example.taskmaster.data.Tasks;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amplifyframework.datastore.generated.model.Task;

import java.util.ArrayList;
import java.util.List;

public class RecycleModels extends RecyclerView.Adapter<RecycleModels.taskviewsholoder> {
    List<Task> models;
    CustomClickListener listener;

    public RecycleModels(ArrayList<Task> models, CustomClickListener listener) {
        this.models = models;
        this.listener = listener;
    }

    @NonNull
    @Override
    public taskviewsholoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listTaskView = layoutInflater.inflate(R.layout.task_item_layout, parent, false);
        return new taskviewsholoder(listTaskView, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull taskviewsholoder holder, int position) {
        holder.title.setText(models.get(position).getTitle());
        holder.body.setText(models.get(position).getBody());
        holder.state.setText(models.get(position).getStatus());

    }

    @Override
    public int getItemCount() {
        return models.size();
    }


    class taskviewsholoder extends RecyclerView.ViewHolder {
        TextView title;
        TextView body;
        TextView state;
        CustomClickListener listener;

        public taskviewsholoder(@NonNull View itemView, CustomClickListener listener) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            body = itemView.findViewById(R.id.body);
            state = itemView.findViewById(R.id.state);
            itemView.setOnClickListener(view -> listener.taskItemClicked(getAdapterPosition()));

        }
    }

    public interface CustomClickListener {
        void taskItemClicked(int position);
    }
}
