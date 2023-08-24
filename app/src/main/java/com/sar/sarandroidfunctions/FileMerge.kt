package com.sar.sarandroidfunctions

import java.io.File
import java.io.FileReader
import java.io.FileWriter

fun main() {
    val listFiles = File("C:\\Users\\Administrator\\Desktop\\20221126").listFiles()
    val file = File("C:\\Users\\Administrator\\Desktop\\merge.txt")
    file.createNewFile()
    FileWriter(file).use { rw ->
        listFiles.forEach {
            FileReader(it).use { fr ->
                fr.readLines().forEach { text ->
                    rw.write(text)
                    rw.write("\r\n")
                }
            }
            rw.write("\r\n")
            rw.write("\r\n")
        }
    }
}