package top.spencercjh.exercise

import java.io.BufferedReader
import java.io.FileReader

/**
 * @author SpencerCJH
 * @date 2019/11/7 0:22
 */
fun readFirstLineFromFile(path: String): String? = BufferedReader(FileReader(path)).use { return it.readLine() }