package congntph34559.fpoly.ph34559lab5application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

import congntph34559.fpoly.ph34559lab5application.API.ApiService;
import congntph34559.fpoly.ph34559lab5application.API.HttpRequest;
import congntph34559.fpoly.ph34559lab5application.Adapter.AdapterDistributor;
import congntph34559.fpoly.ph34559lab5application.DTO.DistributorDTO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    static RecyclerView rcvDistributor;
    FloatingActionButton floatingActionButton;

    @SuppressLint("StaticFieldLeak")
    static AdapterDistributor adapterDistributor;
    TextInputEditText edSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rcvDistributor = findViewById(R.id.rcvDistributor);
        floatingActionButton = findViewById(R.id.floaAdd);
        edSearch = findViewById(R.id.edSearch);


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openDialogCreate();
            }
        });
        CallApiDistributor();

        search();

    }

    private void search() {

        edSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


                String key = s.toString().trim();


                ApiService.API_SERVICE.searchDis(key).enqueue(new Callback<List<DistributorDTO>>() {
                    @Override
                    public void onResponse(Call<List<DistributorDTO>> call, Response<List<DistributorDTO>> response) {

                        if (response.isSuccessful()) {
                            ApiService.API_SERVICE.searchDis(key).enqueue(new Callback<List<DistributorDTO>>() {
                                @Override
                                public void onResponse(Call<List<DistributorDTO>> call, Response<List<DistributorDTO>> response) {
                                    if (response.isSuccessful()) {
                                        List<DistributorDTO> list = response.body();
                                        Log.e("zzzzzzzz", "onResponse: " + list);
                                    }
                                }

                                @Override
                                public void onFailure(Call<List<DistributorDTO>> call, Throwable t) {
                                    Log.e("zzzzzzzz", "onResponse: " + t.getMessage());

                                }
                            });
                        }

                    }

                    @Override
                    public void onFailure(Call<List<DistributorDTO>> call, Throwable t) {
                        Log.e("zzzzzzzzz", "onFailure: " + t.getMessage());
                    }
                });


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }

    public static void CallApiDistributor() {
        ApiService.API_SERVICE.getListDistributor().enqueue(new Callback<List<DistributorDTO>>() {

            @Override
            public void onResponse(Call<List<DistributorDTO>> call, retrofit2.Response<List<DistributorDTO>> response) {
                if (response.isSuccessful()) {
                    List<DistributorDTO> list = response.body();
                    adapterDistributor = new AdapterDistributor(rcvDistributor.getContext(), list);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(rcvDistributor.getContext(), LinearLayoutManager.VERTICAL, false);
                    rcvDistributor.setLayoutManager(linearLayoutManager);
                    rcvDistributor.setAdapter(adapterDistributor);
                    adapterDistributor.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<DistributorDTO>> call, Throwable t) {
                Log.e("zzzzzzzzzz", "onFailure: " + t.getMessage());
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void openDialogCreate() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_create_update_distributor, null, false);

        builder.setView(view);

        AlertDialog dialog = builder.create();
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        AppCompatButton btnCreate, btnCancel;
        TextView tvTitleDialog;
        EditText edName;

        btnCreate = view.findViewById(R.id.btnCreateAndUpdate);
        btnCancel = view.findViewById(R.id.btnCancel);
        tvTitleDialog = view.findViewById(R.id.tvTitleDialog);
        edName = view.findViewById(R.id.edNameDis);

        btnCreate.setText("Create");
        tvTitleDialog.setText("Create distributor");

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (edName.getText().toString().equals("")) {
                    Toast.makeText(MainActivity.this, "Please enter name distributor", Toast.LENGTH_SHORT).show();
                    return;
                }

                String name = edName.getText().toString();
                DistributorDTO obj = new DistributorDTO();
                obj.setName(name);


                ApiService.API_SERVICE.createDistributor(obj).enqueue(new Callback<DistributorDTO>() {
                    @Override
                    public void onResponse(Call<DistributorDTO> call, retrofit2.Response<DistributorDTO> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "Create distributor successfully", Toast.LENGTH_SHORT).show();
                            CallApiDistributor();
                            dialog.dismiss();
                        } else {
                            Toast.makeText(MainActivity.this, "Create failed", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<DistributorDTO> call, Throwable t) {
                        Log.e("zzzzzzzzz", "onFailure: " + t.getMessage());
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
}