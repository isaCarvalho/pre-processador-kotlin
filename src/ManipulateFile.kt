import java.io.File
import java.io.FileReader

class ManipulateFile()
{
    fun readFileText(name : String) : String
    {
        try {
            val file = FileReader(name)
            return file.readText()

        } catch (e: Exception) {
            println(e.message)
            return "Nao foi possivel ler o arquivo!"
        }
    }

    fun writeFileText(content: String)
    {
        try {
            val file = File("files/arquivoFinal.c")
            file.writeText(content)
        } catch (e: java.lang.Exception) {
            println(e.message)
        }
    }
}