package pw.aru.api.dd4j;

import org.json.JSONException;
import org.json.JSONObject;

public final class SimpleAnswer {
    private final String htmlResult;
    private final String firstUrl;
    private final String firstUrlText;
    private final String iconUrl;
    private final int iconWidth;
    private final int iconHeight;

    public SimpleAnswer(String htmlResult, String firstUrl, String firstUrlText, String iconUrl, int iconWidth, int iconHeight) {
        this.htmlResult = htmlResult;
        this.firstUrl = firstUrl;
        this.firstUrlText = firstUrlText;
        this.iconUrl = iconUrl;
        this.iconWidth = iconWidth;
        this.iconHeight = iconHeight;
    }

    protected static SimpleAnswer fromJSON(JSONObject topicJson) {
        try {
            String htmlResult = topicJson.optString("Result");
            String firstUrl = topicJson.optString("FirstURL");
            String firstUrlText = topicJson.optString("Text");

            JSONObject iconJson = topicJson.optJSONObject("Icon");
            if (iconJson == null) {
                return new SimpleAnswer(htmlResult, firstUrl, firstUrlText, null, 0, 0);
            }
            String iconUrl = iconJson.optString("URL");
            int iconWidth;
            int iconHeight;

            try {
                iconWidth = Integer.parseInt(iconJson.optString("Width"));
            } catch (NumberFormatException | JSONException e) {
                iconWidth = 0;
            }

            try {
                iconHeight = Integer.parseInt(iconJson.optString("Height"));
            } catch (NumberFormatException | JSONException e) {
                iconHeight = 0;
            }

            return new SimpleAnswer(
                htmlResult.equals("") ? null : htmlResult,
                firstUrl.equals("") ? null : firstUrl,
                firstUrlText.equals("") ? null : firstUrlText,
                iconUrl.equals("") ? null : iconUrl,
                iconWidth,
                iconHeight
            );
        } catch (JSONException e) {
            throw new IllegalArgumentException("Error parsing JSON of SimpleAnswer", e);
        }
    }

    public String getHtmlResult() {
        return htmlResult;
    }

    public String getFirstUrl() {
        return firstUrl;
    }

    public String getFirstUrlText() {
        return firstUrlText;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public int getIconWidth() {
        return iconWidth;
    }

    public int getIconHeight() {
        return iconHeight;
    }

    @Override
    public String toString() {
        return "SimpleAnswer{" +
            "htmlResult='" + htmlResult + '\'' +
            ", firstUrl='" + firstUrl + '\'' +
            ", firstUrlText='" + firstUrlText + '\'' +
            ", iconUrl='" + iconUrl + '\'' +
            ", iconWidth=" + iconWidth +
            ", iconHeight=" + iconHeight +
            '}';
    }
}
