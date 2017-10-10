package hello.models;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.ArrayList;

/** Class represents a single news article stored within the local database
 *
 */
@JsonAutoDetect
public class News implements Serializable {

    private final String id;
    private final String teamId;
    private final String optaMatchId;
    private final String title;
    private final ArrayList<String> type;
    private final String teaser;
    private final String content;
    private final String url;
    private final String imageUrl;
    private final ArrayList<String> galleryUrls;
    private final String videoUrl;
    private final String published;

    /** Create a new instance of this object and store data relating to the news article
     *
     * @param id            The unique identifier for the article in the DB
     * @param teamId        The team Id associated with the article
     * @param optaMatchId   The opta match id associated with the article
     * @param title         The title of the article
     * @param type          A list of "taxonomies", basically the topics of the article (club news, injury news, etc...)
     * @param teaser        A short teaser about the article
     * @param content       The full content
     * @param url           The URL for the article
     * @param imageUrl      The URL of the preview thumbnail
     * @param galleryUrls   A list of URLs for additional images forming a gallery
     * @param videoUrl      The URL to link to if there is any video content (not embedded)
     * @param published     The date the story was published
     */
    public News(String id, String teamId, String optaMatchId, String title,
                ArrayList<String> type, String teaser, String content, String url,
                String imageUrl, ArrayList<String> galleryUrls, String videoUrl,
                String published) {
        this.id = id;
        this.teamId = teamId;
        this.optaMatchId = optaMatchId;
        this.title = title;
        this.type = type;
        this.teaser = teaser;
        this.content = content;
        this.url = url;
        this.imageUrl = imageUrl;
        this.galleryUrls = galleryUrls;
        this.videoUrl = videoUrl;
        this.published = published;
    }

    /** Retrieve the Id of the Item
     *
     * @return String containing the Id of the news article (GUID)
     */
    @JsonProperty
    public String getId() {return id;}

    /** Retrieve the team Id associated with the article
     *
     * @return String containing the team Id as stored in the DB
     */
    @JsonProperty
    public String getTeamId() {return teamId;}

    /** Retrieve the Opta Match Id associated with the article
     *
     * @return String containing the opta match ID as stored in the DB
     */
    @JsonProperty
    public String getOptaMatchId() {return optaMatchId;}

    /** Retrieve the Title associated with the article
     *
     * @return String containing the title of the article
     */
    @JsonProperty
    public String getTitle() {return title;}

    /** Retrieve the Type associated with the article
     *
     * @return An Array of strings, one for each category relating to this article
     */
    @JsonProperty
    public ArrayList<String> getType() {return type;}

    /** Retrieve the Teaser associated with the article
     *
     * @return String containing the teaser of the article
     */
    @JsonProperty
    public String getTeaser() {return teaser;}

    /** Retrieve the Content associated with the article
     *
     * @return String containing the content of the article
     */
    @JsonProperty
    public String getContent() {return content;}

    /** Retrieve the Url associated with the article
     *
     * @return String containing the Url of the article
     */
    @JsonProperty
    public String getUrl() {return url;}

    /** Retrieve the Thumbnail Image Url associated with the article
     *
     * @return String containing the url of the thumbnail image
     */
    @JsonProperty
    public String getImageUrl() {return imageUrl;}

    /** Retrieve the List of Gallery Images associated with the article
     *
     * @return A list of strings, one for each image in the gallery
     */
    @JsonProperty
    public ArrayList<String> getGalleryUrls() {return galleryUrls;}

    /** Retrieve the Url of the Video associated with the article
     *
     * @return String containing the URL of the video (not embedded)
     */
    @JsonProperty
    public String getVideoUrl() {return videoUrl;}

    /** Retrieve the Published date of the article
     *
     * @return The date the article was published.
     */
    @JsonProperty
    public String getPublished() {return published;}
}
