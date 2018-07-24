package pw.aru.api.dd4j;

public enum AnswerType {
    //Type: response category, i.e. A (article), D (disambiguation), C (category), N (name), E (exclusive), or nothing.
    ARTICLE, DISAMBIGUATION, CATEGORY, NAME, EXCLUSIVE, UNKNOWN;

    public static AnswerType fromString(String type) {
        switch (type) {
            case "A":
                return ARTICLE;
            case "D":
                return DISAMBIGUATION;
            case "C":
                return CATEGORY;
            case "N":
                return NAME;
            case "E":
                return EXCLUSIVE;
            default:
                return UNKNOWN;
        }
    }
}
