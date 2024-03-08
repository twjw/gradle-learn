import java.nio.file.Paths

plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}

tasks.register("hello") {
    // dependsOn 會早在自己執行
    // I'm Gradle -> Hello world!
    dependsOn("intro")
    // doLast 會在執行階段中執行
    doLast {
        println("Hello world!")
    }
}

tasks.register("intro") {
    dependsOn("dynamic1")

    doLast {
        println("I'm Gradle")
    }
}

for (i in 0 until 3) {
    val j = i + 1
    tasks.register("dynamic$j") {
        doLast {
            println("dynamic-task-$j")
        }
    }
}

tasks.register("myLifecycle") {
    doFirst {
        println("myLifecycle-doLast")
    }
    doLast {
        println("myLifecycle-doLast")
    }
    doLast {
        println("myLifecycle-doLast2")
    }
}

// 另一種寫法
val valHello by tasks.registering {
    doLast {
        println("Hello world!")
    }
    doLast() {
        println("Greetings from the $name task.")
    }
}

// apply 再寫回申明處
valHello.get().apply {
    doLast {
        println("Greetings from the $name task. 2")
    }
}


tasks.register("propertyTask") {
    // ext 像是 sessionStorage 那樣寫讀值
    ext.set("hello", "world")

    doLast {
        // 調用
        println(ext.get("hello"))
    }
}

tasks.register("fntest") {
    doLast {
        // 也可以像這樣寫方法調用
        readFile()
    }
}

fun readFile() {
    val path = Paths.get("D:\\2cdaa4ae728a14ec660a6d50be1bd1ef.jpg")
    val filename = path.fileName.toString()
    println("Filename is $filename")
}
