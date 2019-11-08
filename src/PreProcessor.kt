data class PreProcessor (val nameFile : String)
{
    private var codigo = ""

    init {
        codigo = ManipulateFile().readFileText(nameFile)
        readIncludes()
        readDefines()
        takeLineComments()
        takeSpaces()
        takeBlockComments()
        formatDefs()
        takeEspecialChar()
        ManipulateFile().writeFileText(codigo)
    }

    private fun findMatches(regexStr: String) : Sequence<MatchResult>
    {
        val regex = Regex(regexStr)
        val matches = regex.findAll(this.codigo, 0)

        return matches
    }

    private fun readIncludes()
    {
        val matches = findMatches(("#include\\s+<(\\w+.\\w)>"))

        matches.forEach { match ->
            val arq = ManipulateFile().readFileText("files/" + match.groupValues[1])

            codigo = codigo.replace(match.groupValues[0], arq)
        }
    }

    private fun readDefines()
    {
        val matches = findMatches(("#define\\s+(\\w+)\\s+(\\d+)"))

        matches.forEach { match ->
            codigo = codigo.replace(match.groupValues[0], "")
            codigo = codigo.replace(match.groupValues[1], match.groupValues[2])
        }
    }

    private fun takeLineComments()
    {
        val matches = findMatches("//(.*)")

        matches.forEach { match ->
            codigo = codigo.replace(match.groupValues[0], "")
        }
    }

    private fun takeSpaces()
    {
        val matches = findMatches("\\n|\\t")

        matches.forEach { match -> codigo = codigo.replace(match.groupValues[0], "") }
    }

    private fun takeBlockComments()
    {
        val matches = findMatches("\\/\\*(.*?)\\*\\/")

        matches.forEach { match ->
            codigo = codigo.replace(match.groupValues[0], "")
        }
    }

    private fun formatDefs()
    {
        codigo = codigo.replace("endif", "endif ")
        codigo = codigo.replace("ifndef", "ifndef ")
    }

    private fun takeEspecialChar()
    {
        val matches = findMatches("\\s?+(\\;|\\(|\\)|\\=|\\=\\=|\\!\\=|\\>\\=|\\<\\=|\\,|\\{|\\}|\\+|\\-|\\/|\\*|\\#|\\|\\||\\&\\&|\\&|\\|)\\s?+")
        matches.forEach { match -> codigo = codigo.replace(match.groupValues[0], match.groupValues[1]) }
    }
}