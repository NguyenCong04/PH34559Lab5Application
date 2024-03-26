package congntph34559.fpoly.ph34559lab5application.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import congntph34559.fpoly.ph34559lab5application.API.ApiService;
import congntph34559.fpoly.ph34559lab5application.DTO.DistributorDTO;
import congntph34559.fpoly.ph34559lab5application.MainActivity;
import congntph34559.fpoly.ph34559lab5application.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AdapterDistributor extends RecyclerView.Adapter<AdapterDistributor.ViewHolder> {

    private Context context;
    private List<DistributorDTO> list;

    public AdapterDistributor(Context context, List<DistributorDTO> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        String id = list.get(position).get_id();
        String name = list.get(position).getName();

        holder.tvName.setText(list.get(position).getName());

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opendialogEdit(id,name);
            }
        });
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                builder.setTitle("Notification !")
                        .setMessage("Are you sure you want to delete")
                        .setCancelable(true)
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                ApiService.API_SERVICE.deleteDistributor(id).enqueue(new Callback<DistributorDTO>() {
                                    @Override
                                    public void onResponse(Call<DistributorDTO> call, Response<DistributorDTO> response) {
                                        if (response.isSuccessful()) {
                                            Toast.makeText(context, "Delete distributor successfully", Toast.LENGTH_SHORT).show();
                                            MainActivity.CallApiDistributor();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<DistributorDTO> call, Throwable t) {
                                        Log.e("zzzzzzzzzzz", "onFailureDelete: " + t.getMessage());
                                    }
                                });

                            }
                        })
                        .show();


            }
        });


    }

    @SuppressLint("SetTextI18n")
    private void opendialogEdit(String id,String name) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_create_update_distributor, null, false);

        builder.setView(view);

        AlertDialog dialog = builder.create();
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        AppCompatButton btnEdit, btnCancel;
        TextView tvTitleDialog;
        EditText edName;
        btnEdit = view.findViewById(R.id.btnCreateAndUpdate);
        btnCancel = view.findViewById(R.id.btnCancel);
        edName = view.findViewById(R.id.edNameDis);
        tvTitleDialog = view.findViewById(R.id.tvTitleDialog);
        btnEdit.setText("Update");
        tvTitleDialog.setText("Update distributor");

        edName.setText(name);

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DistributorDTO objDis = new DistributorDTO();
                objDis.setName(edName.getText().toString());
                ApiService.API_SERVICE.updateDistributor(id, objDis).enqueue(new Callback<DistributorDTO>() {
                    @Override
                    public void onResponse(Call<DistributorDTO> call, Response<DistributorDTO> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(context, "Update distributor successfully", Toast.LENGTH_SHORT).show();
                            MainActivity.CallApiDistributor();
                            dialog.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<DistributorDTO> call, Throwable t) {
                        Log.e("xxxxxxxx", "onFailureUpdate: " + t.getMessage());
                    }
                });
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        dialog.show();

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvName;
        AppCompatButton btnDelete, btnEdit;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tvNameDis);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            btnEdit = itemView.findViewById(R.id.btnEdit);

        }
    }


}
