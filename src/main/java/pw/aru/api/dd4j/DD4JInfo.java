package pw.aru.api.dd4j;

@SuppressWarnings({"unused", "WeakerAccess"})
public class DD4JInfo {
    public static final String VERSION_MAJOR = "@VERSION_MAJOR@";
    public static final String VERSION_MINOR = "@VERSION_MINOR@";
    public static final String VERSION_REVISION = "@VERSION_REVISION@";
    public static final String COMMIT = "@COMMIT_HASH@";
    public static final String COMMIT_MESSAGE = "@COMMIT_MESSAGE@";
    @SuppressWarnings("ConstantConditions")
    public static final String VERSION = VERSION_MAJOR.startsWith("@") ? "dev" : String.format("%s.%s.%s", VERSION_MAJOR, VERSION_MINOR, VERSION_REVISION);

    public static final String REPOSITORY = "https://github.com/arubot/dd4j";

    public static final String USER_AGENT = String.format("DD4J/%s/v%s/commit %s", REPOSITORY, VERSION, COMMIT);
}
