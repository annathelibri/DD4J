package pw.aru.libs.dd4j;

import com.github.natanbc.reliqua.Reliqua;
import com.github.natanbc.reliqua.limiter.RateLimiter;
import com.github.natanbc.reliqua.limiter.factory.RateLimiterFactory;
import com.github.natanbc.reliqua.request.PendingRequest;
import com.github.natanbc.reliqua.util.StatusCodeValidator;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.zip.GZIPInputStream;

@SuppressWarnings({"unused", "WeakerAccess"})
public class DD4J extends Reliqua implements AutoCloseable {
    private static final Logger LOGGER = LoggerFactory.getLogger("DD4J");
    private static final String API_BASE = "https://api.duckduckgo.com/";

    private final String appName;

    public DD4J() {
        this(new OkHttpClient(), RateLimiterFactory.directFactory(), null, false);
    }

    private DD4J(OkHttpClient httpClient, RateLimiterFactory factory, String appName, boolean trackCallSites) {
        super(httpClient, factory, trackCallSites);
        this.appName = appName == null ? "DD4J" : appName;
    }

    @Override
    public void close() {
        getClient().dispatcher().executorService().shutdown();
    }

    /**
     * Returns the application name given to the builder, sent on the query on the 't' argument.
     *
     * @return The application name.
     */
    @CheckReturnValue
    @Nonnull
    public String getAppName() {
        return appName;
    }

    /**
     * Queries Instant Answers API with the string specified on the query argument.
     *
     * @param query         The query to send.
     * @param no_html       If the results will contain a .
     * @param skip_disambig Offset for the results.
     * @return The bots found matching the given filter.
     */
    @CheckReturnValue
    @Nonnull
    public PendingRequest<InstantAnswer> query(@Nonnull String query, boolean no_html, boolean skip_disambig) {
        String encodedQuery = encodeQuery(query);
        String encodedAppName = encodeQuery(appName);
        Request request = new Request.Builder()
            .url(
                String.format(
                    "%s?q=%s&no_html=%d&skip_disambig=%d&t=%s&format=json&no_redirect=1",
                    API_BASE, encodedQuery, no_html ? 1 : 0, skip_disambig ? 1 : 0, encodedAppName
                )
            )
            .header("User-Agent", DD4JInfo.USER_AGENT)
            .header("Accept-Encoding", "gzip")
            .get()
            .build();

        return createRequest(request)
            .setStatusCodeValidator(StatusCodeValidator.ACCEPT_200)
            .setRateLimiter(getRateLimiter("/"))
            .build(response -> InstantAnswer.fromJSON(encodedQuery, encodedAppName, toJSONObject(response)), null);
    }

    /**
     * Queries Instant Answers API with the string specified on the query argument.
     *
     * @param query The query to send.
     * @return The bots found matching the given filter.
     */
    @CheckReturnValue
    @Nonnull
    public PendingRequest<InstantAnswer> query(@Nonnull String query) {
        return query(query, false, false);
    }

    @CheckReturnValue
    @Nonnull
    private static JSONObject toJSONObject(Response response) throws IOException {
        return new JSONObject(new JSONTokener(getInputStream(response)));
    }

    @CheckReturnValue
    @Nonnull
    private static String encodeQuery(String s) {
        try {
            return URLEncoder.encode(s, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    @CheckReturnValue
    @Nonnull
    private static InputStream getInputStream(Response response) throws IOException {
        ResponseBody body = response.body();
        if (body == null) throw new IllegalStateException("Body is null");
        String encoding = response.header("Content-Encoding");
        InputStream is = body.byteStream();
        if (encoding != null && encoding.equalsIgnoreCase("gzip")) {
            return new GZIPInputStream(is);
        }
        return is;
    }

    public static class Builder {
        private String appName;
        private RateLimiterFactory rateLimiterFactory;
        private OkHttpClient httpClient;
        private boolean trackCallSites;

        @CheckReturnValue
        @Nonnull
        public Builder setAppName(@Nullable String appName) {
            this.appName = appName;
            return this;
        }

        @CheckReturnValue
        @Nonnull
        @Deprecated
        public Builder setRateLimiter(@Nullable RateLimiter rateLimiter) {
            return setRateLimiterFactory(new RateLimiterFactory() {
                @Override
                public RateLimiter getRateLimiter(String key) {
                    return rateLimiter;
                }

                @Override
                protected RateLimiter createRateLimiter(String key) {
                    throw new UnsupportedOperationException();
                }
            });
        }

        @CheckReturnValue
        @Nonnull
        public Builder setRateLimiterFactory(@Nullable RateLimiterFactory rateLimiterFactory) {
            this.rateLimiterFactory = rateLimiterFactory;
            return this;
        }

        @CheckReturnValue
        @Nonnull
        public Builder setHttpClient(@Nullable OkHttpClient httpClient) {
            this.httpClient = httpClient;
            return this;
        }

        @CheckReturnValue
        @Nonnull
        public Builder setCallSiteTrackingEnabled(boolean enabled) {
            this.trackCallSites = enabled;
            return this;
        }

        @CheckReturnValue
        @Nonnull
        public DD4J build() {
            return new DD4J(
                httpClient == null ? new OkHttpClient() : httpClient,
                rateLimiterFactory,
                appName,
                trackCallSites
            );
        }
    }
}
