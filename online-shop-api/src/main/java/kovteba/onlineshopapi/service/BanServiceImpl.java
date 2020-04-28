package kovteba.onlineshopapi.service;

import kovteba.onlineshopapi.entity.BanEntity;
import kovteba.onlineshopapi.repository.BanRepository;
import kovteba.onlineshopapi.responce.Responce;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class BanServiceImpl implements BanService {

    private final BanRepository banRepository;

    public BanServiceImpl(BanRepository banRepository) {
        this.banRepository = banRepository;
    }

    @Override
    public Responce banUserByEmail(String email) {
        return new Responce(HttpStatus.CREATED, banRepository.save(new BanEntity(email)));
    }

    @Override
    public Responce unBanUserByEmail(String email) {
        banRepository.delete(banRepository.findByEmail(email));
        BanEntity banEntity = banRepository.findByEmail(email);
        if (banEntity == null){
            return new Responce(HttpStatus.CREATED, "BAN RECORD DELETED");
        } else {
            return new Responce(HttpStatus.BAD_REQUEST, "SOMETHING WRONG");
        }
    }

    @Override
    public Responce findBanRecordByEmail(String email) {
        Responce responce = new Responce();
        BanEntity banEntity = banRepository.findByEmail(email);
        if (banEntity != null){
            responce.setStatus(HttpStatus.OK);
            responce.setObject(banEntity);
        } else {
            responce.setStatus(HttpStatus.NOT_FOUND);
            responce.setObject(null);
        }
        return responce;
    }
}
