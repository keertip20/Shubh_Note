package in.xparticle.shubhnote.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import in.xparticle.shubhnote.Interfacee.CheckboxInterface;
import in.xparticle.shubhnote.Interfacee.DeleteInterface;
import in.xparticle.shubhnote.Model.ReModel;
import in.xparticle.shubhnote.R;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    ArrayList<ReModel> mList;
    Context context;
    CheckboxInterface listener;
    DeleteInterface deleteInterface;

    public MyAdapter(ArrayList<ReModel> mList, Context context, CheckboxInterface listener, DeleteInterface deleteInterface) {
        this.mList=mList;
        this.context=context;
        this.listener = listener;
        this.deleteInterface=deleteInterface;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rowItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card, parent, false);
        return new MyViewHolder(rowItem);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.id.setText(String.valueOf(position+1));
        holder.item.setText(mList.get(position).getItem());
        holder.delete.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


//                        Log.e("delete", "onClick: delete" );
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                        builder1.setMessage("Are you sure, want to delete this TODO List");
                        builder1.setCancelable(true);

                        builder1.setPositiveButton(
                                "Delete", new DialogInterface.OnClickListener(){
                                    @Override
                                    public void onClick(DialogInterface dialog, int which){
                                        deleteInterface.deleteTodo(mList.get(position).getId());

                                    }
                                });
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                };

                        builder1.setNegativeButton(
                                "Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });

                        AlertDialog alert11 = builder1.create();
                        alert11.show();
                    }
                }
        );
//        holder.checkBox.set(mList.get(position).getCheckbox());
        if(mList.get(position).getCheckbox().toString().equalsIgnoreCase("unChecked")){
            holder.checkBox.setChecked(false);
        }else {
            holder.checkBox.setChecked(true);
        }
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(holder.checkBox.isChecked()){
                    listener.updateCheckBoxState("checked",mList.get(position).getId());
                }
                else{
                    listener.updateCheckBoxState("unChecked",mList.get(position).getId());
                }
            }
        });


    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView id, item;
        CheckBox checkBox;
        ImageView delete;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.tv_id);
            item = itemView.findViewById(R.id.tv_item);
            checkBox=itemView.findViewById(R.id.checkbox);
            delete=itemView.findViewById(R.id.iv_delete);

        }
    }
}
