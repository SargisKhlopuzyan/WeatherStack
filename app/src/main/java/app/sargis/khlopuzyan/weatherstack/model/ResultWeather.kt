package app.sargis.khlopuzyan.weatherstack.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ResultWeather(

    @Json(name = "current")
    val current: Current,

    @Json(name = "location")
    val location: Location,

    @Json(name = "request")
    val request: Request
)

@JsonClass(generateAdapter = true)
data class Request(

    @Json(name = "language")
    val language: String,

    @Json(name = "query")
    val query: String,

    @Json(name = "type")
    val type: String,

    @Json(name = "unit")
    val unit: String
)

@JsonClass(generateAdapter = true)
data class Location(

    @Json(name = "country")
    val country: String,

    @Json(name = "lat")
    val lat: String,

    @Json(name = "localtime")
    val localtime: String,

    @Json(name = "localtime_epoch")
    val localtimeEpoch: Int,

    @Json(name = "lon")
    val lon: String,

    @Json(name = "name")
    val name: String,

    @Json(name = "region")
    val region: String,

    @Json(name = "timezone_id")
    val timezoneId: String,

    @Json(name = "utc_offset")
    val utcOffset: String
)

@Entity(tableName = "current")
@JsonClass(generateAdapter = true)
data class Current(

    @PrimaryKey(autoGenerate = true)
    private var id: Long = 0.toLong(),

    var queryId: String,

    @Json(name = "cloudcover")
    val cloudCover: Int,

    @Json(name = "feelslike")
    val feelsLike: Int,

    @Json(name = "humidity")
    val humidity: Int,

    @Json(name = "is_day")
    val isDay: String,

    @Json(name = "observation_time")
    val observation_time: String,

    @Json(name = "precip")
    val precip: Int,

    @Json(name = "pressure")
    val pressure: Int,

    @Json(name = "temperature")
    val temperature: Int,

    @Json(name = "uv_index")
    val uvIndex: Int,

    @Json(name = "visibility")
    val visibility: Int,

    @Json(name = "weather_code")
    val weatherCode: Int,

    @Json(name = "weather_descriptions")
    val weatherDescriptions: List<String>,

    @Json(name = "weather_icons")
    val weatherIcons: List<String>,

    @Json(name = "wind_degree")
    val windDegree: Int,

    @Json(name = "wind_dir")
    val windDir: String,

    @Json(name = "wind_speed")
    val windSpeed: Int
)