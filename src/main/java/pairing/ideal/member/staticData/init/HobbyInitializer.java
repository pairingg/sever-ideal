package pairing.ideal.member.staticData.init;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pairing.ideal.member.staticData.entity.HobbyList;
import pairing.ideal.member.staticData.repository.HobbyListRepository;

@Component
@RequiredArgsConstructor
public class HobbyInitializer implements CommandLineRunner {

    private final HobbyListRepository hobbyListRepository;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (hobbyListRepository.count() == 0) {
            hobbyListRepository.save(new HobbyList("운동"));
            hobbyListRepository.save(new HobbyList("게임"));
            hobbyListRepository.save(new HobbyList("여행"));
            hobbyListRepository.save(new HobbyList("독서"));
            hobbyListRepository.save(new HobbyList("영화"));
            hobbyListRepository.save(new HobbyList("음악"));
            hobbyListRepository.save(new HobbyList("맛집 탐방"));
            hobbyListRepository.save(new HobbyList("카페"));
            hobbyListRepository.save(new HobbyList("산책"));
            hobbyListRepository.save(new HobbyList("쇼핑"));
        }
    }
}
