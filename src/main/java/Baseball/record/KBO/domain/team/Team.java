package Baseball.record.KBO.domain.team;

import Baseball.record.KBO.domain.player.Player;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter @Setter
public class Team {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "team")
    @JsonManagedReference
    private List<Player> players;

    @Enumerated(EnumType.STRING)
    private TeamName name; // "한화"

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL)
    private List<TeamRecord> records; // 하루하루 쌓이는 기록들

    public Team() {
    }
}
