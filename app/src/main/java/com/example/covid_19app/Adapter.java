package com.example.covid_19app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.NumberFormat;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    @NonNull

    int m=1;
    Context context;
    List<ModalClass> countrylist;

    public Adapter(Context context, List<ModalClass> countrylist) {
        this.context = context;
        this.countrylist = countrylist;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item,null,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  Adapter.ViewHolder holder, int position) {

        ModalClass modalClass=countrylist.get(position);

        holder.country.setText(modalClass.getCountry());

        if(m==1)
        {
            holder.cases.setText(modalClass.getCases());
        }
        else if(m==2)
        {
            holder.cases.setText(modalClass.getDeaths());
        }
        else if(m==3)
        {
            holder.cases.setText(modalClass.getRecovered());
        }
        else
            {
            holder.cases.setText(modalClass.getActive());
        }
    }

    @Override
    public int getItemCount() {
        return countrylist.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder {

        TextView cases,country;

        public ViewHolder(@NonNull  View itemView) {
            super(itemView);
            country=itemView.findViewById(R.id.countryname);
            cases=itemView.findViewById(R.id.countrycase);

        }
    }
    public void filter(String charText){
        if(charText.equals("cases")){
            m=1;
        }else if(charText.equals("deaths")){
            m=2;
        }else if(charText.equals("recovered")){
            m=3;
        }else{
            m=4;
        }

        notifyDataSetChanged();
    }

}
