package com.example.twobufferpagination

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.withContext
import java.io.IOException


class LogFilesReader {

    val filesText: MutableStateFlow<ArrayList<String?>> = MutableStateFlow(arrayListOf())
    private var startPosition: Long = 0
    private var fromZeroPosition = false
    private var endReached = false

    suspend fun readFiles(context: Context) {
        withContext(Dispatchers.Default) {

            val text: ArrayList<String?> = ArrayList(10)
            for (i in 0..9) {
                text.add(null)
            }


            async(Dispatchers.IO) {
                val byteArray = ByteArray(1024)
                text[9] = null
                if (startPosition == 0.toLong()) {
                    val inputStream = context.assets.open("20002216_ServerSequence.log")
                    var readNumber = inputStream.read(byteArray, 0, 1024)
                    for (i in text.indices) {
                        try {
                            if (readNumber != -1) {
                                text[i] = String(byteArray)
                                readNumber = inputStream.read(byteArray, 0, 1024)
                            } else {
                                endReached = true
                            }
                        } catch (e: IOException) {
                            e.printStackTrace()
                        }
                    }
                    inputStream.close()
                    startPosition = 1024 * 10
                    fromZeroPosition = true
                } else if (fromZeroPosition && !endReached) {
                    text[4] = null
                    val inputStream = context.assets.open("20002216_ServerSequence.log")
                    inputStream.skip(startPosition)
                    var readNumber = inputStream.read(byteArray, 0, 1024)
                    for (i in 0 until 5) {
                        try {
                            if (readNumber != -1) {
                                text[i] = String(byteArray)
                                readNumber = inputStream.read(byteArray, 0, 1024)
                            } else {
                                endReached = true
                            }
                        } catch (e: IOException) {
                            e.printStackTrace()
                        }
                    }
                    inputStream.close()
                    startPosition += 1024 * 5
                    fromZeroPosition = false
                } else if (!fromZeroPosition && !endReached) {
                    text[9] = null
                    val inputStream = context.assets.open("20002216_ServerSequence.log")
                    inputStream.skip(startPosition)
                    var readNumber = inputStream.read(byteArray, 0, 1024)
                    for (i in 5 until 10) {
                        try {
                            if (readNumber != -1) {
                                text[i] = String(byteArray)
                                readNumber = inputStream.read(byteArray, 0, 1024)
                            } else {
                                endReached = true
                            }
                        } catch (e: IOException) {
                            e.printStackTrace()
                        }
                    }
                    inputStream.close()
                    startPosition += 1024 * 5
                    fromZeroPosition = true
                }
            }



            async(Dispatchers.IO) {
                if (startPosition == 0.toLong()) {
                    text[9] = null
                    while (text[9] == null) {

                    }
                    filesText.value = text
                } else if (fromZeroPosition && !endReached) {
                    text[4] = null
                    while (text[4] == null) {

                    }
                    filesText.value = text
                } else if (!fromZeroPosition && !endReached) {
                    text[9] = null
                    while (text[9] == null) {

                    }
                    filesText.value = text
                }
            }
        }
    }
}