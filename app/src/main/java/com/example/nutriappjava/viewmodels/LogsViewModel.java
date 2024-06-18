package com.example.nutriappjava.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.nutriappjava.entities.DailyLog;
import com.example.nutriappjava.entities.NutrientSummary;
import com.example.nutriappjava.services.ApiClient;
import com.example.nutriappjava.services.ApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LogsViewModel extends ViewModel {

    private final MutableLiveData<List<DailyLog>> logsLiveData = new MutableLiveData<>();
    private final MutableLiveData<NutrientSummary> summaryLiveData = new MutableLiveData<>();

    public LiveData<List<DailyLog>> getLogsForDate(String token, String date) {
        ApiService apiService = ApiClient.getRetrofitInstance(true).create(ApiService.class);
        Call<List<DailyLog>> call = apiService.getLogsForDate("Bearer " + token, date);
        call.enqueue(new Callback<List<DailyLog>>() {
            @Override
            public void onResponse(Call<List<DailyLog>> call, Response<List<DailyLog>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    logsLiveData.setValue(response.body());
                } else {
                    logsLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<List<DailyLog>> call, Throwable t) {
                logsLiveData.setValue(null);
            }
        });
        return logsLiveData;
    }

    public LiveData<List<DailyLog>> getLogsForPeriod(String token, String period) {
        ApiService apiService = ApiClient.getRetrofitInstance(true).create(ApiService.class);
        Call<List<DailyLog>> call = apiService.getLogsForPeriod("Bearer " + token, period);
        call.enqueue(new Callback<List<DailyLog>>() {
            @Override
            public void onResponse(Call<List<DailyLog>> call, Response<List<DailyLog>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    logsLiveData.setValue(response.body());
                } else {
                    logsLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<List<DailyLog>> call, Throwable t) {
                logsLiveData.setValue(null);
            }
        });
        return logsLiveData;
    }

    public LiveData<NutrientSummary> getLogSummaryForPeriod(String token, String period) {
        ApiService apiService = ApiClient.getRetrofitInstance(true).create(ApiService.class);
        Call<NutrientSummary> call = apiService.getLogSummaryForPeriod("Bearer " + token, period);
        call.enqueue(new Callback<NutrientSummary>() {
            @Override
            public void onResponse(Call<NutrientSummary> call, Response<NutrientSummary> response) {
                if (response.isSuccessful() && response.body() != null) {
                    summaryLiveData.setValue(response.body());
                } else {
                    summaryLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<NutrientSummary> call, Throwable t) {
                summaryLiveData.setValue(null);
            }
        });
        return summaryLiveData;
    }

    public LiveData<NutrientSummary> getLogSummaryForDate(String token, String date) {
        ApiService apiService = ApiClient.getRetrofitInstance(true).create(ApiService.class);
        Call<NutrientSummary> call = apiService.getLogSummaryForDate("Bearer " + token, date);
        call.enqueue(new Callback<NutrientSummary>() {
            @Override
            public void onResponse(Call<NutrientSummary> call, Response<NutrientSummary> response) {
                if (response.isSuccessful() && response.body() != null) {
                    summaryLiveData.setValue(response.body());
                } else {
                    summaryLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<NutrientSummary> call, Throwable t) {
                summaryLiveData.setValue(null);
            }
        });
        return summaryLiveData;
    }
}


