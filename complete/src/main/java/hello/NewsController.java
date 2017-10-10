package hello;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import hello.models.News;
import hello.models.JsonResponse;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@RestController
@RequestMapping("/news")
public class NewsController {

    private final String _mongoDbConnection =  "mongodb://localhost:27017";
    private final String _mongoDbName =  "ArticleDb";

    /** Get all news articles
     *
     * @return JsonResponse containing all articles
     */
    @RequestMapping(method = RequestMethod.GET)
    public JsonResponse news() {
        List<News> articleList = new ArrayList<News>();

        MongoCollection<Document> collection = getCollection("articles");

        try(MongoCursor<Document> cursor = collection.find().sort(new BasicDBObject("published", -1)).iterator()) {
            while(cursor.hasNext()) {
                Document doc = cursor.next();
                News article = new News(doc.get("_id").toString(),
                        doc.getString("teamId"),
                        doc.getString("optaMatchId"),
                        doc.getString("title"),
                        (ArrayList<String>) doc.get("type"),
                        doc.getString("teaser"),
                        doc.getString("content"),
                        doc.getString("url"),
                        doc.getString("imageUrl"),
                        (ArrayList<String>) doc.get("galleryUrls"),
                        doc.getString("videoUrl"),
                        new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(((Date) doc.get("published"))));
                articleList.add(article);
            }
        }
        return new JsonResponse("success", articleList);
    }

    /** Retrieve an individual article by id
     *
     * @param id the id of the article
     * @return JsonResponse containing article data (if exists)
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public JsonResponse newsItem(@PathVariable String id) {
        Document dbArticle = null;
        MongoCollection<Document> collection = getCollection("articles");
        BasicDBObject query = new BasicDBObject("_id", new ObjectId(id));
        dbArticle = collection.find(query).first();
        if(dbArticle != null) {
            News newsStory = new News(dbArticle.get("_id").toString(),
                    dbArticle.getString("teamId"),
                    dbArticle.getString("optaMatchId"),
                    dbArticle.getString("title"),
                    (ArrayList<String>) dbArticle.get("type"),
                    dbArticle.getString("teaser"),
                    dbArticle.getString("content"),
                    dbArticle.getString("url"),
                    dbArticle.getString("imageUrl"),
                    (ArrayList<String>) dbArticle.get("galleryUrls"),
                    dbArticle.getString("videoUrl"),
                    new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(((Date) dbArticle.get("published"))));

            return new JsonResponse("success", newsStory);
        } else {
            return new JsonResponse( "error", "No Article by that Id");
        }
    }

    /** Connect to Mongo DB and retrieve a specified document/collection
     *
     * @param collectionName the collection requested
     * @return the requested document from the MongoDB
     */
    private MongoCollection<Document> getCollection (String collectionName) {
        MongoClient mongoClient = new MongoClient(new MongoClientURI(_mongoDbConnection));
        MongoDatabase database = mongoClient.getDatabase(_mongoDbName);
        return database.getCollection(collectionName);
    }
}
