package com.valentinfilatov.wober;

/**
 * Created by valik on 4/14/18.
 */

import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.valentinfilatov.wober.POJO.Domain;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;


public class DomainRecyclerViewAdapter extends RecyclerView.Adapter<DomainRecyclerViewAdapter.ViewHolder> {

    private ArrayList<Domain> mDataset;
    private StorageReference mStorageRef;


    public ArrayList<Domain> getmDataset() {
        return mDataset;
    }

    // класс view holder-а с помощью которого мы получаем ссылку на каждый элемент
    // отдельного пункта списка
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // наш пункт состоит только из одного TextView
        public TextView mTextView;
        public ImageView ivBackground;

        public ViewHolder(View v) {
            super(v);
            mTextView = (TextView) v.findViewById(R.id.info_text);
            ivBackground = (ImageView) v.findViewById(R.id.ivBackground);
        }
    }

    // Конструктор
    public DomainRecyclerViewAdapter(ArrayList dataset) {
        mDataset = dataset;
        mStorageRef = FirebaseStorage.getInstance().getReference();
    }

    // Создает новые views (вызывается layout manager-ом)
    @Override
    public DomainRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.domain_card, parent, false);

        // тут можно программно менять атрибуты лэйаута (size, margins, paddings и др.)

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Заменяет контент отдельного view (вызывается layout manager-ом)
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        ParameterizedType parameterizedType = (ParameterizedType) getClass()
                .getGenericSuperclass();

        holder.mTextView.setText(mDataset.get(position).getName());
        File localFile = null;
        try {
            localFile = File.createTempFile("images", "jpeg");

            StorageReference repairRef = mStorageRef.child(mDataset.get(position).getImage());

            final File finalLocalFile = localFile;
            repairRef.getFile(localFile)
                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            holder.ivBackground.setImageBitmap(BitmapFactory.decodeFile(finalLocalFile.getAbsolutePath()));
                            holder.ivBackground.setScaleType(ImageView.ScaleType.FIT_XY);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle failed download
                    // ...
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Возвращает размер данных (вызывается layout manager-ом)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}