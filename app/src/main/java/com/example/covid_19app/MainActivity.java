package com.example.covid_19app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.hbb20.CountryCodePicker;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    CountryCodePicker countryCodePicker;
    TextView mtodaytotal,mtotal,mdeaths,mtodayDeaths,mrecovered,mtodayRecovered,mactive,mtodayactive;

    String country;
    TextView mfilter;
    Spinner spinner;
    String[] types={"cases","deaths","recovered","active"};
    List<ModalClass> modalClassList;
    List<ModalClass> modalClassList2;
//    private List<ModalClass> modalClassList3;
    PieChart mpieChart;
    private RecyclerView recyclerView;
    com.example.covid_19app.Adapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);



        countryCodePicker=findViewById(R.id.ccp);
        mtodayactive=findViewById(R.id.todayactive);
        mactive=findViewById(R.id.activecase);
        mdeaths=findViewById(R.id.totaldeath);
        mtodayDeaths=findViewById(R.id.todaydeathcase);
        mrecovered=findViewById(R.id.recovercase);
        mtodayRecovered=findViewById(R.id.todayrecover);
        mtotal=findViewById(R.id.totalcase);
        mtodaytotal=findViewById(R.id.todaytotal);
        mpieChart=findViewById(R.id.piechart);
        spinner=findViewById(R.id.spinner);
        mfilter=findViewById(R.id.filter);
        recyclerView=findViewById(R.id.rcview);
        modalClassList= new ArrayList<>();
        modalClassList2= new ArrayList<>();
//        modalClassList3= new ArrayList<>();


//        modalClassList3.add(new ModalClass("11","23","54","54","76","87","82","coco"));
//        modalClassList3.add(new ModalClass("21","76","78","78","87","21","32","hello"));
//        modalClassList3.add(new ModalClass("32","74","45","65","56","85","45","dabhi"));
//        modalClassList3.add(new ModalClass("45","83","34","23","83","54","67","utsav"));

        spinner.setOnItemSelectedListener(this);
        ArrayAdapter arrayAdapter=new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,types);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(arrayAdapter);

        ApiUitilites.getAPIInterface().getcountrydata().enqueue(new Callback<List<ModalClass>>() {
            @Override
            public void onResponse(Call<List<ModalClass>> call, Response<List<ModalClass>> response) {

                modalClassList2.addAll(response.body());
                Log.d("TAG","Utsav Dabhi"+modalClassList2);
//                adapter=new Adapter(getApplicationContext(),modalClassList2);
//                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<List<ModalClass>> call, Throwable t) {
                Log.d("TAG","Response = "+t.toString());
            }
        });


//        adapter=new Adapter(getApplicationContext(),modalClassList3);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        recyclerView.setLayoutManager(linearLayoutManager);
        adapter=new Adapter(getApplicationContext(),modalClassList2);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);


        countryCodePicker.setAutoDetectedCountry(true);
        country=countryCodePicker.getSelectedCountryName();
        countryCodePicker.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected() {
                country=countryCodePicker.getSelectedCountryName();
                fetchdata();
            }


        });


     fetchdata();

    }

    private void fetchdata() {

        ApiUitilites.getAPIInterface().getcountrydata().enqueue(new Callback<List<ModalClass>>() {
            @Override
            public void onResponse(Call<List<ModalClass>> call, Response<List<ModalClass>> response) {
                modalClassList.addAll(response.body());

                for(int i=0;i<modalClassList.size();i++)
                {
//                    if(modalClassList!=null && modalClassList.get(i).getCountry()!=null &&modalClassList.get(i).getCountry().equals(country))
                        if(modalClassList.get(i).getCountry().equals(country))
                    {

                        mactive.setText(modalClassList.get(i).getActive());
                        mtodayDeaths.setText(modalClassList.get(i).getTodayDeaths());
                        mtodayRecovered.setText(modalClassList.get(i).getTodayRecovered());
                        mtodaytotal.setText(modalClassList.get(i).getTodayCases());
                        mtotal.setText(modalClassList.get(i).getCases());
                        mdeaths.setText(modalClassList.get(i).getDeaths());
                        mrecovered.setText(modalClassList.get(i).getRecovered());
                        mtodayDeaths.setText(modalClassList.get(i).getTodayDeaths());

                        int active,total,recovered,deaths;

                        active=Integer.parseInt(modalClassList.get(i).getActive());
                        total=Integer.parseInt(modalClassList.get(i).getCases());
                        recovered=Integer.parseInt(modalClassList.get(i).getRecovered());
                        deaths=Integer.parseInt(modalClassList.get(i).getDeaths());

                        updategraph(active,total,recovered,deaths);

                    }
                }
            }


            @Override
            public void onFailure(Call<List<ModalClass>> call, Throwable t) {

            }
        });
    }

    private void updategraph(int active, int total, int recovered, int deaths) {

        mpieChart.clearChart();
//        mpieChart.addPieSlice(new PieModel("Active",active,Color.parseColor("#FF4CAF50)")));
//        mpieChart.addPieSlice(new PieModel("Comfirm",total,Color.parseColor("#FFB701")));
//        mpieChart.addPieSlice(new PieModel("Recovered",recovered,Color.parseColor("#38ACCD")));
//        mpieChart.addPieSlice(new PieModel("Deaths",deaths,Color.parseColor("#F55C47")));


        mpieChart.addPieSlice(new PieModel("Active",active,R.color.green));
        mpieChart.addPieSlice(new PieModel("Comfirm",total,R.color.yellow));
        mpieChart.addPieSlice(new PieModel("Recovered",recovered,R.color.blue));
        mpieChart.addPieSlice(new PieModel("Deaths",deaths,R.color.red));
        mpieChart.startAnimation();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    {
            String item=types[position];
            mfilter.setText(item);
            adapter.filter(item);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}