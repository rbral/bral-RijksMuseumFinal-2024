package bral.museum;

import bral.museum.json.ArtObject;
import bral.museum.json.ArtObjects;
import org.junit.jupiter.api.Test;
import com.andrewoid.ApiKey;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class RijksServiceTest {

    @Test
    public void getCollectionByPage()
    {
        // given
        ApiKey apiKey = new ApiKey();
        RijksService service = new RijksServiceFactory().getService();

        // when
        ArtObjects artObjectsResponse = service.getCollectionByPage(
                apiKey.get(),
                1
        ).blockingGet();

        // then
        ArtObject artObject = artObjectsResponse.artObjects.get(0);
        assertNotNull(artObject.longTitle);
        assertNotNull(artObject.title);
        assertNotNull(artObject.principalOrFirstMaker);
        assertNotNull(artObject.webImage);
        assertNotNull(artObject.webImage.url);
    }

    @Test
    public void searchCollectionByQuery()
    {
        // given
        ApiKey apiKey = new ApiKey();
        RijksService service = new RijksServiceFactory().getService();

        // when
        ArtObjects artObjectsResponse = service.searchCollectionByQuery(
                apiKey.get(),
                "rembrandt",
                1
        ).blockingGet();

        // then
        ArtObject artObject = artObjectsResponse.artObjects.get(0);
        assertNotNull(artObject.longTitle);
        assertNotNull(artObject.title);
        assertNotNull(artObject.principalOrFirstMaker);
        assertNotNull(artObject.webImage);
        assertNotNull(artObject.webImage.url);

    }

    @Test
    public void searchCollectionByArtist()
    {
        // given
        ApiKey apiKey = new ApiKey();
        RijksService service = new RijksServiceFactory().getService();

        // when
        ArtObjects artObjectsResponse = service.searchCollectionByArtist(
                apiKey.get(),
                "Vermeer",
                1
        ).blockingGet();

        // then
        ArtObject artObject = artObjectsResponse.artObjects.get(0);
        assertNotNull(artObject.longTitle);
        assertNotNull(artObject.title);
        assertNotNull(artObject.principalOrFirstMaker);
        assertNotNull(artObject.webImage);
        assertNotNull(artObject.webImage.url);
    }

}
