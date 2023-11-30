import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.options.Option
import java.io.File
import java.time.LocalDateTime

private const val RELATIVE_PATH_TO_PACKAGE = "src/main/kotlin/eu/michalchomo/adventofcode"

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
        val srcPath = "${project.rootDir}/$RELATIVE_PATH_TO_PACKAGE"
        val templateFile = File("$srcPath/DayTemplate.kt.template")
        val currentYear = LocalDateTime.now().year
        val currentYearPath = "$srcPath/year$currentYear"
        applyToFileIfNotExists("$currentYearPath/${dayNumber.toDayName()}.kt") {
            it.writeText(
                "package eu.michalchomo.adventofcode.year$currentYear\n\n" + templateFile.readText()
                    .replace("\"Day", "\"${dayNumber.toDayName()}")
            )
        }
        createFileIfNotExists("$currentYearPath/${dayNumber.toDayName()}.txt")
        createFileIfNotExists("$currentYearPath/${dayNumber.toDayName()}_test.txt")
    }
}
