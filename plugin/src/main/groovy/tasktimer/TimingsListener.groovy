package tasktimer

import org.gradle.BuildListener
import org.gradle.BuildResult
import org.gradle.api.Task
import org.gradle.api.execution.TaskExecutionListener
import org.gradle.api.initialization.Settings
import org.gradle.api.invocation.Gradle
import org.gradle.api.tasks.TaskState

import java.util.concurrent.TimeUnit

class TimingsListener implements TaskExecutionListener, BuildListener {
    private long startTime
    private long initialStartTime = 0
    private long finalEndTime = 0
    private timings = []

    @Override
    void beforeExecute(Task task) {
        if (initialStartTime == 0) {
            initialStartTime = System.nanoTime()
        }
        startTime = System.nanoTime()
    }

    @Override
    void afterExecute(Task task, TaskState taskState) {
        def time = System.nanoTime()
        finalEndTime = time
        def ms = TimeUnit.MILLISECONDS.convert(time - startTime, TimeUnit.NANOSECONDS)
        timings.add(new Tuple2<Integer, String>(ms, task.path))
        task.project.logger.warn "${task.project.name}:${task.name} took ${ms}ms"
    }

    @Override
    void buildFinished(BuildResult result) {
        def totalTime = TimeUnit.MILLISECONDS.convert(finalEndTime - initialStartTime, TimeUnit.NANOSECONDS)
        println "Total time = ${totalTime}ms"
    }

    @Override
    void projectsEvaluated(Gradle gradle) {}

    @Override
    void projectsLoaded(Gradle gradle) {}

    @Override
    void settingsEvaluated(Settings settings) {}
}
