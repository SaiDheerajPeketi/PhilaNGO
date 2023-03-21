package com.example.philango;

import android.telecom.Call;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
    private final CustomItem localDataSet;
    private final RecyclerViewInterface recyclerViewInterface;
//    private ItemClickListener itemClickListener;
//    //Added for Item CLick Listener
//    public interface ItemClickListener{
//        void onItemClick(Call.Details details);
//    }
    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */
    //Implements View.OnClickListener is added for List Item Click Listener
    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView headlineText,byText,userName,Amount;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            headlineText = (TextView) view.findViewById(R.id.headlineText);
            byText = (TextView) view.findViewById(R.id.byText);
            userName = view.findViewById(R.id.userName);
            Amount = view.findViewById(R.id.amount);


        }

        public TextView getHeadlineText() {
            return headlineText;
        }
        public TextView getByText() {
            return byText;
        }
        public TextView getUserName(){return userName; }
        public TextView getAmount(){return Amount; }

//        @Override
//        public void onClick(View v) {
//            clickLis
//        }
    }

    /**
     * Initialize the dataset of the Adapter
     *
     * @param dataSet String[] containing the data to populate views to be used
     * by RecyclerView
     */
    public CustomAdapter(CustomItem dataSet,RecyclerViewInterface recyclerViewInterface)
    {   localDataSet = dataSet;
        this.recyclerViewInterface = recyclerViewInterface;
    }


    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.text_row_item, viewGroup, false);
//        //Item Click Listener
//        view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(recyclerViewInterface != null){
//                    int position = getAda
//                }
//            }
//        });
        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.getHeadlineText().setText(localDataSet.Headlines.get(position));
        viewHolder.getByText().setText(localDataSet.Bys.get(position));
        viewHolder.getUserName().setText(localDataSet.Username.get(position));
        viewHolder.getAmount().setText(localDataSet.Amount.get(position).toString());

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(recyclerViewInterface != null){
                    int position = viewHolder.getAbsoluteAdapterPosition();
                    if(position!=RecyclerView.NO_POSITION){
                        recyclerViewInterface.onClick(position);
                        //Log.e("MEERROR", Integer.toString(position));
                    }
                }
            }
        });

//        //Item Click Listener
//        viewHolder.itemView.setOnClickListener(view ->{
//            itemClickListener.onItemClick(localDataSet.Username.get(position));
//        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.Headlines.size();
    }
}










