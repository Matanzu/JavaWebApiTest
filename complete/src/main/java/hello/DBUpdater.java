package hello;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import hello.models.NewsArticleModel;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import static com.mongodb.client.model.Filters.eq;

@Component
public class DBUpdater {
    /** Refresh the news articles in the DB from the feed
     *
     */
    @Scheduled(fixedDelay = 300000)
    public void refreshNewsArticles() {
        Document doc = getXmlDocument("https://www.brentfordfc.com/api/incrowd/getnewlistinformation?count=50");
        if(doc != null) {
            NodeList mainArticles = doc.getElementsByTagName("NewsletterNewsItem");

            for (int i = 0; i < mainArticles.getLength(); i++) {
                Node article = mainArticles.item(i);
                if(article.getNodeType() == Node.ELEMENT_NODE) {
                    try {
                        Element articleItem = (Element)article;
                        String articleId = articleItem.getElementsByTagName("NewsArticleID").item(0).getTextContent();
                        Document articleBody = getXmlDocument("https://www.brentfordfc.com/api/incrowd/getnewsarticleinformation?id=" + articleId);
                        Node articleBodyNode = articleBody.getElementsByTagName("NewsArticle").item(0);
                        Element newsArticle = (Element)articleBodyNode;
                        NewsArticleModel newsItem = new NewsArticleModel(
                                articleId,
                                "", // in the example this appears to be an optaTeamId that is not in the feed, probably has an external lookup
                                newsArticle.getElementsByTagName("OptaMatchId").item(0).getTextContent(),
                                newsArticle.getElementsByTagName("Title").item(0).getTextContent(),
                                Arrays.asList(newsArticle.getElementsByTagName("Taxonomies").item(0).getTextContent().split(",")),
                                newsArticle.getElementsByTagName("TeaserText").item(0).getTextContent(),
                                newsArticle.getElementsByTagName("BodyText").item(0).getTextContent(), // TODO - how do I convert the HTML to something friendly like unicode?
                                newsArticle.getElementsByTagName("ArticleURL").item(0).getTextContent(),
                                newsArticle.getElementsByTagName("ThumbnailImageURL").item(0).getTextContent(),
                                Arrays.asList(newsArticle.getElementsByTagName("GalleryImageURLs").item(0).getTextContent().split(",")),
                                newsArticle.getElementsByTagName("Title").item(0).getTextContent(),
                                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse( newsArticle.getElementsByTagName("PublishDate").item(0).getTextContent() )
                        );

                        writeToDb(newsItem);

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                }
            }
        }
    }

    /** Retrieve an XML document via a get request
     *
     * @param url the URL to access to retrieve the document
     * @return the xml document
     */
    private Document getXmlDocument (String url) {
        // write your code here
        URL requestUrl = null;
        Document xmlDocument = null;
        try {
            requestUrl = new URL(url);
            URLConnection urlConnection = requestUrl.openConnection();
            // need to dupe the service in to thinking it's coming from a browser
            urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.1; en-GB;     rv:1.9.2.13) Gecko/20101203 Firefox/3.6.13 (.NET CLR 3.5.30729)");
            InputStream urlResponse = urlConnection.getInputStream();
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            docFactory.setIgnoringComments(true);
            docFactory.setIgnoringElementContentWhitespace(true);
            docFactory.setValidating(false);

            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            xmlDocument = docBuilder.parse(new InputSource(urlResponse));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
        return xmlDocument;
    }

    /** Write the news article to the MongoDB. If an article already exists, update existing one
     *
     * @param article The news article to update
     */
    private void writeToDb(NewsArticleModel article) {
        org.bson.Document dbArticle = null;
        MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
        MongoDatabase database = mongoClient.getDatabase("ArticleDb");
        MongoCollection<org.bson.Document> collection = database.getCollection("articles");
        // set up new document
        org.bson.Document newItem = new org.bson.Document("articleId", article.getArticleId())
                .append("teamId", article.getTeamId())
                .append("optaMatchId", article.getOptaMatchId())
                .append("title", article.getTitle())
                .append("type", article.getType())
                .append("teaser", article.getTeaser())
                .append("content", article.getContent())
                .append("url", article.getUrl())
                .append("imageUrl", article.getImageUrl())
                .append("galleryUrls", article.getGalleryUrls())
                .append("videoUrls", article.getVideoUrl())
                .append("published", article.getPublished());

        dbArticle = collection.find(eq("articleId", article.getArticleId())).first();
        if(dbArticle == null) {
            collection.insertOne(newItem);
        } else {
            Object articleGuid = dbArticle.get("_id");
            collection.replaceOne(eq("_id", articleGuid), newItem);
        }
        // manually closing connection
        mongoClient.close();
    }
}
