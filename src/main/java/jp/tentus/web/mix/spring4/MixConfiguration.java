package jp.tentus.web.mix.spring4;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Laravel Mix 連携の設定を表します。
 */
@Component
@ConfigurationProperties(prefix = "mix")
@Data
public class MixConfiguration {

    /**
     * Laravel Mix の hot ファイルのパスを表します。
     */
    private String hotPath = "classpath:static/hot";

    /**
     * Laravel Mix の Dev Server ポート番号を表します。
     */
    private int port;

}
