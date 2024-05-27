package bral.museum;

import bral.museum.json.ArtObject;
import bral.museum.json.ArtObjects;
import org.junit.jupiter.api.Test;

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
                apiKey.toString(),
                1
        ).blockingGet();

        // then
        for (ArtObject artObject : artObjectsResponse.artObjects)
        {
            assertNotEquals("", artObject.longTitle);
            assertNotEquals("", artObject.title);
            assertNotEquals("", artObject.principalOrFirstMaker);
            assertNotNull(artObject.webImage);
            assertNotEquals("", artObject.webImage.url);
        }
    }

    @Test
    public void searchCollectionByQuery()
    {
        // given
        ApiKey apiKey = new ApiKey();
        RijksService service = new RijksServiceFactory().getService();

        // when
        ArtObjects artObjectsResponse = service.searchCollectionByQuery(
                apiKey.toString(),
                "rembrandt",
                1
        ).blockingGet();

        // then
        for (ArtObject artObject : artObjectsResponse.artObjects)
        {
            assertNotEquals("", artObject.longTitle);
            assertNotEquals("", artObject.title);
            assertNotEquals("", artObject.principalOrFirstMaker);
            assertNotNull(artObject.webImage);
            assertNotEquals("", artObject.webImage.url);
        }
    }

    @Test
    public void searchCollectionByArtist()
    {
        // given
        ApiKey apiKey = new ApiKey();
        RijksService service = new RijksServiceFactory().getService();

        // when
        ArtObjects artObjectsResponse = service.searchCollectionByArtist(
                apiKey.toString(),
                "Vermeer",
                1
        ).blockingGet();

        // then
        for (ArtObject artObject : artObjectsResponse.artObjects)
        {
            assertNotEquals("", artObject.longTitle);
            assertNotEquals("", artObject.title);
            assertNotEquals("", artObject.principalOrFirstMaker);
            assertNotNull(artObject.webImage);
            assertNotEquals("", artObject.webImage.url);
        }
    }

}
