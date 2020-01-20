package test.minio;

/**
 * @创建人 zhaojingen
 * @创建时间 2019/10/29
 * @描述
 */
import java.util.ArrayList;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.InvalidKeyException;

import org.json.JSONArray;
import org.xmlpull.v1.XmlPullParserException;

import io.minio.MinioClient;
import io.minio.Result;
import io.minio.messages.Bucket;
import io.minio.messages.Item;
import io.minio.errors.MinioException;

public class AlbumDao {
    public static  List<Album> listAlbums() throws NoSuchAlgorithmException,
            IOException, InvalidKeyException, XmlPullParserException, MinioException {

        List<Album> list = new ArrayList<Album>();
        final String minioBucket = "lego";

        // Initialize minio client object.
        MinioClient minioClient = new MinioClient("127.0.0.1", 9000,
                "DVKMS0A8MLXGRWOQWJQ7",
                "xScPRShj9naQBCoF4WRy73g7vtEg+hGABp18ScEt");

        // List all objects.
        Iterable<Result<Item>> myObjects = minioClient.listObjects(minioBucket);

        // Iterate over each elements and set album url.
        for (Result<Item> result : myObjects) {
            Item item = result.get();
            System.out.println(item.lastModified() + ", " + item.size() + ", " + item.objectName());

            // Generate a presigned URL which expires in a day
            String url = minioClient.presignedGetObject(minioBucket, item.objectName(), 60 * 60 * 24);

            // Create a new Album Object
            Album album = new Album();

            // Set the presigned URL in the album object
            album.setUrl(url);

            // Add the album object to the list holding Album objects
            list.add(album);

        }

        // Return list of albums.
        return list;
    }

    public static void main(String[] args) throws Exception{
        List<Album> albums =  listAlbums();
        System.out.println(albums);
    }
}
