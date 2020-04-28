package kovteba.onlineshopapi.service;

import kovteba.onlineshopapi.responce.Responce;

public interface BanService {

    Responce banUserByEmail(String email);

    Responce unBanUserByEmail(String email);

    Responce findBanRecordByEmail(String email);

}
