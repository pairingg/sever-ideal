package pairing.ideal.member.common;

import java.util.ArrayList;
import java.util.List;

public enum Drinking {

    atAllNothing("전혀 안마심"),
    unavoidable("피할 수 없을 때만"),
    sometimes("가끔 마심"),
    often("자주 마심"),
    quit("금주중");


    final private String drinking;
    private Drinking(String drinking) {
        this.drinking = drinking;
    }
    public String getDrinking() {
        return this.drinking;
    }

    public static List<Drinking> getAll(){
        List<Drinking> all = new ArrayList<Drinking>();
        all.add(atAllNothing);
        all.add(unavoidable);
        all.add(sometimes);
        all.add(often);
        all.add(quit);
        return all;
    }
}
