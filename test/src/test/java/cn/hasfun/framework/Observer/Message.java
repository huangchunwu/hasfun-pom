package cn.hasfun.framework.Observer;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Message {

    private int id;

    private String content;
}
