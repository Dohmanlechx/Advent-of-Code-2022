package puzzles

import reader.Reader

class Day07 {
    private val computer = Computer().also { it.execute(terminalOutput = Reader.input(7)) }

    fun p1() {
        val res = computer.sumOfDirectoriesUnderMaxSize()
        require(res == 1206825)
    }

    fun p2() {
        val res = computer.findSmallestDirectoryToDeleteForTheUpgrade()?.size()
        require(res == 9608311)
    }
}

class Computer {
    companion object {
        private const val DIRECTORY_MAX_SIZE = 100000
        private const val AVAILABLE_FILESYSTEM_SIZE = 70000000
        private const val SPACE_NEEDED_FOR_UPGRADE = 30000000
    }

    private val currentPath: MutableList<Directory> = mutableListOf()
    private val directories: MutableList<Directory> = mutableListOf()

    fun execute(terminalOutput: List<String>) {
        for (i in terminalOutput.indices) {
            val line = terminalOutput[i]

            if (line.isCommand()) {
                when {
                    line.cd() -> {
                        when (val path = line.substringAfter("$ cd ")) {
                            ".." -> back()
                            else -> forward(path)
                        }
                    }
                    line.ls() -> {
                        val output = terminalOutput.drop(i + 1)
                        val nextCommand = output.indexOfFirst { it.isCommand() }.takeIf { it > 0 } ?: output.count()
                        val subList = output.subList(0, nextCommand)

                        for (it in subList)
                            when {
                                it.dir() -> createNewDirectory(it)
                                it.file() -> createNewFile(it)
                            }
                    }
                }
            }
        }
    }

    private fun back() {
        if (currentPath.isNotEmpty()) {
            currentPath.removeLast()
        }
    }

    private fun forward(path: String) {
        val child = currentPath.lastOrNull()?.directories?.find { it.path == path } ?: Directory(path)
        currentPath.add(child)
        directories.add(child)
    }

    private fun createNewDirectory(input: String) {
        val name = input.substringAfter("dir ")
        directories.last().addDirectory(Directory(path = name))
    }

    private fun createNewFile(input: String) {
        val (size, name) = input.split(" ")
        directories.last().addFile(name, size.toInt())
    }

    fun findSmallestDirectoryToDeleteForTheUpgrade(): Directory? {
        val unusedSpace = AVAILABLE_FILESYSTEM_SIZE - directories[0].size()
        val neededSpace = SPACE_NEEDED_FOR_UPGRADE - unusedSpace

        return directories
            .sortedBy { it.size() }
            .firstOrNull { it.size() >= neededSpace }
    }

    fun sumOfDirectoriesUnderMaxSize(): Int {
        return directories.fold(0) { acc, directory ->
            val size = directory.size()
            acc + if (size <= DIRECTORY_MAX_SIZE) size else 0
        }
    }

    private fun String.isCommand() = first() == '$'
    private fun String.cd() = startsWith("$ cd")
    private fun String.ls() = startsWith("$ ls")
    private fun String.dir() = startsWith("dir")
    private fun String.file() = first().isDigit()
}

data class Directory(
    val path: String,
    val directories: MutableList<Directory> = mutableListOf(),
    val files: MutableMap<String, Int> = mutableMapOf(),
) {
    fun addDirectory(directory: Directory) {
        directories.add(directory)
    }

    fun addFile(name: String, size: Int) {
        files[name] = size
    }

    fun size(s: Int = 0): Int {
        return files.values.sum() + directories.fold(0) { acc, directory ->
            acc + directory.size(s)
        }
    }
}