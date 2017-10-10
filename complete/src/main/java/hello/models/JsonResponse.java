package hello.models;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/** Class for generic response from API
 *
 */
@JsonAutoDetect
public class JsonResponse implements Serializable {
    private final String success;
    private final Object data;
    private final String created;

    /** Initialise the object
     *
     * @param success   Was the request a success or not? (should really be a bool)
     * @param data      Content of response
     */
    public JsonResponse (
            String success, Object data
    ) {
        this.success = success;
        this.data = data;
        this.created = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
    }

    /** Retrieve Status of the request
     *
     * @return String indicating whether request was successful or not
     */
    @JsonProperty
    public String getSuccess() { return success; }

    /** Retrieve content of response
     *
     * @return Custom object of data, can be a list of strings, a message, a custom class, etc...
     */
    @JsonProperty
    public Object getData() { return data; }

    /** Retrieve date response was created
     *
     * @return A formatted date time string of when the response was created
     */
    @JsonProperty
    public String getCreated() { return created; }
}
