package com.example.project5;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>
{

    private String[] localDataSet;
    AppointmentBook thisBook;
    ArrayList<Appointment> appointments;

    public RecyclerViewAdapter(ArrayList<Appointment> appList)
    {
        appointments = appList;
    }

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView description;
        TextView start;
        TextView end;
        TextView duration;
        RelativeLayout parentLayout;

        public ViewHolder(View view)
        {
            super(view);
            description = view.findViewById(R.id.appointmentDesc);
            start = view.findViewById(R.id.appointmenStart);
            end = view.findViewById(R.id.appointmentEnd);
            duration = view.findViewById(R.id.appointmentDuration);
          //  parentLayout = view.findViewById(R.id.listLayout);
        }
    }

    // Create new views (invoked by the layout manager)
    @Override
    public @NotNull ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.appointment_list_layout, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {

        holder.description.setText( appointments.get(position).getDescription());
        holder.start.setText( appointments.get(position).getBeginTimeString());
        holder.end.setText( appointments.get(position).getEndTimeString());
        holder.duration.setText("Duration goes here dumbo");

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return appointments.size();
    }
}
