package com.github.gubbib.backend.Service.PointLog;

import com.github.gubbib.backend.Domain.PointLog.PointLog;
import com.github.gubbib.backend.Domain.User.User;
import com.github.gubbib.backend.Repository.PointLog.PointLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor_ =  @Autowired)
public class PointLogServiceImp implements PointLogService {

    private final PointLogRepository pointLogRepository;

    @Override
    public void earn(User user, Long amount, String description) {
        PointLog pl = PointLog.earn(user, amount, description);
        pointLogRepository.save(pl);
    }

    @Override
    public void use(User user, Long amount, String description) {
        PointLog pl = PointLog.use(user, amount, description);
        pointLogRepository.save(pl);
    }
}
