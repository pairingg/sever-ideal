package pairing.ideal.member.dto.response;

import java.util.List;
import lombok.Getter;
import pairing.ideal.member.common.Drinking;
import pairing.ideal.member.common.Smoking;

@Getter
public class DrinkAndSmokeResponse {
    private List<Smoking> smokings = Smoking.getAll();
    private List<Drinking> drinkings = Drinking.getAll();
}
