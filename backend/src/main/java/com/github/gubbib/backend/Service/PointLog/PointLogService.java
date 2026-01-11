package com.github.gubbib.backend.Service.PointLog;

import com.github.gubbib.backend.Domain.User.User;

public interface PointLogService {

    void earn(User user, Long amount, String description);

    void use(User user, Long amount, String description);

}
