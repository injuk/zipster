package ga.injuk.zipster

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ZipsterApplication

fun main(args: Array<String>) {
	runApplication<ZipsterApplication>(*args)
}
