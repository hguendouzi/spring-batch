package fr.hguendouzi.scheduler;

import fr.hguendouzi.launcher.BatchLauncher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * @author hguendouzi
 */

@RequiredArgsConstructor
@Slf4j
public class Scheduler {

    private final BatchLauncher launcher;

    @Scheduled(fixedDelay = 5000)
    public void runScheduler() {
        try {
            launcher.runBatch();
        } catch (Exception e) {
            log.error("failed to start scheduler");
        }
    }

}
