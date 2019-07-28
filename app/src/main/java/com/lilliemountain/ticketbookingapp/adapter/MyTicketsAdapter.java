package com.lilliemountain.ticketbookingapp.adapter;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.lilliemountain.ticketbookingapp.R;
import com.lilliemountain.ticketbookingapp.model.Ticket;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.WHITE;

public class MyTicketsAdapter extends RecyclerView.Adapter<MyTicketsAdapter.MyTicketsHolder> {
    List<Ticket> tickets;

    public MyTicketsAdapter(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    @NonNull
    @Override
    public MyTicketsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_tickets,parent,false);
        return new MyTicketsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyTicketsHolder holder, int position) {
        holder.name.setText(tickets.get(position).getEvent().getName());
        holder.time.setText(tickets.get(position).getEvent().getTime());
        String date = (tickets.get(position).getEvent().getDate())+" 2019";
        holder.date.setText(date);
        switch (tickets.get(position).getEvent().getEventid())
        {
            case "event_001":
                holder.photo.setImageDrawable(holder.itemView.getContext().getDrawable(R.drawable.ic_music));
                break;
            case "event_002":
                holder.photo.setImageDrawable(holder.itemView.getContext().getDrawable(R.drawable.ic_art));
                break;
            case "event_003":
                holder.photo.setImageDrawable(holder.itemView.getContext().getDrawable(R.drawable.ic_coaching));
                break;
        }
        holder.ticket=tickets.get(position);
    }

    @Override
    public int getItemCount() {
        return tickets.size();
    }

    public class MyTicketsHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name,date,time;
        ImageView photo;
        Ticket ticket;
        public MyTicketsHolder(@NonNull View itemView) {
            super(itemView);
            photo=itemView.findViewById(R.id.photo);
            name=itemView.findViewById(R.id.name);
            date=itemView.findViewById(R.id.date);
            time=itemView.findViewById(R.id.time);
            itemView.setOnClickListener(this);
            name.setOnClickListener(this);
            date.setOnClickListener(this);
            time.setOnClickListener(this);
            photo.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            try {
                Dialog dialog=new Dialog(itemView.getContext());
                dialog.setTitle("QR CODE : "+ticket.getEvent().getName());
                dialog.setContentView(R.layout.dialog_qr_code);
                ImageView qrcode=dialog.findViewById(R.id.asdasdasd);
                final Bitmap bitmap = encodeAsBitmap("Email : "+ticket.getEmail()+"\nTimeStamp:"+ticket.getTimestamp()+"\nEvent ID: "+ticket.getEvent().getEventid()+".");
                qrcode.setImageBitmap(bitmap);
                dialog.findViewById(R.id.share).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {

                            File cachePath = new File(v.getContext().getCacheDir(), "images");
                            cachePath.mkdirs(); // don't forget to make the directory
                            FileOutputStream stream = new FileOutputStream(cachePath + "/image.png"); // overwrites this image every time
                            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                            stream.close();
                            File imagePath = new File(v.getContext().getCacheDir(), "images");
                            File newFile = new File(imagePath, "image.png");
                            Uri contentUri = FileProvider.getUriForFile(v.getContext(), "com.lilliemountain.ticketbokingapp", newFile);

                            if (contentUri != null) {
                                Intent shareIntent = new Intent();
                                shareIntent.setAction(Intent.ACTION_SEND);
                                shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); // temp permission for receiving app to read this file
                                shareIntent.setDataAndType(contentUri, v.getContext().getContentResolver().getType(contentUri));
                                shareIntent.putExtra(Intent.EXTRA_STREAM, contentUri);
                                v.getContext().startActivity(Intent.createChooser(shareIntent, "Choose an app"));
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                });
                dialog.show();
                Window window = dialog.getWindow();
                window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            } catch (WriterException e) {
                e.printStackTrace();
            }
        }
        Bitmap encodeAsBitmap(String str) throws WriterException {
            BitMatrix result;
            try {
                result = new MultiFormatWriter().encode(str,
                        BarcodeFormat.QR_CODE, 512, 512, null);
            } catch (IllegalArgumentException iae) {
                // Unsupported format
                return null;
            }
            int w = result.getWidth();
            int h = result.getHeight();
            int[] pixels = new int[w * h];
            for (int y = 0; y < h; y++) {
                int offset = y * w;
                for (int x = 0; x < w; x++) {
                    pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
                }
            }
            Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, 512, 0, 0, w, h);
            return bitmap;
        }
    }
}
