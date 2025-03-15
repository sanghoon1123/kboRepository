package Baseball.record.KBO.domain.team;

import lombok.Getter;

@Getter
public enum TeamName {
    KIA(87,55,2,0.613),
    SAMSUNG(78,64,2,0.549),
    LG(76, 66, 2, 0.535),
    DOOSAN(74, 68, 2, 0.521),
    KT(72, 70, 2, 0.507),
    SSG(72, 70, 2, 0.507),
    LOTTE(66, 74, 4, 0.471),
    HANWHA(66, 76, 2, 0.465),
    NC(61, 81, 2, 0.430),
    KIWOOM(58, 86, 0, 0.403);

    private final int win;
    private final int lose;
    private final int draw;
    private final double winningRate;

    TeamName(int win, int lose, int draw, double winningRate) {
        this.win = win;
        this.lose = lose;
        this.draw = draw;
        this.winningRate = winningRate;
    }
}
