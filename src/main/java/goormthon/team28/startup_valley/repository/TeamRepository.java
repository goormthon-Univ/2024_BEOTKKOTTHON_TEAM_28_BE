package goormthon.team28.startup_valley.repository;

import goormthon.team28.startup_valley.domain.Member;
import goormthon.team28.startup_valley.domain.Team;
import goormthon.team28.startup_valley.dto.type.EProjectStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;


@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
    Optional<Team> findByGuildId(String guildId);
    @Modifying(clearAutomatically = true)
    @Query("update Team t set t.leader = :leader where t.id = :teamId")
    void updateLeader(Long teamId, Member leader);
    @Modifying(clearAutomatically = true)
    @Query("update Team t set t.status = :status where t.id = :teamId")
    void updateStatus(Long teamId, EProjectStatus status);
    @Modifying(clearAutomatically = true)
    @Query("update Team t set t.name = :name, t.teamImage = :image where t.id = :teamId")
    void updateInformation(Long teamId, String name, String image);
    @Modifying(clearAutomatically = true)
    @Query("update Team t set t.endAt = :endAt where t.id = :teamId")
    void updateEndAt(Long teamId, LocalDate endAt);
}
