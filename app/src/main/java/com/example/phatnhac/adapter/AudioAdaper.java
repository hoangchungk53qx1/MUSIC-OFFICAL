package com.example.phatnhac.adapter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.phatnhac.R;
import com.example.phatnhac.model.Audio;
import com.example.phatnhac.view.AudioPlayerActivity;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class AudioAdaper extends RecyclerView.Adapter<AudioAdaper.ViewHolder> {

    private ArrayList<Audio> arrayAudio;

    public AudioAdaper(ArrayList<Audio> arrayAudio) {
        this.arrayAudio = arrayAudio;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_name_music,parent,false);

        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(holder.imgImage.getContext())
                .load(arrayAudio.get(position).getImage())
                .into(holder.imgImage);

        holder.txtName.setText(arrayAudio.get(position).getName());
        holder.txtSinger.setText("Ca sỹ:   " + arrayAudio.get(position).getSinger());
    }

    @Override
    public int getItemCount() {
        return arrayAudio.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        CircleImageView imgImage;
        TextView txtName,txtSinger;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgImage = itemView.findViewById(R.id.imgImage);
            txtName = itemView.findViewById(R.id.txtName);
            txtSinger = itemView.findViewById(R.id.txtSinger);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), AudioPlayerActivity.class);
                    intent.putExtra("audio",arrayAudio.get(getAdapterPosition()));
                    v.getContext().startActivity(intent);
                }
            });
        }
    }
}