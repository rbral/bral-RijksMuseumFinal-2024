package bral.museum;

import bral.museum.json.ArtObjects;
import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * https://www.rijksmuseum.nl/
 */
public interface RijksService {
    @GET("/api/en/collection")
    Single<ArtObjects> getCollectionByPage(
            @Query("key") String apiKey,
            @Query("p") int page
    );

    @GET("/api/en/collection")
    Single<ArtObjects> searchCollectionByQuery(
            @Query("key") String apiKey,
            @Query("q") String query,
            @Query("p") int page
    );

    @GET("/api/en/collection")
    Single<ArtObjects> searchCollectionByArtist(
            @Query("key") String apiKey,
            @Query("principalOrFirstMaker") String artist,
            @Query("p") int page
    );


}