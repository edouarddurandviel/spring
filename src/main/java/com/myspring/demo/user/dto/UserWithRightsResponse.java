package com.myspring.demo.user.dto;

import com.myspring.demo.rights.dto.RightsResponse;

public record UserWithRightsResponse(
    Long userId,
    String userEmail,
    RightsResponse perms
) {}


