package kmitl.natcha58070069.com.libreria.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import kmitl.natcha58070069.com.libreria.model.LibreriaInfo;
import kmitl.natcha58070069.com.libreria.R;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    public MyAdapter() {
    }

    private List<LibreriaInfo> data;
    private Context context;
    private MyAdapterListener listener;

    public List<LibreriaInfo> getData() {
        return data;
    }

    public void setData(List<LibreriaInfo> data) {
        this.data = data;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public MyAdapterListener getListener() {
        return listener;
    }

    public void setListener(MyAdapterListener listener) {
        this.listener = listener;
    }

    //Constructor
    public MyAdapter(Context context) {
        this.context = context;
        data = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item, null, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final LibreriaInfo libreriaInfo = data.get(position);

        holder.tvName.setText(libreriaInfo.getName());

        int len = libreriaInfo.getComment().length();
        if (len > 80){
            String commentCutted = libreriaInfo.getComment().substring(0,81);
            holder.tvComment.setText(commentCutted + "..");
        }else {
            holder.tvComment.setText(libreriaInfo.getComment());
        }

        //If user want to edit must set on click in holder item
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClickInfoItem(libreriaInfo);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(data == null){
            data = new ArrayList<>();
        }
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        TextView tvComment;
        ImageView libItem;

        public ViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvComment = itemView.findViewById(R.id.tvComment);
            libItem = itemView.findViewById(R.id.imageView3);
        }
    }

    public interface MyAdapterListener {
        void onClickInfoItem(LibreriaInfo libreriaInfo);
    }
}
