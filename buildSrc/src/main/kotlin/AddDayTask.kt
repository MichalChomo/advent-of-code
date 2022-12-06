import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.options.Option
import java.io.File

open class AddDayTask : DefaultTask() {

    var dayNumber: String = "1"
        @Input
        get() = field
        @Option(option = "n", description = "Number of a day to add")
        set(value) {
            field = value
        }

    private fun String.toDayName() = "Day%02d".format(this.toInt())

    private fun applyToFileIfNotExists(path: String, f: (File) -> Unit) =
        File(path).apply {
            if (!this.exists()) {
                f(this)
            }
        }

    private fun createFileIfNotExists(path: String) = applyToFileIfNotExists(path) { it.createNewFile() }

    @TaskAction
    fun addDay() {
        val srcPath = "${project.rootDir}/src"
        val templateFile = File("$srcPath/DayTemplate.kt")
        applyToFileIfNotExists("$srcPath/${dayNumber.toDayName()}.kt") { templateFile.copyTo(it) }
        createFileIfNotExists("$srcPath/${dayNumber.toDayName()}.txt")
        createFileIfNotExists("$srcPath/${dayNumber.toDayName()}_test.txt")
    }
}