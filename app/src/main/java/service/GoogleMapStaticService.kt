package service


import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


interface GoogleMapStaticService {
   /* val retrofit: Retrofit
        get() = Retrofit.Builder()
                .baseUrl(  "https://maps.googleapis.com")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()

//    @GET("maps/api/place/nearbysearch/json")
    @GET("maps/api/place/nearbysearch/json")
fun getPropertyImage(@Query("zoom") zoom: Int?,
                  @Query("size") size: String,
                  @Query("maptype") maptype: String?,
                  @Query("markers") markers: String?,
                  @Query("key") key: String?): Observable<GoogleMapApiClass?>?


    // Build part of url to get photo of restaurant
  //  var urlPart1: String = getString(R.string.url_google_place_photo_part1)
    //var urlPart2: String = restaurantPhotoUrl
    //var urlPart3: String = getString(R.string.url_google_place_photo_part3)
    //urlConcat = urlPart1 + urlPart2 + urlPart3

    fun getRestaurant(@Query("location") location: String?,
                      @Query("radius") radius: Int,
                      @Query("type") type: String?,
                      @Query("key") key: String?): Observable<GoogleMapApiClass?>?


    @GET("/maps/api/place/details/json")
    fun getRestaurantPhoneAndWebsite(@Query("place_id") place_id: String?,
                                     @Query("key") key: String?): Observable<GooglePlaceDetailApiClass?>?

    */
}

//https://maps.googleapis.com/maps/api/staticmap?zoom=13&size=300x300&maptype=roadmap%20
// &markers=color:red%7Clabel:C%7C40.718217,-73.998284%20&key=AIzaSyCRwilK4p9DLKw5ZH86oGSKb8MR8W5jaHE

//  var urlPart1: String = https://maps.googleapis.com/maps/api/staticmap?zoom=13&size=300x300&maptype=roadmap%20&markers=color:red%7Clabel:C%7C
//var urlPart2: String = 40.718217,-73.998284
//var urlPart3: String = %20&key=AIzaSyCRwilK4p9DLKw5ZH86oGSKb8MR8W5jaHE
//urlConcat = urlPart1 + urlPart2 + urlPart3




