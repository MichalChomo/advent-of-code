# advent-of-code

Welcome to the Advent of Code[^aoc] Kotlin project created by [michalchomo][github] using the [Advent of Code Kotlin Template][template] delivered by JetBrains.

In this repository, Michal Chomo is about to provide solutions for the puzzles using [Kotlin][kotlin] language.

## Modifications to the [Advent of Code Kotlin Template][template]
The directory structure is modified so that one repository can be used for multiple years. Each year has its own
package with prefix `year`, so `year2022`, `year2023`, etc.

Also, there is a gradle task `addDay` that generates files for a specified day, e.g.:
```shell
./gradlew addDay -n 17
```
generates files
- `Day17.kt` - copy of the template [DayTemplate.kt.template](src/main/kotlin/eu/michalchomo/adventofcode/DayTemplate.kt.template)
 with adjusted package and the names of the text files.
- `Day17.txt` - empty file, meant for the input of the day.
- `Day17_test.txt` - empty file, meant for the test input of the day.
- `Day17_answers.txt` - empty file, meant for the correct puzzle answers of the day. Used in tests.

[^aoc]:
    [Advent of Code][aoc] – An annual event of Christmas-oriented programming challenges started December 2015.
    Every year since then, beginning on the first day of December, a programming puzzle is published every day for twenty-five days.
    You can solve the puzzle and provide an answer using the language of your choice.

[aoc]: https://adventofcode.com
[docs]: https://kotlinlang.org/docs/home.html
[github]: https://github.com/michalchomo
[issues]: https://github.com/kotlin-hands-on/advent-of-code-kotlin-template/issues
[kotlin]: https://kotlinlang.org
[slack]: https://surveys.jetbrains.com/s3/kotlin-slack-sign-up
[template]: https://github.com/kotlin-hands-on/advent-of-code-kotlin-template
