package cn.mklaus.demo.conf;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author klaus
 * @date 2019/3/28 2:53 PM
 */
@Component
public class QuartzService {

    @Scheduled(cron = "0 0 0 * * ?", zone = "Asia/Shanghai")
    public void deleteVoteData() {

    }

}
