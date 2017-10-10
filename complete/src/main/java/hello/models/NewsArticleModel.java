package hello.models;

import java.util.Date;
import java.util.List;

/** Collect all relevant data relating to the news article in this object before saving to the DB
 *
 */
public class NewsArticleModel {
    private final String articleId;
    private final String teamId;
    private final String optaMatchId;
    private final String title;
    private final List<String> type;
    private final String teaser;
    private final String content;
    private final String url;
    private final String imageUrl;
    private final List<String> galleryUrls;
    private final String videoUrl;
    private final Date published;

    /** Create a new instance of a NewsArticle
     *
     * @param articleId     The id of the article
     * @param teamId        The team id it relates to
     * @param optaMatchId   The opta match id it relates to
     * @param title         The title of the article
     * @param type          The type of content
     * @param teaser        A short headline summarising the article
     * @param content       The content
     * @param url           The url to the full article
     * @param imageUrl      The url to the image of the article
     * @param galleryUrls   A list of image URls for an image gallery
     * @param videoUrl      The url to the relevant video
     * @param published     The date the story was published
     */
    public NewsArticleModel (String articleId, String teamId, String optaMatchId,
                             String title, List<String> type, String teaser, String content,
                             String url, String imageUrl, List<String> galleryUrls, String videoUrl,
                             Date published) {
        this.articleId = articleId;
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

    /** Retrieve the Article Id
     *
     * @return The ID of the Article
     */
    public String getArticleId() {return articleId;}

    /** Retrieve the Team Id the article relates to
     *
     * @return the ID of the team this relates to
     */
    public String getTeamId() {return teamId;}

    /** Retrieve the Opta Match Id the article relates to
     *
     * @return The ID of the match that Opta uses in their system
     */
    public String getOptaMatchId() {return optaMatchId;}

    /** Retrieve the Title of the article
     *
     * @return The title
     */
    public String getTitle() {return title;}

    /** Retrieve a list of types relating to the content (e.g. video, images, etc...)
     *
     * @return a list of media content
     */
    public List<String> getType() {return type;}

    /** Retrieve the teaser text for the article
     *
     * @return String containing teaser text
     */
    public String getTeaser() {return teaser;}

    /** Retrieve the main content of the article
     *
     * @return String containing content
     */
    public String getContent() {return content;}

    /** Retrieve the URL of the article
     *
     * @return String containing the URL of the news article
     */
    public String getUrl() {return url;}

    /** Retrieve the URL of the thumbnail image for the article
     *
     * @return A String containing the URL of the article
     */
    public String getImageUrl() {return imageUrl;}

    /** Retrieve A list of other image URLs
     *
     * @return A List of image URLs for the article
     */
    public List<String> getGalleryUrls() {return galleryUrls;}

    /** Retrieve The URL for the video (if in the story)
     *
     * @return String containing the URL to link to for the video content
     */
    public String getVideoUrl() {return videoUrl;}

    /** Retrieve the date the story was published
     *
     * @return Date Time of the published article
     */
    public Date getPublished() {return published;}
}
