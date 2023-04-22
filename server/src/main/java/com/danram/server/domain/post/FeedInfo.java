package com.danram.server.domain.post;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class FeedInfo implements Serializable {
    private Long partyId;
    private Long postId;
}
