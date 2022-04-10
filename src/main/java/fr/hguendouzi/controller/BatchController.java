package fr.hguendouzi.controller;

import fr.hguendouzi.launcher.BatchLauncher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hguendouzi
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api")
public class BatchController {

    private final BatchLauncher launch;

    @GetMapping("/launchBatch")
    public @ResponseBody
    BatchStatus doLaunch() {
        BatchStatus status = null;
        try {
            status = launch.runBatch();
        } catch (Exception e) {
            log.error("batch failed");
            status = BatchStatus.FAILED;
        }
        return status;
    }

}
