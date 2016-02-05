package com.monkeybusiness.jaaar.agendacalendarview.weather;

import com.monkeybusiness.jaaar.agendacalendarview.weather.models.Forecast;
import com.monkeybusiness.jaaar.agendacalendarview.weather.models.Request;

import retrofit.RestAdapter;
import rx.Observable;

/**
 * Created by thibaultguegan on 24/10/2015.
 */
public class WeatherRepository {
    private final WeatherAPI mWeatherAPI;

    // region Constructors

    public WeatherRepository() {
        RestAdapter weatherAPIRest = new RestAdapter.Builder()
                .setEndpoint(WeatherAPI.ENDPOINT)
                .setLogLevel(RestAdapter.LogLevel.HEADERS_AND_ARGS)
                .build();
        mWeatherAPI = weatherAPIRest.create(WeatherAPI.class);
    }

    // endregion

    // region Public methods

    public Observable<Forecast> getForecast(Request params) {
        return mWeatherAPI.getForecast(params, params.getQueryParams());
    }

    // endregion
}
