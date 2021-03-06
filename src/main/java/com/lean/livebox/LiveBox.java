package com.lean.livebox;

import com.lean.livebox.core.Extractor;
import com.lean.livebox.core.Live;
import com.lean.livebox.core.Platform;
import com.lean.livebox.core.extractors.DouyuExtractor;
import com.lean.livebox.core.extractors.PandaExtractor;
import com.lean.livebox.core.extractors.ZhanqiExtractor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by lean on 16/7/5.
 */
public class LiveBox {
    private final static Pattern PLATFORM_PATTERN = Pattern.compile("^http://www\\.(.+)\\.[tv|com]");

    private static Extractor douyuExtractor = new DouyuExtractor();
    private static Extractor zhanqiExtractor = new ZhanqiExtractor();
    private static Extractor pandaExtractor = new PandaExtractor();

    private LiveBox() {
    }

    private static Platform determinePlatform(String url) {
        Matcher matcher = PLATFORM_PATTERN.matcher(url);
        if (matcher.find()) {
            String domain = matcher.group(1);
            return Platform.valueOf(domain.toUpperCase());
        }

        return null;
    }

    public static Live extractLive(String url) {
        Platform platform = determinePlatform(url);
        if (platform != null) {
            switch (platform) {
                case DOUYU:
                    return douyuExtractor.extract(url);
                case ZHANQI:
                    return zhanqiExtractor.extract(url);
                case PANDA:
                    return pandaExtractor.extract(url);
            }
        }

        return null;
    }
}
