package life.majiang.community.dto;

import lombok.Data;

// 是为了接收 token 的五个参数
@Data
public class AccessTokenDTO {
    private String client_id;
    private String client_secret;
    private String code;
    private String redirect_uri;
    private String state;
}
