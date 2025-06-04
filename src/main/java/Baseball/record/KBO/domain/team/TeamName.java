package Baseball.record.KBO.domain.team;



import java.util.Arrays;
import java.util.Optional;

public enum TeamName {

    SSG("SSG"),
    LG("LG"),
    KT("KT"),
    NC("NC"),
    KIA("KIA"),
    키움("키움"),
    롯데("롯데"),
    두산("두산"),
    한화("한화"),
    삼성("삼성");

    private final String koreanName;

    TeamName(String koreanName) {
        this.koreanName = koreanName;
    }

    public String getKoreanName() {
        return koreanName;
    }

    public static Optional<TeamName> fromKoreanName(String name) {
        return Arrays.stream(values())
                .filter(t -> t.koreanName.equals(name))
                .findFirst();
    }
}