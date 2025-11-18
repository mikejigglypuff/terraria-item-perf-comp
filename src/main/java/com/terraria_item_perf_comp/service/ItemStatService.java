package com.terraria_item_perf_comp.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import org.springframework.stereotype.Service;

import com.terraria_item_perf_comp.models.Title;
import com.terraria_item_perf_comp.models.User;
import com.terraria_item_perf_comp.models.UserBalanceStat;
import com.terraria_item_perf_comp.repository.UserBalanceStatRepository;

@Service
public class ItemStatService {

    private static final int SCALE = 3;
    private final UserBalanceStatRepository userBalanceStatRepository;

    public ItemStatService(UserBalanceStatRepository userBalanceStatRepository) {
        this.userBalanceStatRepository = userBalanceStatRepository;
    }

    /**
     * 정답 여부 배열을 받아 평균 정답률을 계산하고 user_balance_stats 테이블에 저장합니다.
     *
     * @return 저장된 correctnessRate
     */
    public BigDecimal saveUserBalanceStat(int titleId, int userId, boolean[] correctnessFlags) {
        BigDecimal correctnessRate = calculateCorrectnessRate(correctnessFlags);

        UserBalanceStat stat = userBalanceStatRepository
                .findByUserIdAndTitleId(userId, titleId)
                .orElseGet(() -> UserBalanceStat.builder()
                        .user(User.builder().id(userId).build())
                        .title(Title.builder().id(titleId).build())
                        .build());

        stat.updateCorrectnessRate(correctnessRate);
        UserBalanceStat saved = userBalanceStatRepository.save(stat);
        return saved.getCorrectnessRate();
    }

    private BigDecimal calculateCorrectnessRate(boolean[] correctnessFlags) {
        if (correctnessFlags == null || correctnessFlags.length == 0) {
            return BigDecimal.ZERO.setScale(SCALE, RoundingMode.HALF_UP);
        }

        int correctCount = 0;
        for (boolean flag : correctnessFlags) {
            if (flag) {
                correctCount++;
            }
        }
        BigDecimal rate = BigDecimal.valueOf(correctCount)
                .divide(BigDecimal.valueOf(correctnessFlags.length), SCALE, RoundingMode.HALF_UP);
        return rate;
    }
}


