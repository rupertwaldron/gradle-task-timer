/*
 * This Groovy source file was generated by the Gradle 'init' task.
 */
package tasktimer

import spock.lang.Specification
import spock.lang.TempDir
import org.gradle.testkit.runner.GradleRunner

/**
 * A simple functional test for the 'tasktimer.greeting' plugin.
 */
class TaskTimerPluginFunctionalTest extends Specification {
    @TempDir
    private File projectDir

    private getBuildFile() {
        new File(projectDir, "build.gradle")
    }

    private getSettingsFile() {
        new File(projectDir, "settings.gradle")
    }

    def "can run task"() {
        given:
        settingsFile << ""
        buildFile << """
plugins {
    id 'com.gradlehero.task-timer'
}
"""

        when:
        def runner = GradleRunner.create()
        runner.forwardOutput()
        runner.withPluginClasspath()
//        runner.withArguments("taskTimer")
        runner.withProjectDir(projectDir)
        def result = runner.build()

        then:
        result.output.contains("Total time =")
    }
}