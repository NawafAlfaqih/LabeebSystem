package org.example.labeebsystem.Repository;

import org.example.labeebsystem.Model.Reward;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RewardRepository extends JpaRepository<Reward, Integer> {

    Reward findRewardById(Integer id);
}
