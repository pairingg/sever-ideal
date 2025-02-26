package pairing.ideal.member.staticData.init;//package pairing.member.staticData.init;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//import pairing.member.staticData.entity.RegionList;
//import pairing.member.staticData.entity.ResidenceList;
//import pairing.member.staticData.repository.RegionListRepository;
//import pairing.member.staticData.repository.ResidenceListRepository;
//
//@Component
//@RequiredArgsConstructor
//public class ResidenceInitializer implements CommandLineRunner {
//
//    private final ResidenceListRepository residenceListRepository;
//    private final RegionListRepository regionListRepository;
//
//    @Override
//    public void run(String... args) throws Exception {
//        // 데이터가 이미 존재하는 경우 중복 삽입 방지
//        if (residenceListRepository.count() == 0 && regionListRepository.count() == 0) {
//
//            RegionList seoul = new RegionList("Seoul", residence1);
//            RegionList busan = new RegionList("Busan", residence1);
//            RegionList incheon = new RegionList("Incheon", residence2);
//            RegionList daegu = new RegionList("Daegu", residence2);
//
//            regionListRepository.saveAll(Arrays.asList(seoul, busan, incheon, daegu));
//
//            // 3. Residence의 region 리스트 업데이트
//            residence1.getRegion().addAll(Arrays.asList(seoul, busan));
//            residence2.getRegion().addAll(Arrays.asList(incheon, daegu));
//
//            residenceListRepository.saveAll(Arrays.asList(residence1, residence2));
//        }
//    }
//}