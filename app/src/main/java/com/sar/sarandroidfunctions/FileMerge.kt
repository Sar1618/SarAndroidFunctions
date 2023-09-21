package com.sar.sarandroidfunctions

import java.io.File
import java.io.FileReader
import java.io.FileWriter

fun main() {
    val listFiles = File("C:\\Users\\Administrator\\Desktop\\20221126").listFiles()
    val file = File("C:\\Users\\Administrator\\Desktop\\merge.txt")
    file.createNewFile()
    val strings = mutableListOf<String>()
    FileWriter(file).use { rw ->
        listFiles.forEach {
            FileReader(it).use { fr ->
                fr.readLines().forEach { text ->
                    if (!strings.contains(text)) {
                        rw.write(text)
                        strings.add(text)
                        rw.write("\r\n")
                    }
                }
            }
        }
    }
}