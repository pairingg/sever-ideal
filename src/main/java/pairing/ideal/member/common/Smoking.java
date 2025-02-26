package pairing.ideal.member.common;

import java.util.ArrayList;
import java.util.List;

public enum Smoking {

    never("비흡연"),
    sometimes("가끔 피움"),
    always("매일 피움"),
    electronic("전자 담배"),
    nonSmoking("금연중");

    final private String smoking;

    Smoking(String smoking) {
        this.smoking = smoking;
    }

    public String getDrinking() {
        return this.smoking;
    }

    public static List<Smoking> getAll(){
        List<Smoking> all = new ArrayList<Smoking>();
        all.add(Smoking.never);
        all.add(Smoking.sometimes);
        all.add(Smoking.always);
        all.add(Smoking.electronic);
        all.add(Smoking.nonSmoking);
        return all;
    }
}
