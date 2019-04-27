package pw.aru.libs.dd4j;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SuppressWarnings({"unused", "WeakerAccess"})
public final class InstantAnswer {
    private final String link;
    private final AnswerType type;
    private final String redirectUrl;
    private final String abstractHtml;
    private final String abstractText;
    private final String abstractSource;
    private final String abstractUrl;
    private final String imageUrl;
    private final String heading;
    private final Object answer;
    private final String answerType;
    private final String definitionText;
    private final String definitionSource;
    private final String definitionUrl;
    private final List<SimpleAnswer> relatedTopics;
    private final List<SimpleAnswer> results;

    public InstantAnswer(String link, AnswerType type, String redirectUrl, String abstractHtml, String abstractText, String abstractSource, String abstractUrl, String imageUrl, String heading, Object answer, String answerType, String definitionText, String definitionSource, String definitionUrl, List<SimpleAnswer> relatedTopics, List<SimpleAnswer> results) {
        this.link = link;
        this.type = type;
        this.redirectUrl = redirectUrl;
        this.abstractHtml = abstractHtml;
        this.abstractText = abstractText;
        this.abstractSource = abstractSource;
        this.abstractUrl = abstractUrl;
        this.imageUrl = imageUrl;
        this.heading = heading;
        this.answer = answer;
        this.answerType = answerType;
        this.definitionText = definitionText;
        this.definitionSource = definitionSource;
        this.definitionUrl = definitionUrl;
        this.relatedTopics = relatedTopics;
        this.results = results;
    }

    protected static InstantAnswer fromJSON(String encodedQuery, String encodedAppName, JSONObject obj) {
        try {
            String link = "https://duckduckgo.com/?q=" + encodedQuery + "&t=" + encodedAppName;
            AnswerType type = AnswerType.fromString(obj.getString("Type"));

            String redirectUrl = obj.getString("Redirect");

            String abstractHtml = obj.getString("Abstract");
            String abstractText = obj.getString("AbstractText");
            String abstractSource = obj.getString("AbstractSource");
            String abstractUrl = obj.getString("AbstractURL");
            String imageUrl = obj.getString("Image");
            String heading = obj.getString("Heading");

            Object answer = obj.get("Answer");
            String answerType = obj.getString("AnswerType");

            String definitionText = obj.getString("Definition");
            String definitionSource = obj.getString("DefinitionSource");
            String definitionUrl = obj.getString("DefinitionURL");

            JSONArray topicsArray = obj.getJSONArray("RelatedTopics");
            List<SimpleAnswer> relatedTopics = new ArrayList<>(topicsArray.length());
            for (int i = 0; i < topicsArray.length(); i++) {
                relatedTopics.add(SimpleAnswer.fromJSON(topicsArray.getJSONObject(i)));
            }

            JSONArray resultsArray = obj.getJSONArray("Results");
            List<SimpleAnswer> results = new ArrayList<>(topicsArray.length());
            for (int i = 0; i < resultsArray.length(); i++) {
                results.add(SimpleAnswer.fromJSON(resultsArray.getJSONObject(i)));
            }

            return new InstantAnswer(
                link,
                type,
                redirectUrl.equals("") ? null : redirectUrl,

                abstractHtml.equals("") ? null : abstractHtml,
                abstractText.equals("") ? null : abstractText,
                abstractSource.equals("") ? null : abstractSource,
                abstractUrl.equals("") ? null : abstractUrl,
                imageUrl.equals("") ? null : imageUrl,
                heading.equals("") ? null : heading,

                answer.equals("") ? null : answer,
                answerType.equals("") ? null : answerType,

                definitionText.equals("") ? null : definitionText,
                definitionSource.equals("") ? null : definitionSource,
                definitionUrl.equals("") ? null : definitionUrl,

                Collections.unmodifiableList(relatedTopics),
                Collections.unmodifiableList(results)
            );
        } catch (JSONException e) {
            throw new IllegalArgumentException("Error parsing JSON of InstantAnswer", e);
        }
    }

    public String getLink() {
        return link;
    }

    public AnswerType getType() {
        return type;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public String getAbstractHtml() {
        return abstractHtml;
    }

    public String getAbstractText() {
        return abstractText;
    }

    public String getAbstractSource() {
        return abstractSource;
    }

    public String getAbstractUrl() {
        return abstractUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getHeading() {
        return heading;
    }

    public Object getAnswer() {
        return answer;
    }

    public String getAnswerType() {
        return answerType;
    }

    public String getDefinitionText() {
        return definitionText;
    }

    public String getDefinitionSource() {
        return definitionSource;
    }

    public String getDefinitionUrl() {
        return definitionUrl;
    }

    public List<SimpleAnswer> getRelatedTopics() {
        return relatedTopics;
    }

    public List<SimpleAnswer> getResults() {
        return results;
    }

    @Override
    public String toString() {
        return "InstantAnswer{" +
            "link='" + link + '\'' +
            ", type=" + type +
            ", redirectUrl='" + redirectUrl + '\'' +
            ", abstractHtml='" + abstractHtml + '\'' +
            ", abstractText='" + abstractText + '\'' +
            ", abstractSource='" + abstractSource + '\'' +
            ", abstractUrl='" + abstractUrl + '\'' +
            ", imageUrl='" + imageUrl + '\'' +
            ", heading='" + heading + '\'' +
            ", answer=" + answer +
            ", answerType='" + answerType + '\'' +
            ", definitionText='" + definitionText + '\'' +
            ", definitionSource='" + definitionSource + '\'' +
            ", definitionUrl='" + definitionUrl + '\'' +
            ", relatedTopics=" + relatedTopics +
            ", results=" + results +
            '}';
    }
}
