package jp.tentus.web.mix.spring4;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * Laravel Mix が管理するアセットへの参照方法を提供します。
 */
@Component
@Slf4j
public class Asset {

    @Autowired
    private MixConfiguration configuration;

    @Autowired
    private ResourceLoader resourceLoader;

    /**
     * Dev Server の稼働状況に応じて、参照するアセットの URL を返します。
     * <p>
     * classpath:static/hot の有無によって切り替えを行うため、
     * Laravel Mix の Public Path が src/main/resources/static になっている必要があります。
     *
     * @param path 対象アセットのパス。
     * @return アセットを参照する URL 。
     */
    public String url(String path) {
        val resource = this.resourceLoader.getResource(this.configuration.getHotPath());
        val attributes = RequestContextHolder.currentRequestAttributes();
        UriComponentsBuilder builder;

        if (resource.exists() && (attributes instanceof ServletRequestAttributes)) {
            val request = ((ServletRequestAttributes) attributes).getRequest();

            builder = UriComponentsBuilder
                    .fromHttpRequest(new ServletServerHttpRequest(request))
                    .port(this.configuration.getPort());
        } else {
            builder = UriComponentsBuilder.newInstance();
        }

        return builder
                .replacePath(path)
                .build()
                .toString();
    }

}
