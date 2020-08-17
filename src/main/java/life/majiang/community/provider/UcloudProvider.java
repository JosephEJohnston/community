package life.majiang.community.provider;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

public class UcloudProvider {
    @Value("${ucloud.ufile.public-key}")
    private String clientId;
    @Value("${ucloud.ufile.private-key}")
    private String privateKey;
}
