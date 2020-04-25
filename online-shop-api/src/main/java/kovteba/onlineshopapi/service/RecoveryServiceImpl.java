package kovteba.onlineshopapi.service;

import kovteba.onlineshopapi.entity.Recovery;
import kovteba.onlineshopapi.repository.RecoveryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecoveryServiceImpl implements RecoveryService {

    @Autowired
    private RecoveryRepository recoveryRepository;

    @Override
    public void addNewRecovery(Recovery recovery) {
        recoveryRepository.save(recovery);
    }

    @Override
    public Recovery getRecoveryByEmail(String email) {
        return recoveryRepository.getByEmail(email);
    }
}
